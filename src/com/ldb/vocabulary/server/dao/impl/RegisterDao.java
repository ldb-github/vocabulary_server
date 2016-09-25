package com.ldb.vocabulary.server.dao.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

//import com.ldb.util.jdbc.BeanHandler;
//import com.ldb.util.jdbc.BeanListHandler;
//import com.ldb.util.jdbc.JdbcUtil;
import com.ldb.util.jdbc.JdbcUtil_DBUtils;
//import com.ldb.util.jdbc.ResultSetHandler;
import com.ldb.vocabulary.server.domain.RegisterBean;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ldb.vocabulary.server.dao.IRegisterDao;
import com.ldb.vocabulary.server.domain.Account;

public class RegisterDao implements IRegisterDao {

	@Override
	public int getRegisteredCountOfImei(String imei) throws SQLException{
		int count = 0;
		String sql = "select account.* from account, op_register where op_register.userid is not null " + 
		             " and account.id = op_register.userid and op_register.imei = ? ";
		Object[] params = {imei};
		ResultSetHandler<List<Account>> rsh = new BeanListHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		List<Account> users = (List<Account>) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
		count = users.size();
		return count;
	}
	
	@Override
	public int getRegisteredCountOfMac(String mac) throws SQLException{
		int count = 0;
		String sql = "select account.* from account, op_register where op_register.userid is not null " + 
		             " and account.id = op_register.userid and op_register.mac = ? ";
		Object[] params = {mac};
		ResultSetHandler<List<Account>> rsh = new BeanListHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		List<Account> users = (List<Account>) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
		count = users.size();
		return count;
	}
	
	@Override
	public RegisterBean getRegisterBean(String phoneNumber, String checkCode) throws SQLException{
		String sql = "select * from op_register where phonenumber = ? and checkCode = ? and userid is null";
		Object[] params = {phoneNumber, checkCode};
		ResultSetHandler<RegisterBean> rsh = new BeanHandler<>(RegisterBean.class);
		QueryRunner qr = new QueryRunner();
		return (RegisterBean) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
	}
	
	/**
	 * 保存为获取验证码而请求的注册信息 
	 */
	@Override
	public String saveRegisterInfoForCheckCode(RegisterBean registerBean) throws SQLException{
		String registerId = null;
		
		String sql = "insert into op_register(phonenumber, checkcode, type, source, ip, mac, imei, location, starttime, endtime)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {registerBean.getPhoneNumber(), 
				registerBean.getCheckCode(), 
				registerBean.getType(),
				registerBean.getSource(),
				registerBean.getIp(),
				registerBean.getMac(),
				registerBean.getImei(),
				registerBean.getLocation(),
				registerBean.getStartTime(),
				registerBean.getEndTime()};
		
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
		
		String sql2 = "select id from op_register where phonenumber = ? and checkcode = ? and type = ? "
				+ " and starttime = ? and endtime = ?"
				;
		Object[] params2 = {registerBean.getPhoneNumber(), 
				registerBean.getCheckCode(), 
				registerBean.getType(),
				registerBean.getStartTime(),
				registerBean.getEndTime()};
		
		ColumnListHandler<String> rsh = new ColumnListHandler<String>("id");//BeanHandler<>(String.class);
		List<String> registerIds = qr.query(JdbcUtil_DBUtils.getConnection(), sql2, rsh, params2);
		if (registerIds.size() > 0) {
			registerId = String.valueOf(registerIds.get(0));
		}
		return registerId;
	}
}
