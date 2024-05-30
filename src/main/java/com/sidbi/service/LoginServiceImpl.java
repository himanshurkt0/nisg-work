package com.sidbi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.LoginBean;
import com.sidbi.dao.LoginDao;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.LoginDetailDomain;

@Service("loginService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;

	@Override
	public List getRolesForUser(String UserId) {
		// TODO Auto-generated method stub
		return loginDao.getRolesForUser(UserId);
	}

	@Override
	public Map<String, String> getApplicationForUser(String UserId) {
		// TODO Auto-generated method stub
		return loginDao.getApplicationForUser(UserId);
	}

	@Override
	public Map<String, String> getFirstLevelApplication(String key,String userID, String appName) {
		// TODO Auto-generated method stub
		return loginDao.getFirstLevelApplication(key,userID,appName);
	}

	@Override
	public String getSchemaForApplciation(String param) {
		// TODO Auto-generated method stub
		return loginDao.getSchemaForApplciation(param);
	}

	@Override
	public List<BcmModuleBean> getBcmModuleBeans(String appName, String parentID,String schemaName, String userID) {
		// TODO Auto-generated method stub
		return loginDao.getBcmModuleBeans(appName,parentID,schemaName,userID);
	}

	@Override
	public String getEmpCode(String userId) {
		// TODO Auto-generated method stub
		return loginDao.getEmpCode(userId);
	}

	@Override
	public String getEmpGrade(String empCode) {
		// TODO Auto-generated method stub
		return loginDao.getEmpGrade(empCode);
	}

	@Override
	public CustomerBeanDomain getCustomerDetail(String empCode) {
		// TODO Auto-generated method stub
		return loginDao.getCustomerDetail(empCode);
	}

	@Override
	public void saveLoginDetail(LoginDetailDomain loginBean) {
		loginDao.saveLoginDetail(loginBean);
		
	}

	@Override
	public LoginDetailDomain getCurrentLoggedInUser(String username) {
		return loginDao.getCurrentLoggedInUser(username);
	}

}
