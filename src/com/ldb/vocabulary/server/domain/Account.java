package com.ldb.vocabulary.server.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Account implements Serializable{

	private static final long serialVersionUID = -4313782718477229465L;
	
	// 用户ID
	private String id;
	// 用户名
	private String username;
	// 用户密码
	private String password;
	// 用户邮箱
	private String email;
	// 用户手机 
	private String phoneNumber;
	// 状态
	private String state;
	// 注册时间
	private Timestamp registerTime;
	// TODO 令牌 暂时用UUID
	private String token; 
	// 令牌开始时间
	private Timestamp startTime;
	// 令牌失效时间
	private Timestamp endTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setPassword(String userPwd) {
		this.password = userPwd;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
