package com.sidbi.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class PersonalAdvanceBean {
	
	private String empCode;
	private String empName;
	private String empGrade;
	private String category;
	private int fmCode;
	private String courseYear;
	private String dependentGrade;
	private String startDate;
	private String endDate;
	private String coursePersued;
	private String coursePassed;
	private String percentage;
	private String duration;
	private String payment;
	private String remark;
	private String status;
	private String dependents;
	private String lv_sr_no;
	private MultipartFile billUpload;
	
	private List<PreviousApplication> perviousApplication;
	
	public String getLv_sr_no() {
		return lv_sr_no;
	}
	public void setLv_sr_no(String lv_sr_no) {
		this.lv_sr_no = lv_sr_no;
	}
	public List<PreviousApplication> getPerviousApplication() {
		return perviousApplication;
	}
	public void setPerviousApplication(List<PreviousApplication> perviousApplication) {
		this.perviousApplication = perviousApplication;
	}
	public String getCourseYear() {
		return courseYear;
	}
	public void setCourseYear(String courseYear) {
		this.courseYear = courseYear;
	}
	public String getDependentGrade() {
		return dependentGrade;
	}
	public void setDependentGrade(String dependentGrade) {
		this.dependentGrade = dependentGrade;
	}
	public MultipartFile getBillUpload() {
		return billUpload;
	}
	public void setBillUpload(MultipartFile billUpload) {
		this.billUpload = billUpload;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpGrade() {
		return empGrade;
	}
	public void setEmpGrade(String empGrade) {
		this.empGrade = empGrade;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDependents() {
		return dependents;
	}
	public void setDependents(String dependents) {
		this.dependents = dependents;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public int getFmCode() {
		return fmCode;
	}
	public void setFmCode(int fmCode) {
		this.fmCode = fmCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCoursePersued() {
		return coursePersued;
	}
	public void setCoursePersued(String coursePersued) {
		this.coursePersued = coursePersued;
	}
	public String getCoursePassed() {
		return coursePassed;
	}
	public void setCoursePassed(String coursePassed) {
		this.coursePassed = coursePassed;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
