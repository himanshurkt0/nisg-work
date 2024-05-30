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
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class IncidentalExpenseController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/IncidentalExpense", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView incidentalExpense(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		String tabOpened = "IncidentalExpense";
		try {
			HttpSession session = request.getSession(true);
			CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
			String empCode = customerInfo.getEmpCode();
			String appNameTracker = (String) session.getAttribute("appNameTracker");
			String notifyMsg = (String) session.getAttribute("notifyMsg");
			String transferTabMsg = "";
			Date currentDate = new Date();
			String empGrade = "";
			reimbursementBean.setEmpCode(empCode);
			

			String incidentalMsg = "";
			String icnidentalPaymentType = "";
			String icnidentalFamilyShiftFlag = "";
			String icnidentalFamilyShiftDate = "";
			
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

			// TERRIF BENFIT INCIDENTAL DETAIL
			reimbursementBean.setTrfId(trfId);
			boolean isSuccess = true;
			isSuccess = appService.getIncidentalDetail(reimbursementBean);
			
			if (isSuccess) {
				icnidentalPaymentType = reimbursementBean.getIncidentalPaymentMode();
				icnidentalFamilyShiftFlag = reimbursementBean.getIncidentalFamilyShiftFalg();
				icnidentalFamilyShiftDate = reimbursementBean.getIncidentalFamilyShiftDate();
				reimbursementBean.setIncidentalClaimed("Y");
			}

			// INCIDENTAL CODE

			if (icnidentalFamilyShiftFlag.equals("N")) {
				mv.addObject("icnidentalFamilyShiftFlag", "enable");
				mv.addObject("pbApplyInc", "disable");
				mv.addObject("incCall", "disable");
				mv.addObject("icnidentalFamilyShiftDate", "enable");
			} else {
				mv.addObject("icnidentalFamilyShiftFlag", "disable");
				mv.addObject("pbApplyInc", "disable");
				mv.addObject("incCall", "disable");
				mv.addObject("icnidentalFamilyShiftDate", "disable");
			}

			if (icnidentalFamilyShiftFlag.equals("Y") && icnidentalPaymentType.equals("F")) {
				mv.addObject("pbApplyInc", "disable");
				mv.addObject("icnidentalFamilyShiftFlag", "disable");
				mv.addObject("icnidentalFamilyShiftDate", "disable");
			}
			
			if (icnidentalPaymentType.equals("F")) {
				incidentalMsg = "You have already applied/availed for full Incidental Expences";
			} else if (icnidentalPaymentType.equals("H")) {
				incidentalMsg = "You have already applied/availed for half Incidental Expences";
			}
			
			if (lastIncidentalFlag.equals("Y") || lastIncidentalFlag.equals("N")) {
				mv.addObject("lastIncidentalFlag", "disable");
			}
			
			reimbursementBean.setTypeOfTransfer(HrTrfType);
			reimbursementBean.setEmpName(customerInfo.getEmpName());
			reimbursementBean.setAccomodationType(trfType);
			
			mv.addObject("incidentalMsg", incidentalMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("tabOpened", tabOpened);
		mv.setViewName("secondLevelHome");
		return mv;
	}
	
	@RequestMapping(value = "/saveIncidentalExpense", method = { RequestMethod.POST })
	public ModelAndView saveIncidentalExpense(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
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

		try {
			
			reimbursementBean.setLastIncidentalFlag(lastIncidentalFlag);
			isSaved = appService.saveIncidentalExpense(reimbursementBean);
			
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
