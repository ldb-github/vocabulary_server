package com.ldb.vocabulary.server.domain;

import java.sql.Timestamp;

public class LoginBean {

	// 帐户信息
	private Account account;
	// 短信验证码
	private String checkCode;
	// 设备信息
	private DeviceInfo deviceInfo;
	
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	
	
}
