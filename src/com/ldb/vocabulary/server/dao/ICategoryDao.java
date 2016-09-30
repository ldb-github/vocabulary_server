package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;
import java.util.List;

import com.ldb.vocabulary.server.domain.Category;

public interface ICategoryDao {

	/**
	 * 获取词汇类别列表
	 * @param page 请求页码，从1开始
	 * @param pageSize 页面大小
	 * @param sort 排序方式：f收藏量，w词汇量, t创建时间；如果多种混合，则以逗号间隔
	 * @param sortType 排序类型：a升序，d降序；如果多种混合，则以逗号间隔，并且与sort一一对应
	 * @param secondLan 第二语言
	 * @return
	 * @throws SQLException
	 */
	public List<Category> getCategoryList(int page, int pageSize, String[] sort, 
			String[] sortType, String secondLan) throws SQLException;
	/**
	 * 添加词汇类别
	 * @param category
	 * @throws SQLException
	 */
	public void addCategory(Category category) throws SQLException;
}
