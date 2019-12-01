package com.yx.common.utils;

import java.util.UUID;

/**
 * 该工具类来自于老项目
 * 
 * @author TangHuaLiang
 * @since 2018-07-19
 */
public class UUIDUtil {
	/**
	 * 随机生成UUID 32位字符串
	 * @return String uuid
	 */
	public static String getUUID() {
		
		String uuid = UUID.randomUUID().toString();
		
		uuid = uuid.replace("-", "");
		
		return uuid;
	}
}
