package com.sidbi.controller;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.DeclarationReimbTelephoneBean;
import com.sidbi.bean.EmployeeInfoBean;
import com.sidbi.bean.NonPeriodicBookClaimBean;
import com.sidbi.bean.NonPeriodicBriefcaseBean;
import com.sidbi.bean.ReimbConsentDetailBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.constants.CustomMultipartFile;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class DeclarationClaimPeriodicalNonperiodicalController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/DeclarationClaimPeriodicalNonperiodical", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView declarationClaimPeriodicalNonperiodical(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("jspPage");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
	//	String jspPage = (String) session.getAttribute("jspPage");

		childModule = childModule.replace(".", "/").split("/")[0];
		String promotionType = "";
		String lvPPGrade = "";

		// Previous Opened Tab
		String tabOpened = (String) session.getAttribute("tabOpened");
		if (tabOpened != null){
			reimbursementBean.setTabOpened(tabOpened);
		} else {
			tabOpened = "regularClaim";
			reimbursementBean.setTabOpened("regularClaim");
		}
			

		// CustomerBeanDomain customerInfo = (CustomerBeanDomain)

		try {

			if (notifyMsg == null) {
				notifyMsg = "";
			}

			// Check If Employee is Active or Inactive
			String isActive = appService.checkIfEmployeeActive(empCode);

			// Get All Employee Info
			EmployeeInfoBean employeeInfo = appService.getEmployeeInformation(empCode);
			if (employeeInfo == null) {
				notifyMsg = "Joining Date is not available. Contact your local HR Officer.";
			} else if (isActive.equals("N")) {
				notifyMsg = "Your employee id is deactivated. Contact your local HR Officer.";
			} else {

				// Last Promotion Date
				String promotionDateString = appService.getPromotionDateInDeclaration(empCode);
				Date promotionDate = AppConstants.parseDate(promotionDateString);
				Date currentDate = new Date();
				Date currentNextDate = new Date(new Date().getTime() + 86400000);
				
				String lvEmpPPGrade = appService.getLvEmpPPGrade(reimbursementBean);
				if(lvEmpPPGrade == null){
					lvEmpPPGrade = "NA";
				}
				session.setAttribute("lvEmpPPGrade", lvEmpPPGrade);

				if (promotionDate.before(currentNextDate)) {
					promotionType = appService.getPromotionType(empCode, promotionDateString);
				}
				
				if (promotionType.equals("L") && promotionDate.before(currentDate)) {
					lvPPGrade = appService.getLVPPGradeForBookClaim(employeeInfo.getGrade());
				}

				List<ReimbConsentDetailBean> reimbConsentBeanList = appService.getReimbConsentDetailBean(empCode,
						AppConstants.parseDate(currentDate));
				
				int postTypeCount = appService.getPostType(reimbursementBean);
				String mobEligEmpExceptionalCode = appService.getMobEmpExceptionalCode(empCode);
				List<String> laptopAllowedGradeLst = Arrays.asList(AppConstants.laptopAllowedGradeList);
				List<String> laptopAllowedDesigLst = Arrays.asList(AppConstants.laptopAllowedDesigList);
				String empGrade = customerInfo.getEmpGrade();
				String empDesg = customerInfo.getEmpDesg();
				
				if(laptopAllowedGradeLst.contains(empGrade) || laptopAllowedDesigLst.contains(empDesg)
						|| postTypeCount > 0 || empCode.equals(mobEligEmpExceptionalCode)){
					reimbursementBean.setDisableLaptopTab("enabled");
				} else {
					reimbursementBean.setDisableLaptopTab("disabled");
				}
				
				if(postTypeCount > 0){
					reimbursementBean.setDisableLaptopElements("disabled");
				}
				
				session.setAttribute("empGrade", employeeInfo.getGrade());
				reimbursementBean.setEmpCode(empCode);
				AppConstants.setReimTabEligibility(mv, reimbursementBean, request,appService);
				AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request,appService);
				String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

				if (isUpdatingPage == null || isUpdatingPage.equalsIgnoreCase("true"))
					isUpdatingPage = "false";

				session.setAttribute("reimbConsentBeanList", reimbConsentBeanList);
				session.setAttribute("lvPPGrade", lvPPGrade);
				session.setAttribute("promotionDateString", promotionDateString);
				session.setAttribute("promotionType", promotionType);
				session.setAttribute("isUpdatingPage", "");
				// session.setAttribute("notifyMsg", "");
				appNameTracker = appNameTracker + "->" + childModule;

				mv.addObject("appNameTracker", appNameTracker);
				mv.addObject("notifyMsg", notifyMsg);
				if (tabOpened != null) {
					session.setAttribute("notifyMsg", notifyMsg);
					return new ModelAndView("redirect:/" + tabOpened + "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("notifyMsg", "");
		mv.setViewName("secondLevelHome");
		return mv;

	}

	
	

	

	
	// Setting enable disable for tab elements
	/*public void setReimTabElementEligibility(ModelAndView mv, ReimbursementBean reimbursementBean,
			HttpServletRequest request) {
		// HttpSession session = request.getSession(true);
		List<String> afsCodeList = appService.getAfsCodeList(reimbursementBean);
		reimbursementBean.setAfsCodeList(afsCodeList);
	}*/

	// Setting Common Grade for Tab
	/*public void setReimTabEligibility(ModelAndView mv, ReimbursementBean reimbursementBean,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		appService.getEmployeeGradeDetails(reimbursementBean);
		String empGrade = reimbursementBean.getEmpGrade();
		String[] empGrdList = { "A", "B", "C", "D", "E", "F" };
		List<String> empGradeArray = Arrays.asList(empGrdList);
		if (empGradeArray.contains(empGrade)) {
			reimbursementBean.setCommonGrade("Z");
		} else {
			if (empGrade.equals("3") || empGrade.equals("4")) {
				reimbursementBean.setCommonGrade("9");
			}
		}

		reimbursementBean.setEmpGrade(empGrade);

		// Enabling Disabling tab based on Eligibility
		if (empGrade.equals("I")) {

			mv.addObject("RegularClaim", "");
			mv.addObject("VehicleInsurance", "");
			mv.addObject("TldcClaim", "");
			mv.addObject("NonPeriodicClaim", "tab");
			// mv.addObject("NonPeriodicClaim","");

		} else if (empGrade.equals("G")) {

			mv.addObject("TldcClaim", "");
			mv.addObject("VehicleInsurance", "tab");

		} else if (empGrade.equals("F") || empGrade.equals("G")) {

			mv.addObject("TldcClaim", "");
			mv.addObject("pageTabClaim", "tab");
			mv.addObject("RegularClaim", "tab");
			mv.addObject("rvmeVehicleClaim", "tab");
			mv.addObject("NonPeriodicClaim", "tab");
			mv.addObject("VehicleInsurance", "tab");

		} else {

			mv.addObject("RegularClaim", "tab");
			mv.addObject("VehicleInsurance", "tab");
			mv.addObject("TldcClaim", "tab");
			mv.addObject("NonPeriodicClaim", "tab");
			mv.addObject("TldcClaim", "tab");
			mv.addObject("MedicalClaim", "tab");
			mv.addObject("ClaimAuto", "tab");
			mv.addObject("tabIpadClaim", "tab");
			mv.addObject("creditCardReimb", "tab");
			mv.addObject("LapMobReimb", "tab");
			mv.addObject("rvmeVehicleClaim", "tab");

		}
	}*/

}
