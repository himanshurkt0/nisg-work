package com.sidbi.bean;

public class IncidentalExpenseBean {
	
	String lastIncidentalFlag;
	String lastTransportFlag;
	String transferID;
	String transferType;
	String empCode;
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getLastIncidentalFlag() {
		return lastIncidentalFlag;
	}
	public void setLastIncidentalFlag(String lastIncidentalFlag) {
		this.lastIncidentalFlag = lastIncidentalFlag;
	}
	public String getLastTransportFlag() {
		return lastTransportFlag;
	}
	public void setLastTransportFlag(String lastTransportFlag) {
		this.lastTransportFlag = lastTransportFlag;
	}
	public String getTransferID() {
		return transferID;
	}
	public void setTransferID(String transferID) {
		this.transferID = transferID;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
	
}
