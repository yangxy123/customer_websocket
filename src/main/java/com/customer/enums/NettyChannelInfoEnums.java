package com.customer.enums;
/** 
* @author yangxy
* @version 创建时间：2024年5月31日 下午6:59:09 
*/
public enum NettyChannelInfoEnums {
	/**
	 * 用户语言
	 */
	CHANNEL_LANGUAGE("language"),
	/**
	 * 用户类型
	 */
	CHANNEL_USERTYPE("userType"),
	/**
	 * websocket连接用户名
	 */
	CHANNEL_USER_NAME("userName"),
	/**
	 * 客服用户名
	 */
	CHANNEL_CUSTOMER_NAME("customerName");
	public String key;

	private NettyChannelInfoEnums(String key) {
		this.key = key;
	}
}
