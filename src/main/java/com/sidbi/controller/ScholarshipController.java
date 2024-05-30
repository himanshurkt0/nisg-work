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
import com.sidbi.bean.ReimbBookingHistory;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.SchoolScholarshipBillUpload;
import com.sidbi.service.AppService;

@Controller
public class ScholarshipController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/ScholarshipSCST", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView scholarshipSCST(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String empCode = (String) session.getAttribute("empCode");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		List<BcmModuleBean> bcmModuleBean = (List) session.getAttribute("bcmModuleBean");

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
		appNameTracker = appNameTracker + "->" + "Scholarship - SC/ST";

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

	@RequestMapping(value = "/ScholarShipSaveUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView scholarShipSaveUpdate(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession(true);
		String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		ModelAndView mv = new ModelAndView();
		String notifyMsg = "";
		ReimbBookingHistory bookingHistory = new ReimbBookingHistory();
		boolean bookingAllowed = true;
		reimbursementBean.setDependentList(appService.getDependents(reimbursementBean.getEmpCode()));
		reimbursementBean.setCoursePassedList(appService.getBookGrantCoursePassedList());
		reimbursementBean.setCoursePersueList(appService.getbookGrantCoursePersueMap());
		bookingHistory = appService.getScholarshipPreviousBooking(reimbursementBean);
		String yearCount = appService.getScholarshipYearCount(reimbursementBean);
		String duration = appService.getScholarshipDuration(reimbursementBean);
		if (duration == "") {
			duration = "0";
		}
		bookingHistory.setYearCount(yearCount);
		bookingHistory.setDuration(duration);
		String coursePersueCode = "" + reimbursementBean.getCoursePassedCode();
		if (isUpdatingPage == null) {
			isUpdatingPage = "";
		}
		if (bookingHistory.getCount() > 0 && bookingHistory.getStatus().equalsIgnoreCase("N")) {

			String afmName = appService.getBookGrantFmName(bookingHistory.getFmCode());
			notifyMsg = "Already applied " + afmName + " for the session " + bookingHistory.getSessionStartDate()
					+ " To " + bookingHistory.getSessionEndDate() + " For Course " + bookingHistory.getCourseDesc();
			if (isUpdatingPage.equalsIgnoreCase("true")) {
				bookingAllowed = true;
			} else {
				bookingAllowed = false;
			}

		}
		/*if (bookingHistory.getCount() > 0) {
			String afmName = appService.getBookGrantFmName(bookingHistory.getFmCode());
			notifyMsg = "Already applied " + afmName + " for the session " + bookingHistory.getSessionStartDate()
					+ " To " + bookingHistory.getSessionEndDate() + " For Course " + bookingHistory.getCourseDesc();
			bookingAllowed = false;
		}*/

		if (Integer.parseInt(yearCount) >= Integer.parseInt(duration)
				&& (coursePersueCode.equals("2") || coursePersueCode.equals("3"))
				&& bookingHistory.getStatus().equalsIgnoreCase("R")) {
			notifyMsg = "Already Availed Scolarship for "
					+ reimbursementBean.getDependentList().get(reimbursementBean.getDependentCode());
			bookingAllowed = false;
		}

		if (Integer.parseInt(reimbursementBean.getDuration()) > 18) {
			notifyMsg = "Session Duration cannot be more than 18 Months.";
			bookingAllowed = false;
		}

		if (Integer.parseInt(yearCount) >= Integer.parseInt(duration) && coursePersueCode.equals("4")
				&& bookingHistory.getStatus().equalsIgnoreCase("R")) {
			notifyMsg = "Already Availed Scolarship for "
					+ reimbursementBean.getDependentList().get(reimbursementBean.getDependentCode());
			bookingAllowed = false;
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
		if (durationInMonth > 18) {
			notifyMsg = "Session Duration cannot be more than 18 Months.";
			bookingAllowed = false;
		}

		if (bookingAllowed) {
			String lv_sr_no = "";
			reimbursementBean.setIsUpdatingPage(isUpdatingPage);
			if (isUpdatingPage.equalsIgnoreCase("true")) {
				lv_sr_no = reimbursementBean.getLv_sr_no();

			} else {
				lv_sr_no = appService.getLVRSerialNumber(reimbursementBean.getEmpCode(),
						AppConstants.ScholarshipConstant);
			}

			String dependentRelation = appService.getDependentRelationName(reimbursementBean);
			reimbursementBean.setDependentRelation(dependentRelation);
			reimbursementBean.setLv_sr_no(lv_sr_no);
			reimbursementBean.setBranchCode(customerInfo.getBranchCode());
			notifyMsg = appService.saveScholarShipSCST(reimbursementBean);
			session.setAttribute("notifyMsg", notifyMsg);
			if (notifyMsg.contains("sucessfully saved") || notifyMsg.contains("sucessfully Updated")) {
				return new ModelAndView("redirect:/ScholarshipSCST");
			} 
		} else {
			if (notifyMsg.isEmpty()) {
				notifyMsg = "Sorry !! Your Booking was not successfull. Please try again.";
				session.setAttribute("notifyMsg", notifyMsg);
				return new ModelAndView("redirect:/ScholarshipSCST");
			}

		}
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}
	
	@GetMapping(value = "/downloadScholarshipBill/{empCode}/{srNo}")
	public void downloadScholarshipBill(@PathVariable String empCode, @PathVariable String srNo,
			HttpServletResponse response) throws SQLException, IOException {
		Blob blobPdf = appService.getFinAssisPdf(empCode.substring(1,empCode.length()-1), srNo.substring(1, srNo.length()-1),AppConstants.ScholarshipConstant);
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
