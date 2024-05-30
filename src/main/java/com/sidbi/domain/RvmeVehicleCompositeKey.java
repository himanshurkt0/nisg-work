package com.sidbi.domain;

import java.io.Serializable;

public class RvmeVehicleCompositeKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empCode;
	private String srNumber;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getSrNumber() {
		return srNumber;
	}
	public void setSrNumber(String srNumber) {
		this.srNumber = srNumber;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empCode == null) ? 0 : empCode.hashCode());
		result = prime * result + ((srNumber == null) ? 0 : srNumber.hashCode());
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
		RvmeVehicleCompositeKey other = (RvmeVehicleCompositeKey) obj;
		if (empCode == null) {
			if (other.empCode != null)
				return false;
		} else if (!empCode.equals(other.empCode))
			return false;
		if (srNumber == null) {
			if (other.srNumber != null)
				return false;
		} else if (!srNumber.equals(other.srNumber))
			return false;
		return true;
	}
	
	
	
	

}
