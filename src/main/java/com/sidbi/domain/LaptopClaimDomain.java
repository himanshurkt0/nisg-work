package com.sidbi.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_LAPTOP_CLAIM")
public class LaptopClaimDomain {
	
	@Column(name = "ATL_EMP_CD")
	private String empCode; 
	
	@Id
	@Column(name = "ATL_SR_NO")
	private String srNo; 
	
	@Column(name = "ATL_CLAIM_DATE")
	private Date claimDate; 
	
	@Column(name = "ATL_CLAIM_AMT")
	private String claimAmount; 
	
	@Column(name = "ATL_CLAIM_DET")
	private String claimDetail;
	
	@Column(name = "ATL_BILL_DATE")
	private Date billDate; 
	
	@Column(name = "ATL_VERIFY")
	private String verifyStatus;
	
	@Column(name = "ATL_VERIFY_BY")
	private String verifiedBy;
	
	@Column(name = "ATL_VERIFY_DATE")
	private Date verifyDate ; 
	
	@Column(name = "ATL_APPROVED")
	private String approved; 
	
	@Column(name = "ATL_PAID")
	private String paid; 
	
	@Column(name = "ATL_PAID_DATE")
	private String paidDate; 
	
	@Column(name = "ATL_PAID_AMT")
	private String paidAmount; 
	
	@Column(name = "ATL_REQ_ID")
	private String reqId;
	
	@Column(name = "ATL_REFUND_FLG")
	private String refundFlag;
	
	@Column(name = "ATL_FILE_CONTENT")
	private byte[] billUpload;

	@Column(name = "ATL_FILE_NAME")
	private String fileName;
	
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

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
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

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

}
