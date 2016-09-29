package com.ldb.vocabulary.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ldb.util.jdbc.JdbcUtil_DBUtils;
import com.ldb.vocabulary.server.dao.ICategoryDao;
import com.ldb.vocabulary.server.domain.Category;

public class CategoryDao implements ICategoryDao{

	@Override
	public List<Category> getCategoryList(int page, int pageSize, String[] sort, 
			String[] sortType, String secondLan) throws SQLException {
		int minNo = pageSize * (page - 1) + 1;
		int maxNo = pageSize * page;
		int i = 0;
		String sortStr = "";
		if(sort != null && sort.length > 0){
			while(i < sort.length){
				if(sort[i].equals("f")){
					sortStr = sortStr + ", FAVORITECOUNT " + (sortType[i].equals("d") ? "DESC" : "ASC");
				}else if(sort[i].equals("w")){
					sortStr = sortStr + ", WORDCOUNT " + (sortType[i].equals("d") ? "DESC" : "ASC");
				}else if(sort[i].equals("t")){
					sortStr = sortStr + ", CREATETIME " + (sortType[i].equals("d") ? "DESC" : "ASC");
				}
				i++;
			}
		}
		if(!sortStr.equals("")){
			sortStr = "        ORDER BY " + sortStr.substring(1);
//		}else{
//			sortStr = "        ORDER BY FAVORITECOUNT DESC ";
		}
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder
		.append("(")
		.append("SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, WORDCOUNT, ")
		.append("LANGUAGE, USERNAME, CREATETIME FROM (")
		.append("    SELECT ROWNUM NO, ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT,")
		.append("    WORDCOUNT, LANGUAGE, USERNAME, CREATETIME FROM (")
		.append("        SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, WORDCOUNT, ")
		.append("        LANGUAGE, USERNAME, CREATETIME FROM CATEGORY WHERE SUBINDEX = 0 ")
		.append(sortStr)
		.append("        )")
		.append("    ) WHERE NO >= ? AND NO <= ?")
		.append(")")
		.append("UNION ALL")
		.append("(")
		.append("SELECT A.* FROM CATEGORY A, (")
		.append("    SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, WORDCOUNT, ")
		.append("    LANGUAGE, USERNAME, CREATETIME FROM (")
		.append("        SELECT ROWNUM NO, ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, ")
		.append("        WORDCOUNT, LANGUAGE, USERNAME, CREATETIME FROM (")
		.append("            SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, WORDCOUNT, ")
		.append("            LANGUAGE, USERNAME, CREATETIME FROM CATEGORY WHERE SUBINDEX = 0 ")
		.append(sortStr)
		.append("            )")
		.append("       ) WHERE NO >= ? AND NO <= ? ")
		.append("    ) B WHERE A.ID = B.ID AND A.SUBINDEX <> 0 AND A.LANGUAGE <> B.LANGUAGE AND A.LANGUAGE = ?")
		.append(")");
		
		Object[] params = {minNo, maxNo, minNo, maxNo, secondLan};
		ResultSetHandler<List<Category>> rsh = new BeanListHandler<Category>(Category.class);
		QueryRunner qr = new QueryRunner();
		List<Category> categories = qr.query(JdbcUtil_DBUtils.getConnection(), sBuilder.toString(), rsh, params);

		return categories;
	}

	
	
}
