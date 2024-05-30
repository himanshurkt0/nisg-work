package com.sidbi.domain;

import java.io.Serializable;

public class FinancialAssistCompositeKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empCode;
	private String fmCode;
	private String srNo;
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getFmCode() {
		return fmCode;
	}
	public void setFmCode(String fmCode) {
		this.fmCode = fmCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empCode == null) ? 0 : empCode.hashCode());
		result = prime * result + ((fmCode == null) ? 0 : fmCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinancialAssistCompositeKey other = (FinancialAssistCompositeKey) obj;
		if (empCode == null) {
			if (other.empCode != null)
				return false;
		} else if (!empCode.equals(other.empCode))
			return false;
		if (fmCode == null) {
			if (other.fmCode != null)
				return false;
		} else if (!fmCode.equals(other.fmCode))
			return false;
		return true;
	}
	
	

}
