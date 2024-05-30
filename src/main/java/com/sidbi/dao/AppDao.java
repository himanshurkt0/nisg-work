package com.sidbi.dao;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.sidbi.domain.FinancialAssistDataDomain;
import com.sidbi.domain.LaptopClaimDomain;
import com.sidbi.domain.MobChargesDomain;
import com.sidbi.domain.MobileClaimDomain;
import com.sidbi.domain.MobileClaimReimDomain;
import com.sidbi.domain.ReimConsentDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.domain.TabletClaimDomain;

public interface AppDao {
	
	public String getCatCode(String empCode);

	public Map<Integer, String> getSchoolCoursePersue();

	public Map<Integer, String> getSchoolCoursePassed();

	public Map<String, String> getDependents(String empCode);

	public int getDependentCountValue(String dependentCode, String coursePersureCode,String empCode);

	public String getPaymentValue(String coursePersued, String coursePassed, String dependentGrade, String courseYear,
			String startDate, String percentage,String fmCode);

	public PreviousApplication getScholarshipApplCount(String empCode, String dependents, String coursePersued, String endDate);

	public String getLVRSerialNumber(String empCode, String appCode);

	public String saveScholarshipPMT(String branchCode,String dependentName, String lv_sr_no, PersonalAdvanceBean personalAdvanceBean);

	public List<Object[]> getFinAssistData(String empCode);

	public List<Object[]> getFinAssistDataToEdit(String empCode, String srNo, String appCode);

	public Blob getFinAssisPdf(String empCode, String srNo, String fmCode);

	public Map<String, String> getBookGrantCoursePassedList();

	public Map<String, String> getbookGrantCoursePersueMap();

	public BookGrantBookingHistory getBookGrantPreviousBooking(ReimbursementBean reimbursementBean);

	public String getBookGrantYearCount(ReimbursementBean reimbursementBean);

	public String getBookGrantDuration(ReimbursementBean reimbursementBean);

	public String getBookGrantFmName(String fmCode);

	public String saveBookGrantSCST(ReimbursementBean reimbursementBean);

	public String getDependentRelationName(ReimbursementBean reimbursementBean);

	public List<Object[]> getBookGrantData(String empCode);

	public String getPaymentValue(String coursePersued, String coursePassed, String scholCommonGrade,
			String courseStream, String startDate, String percentage, String scholarshipconstant, String boarder);

	public List<Object[]> getScholarshipSCSTData(String empCode);

	public ReimbBookingHistory getScholarshipPreviousBooking(ReimbursementBean scholarshipBean);

	public String getScholarshipYearCount(ReimbursementBean scholarshipBean);

	public String getScholarshipDuration(ReimbursementBean scholarshipBean);

	public String getScholarShipFmName(String fmCode);

	public String saveScholarShipSCST(ReimbursementBean scholarshipBean);

	public List<FinancialAssistDataDomain> getFinancialAssistVerifyList(String empCode);

	public int getApproveRejectReimBeans(String[] toApproveReimbList, boolean isRejecting);

	public void saveLogAction(String moduleName, String message);

	public EmployeeInfoBean getEmployeeInformation(String empCode);

	public List<String> getReimbursementFMCodeList();

	public String checkIfEmployeeActive(String empCode);

	public String getPromotionDateInDeclaration(String empCode);

	public String getPromotionType(String empCode,String promotionDate);

	public List<ReimbConsentDetailBean> getReimbConsentDetailBean(String empCode, String currentDate);

	public DeclarationReimbTelephoneBean getReimbTelephoneDetails(String empCode);

	public boolean savedeclarationRegularClaimSubmit(ReimbursementBean reimbursementBean);

	public String getFinStartEnd();

	public boolean saveNewsPaperClaimSubmit(ReimbursementBean reimbursementBean);

	public boolean saveHouseHoldHelpClaim(ReimbursementBean reimbursementBean);

	public boolean saveOfficialEntExpClaim(ReimbursementBean reimbursementBean);

	public boolean saveWashingAllowanceClaim(ReimbursementBean reimbursementBean);

	public boolean saveResidenceOfficeAllowance(ReimbursementBean reimbursementBean);

	public boolean saveCarCleaningClaim(ReimbursementBean reimbursementBean);

	public boolean saveTelephoneClaim(ReimbursementBean reimbursementBean);

