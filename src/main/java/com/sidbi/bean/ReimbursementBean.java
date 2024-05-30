package com.sidbi.bean;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sidbi.domain.FinancialAssistDataDomain;

public class ReimbursementBean {

	private String empCode;
	private String empName;
	private String empGrade;
	private String category;
	private String branchCode;
	private String dependentRelation;
	private int fmCode;
	private String courseYear;
	private String dependentGrade;
	private String startDate;
	private String endDate;
	private String coursePersuedCode;
	private String coursePassedCode;
	private String percentage;
	private String duration;
	private String payment;
	private String remark;
	private String status;
	private String dependentCode;
	private String lv_sr_no;
	private MultipartFile billUpload;
	private String empStatus;
	private String bookGrantCommonGrade;
	private String isUpdatingPage;
	private Map<String, String> coursePersueList;
	private Map<String, String> coursePassedList;
	private Map<String, String> dependentList;
	private String courseStream;
	private String boarder;
	private String selfDependent;
	private String scholCommonGrade;
	private String startMonthDate;
	private String approveRejectList;
	private String newspaper;
	private String householdCleaning;
	private String entertainmentExpense;
	private String washingAllowance;
	private String residenceOfficeAllowance;
	private String carCleaning;
	private String telephoneOrMobile;
	private String telephoneNo;
	private String serviceProvider;
	private String telephoneInstallationDate;
	private String dataCharges;
	private boolean regularClaimTermsAndCondition;
	private boolean medicalTermsAndCondition;
	private boolean nonPeriodicTermsAndCondition;
	private String isDataChargesAvailed;

	// For declaration Tab Opened
	private String tabOpened;

	// Mobile Claim members
	

	// BriefCase Reimbursement
	private String briefCaseReimb;
	private String breifCaseCost;
	private String BRReason;
	private String BRPurchaseDate;

	// Last BriefCase Data
	private String lastBriefCaseReimb;
	private String lastBreifCaseCost;
	private String lastBRReason;
	private String lastBRPurchaseDate;

	// Reimbursement For BookGrant
	private String isNonPeriodBookAvailed;
	private String bookCost;
	private String bookDetails;
	private String medicalOtpLimit;
	private String claimedAmount;
	private String maxBRLimit;
	private String BREligibilityDate;

	// Book Claim for last Claim
	private String lastBookClaimDate;
	private String lastBookClaimAvailed;
	private String lastBookGrantFlag;
	private String lastBookCost;
	private String lastBookRemark;

	// Medical ReimBursement For Non Period Claim
	private String isNpMedicalAvailed;
	private String npMedicalAmountClaimed;
	private String npMedicalOpdRequestAmount;
	private String npMedicalOptLimit;
	private String lastNpMedicalAvailed;
	private String lastNpMedicalClaimDate;
	private String lastNpMedicalAmountClaimed;
	private String lastNpMedicalPaidFlag;
	private String npMedicalRemAmount;

	// Medical Reimbursement Tab
	private String medTotalAmountPaid;
	private String medMaxAmount;
	private String medTotalVerifiedAmount;

	// Telephone data
	private String lastTelephone;
	private String lastNewspaper;
	private String lastHouseHoldHelp;
	private String lastOffEntExpense;
	private String lastWashingAllowance;
	private String lastDriverSalary;
	private String lastResidenceAllowance;
	private String lastCarCleaning;
	private String lastInternetData;
	private String lastEducationExpense;
	private String lastDataCharges;

	// Reimbursement Availed Data
	private String isNewsPaperAvailed;
	private String isHouseHoldHelpAvailed;
	private String isOfficialEntExpenceAvailed;
	private String isWashingAllowanceAvailed;
	private String isResidenceOfficeAllowanceAvailed;
	private String isCarCleaningAvailed;
	private String isRVMEAvailed;
	private String isTelephoneAvailed;
	private String isRVMEConveyance;
	private String isDriverSalaryAvailed;
	private String isBankCarAvailed;
	private String isEducationExpenseAvailed;
	private String isIncharge;

	// Vehicle Insurance
	private String isNpInsuranceClaimed;
	private String npInsuranceSrNo;
	private String npInsuranceProviderName;
	private String npPolicyNumber;
	private String npInsuredBy;
	private String npInsuranceType;
	private String npInsuranceFrom;
	private String npInsuranceTill;
	private String npInsurancePremiumPaidt;
	private String npVehicleInsurancelaimRemark;
	private String npInsuranceRemark;
	private String npInsuranceRefundDate;
	private String npInsuranceRefund;
	private String npInsuranceRefundVerifyDate;
	private String npInsuranceVerifyStatus;
	private String vehicleInsuranceProceed;
	private String npInsurancePaidStatus;
	private String npInsuranceRefundStatus;
	private String npInsuranceRefundVerifyStatus;
	private String npInsuranceRefundAmount;

	// RVME
	private String isRvmeVehicleClaim;
	private String verifyStatus;
	private String isRvmeNoVehicleClaim;
	private String rvmeVehicleType;
	private String isRvmeDriverSalary;
	private String rvmePlaceOfUse;
	private String rvmeVehicleRegNumber;
	private String rvmeVehicleRegisteredAt;
	private String rvmeVehicleAcquiredOn;
	private String isVehicleProvidedByBank;
	private String rvmeEngineCapacity;
	private String engineNumber;
	private String chasisNumber;
	private String regvalidityDate;
	private String shiftingDate;
	private String residingPlace;
	private String fileName;
	Map<String, String> rvmePlaceOfUseMap;
	private List<String> afsCodeList;
	private String isElgibleForDriverSalary;
	private String epFlag;
	private String epDate;
	private String vhePerPromo;
	private String empBranchCode;
	private String empDesignation;
	private boolean rvmeAgreement;
	private boolean insuranceAgreement;
	private boolean refundAgreement;
	private String nextPrevDesignation;
	private String promoPerEffectiveDate;
	
	
	//Laptop/Mobile Claim Section
	private String isMobilePurchased;
	private String mobileClaimDate;
	private String mobileDetail;
	private String mobileClaimAmount;
	private String mobileFileName;
	private boolean mobileAgreement;
	private MultipartFile mobileBill;
	
	private String mobileCharges;
	private String mobileServiceProvider;
	private String mobileActivationDate;
	private String mobileNumber;
	private boolean mobileChargesAgreement;
	
	private String isLaptopPurchased;
	private String lapPurchaseDate;
	private String lapDetail;
	private String lapClaimAmount;
	private String nextLaptopDate;
	private MultipartFile lapUploadBill;
	private boolean laptopAgreement;
	private String paidDate;
	private String mobileClaimRemark;
	private String countSBLPRDMonth;
	private String laptopBillClaimDate;
	private int mobLapReimCheck;
	private int mobLapDetCheck;
	private boolean BRTermsAndCondition;
	private boolean BookTermsAndCondition;
	
