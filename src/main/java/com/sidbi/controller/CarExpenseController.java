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

import com.sidbi.bean.CarExpenseDetailBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.bean.TransportExpenseDetailBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class CarExpenseController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/CarExpense", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView carExpense(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		String tabOpened = "CarExpense";
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

			// Car Expense Detail
			CarExpenseDetailBean carExpenseBean = new CarExpenseDetailBean();
			carExpenseBean.setTrfId(trfId);
			appService.getCarExpenseDetailBean(empCode, carExpenseBean);
			
			
			String carAvailed = carExpenseBean.getCarFlag();
			
			if(carAvailed != null && carAvailed.equals("Y")){
				reimbursementBean.setCarShifted(carAvailed);
				reimbursementBean.setCarRatePerKm(carExpenseBean.getCarRate());
				
				String carShiftDate = AppConstants.formatDateAccordingToUi(carExpenseBean.getCarShiftDate());
				reimbursementBean.setCarShiftDate(carShiftDate);
				reimbursementBean.setCarDistance(carExpenseBean.getCarTripCost());
				
				reimbursementBean.setCarDriver(carExpenseBean.getCarShiftMode());
				
				String carMode =  carExpenseBean.getCarShiftMode();
				if(carMode.equals("D")){
					String[] carArray = appService.getCarArray(carExpenseBean);
					String carDeclLimit = carArray[1];
					reimbursementBean.setDecBasicAmount(carDeclLimit);
				} else {
					reimbursementBean.setDecBasicAmount("0");
				}
				
				int totalCost = Integer.parseInt(carExpenseBean.getCarTripCost()) * Integer.parseInt(carExpenseBean.getCarRate());
				reimbursementBean.setCarTotal(String.valueOf(totalCost));
				
				reimbursementBean.setCarClaimAmount(carExpenseBean.getTotalCost());
			}

			/*String car = carExpenseBean.getCarShiftMode();
			String carRate = "";
			String carDeclLimit = "";
			int carSubtotal = 0;
			int atcTotalCost = 0;
			int carTransCost = 0;
			
			
			carSubtotal = carTransCost * Integer.parseInt(carRate);
			atcTotalCost = carSubtotal + Integer.parseInt(carDeclLimit);*/
			
			reimbursementBean.setTypeOfTransfer(HrTrfType);
			reimbursementBean.setEmpName(customerInfo.getEmpName());
			reimbursementBean.setAccomodationType(trfType);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("tabOpened", tabOpened);
		mv.setViewName("secondLevelHome");
		return mv;
	}
	
	@RequestMapping(value = "/saveCarExpenseForm", method = { RequestMethod.POST })
	public ModelAndView saveCarExpenseForm(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
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
		String tabOpened = "CarExpense";
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
			isSaved = appService.saveCarExpenseForm(reimbursementBean);
			
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
