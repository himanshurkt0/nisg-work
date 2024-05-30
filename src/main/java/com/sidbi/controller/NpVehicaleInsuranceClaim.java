package com.sidbi.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.sidbi.bean.VehicleInsuranceBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class NpVehicaleInsuranceClaim {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/npVehicaleInsuranceClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npVehicaleInsuranceClaim(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String viMessage = (String) session.getAttribute("viMessage");
		boolean errorStatus = true;
		appNameTracker += "->" + "Vehicle Insurance Claim";

		try {

			reimbursementBean.setEmpCode(empCode);
			AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
			AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request, appService);

			String[] statusList = { "I", "Y", "N", "R" };
			String vehicleApproveStatus = "";
			String vehicleInsuranceProceed = "N";
			Date vehicleExpiryDate = null;
			int inactiveCount = 1;
			int activeCount = 0;
			Date vehicleAcquiredDate = null;
			Date currentDate = new Date();

			String retireeDateAndResignation = appService.getEmployeeRetireeDate(empCode);
			Date retireeDate = AppConstants.parseDate(retireeDateAndResignation.split("-")[0]);
			String resignDate = retireeDateAndResignation.split("-")[1];

			long retireeDateTime = (retireeDate.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);

			if (retireeDateTime < 30 || !resignDate.equals("null")) {
				notifyMsg = "You are not allowed to apply for Vehicle Insurance as you have recently resigned or retiring in a month.";
			}

			// getting previous rvme details
			RvmeBeanDomain previousRvmeClaim = appService.getPreviousRvmeClaim(empCode, statusList);
			
			if(previousRvmeClaim == null || previousRvmeClaim.getVerifyStatus().equals("N")){
				reimbursementBean.setViDisabled("true");
			}

			String activeInactiveCount = appService.getActiveInactiveCountForVI(reimbursementBean);
			inactiveCount = Integer.parseInt(activeInactiveCount.split("-")[0]);
			activeCount = Integer.parseInt(activeInactiveCount.split("-")[1]);

			if (previousRvmeClaim != null) {
				vehicleApproveStatus = previousRvmeClaim.getVerifyStatus();
				vehicleExpiryDate = AppConstants
						.parseDate(AppConstants.formatDate(previousRvmeClaim.getRcValidDate().split(" ")[0]));
				vehicleAcquiredDate = AppConstants
						.parseDate(AppConstants.formatDate(previousRvmeClaim.getAcquiredDate().split(" ")[0]));

				String vehicleInsuranceDisplay = "Y";

				// get promotion detail for vehicle insurance
				appService.getPromotionDetailForVehicletInsurance(reimbursementBean);

				VehicleInsuranceBean previousInsurance = appService.getPreviousVehicleInsuranceBean(reimbursementBean);
				if (previousInsurance.getInsuranceAvailOption() == null) {
					vehicleInsuranceDisplay = "N";
					vehicleInsuranceProceed = "I";
					reimbursementBean.setNpInsuranceVerifyStatus("X");
				} else {

					// SETTING VALUES FROM PREVIOUS CLAIM TO MAIN BEAN FOR UI
					// DISPLAY
					String[] fromDate = previousInsurance.getFromDate().split("/");
					String formattedFromDate = fromDate[2] + "-" + fromDate[1] + "-" + fromDate[0];
					String[] expiryDate = previousInsurance.getExpiryDate().split("/");
					String formattedExpiryDate = expiryDate[2] + "-" + expiryDate[1] + "-" + expiryDate[0];
					reimbursementBean.setIsNpInsuranceClaimed(previousInsurance.getInsuranceAvailOption());
					reimbursementBean.setNpPolicyNumber(previousInsurance.getPolicyNumber());
					reimbursementBean.setNpInsuranceProviderName(previousInsurance.getInsuredBy());
					reimbursementBean.setNpInsuranceType(previousInsurance.getInsuranceType());
					reimbursementBean.setNpInsuranceFrom(formattedFromDate);
					reimbursementBean.setNpInsuranceTill(formattedExpiryDate);
					reimbursementBean.setNpInsurancePremiumPaidt(previousInsurance.getPremiumAmount());
					reimbursementBean.setNpInsuranceRemark(previousInsurance.getRemark());
					reimbursementBean.setNpInsuranceRefund(previousInsurance.getRefundStatus());
					reimbursementBean.setNpInsuranceRefundDate(previousInsurance.getRefundDate());
					reimbursementBean.setNpInsurancePaidStatus(previousInsurance.getPaidStatus());
					reimbursementBean.setInsuranceAgreement(true);
				//	reimbursementBean.setBillUpload(previousInsurance.getF);

					if (vehicleAcquiredDate.before(currentDate) && (previousRvmeClaim.getVehicleType().equals("FW")
							|| previousRvmeClaim.getVehicleType().equals("TW")) && inactiveCount == 0) {
						previousInsurance.setExpiryDate("01/04/2006");
					}

					Date lastInsuranceExpiryDate = AppConstants
							.parseDate(appService.getLastInsuranceExpiryDate(reimbursementBean));
					Date lastInsuranceFromDate = AppConstants.parseDate(previousInsurance.getFromDate());
					Date refundDate = null;
					Date refundVerifyDate = null;

					if (previousInsurance.getRefundDate() != null) {
						refundDate = AppConstants.parseDate(previousInsurance.getRefundDate());
					}
					if (previousInsurance.getRefundVerifyDate() != null) {
						refundVerifyDate = AppConstants.parseDate(previousInsurance.getRefundVerifyDate());
					}

					if (lastInsuranceFromDate.before(lastInsuranceExpiryDate)
							&& previousInsurance.getVerifyStatus().equals("N")) {
						notifyMsg = "Vehicle Insurance Reimbursement: Insurance start date should not be earlier to last vehicle insurance expiry date!!!";
					} else if (previousInsurance.getVerifyStatus().equals("N")) {
						notifyMsg = "Please submit the Insurance policy  to CAP Nodal Officer for verification";
					} else if (previousInsurance.getVerifyStatus().equals("Y")) {
						notifyMsg = "Your reimbursement has been verified and cleared for payment";
					}

					// FOR ENABLE NEW CLAIM
					if (lastInsuranceExpiryDate.before(currentDate) && previousInsurance.getVerifyStatus().equals("Y")
							&& previousInsurance.getPaidStatus().equals("Y")
							&& previousInsurance.getRefundStatus().equals("N")
							&& previousInsurance.getRefundVerifyStatus().equals("N")) {
						vehicleInsuranceProceed = "I";
					} else {
						vehicleInsuranceProceed = "X";
					}

					// FOR ENABLE NEW CLAIM ONCE YOU REFUND THE OLD VEHICLE
					// CLAIM
					if (previousInsurance.getVerifyStatus().equals("Y") && previousInsurance.getPaidStatus().equals("Y")
							&& previousInsurance.getRefundStatus().equals("Y")
							&& previousInsurance.getRefundVerifyStatus().equals("Y") && refundDate.before(currentDate)
							&& refundVerifyDate.before(currentDate)) {
						vehicleInsuranceProceed = "I";
					}

					// FOR ENABLE NEW CLAIM ONCE REJECTED
					if (previousInsurance.getVerifyStatus().equals("R")) {
						vehicleInsuranceProceed = "I";
					}

				}

				reimbursementBean.setVehicleInsuranceProceed(vehicleInsuranceProceed);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Previous Opened Tab
		String tabOpened = "npVehicaleInsuranceClaim";
		mv.addObject("appNameTracker", appNameTracker);
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("viMessage", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;

	}

	@RequestMapping(value = "/npVehicaleInsuranceClaimSave", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npVehicaleInsuranceClaimSave(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String branchCode = (String) session.getAttribute("branchCode");
		boolean errorStatus = true;
		boolean isSaved = false;
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "npVehicaleInsuranceClaim";
		session.setAttribute("tabOpened", "npVehicaleInsuranceClaim");
		reimbursementBean.setEmpCode(empCode);
		reimbursementBean.setBranchCode(branchCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		try {

			if (claimAllowed) {
				isSaved = appService.saveVehicleInsuranceClaim(reimbursementBean);
			}
			
			if (isSaved) {
				notifyMsg = "Vehicle insurance reimbursement details has been submitted. !!!!!!!";
				session.setAttribute("viMessage", notifyMsg);
				return new ModelAndView("redirect:/npVehicaleInsuranceClaim");
			} else {
				notifyMsg = "Vehicle insurance reimbursement details has not been submitted. Please contact CAPDESK. Error-"+reimbursementBean.getErrMsg();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		mv.addObject("tabOpened", tabOpened);
		mv.addObject("notifyMsg", notifyMsg);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@RequestMapping(value = "/npVehicaleInsuranceRefundSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView npVehicaleInsuranceRefundSubmit(
			@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String viMessage = "";
		boolean errorStatus = true;
		boolean isSaved = false;
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "npVehicaleInsuranceClaim";
		session.setAttribute("tabOpened", "npVehicaleInsuranceClaim");
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		
		try {
			String refundDate = reimbursementBean.getNpInsuranceRefundDate();
			String insuranceSrNo = "";
			Date formattedFromDate = null;
			Date formattedExpiryDate = null;
			Date currentDate = new Date();
			Date formattedRefundDate = null;
			
			if(refundDate != null){
				String[] refundDateArray = refundDate.split("-");
				refundDate = refundDateArray[2] + "/" + refundDateArray[1] + "/" + refundDateArray[0];
				formattedRefundDate = AppConstants.parseDate(refundDate);
			}
			
			if(formattedRefundDate.after(currentDate)){
				claimAllowed = false;
				viMessage = "Refund date cannot be greater than today.";
			}
			
			if(reimbursementBean.getNpInsuranceRefundAmount() == null){
				claimAllowed = false;
				viMessage = "Invalid Refund Amount........";
			}
			
			VehicleInsuranceBean previousInsurance = appService.getPreviousVehicleInsuranceBean(reimbursementBean);
			if (previousInsurance != null) {
				formattedFromDate = AppConstants.parseDate(previousInsurance.getFromDate());
				formattedExpiryDate = AppConstants.parseDate(previousInsurance.getExpiryDate());
				insuranceSrNo = previousInsurance.getSrNo();
				reimbursementBean.setNpInsuranceSrNo(insuranceSrNo);
			}
			
			if(formattedRefundDate.before(formattedFromDate) || formattedRefundDate.after(formattedExpiryDate)){
				claimAllowed = false;
				viMessage = "Invalid Refund date............";
			}
			
			String refundAmount = appService.getNpRefundPremAmount(reimbursementBean,previousInsurance);
			System.out.println(refundAmount);
				
			if (claimAllowed) {
				isSaved = appService.submitVehicleInsuranceRefund(reimbursementBean);
				if(isSaved){
					viMessage = "Vehicle insurance refund details has been submitted. !!!!!!!";
				} else {
					viMessage = "Vehicle insurance refund details has not been submitted. Please contact CAP DESK!";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		session.setAttribute("viMessage", viMessage);
		return new ModelAndView("redirect:/npVehicaleInsuranceClaim");
	}

}
