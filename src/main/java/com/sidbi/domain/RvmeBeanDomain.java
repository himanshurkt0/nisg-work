package com.sidbi.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(RvmeVehicleCompositeKey.class)
@Table(name = "cadmin.adm_vehicle_det")
public class RvmeBeanDomain{
	
	@Id
	@Column(name = "AVD_EMP_CD")
	private String empCode;
	
	@Column(name = "avd_vehicle_type")
	private String vehicleType;
	
	
	@Column(name = "avd_regn_no")
	private String registerNum;
	
	
	@Column(name = "avd_regd_place")
	private String registeredPlace;
	
	
	@Column(name = "avd_maint_place")
	private String maintPlace;
	
	
	@Column(name = "avd_acquired_dt")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private String acquiredDate;
	
	
	@Column(name = "AVD_FW_OWN_HIRED_BANK")
	private String hiredFromBank;
	
	
	@Column(name = "AVD_FW_ENG_CC_LESS_16")
	private String engineCCLessThan16;
	
	
	@Column(name = "AVD_VEHICLE_CHASIS_NO")
	private String chasisNumber;
	
	@Column(name = "AVD_VEHICLE_ENGINE_NO")
	private String engineNumber;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "AVD_VEHICLE_RC_VALID_DATE")
	private String rcValidDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "avd_shift_dt")
	private String shiftDate;
	
	@Column(name = "avd_verify_status")
	private String verifyStatus;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "avd_created_dt")
	private String createdDate;
	
	@Id
	@Column(name = "avd_sr_no")
	private String srNumber;
	
	
	@Column(name = "AVD_FILE_NAME")
	private String fileName;
	
	@Column(name = "AVD_FILE_CONTENT")
	private byte[] billUpload;


	public byte[] getBillUpload() {
		return billUpload;
	}


	public void setBillUpload(byte[] billUpload) {
		this.billUpload = billUpload;
	}


	public String getEmpCode() {
		return empCode;
	}


	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	public String getVehicleType() {
		return vehicleType;
	}


	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}


	public String getRegisterNum() {
		return registerNum;
	}


	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}


	public String getRegisteredPlace() {
		return registeredPlace;
	}


	public void setRegisteredPlace(String registeredPlace) {
		this.registeredPlace = registeredPlace;
	}


	public String getMaintPlace() {
		return maintPlace;
	}


	public void setMaintPlace(String maintPlace) {
		this.maintPlace = maintPlace;
	}


	public String getAcquiredDate() {
		return acquiredDate;
	}


	public void setAcquiredDate(String acquiredDate) {
		this.acquiredDate = acquiredDate;
	}


	public String getHiredFromBank() {
		return hiredFromBank;
	}


	public void setHiredFromBank(String hiredFromBank) {
		this.hiredFromBank = hiredFromBank;
	}


	public String getEngineCCLessThan16() {
		return engineCCLessThan16;
	}


	public void setEngineCCLessThan16(String engineCCLessThan16) {
		this.engineCCLessThan16 = engineCCLessThan16;
	}


	public String getChasisNumber() {
		return chasisNumber;
	}


	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}


	public String getEngineNumber() {
		return engineNumber;
	}


	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}


	public String getRcValidDate() {
		return rcValidDate;
	}


	public void setRcValidDate(String rcValidDate) {
		this.rcValidDate = rcValidDate;
	}


	public String getShiftDate() {
		return shiftDate;
	}


	public void setShiftDate(String shiftDate) {
		this.shiftDate = shiftDate;
	}


	public String getVerifyStatus() {
		return verifyStatus;
	}


	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getSrNumber() {
		return srNumber;
	}


	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
