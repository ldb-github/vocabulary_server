package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;
import java.util.List;

import com.ldb.vocabulary.server.domain.Account;

public interface IAccountDao {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return 查找到的用户
	 * @throws SQLException 
	 */
	Account find(String username) throws SQLException;
	/**
	 * 根据用户名和密码查找用户
	 * @param userName
	 * @param userPwd
	 * @return 查找到的用户
	 * @throws SQLException 
	 */
	Account find(String userName, String userPwd) throws SQLException;
	/**
	 * 根据手机号码查找用户
	 * @param username
	 * @return 查找到的用户
	 * @throws SQLException 
	 */
	Account findByPhoneNumber(String phoneNumber) throws SQLException;
	/**
	 * 根据邮箱查找用户
	 * @param username
	 * @return 查找到的用户
	 * @throws SQLException 
	 */
	Account findByEmail(String email) throws SQLException;
	/**
	 * 根据令牌查找用户
	 * @param username
	 * @return 查找到的用户
	 * @throws SQLException 
	 */
	Account findByToken(String token) throws SQLException;
	/**
	 * 根据手机IMIE查找用户
	 * @param username
	 * @return 查找到的用户
	 */
	List<Account> findByPhoneIMIE(String phoneIMIE);
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException 
	 */
	void add(Account account) throws SQLException;
	/**
	 * 更新用户
	 * @param user
	 * @throws SQLException 
	 */
	void update(Account account) throws SQLException;
	
}
