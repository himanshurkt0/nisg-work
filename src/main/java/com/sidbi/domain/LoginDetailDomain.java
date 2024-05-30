package com.sidbi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadmin.login_detail")
public class LoginDetailDomain {
	
	public LoginDetailDomain() {
		super();
	}

	public LoginDetailDomain(String userId, String emailId, String attempts, String accountStatus) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.attempts = attempts;
		this.accountStatus = accountStatus;
	}

	

	@Id
	@Column(name = "LD_USER_ID")
	private String userId;

	@Column(name = "LD_EMAIL_ID")
	private String emailId;

	@Column(name = "LD_INVALID_ATTEMPTS")
	private String attempts;

	@Column(name = "LD_ACCOUNT_STATUS")
	private String accountStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAttempts() {
		return attempts;
	}

	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

}
