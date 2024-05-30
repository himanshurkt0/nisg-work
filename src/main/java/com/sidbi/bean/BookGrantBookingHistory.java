package com.sidbi.bean;

public class BookGrantBookingHistory {
	
	private int Count;
	private String fmCode;
	private String sessionStartDate;
	private String sessionEndDate;
	private String courseDesc;
	private String status;
	private String yearCount;
	private String duration;
	
	public BookGrantBookingHistory(){
		
	}
		
	public BookGrantBookingHistory(int count, String fmCode, String sessionStartDate, String sessionEndDate,
			String courseDesc, String yearCount, String duration, String status) {
		super();
		Count = count;
		this.fmCode = fmCode;
		this.sessionStartDate = sessionStartDate;
		this.sessionEndDate = sessionEndDate;
		this.courseDesc = courseDesc;
		this.yearCount = yearCount;
		this.duration = duration;
		this.status = status;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getYearCount() {
		return yearCount;
	}
	public void setYearCount(String yearCount) {
		this.yearCount = yearCount;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public String getFmCode() {
		return fmCode;
	}
	public void setFmCode(String fmCode) {
		this.fmCode = fmCode;
	}
	public String getSessionStartDate() {
		return sessionStartDate;
	}
	public void setSessionStartDate(String sessionStartDate) {
		this.sessionStartDate = sessionStartDate;
	}
	public String getSessionEndDate() {
		return sessionEndDate;
	}
	public void setSessionEndDate(String sessionEndDate) {
		this.sessionEndDate = sessionEndDate;
	}
	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
