package com.customer.enums;
/** 
 * 状态枚举
* @author yangxy
* @version 创建时间：2024年5月30日 下午9:12:22 
*/
public enum StatusEnums {
	/**
	 * 停用
	 */
	UNENABLE(0),
	/**
	 * 启用
	 */
	ENABLE(1);
	public int status;

	private StatusEnums(int status) {
		this.status = status;
	}
}
