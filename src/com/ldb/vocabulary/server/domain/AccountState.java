package com.ldb.vocabulary.server.domain;

/**
 * 帐户状态
 * @author lsp
 *
 */
public enum AccountState {

	UNACTIVATED(0),
	NORMAL(1),
	LOCKED(2),
	EXPIRED(3),
	UNKNOWN(-1);
	
	private int value;
	
	AccountState(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public static AccountState geType(int state){
		if(state == UNACTIVATED.getValue()){
			return UNACTIVATED;
		}
		if(state == NORMAL.getValue()){
			return NORMAL;
		}
		if(state == LOCKED.getValue()){
			return LOCKED;
		}
		if(state == EXPIRED.getValue()){
			return EXPIRED;
		}
		
		return UNKNOWN;
	}
}
