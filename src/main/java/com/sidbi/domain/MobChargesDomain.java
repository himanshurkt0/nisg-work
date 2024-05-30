package com.sidbi.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.ADM_mobile_DET")
public class MobChargesDomain {
	
	@Column(name = "AMD_EMP_CD")
	private String empCode;
	
	@Id
	@Column(name = "AMD_SR_NO")
	private String srNo;
	
	@Column(name = "AMD_MOB_NO")
	private String mobNo;
	
	@Column(name = "AMD_SERVICE_PROV")
	private String serviceProvider;
	
	@Column(name = "AMD_INSTALL_DT")
	private Date installDate;
	
	@Column(name = "AMD_CREATED_DT")
	private Date createdDate;

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

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
