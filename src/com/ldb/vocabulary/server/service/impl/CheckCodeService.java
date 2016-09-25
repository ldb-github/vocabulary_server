package com.ldb.vocabulary.server.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.ldb.util.CommonUtils;
import com.ldb.util.DateUtils;
import com.ldb.vocabulary.server.dao.ICheckCodeDao;
import com.ldb.vocabulary.server.dao.impl.CheckCodeDao;
import com.ldb.vocabulary.server.domain.CheckCode;
import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.service.ICheckCodeService;

import jdk.nashorn.api.scripting.JSObject;

public class CheckCodeService implements ICheckCodeService{

	// 短信验证码失效时间，单位分钟
	private static final int CHECKCODE_EXPIRED_TIME = 10;
	// 短信验证码长度
	private static final int CHECKCODE_LEN = 6;
	
	private ICheckCodeDao checkCodeDao = new CheckCodeDao();
		
	// 手机号码
	private String phoneNumber;
	// 类型：r 注册; l 登录
	private String type;
	// 来源:br浏览器,an安卓客户端,ap苹果客户端
	private String source;
	private String ip;
	private String mac;
	// 手机imei
	private String imei;
	// 位置:经度,纬度
	private String location;
	
	@Override
	public String getCheckCode(){
		String result = null;
		int code;
		String message;
		JSONObject jsonObject = new JSONObject();
		
		// 注册
		// TODO 目前是通过RegisterService获取验证码，需要调整为else的模式。
		if(type.equals("re")){
			RegisterBean registerBean = new RegisterBean();
			registerBean.setPhoneNumber(getPhoneNumber());
			registerBean.setType("pc");
			registerBean.setSource(getSource());
			registerBean.setIp(getIp());
			registerBean.setMac(getMac());
			registerBean.setImei(getImei());
			registerBean.setLocation(getLocation());
			RegisterService registerService = new RegisterService();
			result = registerService.getCheckCode(registerBean);
		}else{
			// 登录
			if(type.equals("lg")){
				CheckCode checkCode = new CheckCode();
				checkCode.setType(type);
				checkCode.setPhoneNumber(getPhoneNumber());
				checkCode.setCheckCode(generateCheckCode(CHECKCODE_LEN));
				Date startTime = DateUtils.getCurrentDate();
				checkCode.setStartTime(new Timestamp(startTime.getTime()));
				checkCode.setEndTime(new Timestamp(
						DateUtils.addMinute(startTime, CHECKCODE_EXPIRED_TIME)
						.getTime()));
				if(checkCode.validate()){
					try {
						checkCodeDao.saveCheckCode(checkCode);
						// 发送短信
						String sms = "注册验证码:" + checkCode.getCheckCode() + ", " 
										+ CHECKCODE_EXPIRED_TIME + "分钟内有效";
						CommonUtils.sendSMS(checkCode.getPhoneNumber(), sms);
						code = CommunicationContract.VALUE_CODE_OK;
						message = "验证码已发送";
						jsonObject.put(CommunicationContract.KEY_CHECKCODE, checkCode.getCheckCode());
					} catch (SQLException e) {
						e.printStackTrace();
						code = CommunicationContract.VALUE_CODE_DBERROR;
						message = "获取验证码失败: 数据库请求错误";
					}
				}else{
					code = CommunicationContract.VALUE_CODE_PARAMSERROR;
					message = "获取验证码失败";
					JSONObject errors = new JSONObject();
					for(String key : checkCode.getErrors().keySet()){
						errors.put(key, checkCode.getErrors().get(key));
					}
					jsonObject.put(CommunicationContract.KEY_ERROR, errors);
				}
				
				
			}else{
				code = CommunicationContract.VALUE_CODE_ERROR;
				message = "请求类型错误";
			}
			
			jsonObject.put(CommunicationContract.KEY_CODE, code);
			jsonObject.put(CommunicationContract.KEY_MESSAGE, message);
			result = jsonObject.toString();
		}
		
		return result;
	}
	
	private String generateCheckCode(int len){
		return CommonUtils.makeNumber(len);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
}
