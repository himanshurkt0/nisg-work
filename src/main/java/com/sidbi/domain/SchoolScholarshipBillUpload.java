package com.sidbi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_FNCL_ASSIST")
public class SchoolScholarshipBillUpload {
	
	
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
