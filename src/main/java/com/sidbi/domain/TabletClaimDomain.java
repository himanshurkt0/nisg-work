package com.sidbi.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_TAB_CLAIM")
public class TabletClaimDomain {
	
	@Column(name = "ATC_EMP_CD")
	private String empCode; 
	
	@Id
	@Column(name = "ATC_SR_NO")
	private String srNo;
	
	@Column(name = "ATC_CLAIM_DATE")
	private Date claimDate ;
	
	@Column(name = "ATC_CLAIM_AMT")
	private String claimAmount;
	
	@Column(name = "ATC_BILL_DATE")
	private Date billDate;
	
	@Column(name = "ATC_CLAIM_DET")
	private String claimDetail;
	
	@Column(name = "ATC_VERIFY")
	private String verifyStatus; 
	
	@Column(name = "ATC_VERIFY_BY")
	private String verifiedBy; 
	
	@Column(name = "ATC_VERIFY_DATE")
	private Date verifyDate ; 
	
	@Column(name = "ATC_APPROVED")
	private String approved; 
	
	@Column(name = "ATC_PAID")
	private String paidStatus; 
	
	@Column(name = "ATC_PAID_DATE")
	private Date paidDate; 
	
	@Column(name = "ATC_PAID_AMT")
	private String paidAmount; 
	
	@Column(name = "ATC_REQ_ID")
	private String reqID;
	
	@Column(name = "ATC_FILE_CONTENT")
	private byte[] bill;
	
	@Column(name = "ATC_FILE_NAME")
	private String fileName;
	
	@Column(name = "ATC_REMARKS")
	private String remarks;

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public byte[] getBill() {
		return bill;
	}

	public void setBill(byte[] bill) {
		this.bill = bill;
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

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
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

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

}
