package com.ldb.vocabulary.server.dao;

import java.sql.SQLException;
import java.util.List;

import com.ldb.vocabulary.server.domain.Category;

public interface ICategoryDao {

	public List<Category> getCategoryList(int page, int pageSize, String[] sort, 
			String[] sortType, String secondLan) throws SQLException;
}
