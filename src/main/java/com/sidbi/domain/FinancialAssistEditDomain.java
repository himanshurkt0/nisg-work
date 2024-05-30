package com.sidbi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_FNCL_ASSIST")
public class FinancialAssistEditDomain {
	@Id
	@Column(name = "AFA_EMP_CD")
	private String empCode;
	
	@Column(name = "AFA_FILE_DATA")
	private byte[] billUpload;
	
	@Column(name = "AFA_VERIFY_REMARKS")
	private String remark;
	
	@Column(name = "AFA_SR_NO")
	private String srNo; 
	
	@Column(name = "AFA_AFM_CD")
	private String fmCode;
	
	@Column(name = "AFA_COURSE_PASSED_CD")
	private String coursePassedCode;
	
	@Column(name = "AFA_COURSE_PURSUING_CD")
	private String coursePersueCode;
	
	@Column(name = "AFA_PERCENTAGE_OBTAINED")
	private String perecentage;
	
	@Column(name = "AFA_DEPEND_SRNO")
	private String dependentSrNo;
	
	@Column(name = "AFA_SESSION_START_YR")
	private String startYear;
	
	@Column(name = "AFA_SESSION_END_YR")
	private String endYear;
	
	@Column(name = "AFA_YEAR")
	private String courseYear;
	
	@Column(name = "AFA_AMT")
	private String amount;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public byte[] getBillUpload() {
		return billUpload;
	}

	public void setBillUpload(byte[] billUpload) {
		this.billUpload = billUpload;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getFmCode() {
		return fmCode;
	}

	public void setFmCode(String fmCode) {
		this.fmCode = fmCode;
	}

	public String getCoursePassedCode() {
		return coursePassedCode;
	}

	public void setCoursePassedCode(String coursePassedCode) {
		this.coursePassedCode = coursePassedCode;
	}

	public String getCoursePersueCode() {
		return coursePersueCode;
	}

	public void setCoursePersueCode(String coursePersueCode) {
		this.coursePersueCode = coursePersueCode;
	}

	public String getPerecentage() {
		return perecentage;
	}

	public void setPerecentage(String perecentage) {
		this.perecentage = perecentage;
	}

	public String getDependentSrNo() {
		return dependentSrNo;
	}

	public void setDependentSrNo(String dependentSrNo) {
		this.dependentSrNo = dependentSrNo;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getCourseYear() {
		return courseYear;
	}

	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	

}
