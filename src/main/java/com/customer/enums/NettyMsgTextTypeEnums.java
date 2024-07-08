package com.customer.enums;
/** 
 * 消息内容类型枚举
* @author yangxy
* @version 创建时间：2024年6月3日 上午8:48:14 
*/
public enum NettyMsgTextTypeEnums {
	/**
	 * 图片
	 */
	IMG(1),
	/**
	 * 文本
	 */
	TEXT(0);
	public int textType;

	private NettyMsgTextTypeEnums(int textType) {
		this.textType = textType;
	}
	
	
}
