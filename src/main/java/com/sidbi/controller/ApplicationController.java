package com.sidbi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.service.AppService;

@Controller
public class ApplicationController {

	@Autowired
	AppService appService;

	@RequestMapping(value = "/getJspPageForApp/{moduleId}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getJspPageForApp(@PathVariable String moduleId,HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();

		HttpSession session = request.getSession(true);
		String jspPage = "";
	//	String moduleId = (String) session.getAttribute("moduleId");
		String appNameTracker = (String) session.getAttribute("appNameTracker");
		String empCode = (String) session.getAttribute("empCode");
		CustomerBeanDomain customerInfo = (CustomerBeanDomain) session.getAttribute("customerBean");
		List<BcmModuleBean> bcmModuleBean = (List) session.getAttribute("bcmModuleBean");
		Map<String, String> jspMap = (Map) session.getAttribute("jspMap");

		

		if (jspMap.get(moduleId) != null) {
			String jspPageName = jspMap.get(moduleId);
			jspPage = jspPageName + ".jsp";
			session.setAttribute("jspPage", jspPage);
			return new ModelAndView("redirect:/" + jspPageName + "");
		}
		mv.addObject("bcmModuleBean", bcmModuleBean);
		mv.addObject("empCode", empCode);
		mv.addObject("appNameTracker", appNameTracker);
		mv.setViewName("secondLevelHome");
		return mv;
	}

	

}
