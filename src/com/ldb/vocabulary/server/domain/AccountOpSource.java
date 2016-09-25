package com.ldb.vocabulary.server.domain;

/**
 * 帐户操作来源
 * @author lsp
 *
 */
public enum AccountOpSource {

	BROWSER("br"), // 浏览器
	ANDROID("an"), // 安卓客户端
	APPLE("ap"), // 苹果客户端
	ANDROID_PAD("np"), // 安卓平板客户端
	APPLE_PAD("pp"), // 苹果平板客户端
	UNKNOWN("");
	
	private String name;
	
	AccountOpSource(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static AccountOpSource geSource(String type){
		if(type.equals(BROWSER.toString())){
			return BROWSER;
		}
		if(type.equals(ANDROID.toString())){
			return ANDROID;
		}
		if(type.equals(APPLE.toString())){
			return APPLE;
		}
		if(type.equals(ANDROID_PAD.toString())){
			return ANDROID_PAD;
		}
		if(type.equals(APPLE_PAD.toString())){
			return APPLE_PAD;
		}
		
		return UNKNOWN;
	}
}
