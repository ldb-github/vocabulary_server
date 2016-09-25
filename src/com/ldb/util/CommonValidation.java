package com.ldb.util;

public class CommonValidation {

	public static boolean validateEmail(String email) {
		if (!email.matches("\\w+@\\w+(\\.\\w+)+")) {
			return false;
		}

		return true;
	}
	
	public static boolean validatePhoneNumber(String phoneNumber){
		// TODO 验证phoneNumber
		
		return true;
	}
}
