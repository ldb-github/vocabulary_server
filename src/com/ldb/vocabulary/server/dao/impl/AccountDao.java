package com.ldb.vocabulary.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.ldb.util.DateUtils;
import com.ldb.util.jdbc.JdbcUtil_DBUtils;
import com.ldb.vocabulary.server.dao.IAccountDao;
import com.ldb.vocabulary.server.domain.Account;
import com.sun.jmx.snmp.Timestamp;

public class AccountDao implements IAccountDao{

	@Override
	public Account find(String userName) throws SQLException {
		String sql = "select * from account where username = ? or phonenumber = ? or email = ? ";
		Object[] params = {userName, userName, userName};
		ResultSetHandler<Account> rsh = new BeanHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		return (Account) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}

	@Override
	public Account find(String userName, String userPwd) throws SQLException {
		String sql = "select * from account where username = ? and password = ?";
		Object[] params = {userName, userPwd};
		ResultSetHandler<Account> rsh = new BeanHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		return (Account) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}
	
	@Override
	public Account findByPhoneNumber(String phoneNumber) throws SQLException {
		String sql = "select * from account where phonenumber = ? or username = ?";
		Object[] params = {phoneNumber, phoneNumber};
		ResultSetHandler<Account> rsh = new BeanHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		return (Account) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}

	@Override
	public Account findByEmail(String email) throws SQLException {
		String sql = "select * from account where username = ? and email = ?";
		Object[] params = {email, email};
		ResultSetHandler<Account> rsh = new BeanHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		return (Account) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}

	@Override
	public Account findByToken(String token) throws SQLException {
		String sql = "select * from account where token = ? ";
		Object[] params = {token};
		ResultSetHandler<Account> rsh = new BeanHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		return (Account) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}

	@Override
	public List<Account> findByPhoneIMIE(String phoneIMIE) {
//		String sql = "select * from op_register where phoneIMIE = ?";
//		String[] params = {phoneIMIE};
//		ResultSetHandler rsh = new BeanListHandler(User.class);
//		try {
//			return (List<User>) JdbcUtil.query(sql, params, rsh);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		return null;
	}

	@Override
	public void add(Account account) throws SQLException {
		String sql = "insert into account(username, password, phonenumber, email, state, registertime) " + 
	                 " values(?, ?, ?, ?, ?, ?)";
		Object[] params = {
				account.getUsername(), 
				account.getPassword(), 
				account.getPhoneNumber(), 
				account.getEmail(), 
				account.getState(),
				account.getRegisterTime()};
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	@Override
	public void update(Account account) throws SQLException {
		String sql = "update account set token = ? and starttime = ? and endtime = ? where username = ? ";
		Object[] params = {
				account.getToken(), 
				account.getStartTime(), 
				account.getEndTime(), 
				account.getUsername() };
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	
}
