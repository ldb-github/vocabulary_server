package com.ldb.vocabulary.server.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ldb.vocabulary.server.domain.CommunicationContract;
import com.ldb.vocabulary.server.service.ICategoryService;

import sun.nio.ch.AbstractPollArrayWrapper;

public class CategoryService implements ICategoryService{

	@Override
	public String getCategoryList(int page, String sort, String sortType) {
		// TODO 实现从数据库获取词汇类别列表
		
		JSONObject resultJson = new JSONObject();
		resultJson.put(CommunicationContract.KEY_CODE, CommunicationContract.VALUE_CODE_OK);
		resultJson.put(CommunicationContract.KEY_MESSAGE, "类别列表获取成功");
		
		JSONArray list = new JSONArray();
		JSONObject listItem; //= new JSONObject()
		for(int i = 0; i < 10; i++){
			listItem = new JSONObject();
			listItem.put(CommunicationContract.KEY_CATEGORY_ID, i);
			listItem.put(CommunicationContract.KEY_CATEGORY_NAME, "animal" + i);
			listItem.put(CommunicationContract.KEY_CATEGORY_IMAGE, 
					"http://android.ldb.com:8080/vocabulary/category/image/" + i + "/" + i + ".jpeg");
			listItem.put(CommunicationContract.KEY_CATEGORY_FAVORITE_COUNT, i);
			listItem.put(CommunicationContract.KEY_CATEGORY_WORD_COUNT, i);
			list.put(listItem);
		}
		resultJson.put(CommunicationContract.KEY_CATEGORY_LIST, list);
		
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
