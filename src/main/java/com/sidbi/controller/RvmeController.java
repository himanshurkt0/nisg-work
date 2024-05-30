package com.sidbi.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.ReimbursementBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class RvmeController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/rvmeVehicleClaim", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView rvmeVehicleClaim(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		String rvmeMessage = (String) session.getAttribute("rvmeMessage");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		Date currentDate = new Date();
		String empGrade = "";
		boolean claimAllowed = true;
		// Previous Opened Tab
		String tabOpened = "rvmeVehicleClaim";
		appNameTracker += "->" + "RVME Claim";
		try {
			reimbursementBean.setEmpCode(empCode);
			AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
			AppConstants.setReimTabElementEligibility(mv, reimbursementBean, request, appService);

			List<String> afsCodeList = reimbursementBean.getAfsCodeList();
			empGrade = reimbursementBean.getEmpGrade();

			String[] statusList = { "I", "Y", "N", "R" };

			List<String> metroRvmeLimitAllowedBranchList = Arrays.asList(AppConstants.metroRvmeLimitAllowedBranch);
			String branchCode = customerInfo.getBranchCode();
			if (!metroRvmeLimitAllowedBranchList.contains(branchCode)) {
				reimbursementBean.setDisableResidingPlace("disabled");
			}

			if (!(empGrade.equals("E") || empGrade.equals("F"))) {
				reimbursementBean.setDisableHireByBank("disabled");
			}

			String rvmeSubmittedFmCode = appService.getRvmeSubmittedFmCode(empCode);
			reimbursementBean.setRvmeSubmittedFmCode(rvmeSubmittedFmCode);

			// getting previous rvme details
			RvmeBeanDomain previousRvmeClaim = appService.getPreviousRvmeClaim(empCode, statusList);
			session.setAttribute("previousRvmeBean", previousRvmeClaim);
			if (previousRvmeClaim != null) {
				reimbursementBean.setRvmeVerifyStatus(previousRvmeClaim.getVerifyStatus());
			} else {
				reimbursementBean.setRvmeVerifyStatus("");
			}

			if (previousRvmeClaim != null && rvmeSubmittedFmCode.equals("501")) {
				reimbursementBean.setIsRvmeVehicleClaim("Y");
				reimbursementBean.setRvmeVehicleRegNumber(previousRvmeClaim.getRegisterNum());
				reimbursementBean.setRvmeVehicleRegisteredAt(previousRvmeClaim.getRegisteredPlace());
				reimbursementBean.setRvmeEngineCapacity(previousRvmeClaim.getEngineCCLessThan16());
				reimbursementBean.setRvmeVehicleType(previousRvmeClaim.getVehicleType());
				reimbursementBean.setEngineNumber(previousRvmeClaim.getEngineNumber());
				reimbursementBean.setChasisNumber(previousRvmeClaim.getChasisNumber());
				String rvmeAcquiredDate = previousRvmeClaim.getAcquiredDate().split(" ")[0];
				reimbursementBean.setRvmeVehicleAcquiredOn(rvmeAcquiredDate);
				reimbursementBean.setIsVehicleProvidedByBank(previousRvmeClaim.getHiredFromBank());
				String rvmeRcValidDate = previousRvmeClaim.getRcValidDate().split(" ")[0];
				reimbursementBean.setRegvalidityDate(rvmeRcValidDate);
				String rvmeShiftingDate = previousRvmeClaim.getShiftDate().split(" ")[0];
				reimbursementBean.setShiftingDate(rvmeShiftingDate);
				reimbursementBean.setVerifyStatus(previousRvmeClaim.getVerifyStatus());
				reimbursementBean.setFileName(
						previousRvmeClaim.getFileName() == null ? "null" : previousRvmeClaim.getFileName());
				reimbursementBean.setRvmePlaceOfUse(previousRvmeClaim.getMaintPlace());
				reimbursementBean.setResidingPlace(previousRvmeClaim.getMaintPlace());
				reimbursementBean.setRvmeAgreement(true);
				File pdfFile = new File(empCode + "_RVME.pdf");
				/*
				 * CustomMultipartFile multipartFile = new
				 * CustomMultipartFile(previousRvmeClaim.getBillUpload());
				 * multipartFile.transferTo(pdfFile);
				 * reimbursementBean.setBillUpload(multipartFile);
				 */
				reimbursementBean.setFileName(previousRvmeClaim.getFileName() + ".pdf");
				mv.addObject("disablePopulateButton", "disable");

				if (previousRvmeClaim.getVerifyStatus().equals("N")) {
					if (notifyMsg.isEmpty()) {
						rvmeMessage = "You have already submitted and to be processed by cap cell";
					}
					claimAllowed = false;

				} else if (previousRvmeClaim.getVerifyStatus().equals("R")) {
					rvmeMessage = "Vehicle details rejected by Verifier, please correct vehicle details & submit. If RC Book not submitted, submit the same to CAP Nodal Officer for verification";
				} else {
					rvmeMessage = "You have already declared vehicle in RVME claim";
				}

			} else if (rvmeSubmittedFmCode.equals("502")) {
				reimbursementBean.setIsRvmeVehicleClaim("N");
				reimbursementBean.setIsRvmeNoVehicleClaim("Y");
				reimbursementBean.setRvmeAgreement(true);
				rvmeMessage = "No Vehicle is Declared. !";
			} 

			Map<String, String> rvmePlaceOfUseMap = appService.getRvmePlaceOfUseMap();
			String promotionEffectiveDate = appService.getPromotionEffectiveDate(empCode);
			Date promEffDate = AppConstants.parseDate(promotionEffectiveDate);

			long promotionYearTime = (currentDate.getTime() - promEffDate.getTime()) / (1000 * 60 * 60 * 24);
			Double promotionYearTimeInYears = promotionYearTime / 365.0;
			promotionYearTimeInYears = BigDecimal.valueOf(promotionYearTimeInYears).setScale(2, RoundingMode.CEILING)
					.doubleValue();
			String lv_per_promo = reimbursementBean.getVhePerPromo();
			System.out.println("promotionYearTimeInYears->" + promotionYearTimeInYears);

			if (!afsCodeList.contains("503")) {
				reimbursementBean.setIsElgibleForDriverSalary("disabled");
			}
			if (empGrade.equals("C") && promotionYearTimeInYears >= 5 && lv_per_promo.equals("N")) {
				reimbursementBean.setIsElgibleForDriverSalary("enabled");
			}
			if (empGrade.equals("B") && promotionYearTimeInYears >= 5 && reimbursementBean.getEpFlag().equals("Y")) {
				reimbursementBean.setIsElgibleForDriverSalary("enabled");
			}
			// mv.addObject("rvmeVerifyStatus", claimAllowed);
			mv.addObject("rvmePlaceOfUseMap", rvmePlaceOfUseMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("claimAllowed", claimAllowed);
		mv.addObject("claimAllowed", claimAllowed);
		mv.addObject("tabOpened", tabOpened);
		mv.addObject("rvmeMessage", rvmeMessage);
		mv.addObject("appNameTracker", appNameTracker);
		mv.setViewName("secondLevelHome");
		return mv;

	}

	@RequestMapping(value = "/rvmeVehicleSave", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView rvmeVehicleSave(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		System.out.println("Entered RVME Save");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String empCode = (String) session.getAttribute("empCode");
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		boolean claimAllowed = (boolean) session.getAttribute("claimAllowed");
		String rvmeMessage = "";

		boolean isSaved = false;
		reimbursementBean.setEmpCode(empCode);
		AppConstants.setReimTabEligibility(mv, reimbursementBean, request, appService);
		try {

			if (reimbursementBean.getIsRvmeVehicleClaim() != null) {
				if (reimbursementBean.getRvmeVehicleType().equals("TW")
						&& reimbursementBean.getIsRvmeDriverSalary().equals("Y")) {
					rvmeMessage = "Driver Salary cannot be claimed for Two wheeler";
					claimAllowed = false;
				}
			}

			if (claimAllowed) {
				System.out.println("Entered RVME Save5");
				isSaved = appService.saveRvmeVehicleClaim(reimbursementBean);
			}

			if (isSaved) {
				rvmeMessage = "RVME reimbursement details has been submitted. !!!!!!!";
			} else {
				rvmeMessage = "RVME reimbursement details has not been submitted. Error - " + reimbursementBean.getErrMsg();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("rvmeMessage", rvmeMessage);
		return new ModelAndView("redirect:/rvmeVehicleClaim");

	}

	/*
	 * @GetMapping(value = "/viewRvmeBill") public void
	 * downloadbookGrantBill(@PathVariable String empCode, @PathVariable String
	 * srNo, HttpServletResponse response) throws SQLException, IOException {
	 * Blob blobPdf =
	 * appService.getRvmeBillFile(empCode.substring(1,empCode.length()-1)); int
	 * blobLength = (int) blobPdf.length(); byte[] blobAsBytes =
	 * blobPdf.getBytes(1, blobLength);
	 * response.setHeader("Content-Disposition","inline; filename=\"" +
	 * "FinAssistBill" + "\";"); OutputStream output =
	 * response.getOutputStream(); output.write(blobAsBytes); output.close();
	 * output.flush(); blobPdf.free(); }
	 */

}
