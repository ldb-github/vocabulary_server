package com.ldb.util.jdbc;

import java.sql.ResultSet;

/**
 * @ClassName: ResultSetHandler
 * @Description:结果集处理器接口
 * @author: 孤傲苍狼
 * @date: 2014-10-5 下午12:01:27
 *
 */
public interface ResultSetHandler {

	/**
	 * @Method: handler
	 * @Description: 结果集处理方法
	 * @Anthor:孤傲苍狼
	 *
	 * @param rs
	 *            查询结果集
	 * @return
	 */
	public Object handler(ResultSet rs);
}
