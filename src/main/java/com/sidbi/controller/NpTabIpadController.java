package com.sidbi.controller;

import java.util.Calendar;
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
import com.sidbi.domain.ReimConsentDomain;
import com.sidbi.domain.TabletClaimDomain;
import com.sidbi.service.AppService;

@Controller
public class NpTabIpadController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/tabIpadClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npTabIpadClaim(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		String tabOpened = "tabIpadClaim";
		String tabMessage = "";
		String fmCode = "548";
		Date date = new Date();
		Date currentDate = new Date(date.getTime());
		Date tabEligibleDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = calendar.getTime();
		String verifyStatus = "";
		Date lastTabClaimDate = null;
		Date lastTabPaidClaimDate = null;
		String actPayDate = "";
		try {

			String validMonth = appService.getAfsSalMonth(reimbursementBean, fmCode);
			System.out.println("afsSalMonth  --> " + validMonth);

			String paidDate = appService.getLastTabPaidDate(reimbursementBean);
			System.out.println("paidDate  --> " + paidDate);

			if (paidDate != null) {
				String formattedDaidDate = AppConstants.formatDate(paidDate);
				System.out.println("formattedDaidDate  --> " + formattedDaidDate);
				reimbursementBean.setPaidDate(formattedDaidDate);
				int sBLPDMonth = appService.getSBLPDMonth(reimbursementBean);
				System.out.println("sBLPDMonth  --> " + sBLPDMonth);
			}

			// -- Mobile Claim Entry for GMs
			appService.getLastTabletClaimDetails(reimbursementBean);

			String lastPaidClaimDate = appService.getLastTabPaidClaimDate(reimbursementBean);
			if (lastPaidClaimDate != null) {
				lastTabPaidClaimDate = AppConstants.parseDate(lastPaidClaimDate);
			}
			System.out.println("lastPaidClaimDate  --> " + lastPaidClaimDate);

			String lastClaimDate = appService.getLastTabClaimDate(reimbursementBean);
			if (lastClaimDate != null) {
				lastTabClaimDate = AppConstants.parseDate(lastClaimDate);
			}

			// This method getLaptopValidDate() is generic method will update
			// with generic name later
			String eligibleDate = null;
			if (lastPaidClaimDate != null){
				eligibleDate = appService.getLaptopValidDate(lastPaidClaimDate, validMonth);
			}			
			if (eligibleDate != null) {
				tabEligibleDate = AppConstants.parseDate(eligibleDate);
			} else {
				tabEligibleDate = yesterday;
			}
			System.out.println("eligibleDate  --> " + eligibleDate);

			if (tabEligibleDate.before(currentDate)) {
				verifyStatus = reimbursementBean.getIsVerified();
			} else {
				verifyStatus = null;
			}

			if (lastTabClaimDate != null
					&& (!lastTabPaidClaimDate.equals(lastTabClaimDate) || lastTabPaidClaimDate == null)
					&& !verifyStatus.equals("R")) {
				reimbursementBean.setIsTabClaimed("Y");
			}
			String paidStatus = reimbursementBean.getIsPaid();
			if (paidStatus == null) {
				paidStatus = "";
			}
			if (verifyStatus.equals("N")) {
				tabMessage = "Last Tab/iPad Claim made is yet to be verified by CAP Nodal Officer.";
			} else if (verifyStatus.equals("Y") && paidStatus.equals("N")) {
				tabMessage = "Last Tab/iPad made is yet to be processed for payment by CAP Cell.";
			} else if (verifyStatus.equals("Y") && paidStatus.equals("Y") && paidDate != null) {
				actPayDate = appService.getTabPayDate(reimbursementBean, validMonth);

				if (actPayDate == null) {
					tabMessage = "Last Tab/iPad made is under processing for payment at CAP Cell.";
				} else {
					eligibleDate = appService.getLaptopValidDate(actPayDate, validMonth);
					tabEligibleDate = AppConstants.parseDate(eligibleDate);
					if (tabEligibleDate.after(currentDate)) {
						tabMessage = "Eligible Date for next Tab/iPad Claim shall be on or after " + eligibleDate;
					} else {
						tabMessage = "You can apply for Tab/iPad Claim Reimbursement.";
					}
				}
			} else {
				tabMessage = "You can apply for Tab/iPad Claim Reimbursement.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		appNameTracker += "->" + "Tab/Ipad Claim";
		mv.addObject("appNameTracker", appNameTracker);
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("tabMessage", tabMessage);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@RequestMapping(value = "/tabIpadClaimSave", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npTabIpadClaimSave(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String branchCode = (String) session.getAttribute("branchCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		String tabOpened = "tabIpadClaim";
		String tabMessage = "";
		String fmCode = "548";
		Date date = new Date();
		Date currentDate = new Date(date.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = calendar.getTime();
		String verifyStatus = "";
		Date lastTabPaidClaimDate = null;
		String actPayDate = "";
		String applicableDate = "";
		Date appFromDate = null;
		String formattedDaidDate = "";
		int sBLPDMonth = 0;
		String useFlag = "";
		String maxSrNo = "";
		int tabReimCheck = 0;
		int tabDetCheck = 0;
		String maxTabClaimSrNo = "";
		try {

			applicableDate = appService.getTabApplicableDate(reimbursementBean);
			if (applicableDate != null) {
				appFromDate = AppConstants.parseDate(applicableDate);
			}

			String validMonth = appService.getAfsSalMonth(reimbursementBean, fmCode);
			if (validMonth == null) {
				validMonth = "0";
			}
			String paidDate = appService.getLastTabPaidDate(reimbursementBean);

			if (paidDate != null) {
				formattedDaidDate = AppConstants.formatDate(paidDate);
				reimbursementBean.setPaidDate(formattedDaidDate);
				sBLPDMonth = appService.getSBLPDMonth(reimbursementBean);
			}

			int validMonthTab = Integer.parseInt(validMonth) + sBLPDMonth;
			verifyStatus = reimbursementBean.getIsVerified();
			String lastTab = reimbursementBean.getIsTabClaimed();
			if (lastTab == null) {
				lastTab = "";
			}
			if (verifyStatus == null || verifyStatus.isEmpty()) {
				verifyStatus = "X";
			}
			if (verifyStatus.equals("X") || verifyStatus.equals("R")) {
				if (lastTab.equals("Y")) {
					useFlag = "Y";
					maxSrNo = appService.getMaxSrNoFromConsent(reimbursementBean, fmCode);
					if (maxSrNo == null) {
						maxSrNo = "000";
					}
					tabReimCheck = appService.getTabReimCheck(reimbursementBean, fmCode, validMonthTab);
					tabDetCheck = appService.getTabDetCheck(reimbursementBean, validMonthTab);
				}

				if (tabReimCheck == 0 && tabDetCheck == 0) {
					TabletClaimDomain tabBean = new TabletClaimDomain();
					tabBean.setEmpCode(empCode);
					tabBean.setSrNo(maxSrNo);
					Date claimDate = AppConstants
							.parseDate(AppConstants.formatDate(reimbursementBean.getTabClaimDate()));
					tabBean.setClaimDate(new java.sql.Date(claimDate.getTime()));
					tabBean.setClaimAmount(reimbursementBean.getTabClaimAmount());
					tabBean.setClaimDetail(reimbursementBean.getTabDetail());
					tabBean.setVerifyStatus("N");
					tabBean.setApproved("N");
					tabBean.setPaidStatus("N");
					tabBean.setPaidAmount("0");
					tabBean.setRemarks(reimbursementBean.getRemark());
					tabBean.setFileName("Tab_Claim" + empCode + ".pdf");
					tabBean.setBill(reimbursementBean.getUploadBill().getBytes());

					ReimConsentDomain consentBean = new ReimConsentDomain();
					consentBean.setEmpCode(empCode);
					consentBean.setAfmCode(fmCode);
					consentBean.setSrNo(maxSrNo);
					consentBean.setAvailOption("Y");
					consentBean.setCreatedDate(new java.sql.Date(currentDate.getTime()));
					consentBean.setAgreement("Y");
					consentBean.setEmpGrade(reimbursementBean.getEmpGrade());
					consentBean.setBranchCode(branchCode);
					consentBean.setFromDate(new java.sql.Date(claimDate.getTime()));
					Date toDate = AppConstants.parseDate(appService
							.getLaptopValidDate(AppConstants.parseDate(claimDate), String.valueOf(validMonthTab)));
					consentBean.setToDate(new java.sql.Date(toDate.getTime()));

					boolean isTabClaimSaved = appService.saveTabClaim(tabBean, consentBean);
					if (isTabClaimSaved) {
						tabMessage = "Tab/Ipad reimbursement details has been submitted. !!!!!!!";
						notifyMsg = tabMessage;
						session.setAttribute("notifyMsg", notifyMsg);
						session.setAttribute("tabOpened", tabOpened);
						return new ModelAndView("redirect:/tabIpadClaim");
					} else {
						tabMessage = "Tab/Ipad reimbursement details has not been submitted. Please contact CAP DESK!!!!!!!";
					}
				} else if (tabReimCheck == 1 && tabDetCheck == 1 && verifyStatus.equals("R")) {
					maxTabClaimSrNo = appService.getMaxSrNoInTabClaim(empCode);

					if (Integer.parseInt(maxTabClaimSrNo) != Integer.parseInt(maxSrNo)) {
						tabMessage = "Different claim sr.no. in tab claim and reimburse claim detail table";
						TabletClaimDomain updateBean = new TabletClaimDomain();
						reimbursementBean.setSrNo(maxSrNo);
						boolean isUpdated = appService.updateTabClaim(reimbursementBean,updateBean);

					}
				} else if ((tabReimCheck == 0 && tabDetCheck == 1) || (tabReimCheck == 1 && tabDetCheck == 0)) {
					tabMessage = "Claim Details missing either in tab claim and reimburse claim detail table.";
				}

			}

			if (lastTab.equals("Y") && (verifyStatus.equals("N") || verifyStatus.equals("R"))
					&& reimbursementBean.getFileName() == null) {
				tabMessage = "Pls upload receipt/bill for iPad/Tab.";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("tabMessage", tabMessage);
		mv.setViewName("secondLevelHome");
		return mv;

	}

}
