package com.customer.enums;

/**
 * netty连接用户类型枚举
 * 
 * @author yangxy
 * @version 创建时间：2024年6月1日 上午10:03:08
 */
public enum NettyUserTypeEnums {
	/**
	 * 财务用户
	 */
	FINANCE_USER("1"),
	/**
	 * 客服用户
	 */
	CUSTOMER_USER("2"),
	/**
	 * 普通用户
	 */
	REGULAR_USER("0");

	public String userType;

	private NettyUserTypeEnums(String userType) {
		this.userType = userType;
	}

}
