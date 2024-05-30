package com.sidbi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import com.sidbi.bean.LaptopMobileClaimBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.LaptopClaimDomain;
import com.sidbi.domain.MobChargesDomain;
import com.sidbi.domain.MobileClaimDomain;
import com.sidbi.domain.MobileClaimReimDomain;
import com.sidbi.service.AppService;

@Controller
public class NpMobileLaptopClaim {

	@Autowired
	AppService appService;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/npLaptopMobileClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npLaptopMobileClaim(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		String lastMobileFlag = "";
		String mobileClaimMessage = "";
		String mobileChargesMessage = "";
		String laptopMessage = "";

		String tabOpened = "npLaptopMobileClaim";
		String mobRefundFlag = "N";
		boolean isEligible = true;
		appNameTracker += "->" + "Laptop/Mobile Claim";
		if (notifyMsg != null) {
			mv.addObject("notifyMsg", notifyMsg);
			session.setAttribute("notifyMsg", "");
		}
		String empGrade = reimbursementBean.getEmpGrade();
		try {
			Date currentDate = new Date();
			currentDate = new Date(currentDate.getTime());
			Date apmEffectiveDate = new Date(AppConstants.parseDate(appService.getApmEffectiveDate()).getTime());
			String mobileEmpGrade = "";
			LaptopMobileClaimBean previousBean = new LaptopMobileClaimBean();
			reimbursementBean.setPaidDate(AppConstants.parseDate(currentDate));
			int countSBLPRDMonth = appService.getSBLPDMonth(reimbursementBean);

			int mobileRefundCount = 0;

			String[] gradeABCList = { "A", "B", "C" };

			if (currentDate.after(apmEffectiveDate)) {
				mobileClaimMessage = "March peocessing for the last FY is still pending!! Regular claims Facility will be enabled after March processing ";
				isEligible = false;
			}

			else if (isEligible) {
				mobileEmpGrade = appService.getMobileEmpGrade(reimbursementBean);
				/******
				 * Include to enable BO incharge to claim mob after refund or
				 * promotion to grade 'D'
				 ************/
				if (mobileEmpGrade == null || mobileEmpGrade == "") {
					mobileEmpGrade = "N";
				}
				if (mobileEmpGrade.equals("C")) {
					mobileRefundCount = appService.getMobileRefundCount(reimbursementBean);
				}
				if (mobileRefundCount == 1) {
					mobRefundFlag = "Y";
				} else if (mobileRefundCount == 0) {
					String paidDate = appService.getMobilePaidDate(reimbursementBean);
					reimbursementBean.setPaidDate(paidDate);
					if (paidDate != null) {
						countSBLPRDMonth = appService.getSBLPDMonth(reimbursementBean);
					}
				}

				/***********
				 * cHANGES DONE ON 29-SEP-21 TO ENABLE FACILITY FOR PAYMENT IN
				 * ALTERNATE FY
				 **************/
				reimbursementBean.setCountSBLPRDMonth(String.valueOf(countSBLPRDMonth));
				Date eligibleYearStartEndDate = AppConstants
						.parseDate(appService.getMobileFinStartEnd(reimbursementBean));
				if(eligibleYearStartEndDate == null){
					eligibleYearStartEndDate = AppConstants.parseDate(appService.getFinStartEnd());
				}
				/***********
				 * cHANGES DONE ON 29-SEP-21 TO ENABLE FACILITY FOR PAYMENT IN
				 * ALTERNATE FY
				 **************/

				// Getting previous mobile Claim
				appService.getPrevLaptopMobileClaim(reimbursementBean, previousBean);
				if (previousBean.getMobileClaimAvailed() != null && !previousBean.getMobileVerifyStatus().equals("R")) {
					// Setting previous data to main bean for display
					reimbursementBean.setIsMobilePurchased(previousBean.getMobileClaimAvailed());
					reimbursementBean.setMobileClaimDate(previousBean.getMobileClaimDate());
					reimbursementBean.setMobileDetail(previousBean.getMobileClaimDetail());
					reimbursementBean.setMobileClaimAmount(previousBean.getMobileClaimAmount());
					reimbursementBean.setMobileClaimRemark(previousBean.getMobileRemark());
					reimbursementBean.setMobileAgreement(true);
				}

				if (previousBean.getMobileClaimAvailed() != null) {
					Date lastClaimPaidDate = AppConstants
							.parseDate(appService.getLastMobileClaimPaidDate(reimbursementBean));
					Date lastClaimDate = AppConstants
							.parseDate(AppConstants.formatDate(previousBean.getMobileClaimDate()));
					String verifyStatus = previousBean.getMobileVerifyStatus();
					String paidStatus = previousBean.getMobilePaidStatus();
					if (lastClaimPaidDate == null) {
						lastClaimPaidDate = AppConstants.parseDate("01/01/0001");
					}

					if (eligibleYearStartEndDate.before(currentDate)) {
						lastMobileFlag = "N";

						if (lastClaimDate != null && lastClaimPaidDate != null
								&& !(lastClaimPaidDate.equals(lastClaimDate)) && !(verifyStatus.equals("R"))) {
							lastMobileFlag = "Y";
						}
					} else if (eligibleYearStartEndDate.after(currentDate) && mobileRefundCount == 1) {
						lastMobileFlag = "N";

						if (lastClaimDate != null && lastClaimPaidDate != null
								&& !(lastClaimPaidDate.equals(lastClaimDate)) && !(verifyStatus.equals("R"))) {
							lastMobileFlag = "Y";
						}
					}

					if (verifyStatus.equals("N")) {
						mobileClaimMessage = "Last Mobile Claim made is yet to be verified by CAP Nodal Officer.";
					} else if (verifyStatus.equals("I")) {
						mobileClaimMessage = "Document not uploaded.";
					} else if (verifyStatus.equals("Y") && paidStatus.equals("N")) {
						mobileClaimMessage = "Last Mobile Claim made is yet to be processed for payment by CAP Cell.";
					} else if (verifyStatus.equals("R") && paidStatus.equals("N")) {
						mobileClaimMessage = "Mobile Claim Rejected :";
					} else if (verifyStatus.equals("Y") && paidStatus.equals("Y")
							&& previousBean.getMobilePaidDate() != null) {
						/*
						 * will add later for last part of mobile claim Section
						 */
					}

				}
				if (previousBean == null && (mobileClaimMessage == null || mobileClaimMessage.equals(""))) {
					mobileClaimMessage = "You Can Apply For Mobile Claim !";
				}

				/***********
				 * CHANGES DONE FOR MOBILE CHARGES SECTION
				 **************/
				appService.getPreviousMobileChargesDetail(reimbursementBean, previousBean);

				if (previousBean.getMobileChargesAvailed() != null) {
					// Setting values to main bean for display
					reimbursementBean.setMobileCharges(previousBean.getMobileChargesAvailed());
					reimbursementBean.setMobileNumber(previousBean.getMobileNumber());
					reimbursementBean.setMobileServiceProvider(previousBean.getMobileServiceProvider());
					reimbursementBean.setMobileActivationDate(previousBean.getMobileChargeInstallationDate());

					if (previousBean.getMobileChargesAvailed().equals("Y")
							|| reimbursementBean.getEmpGrade().equals("G")) {
						reimbursementBean.setMobileChargesAgreement(true);
						//
					}
				} else {
					mobileChargesMessage = "You can apply for claiming Mobile Charges Reimbursement.";
				}
				
				if (previousBean.getMobileChargesAvailed() == null && (mobileClaimMessage == null || mobileClaimMessage.equals(""))) {
					mobileClaimMessage = "You Can Apply For Mobile Claim !";
				}

				/***********
				 * CHANGES DONE FOR LAPTOP CLAIM SECTION
				 **************/
				String laptopBillDate = appService.getLaptopBillOrClaimDate(reimbursementBean);
				String laptopValidMonths = appService.getLaptopValidMonth(reimbursementBean);
				Date lastLaptopClaimDate = null;
				Date lastLaptopBillDate = null;
				String laptopVerifyStatus = null;
				Date nextLaptopEligibleDate = null;
				Date laptopPaidDate = null;
				String lastLaptopFlag = "";
				String currentGrade = "";
				String laptopPaidStatus = "";
				Date promotionEffectiveDate = null;
				String lapSBLPDMonth = "0";
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-mm-dd");
				if (laptopBillDate == null) {
					reimbursementBean.setLaptopBillClaimDate(sd.format(currentDate));
				} else {
					reimbursementBean.setLaptopBillClaimDate(laptopBillDate);
					lapSBLPDMonth = appService.getLaptopSBLPDMonth(reimbursementBean);
				}
				appService.getEmployeeGradeDetails(reimbursementBean);

				// Getting Yesterday
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				Date yesterday = calendar.getTime();

				if (laptopValidMonths == null) {
					laptopValidMonths = "0";
				}
				if (lapSBLPDMonth == null) {
					lapSBLPDMonth = "0";
				}

				laptopValidMonths = String
						.valueOf(Integer.parseInt(laptopValidMonths) + Integer.parseInt(lapSBLPDMonth));
				String lapPreviousGrade = appService.getLaptopPreviousGrade(reimbursementBean);

				// FETCHING PREVIOUS LAPTOP CLAIM DATA
				appService.getPreviousLaptopClaim(reimbursementBean, previousBean);
				if (previousBean.getLaptopAvailed() != null) {

					// SETTING VALUES TO MAIN BEAN
					reimbursementBean.setIsLaptopPurchased(previousBean.getLaptopAvailed());
					reimbursementBean.setLapPurchaseDate(previousBean.getLaptopClaimDate());
					reimbursementBean.setLapDetail(previousBean.getLaptopDetail());
					reimbursementBean.setLapClaimAmount(previousBean.getLaptopClaimAmount());

					laptopVerifyStatus = previousBean.getLaptopVerifyStatus();
					if (previousBean.getLaptopPaidDate() != null) {
						laptopPaidDate = AppConstants
								.parseDate(AppConstants.formatDate(previousBean.getLaptopPaidDate()));
					}
					laptopPaidStatus = previousBean.getLaptopPaidStatus();
					lastLaptopClaimDate = AppConstants
							.parseDate(AppConstants.formatDate(previousBean.getLaptopClaimDate()));
					lastLaptopBillDate = AppConstants.parseDate(AppConstants.formatDate(laptopBillDate));
					nextLaptopEligibleDate = AppConstants
							.parseDate(appService.getNextLaptopEligibleDate(lastLaptopBillDate, laptopValidMonths));
					currentGrade = reimbursementBean.getEmpGrade();

					if (nextLaptopEligibleDate == null) {
						nextLaptopEligibleDate = yesterday;
					}
					if (lastLaptopBillDate == null) {
						lastLaptopBillDate = AppConstants.parseDate("01/01/0001");
					}

					if (nextLaptopEligibleDate.before(currentDate)) {
						lastLaptopFlag = "N";
						if (lastLaptopClaimDate != null
								&& (lastLaptopBillDate == null || !lastLaptopBillDate.equals(lastLaptopClaimDate))
								&& !laptopVerifyStatus.equals("R")) {
							lastLaptopFlag = "Y";
						}
					} else if (lapPreviousGrade != null && currentGrade.equals("F")
							&& !currentGrade.equals(lapPreviousGrade)) {
						lastLaptopFlag = "N";
						if (lastLaptopClaimDate != null && !lastLaptopBillDate.equals(lastLaptopClaimDate)
								&& !laptopVerifyStatus.equals("R")) {
							lastLaptopFlag = "Y";
						}
					}

				}
				if (lastLaptopFlag.equals("Y")) {
					mv.addObject("disableLaptopTab", "true");
					reimbursementBean.setLaptopAgreement(true);
				} else {
					mv.addObject("disableLaptopTab", "false");
					reimbursementBean.setLaptopAgreement(false);

					if (laptopVerifyStatus != null && laptopVerifyStatus.equals("R")) {
						reimbursementBean.setIsLaptopPurchased("");
						reimbursementBean.setLapPurchaseDate("");
						reimbursementBean.setLapDetail("");
						reimbursementBean.setLapClaimAmount("");
					}
				}

				if (laptopVerifyStatus != null && laptopVerifyStatus.equals("N")) {
					laptopMessage = "Last Laptop Claim made is yet to be verified by CAP Nodal Officer.";
				} else if (laptopVerifyStatus != null && laptopVerifyStatus.equals("N")
						&& laptopPaidStatus.equals("N")) {
					laptopMessage = "Last Laptop made is yet to be processed for payment by CAP Cell.";
				} else if (laptopVerifyStatus != null && laptopVerifyStatus.equals("R")
						&& laptopPaidStatus.equals("N")) {
					laptopMessage = "Laptop Claim Rejected.";
				} else if (laptopVerifyStatus != null && laptopVerifyStatus.equals("Y") && laptopPaidDate != null) {
					if (lastLaptopBillDate == null) {
						laptopMessage = "Last Laptop made is under processing for payment at CAP Cell.";
					} else {
						if (currentGrade.equals("E") || currentGrade.equals("D")) {
							nextLaptopEligibleDate = AppConstants.parseDate(
									appService.getNextLaptopEligibleDate(lastLaptopBillDate, laptopValidMonths));
						} else if (currentGrade.equals("F")) {
							laptopValidMonths = String.valueOf(Integer.parseInt(lapSBLPDMonth) + 36);
							nextLaptopEligibleDate = AppConstants.parseDate(
									appService.getNextLaptopEligibleDate(lastLaptopBillDate, laptopValidMonths));
						}
						promotionEffectiveDate = AppConstants.parseDate(reimbursementBean.getPromoPerEffectiveDate());
						if (currentGrade.equals("F") && nextLaptopEligibleDate.after(currentDate)
								&& lastLaptopBillDate.after(promotionEffectiveDate)) {
							laptopMessage = "Eligible Date for next Laptop Claim shall be on or after "
									+ AppConstants.parseDate(lastLaptopBillDate);
						} else if ((currentGrade.equals("E") || currentGrade.equals("D"))
								&& nextLaptopEligibleDate.after(currentDate)) {
							laptopMessage = "Eligible Date for next Laptop Claim shall be on or after "
									+ AppConstants.parseDate(lastLaptopBillDate);
						} else {
							laptopMessage = "You can apply for Laptop Claim Reimbursement.";
						}
					}

				}
				System.out.println(nextLaptopEligibleDate);

			}
			session.setAttribute("mobRefundFlag", mobRefundFlag);
			mv.addObject("appNameTracker", appNameTracker);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("laptopMessage", laptopMessage);
		mv.addObject("mobileClaimMessage", mobileClaimMessage);
		mv.addObject("mobileChargesMessage", mobileChargesMessage);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@PostMapping(value = "/mobileFormSubmit")
	public ModelAndView mobileFormSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		boolean claimAllowed = true;
		Date currentDate = new Date();
		currentDate = new Date(currentDate.getTime());
		String mobileClaimMessage = "";
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);

		try {

			int countSBLPRDMonth = 0;
			int mobileReimbCheck = 0;
			String paidDate = appService.getMobilePaidDate(reimbursementBean);
			reimbursementBean.setPaidDate(paidDate);
			if(paidDate != null){
				countSBLPRDMonth = appService.getSBLPDMonth(reimbursementBean);
			}
			
			reimbursementBean.setCountSBLPRDMonth(String.valueOf(countSBLPRDMonth));
			String currentGrade = "";
			appService.getEmployeeGradeDetails(reimbursementBean);
			Date promotionEffectiveDate = null;
			if (reimbursementBean.getMobileAgreement()) {
				String mobileSrNo = appService.getMaxSrNoForLapMobile(reimbursementBean, "541");
				int mobDetailCheck = appService.getMobileDetCheckCount(reimbursementBean);
				String mobRefundFlag = (String) session.getAttribute("mobRefundFlag");

				if (mobRefundFlag != null && mobRefundFlag.equals("Y")) {
					mobDetailCheck = 0;
				}

				currentGrade = reimbursementBean.getEmpGrade();
				promotionEffectiveDate = AppConstants.parseDate(reimbursementBean.getPromoPerEffectiveDate());
				Date mobileClaimDate = AppConstants
						.parseDate(AppConstants.formatDate(reimbursementBean.getMobileClaimDate()));

				if (mobileClaimDate.before(promotionEffectiveDate)) {
					mobileClaimMessage = "Bill date can not be prior to promotion effective date.";
					claimAllowed = false;
				}

				if (claimAllowed && mobileReimbCheck == 0 && mobDetailCheck == 0) {
					MobileClaimDomain mobileBeanToSave = new MobileClaimDomain();
					mobileBeanToSave.setEmpCode(reimbursementBean.getEmpCode());
					mobileBeanToSave.setSrNo(mobileSrNo);
					mobileBeanToSave.setClaimDate(new java.sql.Date(mobileClaimDate.getTime()));
					mobileBeanToSave.setClaimAmount(reimbursementBean.getMobileClaimAmount());
					mobileBeanToSave.setClaimDetail(reimbursementBean.getMobileDetail());
					mobileBeanToSave.setVerifyStatus("N");
					mobileBeanToSave.setBillDate(new java.sql.Date(mobileClaimDate.getTime()));
					mobileBeanToSave.setApproved("N");
					mobileBeanToSave.setPaidStatus("N");
					mobileBeanToSave.setPaidAmount("0");
					mobileBeanToSave.setBillUpload(reimbursementBean.getMobileBill().getBytes());
					mobileBeanToSave.setFileName("claim_" + reimbursementBean.getEmpCode() + ".pdf");

					MobileClaimReimDomain mobileConsentBean = new MobileClaimReimDomain();
					Date fromDate = AppConstants.parseDate(appService.getFinStartDate());
					Date toDate = AppConstants.parseDate(appService.getAddMonths(countSBLPRDMonth));
					mobileConsentBean.setEmpCode(reimbursementBean.getEmpCode());
					mobileConsentBean.setAfmCode("541");
					mobileConsentBean.setSrNo(mobileSrNo);
					mobileConsentBean.setAvailOption("Y");
					mobileConsentBean.setCreatedDate(new java.sql.Date(currentDate.getTime()));
					mobileConsentBean.setAgreement("Y");
					mobileConsentBean.setEmpGrade(currentGrade);
					mobileConsentBean.setFromDate(new java.sql.Date(fromDate.getTime()));
					mobileConsentBean.setToDate(new java.sql.Date(toDate.getTime()));
					mobileConsentBean.setBranchCode(branchCode);
					boolean isMobileClaimSaved = appService.saveMobileClaim(mobileBeanToSave, mobileConsentBean);
					if (isMobileClaimSaved) {
						mobileClaimMessage = "Mobile reimbursement details has been submitted. !!!!!!!";
					} else {
						mobileClaimMessage = "Mobile reimbursement details has not been submitted. Please contact CAP DESK!!!!!!!";
					}
				}
			}

			notifyMsg = mobileClaimMessage;
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/npLaptopMobileClaim");
		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("mobileClaimMessage", mobileClaimMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@PostMapping(value = "/mobileChargesSubmit")
	public ModelAndView mobileChargesSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		Date currentDate = new Date();
		String currentGrade = "";
		String mobileChargesMessage = "";
		Date chargesLnDate = null;
		currentDate = new Date(currentDate.getTime());
		String mobileClaimMessage = "";
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);

		try {
			if (reimbursementBean.getMobileChargesAgreement()) {
				int mobReimCheck = 0;
				int mobDetCount = 0;
				String chargesLnDateString = appService.getLapMobileLnDate(reimbursementBean, "542");
				if (chargesLnDateString != null) {
					chargesLnDate = AppConstants.parseDate(chargesLnDateString);
				}

				if (reimbursementBean.getMobileCharges().equals("Y")) {
					String mobileChargeSrNo = "";
					mobReimCheck = appService.getChargesReimCheck(reimbursementBean);
					mobDetCount = appService.getChargesDetCount(reimbursementBean);
					if (mobReimCheck > 0 && mobDetCount == 0) {
						reimbursementBean.setMobLapReimCheck(mobReimCheck);
						reimbursementBean.setMobLapDetCheck(mobDetCount);

					}
					mobileChargeSrNo = appService.getMaxSrNoForLapMobile(reimbursementBean, "542");
					String finStartAndEndDate = appService.getFinStartAndEndDate();
					Date finStartDate = AppConstants.parseDate(finStartAndEndDate.split("-")[0]);
					Date finEndDate = AppConstants.parseDate(finStartAndEndDate.split("-")[1]);
					Date mobInstallDate = AppConstants
							.parseDate(AppConstants.formatDate(reimbursementBean.getMobileActivationDate()));
					if (currentGrade.equals("D") && reimbursementBean.getEmpDesignation().equals("089")
							&& finEndDate.equals(AppConstants.parseDate("31/03/2013"))) {
						mobInstallDate = AppConstants
								.parseDate(AppConstants.formatDate(reimbursementBean.getMobileActivationDate()));
						if (mobInstallDate.after(AppConstants.parseDate("01/09/2012"))) {
							finStartDate = mobInstallDate;
						} else {
							finStartDate = AppConstants.parseDate("01/09/2012");
						}
					} else if (currentGrade.equals("D") && !reimbursementBean.getEmpDesignation().equals("089")
							&& finEndDate.equals(AppConstants.parseDate("31/03/2013"))) {
						mobInstallDate = AppConstants
								.parseDate(AppConstants.formatDate(reimbursementBean.getMobileActivationDate()));
						if (mobInstallDate.after(AppConstants.parseDate("01/12/2012"))) {
							finStartDate = mobInstallDate;
						} else {
							finStartDate = AppConstants.parseDate("01/12/2012");
						}
					} else if (currentGrade.equals("D") && finEndDate.equals(AppConstants.parseDate("31/03/2013"))) {
						mobInstallDate = AppConstants
								.parseDate(AppConstants.formatDate(reimbursementBean.getMobileActivationDate()));
						if (mobInstallDate.after(AppConstants.parseDate("01/09/2012"))) {
							finStartDate = mobInstallDate;
						} else {
							finStartDate = AppConstants.parseDate("01/09/2012");
						}
					}

					if (mobDetCount == 0) {
						MobChargesDomain mobileCharge = new MobChargesDomain();
						reimbursementBean.setMobLapReimCheck(mobReimCheck);
						mobileCharge.setEmpCode(empCode);
						mobileCharge.setSrNo(mobileChargeSrNo);
						mobileCharge.setMobNo(reimbursementBean.getMobileNumber());
						mobileCharge.setInstallDate(new java.sql.Date(mobInstallDate.getTime()));
						mobileCharge.setCreatedDate(new java.sql.Date(currentDate.getTime()));
						mobileCharge.setServiceProvider(reimbursementBean.getMobileServiceProvider());

						MobileClaimReimDomain chargesConsentBean = new MobileClaimReimDomain();
						chargesConsentBean.setEmpCode(reimbursementBean.getEmpCode());
						chargesConsentBean.setAfmCode("542");
						chargesConsentBean.setSrNo(mobileChargeSrNo);
						chargesConsentBean.setAvailOption("Y");
						chargesConsentBean.setCreatedDate(new java.sql.Date(currentDate.getTime()));
						chargesConsentBean.setAgreement("Y");
						chargesConsentBean.setEmpGrade(reimbursementBean.getEmpGrade());
						chargesConsentBean.setFromDate(new java.sql.Date(finStartDate.getTime()));
						chargesConsentBean.setToDate(new java.sql.Date(finEndDate.getTime()));
						chargesConsentBean.setBranchCode(branchCode);

						boolean isChargesSaved = appService.saveMobileChargesClaim(mobileCharge, chargesConsentBean,
								mobReimCheck);
						if (isChargesSaved) {
							mobileChargesMessage = "Mobile charges reimbursement details has been submitted. !!!!!!!";
						} else {
							mobileChargesMessage = "Mobile charges reimbursement details has not been submitted. Please contact CAP DESK!!!!!!!";
						}
					}
				}

			}

			notifyMsg = mobileChargesMessage;
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/npLaptopMobileClaim");

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("mobileChargesMessage", mobileChargesMessage);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@RequestMapping(value = "/laptopFormSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView laptopFormSubmit(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		Date currentDate = new Date();
		currentDate = new Date(currentDate.getTime());
		String laptopMessage = "";
		// Previous Opened Tab
		String tabOpened = "npLaptopMobileClaim";
		session.setAttribute("tabOpened", "npVehicaleInsuranceClaim");
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		try {

			if (reimbursementBean.getLaptopAgreement()) {
				String lapPreviousGrade = appService.getLaptopPreviousGrade(reimbursementBean);
				String currGrade = reimbursementBean.getEmpGrade();
				Date lapLnDate = null;
				String lapLnDateString = appService.getLapMobileLnDate(reimbursementBean, "550");
				if (lapLnDateString != null) {
					lapLnDate = AppConstants.parseDate(lapLnDateString);
				}
				String laptopBillDate = appService.getLaptopBillOrClaimDate(reimbursementBean);
				String laptopValidMonths = appService.getLaptopValidMonth(reimbursementBean);
				reimbursementBean.setLaptopBillClaimDate(reimbursementBean.getLapPurchaseDate());
				String lapSBLPDMonth = appService.getLaptopSBLPDMonth(reimbursementBean);
				Date lastLaptopBillDate = null;
				String lapSrNo = "";

				if (laptopBillDate != null) {
					lastLaptopBillDate = AppConstants.parseDate(AppConstants.formatDate(laptopBillDate));
				}

				if (laptopValidMonths == null) {
					laptopValidMonths = "48";
				}

				if (lapSBLPDMonth == null) {
					lapSBLPDMonth = "0";
				}

				laptopValidMonths = String
						.valueOf(Integer.parseInt(laptopValidMonths) + Integer.parseInt(lapSBLPDMonth));

				if (reimbursementBean.getIsLaptopPurchased().equals("Y")) {
					String useFlag = "Y";
					String refundFlag = "";
					lapSrNo = appService.getMaxSrNoForLapMobile(reimbursementBean, "550");
					int laptopReimCheck = 0;
					int laptopDetCheck = 0;
					int laptopCount = 0;
					laptopReimCheck = appService.getLaptopReimCheck(empCode, laptopValidMonths);
					laptopDetCheck = appService.getLaptopDetCheck(empCode, laptopValidMonths);
					laptopCount = appService.getLaptopCount(empCode);

					if (laptopCount == 0) {
						refundFlag = "Y";
					} else {
						refundFlag = "N";
					}

					if (lapPreviousGrade != null && currGrade.equals("F") && !currGrade.equals(lapPreviousGrade)) {
						laptopDetCheck = 0;
						laptopReimCheck = 0;
					}

					if (laptopReimCheck == 0 && laptopDetCheck == 0) {
						LaptopClaimDomain lapClaim = new LaptopClaimDomain();
						Date claimDate = AppConstants
								.parseDate(AppConstants.formatDate(reimbursementBean.getLapPurchaseDate()));
						lapClaim.setEmpCode(empCode);
						lapClaim.setSrNo(lapSrNo);
						lapClaim.setClaimAmount(reimbursementBean.getLapClaimAmount());
						lapClaim.setClaimDate(new java.sql.Date(currentDate.getTime()));
						lapClaim.setClaimDetail(reimbursementBean.getLapDetail());
						lapClaim.setBillDate(new java.sql.Date(claimDate.getTime()));
						lapClaim.setVerifyStatus("N");
						lapClaim.setApproved("N");
						lapClaim.setPaid("N");
						lapClaim.setPaidAmount("0");
						lapClaim.setRefundFlag(refundFlag);
						lapClaim.setBillUpload(reimbursementBean.getLapUploadBill().getBytes());
						lapClaim.setFileName("claim_" + reimbursementBean.getEmpCode() + ".pdf");

						MobileClaimReimDomain laptopConsentBean = new MobileClaimReimDomain();
						Date toDate = AppConstants.parseDate(
								appService.getLaptopValidDate(AppConstants.parseDate(claimDate), laptopValidMonths));
						laptopConsentBean.setEmpCode(reimbursementBean.getEmpCode());
						laptopConsentBean.setAfmCode("550");
						laptopConsentBean.setSrNo(lapSrNo);
						laptopConsentBean.setAvailOption("Y");
						laptopConsentBean.setCreatedDate(new java.sql.Date(currentDate.getTime()));
						laptopConsentBean.setAgreement("Y");
						laptopConsentBean.setEmpGrade(reimbursementBean.getEmpGrade());
						laptopConsentBean.setFromDate(new java.sql.Date(currentDate.getTime()));
						laptopConsentBean.setToDate(new java.sql.Date(toDate.getTime()));
						laptopConsentBean.setBranchCode(branchCode);

						boolean isLaptopSaved = appService.saveLaptopClaim(lapClaim, laptopConsentBean);
						if (isLaptopSaved) {
							laptopMessage = "Laptop reimbursement details has been submitted. !!!!!!!";
						} else {
							laptopMessage = "Laptop reimbursement details has not been submitted. Please contact CAP DESK!!!!!!!";
						}
					} else if (laptopReimCheck == 1 && laptopDetCheck == 1) {
						String reimSrNo = appService.getMaxSrNo(reimbursementBean, "550");
						String maxLapSrNo = appService.getMaxLapSrNo(reimbursementBean);
						if (Integer.parseInt(maxLapSrNo) != Integer.parseInt(reimSrNo)) {
							laptopMessage = "Different claim sr.no. in laptop claim and reimburse claim detail table";
							useFlag = "N";
						}

						if (!useFlag.equals("N")) {
							LaptopClaimDomain lapClaim = new LaptopClaimDomain();
							Date claimDate = AppConstants
									.parseDate(AppConstants.formatDate(reimbursementBean.getLapPurchaseDate()));
							lapClaim.setClaimAmount(reimbursementBean.getLapClaimAmount());
							lapClaim.setClaimDate(new java.sql.Date(currentDate.getTime()));
							lapClaim.setClaimDetail(reimbursementBean.getLapDetail());
							lapClaim.setBillDate(new java.sql.Date(claimDate.getTime()));
							lapClaim.setVerifyStatus("N");
							boolean isUpdated = appService.updateLaptopReim(lapClaim, reimbursementBean);

							if (isUpdated) {
								laptopMessage = "Laptop reimbursement details has been updated. !!!!!!!";
							}
						}

					} else if ((laptopReimCheck == 1 && laptopDetCheck == 0)
							|| (laptopReimCheck == 0 && laptopDetCheck == 1)) {
						laptopMessage = "Claim Details missing either in laptop claim and reimburse claim detail table. Laptop_Det_chk = "
								+ laptopDetCheck + " Laptop_Reimb_chk = " + laptopReimCheck;
					} else {
						laptopMessage = "Error while saving Laptop reimbursement";
					}
				}

			}

			notifyMsg = laptopMessage;
			session.setAttribute("notifyMsg", notifyMsg);
			return new ModelAndView("redirect:/npLaptopMobileClaim");

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("laptopMessage", laptopMessage);
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

}
