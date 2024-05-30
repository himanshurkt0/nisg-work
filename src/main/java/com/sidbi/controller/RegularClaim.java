package com.sidbi.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.DeclarationReimbTelephoneBean;
import com.sidbi.bean.EmployeeInfoBean;
import com.sidbi.bean.ReimbConsentDetailBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class RegularClaim {
	
	@Autowired
	AppService appService;
	
	@RequestMapping(value = "/regularClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView regularClaimForm(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String lvEmpPPGrade = (String) session.getAttribute("lvEmpPPGrade");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");

		String promotionType = "";

		// Previous Opened Tab
		String tabOpened = "regularClaim";
		session.setAttribute("tabOpened", tabOpened);
		
		appNameTracker += "->" + "Regular Claim";

		// CustomerBeanDomain customerInfo = (CustomerBeanDomain)
		// session.getAttribute("customerBean");

		String isNewsPaperAvailed = "";
		String isHouseHoldHelpAvailed = "";
		String isOfficialEntExpenceAvailed = "";
		String isWashingAllowanceAvailed = "";
		String isResidenceOfficeAllowanceAvailed = "";
		String isCarCleaningAvailed = "";
		String isTelephoneAvailed = "";
		String isDataChargesAvailed = "";

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

				/*-----------------------------------Regular Claim----------------------------------------------*/

				// Reimbursement Consent Details

				@SuppressWarnings("unchecked")
				Date currentDate = new Date();
				List<ReimbConsentDetailBean> reimbConsentBeanList = appService.getReimbConsentDetailBean(empCode,
						AppConstants.parseDate(currentDate));

				// to disable individual element of regular claim tab
				reimbursementBean.setEmpCode(empCode);
				AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
				AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request, appService);
				
				String inchargeDetail = appService.getInchargeDetails(reimbursementBean);
				int lvInchargeCount = Integer.parseInt(inchargeDetail.split("-")[0]);
				
				List<String> offEntDesgList = Arrays.asList(AppConstants.offEntAllowedDesigList);
				List<String> offEntGradeList = Arrays.asList(AppConstants.offEntAllowedGradeList);
				String empGrade = reimbursementBean.getEmpGrade();
				String empDesig = customerInfo.getEmpDesg();
				System.out.println(empDesig);

				List<String> afsCodeList = reimbursementBean.getAfsCodeList();
				String rroFlag = appService.getRroFlag(reimbursementBean);
				if(rroFlag == null){
					rroFlag = "NA";
				}

				if (!afsCodeList.contains("540")) {
					reimbursementBean.setLastWashingAllowance("disabled");
				}
				if (!afsCodeList.contains("558") ) {
					reimbursementBean.setLastDataCharges("disabled");
				}
				if (!afsCodeList.contains("544")) {
					reimbursementBean.setLastCarCleaning("disabled");
				}
				if (!afsCodeList.contains("537") || (!rroFlag.equals("K") || !empGrade.equals("G"))) {
					reimbursementBean.setLastResidenceAllowance("disabled");
				}
				if (!afsCodeList.contains("520")) {
					reimbursementBean.setLastOffEntExpense("disabled");
				}
				if (!afsCodeList.contains("514")) {
					reimbursementBean.setLastTelephone("disabled");
				}
				//Added on 12-Apr-2013 to enable LHO BMO to apply for the Telephone acility
				if(empGrade.equals("4") && empDesig.equals("29")){
					reimbursementBean.setLastTelephone("enabled");
				}
				if (!afsCodeList.contains("525")) {
					reimbursementBean.setLastHouseHoldHelp("disabled");
				}
				if (!afsCodeList.contains("521")) {
					reimbursementBean.setLastNewspaper("disabled");
				}

				if(offEntGradeList.contains(empGrade) && offEntDesgList.contains(empDesig))
				{
					reimbursementBean.setLastOffEntExpense("enabled");
				}
				
				if(lvInchargeCount > 0){
					reimbursementBean.setLastOffEntExpense("enabled");
				}
				
				if(empGrade.equals("B") && lvEmpPPGrade.equals("C")){
					reimbursementBean.setLastOffEntExpense("enabled");
				}
				
				System.out.println("Emp Grade -> " + empGrade + "  PP Grade -> " + lvEmpPPGrade);
				
				for (ReimbConsentDetailBean reimbConsentBean : reimbConsentBeanList) {
					if (reimbConsentBean.getAfmCode().equals("521")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isNewsPaperAvailed = "Y";
						} else {
							isNewsPaperAvailed = "N";
						}

						reimbursementBean.setLastNewspaper(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("525")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isHouseHoldHelpAvailed = "Y";
						} else {
							isHouseHoldHelpAvailed = "N";
						}
						reimbursementBean.setLastHouseHoldHelp(reimbConsentBean.getAvailOption());
					}

					if (reimbConsentBean.getAfmCode().equals("514")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isTelephoneAvailed = "Y";
						} else {
							isTelephoneAvailed = "N";
						}
						reimbursementBean.setLastTelephone(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("520")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isOfficialEntExpenceAvailed = "Y";
						} else {
							isOfficialEntExpenceAvailed = "N";
						}
						reimbursementBean.setLastOffEntExpense(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("540")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isWashingAllowanceAvailed = "Y";
						} else {
							isWashingAllowanceAvailed = "N";
						}
						reimbursementBean.setLastWashingAllowance(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("537")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isResidenceOfficeAllowanceAvailed = "Y";
						} else {
							isResidenceOfficeAllowanceAvailed = "N";
						}
						reimbursementBean.setLastResidenceAllowance(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("544")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isCarCleaningAvailed = "Y";
						} else {
							isCarCleaningAvailed = "N";
						}
						reimbursementBean.setLastCarCleaning(reimbConsentBean.getAvailOption());
					}
					if (reimbConsentBean.getAfmCode().equals("558")) {
						if (reimbConsentBean.getAvailOption().equals("Y")) {
							isDataChargesAvailed = "Y";
						} else {
							isDataChargesAvailed = "N";
						}
						reimbursementBean.setLastDataCharges(reimbConsentBean.getAvailOption());
					}

				}
				// Setting All availed section in Regular Claim
				reimbursementBean.setIsTelephoneAvailed(isTelephoneAvailed);
				reimbursementBean.setIsNewsPaperAvailed(isNewsPaperAvailed);
				reimbursementBean.setIsCarCleaningAvailed(isCarCleaningAvailed);
				reimbursementBean.setIsWashingAllowanceAvailed(isWashingAllowanceAvailed);
				reimbursementBean.setIsHouseHoldHelpAvailed(isHouseHoldHelpAvailed);
				reimbursementBean.setIsOfficialEntExpenceAvailed(isOfficialEntExpenceAvailed);
				reimbursementBean.setIsResidenceOfficeAllowanceAvailed(isResidenceOfficeAllowanceAvailed);
				reimbursementBean.setIsDataChargesAvailed(isDataChargesAvailed);

				// Telephone Section
				if (reimbursementBean.getLastTelephone() != null) {

					if (reimbursementBean.getLastTelephone().equals("Y")) {
						DeclarationReimbTelephoneBean telephoneBean = appService.getReimbTelephoneDetails(empCode);
						reimbursementBean.setTelephoneNo(telephoneBean.getTelephoneNo());
						reimbursementBean.setServiceProvider(telephoneBean.getServiceProvider());
						reimbursementBean.setTelephoneInstallationDate(telephoneBean.getInstallationDate());
					}

				}

				session.setAttribute("empGrade", employeeInfo.getGrade());
				String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

				if (isUpdatingPage == null || isUpdatingPage.equalsIgnoreCase("true"))
					isUpdatingPage = "false";

				session.setAttribute("isUpdatingPage", "");
				session.setAttribute("notifyMsg", "");
				mv.addObject("appNameTracker", appNameTracker);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("appNameTracker", appNameTracker);
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@RequestMapping(value = "/declarationRegularClaimSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView declarationRegularClaimSubmit(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);

		// To stick to an opened tab for regular claim
		reimbursementBean.setTabOpened("regularClaim");
		session.setAttribute("tabOpened", "regularClaim");

		String notifyMsg = "";

		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		reimbursementBean.setEmpCode(empCode);
		reimbursementBean.setBranchCode(branchCode);

		boolean isNewsPaperSaved = false;
		boolean isHouseHoldHelpSaved = false;
		boolean isOfficialEntExpSaved = false;
		boolean isWashingAllowanceSaved = false;
		boolean isResOfficeAllowanceSaved = false;
		boolean isCarCleaningSaved = false;
		boolean isDataChargesSaved = false;
		
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request, appService);

		List<String> afsCodeList = reimbursementBean.getAfsCodeList();

		// Update Later
		reimbursementBean.setIsIncharge("I");

		if(reimbursementBean.getRegularClaimTermsAndCondition()){
			if (reimbursementBean.getIsNewsPaperAvailed() != null) {
				isNewsPaperSaved = appService.saveNewsPaperClaimSubmit(reimbursementBean);
			}
			if (reimbursementBean.getIsHouseHoldHelpAvailed() != null) {
				isHouseHoldHelpSaved = appService.saveHouseHoldHelpClaim(reimbursementBean);
			}
			if (reimbursementBean.getIsOfficialEntExpenceAvailed() != null) {
				isOfficialEntExpSaved = appService.saveOfficialEntExpClaim(reimbursementBean);
			}
			if (reimbursementBean.getIsWashingAllowanceAvailed() != null) {
				isWashingAllowanceSaved = appService.saveWashingAllowanceClaim(reimbursementBean);
			}
			if (reimbursementBean.getIsResidenceOfficeAllowanceAvailed() != null) {
				isResOfficeAllowanceSaved = appService.saveResidenceOfficeAllowance(reimbursementBean);
			}
			if (reimbursementBean.getIsCarCleaningAvailed() != null) {
				isCarCleaningSaved = appService.saveCarCleaningClaim(reimbursementBean);
			}
			if (reimbursementBean.getIsDataChargesAvailed() != null){
				isDataChargesSaved = appService.saveDataChargesClaim(reimbursementBean);
			}
		} else {
			notifyMsg = "Please accept Terms & Conditions !!";
		}

		if (isNewsPaperSaved || isHouseHoldHelpSaved || isOfficialEntExpSaved || isWashingAllowanceSaved
				|| isResOfficeAllowanceSaved || isCarCleaningSaved || isDataChargesSaved) {
			String finStartEnd = appService.getFinStartEnd();
			System.out.println("Financial Start End ---->" + finStartEnd);
			notifyMsg = "Successfully submitted claims.! ";

		} else {
			notifyMsg = "   -----    No claim to submit. Please Check.!! ";
		}
		session.setAttribute("notifyMsg", notifyMsg);
		return new ModelAndView("redirect:/DeclarationClaimPeriodicalNonperiodical");
		// Financial Start End
	}
	
	
	@RequestMapping(value = "/declarationRegularClaimTelephoneSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView declarationRegularClaimTelephoneSubmit(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);

		// To stick to an opened tab for regular claim
		reimbursementBean.setTabOpened("regularClaim");
		session.setAttribute("tabOpened", "regularClaim");

		String notifyMsg = "";

		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		reimbursementBean.setEmpCode(empCode);
		reimbursementBean.setBranchCode(branchCode);
		boolean isTelephoneClaimSaved = false;

		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request, appService);

		List<String> afsCodeList = reimbursementBean.getAfsCodeList();

		// Update Later
		reimbursementBean.setIsIncharge("Y");
		
		if(reimbursementBean.getRegularClaimTermsAndCondition1()){
			if (reimbursementBean.getIsTelephoneAvailed() != null) {
				isTelephoneClaimSaved = appService.saveTelephoneClaim(reimbursementBean);
			}
			
		} else {
			notifyMsg = "Please accept Terms & Conditions !!";
		}
		

		if ( isTelephoneClaimSaved) {
			String finStartEnd = appService.getFinStartEnd();
			System.out.println("Financial Start End ---->" + finStartEnd);
			notifyMsg = "Telephone reimbursement details has been submitted. !!!!!!! ";

		} else {
			notifyMsg += "Telephone reimbursement details has not been submitted.Please contact CAP DESK";
		}
		session.setAttribute("notifyMsg", notifyMsg);
		return new ModelAndView("redirect:/DeclarationClaimPeriodicalNonperiodical");
		// Financial Start End
	}
	
	
}
