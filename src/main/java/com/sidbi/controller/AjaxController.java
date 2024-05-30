package com.sidbi.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sidbi.constants.AppConstants;
import com.sidbi.service.AppService;

@Controller
public class AjaxController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/firstLevelHomeController", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String firstLevelHomeController(HttpServletRequest request, Model model) {

		String appName = request.getParameter("appName");
		appName = appName.substring(1, appName.length() - 1);
		request.getSession().setAttribute("appName", appName);

		return appName;
	}

	@RequestMapping(value = "/secondLevelHomeController", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String secondLevelHomeController(HttpServletRequest request) {

		String appName = request.getParameter("appName");
		String parentId = request.getParameter("parentId");
		String schemaName = request.getParameter("schemaName");
		String appCalled = request.getParameter("appCalled");
		request.getSession().setAttribute("appName", appName);
		request.getSession().setAttribute("parentId", parentId);
		request.getSession().setAttribute("schemaName", schemaName);
		request.getSession().setAttribute("appCalled", appCalled);

		return appName;
	}

	@RequestMapping(value = "/getJspPageName", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getJspPageName(HttpServletRequest request) {

		String appCalled = request.getParameter("appCalled");
		String moduleId = request.getParameter("moduleId");
		String moduleName = request.getParameter("moduleDesc");

		request.getSession().setAttribute("appCalled", appCalled);
		request.getSession().setAttribute("moduleId", moduleId);
		request.getSession().setAttribute("moduleDesc", moduleName);

		return "true";
	}

	@RequestMapping(value = "/getDependentsCount", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getDependentsCount(HttpServletRequest request) {
		String coursePersureCode = request.getParameter("coursePersued");
		String dependentCode = request.getParameter("dependentCode");
		String empCode = (String) request.getSession().getAttribute("empCode");
		int count = appService.getDependentCountValue(dependentCode, coursePersureCode, empCode);

		return String.valueOf(count);
	}

	@RequestMapping(value = "/getPaymentValue", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getPaymentValue(HttpServletRequest request) {
		String coursePersued = request.getParameter("coursePersued");
		String coursePassed = request.getParameter("coursePassed");
		String empGrade = request.getParameter("empGrade");
		String courseYear = request.getParameter("courseYear");
		String startDate = request.getParameter("startDate");
		String percentage = request.getParameter("percentage");
		startDate = AppConstants.formatDate(startDate);
		String appConstantCode = request.getParameter("appConstantCode");

		String paymentValue = appService.getPaymentValue(coursePersued, coursePassed, empGrade, courseYear, startDate,
				percentage, appConstantCode);
		if (paymentValue == null || paymentValue.equals("null")) {
			paymentValue = "0";
		}
		return paymentValue;
	}

	/*
	 * @RequestMapping(value = "/checkIfApplied", method =
	 * {RequestMethod.GET,RequestMethod.POST}) public @ResponseBody String
	 * checkIfApplied(HttpServletRequest request) { String coursePersued =
	 * request.getParameter("coursePersued"); String dependents =
	 * request.getParameter("dependents"); String endDate =
	 * request.getParameter("endDate"); String empCode =
	 * request.getParameter("empCode"); endDate =
	 * AppConstants.formatDate(endDate); String count =
	 * appService.getScholarshipApplCount(empCode,dependents,coursePersued,
	 * endDate); return count; }
	 */

	@RequestMapping(value = "/getBookGrantPaymentValue", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getBookGrantPaymentValue(HttpServletRequest request) {
		String coursePersued = request.getParameter("coursePersued");
		String coursePassed = request.getParameter("coursePassed");
		String bookGrantCommonGrade = request.getParameter("bookGrantCommonGrade");
		String courseYear = request.getParameter("courseYear");
		String startDate = request.getParameter("startDate");
		String percentage = request.getParameter("percentage");
		startDate = AppConstants.formatDate(startDate);

		String paymentValue = appService.getPaymentValue(coursePersued, coursePassed, bookGrantCommonGrade, courseYear,
				startDate, percentage, AppConstants.BookGrantConstant);
		if (paymentValue.equals("null")) {
			paymentValue = "0";
		}
		return paymentValue;
	}

	@RequestMapping(value = "/getScholPaymentValue", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getScholPaymentValue(HttpServletRequest request) {
		String coursePersued = request.getParameter("coursePersued");
		String coursePassed = request.getParameter("coursePassed");
		String scholCommonGrade = request.getParameter("scholCommonGrade");
		String courseStream = request.getParameter("courseStream");
		String startMonthDate = request.getParameter("startMonthDate");
		String percentage = request.getParameter("percentage");
		String boarder = request.getParameter("boarder");
		startMonthDate = AppConstants.formatDate(startMonthDate);
		String paymentValue = appService.getPaymentValue(coursePersued, coursePassed, scholCommonGrade, courseStream,
				startMonthDate, percentage, AppConstants.ScholarshipConstant, boarder);
		if (paymentValue.equals("null")) {
			paymentValue = "0";
		}
		return paymentValue;
	}

	@RequestMapping(value = "/getFinAssistData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getFinAssistData(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> finAssistList = appService.getFinAssistData(empCode);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(finAssistList);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getFinAssistDataToEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getFinAssistDataToEdit(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		String srNo = (String) request.getParameter("srNo");
		request.getSession().setAttribute("isUpdatingPage", "true");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> finAssistList = appService.getFinAssistDataToEdit(empCode, srNo,
					AppConstants.DependentConstant);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(finAssistList);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getBookGrantSCSTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getBookGrantData(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> bookGrantData = appService.getBookGrantData(empCode);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(bookGrantData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getScholarshipSCSTData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getScholarshipSCSTData(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> scholarshipData = appService.getScholarshipSCSTData(empCode);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(scholarshipData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getBookGrantDataToEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getBookGrantDataToEdit(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		String srNo = (String) request.getParameter("srNo");
		request.getSession().setAttribute("isUpdatingPage", "true");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> bookGrantData = appService.getFinAssistDataToEdit(empCode, srNo,
					AppConstants.BookGrantConstant);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(bookGrantData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getScholarShipDataToEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getScholarShipDataToEdit(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		String srNo = (String) request.getParameter("srNo");
		request.getSession().setAttribute("isUpdatingPage", "true");
		JSONObject formJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<JSONObject> liat = new ArrayList<JSONObject>();
		try {
			List<Object[]> bookGrantData = appService.getFinAssistDataToEdit(empCode, srNo,
					AppConstants.ScholarshipConstant);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(bookGrantData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getTelephoneHistory", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getTelephoneHistory(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		JSONArray jsonArray = new JSONArray();
		try {
			List<Object[]> telephoneData = appService.getTelephoneHistory(empCode);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(telephoneData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getRvmeHistory", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getRvmeHistory(HttpServletRequest request, HttpServletResponse response) {
		String empCode = (String) request.getSession().getAttribute("empCode");
		JSONArray jsonArray = new JSONArray();
		try {
			List<Object[]> telephoneData = appService.getRvmeHistoryButton(empCode);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(telephoneData);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	@RequestMapping(value = "/getCarExpenseRate", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getCarExpenseRate(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> requestData = new HashMap<>();
		String carShiftDate = request.getParameter("carShiftDate");
		requestData.put("carShiftDate", carShiftDate);
		String selectedButton = request.getParameter("selectedButton");
		requestData.put("selectedButton", selectedButton);

		JSONArray jsonArray = new JSONArray();
		try {
			String rate = appService.getCarRateForOthers(requestData);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			String json = new Gson().toJson(rate);
			out.print(json);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

}
