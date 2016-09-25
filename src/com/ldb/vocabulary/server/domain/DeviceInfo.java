package com.ldb.vocabulary.server.domain;

public class DeviceInfo {

	// 来源:br浏览器,an安卓客户端,ap苹果客户端
	private String source;
	private String ip;
	private String mac;
	// 手机imei
	private String imei;
	// 位置:经度,纬度
	private String location;
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
	
	
}
