package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;

import com.ldb.vocabulary.server.domain.CheckCode;

public interface ICheckCodeDao {

	/**
	 * 保存验证码信息
	 * @param checkCode
	 * @throws SQLException
	 */
	void saveCheckCode(CheckCode checkCode) throws SQLException;
	
	/**
	 * 根据手机号，验证码，类型查找验证码
	 * @param phoneNumber
	 * @param checkcode
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	CheckCode getCheckCode(String phoneNumber, String checkcode, String type) throws SQLException;
}
