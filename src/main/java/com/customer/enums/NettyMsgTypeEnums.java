package com.customer.enums;
/** 
 * websocket消息类型枚举
* @author yangxy
* @version 创建时间：2024年6月3日 上午8:50:46 
*/
public enum NettyMsgTypeEnums {
	/**
	 * 系统错误提示
	 */
	ERROR_PROMPT_MSG(3),
	/**
	 * 转人工
	 */
	ARTIFICIAL_MSG(2),
	/**
	 * 普通消息
	 */
	ORDINARY_MSG(1),
	/**
	 * 客服消息
	 */
	CUSTOMER_MSG(0);
	public int type;

	private NettyMsgTypeEnums(int type) {
		this.type = type;
	}
	
	
}
