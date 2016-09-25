package com.ldb.vocabulary.server.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.tomcat.util.net.AbstractEndpoint.Acceptor.AcceptorState;

import com.ldb.util.CommonValidation;
import com.ldb.util.DateUtils;
import com.ldb.vocabulary.server.dao.IAccountDao;
import com.ldb.vocabulary.server.dao.impl.AccountDao;
import com.ldb.vocabulary.server.domain.Account;
import com.ldb.vocabulary.server.domain.AccountState;
import com.ldb.vocabulary.server.exception.UserExistException;
import com.ldb.vocabulary.server.service.IAccountService;

public class AccountService implements IAccountService {

	private IAccountDao userDao = new AccountDao();
	
	@Override
	public void register(Account user) throws UserExistException{
//		// TODO Auto-generated method stub
//		if(userDao.find(user.getUserName()) != null){
//			throw new UserExistException("用户名已被注册");
//		}
//		userDao.add(user);
	}

	@Override
	public Account login(String userName, String userPwd) {
		// TODO Auto-generated method stub
		return null; //userDao.find(userName, userPwd);
	}

	@Override
	public boolean registerFromLogin(Account account) {
		// TODO 注册
		boolean isOk = false;
		String  phoneNumber = account.getPhoneNumber();
		if(phoneNumber != null && !phoneNumber.trim().isEmpty() 
				&& CommonValidation.validatePhoneNumber(phoneNumber)){
			account.setUsername(phoneNumber);
			account.setState(AccountState.NORMAL.getValue() + "");
			account.setRegisterTime(new Timestamp(DateUtils.getCurrentDate().getTime()));
			try {
				userDao.add(account);
				account = userDao.find(account.getUsername());
				isOk = true;
			} catch (SQLException e) {
				// TODO 是否要加点错误信息
				e.printStackTrace();
			}
		}
		
		return isOk;
	}

	
}
