package com.sidbi.dao;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sidbi.bean.BookGrantBookingHistory;
import com.sidbi.bean.CarExpenseDetailBean;
import com.sidbi.bean.DeclarationReimbTelephoneBean;
import com.sidbi.bean.EmployeeInfoBean;
import com.sidbi.bean.LaptopMobileClaimBean;
import com.sidbi.bean.NonPeriodicBookClaimBean;
import com.sidbi.bean.NonPeriodicBriefcaseBean;
import com.sidbi.bean.PersonalAdvanceBean;
import com.sidbi.bean.PreviousApplication;
import com.sidbi.bean.ReimbBookingHistory;
import com.sidbi.bean.ReimbConsentDetailBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.bean.TransportExpenseDetailBean;
import com.sidbi.bean.VehicleInsuranceBean;
import com.sidbi.bean.VehicleRegistrationBean;
import com.sidbi.constants.AppConstants;
import com.sidbi.domain.FinancialAssistDataDomain;
import com.sidbi.domain.LaptopClaimDomain;
import com.sidbi.domain.MobChargesDomain;
import com.sidbi.domain.MobileClaimDomain;
import com.sidbi.domain.MobileClaimReimDomain;
import com.sidbi.domain.ReimConsentDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.domain.SchoolScholarshipBillUpload;
import com.sidbi.domain.TabletClaimDomain;

