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
import com.sidbi.bean.VehicleRegistrationBean;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class VehicleRegistrationController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/VehicleRegistrationTransfer", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView vehicleReregistration(@ModelAttribute("reimbursementBean") ReimbursementBean reimbursementBean,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		String tabOpened = "VehicleRegistrationTransfer";
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

			String vehicleRegMsg = "";
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
			
			VehicleRegistrationBean vehicleBean = new VehicleRegistrationBean();
			vehicleBean.setTrfId(trfId);
			appService.getVehicleRegistrationDetail(reimbursementBean,vehicleBean);
			
			String verifyStatus = vehicleBean.getVerifyFlag() != null ? vehicleBean.getVerifyFlag() : "N";
			String paidStatus = vehicleBean.getPaidFlag() != null ? vehicleBean.getPaidFlag() : "N";
			
			if(verifyStatus.equals("TN") && verifyStatus.equals("TN")){
				vehicleRegMsg = "Upload file for verification by CAP Cell";
			} else if(verifyStatus.equals("NN") && verifyStatus.equals("NN")){
				vehicleRegMsg = "File Uploaded and pending for verification";
			} else if(verifyStatus.equals("RN") && verifyStatus.equals("RN")){
				vehicleRegMsg = "Rejected";
			} else if(verifyStatus.equals("YN") && verifyStatus.equals("YN")){
				vehicleRegMsg = "Bill verified and pending for payment";
			} else if(verifyStatus.equals("YY") && verifyStatus.equals("YY")){
				vehicleRegMsg = "Bill verified and paid";
			}
			
			reimbursementBean.setTypeOfTransfer(HrTrfType);
			reimbursementBean.setEmpName(customerInfo.getEmpName());
			reimbursementBean.setAccomodationType(trfType);
			
			mv.addObject("vehicleRegMsg", vehicleRegMsg);
			
		} catch(Exception e){
			e.printStackTrace();
		}
		mv.addObject("tabOpened", tabOpened);
		mv.setViewName("secondLevelHome");
		return mv;
	}

}
