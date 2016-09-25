	package com.ldb.vocabulary.server.domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.ldb.util.CommonValidation;

public class RegisterBean {

	// 操作流水号
	private String id;
	// 用户id
	private String userId;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 确认密码
	private String confirmPwd;
	// 邮箱
	private String email;
	// 手机号码
	private String phoneNumber;
	// 短信验证码
	private String checkCode;
	// 登录类型:pc手机+短信验证码,up用户名+密码,ep邮箱+密码
	private String type;
	// 来源:br浏览器,an安卓客户端,ap苹果客户端
	private String source;
	private String ip;
	private String mac;
	// 手机imei
	private String imei;
	// 位置:经度,纬度
	private String location;
	// 注册开始时间
	private Timestamp startTime;
	// 注册实效时间(验证码实效)
	private Timestamp endTime;

	/**
	 * 存储校验不通过时给用户的错误提示信息
	 */
	private Map<String, String> errors = new HashMap<String, String>();

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/*
	 * validate方法负责校验表单输入项 表单输入项校验规则： private String userName;
	 * 用户名不能为空，并且要是3-8的字母 abcdABcd private String userPwd; 密码不能为空，并且要是3-8的数字
	 * private String confirmPwd; 两次密码要一致 private String email;
	 * 可以为空，不为空要是一个合法的邮箱 private String birthday; 可以为空，不为空时，要是一个合法的日期
	 */
	public boolean validate() {

		// 手机号 + 验证码 不必校验秘密
		boolean isPC = AccountOpType.geType(type).equals(AccountOpType.PHONE_CHECKCODE);
		
		if (!validateUsername()) {
			return false;
		}

		if (!isPC && !validatePassword()) {
			return false;
		}

		if (!isPC && !validateConfirmPwd()) {
			return false;
		}

		if (!validateEmail()) {
			return false;
		}

		return true;
	}

	public boolean validateUsername() {

		boolean isOk = true;
		if (this.username == null || this.username.trim().equals("")) {
			isOk = false;
			errors.put(CommunicationContract.KEY_USERNAME, "用户名不能为空！！");
		} else {
			if (!this.username.matches("[\\da-zA-Z]{3,40}")) {
				isOk = false;
				errors.put(CommunicationContract.KEY_USERNAME, "用户名必须是3-8位的字母！！");
			}
		}
		return isOk;

	}

	public boolean validatePassword() {

		boolean isOk = true;
		if (this.password == null || this.password.trim().equals("")) {
			isOk = false;
			errors.put("password", "密码不能为空！！");
		} else {
			if (!this.password.matches("\\d{3,8}")) {
				isOk = false;
				errors.put(CommunicationContract.KEY_PASSWORD, "密码必须是3-8位的数字！！");
			}
		}
		return isOk;

	}

	public boolean validateConfirmPwd() {

		boolean isOk = true;
		// private String password2; 两次密码要一致
		if (this.confirmPwd != null) {
			if (!this.confirmPwd.equals(this.password)) {
				isOk = false;
				errors.put(CommunicationContract.KEY_CONFIRMPWD, "两次密码不一致！！");
			}
		}
		return isOk;

	}

	public boolean validateEmail() {

		boolean isOk = true;
		// private String email; 可以为空，不为空要是一个合法的邮箱
		if (this.email != null && !this.email.trim().equals("")) {
			if (!CommonValidation.validateEmail(this.email)) {
				isOk = false;
				errors.put(CommunicationContract.KEY_EMAIL, "邮箱格式不对");
			}
		}
		return isOk;

	}
	
	public boolean validatePhoneNumber() {

		boolean isOk = true;
		// 可以为空，不为空要是一个合法的手机号
		if (this.phoneNumber != null && !this.phoneNumber.trim().equals("")) {
			if (!CommonValidation.validatePhoneNumber(this.phoneNumber)) {
				isOk = false;
				errors.put(CommunicationContract.KEY_PHONENUMBER, "手机号格式不对");
			}
		}
		return isOk;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

}
