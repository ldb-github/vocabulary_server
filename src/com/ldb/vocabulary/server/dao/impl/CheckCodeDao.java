package com.ldb.vocabulary.server.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.ldb.util.jdbc.JdbcUtil_DBUtils;
import com.ldb.vocabulary.server.dao.ICheckCodeDao;
import com.ldb.vocabulary.server.domain.CheckCode;

public class CheckCodeDao implements ICheckCodeDao {

	@Override
	public void saveCheckCode(CheckCode checkCode) throws SQLException {
		String sql = "insert into checkcode(phonenumber, checkcode, starttime, endtime, type)"
				+ " values(?, ?, ?, ?, ?)";
		Object[] params = { 
				checkCode.getPhoneNumber(), 
				checkCode.getCheckCode(), 
				checkCode.getStartTime(),
				checkCode.getEndTime(), 
				checkCode.getType() };

		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	@Override
	public CheckCode getCheckCode(String phoneNumber, String checkcode, String type) throws SQLException {
		String sql = "select phonenumber, checkcode, starttime, endtime, type from checkcode " 
				+ " where phonenumber = ? and checkcode = ? and type = ? ";
		Object[] params = { 
				phoneNumber, 
				checkcode, 
				type };

		QueryRunner qr = new QueryRunner();
		ResultSetHandler<CheckCode> rsh = new BeanHandler<>(CheckCode.class);
		CheckCode code = qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
		return code;
	}
	
	
	
}
