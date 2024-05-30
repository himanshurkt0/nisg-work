package com.sidbi.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.BookGrantBookingHistory;
import com.sidbi.bean.PersonalAdvanceBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class BookGrantController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/BookGrantSCST", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookGrantSCST(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String empCode = (String) session.getAttribute("empCode");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		
		
		String afmName = appService.getBookGrantFmName(AppConstants.BookGrantConstant);
		
		//saving log details
		appService.saveLogAction(afmName, "" + customerInfo.getEmpName() + " Entered");

		String notifyMsg = (String) session.getAttribute("notifyMsg");
		mv.addObject("notifyMsg", notifyMsg);
		if (notifyMsg == null) {
			notifyMsg = "";
		}

		String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

		if (isUpdatingPage == null || isUpdatingPage.equalsIgnoreCase("true"))
			isUpdatingPage = "false";
		
		session.setAttribute("isUpdatingPage", "");
		session.setAttribute("notifyMsg", "");
		appNameTracker = appNameTracker + "->" + "Book Grant - SC/ST";

		reimbursementBean.setDependentList(appService.getDependents(empCode));
		reimbursementBean.setCoursePassedList(appService.getBookGrantCoursePassedList());
		reimbursementBean.setCoursePersueList(appService.getbookGrantCoursePersueMap());
		if (customerInfo != null) {
			reimbursementBean.setEmpStatus(customerInfo.getEmpStatus());
		}

		String catCode = appService.getCatCode(empCode);
		mv.addObject("empName", customerInfo.getEmpName());
		mv.addObject("empGrade", customerInfo.getEmpGrade());
		mv.addObject("catCode", catCode);
		mv.addObject("appNameTracker", appNameTracker);
		mv.setViewName("secondLevelHome");
		return mv;

	}

	@RequestMapping(value = "/BookGrantSaveUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView bookGrantSaveUpdate(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession(true);
		String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		ModelAndView mv = new ModelAndView();
		String notifyMsg = "";
		BookGrantBookingHistory bookingHistory = new BookGrantBookingHistory();
		boolean bookingAllowed = true;
		reimbursementBean.setDependentList(appService.getDependents(reimbursementBean.getEmpCode()));
		reimbursementBean.setCoursePassedList(appService.getBookGrantCoursePassedList());
		reimbursementBean.setCoursePersueList(appService.getbookGrantCoursePersueMap());
		bookingHistory = appService.getBookGrantPreviousBooking(reimbursementBean);
		String yearCount = appService.getBookGrantYearCount(reimbursementBean);
		String duration = appService.getBookGrantDuration(reimbursementBean);
		String afmName = appService.getBookGrantFmName(AppConstants.BookGrantConstant);
		if (duration == "") {
			duration = "0";
		}
		bookingHistory.setYearCount(yearCount);
		bookingHistory.setDuration(duration);
		String coursePersueCode = "" + reimbursementBean.getCoursePassedCode();
		
		if(isUpdatingPage == null){
			isUpdatingPage ="";
		}

		if (bookingHistory.getCount() > 0 && bookingHistory.getStatus().equalsIgnoreCase("N")) {
			
			
			notifyMsg = "Already applied " + afmName + " for the session " + bookingHistory.getSessionStartDate()
					+ " To " + bookingHistory.getSessionEndDate() + " For Course " + bookingHistory.getCourseDesc();
			if(isUpdatingPage.equalsIgnoreCase("true")){
				bookingAllowed = true;
			} else {
				bookingAllowed = false;
			}
			
		}

		if (Integer.parseInt(yearCount) >= Integer.parseInt(duration)) {
			if (reimbursementBean.getStatus() == null || reimbursementBean.getStatus().isEmpty()) {
				reimbursementBean.setStatus("N");
			}
			if (coursePersueCode.equals("2") || coursePersueCode.equals("3") || coursePersueCode.equals("4")) {

				if (!reimbursementBean.getStatus().equals("R")) {
					notifyMsg = "Already Availed Scolarship for " + reimbursementBean.getDependentList()
							.get(String.valueOf(reimbursementBean.getDependentCode().charAt(0)));
					bookingAllowed = false;
				}
			}
		}

		int durationInMonth = AppConstants.durationInMonth(reimbursementBean.getStartDate(),
				reimbursementBean.getEndDate());
		if ((durationInMonth * 30) < 0) {
			notifyMsg = "Session End Date cannot be less than Session Start Date";
			bookingAllowed = false;
		}
		if (durationInMonth > 12) {
			notifyMsg = "Session Duration cannot be more than 12 Months.";
			bookingAllowed = false;
		}

		if (bookingAllowed) {
			String lv_sr_no = "";
			reimbursementBean.setIsUpdatingPage(isUpdatingPage);
			if(isUpdatingPage.equalsIgnoreCase("true")){
				lv_sr_no = reimbursementBean.getLv_sr_no();
				
			} else {
				lv_sr_no = appService.getLVRSerialNumber(reimbursementBean.getEmpCode(),
						AppConstants.BookGrantConstant);
			}
			
			String dependentRelation = appService.getDependentRelationName(reimbursementBean);
			reimbursementBean.setDependentRelation(dependentRelation);
			reimbursementBean.setLv_sr_no(lv_sr_no);
			reimbursementBean.setBranchCode(customerInfo.getBranchCode());
			notifyMsg = appService.saveBookGrantSCST(reimbursementBean);
			session.setAttribute("notifyMsg", notifyMsg);
			if (notifyMsg.contains("sucessfully saved") || notifyMsg.contains("sucessfully Updated")) {
				return new ModelAndView("redirect:/BookGrantSCST");
			}
		} else {
			if (notifyMsg.isEmpty()) {
				notifyMsg = "Sorry !! Your Booking was not successfull. Please try again.";
				session.setAttribute("notifyMsg", notifyMsg);
				return new ModelAndView("redirect:/BookGrantSCST");
			}

		}
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}
	
	
	
	@GetMapping(value = "/downloadbookGrantBill/{empCode}/{srNo}")
	public void downloadbookGrantBill(@PathVariable String empCode, @PathVariable String srNo,
			HttpServletResponse response) throws SQLException, IOException {
		Blob blobPdf = appService.getFinAssisPdf(empCode.substring(1,empCode.length()-1), srNo.substring(1, srNo.length()-1),AppConstants.BookGrantConstant);
		int blobLength = (int) blobPdf.length();
		byte[] blobAsBytes = blobPdf.getBytes(1, blobLength);
		response.setHeader("Content-Disposition","inline; filename=\"" + "FinAssistBill" + "\";");
		OutputStream output = response.getOutputStream();
		output.write(blobAsBytes);
		output.close();
		output.flush();
		blobPdf.free();
	}

}
