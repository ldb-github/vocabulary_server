package com.ldb.vocabulary.server.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import com.ldb.util.CommonUtils;
import com.ldb.vocabulary.server.dao.IAccountDao;
import com.ldb.vocabulary.server.dao.impl.RegisterDao;
import com.ldb.vocabulary.server.dao.impl.AccountDao;
import com.ldb.vocabulary.server.domain.AccountState;
import com.ldb.vocabulary.server.domain.AccountOpSource;
import com.ldb.vocabulary.server.domain.AccountOpType;
import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.service.IRegisterService;
import com.ldb.vocabulary.server.domain.Account;

public class RegisterService implements IRegisterService{
	// 一个终端最多注册次数
	private static final int MAX_REGISTER_COUNT = 3;
	// 短信验证码失效时间，单位分钟
	private static final int CHECKCODE_EXPIRED_TIME = 10;
	// 短信验证码长度
	private static final int CHECKCODE_LEN = 6;

	private IAccountDao userDao = new AccountDao();
	private RegisterDao registerDao = new RegisterDao();
	
	@Override
	public String getCheckCode(RegisterBean registerBean){
		String result = null;
		int code;
		String message;
		JSONObject jsonObject = new JSONObject();
		
		if(registerBean.getPhoneNumber() == null || registerBean.getPhoneNumber().trim().isEmpty() 
				|| !registerBean.validatePhoneNumber() ){ 
			code = CommunicationContract.VALUE_CODE_PARAMSERROR;
			message = "获取验证码失败：手机号有误";
		}else if(isRegistered(registerBean.getPhoneNumber())){
			code = CommunicationContract.VALUE_CODE_ERROR;
			message = "手机号已被注册，请核查。如果忘记密码，请使用密码找回功能。";
		}else{
			String checkCode = CommonUtils.makeNumber(CHECKCODE_LEN);
			registerBean.setCheckCode(checkCode);
			
			Calendar calendar = Calendar.getInstance();
			Date startTime = calendar.getTime();
			calendar.add(Calendar.MINUTE, CHECKCODE_EXPIRED_TIME);
			Date endTime = calendar.getTime();
			registerBean.setStartTime(new Timestamp(startTime.getTime()));
			registerBean.setEndTime(new Timestamp(endTime.getTime()));
			
			try {
					registerDao.saveRegisterInfoForCheckCode(registerBean);
					code = CommunicationContract.VALUE_CODE_OK;
					message = "获取验证码成功";
					jsonObject.put(CommunicationContract.KEY_CHECKCODE, checkCode);
			} catch (SQLException e) {
				e.printStackTrace();
				code = CommunicationContract.VALUE_CODE_DBERROR;
				message = "获取验证码失败";
			}
		}
		jsonObject.put(CommunicationContract.KEY_CODE, code);
		jsonObject.put(CommunicationContract.KEY_MESSAGE, message);
		
		result = jsonObject.toString();
		
		if(code == CommunicationContract.VALUE_CODE_OK){
			String sms = "注册验证码:" + registerBean.getCheckCode() 
			+ ", " + CHECKCODE_EXPIRED_TIME + "分钟内有效";
			CommonUtils.sendSMS(registerBean.getPhoneNumber(), sms);
		}
		
		return result;
	}
	
