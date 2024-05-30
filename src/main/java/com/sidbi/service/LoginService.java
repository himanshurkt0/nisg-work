package com.sidbi.service;

import java.util.List;
import java.util.Map;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.CustomerBean;
import com.sidbi.bean.LoginBean;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.LoginDetailDomain;

public interface LoginService {
	
	public List getRolesForUser(String UserId);
	public Map<String, String> getApplicationForUser(String UserId);
	public Map<String, String> getFirstLevelApplication(String key,String userID, String appName);
	public String getSchemaForApplciation(String param);
	public List<BcmModuleBean> getBcmModuleBeans(String appName, String parentID,String schemaName, String string);
	public String getEmpCode(String userId);
	public String getEmpGrade(String empCode);
	public CustomerBeanDomain getCustomerDetail(String empCode);
	public void saveLoginDetail(LoginDetailDomain loginBean);
	public LoginDetailDomain getCurrentLoggedInUser(String username);
	

}
