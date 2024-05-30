package com.sidbi.bean;

import java.io.Serializable;

public class BcmModuleBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String moduleId;
	private String moduleDesc;
	private String parentId;
	private String moduleType;
	private String moduleLevel;
	private String moduleRole;
	private String moduleAccessBy;
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getModuleLevel() {
		return moduleLevel;
	}
	public void setModuleLevel(String moduleLevel) {
		this.moduleLevel = moduleLevel;
	}
	public String getModuleRole() {
		return moduleRole;
	}
	public void setModuleRole(String moduleRole) {
		this.moduleRole = moduleRole;
	}
	public String getModuleAccessBy() {
		return moduleAccessBy;
	}
	public void setModuleAccessBy(String moduleAccessBy) {
		this.moduleAccessBy = moduleAccessBy;
	}
	
	

}
