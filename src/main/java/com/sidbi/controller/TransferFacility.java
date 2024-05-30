package com.sidbi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.CarExpenseDetailBean;
import com.sidbi.bean.IncidentalExpenseBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.bean.TransportExpenseDetailBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class TransferFacility {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/TransferFacility", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView tansferFacility(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		try {
			
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
			String transferTarrifId = "";
			Date transferValidyDate = null;

			// Common for all tabs
			String transferTarrifIdAndValidity = appService.getTransferIdAndValidy(reimbursementBean);
			if(transferTarrifIdAndValidity != null){
				transferTarrifId = transferTarrifIdAndValidity.split("-")[0];
				transferValidyDate = transferTarrifIdAndValidity.split("-")[1] == null ? null
						: AppConstants.parseDate(transferTarrifIdAndValidity.split("-")[1]);
			}
			Date lvMaxMonth = appService.getTransferEffDate(reimbursementBean);
			
			// IMPLEMTATION TO MAINTAIN ELIGIBILITY OF A TAB FOR USER
			String[] transferValidationFlagPart1 = appService.getTransferValidationFlagPart1(reimbursementBean);
			if (transferValidationFlagPart1.length > 1) {
				lastIncidentalFlag = transferValidationFlagPart1[0];
				lastTransFlag = transferValidationFlagPart1[1];
				trfId = transferValidationFlagPart1[2];
				HrTrfType = transferValidationFlagPart1[3];
				trfType = transferValidationFlagPart1[4];
			}
			
			
			
			if(transferTarrifId==null || (transferValidyDate != null &&transferValidyDate.before(lvMaxMonth)) || (lastIncidentalFlag.equals("N") && lastTransFlag.equals("N"))){
				transferTabMsg = "You are not eligible for trf facility, for any clarification, pl get in touch with HRV LKO.";
			}
			
			String acmoStatus = appService.getTransferFacilityAcmoStatus(reimbursementBean);
			
			if (lastTransFlag.equals("Y") || lastTransFlag.equals("N")) {
				mv.addObject("lastTransFlag", "disable");
			} else {
				mv.addObject("lastTransFlag", "enable");
			}
			
			reimbursementBean.setTypeOfTransfer(HrTrfType);
			reimbursementBean.setEmpName(customerInfo.getEmpName());
			reimbursementBean.setAccomodationType(trfType);
			
			session.setAttribute("trfId", trfId);
			mv.addObject("transferTabMsg", transferTabMsg);
			mv.addObject("empName", customerInfo.getEmpName());
			mv.addObject("typeOfTransfer", HrTrfType);
			mv.addObject("accomodationType", trfType);
			mv.setViewName("secondLevelHome");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mv;
	}
	
	/*@PostMapping(value = "/IncidentalExpenses")
	public ModelAndView IncidentalExpenses(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		try {
			HttpSession session = request.getSession(true);
			String empCode = (String) session.getAttribute("empCode");
			String appNameTracker = (String) session.getAttribute("appNameTracker");
			String trfId = (String) session.getAttribute("trfId");
			String notifyMsg = (String) session.getAttribute("notifyMsg");
			String transferTabMsg = "";
			boolean isSuccess = true;
			Date currentDate = new Date();
			String empGrade = "";
			reimbursementBean.setEmpCode(empCode);
			reimbursementBean.setTrfId(trfId);
			String tabOpened = "IncidentalExpense";
			
			isSuccess = appService.getIncidentalDetail(reimbursementBean);
			
			if(isSuccess){
				System.out.println(isSuccess);
			}
			
			
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		mv.setViewName("IncidentalExpense");
		return mv;
	}*/
}
