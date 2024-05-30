package com.sidbi.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.FinancialAssistDataDomain;
import com.sidbi.service.AppService;

@Controller
public class ReimbSCSTVerification {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/ScholarshipBookGrantFinAssistSCST", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView scholarshipBookGrantFinAssistVerfication(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		try {

			HttpSession session = request.getSession(true);
			String childModule = (String) session.getAttribute("moduleDesc");
			String appNameTracker = (String) session.getAttribute("appNameTracker");
			String empCode = (String) session.getAttribute("empCode");
			CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");

			String notifyMsg = (String) session.getAttribute("notifyMsg");
			if (notifyMsg == null) {
				notifyMsg = "";
			}
			mv.addObject("notifyMsg", notifyMsg);

			String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

			if (isUpdatingPage == null || isUpdatingPage.equalsIgnoreCase("true") || isUpdatingPage.isEmpty())
				isUpdatingPage = "false";

			session.setAttribute("isUpdatingPage", "");
			session.setAttribute("notifyMsg", "");
			appNameTracker = appNameTracker + "->" + childModule;

			List<FinancialAssistDataDomain> verificationBeanList = new ArrayList<FinancialAssistDataDomain>();
			List<FinancialAssistDataDomain> updatedverificationBeanList = new ArrayList<FinancialAssistDataDomain>();
			verificationBeanList = appService.getFinancialAssistVerifyList(empCode);
			reimbursementBean.setReimbursementVerificationList(verificationBeanList);

			for (FinancialAssistDataDomain bean : verificationBeanList) {
				String date = bean.getStartYear();
				String[] dateArray = date.split(" ");
				if(dateArray.length > 1){
					bean.setStartYear(AppConstants.formatDate(dateArray[0]));
				}
				
				date = bean.getEndYear();
				dateArray = date.split(" ");
				if(dateArray.length > 1){
					bean.setEndYear(AppConstants.formatDate(dateArray[0]));
				}
				
				
				if (bean.getVerifiedDate() != null) {
					date = bean.getVerifiedDate();
					dateArray = date.split(" ");
					if(dateArray.length > 1){
						bean.setVerifiedDate(AppConstants.formatDate(date));
					}
				}
				date = bean.getCreatedDate();
				dateArray = date.split(" ");
				if(dateArray.length > 1){
					bean.setCreatedDate(AppConstants.formatDate(dateArray[0]));
				}
				updatedverificationBeanList.add(bean);
			}
			reimbursementBean.setReimbursementVerificationList(updatedverificationBeanList);

			String catCode = appService.getCatCode(empCode);
			mv.addObject("empName", customerInfo.getEmpName());
			mv.addObject("empGrade", customerInfo.getEmpGrade());
			mv.addObject("catCode", catCode);
			mv.addObject("appNameTracker", appNameTracker);
			mv.setViewName("secondLevelHome");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@GetMapping(value = "/downloadBill/{empCode}/{srNo}/{fmCode}")
	public void downloadBill(@PathVariable String empCode, @PathVariable String srNo, @PathVariable String fmCode,
			HttpServletResponse response) throws SQLException, IOException {
		Blob blobPdf = appService.getFinAssisPdf(empCode.substring(1, empCode.length() - 1),
				srNo.substring(1, srNo.length() - 1), fmCode.substring(1, srNo.length() - 1));
		int blobLength = (int) blobPdf.length();
		byte[] blobAsBytes = blobPdf.getBytes(1, blobLength);
		response.setHeader("Content-Disposition", "inline; filename=\"" + "ReimbursementBill" + "\";");
		OutputStream output = response.getOutputStream();
		output.write(blobAsBytes);
		output.close();
		output.flush();
		blobPdf.free();
	}

	@RequestMapping(value = "/approveReimbursementFinAssist", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView approveReimbursementFinAssist(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);

		String notifyMsg = "";
		notifyMsg = (String) session.getAttribute("notifyMsg");
		if (notifyMsg == null) {
			notifyMsg = "";
		}
		boolean isRejecting = false;
		String[] toApproveReimbList = reimbursementBean.getApproveRejectList().split(",");
		int approveBeanList = appService.getApproveRejectReimBeans(toApproveReimbList, isRejecting);

		if (approveBeanList > 0) {
			notifyMsg = "Successfully Approved .";
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/ScholarshipBookGrantFinAssistSCST");
		} else {
			notifyMsg = "Unable to Approve due to some Error .";
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/ScholarshipBookGrantFinAssistSCST");
		}

	}

	@RequestMapping(value = "/rejectReimbursementFinAssist", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView rejectReimbursementFinAssist(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);

		String notifyMsg = "";

		boolean isRejecting = true;
		String[] toRejectReimbList = reimbursementBean.getApproveRejectList().split(",");
		int rejectedData = appService.getApproveRejectReimBeans(toRejectReimbList, isRejecting);

		if (rejectedData > 0) {
			notifyMsg = "Successfully Rejected .";
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/ScholarshipBookGrantFinAssistSCST");
		} else {
			notifyMsg = "Unable to Rejected due to some Error .";
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/ScholarshipBookGrantFinAssistSCST");
		}

	}

}
