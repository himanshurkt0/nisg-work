package com.sidbi.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cadmin.ADM_MOBILE_CLAIM")
public class MobileClaimDomain {
	
	public byte[] getBillUpload() {
		return billUpload;
	}

	public void setBillUpload(byte[] billUpload) {
		this.billUpload = billUpload;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "AMC_EMP_CD")
	private String empCode;
	
	@Id
	@Column(name = "AMC_SR_NO")
	private String srNo;
	
	@Column(name = "AMC_CLAIM_DATE")
	private Date claimDate;
	
	@Column(name = "AMC_CLAIM_AMT")
	private String claimAmount;
	
	@Column(name = "AMC_CLAIM_DET")
	private String claimDetail;
	
	@Column(name = "AMC_VERIFY")
	private String verifyStatus;
	
	@Column(name = "AMC_VERIFY_BY")
	private String verifiedBy;
	
	@Column(name = "AMC_BILL_DATE")
	private Date billDate;
	
	@Column(name = "AMC_VERIFY_DATE")
	private String verifyDate;
	
	@Column(name = "AMC_APPROVED")
	private String approved;
	
	@Column(name = "AMC_PAID")
	private String paidStatus;
	
	@Column(name = "AMC_PAID_DATE")
	private String paidDate;
	
	@Column(name = "AMC_PAID_AMT")
	private String paidAmount;
	
	@Column(name = "AMC_REQ_ID")
	private String requestId;
	
	@Column(name = "AMC_FILE_CONTENT")
	private byte[] billUpload;

	@Column(name = "AMC_FILE_NAME")
	private String fileName;

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

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimDetail() {
		return claimDetail;
	}

	public void setClaimDetail(String claimDetail) {
		this.claimDetail = claimDetail;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
