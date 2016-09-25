package com.ldb.vocabulary.server.service;

public interface ICategoryService {

	/**
	 * 获取类别列表
	 * @param page 页码，从1开始
	 * @param sort 排序方式:f收藏量，w词汇量；如果多种混合，则以逗号间隔
	 * @param sortType 排序方式:a升序，d降序；如果多种混合，则以逗号间隔，并且与sort一一对应
	 * @return
	 */
	String getCategoryList(int page, String sort, String sortType);
	/**
	 * 获取类别词汇列表
	 * @param categoryId 类别id
	 * @param page 页码，从1开始
	 * @return
	 */
	String getCategoryItemList(String categoryId, int page);
	/**
	 * 获取图片
	 * @param categoryId 类别id
	 * @param imageName 图片名称，包括后缀名
	 * @return
	 */
	byte[] getImage(String categoryId, String imageName);
}
