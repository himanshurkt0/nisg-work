package com.sidbi.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.EmployeeInfoBean;
import com.sidbi.bean.NonPeriodicBookClaimBean;
import com.sidbi.bean.NonPeriodicBriefcaseBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.service.AppService;

@Controller
public class NonPeriodicalClaim {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/nonPeriodicClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView declarationNonPeriodicClaims(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String bookMessage = "";
		String briefCaseMessage = "";
		String medicalMessage = "";
		boolean status = true;
		boolean errorStatus = true;
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		
		String promotionType = "";
		String lvPPGrade = (String) session.getAttribute("lvPPGrade");

		// Previous Opened Tab
		String tabOpened = "nonPeriodicClaim";
		appNameTracker += "->" + "Non Periodic Claim";

		try {

			if (notifyMsg == null) {
				notifyMsg = "";
			}

			// Get All Employee Info
			EmployeeInfoBean employeeInfo = appService.getEmployeeInformation(empCode);
			// Check If Employee is Active or Inactive
			String isActive = appService.checkIfEmployeeActive(empCode);

			if (employeeInfo == null) {
				notifyMsg = "Joining Date is not available. Contact your local HR Officer.";
				errorStatus = false;
			} else if (isActive.equals("N")) {
				notifyMsg = "Your employee id is deactivated. Contact your local HR Officer.";
				errorStatus = false;
			} else {
				session.setAttribute("empGrade", employeeInfo.getGrade());
				reimbursementBean.setEmpGrade(employeeInfo.getGrade());
				reimbursementBean.setEmpCode(empCode);

				// Last Promotion Date
				String promotionDateString = (String) session.getAttribute("promotionDateString");
				Date promotionDate = AppConstants.parseDate(promotionDateString);
				Date currentDate = new Date();
				promotionType = (String) session.getAttribute("promotionType");

				// Old BreifCase Data
				NonPeriodicBriefcaseBean breifCaseBean = appService.getPrevBriefCase(empCode);
				Date BREligibleDate = appService.getBREligibilityDate(empCode, employeeInfo.getGrade());
				System.out.println("briefCaseEligibilityDate --->  " + BREligibleDate);
				String maxBRLimit = appService.getMaxBRLimit(employeeInfo.getGrade());
				if (!maxBRLimit.isEmpty()) {
					reimbursementBean.setMaxBRLimit(maxBRLimit);
				} else {
					briefCaseMessage = "BRIEFCASE REIMBURSEMENT MAX LIMIT IS NOT DEFINED. Contact Dcsupport.";
					errorStatus = false;
				}
				if (BREligibleDate != null) {
					reimbursementBean.setBREligibilityDate(AppConstants.parseDate(BREligibleDate));
				} else {
					BREligibleDate = currentDate;
					reimbursementBean.setBREligibilityDate(AppConstants.parseDate(currentDate));
				}
				if (breifCaseBean.getABRAvailOption() != null && breifCaseBean.getABRAvailOption().equals("Y")) {

					if (currentDate.before(BREligibleDate) || currentDate.equals(BREligibleDate)) {
						// Setting fields for main Reimbursement
						reimbursementBean.setBriefCaseReimb(breifCaseBean.getABRAvailOption());
						reimbursementBean.setBRPurchaseDate(breifCaseBean.getABRPurchaseDate());
						reimbursementBean.setBRReason(breifCaseBean.getABRReason());
						reimbursementBean.setBreifCaseCost(breifCaseBean.getABRCost());

						// Setting fields for last Reimbursement
						reimbursementBean.setLastBriefCaseReimb(breifCaseBean.getABRAvailOption());
						reimbursementBean.setBRPurchaseDate(breifCaseBean.getABRPurchaseDate());
						reimbursementBean.setBRReason(breifCaseBean.getABRReason());
						reimbursementBean.setBreifCaseCost(breifCaseBean.getABRCost());

						briefCaseMessage = "You have claimed for Brief Case";
					}

				} else if (breifCaseBean.getABRAvailOption() != null && breifCaseBean.getABRAvailOption().equals("N")) {
					status = false;
					if (currentDate.before(BREligibleDate)) {
						reimbursementBean.setBriefCaseReimb(breifCaseBean.getABRAvailOption());
						reimbursementBean.setLastBriefCaseReimb(breifCaseBean.getABRAvailOption());
					}
				}

				/*-----------------------------------Book Claim Section----------------------------------*/

				if (promotionType.equals("L") && promotionDate.before(currentDate)) {
					lvPPGrade = appService.getLVPPGradeForBookClaim(employeeInfo.getGrade());
				} else {
					lvPPGrade = reimbursementBean.getEmpGrade();
				}
				String[] bookPaidAmountAndCount = appService.getPPABGPaidAmount(empCode).split("-");
				String ABGPaidAmount = bookPaidAmountAndCount[0];
				String countOfBookRecord = bookPaidAmountAndCount[1];

				String maxBookClaimAmount = appService.getNPMaxBookClaimAmount(lvPPGrade);
				reimbursementBean.setBookLimit(maxBookClaimAmount);
				List<String> gradeList = Arrays.asList(new String[] { "A", "B", "C", "D", "E", "F", "G", "3", "4" });

				if (gradeList.contains(reimbursementBean.getEmpGrade())) {
					String bookClaimDate = appService.getNPClaimDate(empCode, "543");
					NonPeriodicBookClaimBean lastBookClaimBean = appService.getLastNonPeriodBookClaim(empCode,
							bookClaimDate);

					if (lastBookClaimBean.getLastBookCost() == null || lastBookClaimBean.getLastBookCost().isEmpty()) {
						lastBookClaimBean.setLastBookCost("0");
					}
					if(lastBookClaimBean.getLastBookGrantFlag() != null && lastBookClaimBean.getLastBookGrantFlag().equals("Y")){
						lastBookClaimBean.setLastBookCost("0");
					}
					int bookRemLimit = Integer.parseInt(maxBookClaimAmount)
							- Integer.parseInt(ABGPaidAmount) ;
					reimbursementBean.setNpBookRemAmount(String.valueOf(bookRemLimit));

					if (lastBookClaimBean.getLastClaimAvailed() != null) {

						// Setting values in main bean
						reimbursementBean.setIsNonPeriodBookAvailed(lastBookClaimBean.getLastClaimAvailed());
						reimbursementBean.setBookCost(lastBookClaimBean.getLastBookCost());
						reimbursementBean.setBookDetails(lastBookClaimBean.getLastBookRemark());
						reimbursementBean.setBookVerifyStatus(lastBookClaimBean.getLastBookGrantFlag());

						// Setting values in main bean for last Claim
						reimbursementBean.setLastBookClaimDate(lastBookClaimBean.getLastBookClaimDate());
						reimbursementBean.setLastBookCost(lastBookClaimBean.getLastBookCost());
						reimbursementBean.setLastBookGrantFlag(lastBookClaimBean.getLastBookGrantFlag());
						reimbursementBean.setLastBookRemark(lastBookClaimBean.getLastBookRemark());
						reimbursementBean.setLastBookClaimAvailed(lastBookClaimBean.getLastClaimAvailed());

						bookMessage = "You have already claimed for Book ";
					} else {
						reimbursementBean.setBookVerifyStatus("N");
						status = false;
					}
				}
				boolean compareFinYear = appService.compareFinancialYear(promotionDateString);
				if (reimbursementBean.getLastBookGrantFlag() != null) {
					if (Integer.parseInt(countOfBookRecord) <= 1 && Integer.parseInt(maxBookClaimAmount) > 0
							&& reimbursementBean.getLastBookGrantFlag().equals("Y")) {
						reimbursementBean.setIsNonPeriodBookAvailed("");
						reimbursementBean.setLastBookGrantFlag("N");
						reimbursementBean.setBookCost("0");
						reimbursementBean.setBookDetails(null);
						reimbursementBean.setLastBookClaimDate(null);
						reimbursementBean.setLastBookClaimAvailed("");
						bookMessage = "You Can Apply For Book Claim.!";
					}
				}

				/*-----------------------------------Medical Opt Section----------------------------------*/

				if (gradeList.contains(employeeInfo.getGrade())) {

					// last claim date
					String lvMedicalClaimDate = appService.getNPClaimDate(empCode, "524");

					// total opd requested amount - total paid amount + pending
					// request amount
					String npMedicalOpdRequestedAmount = appService.getNpMedicalOptClaimAmount(empCode); // hh
					if (npMedicalOpdRequestedAmount == null || npMedicalOpdRequestedAmount.isEmpty()) {
						npMedicalOpdRequestedAmount = "0";
					}

					// change it according to above actual limit
					reimbursementBean.setNpMedicalOpdRequestAmount(npMedicalOpdRequestedAmount);

					// Get Previous Medical Claim in NP
					appService.getPreviousNpMedicalClaim(reimbursementBean);

					if (reimbursementBean.getLastNpMedicalPaidFlag() != null) {
						reimbursementBean.setMedicalVerifyStatus(reimbursementBean.getLastNpMedicalPaidFlag());
					}

					// setting last availed flag
					reimbursementBean.setIsNpMedicalAvailed(reimbursementBean.getLastNpMedicalAvailed());

					// setting 7last claim date as sysdate in case of first
					// entry --- allowing first entry in FY..
					if (reimbursementBean.getLastNpMedicalClaimDate() == null) {
						reimbursementBean.setLastNpMedicalClaimDate(AppConstants.parseDate(currentDate));
					}

					String medicalLvPPGrade = appService.getMedicalLvPPGrade(empCode);
					if (medicalLvPPGrade.isEmpty()) {
						medicalLvPPGrade = employeeInfo.getGrade();
					}
					String maxNPMedicalLimitAndDate = appService.getMaxNpMedicalLimit(medicalLvPPGrade);
					String[] maxNPMedicalLimitAndDateArray = maxNPMedicalLimitAndDate.split("-");
					String maxNPMedicalLimit = maxNPMedicalLimitAndDateArray[0];
					reimbursementBean.setNpMedicalOptLimit(maxNPMedicalLimit);
					Date npMedicalLimitEffDate = AppConstants.parseDate(maxNPMedicalLimitAndDateArray[1]);

					// total amount paid to user along with number of times
					String npPaidAmountAndCount = appService.getNpMedicalPaidAmountAndCount(empCode,
							reimbursementBean.getLastNpMedicalClaimDate());

					String[] paidAmountArray = npPaidAmountAndCount.split("-");
					int paidRecordCount = Integer.parseInt(paidAmountArray[1]);
					int totatAmountpaid = Integer.parseInt(paidAmountArray[0]);// +
																				// Integer.parseInt(npMedicalOpdRequestedAmount);
					// if paid_status <> 'Y'
					if (reimbursementBean.getLastNpMedicalAmountClaimed() == null) {
						reimbursementBean.setLastNpMedicalAmountClaimed("0");
					}
					if (reimbursementBean.getLastNpMedicalPaidFlag() == null) {
						reimbursementBean.setLastNpMedicalPaidFlag("N");
					}
					int unpaidAmount = reimbursementBean.getLastNpMedicalPaidFlag().equals("Y") ? 0
							: Integer.parseInt(reimbursementBean.getLastNpMedicalAmountClaimed());
					int totalAmountClaimed = totatAmountpaid + unpaidAmount;
					reimbursementBean.setNpMedicalAmountClaimed(String.valueOf(totalAmountClaimed));

					/*
					 * if(reimbursementBean.getLastNpMedicalPaidFlag() != null
					 * &&
					 * reimbursementBean.getLastNpMedicalPaidFlag().equals("N"))
					 * { totalAmountClaimed = npMedicalOpdRequestedAmount ;
					 * //totatAmountpaid+
					 * Integer.parseInt(npMedicalOpdRequestedAmount);
					 * reimbursementBean.setNpMedicalAmountClaimed(String.
					 * valueOf(totalAmountClaimed)); } else { totalAmountClaimed
					 * = Integer.parseInt(npMedicalOpdRequestedAmount);
					 * reimbursementBean.setNpMedicalAmountClaimed(
					 * npMedicalOpdRequestedAmount); }
					 */

					int npMedRemainingLimit = Integer.parseInt(maxNPMedicalLimit) - totalAmountClaimed;
					// in case of negative amount - negative amount is because
					// your pp has been revoked and you have already claimed
					// facility in hinger grade and now you in original grade(
					// Without PP)
					reimbursementBean.setNpMedicalRemAmount(String.valueOf(npMedRemainingLimit));

					if (npMedRemainingLimit < 0) {
						medicalMessage = "Negative amount is because your pp has been revoked and you have already claimed facility in hinger grade and now you in original grade( Without PP)";
					} else if (npMedRemainingLimit == 0) {
						medicalMessage = "You have already claimed all amount for Medical";
					} else {
						status = false;
					}

					// if npMedRemainingLimit = 0
					// diabsle submit

					String npPaidUnpaidNpMedicalClaimAmount = appService.getNpPaidUnpaidMedClaimAmount(empCode,
							reimbursementBean.getLastNpMedicalClaimDate());
					int requestCount = Integer.parseInt(npPaidUnpaidNpMedicalClaimAmount.split("-")[1]);

					System.out.println(requestCount);

					if (reimbursementBean.getLastNpMedicalPaidFlag() == null) {
						reimbursementBean.setLastNpMedicalPaidFlag("");
					}

					if (npMedRemainingLimit > 0 && paidRecordCount == 0 && requestCount == 0
							&& reimbursementBean.getLastNpMedicalPaidFlag().equals("N")) {
						reimbursementBean.setLastNpMedicalPaidFlag("");
						reimbursementBean.setLastNpMedicalClaimDate(null);
						reimbursementBean.setIsNpMedicalAvailed("");
						reimbursementBean.setNpMedicalAmountClaimed("0");
					} else if (npMedRemainingLimit > 0 && paidRecordCount < AppConstants.NP_OPD_MAX_CNT
							&& reimbursementBean.getLastNpMedicalPaidFlag().equals("Y")) {
						reimbursementBean.setLastNpMedicalPaidFlag("N");
						reimbursementBean.setLastNpMedicalClaimDate(null);
						reimbursementBean.setIsNpMedicalAvailed("");
						reimbursementBean.setNpMedicalAmountClaimed("0");
					} else if(reimbursementBean.getLastNpMedicalAvailed() != null && !reimbursementBean.getLastNpMedicalAvailed().equals("Y")){
						medicalMessage = "You can not claim Medical Claim as you have already availed the facility.";
					} else {
						medicalMessage = "You Can Apply For Medical Claim.!";
					}
				}

				/*--------------------------------------- End of Medical OPT Claim --------------------------------------*/

				session.setAttribute("notifyMsg", "");
				mv.addObject("appNameTracker", appNameTracker);

				// To disable submit button if All section of Non Period is
				// Already Claimed

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(notifyMsg != null && !notifyMsg.isEmpty()){
			if(notifyMsg.contains("Brief")){
				briefCaseMessage = "";
			}
			if(notifyMsg.contains("Medical")){
				medicalMessage = "";
			}
			if(notifyMsg.contains("Book")){
				bookMessage = "";
			}
			
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("medicalMessage", medicalMessage);
		mv.addObject("bookClaimMessage", bookMessage);
		mv.addObject("briefCaseMessage", briefCaseMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;

	}

	@RequestMapping(value = "/briefCaseSubmit", method = { RequestMethod.POST })
	public ModelAndView briefCaseSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		boolean isBriefCaseSaved = false;
		String briefCaseMessage = "";
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "nonPeriodicClaim";
		session.setAttribute("tabOpened", tabOpened);
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		try {

			if (claimAllowed) {

				if (reimbursementBean.getBRTermsAndCondition()) {
					isBriefCaseSaved = appService.saveNpBriefCaseClaim(reimbursementBean);
					if (isBriefCaseSaved) {
						briefCaseMessage = "Brief Case reimbursement details has been submitted. !!";
					} else {
						briefCaseMessage = "Unable To Save Brief Case reimbursement details !!";
					}
				}

				notifyMsg = briefCaseMessage;
				session.setAttribute("notifyMsg", notifyMsg);
				return new ModelAndView("redirect:/DeclarationClaimPeriodicalNonperiodical");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("briefCaseMessage", briefCaseMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@PostMapping(value = "/bookClaimSubmit")
	public ModelAndView bookClaimSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		boolean isBookGrantSaved = false;
		String bookMessage = "";
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "nonPeriodicClaim";
		session.setAttribute("tabOpened", tabOpened);
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		try {

			if (claimAllowed) {

				if (reimbursementBean.getBookTermsAndCondition()) {
					isBookGrantSaved = appService.saveNpBookGrantClaim(reimbursementBean);
					if (isBookGrantSaved) {
						bookMessage = "Book reimbursement details has been submitted. !!!!";
					} else {
						bookMessage = "Unable To save book reimbursement details";
					}
				}

				notifyMsg = bookMessage;
				session.setAttribute("notifyMsg", notifyMsg);
				return new ModelAndView("redirect:/DeclarationClaimPeriodicalNonperiodical");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("briefCaseMessage", bookMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@RequestMapping(value = "/nonPeriodClaimSubmit", method = { RequestMethod.POST })
	public ModelAndView nonPeriodClaimSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		boolean isMedicalClaimSaved = false;
		String medicalMessage = "";
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "nonPeriodicClaim";
		session.setAttribute("tabOpened", tabOpened);
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);

		try {

			if (claimAllowed) {

				if (reimbursementBean.getMedicalTermsAndCondition()) {
					isMedicalClaimSaved = appService.saveNpMedicalOptClaim(reimbursementBean);
					if (isMedicalClaimSaved) {
						medicalMessage = "Medical OPD reimbursement details has been submitted. !!!!!!";
					} else {
						medicalMessage = "Unable to save Medical OPD reimbursement details !!";
					}
				}

				notifyMsg = medicalMessage;
				session.setAttribute("notifyMsg", notifyMsg);
				return new ModelAndView("redirect:/DeclarationClaimPeriodicalNonperiodical");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("medicalMessage", medicalMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}
}
