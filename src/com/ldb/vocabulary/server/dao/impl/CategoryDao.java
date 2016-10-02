package com.ldb.vocabulary.server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ldb.util.jdbc.JdbcUtil_DBUtils;

import com.ldb.vocabulary.server.dao.ICategoryDao;
import com.ldb.vocabulary.server.domain.Category;
import com.ldb.vocabulary.server.domain.Vocabulary;

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
		}
		
		StringBuilder sql = new StringBuilder();
		sql
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
		.append("UNION ALL") // 下面的sql是第二语言的数据
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
		.append("    ) B WHERE A.ID = B.ID AND A.SUBINDEX <> B.SUBINDEX AND A.LANGUAGE <> B.LANGUAGE AND A.LANGUAGE = ?")
		.append(")");
		
		Object[] params = {minNo, maxNo, minNo, maxNo, secondLan};
		ResultSetHandler<List<Category>> rsh = new BeanListHandler<Category>(Category.class);
		QueryRunner qr = new QueryRunner();
		List<Category> categories = qr.query(JdbcUtil_DBUtils.getConnection(), sql.toString(), rsh, params);

		return categories;
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		String sql = " INSERT INTO CATEGORY(ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, FAVORITECOUNT, WORDCOUNT, " + 
					 " LANGUAGE, USERNAME, CREATETIME) " + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "; // 
		Object[] params = {
				category.getId(),
				category.getSubIndex(),
				category.getName(),
				category.getImage(),
				category.getImageRemote(),
				category.getFavoriteCount(),
				category.getWordCount(),
				category.getLanguage(),
				category.getUsername(),
				category.getCreateTime() };
		
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	
	@Override
	public void addVocabulary(Vocabulary vocabulary) throws SQLException {
		String sql = " INSERT INTO VOCABULARY(ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, " + 
				 " LANGUAGE, USERNAME, CREATETIME) " + " VALUES(?, ?, ?, ?, ?, ?, ?, ?) "; // 
		Object[] params = {
				vocabulary.getId(),
				vocabulary.getSubIndex(),
				vocabulary.getName(),
				vocabulary.getImage(),
				vocabulary.getImageRemote(),
				vocabulary.getLanguage(),
				vocabulary.getUsername(),
				vocabulary.getCreateTime() };
		
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	@Override
	public void addCategoryVocabulary(Vocabulary vocabulary) throws SQLException {
		String cId = vocabulary.getCIdList().get(0);
		String cSubIndex = vocabulary.getcSubIndexList().get(0);
		
		String sql = " INSERT INTO CATEGORY_VOCABULARY(CID, CINDEX, VID, VINDEX) " + 
		      " VALUES(?, ?, ?, ?) ";
		Object[] params = {
				cId,
				cSubIndex,
				vocabulary.getId(),
				vocabulary.getSubIndex() };
		
		QueryRunner qr = new QueryRunner();
		qr.update(JdbcUtil_DBUtils.getConnection(), sql, params);
	}

	@Override
	public List<Vocabulary> getVocabularyList(String categoryId, int categoryIndex, int page, 
			int pageSize, String secondLan) throws SQLException {
		int minNo = pageSize * (page - 1) + 1;
		int maxNo = pageSize * page;
		
		StringBuilder sql = new StringBuilder();
		sql.append("(");
		sql.append("SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, LANGUAGE, USERNAME, CREATETIME FROM(");
		sql.append("    SELECT ROWNUM NO, ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, LANGUAGE, USERNAME, CREATETIME FROM(");
		sql.append("        SELECT A.* FROM VOCABULARY A, CATEGORY_VOCABULARY B ");
		sql.append("        WHERE A.ID = B.VID AND A.SUBINDEX = B.VINDEX AND B.CID = ? AND B.CINDEX = ?");
		sql.append("       ORDER BY A.ID, A.SUBINDEX ");
		sql.append("    )");
		sql.append(")WHERE NO >= ? AND NO <= ?");
		sql.append(")");
		sql.append("UNION ALL");
		sql.append("(");
		sql.append("SELECT C.* FROM VOCABULARY C, (");
		sql.append("    SELECT ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, LANGUAGE, USERNAME, CREATETIME FROM(");
		sql.append("        SELECT ROWNUM NO, ID, SUBINDEX, NAME, IMAGE, IMAGEREMOTE, LANGUAGE, USERNAME, CREATETIME FROM(");
		sql.append("            SELECT A.* FROM VOCABULARY A, CATEGORY_VOCABULARY B ");
		sql.append("            WHERE A.ID = B.VID AND A.SUBINDEX = B.VINDEX AND B.CID = ? AND B.CINDEX = ?");
		sql.append("            ORDER BY A.ID, A.SUBINDEX ");
		sql.append("       )");
		sql.append("    )WHERE NO >= ? AND NO <= ?");
		sql.append(") D WHERE C.ID = D.ID AND C.SUBINDEX <> D.SUBINDEX AND C.LANGUAGE <> D.LANGUAGE AND C.LANGUAGE = ?");
		sql.append(")");

		Object[] params = {categoryId, categoryIndex, minNo, maxNo, categoryId, categoryIndex, minNo, maxNo, secondLan};
		ResultSetHandler<List<Vocabulary>> rsh = new BeanListHandler<Vocabulary>(Vocabulary.class);
		QueryRunner qr = new QueryRunner();
		List<Vocabulary> vocabularies = qr.query(JdbcUtil_DBUtils.getConnection(), sql.toString(), rsh, params);

		return vocabularies;
	}

	
	
}