	//TABLET CLAIM SECTION
	private String isTabClaimed;
	private String tabClaimDate;
	private String tabDetail;
	private String tabClaimAmount;
	private MultipartFile uploadBill;
	private boolean tabAgreement;
	private String isPaid;
	private String isVerified;
	private String srNo;
	private String currentDate;
	private boolean regularClaimTermsAndCondition1;
	private String bookLimit;
	private String npBRRemAmount;
	private String npBookRemAmount;
	private String bookVerifyStatus;
	private String medicalVerifyStatus;
	private String rvmeSubmittedFmCode;
	private String rvmeDisabled;
	private String laptopDisabled;
	private String disableHireByBank;
	private String disableResidingPlace;
	private String disableLaptopTab;
	private String disableLaptopElements;
	private String viDisabled;
	
	private String incidentalPaymentMode;
	public String getCarShiftDate() {
		return carShiftDate;
	}

	public void setCarShiftDate(String carShiftDate) {
		this.carShiftDate = carShiftDate;
	}

	public String getCarShifted() {
		return carShifted;
	}

	public void setCarShifted(String carShifted) {
		this.carShifted = carShifted;
	}

	public String getCarDistance() {
		return carDistance;
	}

	public void setCarDistance(String carDistance) {
		this.carDistance = carDistance;
	}

	public String getCarRatePerKm() {
		return carRatePerKm;
	}

	public void setCarRatePerKm(String carRatePerKm) {
		this.carRatePerKm = carRatePerKm;
	}

	public String getCarTotal() {
		return carTotal;
	}

	public void setCarTotal(String carTotal) {
		this.carTotal = carTotal;
	}

	public String getDecBasicAmount() {
		return decBasicAmount;
	}

	public void setDecBasicAmount(String decBasicAmount) {
		this.decBasicAmount = decBasicAmount;
	}

	public String getCarClaimAmount() {
		return carClaimAmount;
	}

	public void setCarClaimAmount(String carClaimAmount) {
		this.carClaimAmount = carClaimAmount;
	}

	public String getRemarkByCap() {
		return remarkByCap;
	}

	public void setRemarkByCap(String remarkByCap) {
		this.remarkByCap = remarkByCap;
	}

	private String incidentalFamilyShiftDate;
	private String incidentalClaimed;
	private String incidentalFamilyShiftFalg;
	private String trfId;
	private boolean incidentalAgreement;
	
	private String houseHoldGoodApplicable;
	private String familyShifted;
	private String goodShiftDate;
	private String transportCost;
	private String eligibleDeclarationAmount;
	private String distanceCovered;
	private String grossWeight;
	private String totalTransportFare;
	private String totalTransportCost;
	private String accomodationType;
	private String typeOfTransfer;
	
	private String carShiftDate;
	private String carShifted;
	private String carDistance;
	private String carRatePerKm;
	private String carTotal;
	private String decBasicAmount;
	private String carClaimAmount;
	private String remarkByCap;
	
	private String oldRegNumber;
	private String newRegNumber;
	private String newRegDate;
	private String regCost;
	private String regStatus;
	private String regRemarkByCap;
	private MultipartFile regFile;
	
	private String trfType;
	private String HrTrfType;
	private String lastIncidentalFlag;
	private String lastTransFlag;
	private String carDriver;
	 
	public String getCarDriver() {
		return carDriver;
	}

	public void setCarDriver(String carDriver) {
		this.carDriver = carDriver;
	}

	public String getLastTransFlag() {
		return lastTransFlag;
	}

	public void setLastTransFlag(String lastTransFlag) {
		this.lastTransFlag = lastTransFlag;
	}

	public String getLastIncidentalFlag() {
		return lastIncidentalFlag;
	}

	public void setLastIncidentalFlag(String lastIncidentalFlag) {
		this.lastIncidentalFlag = lastIncidentalFlag;
	}

	public String getTrfType() {
		return trfType;
	}

	public void setTrfType(String trfType) {
		this.trfType = trfType;
	}

	public String getHrTrfType() {
		return HrTrfType;
	}

	public void setHrTrfType(String hrTrfType) {
		HrTrfType = hrTrfType;
	}

	public String getOldRegNumber() {
		return oldRegNumber;
	}

	public void setOldRegNumber(String oldRegNumber) {
		this.oldRegNumber = oldRegNumber;
	}

	public String getNewRegNumber() {
		return newRegNumber;
	}

	public void setNewRegNumber(String newRegNumber) {
		this.newRegNumber = newRegNumber;
	}

	public String getNewRegDate() {
		return newRegDate;
	}

	public void setNewRegDate(String newRegDate) {
		this.newRegDate = newRegDate;
	}

	public String getRegCost() {
		return regCost;
	}

