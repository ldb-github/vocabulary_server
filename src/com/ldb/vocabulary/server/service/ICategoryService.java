package com.ldb.vocabulary.server.service;

import java.io.OutputStream;

import com.ldb.vocabulary.server.domain.Category;
import com.ldb.vocabulary.server.domain.Vocabulary;

public interface ICategoryService {

	/**
	 * 获取类别列表
	 * @param page 页码，从1开始
	 * @param sort 排序方式:f收藏量，w词汇量；如果多种混合，则以逗号间隔
	 * @param sortType 排序类型:a升序，d降序；如果多种混合，则以逗号间隔，并且与sort一一对应
	 * @param secondLan 第二语言
	 * @return
	 */
	String getCategoryList(int page, String sort, String sortType, String secondLan);
	/**
	 * 获取类别词汇列表
	 * @param categoryId 类别id
	 * @param categoryIndex TODO
	 * @param page 页码，从1开始
	 * @param secondLan 第二语言
	 * @return
	 */
	String getCategoryItemList(String categoryId, int categoryIndex, int page, String secondLan);
	/**
	 * 获取图片
	 * @param categoryId 类别id
	 * @param imageName 图片名称，包括后缀名
	 * @param out 输出数据流
	 */
	void getImage(String categoryId, String imageName, OutputStream out);
	/**
	 * 添加类别
	 * @param category
	 * @return
	 */
	String addCategory(Category category);
	/**
	 * 添加词汇
	 * @param vocabulary
	 * @return
	 */
	String addVocabulary(Vocabulary vocabulary);
}