	/**
	 * @return
	 * code,   message                          error
	 *   0,    注册成功                                                                
	 * 100,    （注册失败：注册信息不符合业务规范）
	 * 400，       注册信息格式不正确                                          username(用户名不正确),password(密码不正确),
	 *                                          confirmpwd(确认密码不正确),email(邮箱不正确)
	 */
	@Override
	public String register(RegisterBean registerBean) {
		// 处理注册类型
		AccountOpType type = AccountOpType.USER_PASSWORD;
		String username = registerBean.getUsername();
		String password = registerBean.getPassword();
		String phoneNumber = registerBean.getPhoneNumber();
		String email = registerBean.getEmail();
		if (password != null && !password.trim().equals("")) {
			if (username != null && !username.trim().equals("")) {
				type = AccountOpType.USER_PASSWORD; 
			} else if (email != null && !email.trim().equals("")) {
				type = AccountOpType.EMAIL_PASSWORD;
				registerBean.setUsername(email);
			}
		}else if(phoneNumber != null && !phoneNumber.trim().equals("")){
			type = AccountOpType.PHONE_CHECKCODE;
			registerBean.setUsername(phoneNumber);
		}
		registerBean.setType(type.toString());
		
		JSONObject result = new JSONObject();
		if(registerBean.validate()){
			String validateResult = validate(registerBean);
			if(validateResult == null){
				// 注册
				Account user = new Account();
				user.setUsername(registerBean.getUsername());
				user.setPassword(registerBean.getPassword());
				user.setPhoneNumber(registerBean.getPhoneNumber());
				user.setEmail(registerBean.getEmail());
				if(type == AccountOpType.EMAIL_PASSWORD){
					user.setState(AccountState.UNACTIVATED.getValue() + "");
				}else{
					user.setState(AccountState.NORMAL.getValue() + "");
				}
				try {
					userDao.add(user);
					// TODO 回写用户id到op_register
					
					result.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_OK);
					result.put(CommunicationContract.KEY_MESSAGE, "注册成功");
				} catch (SQLException e) {
					result.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_DBERROR);
					result.put(CommunicationContract.KEY_MESSAGE, "注册失败: 用户信息保存失败");
				}
				
			}else{
				result.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_ERROR);
				result.put(CommunicationContract.KEY_MESSAGE, validateResult);
			}
		}else{
			result.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_PARAMSERROR);
			result.put(CommunicationContract.KEY_MESSAGE, "注册失败: 信息格式不正确");
			JSONObject errors = new JSONObject();
			for(String key : registerBean.getErrors().keySet()){
				errors.put(key, registerBean.getErrors().get(key));
			}
			result.put(CommunicationContract.KEY_ERROR, errors);
		}
		
		return result.toString();
	}
	
	/**
	 * 验证注册信息是否符合业务规范
	 * @param registerBean
	 * @return
	 */
	public String validate(RegisterBean registerBean) {
		String result = null;
		
		if((result = validateSource(registerBean)) != null){
			return result;
		}
		
		if((result = validateType(registerBean)) != null){
			return result;
		}
		
		return result;
	}
	
	public String validateSource(RegisterBean registerBean) {
		String result = null;
		
		AccountOpSource source = AccountOpSource.geSource(registerBean.getSource());

		switch (source) {
		case ANDROID: 
		case APPLE: 
		case ANDROID_PAD: 
		case APPLE_PAD: 
			int count = 0;
			int count2 = 0;
			if(registerBean.getImei() != null && !registerBean.getImei().trim().equals("")){
				try {
					count = registerDao.getRegisteredCountOfImei(registerBean.getImei());
				} catch (SQLException e) {
					e.printStackTrace();
					return "数据验证错误";
				}
			}
			if(registerBean.getMac() != null && !registerBean.getMac().trim().equals("")){
				try {
					count2 = registerDao.getRegisteredCountOfMac(registerBean.getImei());
					count = count2 > count ? count2 : count;
				} catch (SQLException e) {
					e.printStackTrace();
					return "数据验证错误";
				}
				
			}
			if(count > MAX_REGISTER_COUNT){
				result = "终端注册次数已经超过了" + MAX_REGISTER_COUNT + "次,不能再使用此终端进行注册";
			}
			break;

		default:
			break;
		}
		
		return result;
	}
	
	public String validateType(RegisterBean registerBean) {
		String result = null;
		
		AccountOpType type = AccountOpType.geType(registerBean.getType());
		
		switch (type) {
		case USER_PASSWORD: // 用户名 + 密码
			// 验证用户名是否已经被注册
			try {
				if(userDao.find(registerBean.getUsername()) != null){
					result = "用户名已被注册,请使用其他用户名";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = "用户名 ,密码验证失败: 数据库请求错误";
			}
			break;
		case PHONE_CHECKCODE: // 手机号 + 短信验证码
			// 验证手机号是否已经被注册
			if(isRegistered(registerBean.getPhoneNumber())){
				result = "手机号已被注册,请核查!如果忘记密码,请使用密码找回功能";
			}
			// 验证验证码是否有效
			RegisterBean registerBean2 = null;
			try {
				registerBean2 = 
						registerDao.getRegisterBean(registerBean.getPhoneNumber(),registerBean.getCheckCode());
				if(registerBean2 != null){
					if(!registerBean2.getPhoneNumber().equals(registerBean.getPhoneNumber())){
						result = "手机号错误,请核查";
					}else if(!registerBean2.getCheckCode().equals(registerBean.getCheckCode())){
						result = "验证码错误,请核查";
					}else if(registerBean2.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
						result = "验证码失效,请重新获取";
					}
				}else{
					result = "手机与验证码不匹配,请核查";
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				result = "手机号验证失败: 数据库请求错误";
			}
			
			break;
		case EMAIL_PASSWORD: // 邮箱 + 密码
			// 验证邮箱是否已经被注册
			try {
				if(userDao.findByEmail(registerBean.getEmail()) != null){
					result = "邮箱已被注册,请使用其他邮箱";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "邮箱验证失败: 数据库请求错误";
			}
			
			break;
		default:
			break;
		}
		
		return result;
	}
	
	private boolean isRegistered(String phoneNumber){
		try {
			return userDao.findByPhoneNumber(phoneNumber) != null;
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}
	
}
