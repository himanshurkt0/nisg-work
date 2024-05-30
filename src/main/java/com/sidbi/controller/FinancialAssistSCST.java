package com.sidbi.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.PersonalAdvanceBean;
import com.sidbi.bean.PreviousApplication;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class FinancialAssistSCST {


	@Autowired
	AppService appService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/FinAssistSCST", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getFinAssistSCST(
			@ModelAttribute("saveFinancialAssist") PersonalAdvanceBean saveFinancialAssist, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession(true);
		String childModule = (String) session.getAttribute("moduleDesc");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String empCode = (String) session.getAttribute("empCode");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		List<BcmModuleBean> bcmModuleBean = (List) session.getAttribute("bcmModuleBean");
		saveFinancialAssist = new PersonalAdvanceBean();
		String notifyMsg = (String) session.getAttribute("notifyMsg");
		mv.addObject("notifyMsg", notifyMsg);
		if (notifyMsg == null) {
			notifyMsg = "";
		}

		String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

		if (isUpdatingPage == null || isUpdatingPage.equalsIgnoreCase("true"))
			isUpdatingPage = "false";

		session.setAttribute("notifyMsg", "");
		appNameTracker = appNameTracker + "->" + "Fin. Assist - SC/ST";

		String catCode = appService.getCatCode(empCode);
		Map<Integer, String> schoolCoursePersue = new HashMap<>();
		Map<Integer, String> schoolCoursePassed = new HashMap<>();
		Map<String, String> dependents = new HashMap<>();
		dependents = appService.getDependents(empCode);
		schoolCoursePersue = appService.getSchoolCoursePersue();
		schoolCoursePassed = appService.getSchoolCoursePassed();

		session.setAttribute("dependents", dependents);
		session.setAttribute("schoolCoursePersue", schoolCoursePersue);
		session.setAttribute("schoolCoursePassed", schoolCoursePassed);

		model.addAttribute("catCode", catCode);
		model.addAttribute("saveFinancialAssist", saveFinancialAssist);
		model.addAttribute("empGrade", customerInfo.getEmpGrade());
		model.addAttribute("empName", customerInfo.getEmpName());
		model.addAttribute("schoolCoursePersue", schoolCoursePersue);
		model.addAttribute("schoolCoursePassed", schoolCoursePassed);
		model.addAttribute("dependents", dependents);

		mv.addObject("bcmModuleBean", bcmModuleBean);
		mv.addObject("empCode", empCode);
		mv.addObject("appNameTracker", appNameTracker);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/FinAssistSCSTSave", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getFinAssistSCSTSave(
			@ModelAttribute("saveFinancialAssist") PersonalAdvanceBean saveFinancialAssist, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String lv_sr_no = "";
		HttpSession session = request.getSession(true);
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		List<BcmModuleBean> bcmModuleBean = (List) session.getAttribute("bcmModuleBean");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");

		Map<Integer, String> schoolCoursePersue = (Map) session.getAttribute("schoolCoursePersue");
		Map<Integer, String> schoolCoursePassed = (Map) session.getAttribute("schoolCoursePassed");
		Map<String, String> dependents = (Map) session.getAttribute("dependents");

		String notifyMsg = "";
		String isUpdatingPage = (String) session.getAttribute("isUpdatingPage");

		if (isUpdatingPage == null)
			isUpdatingPage = "false";
		String dependentName = dependents.get(saveFinancialAssist.getDependents());
		PreviousApplication appliedApplication = appService.getScholarshipApplCount(saveFinancialAssist.getEmpCode(),
				saveFinancialAssist.getDependents(), saveFinancialAssist.getCoursePersued(),
				AppConstants.formatDate(saveFinancialAssist.getEndDate()));
		if (appliedApplication != null) {

			if (Integer.parseInt(appliedApplication.getCount()) > 0) {
				if (appliedApplication.getStatus() != null && appliedApplication.getStatus().equalsIgnoreCase("Y")) {
					notifyMsg = "Already availed for session " + saveFinancialAssist.getStartDate() + " to "
							+ saveFinancialAssist.getEndDate() + " for Course "
							+ schoolCoursePersue.get(Integer.parseInt(saveFinancialAssist.getCoursePersued()));

				} else if (appliedApplication.getStatus() != null
						&& appliedApplication.getStatus().equalsIgnoreCase("N")) {
					if (isUpdatingPage.equalsIgnoreCase("true")) {
						session.setAttribute("isUpdatingPage", "");
						notifyMsg = appService.saveScholarshipPMT(customerInfo.getBranchCode(), dependentName,
								saveFinancialAssist.getLv_sr_no(), saveFinancialAssist);

						if (notifyMsg.contains("sucessfully saved")) {
							notifyMsg = "Successfully Updated.";
							session.setAttribute("notifyMsg", notifyMsg);
							return new ModelAndView("redirect:/FinAssistSCST");
						}
					} else {
						notifyMsg = "Already applied for session " + saveFinancialAssist.getStartDate() + " to "
								+ saveFinancialAssist.getEndDate() + " for Course "
								+ schoolCoursePersue.get(Integer.parseInt(saveFinancialAssist.getCoursePersued()));
					}
				}
				mv.addObject("notifyMsg", notifyMsg);
			} else {
				lv_sr_no = appService.getLVRSerialNumber(saveFinancialAssist.getEmpCode(),AppConstants.DependentConstant);

				notifyMsg = appService.saveScholarshipPMT(customerInfo.getBranchCode(), dependentName, lv_sr_no,
						saveFinancialAssist);
				session.setAttribute("notifyMsg", notifyMsg);
				if (notifyMsg.contains("sucessfully saved")) {
					return new ModelAndView("redirect:/PersonalAdvance");
				}

			}

		}

		notifyMsg = "";

		mv.addObject("dependentValue", saveFinancialAssist.getDependents());
		mv.addObject("coursePersuevalue", saveFinancialAssist.getCoursePersued());
		mv.addObject("coursePassedValue", saveFinancialAssist.getCoursePassed());
		mv.addObject("courseYearValue", saveFinancialAssist.getCourseYear());
		mv.addObject("billUpload", saveFinancialAssist.getBillUpload());
		mv.addObject("schoolCoursePersue", schoolCoursePersue);
		mv.addObject("schoolCoursePassed", schoolCoursePassed);
		mv.addObject("dependents", dependents);
		mv.addObject("saveFinancialAssist", saveFinancialAssist);
		mv.addObject("appNameTracker", appNameTracker);
		mv.addObject("bcmModuleBean", bcmModuleBean);
		mv.setViewName("secondLevelHome");
		return mv;

	}

	@GetMapping(value = "/downloadFinancialAssistBill/{empCode}/{srNo}")
	public void downloadFinancialAssistBill(@PathVariable String empCode, @PathVariable String srNo,
			HttpServletResponse response) throws SQLException, IOException {
		Blob blobPdf = appService.getFinAssisPdf(empCode.substring(1,empCode.length()-1), srNo.substring(1, srNo.length()-1),AppConstants.DependentConstant);
		int blobLength = (int) blobPdf.length();
		byte[] blobAsBytes = blobPdf.getBytes(1, blobLength);
		response.setHeader("Content-Disposition","inline; filename=\"" + "FinAssistBill" + "\";");
		OutputStream output = response.getOutputStream();
		output.write(blobAsBytes);
		output.close();
		output.flush();
		blobPdf.free();
	}
	
	
	
	



}
