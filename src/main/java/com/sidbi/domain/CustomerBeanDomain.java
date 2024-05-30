package com.sidbi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.v_hr_emp")
public class CustomerBeanDomain {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VHE_EMP_CD")
	private String empCode;

	@Column(name = "VHE_EMP_BR_CD")
	private String branchCode;

	@Column(name = "VHE_EMP_FULL_NAME")
	private String empName;

	@Column(name = "VHE_GENDER")
	private String gender;

	@Column(name = "VHE_EMP_DOB")
	private Date dob;

	@Column(name = "VHE_EMP_GRADE")
	private String empGrade;
	
	@Column(name = "VHE_EMP_DESGN")
	private String empDesg;

	@Column(name = "VHE_EMP_STATUS")
	private String empStatus;

	@Column(name = "VHE_EMP_EMAIL_ID")
	private String email;

	@Column(name = "VHE_EMP_RESIDENTIAL_ADDRESS")
	private String address;

	public String getEmpDesg() {
		return empDesg;
	}

	public void setEmpDesg(String empDesg) {
		this.empDesg = empDesg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmpGrade() {
		return empGrade;
	}

	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
