package com.sidbi.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_EMP_REIMB_CONSENT")
public class MobileClaimReimDomain {
	
	@Column(name = "aerc_emp_cd")
	private String empCode;
	
	@Column(name = "aerc_afm_cd")
	private String afmCode; 
	
	@Id
	@Column(name = "aerc_sr_no")
	private String srNo;	 
		
	@Column(name = "aerc_avl_option")
	private String availOption; 
		
	@Column(name = "aerc_CREATEd_dt")
	private Date createdDate; 
		
	@Column(name = "aerc_agreement")
	private String agreement;
		
	@Column(name = "aerc_emp_grade")
  	private String empGrade;
  	
  	@Column(name = "aerc_emp_br_cd")
  	private String branchCode;
  	
  	@Column(name = "aerc_applicable_from")
  	private Date fromDate;
  	
  	@Column(name = "aerc_applicable_to")
  	private Date toDate;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
