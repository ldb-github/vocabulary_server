package com.ldb.vocabulary.server.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ldb.vocabulary.server.dao.ICategoryDao;
import com.ldb.vocabulary.server.dao.impl.CategoryDao;
import com.ldb.vocabulary.server.domain.Category;
import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.service.ICategoryService;

public class CategoryService implements ICategoryService{

	private static final int PAGE_SIZE = 50;
	
	private ICategoryDao categoryDao = new CategoryDao();
	
	@Override
	public String getCategoryList(int page, String sort, String sortType, String secondLan) {
		// TODO 实现从数据库获取词汇类别列表
		
		int code = CommunicationContract.VALUE_CODE_UNKNOWNERROR;
		String message = null;
		JSONObject resultJson = new JSONObject();
		
		String[] sortArray = null;
		String[] sortTypeArray = null;
		if(sort != null && !sort.trim().isEmpty()){
			sortArray = sort.split(",");
		}
		if(sortType != null && !sortType.trim().isEmpty()){
			sortTypeArray = sortType.split(",");
		}
		boolean isContinue = true;
		if(sortArray != null){
			if(sortTypeArray == null || sortArray.length != sortTypeArray.length){
				isContinue = false;
				code = CommunicationContract.VALUE_CODE_ERROR;
				message = "排序方式与排序类型不一致";
			}
		}
		
		if(secondLan == null){
			secondLan = "zh";
		}
		
		if(isContinue){
			try {
				List<Category> categories = 
						categoryDao.getCategoryList(page, PAGE_SIZE, sortArray, sortTypeArray, secondLan);
				code = CommunicationContract.VALUE_CODE_OK;
				message = "类别列表获取成功";
				if(!categories.isEmpty()){
					JSONArray list = new JSONArray();
					JSONObject listItem; 
					for(Category category : categories){
						listItem = new JSONObject();
						listItem.put(CommunicationContract.KEY_CATEGORY_ID, category.getId());
						listItem.put(CommunicationContract.KEY_CATEGORY_SUBINDEX, category.getSubIndex());
						listItem.put(CommunicationContract.KEY_CATEGORY_NAME, category.getName());
						listItem.put(CommunicationContract.KEY_CATEGORY_IMAGE, category.getImage());
						listItem.put(CommunicationContract.KEY_CATEGORY_IMAGE_REMOTE, category.getImageRemote());
	//							"http://android.ldb.com:8080/vocabulary/category/image/1/1.jpeg");
						listItem.put(CommunicationContract.KEY_CATEGORY_FAVORITE_COUNT, category.getFavoriteCount());
						listItem.put(CommunicationContract.KEY_CATEGORY_WORD_COUNT, category.getWordCount());
						listItem.put(CommunicationContract.KEY_CATEGORY_LANGUAGE, category.getLanguage());
						listItem.put(CommunicationContract.KEY_CATEGORY_CREATER, category.getUsername());
						listItem.put(CommunicationContract.KEY_CATEGORY_CREATE_TIME, category.getCreateTime());
						list.put(listItem);
					}
					resultJson.put(CommunicationContract.KEY_CATEGORY_LIST, list);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code = CommunicationContract.VALUE_CODE_DBERROR;
				message = "获取词汇分类列表失败：数据库请求错误";
			}
		}
		
		resultJson.put(CommunicationContract.KEY_CODE, code);
		resultJson.put(CommunicationContract.KEY_MESSAGE, message);
		
		return resultJson.toString();
	}

	@Override
	public String getCategoryItemList(String categoryId, int page) {
		// TODO 实现从数据库获取词汇列表
		
		JSONObject resultJson = new JSONObject();
		resultJson.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_OK);
		resultJson.put(CommunicationContract.KEY_MESSAGE, "词汇列表获取成功");
		
		resultJson.put(CommunicationContract.KEY_CATEGORY_ID, categoryId);
		
		if(categoryId.equals("1")){
			JSONArray list = new JSONArray();
			JSONObject listItem = new JSONObject();
			listItem.put(CommunicationContract.KEY_VOCABULARY_ID, 1);
			listItem.put(CommunicationContract.KEY_VOCABULARY_NAME, "w");
			listItem.put(CommunicationContract.KEY_VOCABULARY_IMAGE, 
					"http://android.ldb.com:8080/vocabulary/category/image/" + categoryId + "/w.jpeg");
			list.put(listItem);
			
			listItem = new JSONObject();
			listItem.put(CommunicationContract.KEY_VOCABULARY_ID, 2);
			listItem.put(CommunicationContract.KEY_VOCABULARY_NAME, "y");
			listItem.put(CommunicationContract.KEY_VOCABULARY_IMAGE, 
					"http://android.ldb.com:8080/vocabulary/category/image/" + categoryId + "/y.jpeg");
			list.put(listItem);
			
			listItem = new JSONObject();
			listItem.put(CommunicationContract.KEY_VOCABULARY_ID, 3);
			listItem.put(CommunicationContract.KEY_VOCABULARY_NAME, "z");
			listItem.put(CommunicationContract.KEY_VOCABULARY_IMAGE, 
					"http://android.ldb.com:8080/vocabulary/category/image/" + categoryId + "/z.jpeg");
			list.put(listItem);
			
			resultJson.put(CommunicationContract.KEY_VOCABULARY_LIST, list);
		}
		
		return resultJson.toString();

	}

	@Override
	public byte[] getImage(String categoryId, String imageName) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