	public boolean saveDataChargesClaim(ReimbursementBean reimbursementBean);

	public NonPeriodicBriefcaseBean getPrevBriefCase(String empCode);

	public Date getBREligibilityDate(String empCode, String grade);

	public String getMaxBRLimit(String grade);

	public String getNonPeriodBookClaimDate(String empCode);

	public NonPeriodicBookClaimBean getLastNonPeriodBookClaim(String empCode,String bookClaimDate);

	public String getLVPPGradeForBookClaim(String grade);

	public String getNPMaxBookClaimAmount(String lvPPGrade);

	public String getPPABGPaidAmount(String empCode);

	public boolean compareFinancialYear(String promotionDate);

	public String getNPClaimDate(String empCode, String npCode);

	public void getPreviousNpMedicalClaim(ReimbursementBean reimbursementBean);

	public String getNpMedicalOptClaimAmount(String empCode);

	public String getMedicalLvPPGrade(String empCode);

	public String getMaxNpMedicalLimit(String medicalLvPPGrade);

	public String getNpMedicalPaidAmountAndCount(String empCode,String claimDate);

	public String getNpPaidUnpaidMedClaimAmount(String empCode, String lastNpMedicalClaimDate);

	public RvmeBeanDomain getPreviousRvmeClaim(String empCode, String[] statusList);

	public Map<String, String> getRvmePlaceOfUseMap();

	public List<String> getAfsCodeList(ReimbursementBean reimbursementBean);

	public String getPromotionEffectiveDate(String empCode);

	public void getEmployeeGradeDetails(ReimbursementBean reimbursementBean);

	public void getEmployeeDetForRVME(ReimbursementBean reimbursementBean);

	public boolean saveRvmeVehicleClaim(ReimbursementBean reimbursementBean);

	public VehicleInsuranceBean getPreviousVehicleInsuranceBean(ReimbursementBean reimbursementBean);

	public String getEmployeeRetireeDate(String empCode);

	public void getPromotionDetailForVehicletInsurance(ReimbursementBean reimbursementBean);

	public String getActiveInactiveCountForVI(ReimbursementBean reimbursementBean);

	public String getLastInsuranceExpiryDate(ReimbursementBean reimbursementBean);

	public boolean saveVehicleInsuranceClaim(ReimbursementBean reimbursementBean);

	public boolean submitVehicleInsuranceRefund(ReimbursementBean reimbursementBean);

	public String getNpRefundPremAmount(ReimbursementBean reimbursementBean, VehicleInsuranceBean previousInsurance);

	public boolean saveNpBriefCaseClaim(ReimbursementBean reimbursementBean);

	public boolean saveNpBookGrantClaim(ReimbursementBean reimbursementBean);

	boolean saveNpMedicalOptClaim(ReimbursementBean reimbursementBean);

	public int getSBLPDMonth(ReimbursementBean reimbursementBean);

	public String getApmEffectiveDate();

	public int getPostType(ReimbursementBean reimbursementBean);

	public String getMobileEmpGrade(ReimbursementBean reimbursementBean);

	public int getMobileRefundCount(ReimbursementBean reimbursementBean);

	public String getMobilePaidDate(ReimbursementBean reimbursementBean);

