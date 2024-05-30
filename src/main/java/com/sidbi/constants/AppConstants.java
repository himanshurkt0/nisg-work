package com.sidbi.constants;

import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.sidbi.bean.BookGrantBookingHistory;
import com.sidbi.bean.DeclarationReimbTelephoneBean;
import com.sidbi.bean.EmployeeInfoBean;
import com.sidbi.bean.NonPeriodicBookClaimBean;
import com.sidbi.bean.NonPeriodicBriefcaseBean;
import com.sidbi.bean.PersonalAdvanceBean;
import com.sidbi.bean.PreviousApplication;
import com.sidbi.bean.ReimbBookingHistory;
import com.sidbi.bean.ReimbConsentDetailBean;
import com.sidbi.bean.ReimbursementBean;
import com.sidbi.domain.FinancialAssistDataDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.service.AppService;

public class AppConstants {

	public static final String ACCOM_MAN = "ACCOMMODATION MANAGEMENT";
	public static final String ACCOM_BANK = "ACCOMODATION BANK FLAT ONLY";
	public static final String ADMINISTRATION = "Administration".toUpperCase();
	public static final String DAF_VHS = "DAF/VHS DETAILS";
	public static final String INDOOR_HOSP = "Indoor Hospitalisation & Comprehensive Health Checkup (CHC)"
			.toUpperCase();
	public static final String MISC_CLAIMS = "Miscellaneous Claims".toUpperCase();
	public static final String HOL_HM_BOOKING = "HOLIDAY HOME BOOKING";
	public static final String LFC = "LFC";
	public static final String LOAN_ADV = "Loans & Advances".toUpperCase();
	public static final String REPORTS = "Reports".toUpperCase();
	public static final String PF = "PF Advance & Withdrawal".toUpperCase();
	public static final String VERIFICATION = "Verification".toUpperCase();
	public static final String MAINTENANCE = "Maintenance".toUpperCase();
	public static final String AUDIT = "Audit for CAP-CSPC ".toUpperCase();
	public static final String FUND = "Fund Processing Module".toUpperCase();
	public static final String CAR_SCHEME = "Bank Car Scheme".toUpperCase();
	public static final String PAYMENT_PROCESS = "Processing Payments".toUpperCase();
	public static final String OVERTIME_CLAIM = "Overtime Claim System".toUpperCase();
	public static final String HOUSE_LOAN = "Housing Loan".toUpperCase();
	public static final String PERSONAL_ADV = "PersonalAdvance";
	public static final String CAP = "CAP";
	public static final String CAP_HIRE = "CAR HIRE";
	public static final String REIMBURSEMENT = "Reimbursement".toUpperCase();
	public static final String TAHA_BILL = "TAHA Bill Settlement".toUpperCase();

	public static final String DependentConstant = "555";
	public static final String BookGrantConstant = "554";
	public static final String ScholarshipConstant = "553";
	public static final int NP_OPD_MAX_CNT = 3;
	
	public static final String [] offEntAllowedDesigList = {"38","48","43","28","171","234"};
	public static final String [] offEntAllowedGradeList = {"A","B"};
	public static final String [] metroRvmeLimitAllowedBranch = {"311","312","330","322","326","328","411"};
	public static final String [] laptopAllowedGradeList = {"G","F","E","D"};
	public static final String [] laptopAllowedDesigList = {"28","171","172","43","16","56","92","55","58","118","122","136","124","89"};
	

	public static String removeSpecialCharacter(String s) {
		for (int i = 0; i < s.length(); i++) {

			if (s.charAt(i) < 'A' || s.charAt(i) > 'Z' && s.charAt(i) < 'a' || s.charAt(i) > 'z') {

				s = s.substring(0, i) + s.substring(i + 1);
				i--;
			}
		}
		return s;
	}

	public static String formatDate(String date) {
		String[] splitDate = date.split("-");
		String formattedDate = splitDate[2] + "/" + splitDate[1] + "/" + splitDate[0];
		return formattedDate;

	}
	
