package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;

import com.ldb.vocabulary.server.domain.RegisterBean;

public interface IRegisterDao {

	int getRegisteredCountOfImei(String imei) throws SQLException;
	int getRegisteredCountOfMac(String mac) throws SQLException;
	RegisterBean getRegisterBean(String phoneNumber, String checkCode) throws SQLException;
	String saveRegisterInfoForCheckCode(RegisterBean registerBean) throws SQLException;
}
