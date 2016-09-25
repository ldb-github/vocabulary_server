package com.ldb.vocabulary.server.domain;

/**
 * 帐户操作类型
 * @author lsp
 *
 */
public enum AccountOpType {

	USER_PASSWORD("up"),
	EMAIL_PASSWORD("ep"),
	PHONE_CHECKCODE("pc"),
	TOKEN("tk"),
	UNKNOWN("");
	
	private String name;
	
	AccountOpType(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static AccountOpType geType(String type){
		if(type.equals(USER_PASSWORD.toString())){
			return USER_PASSWORD;
		}
		if(type.equals(EMAIL_PASSWORD.toString())){
			return EMAIL_PASSWORD;
		}
		if(type.equals(PHONE_CHECKCODE.toString())){
			return PHONE_CHECKCODE;
		}
		if(type.equals(TOKEN.toString())){
			return TOKEN;
		}
		
		return UNKNOWN;
	}

}
