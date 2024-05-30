package com.sidbi.service;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.sidbi.dao.AppDao;
import com.sidbi.domain.FinancialAssistDataDomain;
import com.sidbi.domain.LaptopClaimDomain;
import com.sidbi.domain.MobChargesDomain;
import com.sidbi.domain.MobileClaimDomain;
import com.sidbi.domain.MobileClaimReimDomain;
import com.sidbi.domain.ReimConsentDomain;
import com.sidbi.domain.RvmeBeanDomain;
import com.sidbi.domain.TabletClaimDomain;

@Service("appService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AppServiceImpl implements AppService{
	
	@Autowired
	AppDao appDao;
	
	@Override
	public String getCatCode(String empCode) {
		
		return appDao.getCatCode(empCode);
	}

	@Override
	public Map<Integer, String> getSchoolCoursePersue() {
		
		return appDao.getSchoolCoursePersue();
	}

	@Override
	public Map<Integer, String> getSchoolCoursePassed() {
		
		return appDao.getSchoolCoursePassed();
	}

	@Override
	public Map<String, String> getDependents(String empCode) {
		
		return appDao.getDependents(empCode);
	}

	@Override
	public int getDependentCountValue(String dependentCode, String coursePersureCode,String empCode) {
		
		return appDao.getDependentCountValue(dependentCode,coursePersureCode,empCode);
	}

	@Override
	public String getPaymentValue(String coursePersued, String coursePassed, String dependentGrade, String courseYear,
			String startDate, String percentage,String fmCode) {
		
		return appDao.getPaymentValue(coursePersued,coursePassed,dependentGrade,courseYear,startDate,percentage,fmCode);
	}

	@Override
	public PreviousApplication getScholarshipApplCount(String empCode, String dependents, String coursePersued, String endDate) {
		return appDao.getScholarshipApplCount(empCode,dependents,coursePersued,endDate);
	}

	@Override
	public String getLVRSerialNumber(String empCode,String appCode) {
		
		return appDao.getLVRSerialNumber(empCode,appCode);
	}

	@Override
	public String saveScholarshipPMT(String branchCode,String dependentName, String lv_sr_no, PersonalAdvanceBean personalAdvanceBean) {
		return appDao.saveScholarshipPMT(branchCode,dependentName,lv_sr_no,personalAdvanceBean);
	}

	@Override
	public List<Object[]> getFinAssistData(String empCode) {
		return appDao.getFinAssistData(empCode);
	}

	@Override
	public List<Object[]> getFinAssistDataToEdit(String empCode, String srNo,String appCode) {
		return appDao.getFinAssistDataToEdit(empCode,srNo,appCode);
	}

	@Override
	public Blob getFinAssisPdf(String empCode, String srNo,String fmCode) {
		
		return appDao.getFinAssisPdf(empCode,srNo,fmCode);
	}

	@Override
	public Map<String, String> getBookGrantCoursePassedList() {
		return appDao.getBookGrantCoursePassedList();
	}

	@Override
	public Map<String, String> getbookGrantCoursePersueMap() {
		return appDao.getbookGrantCoursePersueMap();
	}

	@Override
	public BookGrantBookingHistory getBookGrantPreviousBooking(ReimbursementBean reimbursementBean) {
		return appDao.getBookGrantPreviousBooking(reimbursementBean);
	}

	@Override
	public String getBookGrantYearCount(ReimbursementBean reimbursementBean) {
		return appDao.getBookGrantYearCount(reimbursementBean);
	}

	@Override
	public String getBookGrantDuration(ReimbursementBean reimbursementBean) {
		return appDao.getBookGrantDuration(reimbursementBean);
	}

	@Override
	public String getBookGrantFmName(String fmCode) {
		return appDao.getBookGrantFmName(fmCode);
	}

	@Override
	public String saveBookGrantSCST(ReimbursementBean reimbursementBean) {
		return appDao.saveBookGrantSCST(reimbursementBean);
	}

	@Override
	public String getDependentRelationName(ReimbursementBean reimbursementBean) {
		return appDao.getDependentRelationName(reimbursementBean);
	}

	@Override
	public List<Object[]> getBookGrantData(String empCode) {
		return appDao.getBookGrantData(empCode);
	}

	@Override
	public String getPaymentValue(String coursePersued, String coursePassed, String scholCommonGrade,
			String courseStream, String startDate, String percentage, String scholarshipconstant, String boarder) {
		return appDao.getPaymentValue(coursePersued,coursePassed,scholCommonGrade,courseStream,startDate,percentage,scholarshipconstant,boarder);

	}

	@Override
	public List<Object[]> getScholarshipSCSTData(String empCode) {
		
		return appDao.getScholarshipSCSTData(empCode);
	}

	@Override
	public ReimbBookingHistory getScholarshipPreviousBooking(ReimbursementBean scholarshipBean) {
		return appDao.getScholarshipPreviousBooking(scholarshipBean);
	}

	@Override
	public String getScholarshipYearCount(ReimbursementBean scholarshipBean) {
		return appDao.getScholarshipYearCount(scholarshipBean);
	}

	@Override
	public String getScholarshipDuration(ReimbursementBean scholarshipBean) {
		return appDao.getScholarshipDuration(scholarshipBean);
	}

	@Override
	public String getScholarShipFmName(String fmCode) {
		
		return appDao.getScholarShipFmName(fmCode);
	}

	@Override
	public String saveScholarShipSCST(ReimbursementBean scholarshipBean) {
		
		return appDao.saveScholarShipSCST(scholarshipBean);
	}

	@Override
	public List<FinancialAssistDataDomain> getFinancialAssistVerifyList(String empCode) {
		return appDao.getFinancialAssistVerifyList(empCode);
	}

	@Override
	public int getApproveRejectReimBeans(String[] toApproveReimbList,boolean isRejecting) {
		
		return appDao.getApproveRejectReimBeans(toApproveReimbList,isRejecting);
	}
	
	@Override
	public void saveLogAction(String moduleName,String message) {
		
		appDao.saveLogAction(moduleName,message);
	}

	@Override
	public EmployeeInfoBean getEmployeeInformation(String empCode) {
		
		return appDao.getEmployeeInformation(empCode);
	}

	@Override
	public List<String> getReimbursementFMCodeList() {
		
		return appDao.getReimbursementFMCodeList();
	}

	@Override
	public String checkIfEmployeeActive(String empCode) {
		
		return appDao.checkIfEmployeeActive(empCode);
	}

	@Override
	public String getPromotionDateInDeclaration(String empCode) {
		
		return appDao.getPromotionDateInDeclaration(empCode);
	}

	@Override
	public String getPromotionType(String empCode,String promotionDate) {
		
		return appDao.getPromotionType(empCode,promotionDate);
	}

	@Override
	public List<ReimbConsentDetailBean> getReimbConsentDetailBean(String empCode, String currentDate) {
		
		return appDao.getReimbConsentDetailBean(empCode,currentDate);
	}

	@Override
	public DeclarationReimbTelephoneBean getReimbTelephoneDetails(String empCode) {
		
		return appDao.getReimbTelephoneDetails(empCode);
	}

	@Override
	public boolean savedeclarationRegularClaimSubmit(ReimbursementBean reimbursementBean) {
		
		return appDao.savedeclarationRegularClaimSubmit(reimbursementBean);
	}

	@Override
	public String getFinStartEnd() {
		
		return appDao.getFinStartEnd();
	}

	@Override
	public boolean saveNewsPaperClaimSubmit(ReimbursementBean reimbursementBean) {
		
		return appDao.saveNewsPaperClaimSubmit(reimbursementBean);
	}

	@Override
	public boolean saveHouseHoldHelpClaim(ReimbursementBean reimbursementBean) {
		
		return appDao.saveHouseHoldHelpClaim(reimbursementBean);
	}

	@Override
	public boolean saveOfficialEntExpClaim(ReimbursementBean reimbursementBean) {
		
		return appDao.saveOfficialEntExpClaim(reimbursementBean);
	}

	@Override
	public boolean saveWashingAllowanceClaim(ReimbursementBean reimbursementBean) {
		
		return appDao.saveWashingAllowanceClaim(reimbursementBean);
	}

	@Override
	public boolean saveResidenceOfficeAllowance(ReimbursementBean reimbursementBean) {
		
		return appDao.saveResidenceOfficeAllowance(reimbursementBean);
	}

	@Override
	public boolean saveCarCleaningClaim(ReimbursementBean reimbursementBean) {
		
		return appDao.saveCarCleaningClaim(reimbursementBean);
	}

	@Override
	public boolean saveTelephoneClaim(ReimbursementBean reimbursementBean) {
		
		return appDao.saveTelephoneClaim(reimbursementBean);
	}

	@Override
	public boolean saveDataChargesClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveDataChargesClaim(reimbursementBean);
	}

	@Override
	public NonPeriodicBriefcaseBean getPrevBriefCase(String empCode) {
		return appDao.getPrevBriefCase(empCode);
	}

	@Override
	public Date getBREligibilityDate(String empCode, String grade) {
		return appDao.getBREligibilityDate(empCode,grade);
	}

	@Override
	public String getMaxBRLimit(String grade) {
		return appDao.getMaxBRLimit(grade);
	}

	@Override
	public String getNonPeriodBookClaimDate(String empCode) {
		return appDao.getNonPeriodBookClaimDate(empCode);
	}

	@Override
	public NonPeriodicBookClaimBean getLastNonPeriodBookClaim(String empCode,String bookClaimDate) {
		return appDao.getLastNonPeriodBookClaim(empCode,bookClaimDate);
	}

	@Override
	public String getLVPPGradeForBookClaim(String grade) {
		return appDao.getLVPPGradeForBookClaim(grade);
	}

	@Override
	public String getNPMaxBookClaimAmount(String lvPPGrade) {
		return appDao.getNPMaxBookClaimAmount(lvPPGrade);
	}

	@Override
	public String getPPABGPaidAmount(String empCode) {
		return appDao.getPPABGPaidAmount(empCode);
	}

	@Override
	public boolean compareFinancialYear(String promotionDate) {
		return appDao.compareFinancialYear(promotionDate);
	}

	@Override
	public String getNPClaimDate(String empCode, String npCode) {
		return appDao.getNPClaimDate(empCode,npCode);
	}

	@Override
	public void getPreviousNpMedicalClaim(ReimbursementBean reimbursementBean) {
		appDao.getPreviousNpMedicalClaim(reimbursementBean);
	}

	@Override
	public String getNpMedicalOptClaimAmount(String empCode) {
		return appDao.getNpMedicalOptClaimAmount(empCode);
	}

	@Override
	public String getMedicalLvPPGrade(String empCode) {
		return appDao.getMedicalLvPPGrade(empCode);
	}

	@Override
	public String getMaxNpMedicalLimit(String medicalLvPPGrade) {
		return appDao.getMaxNpMedicalLimit(medicalLvPPGrade);
	}

	@Override
	public String getNpMedicalPaidAmountAndCount(String empCode,String claimDate) {
		return appDao.getNpMedicalPaidAmountAndCount(empCode,claimDate);
	}

	@Override
	public String getNpPaidUnpaidMedClaimAmount(String empCode, String lastNpMedicalClaimDate) {
		return appDao.getNpPaidUnpaidMedClaimAmount(empCode,lastNpMedicalClaimDate);
	}

	@Override
	public RvmeBeanDomain getPreviousRvmeClaim(String empCode, String[] statusList) {
		return appDao.getPreviousRvmeClaim(empCode,statusList);
	}

	@Override
	public Map<String, String> getRvmePlaceOfUseMap() {
		return appDao.getRvmePlaceOfUseMap();
	}

	@Override
	public List<String> getAfsCodeList(ReimbursementBean reimbursementBean) {
		return appDao.getAfsCodeList(reimbursementBean);
	}

	@Override
	public String getPromotionEffectiveDate(String empCode) {
		return appDao.getPromotionEffectiveDate(empCode);
	}

	@Override
	public void getEmployeeGradeDetails(ReimbursementBean reimbursementBean) {
		 appDao.getEmployeeGradeDetails(reimbursementBean);
	}

	@Override
	public void getEmployeeDetForRVME(ReimbursementBean reimbursementBean) {
		appDao.getEmployeeDetForRVME(reimbursementBean);
	}

	@Override
	public boolean saveRvmeVehicleClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveRvmeVehicleClaim(reimbursementBean);
	}

	@Override
	public VehicleInsuranceBean getPreviousVehicleInsuranceBean(ReimbursementBean reimbursementBean) {
		return appDao.getPreviousVehicleInsuranceBean(reimbursementBean);
	}

	@Override
	public String getEmployeeRetireeDate(String empCode) {
		return appDao.getEmployeeRetireeDate(empCode);
	}

	@Override
	public void getPromotionDetailForVehicletInsurance(ReimbursementBean reimbursementBean) {
		appDao.getPromotionDetailForVehicletInsurance(reimbursementBean);
	}

	@Override
	public String getActiveInactiveCountForVI(ReimbursementBean reimbursementBean) {
		return appDao.getActiveInactiveCountForVI(reimbursementBean);
	}

	@Override
	public String getLastInsuranceExpiryDate(ReimbursementBean reimbursementBean) {
		return appDao.getLastInsuranceExpiryDate(reimbursementBean);
	}

	@Override
	public boolean saveVehicleInsuranceClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveVehicleInsuranceClaim(reimbursementBean);
	}

	@Override
	public boolean submitVehicleInsuranceRefund(ReimbursementBean reimbursementBean) {
		return appDao.submitVehicleInsuranceRefund(reimbursementBean);
	}

	@Override
	public String getNpRefundPremAmount(ReimbursementBean reimbursementBean, VehicleInsuranceBean previousInsurance) {
		return appDao.getNpRefundPremAmount(reimbursementBean,previousInsurance);
	}
	
	@Override
	public boolean saveNpBriefCaseClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveNpBriefCaseClaim(reimbursementBean);
	}

	@Override
	public boolean saveNpBookGrantClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveNpBookGrantClaim(reimbursementBean);
	}

	@Override
	public boolean saveNpMedicalOptClaim(ReimbursementBean reimbursementBean) {
		return appDao.saveNpMedicalOptClaim(reimbursementBean);
	}

	@Override
	public int getSBLPDMonth(ReimbursementBean reimbursementBean) {
		return appDao.getSBLPDMonth(reimbursementBean);
	}

	@Override
	public String getApmEffectiveDate() {
		return appDao.getApmEffectiveDate();
	}

	@Override
	public int getPostType(ReimbursementBean reimbursementBean) {
		return appDao.getPostType(reimbursementBean);
	}

	@Override
	public String getMobileEmpGrade(ReimbursementBean reimbursementBean) {
		return appDao.getMobileEmpGrade(reimbursementBean);
	}

	@Override
	public int getMobileRefundCount(ReimbursementBean reimbursementBean) {
		return appDao.getMobileRefundCount(reimbursementBean);
	}

	@Override
	public String getMobilePaidDate(ReimbursementBean reimbursementBean) {
		return appDao.getMobilePaidDate(reimbursementBean);
	}

	@Override
	public void getPrevLaptopMobileClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean) {
		appDao.getPrevLaptopMobileClaim(reimbursementBean,previousBean);
	}

	@Override
	public String getLastMobileClaimPaidDate(ReimbursementBean reimbursementBean) {
		return appDao.getLastMobileClaimPaidDate(reimbursementBean);
	}

	@Override
	public String getMobileFinStartEnd(ReimbursementBean reimbursementBean) {
		return appDao.getMobileFinStartEnd(reimbursementBean);
	}

	@Override
	public void getPreviousMobileChargesDetail(ReimbursementBean reimbursementBean,
			LaptopMobileClaimBean previousBean) {
		appDao.getPreviousMobileChargesDetail(reimbursementBean,previousBean);
	}

	@Override
	public String getLaptopBillOrClaimDate(ReimbursementBean reimbursementBean) {
		return appDao.getLaptopBillOrClaimDate(reimbursementBean);
	}

	@Override
	public String getLaptopValidMonth(ReimbursementBean reimbursementBean) {
		return appDao.getLaptopValidMonth(reimbursementBean);
	}

	@Override
	public String getLaptopSBLPDMonth(ReimbursementBean reimbursementBean) {
		return appDao.getLaptopSBLPDMonth(reimbursementBean);
	}

	@Override
	public String getLaptopPreviousGrade(ReimbursementBean reimbursementBean) {
		return appDao.getLaptopPreviousGrade(reimbursementBean);
	}

	@Override
	public void getPreviousLaptopClaim(ReimbursementBean reimbursementBean, LaptopMobileClaimBean previousBean) {
		appDao.getPreviousLaptopClaim(reimbursementBean,previousBean);
	}

	@Override
	public String getNextLaptopEligibleDate(Date lastLaptopClaimDate, String laptopValidMonths) {
		return appDao.getNextLaptopEligibleDate(lastLaptopClaimDate,laptopValidMonths);
	}

	@Override
	public String getMobileClaimLnDate(ReimbursementBean reimbursementBean) {
		return appDao.getMobileClaimLnDate(reimbursementBean);
	}

	@Override
	public String getMobileFinStartEndForSave(ReimbursementBean reimbursementBean) {
		return appDao.getMobileFinStartEndForSave(reimbursementBean);
	}

	@Override
	public String getMaxSrNoForLapMobile(ReimbursementBean reimbursementBean,String afmCode) {
		return appDao.getMaxSrNoForLapMobile(reimbursementBean,afmCode);
	}

	@Override
	public int getMobileReimCheckCount(ReimbursementBean reimbursementBean) {
		return appDao.getMobileReimCheckCount(reimbursementBean);
	}

	@Override
	public int getMobileDetCheckCount(ReimbursementBean reimbursementBean) {
		return appDao.getMobileDetCheckCount(reimbursementBean);
	}

	@Override
	public boolean saveMobileClaim(MobileClaimDomain mobileClaimDomain,MobileClaimReimDomain mobileConsentBean) {
		return appDao.saveMobileClaim(mobileClaimDomain,mobileConsentBean);
	}

	@Override
	public String getFinStartDate() {
		return appDao.getFinStartDate();
	}

	@Override
	public String getAddMonths(int countSBLPRDMonth) {
		return appDao.getAddMonths(countSBLPRDMonth);
	}

	@Override
	public String getLapMobileLnDate(ReimbursementBean reimbursementBean, String afmCode) {
		return appDao.getLapMobileLnDate(reimbursementBean,afmCode);
	}

	@Override
	public int getChargesReimCheck(ReimbursementBean reimbursementBean) {
		return appDao.getChargesReimCheck(reimbursementBean);
	}

	@Override
	public int getChargesDetCount(ReimbursementBean reimbursementBean) {
		return appDao.getChargesDetCount(reimbursementBean);
	}

	@Override
	public String getFinStartAndEndDate() {
		return appDao.getFinStartAndEndDate();
	}

	@Override
	public boolean saveMobileChargesClaim(MobChargesDomain mobileCharge, MobileClaimReimDomain chargesConsentBean,
			int mobReimCheck) {
		return appDao.saveMobileChargesClaim(mobileCharge,chargesConsentBean,mobReimCheck);
	}

	@Override
	public String getPrevEmpGrade(String empCode) {
		return appDao.getPrevEmpGrade(empCode);
	}

	@Override
	public int getLaptopReimCheck(String empCode,String month) {
		return appDao.getLaptopReimCheck(empCode,month);
	}

	@Override
	public int getLaptopDetCheck(String empCode, String laptopValidMonths) {
		return appDao.getLaptopDetCheck(empCode,laptopValidMonths);
	}

	@Override
	public int getLaptopCount(String empCode) {
		return appDao.getLaptopCount(empCode);
	}

	@Override
	public String getLaptopValidDate(String parseDate, String laptopValidMonths) {
		return appDao.getLaptopValidDate(parseDate,laptopValidMonths);
	}

	@Override
	public boolean saveLaptopClaim(LaptopClaimDomain lapClaim, MobileClaimReimDomain laptopConsentBean) {
		return appDao.saveLaptopClaim(lapClaim,laptopConsentBean);
	}

	@Override
	public String getMaxSrNo(ReimbursementBean reimbursementBean, String afmCode) {
		return appDao.getMaxSrNo(reimbursementBean, afmCode);
	}

	@Override
	public String getMaxLapSrNo(ReimbursementBean reimbursementBean) {
		return appDao.getMaxLapSrNo(reimbursementBean);
	}

	@Override
	public boolean updateLaptopReim(LaptopClaimDomain lapClaim, ReimbursementBean reimbursementBean) {
		return appDao.updateLaptopReim(lapClaim,reimbursementBean);
	}

	@Override
	public String getAfsSalMonth(ReimbursementBean reimbursementBean, String fmCode) {
		return appDao.getAfsSalMonth(reimbursementBean,fmCode);
	}

	@Override
	public String getLastTabPaidDate(ReimbursementBean reimbursementBean) {
		return appDao.getLastTabPaidDate(reimbursementBean);
	}

	@Override
	public void getLastTabletClaimDetails(ReimbursementBean reimbursementBean) {
		appDao.getLastTabletClaimDetails(reimbursementBean);
	}

	@Override
	public String getLastTabPaidClaimDate(ReimbursementBean reimbursementBean) {
		return appDao.getLastTabPaidClaimDate(reimbursementBean);
	}

	@Override
	public String getLastTabClaimDate(ReimbursementBean reimbursementBean) {
		return appDao.getLastTabClaimDate(reimbursementBean);
	}

	@Override
	public String getTabPayDate(ReimbursementBean reimbursementBean, String validMonth) {
		return appDao.getTabPayDate(reimbursementBean,validMonth);
	}

	@Override
	public String getTabApplicableDate(ReimbursementBean reimbursementBean) {
		return appDao.getTabApplicableDate(reimbursementBean);
	}

	@Override
	public String getMaxSrNoFromConsent(ReimbursementBean reimbursementBean, String fmCode) {
		return appDao.getMaxSrNoFromConsent(reimbursementBean,fmCode);
	}

	@Override
	public int getTabReimCheck(ReimbursementBean reimbursementBean, String fmCode, int validMonthTab) {
		return appDao.getTabReimCheck(reimbursementBean,fmCode, validMonthTab);
	}

	@Override
	public int getTabDetCheck(ReimbursementBean reimbursementBean, int validMonthTab) {
		return appDao.getTabDetCheck(reimbursementBean,validMonthTab);
	}

	@Override
	public boolean saveTabClaim(TabletClaimDomain tabBean, ReimConsentDomain consentBean) {
		return appDao.saveTabClaim(tabBean,consentBean);
	}

	@Override
	public String getMaxSrNoInTabClaim(String empCode) {
		return appDao.getMaxSrNoInTabClaim(empCode);
	}

	@Override
	public boolean updateTabClaim(ReimbursementBean reimbursementBean, TabletClaimDomain updateBean) {
		return appDao.updateTabClaim(reimbursementBean,updateBean);
	}

	@Override
	public String[] getTransportExpenseDetail(String empCode, TransportExpenseDetailBean transportDetBean) {
		return appDao.getTransportExpenseDetail(empCode,transportDetBean);
	}

	@Override
	public String[] getTransferValidationFlagPart1(ReimbursementBean reimbursementBean) {
		return appDao.getTransferValidationFlagPart1(reimbursementBean);
	}

	@Override
	public boolean getIncidentalDetail(ReimbursementBean reimbursementBean) {
		return appDao.getIncidentalDetail(reimbursementBean);
	}

	@Override
	public void getCarExpenseDetailBean(String empCode, CarExpenseDetailBean carExpenseBean) {
		appDao.getCarExpenseDetailBean(empCode,carExpenseBean);
	}

	@Override
	public String[] getCarArray(CarExpenseDetailBean carExpenseBean) {
		return appDao.getCarArray(carExpenseBean);
	}

	@Override
	public String getTransferFacilityAcmoStatus(ReimbursementBean reimbursementBean) {
		return appDao.getTransferFacilityAcmoStatus(reimbursementBean);
	}

	@Override
	public String getTransferIdAndValidy(ReimbursementBean reimbursementBean) {
		return appDao.getTransferIdAndValidy(reimbursementBean);
	}

	@Override
	public Date getTransferEffDate(ReimbursementBean reimbursementBean) {
		return  appDao.getTransferEffDate(reimbursementBean);
	}

	@Override
	public void getVehicleRegistrationDetail(ReimbursementBean reimbursementBean,VehicleRegistrationBean vehicleBean) {
		appDao.getVehicleRegistrationDetail(reimbursementBean,vehicleBean);
	}

	@Override
	public String getRvmeSubmittedFmCode(String empCode) {
		// TODO Auto-generated method stub
		return appDao.getRvmeSubmittedFmCode(empCode);
	}

	@Override
	public String getRroFlag(ReimbursementBean reimbursementBean) {
		return appDao.getRroFlag(reimbursementBean);
	}

	@Override
	public String getInchargeDetails(ReimbursementBean reimbursementBean) {
		return appDao.getInchargeDetails(reimbursementBean);
	}

	@Override
	public String getLvEmpPPGrade(ReimbursementBean reimbursementBean) {
		return appDao.getLvEmpPPGrade(reimbursementBean);
	}

	@Override
	public String getMobEmpExceptionalCode(String empCode) {
		return appDao.getMobEmpExceptionalCode(empCode);
	}

	@Override
	public List<Object[]> getTelephoneHistory(String empCode) {
		return appDao.getTelephoneHistory(empCode);
	}

	@Override
	public List<Object[]> getRvmeHistoryButton(String empCode) {
		return appDao.getRvmeHistoryButton(empCode);
	}

	@Override
	public boolean saveIncidentalExpense(ReimbursementBean reimbursementBean) {
		return appDao.saveIncidentalExpense(reimbursementBean);
	}

	@Override
	public boolean saveTransPortExpenseForm(ReimbursementBean reimbursementBean) {
		return appDao.saveTransPortExpenseForm(reimbursementBean);
	}

	@Override
	public boolean saveCarExpenseForm(ReimbursementBean reimbursementBean) {
		return appDao.saveCarExpenseForm(reimbursementBean);
	}

	@Override
	public String getCarRateForOthers(Map<String, String> requestData) {
		return appDao.getCarRateForOthers(requestData);
	}

	

	

}