	public void getPrevLaptopMobileClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean);

	public String getLastMobileClaimPaidDate(ReimbursementBean reimbursementBean);

	public String getMobileFinStartEnd(ReimbursementBean reimbursementBean);

	public void getPreviousMobileChargesDetail(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean);

	public String getLaptopBillOrClaimDate(ReimbursementBean reimbursementBean);

	public String getLaptopValidMonth(ReimbursementBean reimbursementBean);

	public String getLaptopSBLPDMonth(ReimbursementBean reimbursementBean);

	public String getLaptopPreviousGrade(ReimbursementBean reimbursementBean);

	public void getPreviousLaptopClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean);

	public String getNextLaptopEligibleDate(Date lastLaptopClaimDate, String laptopValidMonths);

	public String getMobileClaimLnDate(ReimbursementBean reimbursementBean);

	public String getMobileFinStartEndForSave(ReimbursementBean reimbursementBean);

	public String getMaxSrNoForLapMobile(ReimbursementBean reimbursementBean,String afmCode);

	public int getMobileReimCheckCount(ReimbursementBean reimbursementBean);

	public int getMobileDetCheckCount(ReimbursementBean reimbursementBean);

	public boolean saveMobileClaim(MobileClaimDomain mobileBeanToSave, MobileClaimReimDomain mobileConsentBean);

	public String getFinStartDate();

	public String getAddMonths(int countSBLPRDMonth);

	public String getLapMobileLnDate(ReimbursementBean reimbursementBean, String afmCode);

	public int getChargesReimCheck(ReimbursementBean reimbursementBean);

	public int getChargesDetCount(ReimbursementBean reimbursementBean);

	public String getFinStartAndEndDate();

	public boolean saveMobileChargesClaim(MobChargesDomain mobileCharge, MobileClaimReimDomain chargesConsentBean,
			int mobReimCheck);

	public String getPrevEmpGrade(String empCode);

	public int getLaptopReimCheck(String empCode,String month);

	public int getLaptopDetCheck(String empCode, String laptopValidMonths);

	public int getLaptopCount(String empCode);

	public String getLaptopValidDate(String parseDate, String laptopValidMonths);

	public boolean saveLaptopClaim(LaptopClaimDomain lapClaim, MobileClaimReimDomain laptopConsentBean);

	public String getMaxSrNo(ReimbursementBean reimbursementBean, String afmCode);

	public String getMaxLapSrNo(ReimbursementBean reimbursementBean);

	public boolean updateLaptopReim(LaptopClaimDomain lapClaim, ReimbursementBean reimbursementBean);

	public String getAfsSalMonth(ReimbursementBean reimbursementBean, String fmCode);

	public String getLastTabPaidDate(ReimbursementBean reimbursementBean);

	public void getLastTabletClaimDetails(ReimbursementBean reimbursementBean);

	public String getLastTabPaidClaimDate(ReimbursementBean reimbursementBean);

	public String getLastTabClaimDate(ReimbursementBean reimbursementBean);

	public String getTabPayDate(ReimbursementBean reimbursementBean, String validMonth);

	public String getTabApplicableDate(ReimbursementBean reimbursementBean);

	public String getMaxSrNoFromConsent(ReimbursementBean reimbursementBean, String fmCode);

	public int getTabReimCheck(ReimbursementBean reimbursementBean, String fmCode, int validMonthTab);

	public int getTabDetCheck(ReimbursementBean reimbursementBean, int validMonthTab);

	public boolean saveTabClaim(TabletClaimDomain tabBean, ReimConsentDomain consentBean);

	public String getMaxSrNoInTabClaim(String empCode);

	public boolean updateTabClaim(ReimbursementBean reimbursementBean, TabletClaimDomain updateBean);

	public String[] getTransferValidationFlagPart1(ReimbursementBean reimbursementBean);

	public boolean getIncidentalDetail(ReimbursementBean reimbursementBean);

	public String[] getTransportExpenseDetail(String empCode, TransportExpenseDetailBean transportDetBean);

	public void getCarExpenseDetailBean(String empCode, CarExpenseDetailBean carExpenseBean);

	public String[] getCarArray(CarExpenseDetailBean carExpenseBean);

	public String getTransferFacilityAcmoStatus(ReimbursementBean reimbursementBean);

	public String getTransferIdAndValidy(ReimbursementBean reimbursementBean);

	public Date getTransferEffDate(ReimbursementBean reimbursementBean);

	public void getVehicleRegistrationDetail(ReimbursementBean reimbursementBean,VehicleRegistrationBean vehicleBean);

	public String getRvmeSubmittedFmCode(String empCode);

	public String getRroFlag(ReimbursementBean reimbursementBean);

	public String getInchargeDetails(ReimbursementBean reimbursementBean);

	public String getLvEmpPPGrade(ReimbursementBean reimbursementBean);

	public String getMobEmpExceptionalCode(String empCode);

	public List<Object[]> getTelephoneHistory(String empCode);

	public List<Object[]> getRvmeHistoryButton(String empCode);

	public boolean saveIncidentalExpense(ReimbursementBean reimbursementBean);

	public boolean saveTransPortExpenseForm(ReimbursementBean reimbursementBean);

	public boolean saveCarExpenseForm(ReimbursementBean reimbursementBean);

	public String getCarRateForOthers(Map<String, String> requestData);

}
