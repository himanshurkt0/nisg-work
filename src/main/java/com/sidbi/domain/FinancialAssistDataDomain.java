package com.sidbi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@IdClass(FinancialAssistCompositeKey.class)
@Table(name = "cadmin.ADM_FNCL_ASSIST")
public class FinancialAssistDataDomain {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "AFA_EMP_CD")
	private String empCode;
	
	@Column(name = "AFA_FILE_DATA")
	private byte[] billUpload;
	
	@Column(name = "AFA_VERIFY_REMARKS")
	private String remark;
	
	@Column(name = "AFA_DEPEND_SRNO")
	private String dependentCode;
	
	@Column(name = "AFA_SR_NO")
	private String srNo;
	
	@Id
	@Column(name = "AFA_AFM_CD")
	private String fmCode;
	
	@Column(name = "AFA_DEPEND_RELATION")
	private String dependentRelation;
	
	@Column(name = "AFA_CHILD_NAME")
	private String dependentName;
	
	@Column(name = "AFA_COURSE_PASSED_CD")
	private String coursePassedCode;
	
	@Column(name = "AFA_COURSE_PURSUING_CD")
	private String coursePersueCode;
	
	@Column(name = "AFA_PERCENTAGE_OBTAINED")
	private String perecentage;
	
	
	@Column(name = "AFA_SESSION_START_YR")
	private String startYear;
	
	@Column(name = "AFA_SESSION_END_YR")
	private String endYear;
	
	@Column(name = "AFA_CREATED_DT")
	private String createdDate;
	
	@Column(name = "AFA_VERIFY_DT")
	private String verifiedDate;
	
	@Column(name = "AFA_VERIFY_STATUS")
	private String verifyStatus;
	
	@Column(name = "AFA_YEAR")
	private String courseYear;
	
	@Column(name = "AFA_AMT")
	private String amount;
	
	
	@Column(name = "AFA_DSPLN")
	private String courseStream;
	
	@Column(name = "AFA_DAY_BOARDER_FLAG")
	private String boarder;
	
	@Column(name = "AFA_VERIFY_BY")
	private String verifyBy;
	
	@Column(name = "AFA_PAID_STATUS")
	private String paidStatus;
	

	public String getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	public String getDependentCode() {
		return dependentCode;
	}

	public void setDependentCode(String dependentCode) {
		this.dependentCode = dependentCode;
	}

	public String getCourseStream() {
		return courseStream;
	}

	public void setCourseStream(String courseStream) {
		this.courseStream = courseStream;
	}

	public String getBoarder() {
		return boarder;
	}

	public void setBoarder(String boarder) {
		this.boarder = boarder;
	}

	public String getDependentRelation() {
		return dependentRelation;
	}

	public void setDependentRelation(String dependentRelation) {
		this.dependentRelation = dependentRelation;
	}

	public String getDependentName() {
		return dependentName;
	}

	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
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

	

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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
	

}
