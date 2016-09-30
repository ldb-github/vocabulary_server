package com.ldb.vocabulary.server.domain;

public class CommunicationContract {
	// 字符集
	public static final String CHARSET_UTF_8 = "UTF-8";
		
	public static final String KEY_CODE = "code";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_ERROR = "error";
	
	public static final int VALUE_CODE_OK = 0;
	public static final int VALUE_CODE_ERROR = 100;
	// 传入参数有误，比如格式不符合规范
	public static final int VALUE_CODE_PARAMSERROR = 400;
	// 数据库处理失败
	public static final int VALUE_CODE_DBERROR = 500;
	// 数据库处理失败
	public static final int VALUE_CODE_UNKNOWNERROR = 999;
	
	// Account
	public static final String KEY_ACCOUNT = "account";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_CONFIRMPWD = "confirmpwd";
	public static final String KEY_EMAIL = "email";
	// 短信验证码
	public static final String KEY_CHECKCODE = "checkcode";
	public static final String KEY_PHONENUMBER = "phonenumber";
	public static final String KEY_TOKEN = "token";
	// 帐户状态
	public static final String KEY_STATE = "state";
	public static final String KEY_REGISTER_TIME = "registertime";
	
	// Category
	public static final String KEY_CATEGORY_ID = "c_id";
	public static final String KEY_CATEGORY_SUBINDEX = "sub_index";
	public static final String KEY_CATEGORY_NAME = "c_name";
	public static final String KEY_CATEGORY_IMAGE = "image";
	public static final String KEY_CATEGORY_IMAGE_REMOTE = "image_remote";
	public static final String KEY_CATEGORY_FAVORITE_COUNT = "f_count";
	public static final String KEY_CATEGORY_WORD_COUNT = "w_count";
	public static final String KEY_CATEGORY_LANGUAGE = "lan";
	public static final String KEY_CATEGORY_SECOND_LANGUAGE = "s_lan";
	public static final String KEY_CATEGORY_CREATER = "creater";
	public static final String KEY_CATEGORY_CREATE_TIME = "create_time";
	public static final String KEY_CATEGORY_LIST = "category";
	public static final String KEY_PAGE = "page";
	public static final String KEY_SORT = "sort";
	public static final String KEY_SORT_TYPE = "s_type";
	public static final String METHOD_LIST = "list";
	public static final String METHOD_LIST_VOCABULARY = "listv";
	public static final String METHOD_IMAGE = "image";
	public static final String METHOD_ADD = "add";
	
	
	// Vocabulary
	public static final String KEY_VOCABULARY_LIST = "vocabulary";
	public static final String KEY_VOCABULARY_ID = "id";
	public static final String KEY_VOCABULARY_NAME = "name";
	public static final String KEY_VOCABULARY_IMAGE = "image";
	
}
