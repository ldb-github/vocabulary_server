package com.ldb.vocabulary.server.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.ldb.util.CommonValidation;

public class CheckCode {

	private String phoneNumber;
	private String checkCode;
	private Timestamp startTime;
	private Timestamp endTime;
	// 类型：re注册; lg登录
	private String type;
	private Map<String, String> errors = new HashMap<String, String>();
	
	public boolean validate(){
		boolean result = validatePhoneNumber();
		return result;
	}
	
	public boolean validatePhoneNumber(){
		boolean isOk = true;
		if (phoneNumber == null || phoneNumber.trim().equals("")) {
			isOk = false;
			errors.put(CommunicationContract.KEY_PHONENUMBER, "手机号不能空");
		}else{
			if (!CommonValidation.validatePhoneNumber(phoneNumber)) {
				isOk = false;
				errors.put(CommunicationContract.KEY_PHONENUMBER, "手机号格式不对");
			}
		}
		return isOk;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	
}
