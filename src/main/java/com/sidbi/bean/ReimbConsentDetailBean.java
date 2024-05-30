package com.sidbi.bean;

import java.io.Serializable;

public class ReimbConsentDetailBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String empCode;
	private String afmCode;
	private String srNo;
	private String availOption;
	private String createdDate;
	private String rvmeBrCode;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getAfmCode() {
		return afmCode;
	}
	public void setAfmCode(String afmCode) {
		this.afmCode = afmCode;
	}
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getAvailOption() {
		return availOption;
	}
	public void setAvailOption(String availOption) {
		this.availOption = availOption;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getRvmeBrCode() {
		return rvmeBrCode;
	}
	public void setRvmeBrCode(String rvmeBrCode) {
		this.rvmeBrCode = rvmeBrCode;
	}
	
	

}
