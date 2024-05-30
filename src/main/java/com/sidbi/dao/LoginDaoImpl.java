package com.sidbi.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidbi.bean.BcmModuleBean;
import com.sidbi.bean.CustomerBean;
import com.sidbi.bean.LoginBean;
import com.sidbi.domain.CustomerBeanDomain;
import com.sidbi.domain.LoginDetailDomain;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List getRolesForUser(String UserId) {

		List result = new ArrayList();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"select CUR_APPL_ROLE from sfarm01.bcm_user_roles where aur_user_id=upper('" + UserId + "')");
			result = query.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getApplicationForUser(String UserId) {
		Map<String, String> result = new HashMap<>();
		List<Object[]> data = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createNativeQuery("select CUR_APPL,CUR_APPL_ROLE from sfarm01.bcm_user_roles where CUR_USER_ID=upper('"
							+ UserId + "') and CUR_APPL = 'CAP DESK'");
			data = query.list();
			for (Object[] string : data) {
				if (string[0] != null && string[1] != null) {
					result.put(string[0].toString(), string[1].toString());
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, String> getFirstLevelApplication(String key, String userID, String appName) {

		Map<String, String> innerMap = new LinkedHashMap<>();
		List<Object[]> data = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("select BM_MODULE_DESC,NVL(BM_MODULE_ID, '0') from " + key
					+ ".bcm_modules where BM_MIGRATED_JAVA = 'Y' and " + "INSTR((SELECT CUR_APPL_ROLE FROM sfarm01.bcm_user_roles where CUR_USER_ID ='"
					+ userID + "' AND CUR_APPL = '" + appName.toUpperCase() + "'),BM_MODULE_ROLE)<> 0 "
					+ "AND BM_MODULE_TYPE = 'M' order by BM_MODULE_ID asc");
			data = query.list();
			for (Object[] row : data) {
				innerMap.put(row[0].toString(), row[1].toString());
			}

			return innerMap;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return innerMap;

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getSchemaForApplciation(String param) {
		String schemaName = "";

		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery(
					"select CA_BO_REFERENCE from sfarm01.bcm_applications where CA_MODULE_DESC = '" + param + "'");
			schemaName = query.getResultList().toString();
			return schemaName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return schemaName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public List<BcmModuleBean> getBcmModuleBeans(String appName, String parentID,String schemaName, String userID) {
		List<BcmModuleBean> bcmModuleBeanList = new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("select BM_MODULE_ID,BM_MODULE_DESC,BM_PARENT_MODULE,BM_MODULE_TYPE,BM_MODULE_LEVEL,BM_MODULE_EXECUTABLE,BM_MODULE_ROLE,BM_MODULE_ACESS_BY from "+schemaName+".bcm_modules where BM_MIGRATED_JAVA = 'Y' and BM_PARENT_MODULE = '"+parentID+"' and INSTR((SELECT CUR_APPL_ROLE FROM sfarm01.bcm_user_roles where CUR_USER_ID = '"+userID+"' AND CUR_APPL = '"+appName+"'),BM_MODULE_ROLE)<> 0 order by BM_MODULE_ID asc");
			List<Object[]> data = query.list();
			
			for(Object[] row : data){
				BcmModuleBean bcmModuleBean = new BcmModuleBean();
				if(row[0] != null){
					bcmModuleBean.setModuleId(row[0].toString());
				}
				if(row[1] != null){
					bcmModuleBean.setModuleDesc(row[1].toString());
				}
				if(row[2] != null){
					bcmModuleBean.setParentId(row[2].toString());
				}
				if(row[3] != null){
					bcmModuleBean.setModuleType(row[3].toString());
				}
				if(row[4] != null){
					bcmModuleBean.setModuleLevel(row[4].toString());
				}
				if(row[5] != null){
					bcmModuleBean.setModuleRole(row[5].toString());
				}
				if(row[6] != null){
					bcmModuleBean.setModuleAccessBy(row[6].toString());
				}
				
				bcmModuleBeanList.add(bcmModuleBean);
			}
			
			return bcmModuleBeanList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bcmModuleBeanList;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getEmpCode(String userId) {
		String empCode = "";
		try{
			Session session = sessionFactory.getCurrentSession();
			@SuppressWarnings("rawtypes")
			Query query = session.createNativeQuery("select CUP_EMP_CODE from sfarm01.bcm_user_profile where CUP_USER_ID = '"+userId+"'");
			empCode =(String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return empCode;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public String getEmpGrade(String empCode) {
		String grade = "";
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createNativeQuery("select VHE_EMP_GRADE from cadmin.v_hr_emp where VHE_EMP_CD = '"+empCode+"'");
			grade =(String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public CustomerBeanDomain getCustomerDetail(String empCode) {
		CustomerBeanDomain customerDetails = new CustomerBeanDomain();
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from CustomerBeanDomain where empCode = :empCode");
			query.setParameter("empCode", empCode);
			customerDetails = (CustomerBeanDomain) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDetails;
	}

	@Override
	public void saveLoginDetail(LoginDetailDomain loginBean) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(loginBean);

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public LoginDetailDomain getCurrentLoggedInUser(String username) {
		LoginDetailDomain customerDetails = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from LoginDetailDomain where userId = :username");
			query.setParameter("username", username);
			customerDetails = (LoginDetailDomain) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerDetails;
	}
	
	
}