	public void setRegCost(String regCost) {
		this.regCost = regCost;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getRegRemarkByCap() {
		return regRemarkByCap;
	}

	public void setRegRemarkByCap(String regRemarkByCap) {
		this.regRemarkByCap = regRemarkByCap;
	}

	public MultipartFile getRegFile() {
		return regFile;
	}

	public void setRegFile(MultipartFile regFile) {
		this.regFile = regFile;
	}

	public String getAccomodationType() {
		return accomodationType;
	}

	public void setAccomodationType(String accomodationType) {
		this.accomodationType = accomodationType;
	}

	public String getTypeOfTransfer() {
		return typeOfTransfer;
	}

	public void setTypeOfTransfer(String typeOfTransfer) {
		this.typeOfTransfer = typeOfTransfer;
	}

	public String getHouseHoldGoodApplicable() {
		return houseHoldGoodApplicable;
	}

	public void setHouseHoldGoodApplicable(String houseHoldGoodApplicable) {
		this.houseHoldGoodApplicable = houseHoldGoodApplicable;
	}

	public String getFamilyShifted() {
		return familyShifted;
	}

	public void setFamilyShifted(String familyShifted) {
		this.familyShifted = familyShifted;
	}

	public String getGoodShiftDate() {
		return goodShiftDate;
	}

	public void setGoodShiftDate(String goodShiftDate) {
		this.goodShiftDate = goodShiftDate;
	}

	public String getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(String transportCost) {
		this.transportCost = transportCost;
	}

	public String getEligibleDeclarationAmount() {
		return eligibleDeclarationAmount;
	}

	public void setEligibleDeclarationAmount(String eligibleDeclarationAmount) {
		this.eligibleDeclarationAmount = eligibleDeclarationAmount;
	}

	public String getDistanceCovered() {
		return distanceCovered;
	}

	public void setDistanceCovered(String distanceCovered) {
		this.distanceCovered = distanceCovered;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getTotalTransportFare() {
		return totalTransportFare;
	}

	public void setTotalTransportFare(String totalTransportFare) {
		this.totalTransportFare = totalTransportFare;
	}

	public String getTotalTransportCost() {
		return totalTransportCost;
	}

	public void setTotalTransportCost(String totalTransportCost) {
		this.totalTransportCost = totalTransportCost;
	}

	public boolean getIncidentalAgreement() {
		return incidentalAgreement;
	}

	public void setIncidentalAgreement(boolean incidentalAgreement) {
		this.incidentalAgreement = incidentalAgreement;
	}

	public String getTrfId() {
		return trfId;
	}

	public void setTrfId(String trfId) {
		this.trfId = trfId;
	}

	public String getIncidentalPaymentMode() {
		return incidentalPaymentMode;
	}

	public void setIncidentalPaymentMode(String incidentalPaymentMode) {
		this.incidentalPaymentMode = incidentalPaymentMode;
	}

	public String getIncidentalFamilyShiftDate() {
		return incidentalFamilyShiftDate;
	}

	public void setIncidentalFamilyShiftDate(String incidentalFamilyShiftDate) {
		this.incidentalFamilyShiftDate = incidentalFamilyShiftDate;
	}

	public String getIncidentalClaimed() {
		return incidentalClaimed;
	}

	public void setIncidentalClaimed(String incidentalClaimed) {
		this.incidentalClaimed = incidentalClaimed;
	}

	public String getIncidentalFamilyShiftFalg() {
		return incidentalFamilyShiftFalg;
	}

	public void setIncidentalFamilyShiftFalg(String incidentalFamilyShiftFalg) {
		this.incidentalFamilyShiftFalg = incidentalFamilyShiftFalg;
	}

	public String getViDisabled() {
		return viDisabled;
	}

	public void setViDisabled(String viDisabled) {
		this.viDisabled = viDisabled;
	}

	public String getDisableLaptopElements() {
		return disableLaptopElements;
	}

	public void setDisableLaptopElements(String disableLaptopElements) {
		this.disableLaptopElements = disableLaptopElements;
	}

	public String getDisableLaptopTab() {
		return disableLaptopTab;
	}

	public void setDisableLaptopTab(String disableLaptopTab) {
		this.disableLaptopTab = disableLaptopTab;
	}

	public String getDisableResidingPlace() {
		return disableResidingPlace;
	}

	public void setDisableResidingPlace(String disableResidingPlace) {
		this.disableResidingPlace = disableResidingPlace;
	}

	public String getDisableHireByBank() {
		return disableHireByBank;
	}

	public void setDisableHireByBank(String disableHireByBank) {
		this.disableHireByBank = disableHireByBank;
	}

	public String getLaptopDisabled() {
		return laptopDisabled;
	}

	public void setLaptopDisabled(String laptopDisabled) {
		this.laptopDisabled = laptopDisabled;
	}

	public String getRvmeDisabled() {
		return rvmeDisabled;
	}

	public void setRvmeDisabled(String rvmeDisabled) {
		this.rvmeDisabled = rvmeDisabled;
	}

	public String getRvmeSubmittedFmCode() {
		return rvmeSubmittedFmCode;
	}

	public void setRvmeSubmittedFmCode(String rvmeSubmittedFmCode) {
		this.rvmeSubmittedFmCode = rvmeSubmittedFmCode;
	}

	public String getMedicalVerifyStatus() {
		return medicalVerifyStatus;
	}

	public void setMedicalVerifyStatus(String medicalVerifyStatus) {
		this.medicalVerifyStatus = medicalVerifyStatus;
	}

	public String getBookVerifyStatus() {
		return bookVerifyStatus;
	}

	public void setBookVerifyStatus(String bookVerifyStatus) {
		this.bookVerifyStatus = bookVerifyStatus;
	}

	public String getNpBookRemAmount() {
		return npBookRemAmount;
	}

	public void setNpBookRemAmount(String npBookRemAmount) {
		this.npBookRemAmount = npBookRemAmount;
	}

	public String getNpBRRemAmount() {
		return npBRRemAmount;
	}

	public void setNpBRRemAmount(String npBRRemAmount) {
		this.npBRRemAmount = npBRRemAmount;
	}

	private String rvmeVerifyStatus;
	
	public String getRvmeVerifyStatus() {
		return rvmeVerifyStatus;
	}

	public void setRvmeVerifyStatus(String rvmeVerifyStatus) {
		this.rvmeVerifyStatus = rvmeVerifyStatus;
	}

	public String getBookLimit() {
		return bookLimit;
	}

	public void setBookLimit(String bookLimit) {
		this.bookLimit = bookLimit;
	}

	public boolean getRegularClaimTermsAndCondition1() {
		return regularClaimTermsAndCondition1;
	}

	public void setRegularClaimTermsAndCondition1(boolean regularClaimTermsAndCondition1) {
		this.regularClaimTermsAndCondition1 = regularClaimTermsAndCondition1;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	private String errMsg;
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public boolean getTabAgreement() {
		return tabAgreement;
	}

	public void setTabAgreement(boolean tabAgreement) {
		this.tabAgreement = tabAgreement;
	}

	public String getIsTabClaimed() {
		return isTabClaimed;
	}

	public void setIsTabClaimed(String isTabClaimed) {
		this.isTabClaimed = isTabClaimed;
	}

	public String getTabClaimDate() {
		return tabClaimDate;
	}

	public void setTabClaimDate(String tabClaimDate) {
		this.tabClaimDate = tabClaimDate;
	}

	public String getTabDetail() {
		return tabDetail;
	}

	public void setTabDetail(String tabDetail) {
		this.tabDetail = tabDetail;
	}

	public String getTabClaimAmount() {
		return tabClaimAmount;
	}

	public void setTabClaimAmount(String tabClaimAmount) {
		this.tabClaimAmount = tabClaimAmount;
	}

	public MultipartFile getUploadBill() {
		return uploadBill;
	}

	public void setUploadBill(MultipartFile uploadBill) {
		this.uploadBill = uploadBill;
	}

	public boolean getBRTermsAndCondition() {
		return BRTermsAndCondition;
	}

	public void setBRTermsAndCondition(boolean bRTermsAndCondition) {
		BRTermsAndCondition = bRTermsAndCondition;
	}

	public boolean getBookTermsAndCondition() {
		return BookTermsAndCondition;
	}

	public void setBookTermsAndCondition(boolean bookTermsAndCondition) {
		BookTermsAndCondition = bookTermsAndCondition;
	}

	public int getMobLapReimCheck() {
		return mobLapReimCheck;
	}

	public void setMobLapReimCheck(int mobLapReimCheck) {
		this.mobLapReimCheck = mobLapReimCheck;
	}

	public int getMobLapDetCheck() {
		return mobLapDetCheck;
	}

	public void setMobLapDetCheck(int mobLapDetCheck) {
		this.mobLapDetCheck = mobLapDetCheck;
	}

	public String getLaptopBillClaimDate() {
		return laptopBillClaimDate;
	}

	public void setLaptopBillClaimDate(String laptopBillClaimDate) {
		this.laptopBillClaimDate = laptopBillClaimDate;
	}

	public String getCountSBLPRDMonth() {
		return countSBLPRDMonth;
	}

	public void setCountSBLPRDMonth(String countSBLPRDMonth) {
		this.countSBLPRDMonth = countSBLPRDMonth;
	}

	public String getMobileClaimRemark() {
		return mobileClaimRemark;
	}

	public void setMobileClaimRemark(String mobileClaimRemark) {
		this.mobileClaimRemark = mobileClaimRemark;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public String getIsLaptopPurchased() {
		return isLaptopPurchased;
	}

	public void setIsLaptopPurchased(String isLaptopPurchased) {
		this.isLaptopPurchased = isLaptopPurchased;
	}

	public String getLapPurchaseDate() {
		return lapPurchaseDate;
	}

	public void setLapPurchaseDate(String lapPurchaseDate) {
		this.lapPurchaseDate = lapPurchaseDate;
	}

	public String getLapDetail() {
		return lapDetail;
	}

	public void setLapDetail(String lapDetail) {
		this.lapDetail = lapDetail;
	}

	public String getLapClaimAmount() {
		return lapClaimAmount;
	}

	public void setLapClaimAmount(String lapClaimAmount) {
		this.lapClaimAmount = lapClaimAmount;
	}

	public String getNextLaptopDate() {
		return nextLaptopDate;
	}

	public void setNextLaptopDate(String nextLaptopDate) {
		this.nextLaptopDate = nextLaptopDate;
	}

	public MultipartFile getLapUploadBill() {
		return lapUploadBill;
	}

	public void setLapUploadBill(MultipartFile lapUploadBill) {
		this.lapUploadBill = lapUploadBill;
	}

	public boolean getLaptopAgreement() {
		return laptopAgreement;
	}

	public void setLaptopAgreement(boolean laptopAgreement) {
		this.laptopAgreement = laptopAgreement;
	}

	public String getMobileCharges() {
		return mobileCharges;
	}

	public void setMobileCharges(String mobileCharges) {
		this.mobileCharges = mobileCharges;
	}

	public String getMobileServiceProvider() {
		return mobileServiceProvider;
	}

	public void setMobileServiceProvider(String mobileServiceProvider) {
		this.mobileServiceProvider = mobileServiceProvider;
	}

	public String getMobileActivationDate() {
		return mobileActivationDate;
	}

	public void setMobileActivationDate(String mobileActivationDate) {
		this.mobileActivationDate = mobileActivationDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean getMobileChargesAgreement() {
		return mobileChargesAgreement;
	}

	public void setMobileChargesAgreement(boolean mobileChargesAgreement) {
		this.mobileChargesAgreement = mobileChargesAgreement;
	}

	public String getIsMobilePurchased() {
		return isMobilePurchased;
	}

	public void setIsMobilePurchased(String isMobilePurchased) {
		this.isMobilePurchased = isMobilePurchased;
	}

	public String getNpInsuranceRefundAmount() {
		return npInsuranceRefundAmount;
	}

	public void setNpInsuranceRefundAmount(String npInsuranceRefundAmount) {
		this.npInsuranceRefundAmount = npInsuranceRefundAmount;
	}

	public String getNpInsuranceRefundStatus() {
		return npInsuranceRefundStatus;
	}

	public void setNpInsuranceRefundStatus(String npInsuranceRefundStatus) {
		this.npInsuranceRefundStatus = npInsuranceRefundStatus;
	}

	public String getNpInsuranceRefundVerifyStatus() {
		return npInsuranceRefundVerifyStatus;
	}

	public void setNpInsuranceRefundVerifyStatus(String npInsuranceRefundVerifyStatus) {
		this.npInsuranceRefundVerifyStatus = npInsuranceRefundVerifyStatus;
	}

	public String getNpInsurancePaidStatus() {
		return npInsurancePaidStatus;
	}

	public void setNpInsurancePaidStatus(String npInsurancePaidStatus) {
		this.npInsurancePaidStatus = npInsurancePaidStatus;
	}

	public String getVehicleInsuranceProceed() {
		return vehicleInsuranceProceed;
	}

	public void setVehicleInsuranceProceed(String vehicleInsuranceProceed) {
		this.vehicleInsuranceProceed = vehicleInsuranceProceed;
	}

	public String getNpInsuranceVerifyStatus() {
		return npInsuranceVerifyStatus;
	}

	public void setNpInsuranceVerifyStatus(String npInsuranceVerifyStatus) {
		this.npInsuranceVerifyStatus = npInsuranceVerifyStatus;
	}

	public String getNpInsuranceRefundVerifyDate() {
		return npInsuranceRefundVerifyDate;
	}

	public void setNpInsuranceRefundVerifyDate(String npInsuranceRefundVerifyDate) {
		this.npInsuranceRefundVerifyDate = npInsuranceRefundVerifyDate;
	}

	public String getNpInsuredBy() {
		return npInsuredBy;
	}

	public void setNpInsuredBy(String npInsuredBy) {
		this.npInsuredBy = npInsuredBy;
	}

	public String getNpInsuranceSrNo() {
		return npInsuranceSrNo;
	}

	public void setNpInsuranceSrNo(String npInsuranceSrNo) {
		this.npInsuranceSrNo = npInsuranceSrNo;
	}

	public String getPromoPerEffectiveDate() {
		return promoPerEffectiveDate;
	}

	public void setPromoPerEffectiveDate(String promoPerEffectiveDate) {
		this.promoPerEffectiveDate = promoPerEffectiveDate;
	}

	public String getNextPrevDesignation() {
		return nextPrevDesignation;
	}

	public void setNextPrevDesignation(String nextPrevDesignation) {
		this.nextPrevDesignation = nextPrevDesignation;
	}

	public boolean isRefundAgreement() {
		return refundAgreement;
	}

	public void setRefundAgreement(boolean refundAgreement) {
		this.refundAgreement = refundAgreement;
	}

	public String getNpInsurancePremiumPaidt() {
		return npInsurancePremiumPaidt;
	}

	public void setNpInsurancePremiumPaidt(String npInsurancePremiumPaidt) {
		this.npInsurancePremiumPaidt = npInsurancePremiumPaidt;
	}

	public String getNpVehicleInsurancelaimRemark() {
		return npVehicleInsurancelaimRemark;
	}

	public void setNpVehicleInsurancelaimRemark(String npVehicleInsurancelaimRemark) {
		this.npVehicleInsurancelaimRemark = npVehicleInsurancelaimRemark;
	}

	public String getNpInsuranceRemark() {
		return npInsuranceRemark;
	}

	public void setNpInsuranceRemark(String npInsuranceRemark) {
		this.npInsuranceRemark = npInsuranceRemark;
	}

	public boolean getInsuranceAgreement() {
		return insuranceAgreement;
	}

	public void setInsuranceAgreement(boolean insuranceAgreement) {
		this.insuranceAgreement = insuranceAgreement;
	}

	public String getNpInsuranceRefundDate() {
		return npInsuranceRefundDate;
	}

	public void setNpInsuranceRefundDate(String npInsuranceRefundDate) {
		this.npInsuranceRefundDate = npInsuranceRefundDate;
	}

	public String getNpInsuranceRefund() {
		return npInsuranceRefund;
	}

	public void setNpInsuranceRefund(String npInsuranceRefund) {
		this.npInsuranceRefund = npInsuranceRefund;
	}

	public boolean getRvmeAgreement() {
		return rvmeAgreement;
	}

	public void setRvmeAgreement(boolean rvmeAgreement) {
		this.rvmeAgreement = rvmeAgreement;
	}

	public String getEmpBranchCode() {
		return empBranchCode;
	}

	public void setEmpBranchCode(String empBranchCode) {
		this.empBranchCode = empBranchCode;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public String getEpFlag() {
		return epFlag;
	}

	public void setEpFlag(String epFlag) {
		this.epFlag = epFlag;
	}

	public String getEpDate() {
		return epDate;
	}

	public void setEpDate(String epDate) {
		this.epDate = epDate;
	}

	public String getVhePerPromo() {
		return vhePerPromo;
	}

	public void setVhePerPromo(String vhePerPromo) {
		this.vhePerPromo = vhePerPromo;
	}

	public String getIsElgibleForDriverSalary() {
		return isElgibleForDriverSalary;
	}

	public void setIsElgibleForDriverSalary(String isElgibleForDriverSalary) {
		this.isElgibleForDriverSalary = isElgibleForDriverSalary;
	}

	public List<String> getAfsCodeList() {
		return afsCodeList;
	}

	public void setAfsCodeList(List<String> afsCodeList) {
		this.afsCodeList = afsCodeList;
	}

	private String commonGrade;

	public String getCommonGrade() {
		return commonGrade;
	}

	public void setCommonGrade(String commonGrade) {
		this.commonGrade = commonGrade;
	}

	public Map<String, String> getRvmePlaceOfUseMap() {
		return rvmePlaceOfUseMap;
	}

	public void setRvmePlaceOfUseMap(Map<String, String> rvmePlaceOfUseMap) {
		this.rvmePlaceOfUseMap = rvmePlaceOfUseMap;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResidingPlace() {
		return residingPlace;
	}

	public void setResidingPlace(String residingPlace) {
		this.residingPlace = residingPlace;
	}

	public String getShiftingDate() {
		return shiftingDate;
	}

	public void setShiftingDate(String shiftingDate) {
		this.shiftingDate = shiftingDate;
	}

	public String getRegvalidityDate() {
		return regvalidityDate;
	}

	public void setRegvalidityDate(String regvalidityDate) {
		this.regvalidityDate = regvalidityDate;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getIsVehicleProvidedByBank() {
		return isVehicleProvidedByBank;
	}

	public void setIsVehicleProvidedByBank(String isVehicleProvidedByBank) {
		this.isVehicleProvidedByBank = isVehicleProvidedByBank;
	}

	public String getRvmeEngineCapacity() {
		return rvmeEngineCapacity;
	}

	public void setRvmeEngineCapacity(String rvmeEngineCapacity) {
		this.rvmeEngineCapacity = rvmeEngineCapacity;
	}

	public String getRvmeVehicleRegisteredAt() {
		return rvmeVehicleRegisteredAt;
	}

	public void setRvmeVehicleRegisteredAt(String rvmeVehicleRegisteredAt) {
		this.rvmeVehicleRegisteredAt = rvmeVehicleRegisteredAt;
	}

	public String getRvmeVehicleAcquiredOn() {
		return rvmeVehicleAcquiredOn;
	}

	public void setRvmeVehicleAcquiredOn(String rvmeVehicleAcquiredOn) {
		this.rvmeVehicleAcquiredOn = rvmeVehicleAcquiredOn;
	}

	public String getRvmePlaceOfUse() {
		return rvmePlaceOfUse;
	}

	public void setRvmePlaceOfUse(String rvmePlaceOfUse) {
		this.rvmePlaceOfUse = rvmePlaceOfUse;
	}

	public String getRvmeVehicleRegNumber() {
		return rvmeVehicleRegNumber;
	}

	public void setRvmeVehicleRegNumber(String rvmeVehicleRegNumber) {
		this.rvmeVehicleRegNumber = rvmeVehicleRegNumber;
	}

	public String getIsRvmeDriverSalary() {
		return isRvmeDriverSalary;
	}

	public void setIsRvmeDriverSalary(String isRvmeDriverSalary) {
		this.isRvmeDriverSalary = isRvmeDriverSalary;
	}

	public String getIsRvmeVehicleClaim() {
		return isRvmeVehicleClaim;
	}

	public void setIsRvmeVehicleClaim(String isRvmeVehicleClaim) {
		this.isRvmeVehicleClaim = isRvmeVehicleClaim;
	}

	public String getIsRvmeNoVehicleClaim() {
		return isRvmeNoVehicleClaim;
	}

	public void setIsRvmeNoVehicleClaim(String isRvmeNoVehicleClaim) {
		this.isRvmeNoVehicleClaim = isRvmeNoVehicleClaim;
	}

	public String getRvmeVehicleType() {
		return rvmeVehicleType;
	}

	public void setRvmeVehicleType(String rvmeVehicleType) {
		this.rvmeVehicleType = rvmeVehicleType;
	}

	public String getIsNpInsuranceClaimed() {
		return isNpInsuranceClaimed;
	}

	public void setIsNpInsuranceClaimed(String isNpInsuranceClaimed) {
		this.isNpInsuranceClaimed = isNpInsuranceClaimed;
	}

	public String getNpInsuranceProviderName() {
		return npInsuranceProviderName;
	}

	public void setNpInsuranceProviderName(String npInsuranceProviderName) {
		this.npInsuranceProviderName = npInsuranceProviderName;
	}

	public String getNpMedicalOpdRequestAmount() {
		return npMedicalOpdRequestAmount;
	}

	public void setNpMedicalOpdRequestAmount(String npMedicalOpdRequestAmount) {
		this.npMedicalOpdRequestAmount = npMedicalOpdRequestAmount;
	}

	public String getNpPolicyNumber() {
		return npPolicyNumber;
	}

	public void setNpPolicyNumber(String npPolicyNumber) {
		this.npPolicyNumber = npPolicyNumber;
	}

	public String getNpInsuranceType() {
		return npInsuranceType;
	}

	public void setNpInsuranceType(String npInsuranceType) {
		this.npInsuranceType = npInsuranceType;
	}

	public String getNpInsuranceFrom() {
		return npInsuranceFrom;
	}

	public void setNpInsuranceFrom(String npInsuranceFrom) {
		this.npInsuranceFrom = npInsuranceFrom;
	}

	public String getNpInsuranceTill() {
		return npInsuranceTill;
	}

	public void setNpInsuranceTill(String npInsuranceTill) {
		this.npInsuranceTill = npInsuranceTill;
	}

	public String getLastBriefCaseReimb() {
		return lastBriefCaseReimb;
	}

	public void setLastBriefCaseReimb(String lastBriefCaseReimb) {
		this.lastBriefCaseReimb = lastBriefCaseReimb;
	}

	public String getLastBreifCaseCost() {
		return lastBreifCaseCost;
	}

	public void setLastBreifCaseCost(String lastBreifCaseCost) {
		this.lastBreifCaseCost = lastBreifCaseCost;
	}

	public String getLastBRReason() {
		return lastBRReason;
	}

	public void setLastBRReason(String lastBRReason) {
		this.lastBRReason = lastBRReason;
	}

	public String getLastBRPurchaseDate() {
		return lastBRPurchaseDate;
	}

	public void setLastBRPurchaseDate(String lastBRPurchaseDate) {
		this.lastBRPurchaseDate = lastBRPurchaseDate;
	}

	public String getBRPurchaseDate() {
		return BRPurchaseDate;
	}

	public void setBRPurchaseDate(String bRPurchaseDate) {
		BRPurchaseDate = bRPurchaseDate;
	}

	public String getIsIncharge() {
		return isIncharge;
	}

	public void setIsIncharge(String isIncharge) {
		this.isIncharge = isIncharge;
	}

	public String getTabOpened() {
		return tabOpened;
	}

	public void setTabOpened(String tabOpened) {
		this.tabOpened = tabOpened;
	}

	public String getTelephoneInstallationDate() {
		return telephoneInstallationDate;
	}

	public void setTelephoneInstallationDate(String telephoneInstallationDate) {
		this.telephoneInstallationDate = telephoneInstallationDate;
	}

	public boolean getRegularClaimTermsAndCondition() {
		return regularClaimTermsAndCondition;
	}

	public void setRegularClaimTermsAndCondition(boolean regularClaimTermsAndCondition) {
		this.regularClaimTermsAndCondition = regularClaimTermsAndCondition;
	}

	public boolean getMedicalTermsAndCondition() {
		return medicalTermsAndCondition;
	}

	public void setMedicalTermsAndCondition(boolean medicalTermsAndCondition) {
		this.medicalTermsAndCondition = medicalTermsAndCondition;
	}

	public boolean getNonPeriodicTermsAndCondition() {
		return nonPeriodicTermsAndCondition;
	}

	public void setNonPeriodicTermsAndCondition(boolean nonPeriodicTermsAndCondition) {
		this.nonPeriodicTermsAndCondition = nonPeriodicTermsAndCondition;
	}

	public String getLastNewspaper() {
		return lastNewspaper;
	}

	public void setLastNewspaper(String lastNewspaper) {
		this.lastNewspaper = lastNewspaper;
	}

	public String getLastHouseHoldHelp() {
		return lastHouseHoldHelp;
	}

	public void setLastHouseHoldHelp(String lastHouseHoldHelp) {
		this.lastHouseHoldHelp = lastHouseHoldHelp;
	}

	public String getLastOffEntExpense() {
		return lastOffEntExpense;
	}

	public void setLastOffEntExpense(String lastOffEntExpense) {
		this.lastOffEntExpense = lastOffEntExpense;
	}

	public String getLastWashingAllowance() {
		return lastWashingAllowance;
	}

	public void setLastWashingAllowance(String lastWashingAllowance) {
		this.lastWashingAllowance = lastWashingAllowance;
	}

	public String getLastDriverSalary() {
		return lastDriverSalary;
	}

	public void setLastDriverSalary(String lastDriverSalary) {
		this.lastDriverSalary = lastDriverSalary;
	}

	public String getLastBookClaimAvailed() {
		return lastBookClaimAvailed;
	}

	public void setLastBookClaimAvailed(String lastBookClaimAvailed) {
		this.lastBookClaimAvailed = lastBookClaimAvailed;
	}

	public String getLastResidenceAllowance() {
		return lastResidenceAllowance;
	}

	public void setLastResidenceAllowance(String lastResidenceAllowance) {
		this.lastResidenceAllowance = lastResidenceAllowance;
	}

	public String getLastBookClaimDate() {
		return lastBookClaimDate;
	}

	public void setLastBookClaimDate(String lastBookClaimDate) {
		this.lastBookClaimDate = lastBookClaimDate;
	}

	public String getLastBookGrantFlag() {
		return lastBookGrantFlag;
	}

	public void setLastBookGrantFlag(String lastBookGrantFlag) {
		this.lastBookGrantFlag = lastBookGrantFlag;
	}

	public String getLastBookCost() {
		return lastBookCost;
	}

	public void setLastBookCost(String lastBookCost) {
		this.lastBookCost = lastBookCost;
	}

	public String getLastBookRemark() {
		return lastBookRemark;
	}

	public void setLastBookRemark(String lastBookRemark) {
		this.lastBookRemark = lastBookRemark;
	}

	public String getBREligibilityDate() {
		return BREligibilityDate;
	}

	public void setBREligibilityDate(String bREligibilityDate) {
		BREligibilityDate = bREligibilityDate;
	}

	public String getLastCarCleaning() {
		return lastCarCleaning;
	}

	public void setLastCarCleaning(String lastCarCleaning) {
		this.lastCarCleaning = lastCarCleaning;
	}

	public String getMaxBRLimit() {
		return maxBRLimit;
	}

	public void setMaxBRLimit(String maxBRLimit) {
		this.maxBRLimit = maxBRLimit;
	}

	public String getLastInternetData() {
		return lastInternetData;
	}

	public void setLastInternetData(String lastInternetData) {
		this.lastInternetData = lastInternetData;
	}

	public String getLastEducationExpense() {
		return lastEducationExpense;
	}

	public void setLastEducationExpense(String lastEducationExpense) {
		this.lastEducationExpense = lastEducationExpense;
	}

	public String getNpMedicalRemAmount() {
		return npMedicalRemAmount;
	}

	public void setNpMedicalRemAmount(String npMedicalRemAmount) {
		this.npMedicalRemAmount = npMedicalRemAmount;
	}

	public String getIsNewsPaperAvailed() {
		return isNewsPaperAvailed;
	}

	public void setIsNewsPaperAvailed(String isNewsPaperAvailed) {
		this.isNewsPaperAvailed = isNewsPaperAvailed;
	}

	public String getBRReason() {
		return BRReason;
	}

	public void setBRReason(String bRReason) {
		BRReason = bRReason;
	}

	public String getIsHouseHoldHelpAvailed() {
		return isHouseHoldHelpAvailed;
	}

	public void setIsHouseHoldHelpAvailed(String isHouseHoldHelpAvailed) {
		this.isHouseHoldHelpAvailed = isHouseHoldHelpAvailed;
	}

	public String getIsOfficialEntExpenceAvailed() {
		return isOfficialEntExpenceAvailed;
	}

	public void setIsOfficialEntExpenceAvailed(String isOfficialEntExpenceAvailed) {
		this.isOfficialEntExpenceAvailed = isOfficialEntExpenceAvailed;
	}

	public String getIsWashingAllowanceAvailed() {
		return isWashingAllowanceAvailed;
	}

	public void setIsWashingAllowanceAvailed(String isWashingAllowanceAvailed) {
		this.isWashingAllowanceAvailed = isWashingAllowanceAvailed;
	}

	public String getIsResidenceOfficeAllowanceAvailed() {
		return isResidenceOfficeAllowanceAvailed;
	}

	public void setIsResidenceOfficeAllowanceAvailed(String isResidenceOfficeAllowanceAvailed) {
		this.isResidenceOfficeAllowanceAvailed = isResidenceOfficeAllowanceAvailed;
	}

	public String getLastDataCharges() {
		return lastDataCharges;
	}

	public void setLastDataCharges(String lastDataCharges) {
		this.lastDataCharges = lastDataCharges;
	}

	public String getIsCarCleaningAvailed() {
		return isCarCleaningAvailed;
	}

	public void setIsCarCleaningAvailed(String isCarCleaningAvailed) {
		this.isCarCleaningAvailed = isCarCleaningAvailed;
	}

	public String getIsRVMEAvailed() {
		return isRVMEAvailed;
	}

	public void setIsRVMEAvailed(String isRVMEAvailed) {
		this.isRVMEAvailed = isRVMEAvailed;
	}

	public String getIsTelephoneAvailed() {
		return isTelephoneAvailed;
	}

	public void setIsTelephoneAvailed(String isTelephoneAvailed) {
		this.isTelephoneAvailed = isTelephoneAvailed;
	}

	public String getIsRVMEConveyance() {
		return isRVMEConveyance;
	}

	public void setIsRVMEConveyance(String isRVMEConveyance) {
		this.isRVMEConveyance = isRVMEConveyance;
	}

	public String getIsNpMedicalAvailed() {
		return isNpMedicalAvailed;
	}

	public void setIsNpMedicalAvailed(String isNpMedicalAvailed) {
		this.isNpMedicalAvailed = isNpMedicalAvailed;
	}

	public String getNpMedicalAmountClaimed() {
		return npMedicalAmountClaimed;
	}

	public void setNpMedicalAmountClaimed(String npMedicalAmountClaimed) {
		this.npMedicalAmountClaimed = npMedicalAmountClaimed;
	}

	public String getNpMedicalOptLimit() {
		return npMedicalOptLimit;
	}

	public void setNpMedicalOptLimit(String npMedicalOptLimit) {
		this.npMedicalOptLimit = npMedicalOptLimit;
	}

	public String getLastNpMedicalAvailed() {
		return lastNpMedicalAvailed;
	}

	public void setLastNpMedicalAvailed(String lastNpMedicalAvailed) {
		this.lastNpMedicalAvailed = lastNpMedicalAvailed;
	}

	public String getLastNpMedicalClaimDate() {
		return lastNpMedicalClaimDate;
	}

	public void setLastNpMedicalClaimDate(String lastNpMedicalClaimDate) {
		this.lastNpMedicalClaimDate = lastNpMedicalClaimDate;
	}

	public String getLastNpMedicalAmountClaimed() {
		return lastNpMedicalAmountClaimed;
	}

	public void setLastNpMedicalAmountClaimed(String lastNpMedicalAmountClaimed) {
		this.lastNpMedicalAmountClaimed = lastNpMedicalAmountClaimed;
	}

	public String getLastNpMedicalPaidFlag() {
		return lastNpMedicalPaidFlag;
	}

	public void setLastNpMedicalPaidFlag(String lastNpMedicalPaidFlag) {
		this.lastNpMedicalPaidFlag = lastNpMedicalPaidFlag;
	}

	public String getIsDriverSalaryAvailed() {
		return isDriverSalaryAvailed;
	}

	public void setIsDriverSalaryAvailed(String isDriverSalaryAvailed) {
		this.isDriverSalaryAvailed = isDriverSalaryAvailed;
	}

	public String getIsBankCarAvailed() {
		return isBankCarAvailed;
	}

	public void setIsBankCarAvailed(String isBankCarAvailed) {
		this.isBankCarAvailed = isBankCarAvailed;
	}

	public String getIsEducationExpenseAvailed() {
		return isEducationExpenseAvailed;
	}

	public void setIsEducationExpenseAvailed(String isEducationExpenseAvailed) {
		this.isEducationExpenseAvailed = isEducationExpenseAvailed;
	}

	public String getLastTelephone() {
		return lastTelephone;
	}

	public void setLastTelephone(String lastTelephone) {
		this.lastTelephone = lastTelephone;
	}

	public String getMedTotalVerifiedAmount() {
		return medTotalVerifiedAmount;
	}

	public void setMedTotalVerifiedAmount(String medTotalVerifiedAmount) {
		this.medTotalVerifiedAmount = medTotalVerifiedAmount;
	}

	public String getMedTotalAmountPaid() {
		return medTotalAmountPaid;
	}

	public void setMedTotalAmountPaid(String medTotalAmountPaid) {
		this.medTotalAmountPaid = medTotalAmountPaid;
	}

	public String getMedMaxAmount() {
		return medMaxAmount;
	}

	public void setMedMaxAmount(String medMaxAmount) {
		this.medMaxAmount = medMaxAmount;
	}

	public String getBriefCaseReimb() {
		return briefCaseReimb;
	}

	public void setBriefCaseReimb(String briefCaseReimb) {
		this.briefCaseReimb = briefCaseReimb;
	}

	public String getBreifCaseCost() {
		return breifCaseCost;
	}

	public void setBreifCaseCost(String breifCaseCost) {
		this.breifCaseCost = breifCaseCost;
	}

	public String getBookCost() {
		return bookCost;
	}

	public void setBookCost(String bookCost) {
		this.bookCost = bookCost;
	}

	public String getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}

	public String getMedicalOtpLimit() {
		return medicalOtpLimit;
	}

	public void setMedicalOtpLimit(String medicalOtpLimit) {
		this.medicalOtpLimit = medicalOtpLimit;
	}

	public String getClaimedAmount() {
		return claimedAmount;
	}

	public void setClaimedAmount(String claimedAmount) {
		this.claimedAmount = claimedAmount;
	}

	public String getIsNonPeriodBookAvailed() {
		return isNonPeriodBookAvailed;
	}

	public void setIsNonPeriodBookAvailed(String isNonPeriodBookAvailed) {
		this.isNonPeriodBookAvailed = isNonPeriodBookAvailed;
	}

	public String getMobileClaimDate() {
		return mobileClaimDate;
	}

	public void setMobileClaimDate(String mobileClaimDate) {
		this.mobileClaimDate = mobileClaimDate;
	}

	public String getMobileDetail() {
		return mobileDetail;
	}

	public void setMobileDetail(String mobileDetail) {
		this.mobileDetail = mobileDetail;
	}

	public String getMobileClaimAmount() {
		return mobileClaimAmount;
	}

	public void setMobileClaimAmount(String mobileClaimAmount) {
		this.mobileClaimAmount = mobileClaimAmount;
	}

	public String getMobileFileName() {
		return mobileFileName;
	}

	public void setMobileFileName(String mobileFileName) {
		this.mobileFileName = mobileFileName;
	}

	public boolean getMobileAgreement() {
		return mobileAgreement;
	}

	public void setMobileAgreement(boolean mobileAgreement) {
		this.mobileAgreement = mobileAgreement;
	}

	public MultipartFile getMobileBill() {
		return mobileBill;
	}

	public void setMobileBill(MultipartFile mobileBill) {
		this.mobileBill = mobileBill;
	}

	public String getTelephoneOrMobile() {
		return telephoneOrMobile;
	}

	public void setTelephoneOrMobile(String telephoneOrMobile) {
		this.telephoneOrMobile = telephoneOrMobile;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getDataCharges() {
		return dataCharges;
	}

	public void setDataCharges(String dataCharges) {
		this.dataCharges = dataCharges;
	}

	public String getHouseholdCleaning() {
		return householdCleaning;
	}

	public void setHouseholdCleaning(String householdCleaning) {
		this.householdCleaning = householdCleaning;
	}

	public String getEntertainmentExpense() {
		return entertainmentExpense;
	}

	public void setEntertainmentExpense(String entertainmentExpense) {
		this.entertainmentExpense = entertainmentExpense;
	}

	public String getWashingAllowance() {
		return washingAllowance;
	}

	public void setWashingAllowance(String washingAllowance) {
		this.washingAllowance = washingAllowance;
	}

	public String getResidenceOfficeAllowance() {
		return residenceOfficeAllowance;
	}

	public void setResidenceOfficeAllowance(String residenceOfficeAllowance) {
		this.residenceOfficeAllowance = residenceOfficeAllowance;
	}

	public String getCarCleaning() {
		return carCleaning;
	}

	public void setCarCleaning(String carCleaning) {
		this.carCleaning = carCleaning;
	}

	public String getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(String newspaper) {
		this.newspaper = newspaper;
	}

	public String getApproveRejectList() {
		return approveRejectList;
	}

	public void setApproveRejectList(String approveRejectList) {
		this.approveRejectList = approveRejectList;
	}

	private List<FinancialAssistDataDomain> reimbursementVerificationList;

	public List<FinancialAssistDataDomain> getReimbursementVerificationList() {
		return reimbursementVerificationList;
	}

	public void setReimbursementVerificationList(List<FinancialAssistDataDomain> reimbursementVerificationList) {
		this.reimbursementVerificationList = reimbursementVerificationList;
	}

	public String getStartMonthDate() {
		return startMonthDate;
	}

	public void setStartMonthDate(String startMonthDate) {
		this.startMonthDate = startMonthDate;
	}

	public String getScholCommonGrade() {
		return scholCommonGrade;
	}

	public void setScholCommonGrade(String scholCommonGrade) {
		this.scholCommonGrade = scholCommonGrade;
	}

	public String getSelfDependent() {
		return selfDependent;
	}

	public void setSelfDependent(String selfDependent) {
		this.selfDependent = selfDependent;
	}

	public String getBoarder() {
		return boarder;
	}

	public void setBoarder(String boarder) {
		this.boarder = boarder;
	}

	public String getCourseStream() {
		return courseStream;
	}

	public void setCourseStream(String courseStream) {
		this.courseStream = courseStream;
	}

	public String getIsUpdatingPage() {
		return isUpdatingPage;
	}

	public void setIsUpdatingPage(String isUpdatingPage) {
		this.isUpdatingPage = isUpdatingPage;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBookGrantCommonGrade() {
		return bookGrantCommonGrade;
	}

	public void setBookGrantCommonGrade(String bookGrantCommonGrade) {
		this.bookGrantCommonGrade = bookGrantCommonGrade;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	private String AFA_SEL_DEPD;

	public String getAFA_SEL_DEPD() {
		return AFA_SEL_DEPD;
	}

	public void setAFA_SEL_DEPD(String aFA_SEL_DEPD) {
		AFA_SEL_DEPD = aFA_SEL_DEPD;
	}

	private List<PreviousApplication> perviousApplication;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDependentRelation() {
		return dependentRelation;
	}

	public void setDependentRelation(String dependentRelation) {
		this.dependentRelation = dependentRelation;
	}

	public int getFmCode() {
		return fmCode;
	}

	public void setFmCode(int fmCode) {
		this.fmCode = fmCode;
	}

	public String getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}

	public String getDependentGrade() {
		return dependentGrade;
	}

	public void setDependentGrade(String dependentGrade) {
		this.dependentGrade = dependentGrade;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCoursePersuedCode() {
		return coursePersuedCode;
	}

	public void setCoursePersuedCode(String coursePersuedCode) {
		this.coursePersuedCode = coursePersuedCode;
	}

	public String getCoursePassedCode() {
		return coursePassedCode;
	}

	public void setCoursePassedCode(String coursePassedCode) {
		this.coursePassedCode = coursePassedCode;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDependentCode() {
		return dependentCode;
	}

	public void setDependentCode(String dependentCode) {
		this.dependentCode = dependentCode;
	}

	public String getLv_sr_no() {
		return lv_sr_no;
	}

	public void setLv_sr_no(String lv_sr_no) {
		this.lv_sr_no = lv_sr_no;
	}

	public MultipartFile getBillUpload() {
		return billUpload;
	}

	public void setBillUpload(MultipartFile billUpload) {
		this.billUpload = billUpload;
	}

	public List<PreviousApplication> getPerviousApplication() {
		return perviousApplication;
	}

	public void setPerviousApplication(List<PreviousApplication> perviousApplication) {
		this.perviousApplication = perviousApplication;
	}

	public Map<String, String> getCoursePersueList() {
		return coursePersueList;
	}

	public void setCoursePersueList(Map<String, String> coursePersueList) {
		this.coursePersueList = coursePersueList;
	}

	public Map<String, String> getCoursePassedList() {
		return coursePassedList;
	}

	public void setCoursePassedList(Map<String, String> coursePassedList) {
		this.coursePassedList = coursePassedList;
	}

	public Map<String, String> getDependentList() {
		return dependentList;
	}

	public void setDependentList(Map<String, String> dependentList) {
		this.dependentList = dependentList;
	}

	public String getIsDataChargesAvailed() {
		return isDataChargesAvailed;
	}

	public void setIsDataChargesAvailed(String isDataChargesAvailed) {
		this.isDataChargesAvailed = isDataChargesAvailed;
	}

}
