package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;

import com.ldb.vocabulary.server.domain.SystemParameter;

public interface ICommonDao {

	/**
	 * 根据参数名获取参数
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	SystemParameter getParameterByName(String name) throws SQLException;
	/**
	 * 获取参数值
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	String getParameterValue(String name) throws SQLException;
	/**
	 * 获取参数并更新
	 * @param name
	 * @param update
	 * @return
	 * @throws SQLException
	 */
	String getParameterValueForUpdate(String name, String update) throws SQLException;
	/**
	 * 设置参数
	 * @param name
	 * @param value
	 * @throws SQLException
	 */
	void setParameter(String name, String value) throws SQLException;
}
