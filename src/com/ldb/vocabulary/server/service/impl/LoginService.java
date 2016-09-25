package com.ldb.vocabulary.server.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import com.ldb.util.CommonUtils;
import com.ldb.util.DateUtils;
import com.ldb.vocabulary.server.dao.IAccountDao;
import com.ldb.vocabulary.server.dao.ICheckCodeDao;
import com.ldb.vocabulary.server.dao.impl.AccountDao;
import com.ldb.vocabulary.server.dao.impl.CheckCodeDao;
import com.ldb.vocabulary.server.domain.Account;
import com.ldb.vocabulary.server.domain.CheckCode;
import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.domain.DeviceInfo;
import com.ldb.vocabulary.server.domain.LoginBean;
import com.ldb.vocabulary.server.service.IAccountService;
import com.ldb.vocabulary.server.service.ILoginService;

public class LoginService implements ILoginService{

	// 令牌失效时间，单位 天
	private static final int TOKEN_EXPIRED_TIME = 30;
	private static final String CHECKCODE_TYPE = "lg";
	
	@Override
	public String login(LoginBean loginBean) {
		int code = CommunicationContract.VALUE_CODE_UNKNOWNERROR;
		String message = null;
		JSONObject resultJson = new JSONObject();
		
		Account account = loginBean.getAccount();
		DeviceInfo deviceInfo = loginBean.getDeviceInfo();
		
		// TODO 日志记录
		
		
		ICheckCodeDao checkCodeDao = new CheckCodeDao();
		IAccountDao accountDao = new AccountDao();
		String phoneNumber = account.getPhoneNumber();
		String checkCode = loginBean.getCheckCode();
		Account accountFound = null;
		boolean isOk = false;
		if(phoneNumber != null && !phoneNumber.trim().isEmpty()
				&& checkCode != null && !checkCode.trim().isEmpty()){
			// 如果手机号和验证码不为空，则验证是否匹配及验证码是否失效
			try {
				CheckCode checkCodeBean = checkCodeDao.getCheckCode(account.getPhoneNumber(), 
						loginBean.getCheckCode(), CHECKCODE_TYPE);
				if(checkCodeBean != null){
					if(checkCodeBean.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
						code = CommunicationContract.VALUE_CODE_ERROR;
						message = "验证码失效,请重新获取";
					}else{
						// 根据手机号查找用户
						Account accountPhone = accountDao.findByPhoneNumber(phoneNumber);
						if(accountPhone != null){
							isOk = true;
							accountFound = accountPhone;
						}else{
							// 用户不存在，进行注册
							// TODO 这里是通过IAccountService还是直接用IAccountDao好呢？？
							IAccountService accountService = new AccountService();
							if(accountService.registerFromLogin(account)){
								isOk = true;
								accountFound = account; 
							}
						}
					}
				}else{
					code = CommunicationContract.VALUE_CODE_ERROR;
					message = "手机号与验证码不匹配";
				}
			} catch (SQLException e) {
				code = CommunicationContract.VALUE_CODE_DBERROR;
				message = "登录失败: 手机号与验证码校验请求失败";
			}
		}else if(account.getUsername() != null && !account.getUsername().trim().isEmpty()
					&& account.getPassword() != null && !account.getPassword().trim().isEmpty()){
			// TODO 用户名 + 密码
			try {
				Account accountUP = accountDao.find(account.getUsername(), account.getPassword());
				if(accountUP != null){
					if(!accountUP.getUsername().equals(account.getUsername())){
						code = CommunicationContract.VALUE_CODE_ERROR;
						message = "登录失败: 用户信息错误";
					}else{
						isOk = true;
						accountFound = accountUP;
					}
				}else{
					code = CommunicationContract.VALUE_CODE_ERROR;
					message = "登录失败: 用户名或密码错误";
				}
					
			} catch (SQLException e) {
				code = CommunicationContract.VALUE_CODE_DBERROR;
				message = "登录失败: 用户校验请求失败";
			}
		}else if(account.getToken() != null && !account.getToken().trim().isEmpty()){
			// TODO token 验证方式
			try {
				Account accountToken = accountDao.findByToken(account.getToken());
				if(accountToken != null){
					if(accountToken.getUsername().equals(account.getUsername())){
						isOk = true;
						accountFound = accountToken;
					}
				}
			} catch (SQLException e) {
				// 校验失败，不做处理
			}
		}
			
			
		if(isOk){
			// TODO 生成Token
			String token = null;
			Account accountToken = null;
			while(true){
				token = CommonUtils.makeToken();
				try {
					accountToken = accountDao.findByToken(token);
					if(accountToken == null){
						break;
					}
				} catch (SQLException e) {
					token = null;
					break;
				}
			}
			
			// TODO 更新帐户表的token
			accountFound.setToken(token);
			Date startTime = DateUtils.getCurrentDate();
			accountFound.setStartTime(new Timestamp(startTime.getTime()));
			accountFound.setEndTime(new Timestamp(DateUtils.addDay(startTime, TOKEN_EXPIRED_TIME).getTime()));
			try {
				accountDao.update(accountFound);
			} catch (SQLException e) {
				// 更新token失败，不做处理
			}
			
			code = CommunicationContract.VALUE_CODE_OK;
			message = "登录成功";
			
			
			JSONObject accountJson = new JSONObject();
			accountJson.put(CommunicationContract.KEY_USERNAME, accountFound.getUsername());
			accountJson.put(CommunicationContract.KEY_EMAIL, accountFound.getEmail());
			accountJson.put(CommunicationContract.KEY_PHONENUMBER, accountFound.getPhoneNumber());
			accountJson.put(CommunicationContract.KEY_TOKEN, accountFound.getToken());
			accountJson.put(CommunicationContract.KEY_STATE, accountFound.getState());
			accountJson.put(CommunicationContract.KEY_REGISTER_TIME, 
					DateUtils.getDateTime(accountFound.getRegisterTime()));
			
			resultJson.put(CommunicationContract.KEY_ACCOUNT, accountJson);
		}
		
		if(code == CommunicationContract.VALUE_CODE_UNKNOWNERROR){
			message = "登录失败: 不支持的登录方式";
		}
		
		resultJson.put(CommunicationContract.KEY_CODE, code);
		resultJson.put(CommunicationContract.KEY_MESSAGE, message);
		
		return resultJson.toString();
	}
	
	

}
