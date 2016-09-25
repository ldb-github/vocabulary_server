package com.ldb.util;

import java.util.Random;
import java.util.UUID;

public class CommonUtils {

	/**
	 * 生成6位随机数字
	 * 
	 * @return
	 */
	public static String makeNumber() {
		Random random = new Random();
		String num = random.nextInt(999999) + "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6 - num.length(); i++) {
			sb.append("0");
		}
		num = sb.toString() + num;
		return num;
	}
	
	/**
	 * 生成位随机数字
	 * @param len 随机数长度
	 * @return 随机数字
	 */
	public static String makeNumber(int len) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void sendSMS(String phoneNumber, String message){
		// TODO 发送短信
	}
	
	public static UUID makeUUID(){
		return UUID.randomUUID();
	}
	
	public static String makeToken(){
		return makeUUID().toString();
	}
}
