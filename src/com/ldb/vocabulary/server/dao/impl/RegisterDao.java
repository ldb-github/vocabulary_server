package com.ldb.vocabulary.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.ldb.util.jdbc.JdbcUtil_DBUtils;
import com.ldb.vocabulary.server.domain.RegisterBean;
import com.ldb.vocabulary.server.dao.IRegisterDao;
import com.ldb.vocabulary.server.domain.Account;

public class RegisterDao implements IRegisterDao {

	@Override
	public int getRegisteredCountOfImei(String imei) throws SQLException{
		int count = 0;
		String sql = " SELECT ACCOUNT.* FROM ACCOUNT, OP_REGISTER WHERE OP_REGISTER.USERID IS NOT NULL " + 
                	 " AND ACCOUNT.ID = OP_REGISTER.USERID AND OP_REGISTER.IMEI = ? ";
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
		String sql = " SELECT ACCOUNT.* FROM ACCOUNT, OP_REGISTER WHERE OP_REGISTER.USERID IS NOT NULL " + 
                	 " AND ACCOUNT.ID = OP_REGISTER.USERID AND OP_REGISTER.MAC = ? ";
		Object[] params = {mac};
		ResultSetHandler<List<Account>> rsh = new BeanListHandler<>(Account.class);
		QueryRunner qr = new QueryRunner();
		List<Account> users = (List<Account>) qr.query(JdbcUtil_DBUtils.getConnection(), sql, rsh, params);
		count = users.size();
		return count;
	}
	
	@Override
	public RegisterBean getRegisterBean(String phoneNumber, String checkCode) throws SQLException{
		String sql = " SELECT * FROM OP_REGISTER WHERE PHONENUMBER = ? AND CHECKCODE = ? AND USERID IS NULL ";
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
		
		String sql = " INSERT INTO OP_REGISTER(PHONENUMBER, CHECKCODE, TYPE, SOURCE, IP, MAC, IMEI, LOCATION, " + 
					 " STARTTIME, ENDTIME) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		
		String sql2 = " SELECT ID FROM OP_REGISTER WHERE PHONENUMBER = ? AND CHECKCODE = ? AND TYPE = ? " + 
					  " AND STARTTIME = ? AND ENDTIME = ? ";
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