@SuppressWarnings("deprecation")
@Repository("appDao")
public class AppDaoImpl implements AppDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "rawtypes" })
	@Override
	public String getCatCode(String empCode) {

		Session session = sessionFactory.openSession();
		String catCode = "";
		try {

			Query query = session.createNativeQuery(
					"select ltrim(rtrim(CAT_CODE)) from cadmin.V_EMP_CATG where VHE_EMP_CD= '" + empCode + "'");
			catCode = (String) query.uniqueResult();
			return catCode;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return catCode;
	}

	@Override
	public Map<Integer, String> getSchoolCoursePersue() {
		Session session = sessionFactory.openSession();
		Map<Integer, String> schoolCoursePersue = new HashMap<>();
		try {

			Query query = session.createNativeQuery(
					"SELECT AQM_DESC,AQM_CD FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('5','6') ORDER BY AQM_CD");
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				if (obj[0] != null && obj[1] != null) {
					schoolCoursePersue.put(Integer.parseInt(obj[1].toString()), obj[0].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return schoolCoursePersue;
	}

	@Override
	public Map<Integer, String> getSchoolCoursePassed() {
		Session session = sessionFactory.openSession();
		Map<Integer, String> schoolCoursePassed = new HashMap<>();
		try {

			Query query = session.createNativeQuery(
					"SELECT AQM_DESC,AQM_CD FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('4')   ORDER BY AQM_CD");
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				if (obj[0] != null && obj[1] != null) {
					schoolCoursePassed.put(Integer.parseInt(obj[1].toString()), obj[0].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return schoolCoursePassed;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, String> getDependents(String empCode) {
		Session session = sessionFactory.openSession();
		Map<String, String> dependents = new HashMap<>();
		try {

			Query query = session.createNativeQuery(
					"select VHED_DEPEND_SRNO,VHED_DEPEND_NAME from cadmin.V_HR_EMP_DEPENDENTS where VHED_EMP_CD = '"
							+ empCode + "'");
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				if (obj[0] != null && obj[1] != null) {
					dependents.put(obj[0].toString(), obj[1].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return dependents;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getDependentCountValue(String dependentCode, String coursePersureCode, String empCode) {

		Session session = sessionFactory.openSession();
		int count = 0;
		try {
			Query query = session.createNativeQuery("select count(*) from cadmin.ADM_FNCL_ASSIST "
					+ "where AFA_EMP_CD = '" + empCode + "' and AFA_DEPEND_SRNO = '" + dependentCode + "' "
					+ "and AFA_COURSE_PURSUING_CD='" + coursePersureCode + "' " + "and AFA_AFM_CD ='"
					+ AppConstants.DependentConstant + "' " + "and AFA_VERIFY_STATUS = 'Y'");
			count = Integer.parseInt(String.valueOf(query.uniqueResult()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count;
	}

	@Override
	public String getPaymentValue(String coursePersued, String coursePassed, String dependentGrade, String courseYear,
			String startDate, String percentage, String fmCode) {
		Session session = sessionFactory.openSession();
		String paymentValue = "";
		try {
			Query query = session.createNativeQuery("SELECT Fa_Get_schol_book_Fncl_elg('" + fmCode + "'," + " '"
					+ coursePersued + "', '" + coursePassed + "', '" + dependentGrade + "', 'Z', '" + percentage
					+ "', 'Z', 'Z', to_date('" + startDate + "','dd/MM/yyyy'))" + " from dual");
			paymentValue = String.valueOf(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paymentValue;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public PreviousApplication getScholarshipApplCount(String empCode, String dependents, String coursePersued,
			String endDate) {
		Session session = sessionFactory.openSession();
		String count = "";
		PreviousApplication previousApplication = new PreviousApplication();

		List<Object[]> objectList;
		try {
			Query query = session.createNativeQuery(
					"SELECT COUNT(*),AFA_AFM_CD,AFA_SESSION_START_YR, AFA_SESSION_END_YR,AQM_DESC,NVL(AFA_VERIFY_STATUS,'N') FROM cadmin.ADM_FNCL_ASSIST,cadmin.ADM_QUAL_MASTER "
							+ "WHERE afa_emp_cd = '" + empCode + "' " + "AND afa_depend_srno = '" + dependents + "' "
							+ "AND AFA_AFM_CD IN('" + AppConstants.DependentConstant + "') "
							+ " AND AFA_COURSE_PURSUING_CD = AQM_CD " + "AND    AFA_SESSION_END_YR >= to_date('"
							+ endDate + "','dd/MM/yyyy') " + "AND NVL(AFA_VERIFY_STATUS,'N') IN ('Y' ,'N') "
							+ "group by AFA_AFM_CD,AFA_SESSION_START_YR, AFA_SESSION_END_YR,AQM_DESC,AFA_VERIFY_STATUS");
			objectList = query.list();

			if (!objectList.isEmpty()) {
				for (Object[] object : objectList) {

					if (object[0] != null) {
						previousApplication.setCount(object[0].toString());

					}
					if (object[1] != null) {
						previousApplication.setAfmCode(object[1].toString());

					}
					if (object[2] != null) {
						previousApplication.setPreviousSessionStartDate(object[2].toString());

					}
					if (object[3] != null) {
						previousApplication.setPreviousSessionEndDate(object[3].toString());

					}
					if (object[4] != null) {
						previousApplication.setCourseDesc(object[4].toString());

					}
					if (object[5] != null) {
						previousApplication.setStatus(object[5].toString());

					}

				}
			} else {
				previousApplication.setCount("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return previousApplication;
	}

	@Override
	public String getLVRSerialNumber(String empCode, String appCode) {
		String lvr_sr_no = "";
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createNativeQuery("SELECT lpad(to_char(nvl(MAX(AERC_SR_NO),0)),3,'0') "
					+ "FROM cadmin.ADM_EMP_REIMB_CONSENT WHERE aerc_emp_cd = '" + empCode + "' "
					+ " AND aerc_afm_cd = '" + appCode + "'");
			lvr_sr_no = String.valueOf(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return lvr_sr_no;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String saveScholarshipPMT(String branchCode, String dependentName, String lv_sr_no,
			PersonalAdvanceBean personalAdvanceBean) {
		String successMessage = "";
		String dependentRelation = "";
		String startYear = AppConstants.formatDate(personalAdvanceBean.getStartDate(), "01");
		String endYear = AppConstants.formatDate(personalAdvanceBean.getEndDate(), "30");

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		try {
			Query query = session.createNativeQuery("select VHED_DEPEND_RELATION from cadmin.V_HR_EMP_DEPENDENTS "
					+ "where VHED_EMP_CD = '" + personalAdvanceBean.getEmpCode() + "' " + "and VHED_DEPEND_NAME = '"
					+ dependentName + "' " + "and VHED_DEPEND_SRNO = '" + personalAdvanceBean.getDependents() + "' ");
			dependentRelation = String.valueOf(query.uniqueResult());

			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall(
						"{ call cadmin.Ka_Reimbcomm.pa_scholarship_pmt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, branchCode);
				stmt.setString(2, personalAdvanceBean.getEmpCode());
				stmt.setString(3, AppConstants.DependentConstant);
				stmt.setString(4, "Y");
				stmt.setString(5, lv_sr_no);
				stmt.setString(6, lv_sr_no);
				stmt.setString(7, personalAdvanceBean.getDependents());
				stmt.setString(8, dependentName);
				stmt.setString(9, dependentRelation);
				stmt.setString(10, personalAdvanceBean.getCoursePersued());
				stmt.setString(11, personalAdvanceBean.getCoursePassed());
				stmt.setString(12, "Z");
				stmt.setString(13, personalAdvanceBean.getPercentage());
				stmt.setString(14, personalAdvanceBean.getCourseYear());
				stmt.setString(15, "Z");
				stmt.setDate(16, new Date(AppConstants.parseDate(startYear).getTime()));
				stmt.setDate(17, new Date(AppConstants.parseDate(endYear).getTime()));
				stmt.setString(18, personalAdvanceBean.getPayment());

				stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(20, java.sql.Types.VARCHAR);

				stmt.execute();

				String errNo = (String) stmt.getString(19);
				String errMsg = (String) stmt.getString(20);

				if (errNo == null && errMsg == null) {

					//// sr no

					// Blob
					// blob=Hibernate.getLobCreator(sessionFactory.getCurrentSession()).createBlob(personalAdvanceBean.getBillUpload().getInputStream(),personalAdvanceBean.getBillUpload().getSize());

					System.out.println(lv_sr_no);
					query = session.createNativeQuery(
							"select AFA_SR_NO from cadmin.ADM_FNCL_ASSIST WHERE AFA_SESSION_END_YR= (select max(AFA_SESSION_END_YR) from cadmin.ADM_FNCL_ASSIST "
									+ "WHERE afa_emp_cd = '" + personalAdvanceBean.getEmpCode() + "' "
									+ " AND   afa_depend_srno = '" + personalAdvanceBean.getDependents() + "' "
									+ " AND  NVL(AFA_VERIFY_STATUS,'N')<>'C' "
									+ "AND AFA_AFM_CD = '555')  AND afa_emp_cd = '" + personalAdvanceBean.getEmpCode()
									+ "' " + " AND   afa_depend_srno = '" + personalAdvanceBean.getDependents() + "' "
									+ " AND AFA_AFM_CD = '555'");
					lv_sr_no = (String) query.uniqueResult();
					byte[] bytes = personalAdvanceBean.getBillUpload().getBytes();
					SchoolScholarshipBillUpload billUpload = new SchoolScholarshipBillUpload();
					billUpload.setBillUpload(bytes);
					billUpload.setEmpCode(personalAdvanceBean.getEmpCode());
					billUpload.setSrNo(lv_sr_no);
					billUpload.setFmCode(AppConstants.DependentConstant);
					billUpload.setRemark(personalAdvanceBean.getRemark());

					/*
					 * query = session.
					 * createQuery("update cadmin.ADM_FNCL_ASSIST set AFA_FILE_DATA = "
					 * +
					 * " '"+blob+"', AFA_VERIFY_REMARKS = '"+personalAdvanceBean
					 * .getRemark()+"' where " +
					 * "AFA_EMP_CD = '"+personalAdvanceBean.getEmpCode()+"' " +
					 * "and AFA_SR_NO = '"+lv_sr_no+"' and " +
					 * "AFA_AFM_CD = '"+AppConstants.DependentConstant+"'");
					 */
					String hql = "UPDATE SchoolScholarshipBillUpload set billUpload = :billUpload, remark =:remark "
							+ "WHERE empCode = :empCode " + "and srNo =:srNo and fmCode =:fmCode";
					query = session.createQuery(hql);
					query.setParameter("billUpload", bytes);
					query.setParameter("remark", personalAdvanceBean.getRemark());
					query.setParameter("empCode", personalAdvanceBean.getEmpCode());
					query.setParameter("srNo", lv_sr_no);
					query.setParameter("fmCode", AppConstants.DependentConstant);

					int result = query.executeUpdate();
					System.out.println("Row affected ---- >  " + result);

					tx.commit();
					successMessage = "The reimbursements requests have been sucessfully saved.";
				} else {
					successMessage = errMsg;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return successMessage;
	}

	@Override
	public List<Object[]> getFinAssistData(String empCode) {

		Session session = sessionFactory.openSession();
		List<Object[]> finAssistData = new ArrayList<>();
		try {
			Query query = session.createNativeQuery("select AFA_EMP_CD,AFA_SR_NO,AFA_DEPEND_RELATION,AFA_CHILD_NAME,"
					+ "(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD = AFA_COURSE_PASSED_CD),(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD = AFA_COURSE_PURSUING_CD),AFA_PERCENTAGE_OBTAINED,to_char(AFA_SESSION_START_YR,'DD/MM/YYYY'),"
					+ "to_char(AFA_SESSION_END_YR,'DD/MM/YYYY'),to_char(AFA_CREATED_DT,'DD/MM/YYYY'),nvl(to_char(AFA_VERIFY_DT,'DD/MM/YYYY'),'Not Verified'),nvl(AFA_VERIFY_STATUS,'N'),AFA_YEAR,AFA_AMT,"
					+ "AFA_AFM_CD,nvl(AFA_FILE_NAME,'FinAssistBill.pdf') from cadmin.ADM_FNCL_ASSIST where AFA_EMP_CD = '" + empCode + "' and AFA_AFM_CD = '555'");
			finAssistData = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return finAssistData;
	}

	@Override
	public List<Object[]> getFinAssistDataToEdit(String empCode, String srNo, String appCode) {
		Session session = sessionFactory.openSession();
		List<Object[]> finAssistData = new ArrayList<>();
		try {
			Query query = session.createNativeQuery("select AFA_EMP_CD,AFA_SR_NO,AFA_DEPEND_SRNO,"
					+ "AFA_COURSE_PASSED_CD,AFA_COURSE_PURSUING_CD,AFA_PERCENTAGE_OBTAINED,to_char(AFA_SESSION_START_YR,'YYYY-MM-DD'),"
					+ "to_char(AFA_SESSION_END_YR,'YYYY-MM-DD'),AFA_YEAR,AFA_AMT,"
					+ "AFA_AFM_CD,AFA_FILE_DATA,AFA_CHILD_NAME,AFA_DSPLN,AFA_DAY_BOARDER_FLAG from cadmin.ADM_FNCL_ASSIST where AFA_EMP_CD = '"
					+ empCode + "' and AFA_SR_NO = '" + srNo + "' " + "and AFA_AFM_CD = '" + appCode + "' ");
			finAssistData = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return finAssistData;
	}

	@Override
	public Blob getFinAssisPdf(String empCode, String srNo, String fmCode) {

		Session session = sessionFactory.openSession();
		Blob blobPdf = null;
		try {
			Query query = session
					.createNativeQuery("select AFA_FILE_DATA from cadmin.ADM_FNCL_ASSIST where AFA_EMP_CD = '" + empCode
							+ "' and AFA_SR_NO = '" + srNo + "' " + "and AFA_AFM_CD = '" + fmCode + "' ");
			blobPdf = (Blob) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return blobPdf;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public Map<String, String> getBookGrantCoursePassedList() {
		Session session = sessionFactory.openSession();
		Map<String, String> coursePassList = new HashMap<>();
		List<Object[]> objectList = null;
		try {
			Query query = session
					.createNativeQuery("SELECT AQM_CD,AQM_DESC FROM ADM_QUAL_MASTER where AQM_CD in('1','2','3','4')"
							+ " ORDER BY AQM_CD ");
			objectList = query.list();
			for (Object[] obj : objectList) {
				if (obj[0] != null && obj[1] != null) {
					coursePassList.put(obj[0].toString(), obj[1].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return coursePassList;
	}

	@Override
	public Map<String, String> getbookGrantCoursePersueMap() {
		Session session = sessionFactory.openSession();
		Map<String, String> coursePersueMap = new HashMap<>();
		List<Object[]> objectList = null;
		try {
			Query query = session
					.createNativeQuery("SELECT AQM_CD,AQM_DESC FROM ADM_QUAL_MASTER where AQM_CD in('1','2','3','4')"
							+ " ORDER BY AQM_CD ");
			objectList = query.list();
			for (Object[] obj : objectList) {
				if (obj[0] != null && obj[1] != null) {
					coursePersueMap.put(obj[0].toString(), obj[1].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return coursePersueMap;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	@Override
	public BookGrantBookingHistory getBookGrantPreviousBooking(ReimbursementBean reimbursementBean) {
		BookGrantBookingHistory bookingHistory = null;
		Session session = sessionFactory.openSession();
		List<Object[]> resultObject = null;
		try {
			Query query = session.createNativeQuery(
					" SELECT COUNT(*),AFA_AFM_CD,to_char(AFA_SESSION_START_YR,'dd/mm/yyyy'), to_char(AFA_SESSION_END_YR,'dd/mm/yyyy'),AQM_DESC,NVL(AFA_VERIFY_STATUS,'N') "
							+ "FROM cadmin.ADM_FNCL_ASSIST, cadmin.ADM_QUAL_MASTER " + "WHERE afa_emp_cd = '"
							+ reimbursementBean.getEmpCode() + "' " + " AND  afa_depend_srno = '"
							+ reimbursementBean.getDependentCode().charAt(0) + "' " + "AND AFA_AFM_CD IN('"
							+ AppConstants.BookGrantConstant + "') " + "AND AFA_COURSE_PURSUING_CD= '"
							+ reimbursementBean.getCoursePersuedCode() + "' " + " AND AQM_CD= '"
							+ reimbursementBean.getCoursePersuedCode() + "' "
							+ "AND AFA_SESSION_END_YR >=(select max(AFA_SESSION_END_YR) from cadmin.ADM_FNCL_ASSIST "
							+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
							+ " AND   afa_depend_srno = '" + reimbursementBean.getDependentCode().charAt(0) + "' "
							+ "AND AFA_COURSE_PURSUING_CD='" + reimbursementBean.getCoursePersuedCode() + "') "
							+ " group by AFA_AFM_CD,AFA_SESSION_START_YR, AFA_SESSION_END_YR,AQM_DESC,AFA_VERIFY_STATUS");
			resultObject = query.list();
			if (!resultObject.isEmpty()) {
				for (Object[] obj : resultObject) {
					bookingHistory = new BookGrantBookingHistory();
					if (obj[0] != null) {
						bookingHistory.setCount(Integer.parseInt(obj[0].toString()));
					}
					if (obj[1] != null) {
						bookingHistory.setFmCode(obj[1].toString());
					}
					if (obj[2] != null) {
						bookingHistory.setSessionStartDate(obj[2].toString());
					}
					if (obj[3] != null) {
						bookingHistory.setSessionEndDate(obj[3].toString());
					}
					if (obj[4] != null) {
						bookingHistory.setCourseDesc(obj[4].toString());
					}
					if (obj[5] != null) {
						bookingHistory.setStatus(obj[5].toString());
					}

				}
			} else {
				bookingHistory = new BookGrantBookingHistory();
				bookingHistory.setCount(0);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bookingHistory;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getBookGrantYearCount(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Object object = null;
		String yearCount = "";
		try {

			Query query = session.createNativeQuery("SELECT COUNT(*) FROM cadmin.ADM_FNCL_ASSIST "
					+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' " + " AND    afa_depend_srno ='"
					+ reimbursementBean.getDependentCode().charAt(0) + "' " + "AND AFA_COURSE_PURSUING_CD='"
					+ reimbursementBean.getCoursePersuedCode().charAt(0) + "' " + "AND AFA_AFM_CD IN('"
					+ AppConstants.BookGrantConstant + "')");

			object = query.uniqueResult();
			if (object == null) {
				yearCount = "0";
			} else {
				yearCount = object.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return yearCount;
	}

	@Override
	public String getBookGrantDuration(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Object object = null;
		String duration = "";
		try {

			Query query = session.createNativeQuery("SELECT AQM_DURATION FROM cadmin.ADM_QUAL_MASTER"
					+ " WHERE AQM_CD ='" + reimbursementBean.getCoursePersuedCode().charAt(0) + "'");

			object = query.uniqueResult();
			if (object == null) {
				duration = "0";
			} else {
				duration = object.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return duration;
	}

	@Override
	public String getBookGrantFmName(String fmCode) {
		Session session = sessionFactory.openSession();
		Object object = null;
		String fmName = "";
		try {

			Query query = session.createNativeQuery(
					" select AFM_NAME FROM cadmin.ADM_FACILITY_MASTER" + " where afm_cd='" + fmCode + "'");

			object = query.uniqueResult();
			if (object != null) {
				fmName = (String) object;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return fmName;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public String saveBookGrantSCST(ReimbursementBean reimbursementBean) {
		String successMessage = "";
		String startYear = AppConstants.formatDate(reimbursementBean.getStartDate());
		String endYear = AppConstants.formatDate(reimbursementBean.getEndDate());
		Session session = sessionFactory.openSession();
		String isUpdatingPage = reimbursementBean.getIsUpdatingPage();
		if (isUpdatingPage == null) {
			isUpdatingPage = "";
		}
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall(
						"{ call cadmin.Ka_Reimbcomm.pa_scholarship_pmt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, AppConstants.BookGrantConstant);
				stmt.setString(4, "Y");
				stmt.setString(5, reimbursementBean.getLv_sr_no());
				stmt.setString(6, reimbursementBean.getLv_sr_no());
				stmt.setString(7, "" + reimbursementBean.getDependentCode().charAt(0));
				stmt.setString(8,
						reimbursementBean.getDependentList().get("" + reimbursementBean.getDependentCode().charAt(0)));
				stmt.setString(9, reimbursementBean.getDependentRelation());
				stmt.setString(10, "" + reimbursementBean.getCoursePersuedCode());
				stmt.setString(11, "" + reimbursementBean.getCoursePassedCode());
				stmt.setString(12, "Z");
				stmt.setString(13, reimbursementBean.getPercentage());
				stmt.setString(14, "Z");
				stmt.setString(15, "Z");
				stmt.setDate(16, new Date(AppConstants.parseDate(startYear).getTime()));
				stmt.setDate(17, new Date(AppConstants.parseDate(endYear).getTime()));
				stmt.setString(18, reimbursementBean.getPayment());

				stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(20, java.sql.Types.VARCHAR);

				stmt.execute();

				String errNo = (String) stmt.getString(19);
				String errMsg = (String) stmt.getString(20);

				if (errNo == null && errMsg == null) {
					String lv_sr_no = "";
					query = session.createNativeQuery(
							"select AFA_SR_NO from cadmin.ADM_FNCL_ASSIST WHERE AFA_SESSION_END_YR= (select max(AFA_SESSION_END_YR) from cadmin.ADM_FNCL_ASSIST "
									+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
									+ " AND   afa_depend_srno = '" + reimbursementBean.getDependentCode().charAt(0)
									+ "' " + " AND  NVL(AFA_VERIFY_STATUS,'N')<>'C' " + "AND AFA_AFM_CD = '"
									+ AppConstants.BookGrantConstant + "')  AND afa_emp_cd = '"
									+ reimbursementBean.getEmpCode() + "' " + " AND   afa_depend_srno = '"
									+ reimbursementBean.getDependentCode().charAt(0) + "' " + " AND AFA_AFM_CD = '"
									+ AppConstants.BookGrantConstant + "'");
					if (isUpdatingPage.equalsIgnoreCase("true")) {
						lv_sr_no = reimbursementBean.getLv_sr_no();
					} else {
						lv_sr_no = (String) query.uniqueResult();
					}

					byte[] bytes = reimbursementBean.getBillUpload().getBytes();
					SchoolScholarshipBillUpload billUpload = new SchoolScholarshipBillUpload();
					billUpload.setBillUpload(bytes);
					billUpload.setEmpCode(reimbursementBean.getEmpCode());
					billUpload.setSrNo(lv_sr_no);
					billUpload.setFmCode(AppConstants.BookGrantConstant);
					billUpload.setRemark(reimbursementBean.getRemark());

					String hql = "UPDATE SchoolScholarshipBillUpload set billUpload = :billUpload, remark =:remark "
							+ "WHERE empCode = :empCode " + "and srNo =:srNo and fmCode =:fmCode";
					query = session.createQuery(hql);
					query.setParameter("billUpload", bytes);
					query.setParameter("remark", reimbursementBean.getRemark());
					query.setParameter("empCode", reimbursementBean.getEmpCode());
					query.setParameter("srNo", lv_sr_no);
					query.setParameter("fmCode", AppConstants.BookGrantConstant);

					int result = query.executeUpdate();
					System.out.println("Row affected ---- >  " + result);

					tx.commit();
					if (isUpdatingPage.equalsIgnoreCase("true")) {
						successMessage = "The reimbursements requests have been sucessfully Updated.";
					} else {
						successMessage = "The reimbursements requests have been sucessfully saved.";
					}

				} else {
					successMessage = errMsg;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return successMessage;
	}

	@Override
	public String getDependentRelationName(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Object object = null;
		String dependentName = "";
		try {

			Query query = session.createNativeQuery(" select VHED_DEPEND_RELATION FROM cadmin.V_HR_EMP_DEPENDENTS"
					+ " where VHED_EMP_CD= '" + reimbursementBean.getEmpCode() + "' " + "and VHED_DEPEND_SRNO = '"
					+ reimbursementBean.getDependentCode().charAt(0) + "' ");

			object = query.uniqueResult();
			if (object != null) {
				dependentName = (String) object;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return dependentName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public List<Object[]> getBookGrantData(String empCode) {
		Session session = sessionFactory.openSession();
		List<Object[]> finAssistData = new ArrayList<>();
		try {
			Query query = session.createNativeQuery("select AFA_EMP_CD,AFA_SR_NO,AFA_DEPEND_RELATION,AFA_CHILD_NAME,"
					+ "(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('1','2','3','4') and AQM_CD = AFA_COURSE_PASSED_CD),(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('1','2','3','4') and AQM_CD = AFA_COURSE_PURSUING_CD),AFA_PERCENTAGE_OBTAINED,to_char(AFA_SESSION_START_YR,'DD/MM/YYYY'),"
					+ "to_char(AFA_SESSION_END_YR,'DD/MM/YYYY'),to_char(AFA_CREATED_DT,'DD/MM/YYYY'),NVL(to_char(AFA_VERIFY_DT,'DD/MM/YYYY'),'Not Verified'),NVL(AFA_VERIFY_STATUS,'N'),AFA_YEAR,AFA_AMT,"
					+ "AFA_AFM_CD,nvl(AFA_FILE_NAME,'BookBill.pdf') from cadmin.ADM_FNCL_ASSIST where AFA_EMP_CD = '" + empCode
					+ "' and AFA_AFM_CD = '" + AppConstants.BookGrantConstant + "'");
			finAssistData = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return finAssistData;
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public String getPaymentValue(String coursePersued, String coursePassed, String scholCommonGrade,
			String courseStream, String startDate, String percentage, String scholarshipconstant, String boarder) {

		Session session = sessionFactory.openSession();
		String paymentValue = "";
		try {
			Query query = session.createNativeQuery(
					"SELECT Fa_Get_schol_book_Fncl_elg('" + scholarshipconstant + "'," + " '" + coursePersued + "', '"
							+ coursePassed + "', '" + scholCommonGrade + "', '" + courseStream + "', '" + percentage
							+ "', '" + boarder + "', 'Z', to_date('" + startDate + "','dd/MM/yyyy'))" + " from dual");
			paymentValue = String.valueOf(query.uniqueResult());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paymentValue;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public List<Object[]> getScholarshipSCSTData(String empCode) {
		Session session = sessionFactory.openSession();
		List<Object[]> scholarshipData = new ArrayList<>();
		try {
			Query query = session.createNativeQuery("select AFA_EMP_CD,AFA_SR_NO,AFA_DEPEND_RELATION,AFA_CHILD_NAME,"
					+ "(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('1','2','3','4') and AQM_CD = AFA_COURSE_PASSED_CD),(SELECT AQM_DESC FROM cadmin.ADM_QUAL_MASTER where AQM_CD in('1','2','3','4') and AQM_CD = AFA_COURSE_PURSUING_CD),AFA_PERCENTAGE_OBTAINED,to_char(AFA_SESSION_START_YR,'DD/MM/YYYY'),"
					+ "to_char(AFA_SESSION_END_YR,'DD/MM/YYYY'),to_char(AFA_CREATED_DT,'DD/MM/YYYY'),nvl(to_char(AFA_VERIFY_DT,'DD/MM/YYYY'),'Not Verified'),NVL(AFA_VERIFY_STATUS,'N'),AFA_YEAR,AFA_AMT,"
					+ "AFA_AFM_CD,nvl(AFA_FILE_NAME,'ScholarshipBill.pdf') from cadmin.ADM_FNCL_ASSIST where AFA_EMP_CD = '" + empCode
					+ "' and AFA_AFM_CD = '" + AppConstants.ScholarshipConstant + "'");
			scholarshipData = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return scholarshipData;
	}

	@Override
	public ReimbBookingHistory getScholarshipPreviousBooking(ReimbursementBean reimbursementBean) {

		ReimbBookingHistory bookingHistory = null;
		Session session = sessionFactory.openSession();
		List<Object[]> resultObject = null;
		try {
			Query query = session.createNativeQuery(
					" SELECT COUNT(*),AFA_AFM_CD,to_char(AFA_SESSION_START_YR,'dd/mm/yyyy'), to_char(AFA_SESSION_END_YR,'dd/mm/yyyy'),AQM_DESC,NVL(AFA_VERIFY_STATUS,'N') "
							+ "FROM cadmin.ADM_FNCL_ASSIST, cadmin.ADM_QUAL_MASTER " + "WHERE afa_emp_cd = '"
							+ reimbursementBean.getEmpCode() + "' " + " AND  afa_depend_srno = '"
							+ reimbursementBean.getDependentCode().charAt(0) + "' " + "AND AFA_AFM_CD IN('"
							+ AppConstants.ScholarshipConstant + "') " + "AND AFA_COURSE_PURSUING_CD= '"
							+ reimbursementBean.getCoursePersuedCode() + "' " + " AND AQM_CD= '"
							+ reimbursementBean.getCoursePersuedCode() + "' "
							+ "AND AFA_SESSION_END_YR >=(select max(AFA_SESSION_END_YR) from cadmin.ADM_FNCL_ASSIST "
							+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
							+ " AND   afa_depend_srno = '" + reimbursementBean.getDependentCode().charAt(0) + "' "
							+ "AND AFA_COURSE_PURSUING_CD='" + reimbursementBean.getCoursePersuedCode() + "') "
							+ " group by AFA_AFM_CD,AFA_SESSION_START_YR, AFA_SESSION_END_YR,AQM_DESC,AFA_VERIFY_STATUS");
			resultObject = query.list();
			if (!resultObject.isEmpty()) {
				for (Object[] obj : resultObject) {
					bookingHistory = new ReimbBookingHistory();
					if (obj[0] != null) {
						bookingHistory.setCount(Integer.parseInt(obj[0].toString()));
					}
					if (obj[1] != null) {
						bookingHistory.setFmCode(obj[1].toString());
					}
					if (obj[2] != null) {
						bookingHistory.setSessionStartDate(obj[2].toString());
					}
					if (obj[3] != null) {
						bookingHistory.setSessionEndDate(obj[3].toString());
					}
					if (obj[4] != null) {
						bookingHistory.setCourseDesc(obj[4].toString());
					}
					if (obj[5] != null) {
						bookingHistory.setStatus(obj[5].toString());
					}

				}
			} else {
				bookingHistory = new ReimbBookingHistory();
				bookingHistory.setCount(0);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bookingHistory;

	}

	@SuppressWarnings("deprecation")
	@Override
	public String getScholarshipYearCount(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		Object object = null;
		String yearCount = "";
		try {

			Query query = session.createNativeQuery("SELECT COUNT(*) FROM cadmin.ADM_FNCL_ASSIST "
					+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' " + " AND    afa_depend_srno ='"
					+ reimbursementBean.getDependentCode().charAt(0) + "' " + "AND AFA_COURSE_PURSUING_CD='"
					+ reimbursementBean.getCoursePersuedCode() + "' " + "AND AFA_AFM_CD IN('"
					+ AppConstants.ScholarshipConstant + "')");

			object = query.uniqueResult();
			if (object == null) {
				yearCount = "0";
			} else {
				yearCount = object.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return yearCount;

	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public String getScholarshipDuration(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		Object object = null;
		String duration = "";
		try {

			Query query = session.createNativeQuery("SELECT AQM_DURATION FROM cadmin.ADM_QUAL_MASTER"
					+ " WHERE AQM_CD ='" + reimbursementBean.getCoursePersuedCode() + "'");

			object = query.uniqueResult();
			if (object == null) {
				duration = "0";
			} else {
				duration = object.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return duration;

	}

	@Override
	public String getScholarShipFmName(String fmCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String saveScholarShipSCST(ReimbursementBean reimbursementBean) {

		String successMessage = "";
		String startYear = AppConstants.formatDate(reimbursementBean.getStartDate());
		String endYear = AppConstants.formatDate(reimbursementBean.getEndDate());
		Session session = sessionFactory.openSession();
		String isUpdatingPage = reimbursementBean.getIsUpdatingPage();
		if (isUpdatingPage == null) {
			isUpdatingPage = "";
		}
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall(
						"{ call cadmin.Ka_Reimbcomm.pa_scholarship_pmt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, AppConstants.ScholarshipConstant);
				stmt.setString(4, "Y");
				stmt.setString(5, reimbursementBean.getLv_sr_no());
				stmt.setString(6, reimbursementBean.getLv_sr_no());
				stmt.setString(7, "" + reimbursementBean.getDependentCode().charAt(0));
				stmt.setString(8,
						reimbursementBean.getDependentList().get("" + reimbursementBean.getDependentCode().charAt(0)));
				stmt.setString(9, reimbursementBean.getDependentRelation());
				stmt.setString(10, "" + reimbursementBean.getCoursePersuedCode());
				stmt.setString(11, "" + reimbursementBean.getCoursePassedCode());
				stmt.setString(12, reimbursementBean.getCourseStream());
				stmt.setString(13, reimbursementBean.getPercentage());
				stmt.setString(14, "Z");
				stmt.setString(15, reimbursementBean.getBoarder());
				stmt.setDate(16, new Date(AppConstants.parseDate(startYear).getTime()));
				stmt.setDate(17, new Date(AppConstants.parseDate(endYear).getTime()));
				stmt.setString(18, reimbursementBean.getPayment());

				stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(20, java.sql.Types.VARCHAR);

				stmt.execute();

				String errNo = (String) stmt.getString(19);
				String errMsg = (String) stmt.getString(20);

				if (errNo == null && errMsg == null) {
					String lv_sr_no = "";
					query = session.createNativeQuery(
							"select AFA_SR_NO from cadmin.ADM_FNCL_ASSIST WHERE AFA_SESSION_END_YR= (select max(AFA_SESSION_END_YR) from cadmin.ADM_FNCL_ASSIST "
									+ "WHERE afa_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
									+ " AND   afa_depend_srno = '" + reimbursementBean.getDependentCode().charAt(0)
									+ "' " + " AND  NVL(AFA_VERIFY_STATUS,'N')<>'C' " + "AND AFA_AFM_CD = '"
									+ AppConstants.BookGrantConstant + "')  AND afa_emp_cd = '"
									+ reimbursementBean.getEmpCode() + "' " + " AND   afa_depend_srno = '"
									+ reimbursementBean.getDependentCode().charAt(0) + "' " + " AND AFA_AFM_CD = '"
									+ AppConstants.ScholarshipConstant + "'");
					if (isUpdatingPage.equalsIgnoreCase("true")) {
						lv_sr_no = reimbursementBean.getLv_sr_no();
					} else {
						lv_sr_no = (String) query.uniqueResult();
					}

					byte[] bytes = reimbursementBean.getBillUpload().getBytes();
					SchoolScholarshipBillUpload billUpload = new SchoolScholarshipBillUpload();
					billUpload.setBillUpload(bytes);
					billUpload.setEmpCode(reimbursementBean.getEmpCode());
					billUpload.setSrNo(lv_sr_no);
					billUpload.setFmCode(AppConstants.ScholarshipConstant);
					billUpload.setRemark(reimbursementBean.getRemark());

					String hql = "UPDATE SchoolScholarshipBillUpload set billUpload = :billUpload, remark =:remark "
							+ "WHERE empCode = :empCode " + "and srNo =:srNo and fmCode =:fmCode";
					query = session.createQuery(hql);
					query.setParameter("billUpload", bytes);
					query.setParameter("remark", reimbursementBean.getRemark());
					query.setParameter("empCode", reimbursementBean.getEmpCode());
					query.setParameter("srNo", lv_sr_no);
					query.setParameter("fmCode", AppConstants.ScholarshipConstant);

					int result = query.executeUpdate();
					System.out.println("Row affected ---- >  " + result);

					tx.commit();
					if (isUpdatingPage.equalsIgnoreCase("true")) {
						successMessage = "The reimbursements requests have been sucessfully Updated.";
					} else {
						successMessage = "The reimbursements requests have been sucessfully saved.";
					}

				} else {
					successMessage = errMsg;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return successMessage;

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<FinancialAssistDataDomain> getFinancialAssistVerifyList(String empCode) {
		Session session = sessionFactory.openSession();
		List<FinancialAssistDataDomain> verificationBeanList = new ArrayList<FinancialAssistDataDomain>();

		try {
			List<Object[]> objectList = null;
			Query query = session.createQuery(
					"from FinancialAssistDataDomain" + " where empCode ='" + empCode + "' " + "and fmCode in ('"
							+ AppConstants.DependentConstant + "','" + AppConstants.BookGrantConstant + "', " + "'"
							+ AppConstants.ScholarshipConstant + "') " + "and nvl(verifyStatus,'N') not in('Y','R')");
			// query.setParameter("empCode", empCode);
			verificationBeanList = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return verificationBeanList;
	}

	@Override
	public int getApproveRejectReimBeans(String[] toApproveReimbList, boolean isRejecting) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int rowUpdated = 0;

		try {
			tx = session.beginTransaction();
			Query query = null;
			for (String approveData : toApproveReimbList) {
				String[] dataList = approveData.split("-");
				String pattern = "dd-MM-yyyy";
				String dateInString = new SimpleDateFormat(pattern).format(new java.util.Date());
				if (isRejecting) {
					query = session
							.createQuery("update FinancialAssistDataDomain set verifyStatus = 'R'," + "verifiedDate = '"
									+ dateInString + "' " + "where empCode = '" + dataList[0] + "' " + "and srNo = '"
									+ dataList[1] + "' " + "and fmCode = '" + dataList[2] + "' and dependentCode = '"
									+ dataList[4] + "' " + "and nvl(verifyStatus,'N') not in ('Y')");

				} else {
					query = session
							.createQuery("update FinancialAssistDataDomain set verifyStatus = 'Y'," + "verifiedDate = '"
									+ dateInString + "' " + "where empCode = '" + dataList[0] + "' " + "and srNo = '"
									+ dataList[1] + "' " + "and fmCode = '" + dataList[2] + "'  and dependentCode = '"
									+ dataList[4] + "' " + "and nvl(verifyStatus,'N') not in ('R')");
				}
				int rowCount = query.executeUpdate();
				rowUpdated += rowCount;
			}

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return rowUpdated;
	}

	@SuppressWarnings("unused")
	@Override
	public void saveLogAction(String moduleName, String message) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.Ka_Log.PA_LOG_ACTIONS(?,?) }");
				stmt.setString(1, moduleName);
				stmt.setString(2, message);
				stmt.execute();
				tx.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public EmployeeInfoBean getEmployeeInformation(String empCode) {

		Session session = sessionFactory.openSession();
		EmployeeInfoBean employeeInfoBean = null;

		List<Object[]> objectList;
		try {
			Query query = session.createNativeQuery(
					"select vhe_emp_grade, vhe_emp_join_dt,vhe_emp_retire_dt,vhe_resgn_dt,vhe_emp_desgn, vhe_class_code "
							+ "from cadmin.v_hr_emp where vhe_emp_cd= '" + empCode + "' ");
			objectList = query.list();

			if (objectList.size() > 1) {
				return employeeInfoBean;
			}

			if (!objectList.isEmpty()) {
				employeeInfoBean = new EmployeeInfoBean();
				for (Object[] object : objectList) {

					if (object[0] != null) {
						employeeInfoBean.setGrade(object[0].toString());

					}
					if (object[1] != null) {
						employeeInfoBean.setJoiningDate(object[1].toString());

					}
					if (object[2] != null) {
						employeeInfoBean.setRetireeDate(object[2].toString());

					}
					if (object[3] != null) {
						employeeInfoBean.setResignDate(object[3].toString());

					}
					if (object[4] != null) {
						employeeInfoBean.setDesignation(object[4].toString());

					}
					if (object[5] != null) {
						employeeInfoBean.setClassCode(object[5].toString());

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return employeeInfoBean;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getReimbursementFMCodeList() {

		Session session = sessionFactory.openSession();
		List<String> afmCodeList = new ArrayList<String>();

		try {
			Query query = session.createNativeQuery("");
			afmCodeList = (List<String>) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return afmCodeList;

	}

	@Override
	public String checkIfEmployeeActive(String empCode) {
		Session session = sessionFactory.openSession();
		String isActive = "";

		try {
			Query query = session.createNativeQuery("select 1 from cadmin.v_hr_emp_inactive where  "
					+ " vinc_emp_code='" + empCode + "' and vinc_from_date<=SYSDATE  AND "
					+ "(VINC_TO_DATE IS NULL OR VINC_TO_DATE>=SYSDATE) AND  VINC_ACTIVE_FLAG='Y'");
			isActive = (String) query.uniqueResult();

			if (isActive == null) {
				isActive = "Y";
			} else {
				isActive = "N";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return isActive;
	}

	@Override
	public String getPromotionDateInDeclaration(String empCode) {
		Session session = sessionFactory.openSession();
		String promotionDate = "";

		try {
			Query query = session.createNativeQuery(
					"select to_char(nvl(max(pr_perk_eff_dt),trunc(sysdate+1)),'dd/mm/yyyy') from cadmin.v_hr_promotion "
							+ " where  pr_emp_id='" + empCode + "' and pr_promo_type in ('P','L')");
			promotionDate = (String) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return promotionDate;
	}

	@Override
	public String getPromotionType(String empCode, String promotionDate) {
		Session session = sessionFactory.openSession();
		String promotionType = "";

		try {
			Query query = session.createNativeQuery(
					"select NVL(pr_promo_type,'N') from cadmin.v_hr_promotion where " + "pr_emp_id = '" + empCode
							+ "' and (pr_perk_eff_dt) = to_date('" + promotionDate + "','dd/mm/yyyy') ");
			promotionType = (String) query.uniqueResult();
			if(promotionType == null){
				promotionType = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return promotionType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReimbConsentDetailBean> getReimbConsentDetailBean(String empCode, String currentDate) {
		Session session = sessionFactory.openSession();
		List<ReimbConsentDetailBean> reimbConsentDetailBeanList = new ArrayList<>();

		List<Object[]> objectList;
		try {
			Query query = session.createNativeQuery(
					"SELECT aerc_emp_cd, aerc_afm_cd, aerc_sr_no, aerc_avl_option, aerc_created_dt,aerc_rvme_br_cd  "
							+ "FROM  cadmin.adm_emp_reimb_consent  "
							+ "WHERE (aerc_emp_cd, aerc_afm_cd, aerc_created_dt) "
							+ "IN ( SELECT aerc_emp_cd, aerc_afm_cd, max(aerc_created_dt) "
							+ "FROM   cadmin.adm_emp_reimb_consent " + "WHERE  aerc_emp_cd = '" + empCode + "' "
							+ "AND    aerc_created_dt >= ( "
							+ "SELECT decode(afm_cal_yr, 'C', to_date('01/01/'||to_char(sysdate,'yyyy'),'dd/MM/yyyy'), "
							+ " 'O', to_date('01/01/2016','dd/MM/yyyy'),"
							+ "'F', to_date('01/04/'||to_char(to_number(fa_get_fn_yr(sysdate) - 1)) , 'dd/MM/yyyy'), sysdate  ) first_dt "
							+ "FROM adm_facility_master  " + "WHERE afm_cd = aerc_afm_cd) "
							+ "GROUP BY aerc_emp_cd, aerc_afm_cd )  " + "ORDER BY aerc_afm_cd, aerc_created_dt");

			objectList = query.list();

			if (!objectList.isEmpty()) {

				for (Object[] object : objectList) {
					ReimbConsentDetailBean reimbConsentDetailBean = new ReimbConsentDetailBean();

					if (object[0] != null) {
						reimbConsentDetailBean.setEmpCode(object[0].toString());

					}
					if (object[1] != null) {
						reimbConsentDetailBean.setAfmCode(object[1].toString());

					}
					if (object[2] != null) {
						reimbConsentDetailBean.setSrNo(object[2].toString());

					}
					if (object[3] != null) {
						reimbConsentDetailBean.setAvailOption(object[3].toString());

					}
					if (object[4] != null) {
						reimbConsentDetailBean.setCreatedDate(object[4].toString());

					}
					if (object[5] != null) {
						reimbConsentDetailBean.setRvmeBrCode(object[5].toString());

					}
					reimbConsentDetailBeanList.add(reimbConsentDetailBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return reimbConsentDetailBeanList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeclarationReimbTelephoneBean getReimbTelephoneDetails(String empCode) {
		Session session = sessionFactory.openSession();
		DeclarationReimbTelephoneBean telephoneDetail = new DeclarationReimbTelephoneBean();
		List<Object[]> objectList;

		try {
			Query query = session
					.createNativeQuery(" SELECT atd_tel_no, atd_service_prov, to_char(atd_install_dt,'yyyy-mm-dd') "
							+ " FROM cadmin.adm_telephone_det WHERE atd_emp_cd = '" + empCode + "' "
							+ "AND atd_created_dt = (SELECT max(atd_created_dt) " + "FROM cadmin.adm_telephone_det "
							+ "WHERE atd_emp_cd = '" + empCode + "' and "
							+ " atd_created_dt>=to_date('01/04/'||to_char(to_number(fa_get_fn_yr(sysdate) - 1)) , 'dd/MM/yyyy'))");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						telephoneDetail.setTelephoneNo(object[0].toString());
					}
					if (object[1] != null) {
						telephoneDetail.setServiceProvider(object[1].toString());
					}
					if (object[2] != null) {
						telephoneDetail.setInstallationDate(object[2].toString());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return telephoneDetail;
	}

	@Override
	public boolean savedeclarationRegularClaimSubmit(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_np_submit(?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "521");
				stmt.setString(4, reimbursementBean.getIsNewsPaperAvailed());

				stmt.setString(5, reimbursementBean.getRegularClaimTermsAndCondition() == true ? "Y" : "N");

				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(6);
				String errMsg = (String) stmt.getString(7);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public String getFinStartEnd() {

		Session session = sessionFactory.openSession();
		String finStartEnd = "";
		try {

			Query query = session.createNativeQuery("select to_char(FA_GET_FIN_START_END,'dd/mm/yyyy') from dual");
			finStartEnd = (String) query.uniqueResult();
			return finStartEnd;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return finStartEnd;
	}

	@Override
	public boolean saveNewsPaperClaimSubmit(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_np_submit(?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "521");
				stmt.setString(4, reimbursementBean.getIsNewsPaperAvailed());

				stmt.setString(5, reimbursementBean.getRegularClaimTermsAndCondition() == true ? "Y" : "N");

				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(6);
				String errMsg = (String) stmt.getString(7);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveHouseHoldHelpClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_hh_submit(?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "525");
				stmt.setString(4, reimbursementBean.getIsHouseHoldHelpAvailed());

				stmt.setString(5, reimbursementBean.getRegularClaimTermsAndCondition() == true ? "Y" : "N");

				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(6);
				String errMsg = (String) stmt.getString(7);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveOfficialEntExpClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_oent_submit(?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "520");
				stmt.setString(4, reimbursementBean.getIsOfficialEntExpenceAvailed());

				// Will update Later for text Incharge
				stmt.setString(5, null);
				stmt.setString(6, reimbursementBean.getRegularClaimTermsAndCondition() ? "Y" : "N");

				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(8, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(7);
				String errMsg = (String) stmt.getString(8);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveWashingAllowanceClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_wa_submit(?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "540");
				stmt.setString(4, reimbursementBean.getIsWashingAllowanceAvailed());

				// Will update Later for text Incharge
				stmt.setString(5, null);
				stmt.setString(6, reimbursementBean.getRegularClaimTermsAndCondition() ? "Y" : "N");

				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(8, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(7);
				String errMsg = (String) stmt.getString(8);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveResidenceOfficeAllowance(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_roff_submit(?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "537");
				stmt.setString(4, reimbursementBean.getIsResidenceOfficeAllowanceAvailed());

				stmt.setString(5, reimbursementBean.getRegularClaimTermsAndCondition() == true ? "Y" : "N");

				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(6);
				String errMsg = (String) stmt.getString(7);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveCarCleaningClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_cc_submit(?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "544");
				stmt.setString(4, reimbursementBean.getIsCarCleaningAvailed());

				stmt.setString(5, reimbursementBean.getRegularClaimTermsAndCondition() ? "Y" : "N");

				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(6);
				String errMsg = (String) stmt.getString(7);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveTelephoneClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con
						.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_tel_submit(?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, reimbursementBean.getIsIncharge());
				stmt.setString(4, "514");
				stmt.setString(5, reimbursementBean.getIsTelephoneAvailed());
				stmt.setString(6, reimbursementBean.getTelephoneNo());
				stmt.setString(7, reimbursementBean.getServiceProvider());
				if (reimbursementBean.getTelephoneInstallationDate() != "") {
					stmt.setTimestamp(8,
							new Timestamp(AppConstants
									.parseDate(
											AppConstants.formatDate(reimbursementBean.getTelephoneInstallationDate()))
									.getTime()));
				} else {
					stmt.setTimestamp(8, new Timestamp(0));
				}
				stmt.setString(9, reimbursementBean.getRegularClaimTermsAndCondition1() ? "Y" : "N");

				stmt.registerOutParameter(10, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(11, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(10);
				String errMsg = (String) stmt.getString(11);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveDataChargesClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_dc_submit(?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());

				// Will update Later for text Incharge
				stmt.setString(3, null);
				stmt.setString(4, "558");
				stmt.setString(5, reimbursementBean.getIsDataChargesAvailed());

				stmt.setString(6, "Y");

				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(8, java.sql.Types.VARCHAR);

				success = stmt.execute();
				String errNo = (String) stmt.getString(7);
				String errMsg = (String) stmt.getString(8);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public NonPeriodicBriefcaseBean getPrevBriefCase(String empCode) {
		Session session = sessionFactory.openSession();
		NonPeriodicBriefcaseBean breifCaseBean = new NonPeriodicBriefcaseBean();
		List<Object[]> objectList;

		try {
			Query query = session.createNativeQuery(
					" select  to_number(trunc(sysdate)-ABR_PURCHASE_DT),to_char(ABR_PURCHASE_DT,'yyyy-mm-dd'), ABR_COST,"
							+ " ABR_REASONS, ABR_PAID_FLAG ,AERC_AVL_OPTION "
							+ " from    cadmin.adm_brfcase,cadmin.adm_emp_reimb_consent where abr_emp_cd='" + empCode
							+ "' and abr_emp_cd = aerc_emp_cd "
							+ "and abr_sr_no = aerc_sr_no and aerc_afm_cd='522' and  "
							+ "ABR_PURCHASE_DT = (SELECT MAX(ABR_PURCHASE_DT) FROM ADM_BRFCASE WHERE abr_emp_cd='"
							+ empCode + "') and AERC_CREATED_DT >= FA_GET_FIN_START_END");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						breifCaseBean.setBreifCaseDays(object[0].toString());
					}
					if (object[1] != null) {
						breifCaseBean.setABRPurchaseDate(object[1].toString());
					}
					if (object[2] != null) {
						breifCaseBean.setABRCost(object[2].toString());
					}
					if (object[3] != null) {
						breifCaseBean.setABRReason(object[3].toString());
					}
					if (object[4] != null) {
						breifCaseBean.setABRPaidFlag(object[4].toString());
					}
					if (object[5] != null) {
						breifCaseBean.setABRAvailOption(object[5].toString());
					}
				}
			} else {
				breifCaseBean.setBreifCaseDays("1200");
				breifCaseBean.setABRPaidFlag("N");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return breifCaseBean;
	}

	@Override
	public java.util.Date getBREligibilityDate(String empCode, String grade) {
		Session session = sessionFactory.openSession();
		String briefCaseEligibilityDate = "";
		java.util.Date date = null;

		try {
			Query query = session.createNativeQuery(
					"SELECT cadmin.fa_get_briefcase_elig_date(" + empCode + ",'" + grade + "') FROM dual");
			date = (java.util.Date) query.uniqueResult();
			// briefCaseEligibilityDate = AppConstants.parseDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return date;
	}

	@Override
	public String getMaxBRLimit(String grade) {
		Session session = sessionFactory.openSession();
		String maxBRLimit = "";

		try {
			Query query = session.createNativeQuery("SELECT AFS_MAX_LIMIT FROM cadmin.ADM_FACILITY_PARAM "
					+ "WHERE  AFS_AFM_CD='522' AND AFS_EMP_GR='" + grade + "' "
					+ " and AFS_EFF_DT = (SELECT MAX (AFS_EFF_DT) "
					+ "FROM cadmin.ADM_FACILITY_PARAM WHERE  AFS_AFM_CD='522' AND " + "AFS_EMP_GR='" + grade + "')");
			maxBRLimit = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxBRLimit;
	}

	@Override
	public String getNonPeriodBookClaimDate(String empCode) {
		Session session = sessionFactory.openSession();
		String bookClaimDate = "";

		try {
			Query query = session.createNativeQuery("select NVL(MAX(to_char(aerc_created_dt,'dd/mm/yyyy')),SYSDATE) "
					+ "from adm_emp_reimb_consent  " + " where aerc_emp_cd= '" + empCode + "' "
					+ "and  FA_GET_FN_YR(aerc_created_dt,4)=FA_GET_FN_YR(SYSDATE,4)  " + "and  aerc_afm_cd='543' ");
			bookClaimDate = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bookClaimDate;
	}

	@Override
	public NonPeriodicBookClaimBean getLastNonPeriodBookClaim(String empCode, String bookClaimDate) {
		Session session = sessionFactory.openSession();
		NonPeriodicBookClaimBean lastBookClaimDetail = new NonPeriodicBookClaimBean();
		List<Object[]> objectList;

		try {
			Query query = session.createNativeQuery(
					" SELECT  to_char(aerc_created_dt,'dd/mm/yyyy'),aerc_avl_option,nvl(ABG_PAID_FLAG,'N'),abg_cost,abg_remarks "
							+ " FROM   cadmin.adm_emp_reimb_consent, cadmin.adm_book_grant " + " WHERE abg_emp_cd='"
							+ empCode + "'  and abg_emp_cd = aerc_emp_cd and abg_sr_no = aerc_sr_no "
							+ "and aerc_afm_cd='543'  " + "and ABG_PURCHASE_DT = (SELECT MAX(ABG_PURCHASE_DT) FROM adm_book_grant WHERE abg_emp_cd='" + empCode + "') "
									+ " and ABG_SR_NO = (SELECT MAX(ABG_SR_NO) FROM adm_book_grant WHERE abg_emp_cd='" + empCode + "') ");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						lastBookClaimDetail.setLastBookClaimDate(object[0].toString());
					}
					if (object[1] != null) {
						lastBookClaimDetail.setLastClaimAvailed(object[1].toString());
					}
					if (object[2] != null) {
						lastBookClaimDetail.setLastBookGrantFlag(object[2].toString());
					}
					if (object[3] != null) {
						lastBookClaimDetail.setLastBookCost(object[3].toString());
					}
					if (object[4] != null) {
						lastBookClaimDetail.setLastBookRemark(object[4].toString());
					}

				}
			} else {
				lastBookClaimDetail.setLastBookGrantFlag("N");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lastBookClaimDetail;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getLVPPGradeForBookClaim(String grade) {
		Session session = sessionFactory.openSession();
		String lvPPGrade = "";

		try {
			Query query = session.createNativeQuery("SELECT APG_PP_GRADE FROM cadmin.ADM_PP_GRADE "
					+ "WHERE  APG_AFM_CD='543'  " + "and APG_GRADE='" + grade + "' ");
			lvPPGrade = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lvPPGrade;
	}

	@Override
	public String getNPMaxBookClaimAmount(String lvPPGrade) {
		Session session = sessionFactory.openSession();
		String maxBookClaimAmount = "";

		try {
			Query query = session.createNativeQuery("SELECT AFS_MAX_LIMIT FROM cadmin.ADM_FACILITY_PARAM  "
					+ " WHERE  AFS_AFM_CD='543'  " + "AND AFS_EMP_GR = '" + lvPPGrade + "' "
					+ "AND AFS_EFF_DT=(SELECT MAX(AFS_EFF_DT) FROM cadmin.ADM_FACILITY_PARAM WHERE  AFS_AFM_CD='543' AND AFS_EMP_GR = '"
					+ lvPPGrade + "')");
			maxBookClaimAmount = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxBookClaimAmount;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getPPABGPaidAmount(String empCode) {
		Session session = sessionFactory.openSession();
		String bookPaidAmountMap = "";

		try {
			Query query = session.createNativeQuery("select sum(ABG_PAID_AMOUNT), count(*) from cadmin.ADM_BOOK_GRANT"
					+ " where FA_GET_FN_YR(ABG_PAID_DT,4)=FA_GET_FN_YR(SYSDATE,4)  " + "and ABG_EMP_CD = '" + empCode
					+ "'  " + "and ABG_PAID_FLAG = 'Y'");
			List<Object[]> result = query.list();
			if (result != null) {
				for (Object[] object : result) {
					if (object[0] != null) {
						bookPaidAmountMap += object[0].toString();
					} else {
						bookPaidAmountMap += "0";
					}
					if (object[1] != null) {
						bookPaidAmountMap += "-" + object[1].toString();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bookPaidAmountMap;
	}

	@Override
	public boolean compareFinancialYear(String promotionDate) {
		Session session = sessionFactory.openSession();
		String result = "";

		try {
			Query query = session.createNativeQuery("select (case when FA_GET_FN_YR(to_date('" + promotionDate
					+ "','dd/mm/yyyy'),'4') = FA_GET_FN_YR(SYSDATE,'4') then 'true' " + " when FA_GET_FN_YR(to_date('"
					+ promotionDate + "','dd/mm/yyyy'),'4') <> FA_GET_FN_YR(SYSDATE,'4') then 'false'  "
					+ "end) from dual");
			result = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result.equals("true");
	}

	@Override
	public String getNPClaimDate(String empCode, String npCode) {
		Session session = sessionFactory.openSession();
		String claimDate = "";

		try {
			Query query = session.createNativeQuery("select NVL(MAX(to_char(aerc_created_dt,'dd/mm/yyyy')),SYSDATE) "
					+ "from adm_emp_reimb_consent  " + " where aerc_emp_cd= '" + empCode + "' "
					+ "and  FA_GET_FN_YR(aerc_created_dt,4)=FA_GET_FN_YR(SYSDATE,4)  " + "and  aerc_afm_cd='" + npCode
					+ "' ");
			claimDate = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return claimDate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getPreviousNpMedicalClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		List<Object[]> objectList;

		try {
			Query query = session.createNativeQuery(
					"SELECT  aerc_avl_option,nvl(amo_paid_flag,'N'),to_char(TRUNC(AMO_CLAIM_DATE),'dd/mm/yyyy'),nvl(AMO_CLAIM_AMOUNT,'0') "
							+ "FROM   cadmin.adm_emp_reimb_consent,cadmin.adm_medical_opt " + "WHERE amo_emp_cd='"
							+ reimbursementBean.getEmpCode() + "' " + "and amo_emp_cd = aerc_emp_cd "
							+ "and amo_sr_no = aerc_sr_no " + "and aerc_afm_cd='524' "
							+ "and FA_GET_FN_YR(aerc_created_dt,4)=FA_GET_FN_YR(SYSDATE,4) "
							+ "and AMO_CLAIM_DATE = (SELECT MAX(AMO_CLAIM_DATE) "
							+ "FROM ADM_MEDICAL_OPT WHERE amo_emp_cd='" + reimbursementBean.getEmpCode() + "')");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						reimbursementBean.setLastNpMedicalAvailed(object[0].toString());
					}
					if (object[1] != null) {
						reimbursementBean.setLastNpMedicalPaidFlag(object[1].toString());
					}
					if (object[2] != null) {
						reimbursementBean.setLastNpMedicalClaimDate(object[2].toString());
					}
					if (object[3] != null) {
						reimbursementBean.setLastNpMedicalAmountClaimed(object[3].toString());
					}

				}
			} else {
				reimbursementBean.setLastNpMedicalAvailed("N");
				reimbursementBean.setLastNpMedicalPaidFlag("N");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getNpMedicalOptClaimAmount(String empCode) {
		Session session = sessionFactory.openSession();
		String claimAmount = "";

		try {
			Query query = session.createNativeQuery("SELECT  nvl(sum(amo_claim_amount),'0') "
					+ "FROM  cadmin.adm_emp_reimb_consent,cadmin.adm_medical_opt  " + " where amo_emp_cd= '" + empCode
					+ "' and amo_emp_cd = aerc_emp_cd " + "and amo_sr_no = aerc_sr_no and aerc_afm_cd='524' and  "
					+ "FA_GET_FN_YR(aerc_created_dt,4)=FA_GET_FN_YR(SYSDATE,4) ");
			claimAmount = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return claimAmount;
	}

	@Override
	public String getMedicalLvPPGrade(String empCode) {
		Session session = sessionFactory.openSession();
		String grade = "";

		try {
			Query query = session.createNativeQuery(
					"select CADMIN.Ka_Reimburse_Post.Fa_Grade('" + empCode + "','501',SYSDATE)  from dual ");
			grade = query.uniqueResult().toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return grade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxNpMedicalLimit(String medicalLvPPGrade) {
		Session session = sessionFactory.openSession();
		String limit = "";

		try {
			Query query = session.createNativeQuery("SELECT AFS_MAX_LIMIT,to_char(AFS_EFF_DT,'dd/mm/yyyy') "
					+ "FROM cadmin.ADM_FACILITY_PARAM " + "WHERE  AFS_AFM_CD='524' " + "AND AFS_EMP_GR = '"
					+ medicalLvPPGrade + "' " + "AND AFS_EFF_DT=(SELECT MAX(AFS_EFF_DT) "
					+ "FROM cadmin.ADM_FACILITY_PARAM WHERE  AFS_AFM_CD='524' AND AFS_EMP_GR = '" + medicalLvPPGrade
					+ "')");
			List<Object[]> result = query.list();
			if (result != null) {
				for (Object[] object : result) {
					limit = object[0].toString();
					limit += "-" + object[1].toString();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return limit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNpMedicalPaidAmountAndCount(String empCode, String claimDate) {
		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		String result = "";

		try {
			Query query = session.createNativeQuery("select NVL(sum(AMO_PAID_AMOUNT),0), count(*)  "
					+ "from ADM_MEDICAL_OPT " + "where FA_GET_FN_YR(AMO_CLAIM_DATE,4)=FA_GET_FN_YR(SYSDATE,4) "
					+ "and AMO_EMP_CD = '" + empCode + "' " + "and AMO_PAID_FLAG = 'Y' ");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					result = object[0].toString();
					result += "-" + object[1].toString();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNpPaidUnpaidMedClaimAmount(String empCode, String lastNpMedicalClaimDate) {
		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		String result = "";

		try {
			Query query = session.createNativeQuery("select NVL(sum(AMO_PAID_AMOUNT),0), count(*)  "
					+ "from  cadmin.adm_medical_opt,cadmin.adm_emp_reimb_consent " + "where aerc_emp_cd=amo_emp_cd  "
					+ "AND aerc_sr_no=amo_sr_no " + "and amo_emp_cd='" + empCode + "' " + "and aerc_afm_cd='524' "
					+ "and  FA_GET_FN_YR(AMO_CLAIM_DATE,4)=FA_GET_FN_YR(SYSDATE,4)");

			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					result = object[0].toString();
					result += "-" + object[1].toString();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public RvmeBeanDomain getPreviousRvmeClaim(String empCode, String[] statusList) {
		Session session = sessionFactory.openSession();
		RvmeBeanDomain beans = null;
		List<String> list = Arrays.asList(statusList);

		try {
			Query query = session
					.createQuery("From RvmeBeanDomain rvme where rvme.empCode = '" + empCode + "' "
							+ "and rvme.verifyStatus in (:statusList) "
							+ "AND rvme.createdDate=(SELECT MAX(rvme2.createdDate) FROM RvmeBeanDomain rvme2 "
							+ "WHERE  rvme2.empCode ='" + empCode + "' and  verifyStatus in (:statusList) "
							+ " AND rvme2.createdDate>=to_date('01/04/'||to_char(to_number(fa_get_fn_yr(SYSDATE) - 1)),'dd/MM/yyyy'))")
					.setParameterList("statusList", list);

			beans = (RvmeBeanDomain) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return beans;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> getRvmePlaceOfUseMap() {
		Session session = sessionFactory.openSession();
		Map<String, String> rvmePlaceOfUseMap = new HashMap<>();
		try {

			Query query = session.createNativeQuery(
					" select BRANCH_CD,BRANCH_NAME from cadmin.bcm_branch_Code  WHERE STATUS <> 'C'");
			List<Object[]> list = query.list();
			for (Object[] obj : list) {
				if (obj[0] != null && obj[1] != null) {
					rvmePlaceOfUseMap.put(obj[0].toString(), obj[1].toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return rvmePlaceOfUseMap;
	}

	@Override
	public List<String> getAfsCodeList(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		List<String> afmCodes = new ArrayList<>();
		try {

			Query query = session.createNativeQuery(
					" SELECT distinct afs_afm_cd FROM cadmin.adm_facility_param, cadmin.adm_facility_master "
							+ "WHERE afm_cd = afs_afm_cd AND afm_type = 'R' " + "AND afm_reimb_type = 'D' AND "
							+ "afs_emp_gr IN ('" + reimbursementBean.getEmpGrade() + "', '"
							+ reimbursementBean.getCommonGrade() + "')");
			afmCodes = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return afmCodes;
	}

	@Override
	public String getPromotionEffectiveDate(String empCode) {

		Session session = sessionFactory.openSession();
		String promotionEffectiveDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(VHE_PROMO_EFF_DT,'dd/mm/yyyy') from cadmin.V_HR_EMP where VHE_EMP_CD= '" + empCode
							+ "'");
			promotionEffectiveDate = (String) query.uniqueResult();
			return promotionEffectiveDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return promotionEffectiveDate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getEmployeeGradeDetails(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"SELECT vhe_emp_grade, NVL(EP_FLAG,'N'), to_char(EP_DATE,'dd/mm/yyyy') ,NVL(VHE_PER_PROMO,'N'),vhe_emp_br_cd,VHE_EMP_DESGN,to_char(VHE_PROMO_EFF_DT,'dd/mm/yyyy') from cadmin.V_HR_EMP where VHE_EMP_CD= '"
							+ reimbursementBean.getEmpCode() + "'");
			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						reimbursementBean.setEmpGrade(object[0].toString());
					}
					if (object[1] != null) {
						reimbursementBean.setEpFlag(object[1].toString());
					}
					if (object[2] != null) {
						reimbursementBean.setEpDate(object[2].toString());
					}
					if (object[3] != null) {
						reimbursementBean.setVhePerPromo(object[3].toString());
					}
					if (object[4] != null) {
						reimbursementBean.setEmpBranchCode(object[4].toString());
					}
					if (object[5] != null) {
						reimbursementBean.setEmpDesignation(object[5].toString());
					}
					if (object[6] != null) {
						reimbursementBean.setPromoPerEffectiveDate(object[6].toString());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void getEmployeeDetForRVME(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"SELECT vhe_emp_grade, NVL(EP_FLAG,'N'), to_char(EP_DATE,'dd/mm/yyyy') ,NVL(VHE_PER_PROMO,'N') from cadmin.V_HR_EMP where VHE_EMP_CD= '"
							+ reimbursementBean.getEmpCode() + "'");
			objectList = query.list();

			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						reimbursementBean.setEmpGrade(object[0].toString());
					}
					if (object[1] != null) {
						reimbursementBean.setEpFlag(object[1].toString());
					}
					if (object[2] != null) {
						reimbursementBean.setEpDate(object[2].toString());
					}
					if (object[3] != null) {
						reimbursementBean.setVhePerPromo(object[3].toString());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean saveRvmeVehicleClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String validityDate = "";
		String shiftingDate = "";
		String acquiredOn = "";
		if(reimbursementBean.getIsRvmeVehicleClaim() != null){
			validityDate = AppConstants.formatDate(reimbursementBean.getRegvalidityDate());
			shiftingDate = AppConstants.formatDate(reimbursementBean.getShiftingDate());
			acquiredOn = AppConstants.formatDate(reimbursementBean.getRvmeVehicleAcquiredOn());
		}
		
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_rvme_submit(?,?,?,?,?,?,?,?,?,"
						+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());

				// Will update Later for text Incharge
				stmt.setString(3, reimbursementBean.getIsIncharge());
				stmt.setString(4, "501");
				stmt.setString(5, reimbursementBean.getIsRvmeVehicleClaim());

				stmt.setString(6, reimbursementBean.getRvmeVehicleType());
				stmt.setString(7, reimbursementBean.getRvmePlaceOfUse());
				stmt.setString(8, reimbursementBean.getResidingPlace());
				stmt.setString(9, reimbursementBean.getRvmeVehicleRegNumber());
				stmt.setString(10, reimbursementBean.getRvmeVehicleRegisteredAt());
				if(reimbursementBean.getIsRvmeVehicleClaim() != null){
					stmt.setDate(11, new Date(AppConstants.parseDate(acquiredOn).getTime()));
				} else {
					stmt.setDate(11, null);
				}
				
				stmt.setString(12, reimbursementBean.getIsVehicleProvidedByBank());
				stmt.setString(13, reimbursementBean.getRvmeEngineCapacity());
				stmt.setString(14, reimbursementBean.getChasisNumber());
				stmt.setString(15, reimbursementBean.getEngineNumber());
				if(reimbursementBean.getIsRvmeVehicleClaim() != null){
					stmt.setDate(16, new Date(AppConstants.parseDate(validityDate).getTime()));
				} else {
					stmt.setDate(16, null);
				}
				if(reimbursementBean.getIsRvmeVehicleClaim() != null){
					stmt.setDate(17, new Date(AppConstants.parseDate(shiftingDate).getTime()));
				} else {
					stmt.setDate(17, null);
				}
				stmt.setString(18, "502");
				stmt.setString(19, reimbursementBean.getIsRvmeNoVehicleClaim());
				stmt.setString(20, "503");
				stmt.setString(21, reimbursementBean.getIsRvmeDriverSalary());
				stmt.setString(22, reimbursementBean.getRvmeAgreement() ? "Y" : "N");

				stmt.registerOutParameter(23, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(24, java.sql.Types.VARCHAR);

				success = stmt.execute();
				errNo = (String) stmt.getString(23);
				String errMsg = (String) stmt.getString(24);

				if (errMsg != null || !errNo.equals("00000")) {
					System.out.println("Error ----- > " + errMsg);
				}
			}

			if (reimbursementBean.getIsRvmeVehicleClaim() != null && errNo.equals("00000")) {
				String srNo = session.createNativeQuery("select max(AVD_SR_NO) from cadmin.ADM_VEHICLE_DET where "
						+ "AVD_EMP_CD ='" + reimbursementBean.getEmpCode() + "'").uniqueResult().toString();

				byte[] bytes = reimbursementBean.getBillUpload().getBytes();
				RvmeBeanDomain rvmeDomain = new RvmeBeanDomain();
				rvmeDomain.setBillUpload(bytes);
				rvmeDomain.setFileName(reimbursementBean.getFileName());
				rvmeDomain.setSrNumber(srNo);

				String hql = "UPDATE RvmeBeanDomain set billUpload = :billUpload, fileName = :fileName "
						+ "WHERE empCode = :empCode " + "and srNumber = '" + srNo + "' ";
				query = session.createQuery(hql);
				query.setParameter("billUpload", bytes);
				query.setParameter("fileName", reimbursementBean.getFileName());
				query.setParameter("empCode", reimbursementBean.getEmpCode());

				int result = query.executeUpdate();
				System.out.println("Row affected ---- >  " + result);
				tx.commit();
				success = true;
			} 
			
			success = false;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public VehicleInsuranceBean getPreviousVehicleInsuranceBean(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		VehicleInsuranceBean bean = new VehicleInsuranceBean();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"SELECT AERC_AVL_OPTION, AVID_SR_NO, AVID_POLICY_NO, AVID_INSURED_BY,AVID_INSURANCE_TYPE,to_char(AVID_INSU_FROM_DT,'dd/mm/yyyy') AVID_INSU_FROM_DT, "
							+ "AVID_PREMIUM_AMT,to_char(avid_insu_expiry_dt,'dd/mm/yyyy') avid_insu_expiry_dt,to_char(avid_refund_dt,'dd/mm/yyyy') avid_refund_dt,to_char(avid_refund_verify_dt,'dd/mm/yyyy') avid_refund_verify_dt, "
							+ "avid_verify_status,avid_paid_status, avid_refund_status,avid_refund_verify_status,AVID_FILE_NAME,AVID_VERIFY_REMARKS "
							+ "FROM cadmin.ADM_VINSURE_DET,cadmin.ADM_EMP_REIMB_CONSENT " + "WHERE avid_emp_cd= '"
							+ reimbursementBean.getEmpCode() + "' and "
							+ "AVID_EMP_CD = AERC_EMP_CD AND AVID_SR_NO=AERC_SR_NO AND " + " AERC_AFM_CD='504' AND  "
							+ "(aerc_emp_grade  in ('A','B','C','D','E','F') or  NVL('"
							+ reimbursementBean.getNextPrevDesignation() + "','NA') = 'A3') and"
							+ " avid_created_dt in ( select max(avid_created_dt) "
							+ "FROM ADM_VINSURE_DET  where avid_emp_cd='" + reimbursementBean.getEmpCode() + "')"
							+ " and aerc_created_dt>=FA_GET_FIN_START_END");
			objectList = query.list();
			// Object object = query.uniqueResult();

			if (objectList != null) {
				for (Object[] obj : objectList) {
					if (obj[0] != null) {
						bean.setInsuranceAvailOption(obj[0].toString());
					}
					if (obj[1] != null) {
						bean.setSrNo(obj[1].toString());
					}
					if (obj[2] != null) {
						bean.setPolicyNumber(obj[2].toString());
					}
					if (obj[3] != null) {
						bean.setInsuredBy(obj[3].toString());
					}
					if (obj[4] != null) {
						bean.setInsuranceType(obj[4].toString());
					}
					if (obj[5] != null) {
						bean.setFromDate(obj[5].toString());
					}
					if (obj[6] != null) {
						bean.setPremiumAmount(obj[6].toString());
					}
					if (obj[7] != null) {
						bean.setExpiryDate(obj[7].toString());
					}
					if (obj[8] != null) {
						bean.setRefundDate(obj[8].toString());
					}
					if (obj[9] != null) {
						bean.setRefundVerifyDate(obj[9].toString());
					}
					if (obj[10] != null) {
						bean.setVerifyStatus(obj[10].toString());
					}
					if (obj[11] != null) {
						bean.setPaidStatus(obj[11].toString());
					}
					if (obj[12] != null) {
						bean.setRefundStatus(obj[12].toString());
					}
					if (obj[13] != null) {
						bean.setRefundVerifyStatus(obj[13].toString());
					}
					if (obj[14] != null) {
						bean.setFileName(obj[14].toString());
					}
					if (obj[15] != null) {
						bean.setRemark(obj[15].toString());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return bean;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getEmployeeRetireeDate(String empCode) {

		Session session = sessionFactory.openSession();
		String retireeDate = "";
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select to_char(VHE_EMP_RETIRE_DT,'dd/mm/yyyy'),to_char(VHE_RESGN_DT,'dd/mm/yyyy') from v_hr_emp where VHE_EMP_CD= '"
							+ empCode + "'");
			objectList = query.list();
			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						retireeDate += object[0].toString();
					}
					if (object[1] != null) {
						retireeDate += "-" + object[0].toString();
					} else {
						retireeDate += "-null";
					}
				}
			}
			return retireeDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return retireeDate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getPromotionDetailForVehicletInsurance(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String retireeDate = "";
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select to_char(pr_perk_eff_dt,'dd/mm/yyyy'),pr_nxt_dsg||pr_prev_dsg from cadmin.v_hr_promotion where pr_emp_id= '"
							+ reimbursementBean.getEmpCode()
							+ "' and pr_nxt_dsg||pr_prev_dsg in ('A3','AO','BM','AS','AP') and "
							+ "pr_perk_eff_dt=(select max (pr_perk_eff_dt) from cadmin.v_hr_promotion where "
							+ " pr_emp_id='" + reimbursementBean.getEmpCode() + "')");
			objectList = query.list();
			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						reimbursementBean.setPromoPerEffectiveDate(object[0].toString());
					} else {
						reimbursementBean.setPromoPerEffectiveDate("01/01/1900");
					}
					if (object[1] != null) {
						reimbursementBean.setNextPrevDesignation(object[1].toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getActiveInactiveCountForVI(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String count = "";
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select sum(decode(aers_flag,'I',1,0)), sum(decode(aers_flag,'Y',1,0)) from cadmin.adm_emp_rvme_schedule where AERS_EMP_CD = '"
							+ reimbursementBean.getEmpCode()
							+ "' and trunc(aers_month)>=TO_DATE('01/'||TO_CHAR(SYSDATE,'MM/YYYY'),'DD/MM/YYYY') and "
							+ "AERS_ELIG_VEH_TYPE IN ('FW','TW')");
			objectList = query.list();
			if (objectList != null) {
				for (Object[] object : objectList) {
					if (object[0] != null) {
						count += object[0].toString();
					} else {
						count += "1";
					}
					if (object[1] != null) {
						count += "-" + object[0].toString();
					} else {
						count += "-0";
					}
				}
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count;
	}

	@Override
	public String getLastInsuranceExpiryDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String expiryDate = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT to_char(nvl(trunc(avid_refund_dt),TRUNC(avid_insu_expiry_dt)),'dd/mm/yyyy') FROM ADM_VINSURE_DET WHERE "
							+ "avid_emp_cd = '" + reimbursementBean.getEmpCode() + "' and "
							+ "AVID_PAID_STATUS='Y' AND  avid_created_dt in ( select max(avid_created_dt) "
							+ " FROM ADM_VINSURE_DET  where avid_emp_cd = '" + reimbursementBean.getEmpCode()
							+ "'  AND AVID_PAID_STATUS='Y')");
			expiryDate = (String) query.uniqueResult();
			return expiryDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return expiryDate;
	}

	@Override
	public boolean saveVehicleInsuranceClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String expiryDate = AppConstants.formatDate(reimbursementBean.getNpInsuranceTill());
		String fromDate = AppConstants.formatDate(reimbursementBean.getNpInsuranceFrom());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_vehicle_insurance(?,?,?,?,"
						+ "?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "504");
				stmt.setString(4, reimbursementBean.getIsNpInsuranceClaimed());
				stmt.setString(5, reimbursementBean.getNpPolicyNumber());
				stmt.setString(6, reimbursementBean.getNpInsuranceType());
				stmt.setString(7, reimbursementBean.getNpInsuranceProviderName());
				stmt.setDate(8, new Date(AppConstants.parseDate(fromDate).getTime()));
				stmt.setDate(9, new Date(AppConstants.parseDate(expiryDate).getTime()));
				stmt.setString(10, reimbursementBean.getNpInsurancePremiumPaidt());
				stmt.setString(11, reimbursementBean.getInsuranceAgreement() ? "Y" : "N");

				stmt.registerOutParameter(12, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(13, java.sql.Types.VARCHAR);

				success = stmt.execute();
				errNo = (String) stmt.getString(12);
				String errMsg = (String) stmt.getString(13);

				/*
				 * if (errMsg != null || !errNo.equals("00000")) {
				 * System.out.println("Error ----- > " + errMsg); }
				 */

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				} else {
					reimbursementBean.setErrMsg(errMsg);
				}

			}

			if (errNo.equals("00000")) {
				// tx = session.beginTransaction();
				/*
				 * String srNo = session.
				 * createNativeQuery("select max(AVD_SR_NO) from cadmin.ADM_VEHICLE_DET where "
				 * + "AVD_EMP_CD ='" + reimbursementBean.getEmpCode() +
				 * "'").uniqueResult().toString();
				 * 
				 * byte[] bytes = reimbursementBean.getBillUpload().getBytes();
				 * RvmeBeanDomain rvmeDomain = new RvmeBeanDomain();
				 * rvmeDomain.setBillUpload(bytes);
				 * rvmeDomain.setFileName(reimbursementBean.getFileName());
				 * rvmeDomain.setSrNumber(srNo);
				 * 
				 * String hql =
				 * "UPDATE RvmeBeanDomain set billUpload = :billUpload, fileName = :fileName "
				 * + "WHERE empCode = :empCode " + "and srNumber = '" + srNo +
				 * "' "; query = session.createQuery(hql);
				 * query.setParameter("billUpload", bytes);
				 * query.setParameter("fileName",
				 * reimbursementBean.getFileName()); //
				 * query.setParameter("srNumber", srNo);
				 * query.setParameter("empCode",
				 * reimbursementBean.getEmpCode());
				 * 
				 * int result = query.executeUpdate();
				 * System.out.println("Row affected ---- >  " + result);
				 * 
				 * tx.commit(); success = true;
				 */
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean submitVehicleInsuranceRefund(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String refundDate = AppConstants.formatDate(reimbursementBean.getNpInsuranceRefundDate());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_vehicle_insurance_refund(?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpCode());
				stmt.setString(2, reimbursementBean.getNpInsuranceSrNo());
				stmt.setDate(3, new Date(AppConstants.parseDate(refundDate).getTime()));
				stmt.setInt(4, Integer.parseInt(reimbursementBean.getNpInsuranceRefundAmount()));

				stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(6, java.sql.Types.VARCHAR);

				success = stmt.execute();
				errNo = (String) stmt.getString(5);
				String errMsg = (String) stmt.getString(6);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public String getNpRefundPremAmount(ReimbursementBean reimbursementBean, VehicleInsuranceBean previousInsurance) {

		Session session = sessionFactory.openSession();
		BigDecimal refundAmount = null;
		try {

			Query query = session.createNativeQuery("SELECT ABS(TRUNC(((TRUNC(to_date('"
					+ previousInsurance.getExpiryDate() + "','dd/mm/yyyy'))-TRUNC(to_date('"
					+ reimbursementBean.getNpInsuranceRefundDate() + "','yyyy-mm-dd')))*"
					+ reimbursementBean.getNpInsuranceRefundAmount() + ")/365,00)) from dual");
			refundAmount = (BigDecimal) query.uniqueResult();
			return refundAmount.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return refundAmount.toString();
	}

	@Override
	public boolean saveNpBriefCaseClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String purchaseDate = AppConstants.formatDate(reimbursementBean.getBRPurchaseDate());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_briefcase(?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "522");
				stmt.setString(4, reimbursementBean.getBriefCaseReimb());
				stmt.setInt(5, Integer.parseInt(reimbursementBean.getBreifCaseCost()));
				stmt.setDate(6, new Date(AppConstants.parseDate(purchaseDate).getTime()));
				stmt.setString(7, reimbursementBean.getBRReason());
				stmt.setString(8, reimbursementBean.getBRTermsAndCondition() ? "Y" : "N");
				stmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(10, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(9);
				String errMsg = (String) stmt.getString(10);

				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveNpBookGrantClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_bookgrant(?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "543");
				stmt.setString(4, reimbursementBean.getIsNonPeriodBookAvailed());
				stmt.setInt(5, Integer.parseInt(reimbursementBean.getBookCost()));
				stmt.setString(6, reimbursementBean.getBookDetails());
				stmt.setString(7, reimbursementBean.getBookTermsAndCondition() ? "Y" : "N");
				stmt.registerOutParameter(8, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(8);
				String errMsg = (String) stmt.getString(9);
				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveNpMedicalOptClaim(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.KA_REIMBCOMM_JAVA.pa_consent_medopd(?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, "524");
				stmt.setString(4, reimbursementBean.getIsNpMedicalAvailed());
				stmt.setInt(5, Integer.parseInt(reimbursementBean.getNpMedicalAmountClaimed()));
				stmt.setString(6, reimbursementBean.getMedicalTermsAndCondition() ? "Y" : "N");
				stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(8, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(7);
				String errMsg = (String) stmt.getString(8);
				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public int getSBLPDMonth(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String count = "";
		BigDecimal result = null;
		try {

			Query query = session.createNativeQuery("SELECT cadmin.F_GET_SBL_PD_MTH('" + reimbursementBean.getEmpCode()
					+ "',to_date('" + reimbursementBean.getPaidDate() + "','dd/mm/yyyy'))  FROM DUAL");
			result = (BigDecimal) query.uniqueResult();
			if (result != null) {
				count = result.toString();
			} else {
				count = "0";
			}

			return Integer.parseInt(count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return Integer.parseInt(count);
	}

	@Override
	public String getApmEffectiveDate() {

		Session session = sessionFactory.openSession();
		String effectiveDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(apm_eff_dt,'dd/mm/yyyy') from adm_param where apm_rate_type='P'");
			effectiveDate = (String) query.uniqueResult();
			return effectiveDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return effectiveDate;
	}

	@Override
	public int getPostType(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal postType = null;
		try {

			Query query = session.createNativeQuery(
					"select COUNT(DISTINCT VHS_POST_TYPE) from cadmin.V_HR_EMP_SPLPOST,cadmin.V_HR_EMP "
							+ " where VHS_ACTIVE(+) ='Y' AND VHS_POST_TYPE(+)IN('I','E','R') "
							+ " and VHS_EMP_CODE(+)=VHE_EMP_CD and VHE_EMP_CD='" + reimbursementBean.getEmpCode()
							+ "'");
			postType = (BigDecimal) query.uniqueResult();
			return postType.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return postType.intValue();
	}

	@Override
	public String getMobileEmpGrade(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String mobileGrade = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT AERC_EMP_GRADE FROM cadmin.ADM_EMP_REIMB_CONSENT,cadmin.ADM_MOBILE_CLAIM "
							+ "WHERE AERC_EMP_CD=AMC_EMP_CD AND AERC_AFM_CD='541' " + "AND AERC_EMP_CD='"
							+ reimbursementBean.getEmpCode() + "' "
							+ "AND  AMC_SR_NO =AERC_SR_NO AND AERC_SR_NO=(  SELECT MAX(AMC_SR_NO) "
							+ "FROM cadmin.ADM_MOBILE_CLAIM  WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode()
							+ "' " + " and NVL(AMC_VERIFY,'N')='Y' and NVL(AMC_PAID,'N')='Y')");
			mobileGrade = (String) query.uniqueResult();
			return mobileGrade;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return mobileGrade;
	}

	@Override
	public int getMobileRefundCount(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal mobileRefundCount = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*) FROM cadmin.adm_mob_handset_recv "
					+ "WHERE AMHR_EMP_CD = '" + reimbursementBean.getEmpCode()
					+ "' and  AMHR_BILL_DT = ( SELECT MAX(trunc(AMC_BILL_DATE)) "
					+ "FROM ADM_MOBILE_CLAIM  WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode()
					+ "' and NVL(AMC_VERIFY,'N')='Y' " + "and NVL(AMC_PAID,'N')='Y')");
			mobileRefundCount = (BigDecimal) query.uniqueResult();
			return mobileRefundCount.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return mobileRefundCount.intValue();
	}

	@Override
	public String getMobilePaidDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String paidDate = "";
		try {

			Query query = session
					.createNativeQuery("SELECT to_char(TRUNC(amc_paid_date),'dd/mm/yyyy') FROM cadmin.ADM_MOBILE_CLAIM "
							+ "WHERE amc_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
							+ "AND  AMC_SR_NO IN(	SELECT MAX(AMC_SR_NO) FROM cadmin.ADM_MOBILE_CLAIM "
							+ "WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(AMC_VERIFY,'N')='Y' AND NVL(AMC_PAID,'N')='Y')");
			paidDate = (String) query.uniqueResult();
			return paidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paidDate;
	}

	@Override
	public void getPrevLaptopMobileClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"SELECT  to_char(NVL(AMC_BILL_DATE,AMC_CLAIM_DATE),'yyyy-mm-dd'),AERC_AVL_OPTION,AMC_CLAIM_AMT,AMC_CLAIM_DET,AMC_FILE_NAME,AMC_SR_NO,AMC_VERIFY,amc_paid,to_char(amc_paid_date,'yyyy-mm-dd'),AMC_REMARKS "
							+ "FROM ADM_EMP_REIMB_CONSENT,ADM_MOBILE_CLAIM WHERE AERC_EMP_CD=AMC_EMP_CD and  AERC_AFM_CD='541' "
							+ "and AERC_EMP_CD='" + reimbursementBean.getEmpCode()
							+ "' AND AERC_SR_NO = AMC_SR_NO  and AERC_SR_NO =( SELECT MAX(AMC_SR_NO) "
							+ " FROM ADM_MOBILE_CLAIM  WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode() + "') ");
			// + " and AERC_CREATED_DT>=FA_GET_FIN_START_END");
			objectList = query.list();
			// Object object = query.uniqueResult();

			if (objectList != null) {
				for (Object[] obj : objectList) {
					if (obj[0] != null) {
						previousBean.setMobileClaimDate(obj[0].toString());
					}
					if (obj[1] != null) {
						previousBean.setMobileClaimAvailed(obj[1].toString());
					}
					if (obj[2] != null) {
						previousBean.setMobileClaimAmount(obj[2].toString());
					}
					if (obj[3] != null) {
						previousBean.setMobileClaimDetail(obj[3].toString());
					}
					if (obj[4] != null) {
						previousBean.setMobileFileName(obj[4].toString());
					}
					if (obj[5] != null) {
						previousBean.setMobileSrNo(obj[5].toString());
					}
					if (obj[6] != null) {
						previousBean.setMobileVerifyStatus(obj[6].toString());
					}
					if (obj[7] != null) {
						previousBean.setMobilePaidStatus(obj[7].toString());
					}
					if (obj[8] != null) {
						previousBean.setMobilePaidDate(obj[8].toString());
					}
					if (obj[9] != null) {
						previousBean.setMobileRemark(obj[9].toString());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getLastMobileClaimPaidDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String claimPaidDate = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT to_char(max(trunc(NVL(AMC_BILL_DATE,AMC_CLAIM_DATE))),'dd/mm/yyyy') FROM ADM_MOBILE_CLAIM"
							+ " WHERE AMC_EMP_CD= '" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(AMC_VERIFY,'N')='Y' AND NVL(AMC_PAID,'N')='Y'");
			claimPaidDate = (String) query.uniqueResult();
			return claimPaidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return claimPaidDate;
	}

	@Override
	public String getMobileFinStartEnd(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String finStartEndDate = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT to_char(ADD_MONTHS(FA_GET_FIN_START_END(ADD_MONTHS(NVL(TRUNC(AMC_CLAIM_DATE),TRUNC(amc_paid_date)),0),'E') + 1,12),'dd/mm/yyyy')"
							+ " FROM cadmin.ADM_MOBILE_CLAIM WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode()
							+ "' " + "AND  AMC_SR_NO IN( SELECT MAX(AMC_SR_NO) FROM cadmin.ADM_MOBILE_CLAIM "
							+ " WHERE amc_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(AMC_VERIFY,'N')='Y'  AND NVL(AMC_PAID,'N')='Y') "
							+ "AND NVL(AMC_VERIFY,'N')='Y' " + "AND NVL(AMC_PAID,'N')='Y'");
			finStartEndDate = (String) query.uniqueResult();
			return finStartEndDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return finStartEndDate;
	}

	@Override
	public void getPreviousMobileChargesDetail(ReimbursementBean reimbursementBean,
			LaptopMobileClaimBean previousBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select AERC_AVL_OPTION,AMD_MOB_NO,AMD_SERVICE_PROV, to_char(AMD_INSTALL_DT,'yyyy-mm-dd') "
							+ "from cadmin.ADM_EMP_REIMB_CONSENT,cadmin.ADM_MOBILE_DET "
							+ "where AERC_EMP_CD=AMD_EMP_CD and	  AERC_SR_NO=AMD_SR_NO and AERC_AFM_CD='542'"
							+ " and	  AERC_EMP_CD='" + reimbursementBean.getEmpCode() + "' "
							+ "and	  sysdate  BETWEEN AERC_APPLICABLE_FROM AND AERC_APPLICABLE_TO "
							+ "and	  TRUNC(AMD_CREATED_DT)  BETWEEN AERC_APPLICABLE_FROM AND AERC_APPLICABLE_TO");
			// + " and AERC_CREATED_DT>=FA_GET_FIN_START_END");
			objectList = query.list();
			// Object object = query.uniqueResult();

			if (objectList != null) {
				for (Object[] obj : objectList) {
					if (obj[0] != null) {
						previousBean.setMobileChargesAvailed(obj[0].toString());
					}
					if (obj[1] != null) {
						previousBean.setMobileNumber(obj[1].toString());
					}
					if (obj[2] != null) {
						previousBean.setMobileServiceProvider(obj[2].toString());
					}
					if (obj[3] != null) {
						previousBean.setMobileChargeInstallationDate(obj[3].toString());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getLaptopBillOrClaimDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String billDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(NVL(trunc(ATL_BILL_DATE),trunc(ATL_CLAIM_DATE)),'yyyy-mm-dd') from ADM_LAPTOP_CLAIM "
							+ "where ATL_EMP_CD='" + reimbursementBean.getEmpCode()
							+ "' and trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE)) = (select max(trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE))) "
							+ "from ADM_LAPTOP_CLAIM where ATL_EMP_CD='" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(ATL_VERIFY,'N') = 'Y'  AND NVL(ATL_PAID,'N') = 'Y'	)");
			billDate = (String) query.uniqueResult();
			return billDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return billDate;
	}

	@Override
	public String getLaptopValidMonth(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal validMonth = null;
		try {

			Query query = session
					.createNativeQuery("select afs_sal_mnths from adm_facility_param  where AFS_AFM_CD='550' "
							+ " and  AFS_EMP_GR= '" + reimbursementBean.getEmpGrade() + "' "
							+ "and afs_eff_dt=(select max(afs_eff_dt) from adm_facility_param where AFS_AFM_CD='550' "
							+ "and  AFS_EMP_GR='" + reimbursementBean.getEmpGrade() + "')");
			validMonth = (BigDecimal) query.uniqueResult();
			if(validMonth != null){
				return validMonth.toString();
			} else {
				return "0";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return validMonth.toString();
	}

	@Override
	public String getLaptopSBLPDMonth(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal lapSBLPDMonth = null;
		try {

			Query query = session.createNativeQuery("SELECT cadmin.F_GET_SBL_PD_MTH('" + reimbursementBean.getEmpCode()
					+ "',to_date('" + reimbursementBean.getLaptopBillClaimDate() + "','yyyy-mm-dd'))  FROM DUAL");
			lapSBLPDMonth = (BigDecimal) query.uniqueResult();
			return String.valueOf(lapSBLPDMonth.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return String.valueOf(lapSBLPDMonth.intValue());

	}

	@Override
	public String getLaptopPreviousGrade(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String prevGrade = "";
		try {

			Query query = session.createNativeQuery("select AERC_EMP_GRADE from ADM_EMP_REIMB_CONSENT,ADM_LAPTOP_CLAIM "
					+ "where AERC_EMP_CD=ATL_EMP_CD and	  AERC_AFM_CD='550' " + "and AERC_EMP_CD='"
					+ reimbursementBean.getEmpCode() + "' "
					+ "and   AERC_SR_NO=ATL_SR_NO and trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE)) = (select max(trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE))) "
					+ "from ADM_LAPTOP_CLAIM  WHERE ATL_EMP_CD ='" + reimbursementBean.getEmpCode() + "' "
					+ " and NVL(ATL_VERIFY,'N') = 'Y' and NVL(ATL_PAID,'N')='Y')");
			prevGrade = (String) query.uniqueResult();
			return prevGrade;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return prevGrade;
	}

	@Override
	public void getPreviousLaptopClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select to_char(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE),'yyyy-mm-dd'),AERC_AVL_OPTION,ATL_CLAIM_AMT, ATL_CLAIM_DET,to_char(TRUNC(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE)),'yyyy-mm-dd'), "
							+ " ATL_FILE_NAME,ATL_SR_NO,ATL_VERIFY,atl_paid, to_char(atl_paid_date,'yyyy-mm-dd') from ADM_EMP_REIMB_CONSENT,ADM_LAPTOP_CLAIM "
							+ "where AERC_EMP_CD=ATL_EMP_CD and  AERC_AFM_CD='550' and   AERC_SR_NO=ATL_SR_NO"
							+ " and	  AERC_EMP_CD='" + reimbursementBean.getEmpCode() + "' "
							+ "and trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE)) = (select max(trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE))) "
							+ "from ADM_LAPTOP_CLAIM where ATL_EMP_CD='" + reimbursementBean.getEmpCode() + "')");
			// + " and AERC_CREATED_DT>=FA_GET_FIN_START_END");
			objectList = query.list();
			// Object object = query.uniqueResult();

			if (objectList != null) {
				for (Object[] obj : objectList) {
					if (obj[0] != null) {
						previousBean.setLaptopBillDate(obj[0].toString());
					}
					if (obj[1] != null) {
						previousBean.setLaptopAvailed(obj[1].toString());
					}
					if (obj[2] != null) {
						previousBean.setLaptopClaimAmount(obj[2].toString());
					}
					if (obj[3] != null) {
						previousBean.setLaptopDetail(obj[3].toString());
					}
					if (obj[4] != null) {
						previousBean.setLaptopClaimDate(obj[4].toString());
					}
					if (obj[5] != null) {
						previousBean.setLaptopFileName(obj[5].toString());
					}
					if (obj[6] != null) {
						previousBean.setLaptopSrNo(obj[6].toString());
					}
					if (obj[7] != null) {
						previousBean.setLaptopVerifyStatus(obj[7].toString());
					}
					if (obj[8] != null) {
						previousBean.setLaptopPaidStatus(obj[8].toString());
					}
					if (obj[9] != null) {
						previousBean.setLaptopPaidDate(obj[9].toString());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getNextLaptopEligibleDate(java.util.Date lastLaptopBillDate, String laptopValidMonths) {

		Session session = sessionFactory.openSession();
		String nextEligibleDate = "";
		try {

			String billDate = AppConstants.parseDate(lastLaptopBillDate);
			Query query = session.createNativeQuery("select to_char(add_months(to_date('" + billDate
					+ "','dd/mm/yyyy')," + Integer.parseInt(laptopValidMonths) + "),'dd/mm/yyyy') from dual");
			nextEligibleDate = (String) query.uniqueResult();
			return nextEligibleDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return nextEligibleDate;
	}

	@Override
	public String getMobileClaimLnDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String lnDate = "";
		try {

			Query query = session.createNativeQuery(
					" SELECT to_char(AERC_APPLICABLE_FROM,'dd/mm/yyyy') FROM cadmin.ADM_EMP_REIMB_CONSENT "
							+ "WHERE aerc_emp_cd = '" + reimbursementBean.getEmpCode() + "' AND aerc_afm_cd = '541' "
							+ "AND AERC_CREATED_DT >= Fa_Get_Fin_Start_End(SYSDATE,'S')");
			lnDate = (String) query.uniqueResult();
			return lnDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lnDate;
	}

	@Override
	public String getLapMobileLnDate(ReimbursementBean reimbursementBean, String afmCode) {

		Session session = sessionFactory.openSession();
		String lnDate = "";
		try {

			Query query = session.createNativeQuery(" SELECT AERC_APPLICABLE_FROM FROM cadmin.ADM_EMP_REIMB_CONSENT "
					+ "WHERE aerc_emp_cd = '" + reimbursementBean.getEmpCode() + "' AND aerc_afm_cd = '" + afmCode
					+ "' " + "AND AERC_CREATED_DT >= Fa_Get_Fin_Start_End(SYSDATE,'S')");
			lnDate = (String) query.uniqueResult();
			return lnDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lnDate;
	}

	@Override
	public String getMobileFinStartEndForSave(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String finStartEndDate = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT to_char(ADD_MONTHS(FA_GET_FIN_START_END(ADD_MONTHS(NVL(TRUNC(AMC_CLAIM_DATE),TRUNC(amc_paid_date)),"
							+ Integer.parseInt(reimbursementBean.getCountSBLPRDMonth()) + "),'E') + 1,12),'dd/mm/yyyy')"
							+ " FROM cadmin.ADM_MOBILE_CLAIM WHERE amc_emp_cd ='" + reimbursementBean.getEmpCode()
							+ "' " + "and  AMC_SR_NO IN(SELECT MAX(AMC_SR_NO) FROM ADM_MOBILE_CLAIM "
							+ " WHERE amc_emp_cd = '" + reimbursementBean.getEmpCode() + "') ");
			finStartEndDate = (String) query.uniqueResult();
			return finStartEndDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return finStartEndDate;
	}

	@Override
	public String getMaxSrNoForLapMobile(ReimbursementBean reimbursementBean, String afmCode) {

		Session session = sessionFactory.openSession();
		String maxSrNo = "";
		try {

			Query query = session
					.createNativeQuery("SELECT nvl(MAX(AERC_SR_NO),0) FROM ADM_EMP_REIMB_CONSENT " + "WHERE aerc_emp_cd = '"
							+ reimbursementBean.getEmpCode() + "' " + " AND aerc_afm_cd = '" + afmCode + "'");
			maxSrNo = (String) query.uniqueResult();

			if (reimbursementBean.getMobileChargesAgreement() && !reimbursementBean.getMobileAgreement()
					&& !reimbursementBean.getLaptopAgreement() && reimbursementBean.getMobLapReimCheck() > 0
					&& reimbursementBean.getMobLapDetCheck() == 0) {
				query = session.createNativeQuery("select lpad(to_char(nvl('" + maxSrNo + "',0)),3,'0') from dual");
			} else {
				query = session.createNativeQuery("select lpad(to_char(nvl('" + maxSrNo + "',0) + 1),3,'0') from dual");
			}

			maxSrNo = (String) query.uniqueResult();
			
			return maxSrNo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxSrNo;
	}

	@Override
	public int getMobileReimCheckCount(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*) from ADM_EMP_REIMB_CONSENT  "
					+ "WHERE aerc_emp_cd = '" + reimbursementBean.getEmpCode() + "' AND aerc_afm_cd = '541' "
					+ "AND  trunc(AERC_APPLICABLE_FROM) >= FA_GET_FIN_START_END(trunc(sysdate),'S') "
					+ " AND   trunc(AERC_APPLICABLE_FROM)<= ADD_MONTHS(FA_GET_FIN_START_END(ADD_MONTHS(TRUNC(sysdate),"
					+ Integer.parseInt(reimbursementBean.getCountSBLPRDMonth()) + "),'E') ,12) "
					+ "AND AERC_SR_NO = (SELECT MAX(AMC_SR_NO) FROM ADM_MOBILE_CLAIM WHERE amc_emp_cd ='"
					+ reimbursementBean.getEmpCode() + "')");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public int getMobileDetCheckCount(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*) from ADM_MOBILE_CLAIM  " + "WHERE AMC_EMP_CD = '"
					+ reimbursementBean.getEmpCode() + "' "
					+ "  AND trunc(NVL(AMC_BILL_DATE,AMC_CLAIM_DATE)) >= FA_GET_FIN_START_END(trunc(sysdate),'S') "
					+ "AND   trunc(NVL(AMC_BILL_DATE,AMC_CLAIM_DATE))<= ADD_MONTHS(FA_GET_FIN_START_END(ADD_MONTHS(TRUNC(sysdate),"
					+ Integer.parseInt(reimbursementBean.getCountSBLPRDMonth()) + "),'E') ,12) "
					+ " AND  AMC_SR_NO IN(SELECT MAX(AMC_SR_NO) FROM ADM_MOBILE_CLAIM  	WHERE amc_emp_cd ='"
					+ reimbursementBean.getEmpCode() + "')");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public boolean saveMobileClaim(MobileClaimDomain mobileBeanToSave, MobileClaimReimDomain mobileConsentBean) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(mobileBeanToSave);
			session.save(mobileConsentBean);
			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}

	@Override
	public String getFinStartDate() {

		Session session = sessionFactory.openSession();
		String date = null;
		try {

			Query query = session.createNativeQuery(
					"select to_char(FA_GET_FIN_START_END(trunc(sysdate),'S'),'dd/mm/yyyy') from Dual");
			date = (String) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return date;
	}

	@Override
	public String getAddMonths(int countSBLPRDMonth) {

		Session session = sessionFactory.openSession();
		String date = null;
		try {

			Query query = session
					.createNativeQuery("select to_char(ADD_MONTHS(FA_GET_FIN_START_END(ADD_MONTHS(TRUNC(sysdate),"
							+ countSBLPRDMonth + "),'E') ,12),'dd/mm/yyyy') from Dual");
			date = (String) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return date;
	}

	@Override
	public int getChargesReimCheck(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*) from ADM_EMP_REIMB_CONSENT "
					+ "where AERC_EMP_CD='" + reimbursementBean.getEmpCode() + "' " + "and	  AERC_AFM_CD='542' "
					+ "and	  TRUNC(SYSDATE) BETWEEN TRUNC(AERC_APPLICABLE_FROM) AND TRUNC(AERC_APPLICABLE_TO)");
			count = (BigDecimal) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count.intValue();
	}

	@Override
	public int getChargesDetCount(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*) from ADM_MOBILE_DET " + "where AMD_EMP_CD='"
					+ reimbursementBean.getEmpCode() + "' "
					+ "and trunc(AMD_CREATED_DT) BETWEEN Fa_Get_Fin_Start_End(SYSDATE,'S') AND Fa_Get_Fin_Start_End(SYSDATE,'E')");
			count = (BigDecimal) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count.intValue();
	}

	@Override
	public String getFinStartAndEndDate() {

		Session session = sessionFactory.openSession();
		String date = null;
		try {

			Query query = session.createNativeQuery(
					"select to_char(TRUNC(Fa_Get_Fin_Start_End(SYSDATE,'S')),'dd/mm/yyyy'),to_char(TRUNC(Fa_Get_Fin_Start_End(SYSDATE,'E')),'dd/mm/yyyy') from Dual");
			List<Object[]> result = query.list();
			if (result != null) {
				Object[] dateArray = result.get(0);
				date = dateArray[0].toString() + "-" + dateArray[1].toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return date;
	}

	@Override
	public boolean saveMobileChargesClaim(MobChargesDomain mobileCharge, MobileClaimReimDomain chargesConsentBean,
			int mobReimCheck) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(mobileCharge);
			if (mobReimCheck == 0) {
				session.save(chargesConsentBean);
			}

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}

	@Override
	public String getPrevEmpGrade(String empCode) {

		Session session = sessionFactory.openSession();
		String empGrade = "";
		try {

			Query query = session.createNativeQuery("select AERC_EMP_GRADE from ADM_EMP_REIMB_CONSENT,ADM_LAPTOP_CLAIM "
					+ "where AERC_EMP_CD=ATL_EMP_CD and	  AERC_AFM_CD='550' " + "and	  AERC_EMP_CD= '" + empCode
					+ "' and   AERC_APPLICABLE_FROM=ATL_CLAIM_DATE "
					+ "AND   ATL_BILL_DATE = (select max(ATL_BILL_DATE) from ADM_LAPTOP_CLAIM " + "where ATL_EMP_CD= '"
					+ empCode + "' and NVL(ATL_VERIFY,'N') = 'Y' " + "and NVL(ATL_PAID,'N')= 'Y' )");
			empGrade = (String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return empGrade;
	}

	@Override
	public int getLaptopReimCheck(String empCode, String month) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*)  from ADM_EMP_REIMB_CONSENT "
					+ "where AERC_EMP_CD= '" + empCode + "' AND aerc_afm_cd = '550' "
					+ "and	 add_months(trunc(AERC_APPLICABLE_FROM),'" + month + "') >sysdate");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public int getLaptopDetCheck(String empCode, String laptopValidMonths) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*)  from ADM_LAPTOP_CLAIM " + "where ATL_EMP_CD= '"
					+ empCode + "' " + "and add_months(trunc(NVL(ATL_BILL_DATE,ATL_CLAIM_DATE)),'" + laptopValidMonths
					+ "') >sysdate");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public int getLaptopCount(String empCode) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery(
					"SELECT count(*)  from ADM_LAPTOP_CLAIM " + "where ATL_EMP_CD= '" + empCode + "' ");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public String getLaptopValidDate(String parseDate, String laptopValidMonths) {

		Session session = sessionFactory.openSession();
		String date = null;
		try {

			Query query = session.createNativeQuery("select to_char(add_months(trunc(to_date('" + parseDate
					+ "','dd/mm/yyyy'))," + laptopValidMonths + ")-1,'dd/mm/yyyy') from Dual");
			date = (String) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return date;
	}

	@Override
	public boolean saveLaptopClaim(LaptopClaimDomain lapClaim, MobileClaimReimDomain laptopConsentBean) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(lapClaim);
			session.save(laptopConsentBean);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}

	@Override
	public String getMaxSrNo(ReimbursementBean reimbursementBean, String afmCode) {

		Session session = sessionFactory.openSession();
		String maxSrNo = "";
		try {

			Query query = session
					.createNativeQuery("SELECT MAX(AERC_SR_NO) FROM ADM_EMP_REIMB_CONSENT " + "WHERE aerc_emp_cd = '"
							+ reimbursementBean.getEmpCode() + "' " + " AND aerc_afm_cd = '" + afmCode + "'");
			maxSrNo = (String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxSrNo;
	}

	@Override
	public String getMaxLapSrNo(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String maxSrNo = "";
		try {

			Query query = session.createNativeQuery("SELECT MAX(ATL_SR_NO) FROM ADM_LAPTOP_CLAIM "
					+ "WHERE aerc_emp_cd = '" + reimbursementBean.getEmpCode() + "'");
			maxSrNo = (String) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxSrNo;
	}

	@Override
	public boolean updateLaptopReim(LaptopClaimDomain lapClaim, ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		int update = 0;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String hql = "UPDATE LaptopClaimDomain set claimDate = :claimDate, claimAmount =:claimAmount "
					+ "claimDetail =:claimDetail, billDate =:billDate, verifyStatus =:verifyStatus"
					+ "WHERE empCode = :empCode " + "and srNo =:srNo and verifyStatus =:verifyStatus";
			Query query = session.createQuery(hql);
			query.setParameter("claimDate", lapClaim.getClaimDate());
			query.setParameter("claimAmount", lapClaim.getClaimAmount());
			query.setParameter("claimDetail", lapClaim.getClaimDetail());
			query.setParameter("billDate", lapClaim.getBillDate());
			query.setParameter("verifyStatus", lapClaim.getVerifyStatus());
			query.setParameter("empCode", lapClaim.getEmpCode());
			query.setParameter("srNo", lapClaim.getSrNo());
			update = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (update > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String getAfsSalMonth(ReimbursementBean reimbursementBean, String fmCode) {

		Session session = sessionFactory.openSession();
		BigDecimal validMonth = null;
		try {

			Query query = session.createNativeQuery("select afs_sal_mnths from adm_facility_param  where AFS_AFM_CD='"
					+ fmCode + "' " + " and  AFS_EMP_GR= '" + reimbursementBean.getEmpGrade() + "' "
					+ "and afs_eff_dt=(select max(afs_eff_dt) from adm_facility_param where AFS_AFM_CD='" + fmCode
					+ "' " + "and  AFS_EMP_GR='" + reimbursementBean.getEmpGrade() + "')");
			validMonth = (BigDecimal) query.uniqueResult();
			if (validMonth != null) {
				return validMonth.toString();
			} else {
				return "0";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return validMonth.toString();
	}

	@Override
	public String getLastTabPaidDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String billDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(NVL(trunc(ATC_CLAIM_DATE),trunc(ATC_PAID_DATE)),'yyyy-mm-dd') from ADM_TAB_CLAIM "
							+ "where atc_emp_cd='" + reimbursementBean.getEmpCode() + "' and "
							+ " ATC_SR_NO =  (	SELECT MAX(ATC_SR_NO) FROM ADM_TAB_CLAIM " + "WHERE atc_emp_cd ='"
							+ reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(ATC_VERIFY,'N') = 'Y'  AND NVL(ATC_PAID,'N') = 'Y'	)");
			billDate = (String) query.uniqueResult();
			return billDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return billDate;
	}

	@Override
	public void getLastTabletClaimDetails(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		List<Object[]> objectList;
		try {

			Query query = session.createNativeQuery(
					"select to_char(ATC_CLAIM_DATE,'yyyy-mm-dd'),AERC_AVL_OPTION,ATC_CLAIM_AMT, ATC_CLAIM_DET,ATC_FILE_NAME, "
							+ " ATC_SR_NO,ATC_VERIFY,atc_paid,to_char(atc_paid_date,'yyyy-mm-dd') from ADM_EMP_REIMB_CONSENT,ADM_TAB_CLAIM "
							+ "where AERC_EMP_CD=ATC_EMP_CD and  AERC_AFM_CD='548' and   AERC_SR_NO=ATC_SR_NO"
							+ " and	  AERC_EMP_CD='" + reimbursementBean.getEmpCode() + "' "
							+ "AND AERC_SR_NO =( SELECT MAX(ATC_SR_NO) FROM ADM_TAB_CLAIM  " + "WHERE atc_emp_cd ='"
							+ reimbursementBean.getEmpCode() + "')");
			objectList = query.list();
			if (objectList != null) {
				for (Object[] obj : objectList) {
					if (obj[0] != null) {
						reimbursementBean.setTabClaimDate(obj[0].toString());
					}
					if (obj[1] != null) {
						reimbursementBean.setIsTabClaimed(obj[1].toString());
					}
					if (obj[2] != null) {
						reimbursementBean.setTabClaimAmount(obj[2].toString());
					}
					if (obj[3] != null) {
						reimbursementBean.setTabDetail(obj[3].toString());
					}
					if (obj[4] != null) {
						reimbursementBean.setFileName(obj[4].toString());
					}
					if (obj[5] != null) {
						reimbursementBean.setSrNo(obj[5].toString());
					}
					if (obj[6] != null) {
						reimbursementBean.setIsVerified(obj[6].toString());
					}
					if (obj[7] != null) {
						reimbursementBean.setIsPaid(obj[7].toString());
					}
					if (obj[8] != null) {
						reimbursementBean.setPaidDate(obj[8].toString());
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getLastTabPaidClaimDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String paidDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(max(trunc(NVL(ATC_BILL_DATE,ATC_CLAIM_DATE))),'dd/mm/yyyy') from ADM_TAB_CLAIM "
							+ "where atc_emp_cd='" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(ATC_VERIFY,'N') = 'Y'  AND NVL(ATC_PAID,'N') = 'Y'");
			paidDate = (String) query.uniqueResult();
			return paidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paidDate;
	}

	@Override
	public String getLastTabClaimDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String paidDate = "";
		try {

			Query query = session.createNativeQuery(
					"select to_char(max(trunc(NVL(ATC_BILL_DATE,ATC_CLAIM_DATE))),'dd/mm/yyyy') from ADM_TAB_CLAIM "
							+ "where atc_emp_cd='" + reimbursementBean.getEmpCode() + "' "
							+ "AND NVL(ATC_VERIFY,'N') = 'Y'  AND NVL(ATC_PAID,'N') = 'Y'");
			paidDate = (String) query.uniqueResult();
			return paidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paidDate;
	}

	@Override
	public String getTabPayDate(ReimbursementBean reimbursementBean, String validMonth) {

		Session session = sessionFactory.openSession();
		String paidDate = "";
		try {

			Query query = session.createNativeQuery(
					"SELECT to_char(TRUNC(ATC_CLAIM_DATE),'dd/mm/yyyy') FROM ADM_PYMT_DTLS, ADM_TAB_CLAIM "
							+ "WHERE apd_afm_cd = '548' AND apd_emp_cd = '" + reimbursementBean.getEmpCode() + "' "
							+ "AND ATC_REQ_ID = APD_REQ_ID AND APD_STATUS = 'AD' "
							+ "and	  add_months(trunc(ATC_CLAIM_DATE)," + validMonth + ") > trunc(sysdate)");
			paidDate = (String) query.uniqueResult();
			return paidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paidDate;
	}

	@Override
	public String getTabApplicableDate(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String paidDate = "";
		try {

			Query query = session
					.createNativeQuery("SELECT to_char(AERC_APPLICABLE_FROM,'dd/mm/yyyy') FROM ADM_EMP_REIMB_CONSENT "
							+ "WHERE aerc_emp_cd = '" + reimbursementBean.getEmpCode() + "' AND aerc_afm_cd = '548' "
							+ "AND AERC_CREATED_DT >= Fa_Get_Fin_Start_End(SYSDATE,'S')");
			paidDate = (String) query.uniqueResult();
			return paidDate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return paidDate;
	}

	@Override
	public String getMaxSrNoFromConsent(ReimbursementBean reimbursementBean, String fmCode) {

		Session session = sessionFactory.openSession();
		String maxSrNo = "";
		try {
			Query query = session
					.createNativeQuery("SELECT NVL(MAX(AERC_SR_NO),0) FROM ADM_EMP_REIMB_CONSENT " + "WHERE aerc_emp_cd = '"
							+ reimbursementBean.getEmpCode() + "' " + " AND aerc_afm_cd = '" + fmCode + "'");
			maxSrNo = (String) query.uniqueResult();
			query = session.createNativeQuery("select lpad(to_char(nvl('" + maxSrNo + "',0) + 1),3,'0') from dual");
			maxSrNo = (String) query.uniqueResult();
			return maxSrNo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxSrNo;
	}

	@Override
	public int getTabReimCheck(ReimbursementBean reimbursementBean, String fmCode, int validMonthTab) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*)  from ADM_EMP_REIMB_CONSENT "
					+ "where AERC_EMP_CD= '" + reimbursementBean.getEmpCode() + "' AND aerc_afm_cd = '" + fmCode + "' "
					+ "and	 add_months(trunc(AERC_APPLICABLE_FROM),'" + validMonthTab + "') >sysdate");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public int getTabDetCheck(ReimbursementBean reimbursementBean, int validMonthTab) {

		Session session = sessionFactory.openSession();
		BigDecimal count = null;
		try {

			Query query = session.createNativeQuery("SELECT count(*)  from ADM_TAB_CLAIM " + "where ATC_EMP_CD= '"
					+ reimbursementBean.getEmpCode() + "' "
					+ "and	  add_months(trunc(NVL(ATC_BILL_DATE,ATC_CLAIM_DATE))," + validMonthTab + ") > sysdate");
			count = (BigDecimal) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return count.intValue();
	}

	@Override
	public boolean saveTabClaim(TabletClaimDomain tabBean, ReimConsentDomain consentBean) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(tabBean);
			session.save(consentBean);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}

	@Override
	public String getMaxSrNoInTabClaim(String empCode) {

		Session session = sessionFactory.openSession();
		String maxSrNo = "";
		try {
			Query query = session.createNativeQuery(
					"SELECT MAX(ATC_SR_NO) FROM ADM_TAB_CLAIM " + "WHERE ATC_EMP_CD = '" + empCode + "'");
			maxSrNo = (String) query.uniqueResult();
			return maxSrNo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return maxSrNo;
	}

	@Override
	public boolean updateTabClaim(ReimbursementBean reimbursementBean, TabletClaimDomain updateBean) {

		Session session = sessionFactory.openSession();
		int update = 0;
		Transaction tx = null;
		java.util.Date today = new java.util.Date();
		today = new java.util.Date(today.getTime());

		try {
			tx = session.beginTransaction();
			String billDateInString = AppConstants.formatDate(reimbursementBean.getTabClaimDate());
			java.util.Date utilBillDate = AppConstants.parseDate(billDateInString);
			String hql = "UPDATE TabletClaimDomain set claimDate = :claimDate, claimAmount =:claimAmount "
					+ "claimDetail =:claimDetail, billDate =:billDate, verifyStatus =:verifyStatus"
					+ "WHERE empCode = :empCode " + "and srNo =:srNo and verifyStatus =:verifyStatus";
			Query query = session.createQuery(hql);
			query.setParameter("claimDate", new Date(today.getTime()));
			query.setParameter("claimAmount", reimbursementBean.getTabClaimAmount());
			query.setParameter("claimDetail", reimbursementBean.getTabDetail());
			query.setParameter("billDate", new Date(utilBillDate.getTime()));
			query.setParameter("verifyStatus", reimbursementBean.getIsVerified());
			query.setParameter("empCode", reimbursementBean.getEmpCode());
			query.setParameter("srNo", reimbursementBean.getSrNo());
			update = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (update > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String[] getTransferValidationFlagPart1(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String empCode = reimbursementBean.getEmpCode();
		String[] stringArray = null;
		String result = "";
		try {
			Query query = session.createNativeQuery(
					"select NVL(ETB_INCIDENTAL_EXP,'null'), nvl(ETB_TRANSPORT_EXP,'null'),ETB_TRX_NO, "
							+ "decode(ETB_INCIDENTAL_EXP,'N','Normal Transfer','Normal Transfer'), "
							+ "decode(ETB_INCIDENTAL_EXP,'N','R','N') "
							+ "from cadmin.V_HR_EMP_TRNSF_BENEFIT where ETB_EMP= '" + empCode + "' "
							+ "and ETB_REPORTING_DATE= (select max(ETB_REPORTING_DATE) "
							+ "from cadmin.V_HR_EMP_TRNSF_BENEFIT where ETB_EMP='" + empCode + "')");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						result += row[0].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[1] != null) {
						result += row[1].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[2] != null) {
						result += row[2].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[3] != null) {
						result += row[3].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[4] != null) {
						result += row[4].toString();
					} else {
						result += "null";
					}
				}
			}
			stringArray = result.split("-");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return stringArray;
	}

	@Override
	public boolean getIncidentalDetail(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		String[] stringArray = null;
		String result = "";
		try {
			Query query = session.createNativeQuery(
					"select ATI_PMT_MODE,ATI_FAMILY_SHIFT_FLG, to_char(ATI_FAMILY_SHIFT_DT,'dd-mm-yyyy') "
							+ "from ADM_TRF_BENIFIT_INC_DTL " + "where ATI_EMP_CD= '" + reimbursementBean.getEmpCode()
							+ "' and LTRIM(RTRIM(ATI_TRF_ID))=LTRIM(RTRIM('" + reimbursementBean.getTrfId() + "'))");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						reimbursementBean.setIncidentalPaymentMode(row[0].toString());
					}
					if (row[1] != null) {
						reimbursementBean.setIncidentalFamilyShiftFalg(row[1].toString());
					}
					if (row[2] != null) {
						reimbursementBean.setIncidentalFamilyShiftDate(row[2].toString());
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return true;
	}

	@Override
	public String[] getTransportExpenseDetail(String empCode, TransportExpenseDetailBean transportDetBean) {
		Session session = sessionFactory.openSession();
		String[] stringArray = null;
		String result = "";
		try {
			Query query = session.createNativeQuery(
					"select ATE_SR_NO,ATE_VERIFY_STATUS,NVL(ATE_PAID_FLAG,'N'),ATE_FAMILY_FLAG, to_char(ATE_FAMILY_SHIFT_DATE,'dd/mm/yyyy'),ATE_GOODS_WEIGHT, "
							+ "ATE_BIKE_FLAG,ATE_BIKE_TRP_COST,ATE_HLD_TRP_COST,ATE_TRP_DECL_AMT,ATE_TOT_TRP_COST "
							+ ",AERC_AGREEMENT,NVL(ATE_DORMATORY,'N'),ATE_FILE_NAME,ATE_REMARKS, "
							+ "ATE_DIST_KM,ATE_FRIGHT_CHG,ATE_HLD_TRP_COST+ATE_TRP_DECL_AMT,ATE_DIST_KM*ATE_FRIGHT_CHG "
							+ "from ADM_TRF_TRANSPORT_EXP,ADM_EMP_REIMB_CONSENT "
							+ " where ATE_EMP_CD=AERC_EMP_CD AND AERC_EMP_CD= '" + empCode
							+ "' AND ATE_SR_NO=AERC_SR_NO " + "and ATE_TRF_ID='" + transportDetBean.getTrfId()
							+ "' and AERC_AFM_CD='546'");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						transportDetBean.setSrNo(row[0].toString());
					}
					if (row[1] != null) {
						transportDetBean.setVerifyStatus(row[1].toString());
					}
					if (row[2] != null) {
						transportDetBean.setPaidStatus(row[2].toString());
					}
					if (row[3] != null) {
						transportDetBean.setFamilyFlag(row[3].toString());
					}
					if (row[4] != null) {
						transportDetBean.setFamilyShiftDate(row[4].toString());
					}
					if (row[5] != null) {
						transportDetBean.setGoodsWeight(row[5].toString());
					}
					if (row[6] != null) {
						transportDetBean.setBikeFlag(row[6].toString());
					}
					if (row[7] != null) {
						transportDetBean.setBikeTripCost(row[7].toString());
					}
					if (row[8] != null) {
						transportDetBean.setHldTripCost(row[8].toString());
					}
					if (row[9] != null) {
						transportDetBean.setTripDecAmount(row[9].toString());
					}
					if (row[10] != null) {
						transportDetBean.setTotalTripCost(row[10].toString());
					}
					if (row[11] != null) {
						transportDetBean.setAgreement(row[11].toString());
					}
					if (row[12] != null) {
						transportDetBean.setDormatory(row[12].toString());
					}
					if (row[13] != null) {
						transportDetBean.setFileName(row[13].toString());
					}
					if (row[14] != null) {
						transportDetBean.setRemark(row[14].toString());
					}
					if (row[15] != null) {
						transportDetBean.setDistanceInKm(row[15].toString());
					}
					if (row[16] != null) {
						transportDetBean.setFamilyShiftDate(row[16].toString());
					}
					if (row[17] != null) {
						transportDetBean.setFrightCharge(row[17].toString());
					}
					if (row[18] != null) {
						transportDetBean.setTransportTotalAmount(row[18].toString());
					}
					if (row[19] != null) {
						transportDetBean.setTotalFrightCharge(row[19].toString());
					}
				}
			}
			stringArray = result.split("-");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return stringArray;
	}

	@Override
	public void getCarExpenseDetailBean(String empCode, CarExpenseDetailBean carExpenseBean) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createNativeQuery(
					"select ATC_SR_NO,ATC_VERIFY_STATUS,ATC_PAID_FLAG,ATC_CAR_FLAG, ATC_CAR_SHIFT_MODE, to_char(ATC_CAR_SHIFT_DATE,'dd/mm/yyyy'), ATC_CAR_TRP_COST, ATC_FUEL_COST, "
							+ "ATC_DRIVER_CHG,ATC_DRIVER_TKT_COST, ATC_CAR_SERV_COST, ATC_TOT_COST,ATC_CAR_RATE,ATC_FILE_NAME,ATC_REMARKS "
							+ "from ADM_TRF_CAR_EXP " + " where ATC_EMP_CD= '" + empCode + "' " + "and ATC_TRF_ID='"
							+ carExpenseBean.getTrfId() + "'");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						carExpenseBean.setSrNo(row[0].toString());
					}
					if (row[1] != null) {
						carExpenseBean.setVerifyStatus(row[1].toString());
					}
					if (row[2] != null) {
						carExpenseBean.setPaidStatus(row[2].toString());
					}
					if (row[3] != null) {
						carExpenseBean.setCarFlag(row[3].toString());
					}
					if (row[4] != null) {
						carExpenseBean.setCarShiftMode(row[4].toString());
					}
					if (row[5] != null) {
						carExpenseBean.setCarShiftDate(row[5].toString());
					}
					if (row[6] != null) {
						carExpenseBean.setCarTripCost(row[6].toString());
					}
					if (row[7] != null) {
						carExpenseBean.setFuelCost(row[7].toString());
					}
					if (row[8] != null) {
						carExpenseBean.setDriverCharge(row[8].toString());
					}
					if (row[9] != null) {
						carExpenseBean.setDriverTicketCost(row[9].toString());
					}
					if (row[10] != null) {
						carExpenseBean.setCarServiceCost(row[10].toString());
					}
					if (row[11] != null) {
						carExpenseBean.setTotalCost(row[11].toString());
					}
					if (row[12] != null) {
						carExpenseBean.setCarRate(row[12].toString());
					}
					if (row[13] != null) {
						carExpenseBean.setFileName(row[13].toString());
					}
					if (row[14] != null) {
						carExpenseBean.setRemarks(row[14].toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String[] getCarArray(CarExpenseDetailBean carExpenseBean) {
		Session session = sessionFactory.openSession();
		String[] stringArray = null;
		String result = "";
		try {
			Query query = session
					.createNativeQuery("select ATDL_CAR_RATE_OWN_FAMILY,ATDL_CAR_DECL_LIMIT from ADM_TRF_DECL_LIMIT "
							+ "where ATDL_SR_NO = '001' and ATDL_EFFECTIVE_DATE IN (select max(ATDL_EFFECTIVE_DATE) "
							+ "from ADM_TRF_DECL_LIMIT where ATDL_EFFECTIVE_DATE<= to_date('"
							+ carExpenseBean.getCarShiftDate() + "','dd/mm/yyyy'))");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						result += row[0].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[1] != null) {
						result += row[1].toString();
					} else {
						result += "null";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		stringArray = result.split("-");
		return stringArray;
	}

	@Override
	public String getTransferFacilityAcmoStatus(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		String stringArray = null;
		String result = "";
		try {
			Query query = session
					.createNativeQuery("select decode(F_GET_ACMO_STATUS('"+reimbursementBean.getEmpCode()+"',sysdate),'003','Dormatory','Other Than Dormatory'),F_GET_ACMO_STATUS('"+reimbursementBean.getEmpCode()+"',sysdate) from dual");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						result += row[0].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[1] != null) {
						result += row[1].toString();
					} else {
						result += "null";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return stringArray;
	}

	@Override
	public String getTransferIdAndValidy(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		String stringArray = null;
		String result = "";
		try {
			Query query = session
					.createNativeQuery("select ETB_TRX_NO,to_char(ETB_REPORTING_DATE,'dd/mm/yyyy') "
							+ "from V_HR_EMP_TRNSF_BENEFIT,ADM_TRF_BENIFIT_INC_DTL "
							+ "where ETB_TRX_NO=ATI_TRF_ID(+) and ETB_EMP=ATI_EMP_CD(+) "
							+ "and ETB_EMP= '"+reimbursementBean.getEmpCode()+"' "
									+ "and NVL(ATI_PMT_FLAG,'N')in('N','Y','H') "
									+ "and ETB_REPORTING_DATE=( select max(ETB_REPORTING_DATE) "
									+ "from V_HR_EMP_TRNSF_BENEFIT  where ETB_EMP= '"+reimbursementBean.getEmpCode()+"')");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						result += row[0].toString() + "-";
					} else {
						result += "null" + "-";
					}
					if (row[1] != null) {
						result += row[1].toString();
					} else {
						result += "null";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return stringArray;
	}

	@Override
	public java.util.Date getTransferEffDate(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		java.util.Date stringArray = null;
		String result = "";
		try {
			Query query = session
					.createNativeQuery("select to_char(AFS_EFF_DT,'dd/mm/yyyy') from ADM_FACILITY_PARAM "
							+ "where AFS_AFM_CD ='546' and AFS_EFF_DT = (select max(AFS_EFF_DT)from ADM_FACILITY_PARAM "
							+ "where AFS_AFM_CD ='546')");
							
			result = (String) query.uniqueResult();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return stringArray;
	}

	@Override
	public void getVehicleRegistrationDetail(ReimbursementBean reimbursementBean,VehicleRegistrationBean vehicleBean) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createNativeQuery(
					"SELECT ATV_VEHICLE_REG_DT, ATV_OLD_REG_NO, ATV_NEW_REG_NO, ATV_REG_COST,ATV_VERIFY_FLAG,NVL(ATV_PAID_FLAG,'N'), "
							+ "ATV_SR_NO,ATV_FILE_NAME,ATV_REMARKS FROM ADM_TRF_VEHICLE_REG "
							+ "WHERE ATV_EMP_CD= '" + reimbursementBean.getEmpCode() + "' " + "and ATV_TRF_ID='"
							+ vehicleBean.getTrfId() + "'");
			List<Object[]> objectArray = query.list();
			if (!objectArray.isEmpty()) {
				for (Object[] row : objectArray) {

					if (row[0] != null) {
						vehicleBean.setRegistrationDate((java.util.Date) row[0]);
					}
					if (row[1] != null) {
						vehicleBean.setOldRegNo(row[1].toString());
					}
					if (row[2] != null) {
						vehicleBean.setNewRegNo(row[2].toString());
					}
					if (row[3] != null) {
						vehicleBean.setRegCost(row[3].toString());
					}
					if (row[4] != null) {
						vehicleBean.setVerifyFlag(row[4].toString());
					}
					if (row[5] != null) {
						vehicleBean.setPaidFlag(row[5].toString());
					}
					if (row[6] != null) {
						vehicleBean.setSrNo(row[6].toString());
					}
					if (row[7] != null) {
						vehicleBean.setFileName(row[7].toString());
					}
					
					if (row[8] != null) {
						vehicleBean.setRemarks(row[8].toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@Override
	public String getRvmeSubmittedFmCode(String empCode) {
		Session session = sessionFactory.openSession();
		String result = "";
		try {
			Query query = session
					.createNativeQuery("SELECT AERC_AFM_CD from cadmin.adm_emp_reimb_consent WHERE AERC_AFM_CD in ('501','502') "
							+ "AND TRUNC(AERC_CREATED_DT) = (SELECT TRUNC(MAX(AERC_CREATED_DT)) from cadmin.adm_emp_reimb_consent "
							+ "WHERE AERC_AFM_CD in ('501','502') AND AERC_EMP_CD = '"+empCode+"') AND AERC_AVL_OPTION = 'Y' "
									+ "AND AERC_EMP_CD = '"+empCode+"'");
							
			result = (String) query.uniqueResult();
			
			if(result == null){
				result = "0";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	@Override
	public String getRroFlag(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String catCode = "";
		try {

			Query query = session.createNativeQuery(
					"select NVL(STATUS,'NA') from v_hr_emp, masters.bcm_branch_code "
					+ "where vhe_emp_br_cd =  '"+reimbursementBean.getBranchCode()+"' "
							+ "	and vhe_emp_cd = '"+reimbursementBean.getEmpCode()+"'");
			catCode = (String) query.uniqueResult();
			return catCode;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return catCode;
	}

	@Override
	public String getInchargeDetails(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String result = "";
		try {

			Query query = session.createNativeQuery(
					"Select count(*),to_char(max(VHS_from_date),'dd/mm/yyyy')  from v_hr_emp_splpost "
					+ "Where vhs_emp_code = '"+reimbursementBean.getEmpCode()+"' and vhs_emp_grade in ('A','B','C') and "
					+ "vhs_active = 'Y' and vhs_post_type = 'I' AND sysdate between vhs_from_date and nvl(vhs_to_date,sysdate)");
			List<Object[]> objectList =  query.list();
			if(objectList != null){
				for(Object [] obj : objectList){
					if(obj[0] != null){
						result += obj[0].toString() + "-";
					}else {
						result += "0-";
					}
					if(obj[1] != null){
						result += obj[1].toString();
					} else {
						result += "empty";
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public String getLvEmpPPGrade(ReimbursementBean reimbursementBean) {

		Session session = sessionFactory.openSession();
		String result = "";
		try {

			Query query = session.createNativeQuery("select cadmin.Fa_Grade_PP('"+reimbursementBean.getEmpCode()+"',sysdate) from dual");
			result = (String) query.uniqueResult();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public String getMobEmpExceptionalCode(String empCode) {

		Session session = sessionFactory.openSession();
		String result = "";
		try {

			Query query = session.createNativeQuery(
					" select AEMR_EMP_CD from cadmin.ADM_EMP_MOBILE_MASTER where AEMR_EMP_CD= '" + empCode + "'");
			result = (String) query.uniqueResult();
			if(result == null){
				result = "";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public List<Object[]> getTelephoneHistory(String empCode) {

		Session session = sessionFactory.openSession();
		List<Object[]> result = null;
		try {

			Query query = session.createNativeQuery(
					" SELECT atd_tel_no, atd_service_prov, to_char(atd_install_dt,'yyyy-mm-dd') from cadmin.adm_telephone_det "
					+ "where atd_emp_cd= '" + empCode + "' AND  atd_created_dt = (	SELECT max(atd_created_dt) "
							+ " FROM cadmin.adm_telephone_det WHERE atd_emp_cd = '" + empCode + "')");
			result = query.list();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public List<Object[]> getRvmeHistoryButton(String empCode) {

		Session session = sessionFactory.openSession();
		List<Object[]> result = null;
		try {

			Query query = session.createNativeQuery(
					" SELECT avd_vehicle_type,avd_regn_no, avd_regd_place,avd_maint_place,to_char(avd_acquired_dt,'yyyy-mm-dd'),AVD_FW_OWN_HIRED_BANK,AVD_FW_ENG_CC_LESS_16, "
					+ "AVD_VEHICLE_CHASIS_NO,AVD_VEHICLE_ENGINE_NO, to_char(AVD_VEHICLE_RC_VALID_DATE,'yyyy-mm-dd'),to_char(avd_shift_dt,'yyyy-mm-dd')	FROM  cadmin.adm_vehicle_det "
							+ "WHERE  avd_emp_cd = '" + empCode + "' and avd_verify_status in ('Y','N') "
									+ "AND avd_created_dt = (SELECT MAX(avd_created_dt) FROM cadmin.adm_vehicle_det "
									+ " WHERE  avd_emp_cd = '" + empCode + "' and avd_verify_status in ('Y','N'))");
			result = query.list();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@Override
	public boolean saveIncidentalExpense(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String familyShiftDate = AppConstants.formatDate(reimbursementBean.getIncidentalFamilyShiftDate());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.Ka_TRF_BENIFIT_EXP.pa_submit_trf_inc_benifit(?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, reimbursementBean.getTrfId());
				stmt.setString(4, "545");
				stmt.setString(5, reimbursementBean.getIncidentalClaimed());
				stmt.setString(6, reimbursementBean.getIncidentalFamilyShiftFalg());
				stmt.setDate(7, new Date(AppConstants.parseDate(familyShiftDate).getTime()));
				stmt.setString(8, reimbursementBean.getIncidentalPaymentMode());
				stmt.registerOutParameter(9, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(10, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(9);
				String errMsg = (String) stmt.getString(10);
				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveTransPortExpenseForm(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String familyShiftDate = AppConstants.formatDate(reimbursementBean.getGoodShiftDate());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.Ka_TRF_BENIFIT_EXP.pa_submit_trf_trp_exp(?,?,?,?,?,?,?,?,?,?,"
						+ "?,?,?,?,?,?,?,?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, reimbursementBean.getTrfId());
				stmt.setString(4, "546");
				stmt.setString(5, reimbursementBean.getLastTransFlag());
				stmt.setString(6, reimbursementBean.getAccomodationType());
				stmt.setString(7, reimbursementBean.getHouseHoldGoodApplicable() == null ? "N" : reimbursementBean.getHouseHoldGoodApplicable());
				stmt.setString(8, reimbursementBean.getFamilyShifted());
				stmt.setDate(9, new Date(AppConstants.parseDate(familyShiftDate).getTime()));
				stmt.setString(10, "N"); // DORMATORY Will Update Later
				stmt.setString(11, reimbursementBean.getGrossWeight());
				stmt.setString(12, reimbursementBean.getTransportCost());
				stmt.setString(13, "0");
				stmt.setString(14, reimbursementBean.getEligibleDeclarationAmount());
				stmt.setString(15, reimbursementBean.getTotalTransportCost());
				stmt.setString(16, reimbursementBean.getDistanceCovered());
				stmt.setString(17, "1");
				stmt.registerOutParameter(18, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(18);
				String errMsg = (String) stmt.getString(19);
				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public boolean saveCarExpenseForm(ReimbursementBean reimbursementBean) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Connection con = null;
		CallableStatement stmt = null;
		boolean success = false;
		String errNo = "";
		Query query = null;
		String carShiftDate = AppConstants.formatDate(reimbursementBean.getCarShiftDate());
		try {
			SessionImpl sessionImpl = (SessionImpl) session;
			con = sessionImpl.connection();
			if (session != null) {
				tx = session.beginTransaction();
				stmt = con.prepareCall("{ call cadmin.Ka_TRF_BENIFIT_EXP.pa_submit_trf_car_exp(?,?,?,?,?,?,?,?,?,?,"
						+ "?,?,?,?,?) }");
				stmt.setString(1, reimbursementBean.getEmpBranchCode());
				stmt.setString(2, reimbursementBean.getEmpCode());
				stmt.setString(3, reimbursementBean.getTrfId());
				stmt.setString(4, "547");
				stmt.setString(5, reimbursementBean.getCarShifted());
				stmt.setString(6, reimbursementBean.getCarDriver()); 
				stmt.setDate(7, new Date(AppConstants.parseDate(carShiftDate).getTime()));
				stmt.setString(8, reimbursementBean.getCarDistance());
				stmt.setString(9, "0");
				stmt.setString(10, reimbursementBean.getDecBasicAmount());
				stmt.setString(11, "0");
				stmt.setString(12, reimbursementBean.getCarRatePerKm());
				stmt.setString(13, reimbursementBean.getCarClaimAmount());
				stmt.registerOutParameter(14, java.sql.Types.VARCHAR);
				stmt.registerOutParameter(15, java.sql.Types.VARCHAR);
				success = stmt.execute();
				errNo = (String) stmt.getString(14);
				String errMsg = (String) stmt.getString(15);
				if (errMsg == null && errNo.equals("00000")) {
					tx.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;

	}

	@Override
	public String getCarRateForOthers(Map<String, String> requestData) {

		Session session = sessionFactory.openSession();
		String selectedButton = requestData.get("selectedButton");
		String carRate = "";
		BigDecimal rate = null;
		String finalResult = "";
		Query query = null;
		String catCode = "";
		try {
			
			if(selectedButton.equals("D")){
				query = session.createNativeQuery(
						"select ATDL_CAR_RATE_OWN_FAMILY,ATDL_CAR_DECL_LIMIT from cadmin.ADM_TRF_DECL_LIMIT "
						+ " where ATDL_SR_NO = '001' and ATDL_EFFECTIVE_DATE IN (select max(ATDL_EFFECTIVE_DATE) "
						+ " from cadmin.ADM_TRF_DECL_LIMIT where ATDL_EFFECTIVE_DATE<= to_date('"+requestData.get("carShiftDate")+"','yyyy-mm-dd')) ");
				List<Object[]> rows = query.list();
				if(rows.size() > 0){
					for(Object [] row : rows){
						if(row[0] != null){
							requestData.put("ATDL_CAR_RATE_OWN_FAMILY", row[0].toString());
							finalResult += row[0].toString() + "-";
							
						} else {
							requestData.put("ATDL_CAR_RATE_OWN_FAMILY", "0");
							finalResult += "empty-";
						}
						
						if(row[1] != null){
							requestData.put("ATDL_CAR_DECL_LIMIT", row[0].toString());
							finalResult += row[1].toString();
						} else {
							requestData.put("ATDL_CAR_DECL_LIMIT", "0");
							finalResult += "empty";
						}
						
						if(row[0] != null || row[1] != null){
							BigDecimal value1 = new BigDecimal(row[0].toString());
							BigDecimal value2 = new BigDecimal(row[1].toString());
							
							rate = value1.add(value2);
						}
					}
				}
			} else {
				query = session.createNativeQuery(
						" select ATDL_CAR_RATE from cadmin.ADM_TRF_DECL_LIMIT  where ATDL_SR_NO = '001' "
						+ " and ATDL_EFFECTIVE_DATE IN (select max(ATDL_EFFECTIVE_DATE) "
						+ "  from cadmin.ADM_TRF_DECL_LIMIT where ATDL_EFFECTIVE_DATE<= to_date('"+requestData.get("carShiftDate")+"','yyyy-mm-dd')) ");
				
				rate = (BigDecimal) query.uniqueResult();
				finalResult = rate.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return finalResult;
	}

}
