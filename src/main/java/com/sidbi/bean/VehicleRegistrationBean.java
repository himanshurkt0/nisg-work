package com.sidbi.bean;

import java.io.Serializable;
import java.util.Date;

public class VehicleRegistrationBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date registrationDate;
	private String oldRegNo;
	private String newRegNo;
	private String regCost;
	private String verifyFlag;
	private String paidFlag;
	private String srNo;
	private String fileName;
	private String remarks;
	private String trfId;
	
	public String getTrfId() {
		return trfId;
	}
	public void setTrfId(String trfId) {
		this.trfId = trfId;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getOldRegNo() {
		return oldRegNo;
	}
	public void setOldRegNo(String oldRegNo) {
		this.oldRegNo = oldRegNo;
	}
	public String getNewRegNo() {
		return newRegNo;
	}
	public void setNewRegNo(String newRegNo) {
		this.newRegNo = newRegNo;
	}
	public String getRegCost() {
		return regCost;
	}
	public void setRegCost(String regCost) {
		this.regCost = regCost;
	}
	public String getVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	public String getPaidFlag() {
		return paidFlag;
	}
	public void setPaidFlag(String paidFlag) {
		this.paidFlag = paidFlag;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
