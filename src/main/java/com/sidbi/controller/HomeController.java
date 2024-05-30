package com.sidbi.controller;

import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.apiController.UatApiFetchController;
import com.sidbi.auth.ActiveDirectoryAuthentication;
import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.LoginBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.LoginDetailDomain;
import com.sidbi.service.LoginService;

/**
 * @author nisg_himanshuk
 *
 */
@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	LoginService service;

	private List<String> schemaAppList;
	private String appNameTracker;

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView home(@ModelAttribute("LoginBean") LoginBean loginBean, Model model, HttpServletRequest req,
			HttpServletResponse response) {
		HttpSession session = req.getSession(true);
		ModelAndView mv = new ModelAndView();
		try {
			String userName = "";
			String password = "";
			boolean authResult = false;
			if (loginBean.getUsername() != null) {
				userName = loginBean.getUsername();
				password = loginBean.getPassword();
				LoginDetailDomain currentUserBean = service.getCurrentLoggedInUser(loginBean.getUsername());

				if (currentUserBean != null && currentUserBean.getAccountStatus().equals("Active")) {
					authResult = false;

				} else {
					ActiveDirectoryAuthentication authentication = new ActiveDirectoryAuthentication("SIDBIFARM.COM");
					// authResult = authentication.authenticate(userName,
					// password);
					authResult = true;
				}
			}
			if (authResult) {
				if (userName != null) {
					session.setAttribute("isLoggedIn", "true");
					session.setAttribute("loginBean", loginBean);
					model.addAttribute("DASHBOARD", true);

					LoginDetailDomain loginDetail = new LoginDetailDomain(userName.toUpperCase(),
							userName + "@sidbi.in", "1", "Active");
				//	service.saveLoginDetail(loginDetail);

					Map<String, String> userApplicationAndRole = service.getApplicationForUser(userName.toUpperCase());
					List<String> userApplications = new ArrayList<String>();

					String empCode = service.getEmpCode(userName.toUpperCase());
					CustomerBeanDomain customerBean = service.getCustomerDetail(empCode);

					for (Map.Entry<String, String> map : userApplicationAndRole.entrySet()) {
						userApplications.add(map.getKey());
					}

					session.setAttribute("empCode", empCode);
					session.setAttribute("username", userName.toUpperCase());
					session.setAttribute("branchCode", customerBean.getBranchCode());
					session.setAttribute("customerBean", customerBean);
					session.setAttribute("userApplications", userApplications);
					model.addAttribute("userApplications", userApplications);
					model.addAttribute("status", false);
					mv.setViewName("home");
				} else {
					model.addAttribute("error","Please Enter UserName & Password !");
					session.invalidate();
					mv.setViewName("login");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.invalidate();
			model.addAttribute("error", "Techincal Issue, Please contact CapDesk Cell !");
			mv.setViewName("login");
		}
		return mv;
	}

	@RequestMapping(value = "/validateLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView validateLogin(@ModelAttribute("LoginBean") LoginBean loginBean, @CookieValue(value = "UserID", defaultValue = "") String userName,@CookieValue(value = "authToken",defaultValue = "") String authToken, Model model, HttpServletRequest req,
			HttpServletResponse response) {
		HttpSession session = req.getSession(true);
		ModelAndView mv = new ModelAndView();
		String resturnedJsonResponseForUat = "";
		/*String userName = "";
		String authToken = "";*/

		try {

	//		resturnedJsonResponseForUat = UatApiFetchController.getUatApiJSONValidator(userName,authToken);
			
	//		resturnedJsonResponseForUat = UatApiFetchController.getUatApiJSONValidator("Himanshu","TestID");

			if (userName != null && !userName.isEmpty()) {
				session.setAttribute("currentUserName", userName);
				model.addAttribute("DASHBOARD", true);

				LoginDetailDomain loginDetail = new LoginDetailDomain(userName.toUpperCase(), userName + "@sidbi.in",
						"1", "Active");
		//		service.saveLoginDetail(loginDetail);

				Map<String, String> userApplicationAndRole = service.getApplicationForUser(userName.toUpperCase());
				List<String> userApplications = new ArrayList<String>();

				String empCode = service.getEmpCode(userName.toUpperCase());
				CustomerBeanDomain customerBean = service.getCustomerDetail(empCode);

				for (Map.Entry<String, String> map : userApplicationAndRole.entrySet()) {
					userApplications.add(map.getKey());
				}

				session.setAttribute("empCode", empCode);
				session.setAttribute("username", userName.toUpperCase());
				session.setAttribute("branchCode", customerBean.getBranchCode());
				session.setAttribute("customerBean", customerBean);
				session.setAttribute("userApplications", userApplications);
				model.addAttribute("userApplications", userApplications);
				model.addAttribute("status", false);
				mv.setViewName("home");
			} else {
				model.addAttribute("error","Please Enter UserName & Password !");
				mv.setViewName("login");
			}
		} catch (Exception e) {
			session.invalidate();
			model.addAttribute("error", "Techincal Issue, Please contact CapDesk Cell !");
			mv.setViewName("login");
			e.printStackTrace();
		}
		
		return mv;

	}

	@RequestMapping(value = "/afterLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public String afterLogin(@ModelAttribute("LoginBean") LoginBean loginBean, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		List<String> userApplications = new ArrayList<String>();
		String userID = (String) session.getAttribute("username");
		Map<String, String> userApplicationAndRole = service.getApplicationForUser(userID.toUpperCase());
		for (Map.Entry<String, String> map : userApplicationAndRole.entrySet()) {
			userApplications.add(map.getKey());
		}
		model.addAttribute("DASHBOARD", true);
		model.addAttribute("userApplications", userApplications);
		model.addAttribute("status", false);
		return "home";
	}

	@RequestMapping(value = "/firstLevelController/{param}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView firstLevelController(@PathVariable String param, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		// String param = (String) session.getAttribute("appName");
		// param = param.substring(1, param.length()-1);
		String schemaName = service.getSchemaForApplciation(param);
		schemaName = schemaName.substring(1, schemaName.length() - 1);
		String userID = (String) session.getAttribute("username");
		// userID = userID.toUpperCase();
		Map<String, String> schemaAppMap = service.getFirstLevelApplication(schemaName, userID, param);
		schemaAppList = new ArrayList<>();

		for (Map.Entry<String, String> map : schemaAppMap.entrySet()) {
			schemaAppList.add(map.getKey());
		}
		appNameTracker = "->" + param;
		session.setAttribute("appNameTracker", appNameTracker);
		session.setAttribute("schemaAppMap", schemaAppMap);
		model.addObject("schemaAppList", schemaAppList);
		model.addObject("schemaAppMap", schemaAppMap);
		model.addObject("schemaName", schemaName);
		model.addObject("appName", param);
		model.addObject("appNameTracker", appNameTracker);
		model.addObject("status", true);
		model.setViewName("firstLevelHome");
		return model;
	}

	@RequestMapping(value = "/secondLevelHome/{appName}/{schemaName}/{parentID}/{appCalled}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String secondLevelHome(@PathVariable String appName, @PathVariable String schemaName,
			@PathVariable String parentID, @PathVariable String appCalled, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(true);
		String userID = (String) session.getAttribute("username");
		List<BcmModuleBean> bcmModuleBean = new ArrayList<>();
		appNameTracker = (String) session.getAttribute("appNameTracker");
		appNameTracker = appNameTracker + "->" + appCalled;

		bcmModuleBean = service.getBcmModuleBeans(appName, parentID, schemaName, userID);
		Map<String, String> jspMap = new HashMap<>();

		for (BcmModuleBean bean : bcmModuleBean) {
			String jspMappedName = AppConstants.removeSpecialCharacter(bean.getModuleDesc());
			System.out
					.println("\n Jsp Mapped Name === " + bean.getModuleId() + " -------->    " + jspMappedName + "\n");
			jspMap.put(bean.getModuleId(), jspMappedName);
		}

		session.setAttribute("appNameTracker", appNameTracker);
		session.setAttribute("bcmModuleBean", bcmModuleBean);
		session.setAttribute("jspMap", jspMap);
		session.setAttribute("jspPage", "");

		model.addAttribute("appCalled", appCalled);
		model.addAttribute("schemaAppList", schemaAppList);
		model.addAttribute("appNameTracker", appNameTracker);
		// model.addAttribute("schemaAppMap", schemaAppMap);
		model.addAttribute("appName", appName);
		model.addAttribute("schemaName", schemaName);
		model.addAttribute("status", true);
		model.addAttribute("bcmModuleBean", bcmModuleBean);
		model.addAttribute("status", true);

		return "secondLevelHome";

	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest req, HttpServletResponse response) {
		HttpSession session = req.getSession(true);
		session.setAttribute("isLoggedIn", "");

		// Setting header for VAPT Security
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Referrer-Policy", "max-age=session; includeSubDomains");
		response.setHeader("Referrer-Policy", "same-origin");
		response.setHeader("Set-Cookie", "HttpOnly=Secure");
		response.setHeader("Set-Cookie", "SameSite=Lax");
		response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8086/CapDesk/");
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setHeader("X-XSS-Protection", "1; mode=block");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0,must-revalidate");
		response.setHeader("Clear-Site-Data", "*");
		response.setDateHeader("Expires", -1);
		response.setHeader("Pragma", "no-cache");

		model.addAttribute("LoginBean", new LoginBean());
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		session.setAttribute("loginBean", new LoginBean());
		model.addAttribute("LoginBean", new LoginBean());
		String userName = (String) session.getAttribute("username");
		LoginDetailDomain loginDetail = new LoginDetailDomain(userName, userName + "@sidbi.in", "1", "InActive");
	//	service.saveLoginDetail(loginDetail);

		session.setAttribute("isLoggedIn", "");
		session.invalidate();
		return "login";
	}

}