	public static String formatDateAccordingToUi(String date) {
		String[] splitDate = date.split("/");
		String formattedDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
		return formattedDate;

	}

	public static String formatDate(String date, String startDate) {
		String[] splitDate = date.split("-");
		String formattedDate = startDate + "/" + splitDate[1] + "/" + splitDate[0];
		return formattedDate;

	}

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		if(date != null){
			Date formattedDate = sd.parse(date);
			return formattedDate;
		}
		return null;
	}
	
	public static String parseDate(Date date) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = sd.format(date);
		return formattedDate;
	}

	public static int durationInMonth(String startDate, String endDate) throws ParseException {
		Date start = null;
		Date end = null;
		if(startDate.contains("-")||endDate.contains("-")){
			start = parseDate(formatDate(startDate));
			end = parseDate(formatDate(endDate));
		} else {
			start = parseDate(startDate);
			end = parseDate(endDate);
		}
		
		return (int) ((start.getTime() - end.getTime())/ (1000 * 60 * 60 * 24 * 30));
	}
	
	// Setting enable disable for tab elements
		public static void setReimTabElementEligibility(ModelAndView mv, ReimbursementBean reimbursementBean,
				HttpServletRequest request, AppService service) {
			List<String> afsCodeList = service.getAfsCodeList(reimbursementBean);
			reimbursementBean.setAfsCodeList(afsCodeList);
		}

		// Setting Common Grade for Tab
		public static void setReimTabEligibility(ModelAndView mv, ReimbursementBean reimbursementBean,
				HttpServletRequest request, AppService service) {
			service.getEmployeeGradeDetails(reimbursementBean);
			String empGrade = reimbursementBean.getEmpGrade();
			String[] empGrdList = { "A", "B", "C", "D", "E", "F" };
			List<String> empGradeArray = Arrays.asList(empGrdList);
			if (empGradeArray.contains(empGrade)) {
				reimbursementBean.setCommonGrade("Z");
			} else {
				if (empGrade.equals("3") || empGrade.equals("4")) {
					reimbursementBean.setCommonGrade("9");
				}
			}
			
			reimbursementBean.setEmpGrade(empGrade);

			// Enabling Disabling tab based on Eligibility
			if (empGrade.equals("I")) {

				mv.addObject("RegularClaim", "");
				mv.addObject("VehicleInsurance", "");
				mv.addObject("TldcClaim", "");
				mv.addObject("NonPeriodicClaim", "tab");
				mv.addObject("LaptopMobileClaim", "tab");
				// mv.addObject("NonPeriodicClaim","");

			} else if (empGrade.equals("G")) {

				mv.addObject("TldcClaim", "");
				mv.addObject("VehicleInsurance", "tab");
				mv.addObject("LaptopMobileClaim", "tab");

			} else if (empGrade.equals("F") || empGrade.equals("G")) {

				mv.addObject("TabIpadClaim", "tab");
				mv.addObject("pageTabClaim", "tab");
				mv.addObject("RegularClaim", "tab");
				mv.addObject("rvmeVehicleClaim", "tab");
				mv.addObject("NonPeriodicClaim", "tab");
				mv.addObject("VehicleInsurance", "tab");
				mv.addObject("LaptopMobileClaim", "tab");

			} else {

				mv.addObject("RegularClaim", "tab");
				mv.addObject("VehicleInsurance", "tab");
				mv.addObject("NonPeriodicClaim", "tab");
				mv.addObject("TldcClaim", "tab");
				mv.addObject("MedicalClaim", "tab");
				mv.addObject("rvmeVehicleClaim", "tab");
				//mv.addObject("LaptopMobileClaim", "tab");

			}
			
			if(reimbursementBean.getDisableLaptopTab() != null && reimbursementBean.getDisableLaptopTab().equals("disabled")){
				mv.addObject("LaptopMobileClaim", "");
			} else if(reimbursementBean.getDisableLaptopTab() != null){
				mv.addObject("LaptopMobileClaim", "tab");
			}
		}


}
