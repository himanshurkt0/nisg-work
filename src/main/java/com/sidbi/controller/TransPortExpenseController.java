package com.sidbi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.ReimbursementBean;
import com.sidbi.bean.TransportExpenseDetailBean;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class TransPortExpenseController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/TransPortExpense", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView transPortExpense(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		String tabOpened = "TransPortExpense";
		try {
			HttpSession session = request.getSession(true);
			CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
			String empCode = (String) session.getAttribute("empCode");
			String appNameTracker = (String) session.getAttribute("appNameTracker");
			String notifyMsg = (String) session.getAttribute("notifyMsg");
			String transferTabMsg = "";
			Date currentDate = new Date();
			String empGrade = "";
			reimbursementBean.setEmpCode(empCode);

			String lastIncidentalFlag = "";
			String lastTransFlag = "";
			String trfId = "";
			String HrTrfType = "";
			String trfType = "";

			String[] transferValidationFlagPart1 = appService.getTransferValidationFlagPart1(reimbursementBean);
			if (transferValidationFlagPart1.length > 1) {
				lastIncidentalFlag = transferValidationFlagPart1[0];
				lastTransFlag = transferValidationFlagPart1[1];
				trfId = transferValidationFlagPart1[2];
				HrTrfType = transferValidationFlagPart1[3];
				trfType = transferValidationFlagPart1[4];
			}

			// Tranport Expense Detail
			TransportExpenseDetailBean transportDetBean = new TransportExpenseDetailBean();
			transportDetBean.setTrfId(trfId);
			String[] transportExpenseDetail = appService.getTransportExpenseDetail(empCode, transportDetBean);

			String transportVerifyFlag = transportDetBean.getVerifyStatus();
			String transportPaymentFlag = transportDetBean.getPaidStatus();
			String transportMsg = "";
			if (transportVerifyFlag == null) {
				transportVerifyFlag = "";
			}
			if (transportPaymentFlag == null) {
				transportPaymentFlag = "";
			}
			if (transportPaymentFlag.equals("NN") || transportVerifyFlag.equals("NN")) {
				transportMsg = "Pending for verification";
			} else if (transportPaymentFlag.equals("RN") || transportVerifyFlag.equals("RN")) {
				transportMsg = "Rejected";
			} else if (transportPaymentFlag.equals("YN") || transportVerifyFlag.equals("YN")) {
				transportMsg = "Bill verified and pending for payment";
			} else if (transportPaymentFlag.equals("YY") || transportVerifyFlag.equals("YY")) {
				transportMsg = "Bill verified and paid";
			}
			
			reimbursementBean.setTypeOfTransfer(HrTrfType);
			reimbursementBean.setEmpName(customerInfo.getEmpName());
			reimbursementBean.setAccomodationType(trfType);

			mv.addObject("transportMsg", transportMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("tabOpened", tabOpened);
		mv.setViewName("secondLevelHome");
		return mv;
	}
	
	@RequestMapping(value = "/saveTransPortExpenseForm", method = { RequestMethod.POST })
	public ModelAndView saveTransPortExpenseForm(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		String empCode = customerInfo.getEmpCode();
		String branchCode = customerInfo.getBranchCode();
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String incidentalSaveMsg = "";
		boolean claimAllowed = true;
		boolean isSaved = false;
		// Previous Opened Tab
		String tabOpened = "IncidentalExpense";
		session.setAttribute("tabOpened", tabOpened);
		reimbursementBean.setEmpCode(empCode);
		reimbursementBean.setEmpBranchCode(branchCode);
		
		String lastIncidentalFlag = "";
		String lastTransFlag = "";
		String trfId = "";
		String HrTrfType = "";
		String trfType = "";
		
		String[] transferValidationFlagPart1 = appService.getTransferValidationFlagPart1(reimbursementBean);
		if (transferValidationFlagPart1.length > 1) {
			lastIncidentalFlag = transferValidationFlagPart1[0];
			lastTransFlag = transferValidationFlagPart1[1];
			trfId = transferValidationFlagPart1[2];
			HrTrfType = transferValidationFlagPart1[3];
			trfType = transferValidationFlagPart1[4];
		}
		reimbursementBean.setTrfId(trfId);
		reimbursementBean.setLastTransFlag(lastTransFlag);

		try {
			isSaved = appService.saveTransPortExpenseForm(reimbursementBean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("incidentalMsg", incidentalSaveMsg);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

}
