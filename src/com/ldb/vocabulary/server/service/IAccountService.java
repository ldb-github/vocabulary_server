package com.ldb.vocabulary.server.service;

import com.ldb.vocabulary.server.domain.Account;
import com.ldb.vocabulary.server.exception.UserExistException;

public interface IAccountService {

	/**
	 * 用户注册
	 * @param user
	 */
	void register(Account user) throws UserExistException;
	/**
	 * 用户登录
	 * @param userName
	 * @param userPwd
	 * @return 根据用户名和密码查找到的用户
	 */
	Account login(String userName, String userPwd);
	/**
	 * 使用手机号 + 验证码模式登录时，如果用户不存在，则进行注册。
	 * @param user
	 */
	boolean registerFromLogin(Account account);
}
