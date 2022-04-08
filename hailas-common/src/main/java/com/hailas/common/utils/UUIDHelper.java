package com.hailas.common.utils;

import java.util.UUID;

/**
 * 全局统一标示符辅助类
 * 
 * @author wulin
 * @since 2015年4月21日 下午2:08:25
 */
public final class UUIDHelper {
	
	/**
	 * 获取UUID
	 * @return
	 * @author wulin
	 * @since 2015年4月21日 下午2:08:15
	 */
	public static String get(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
