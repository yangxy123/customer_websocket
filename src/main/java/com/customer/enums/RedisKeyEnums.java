package com.customer.enums;

/**
 * redis缓存key枚举
 * @author yangxy
 * @version 创建时间：2023年10月26日 下午5:12:00
 */
public enum RedisKeyEnums {
	/**
	 * 登录token缓存
	 */
	LOGIN_KEY("login:",0),
	/**
	 * AI智能回复配置缓存
	 */
	AI_REPLY_KEY("ai_reply",4),
	/**
	 * 客服人员对应用户缓存
	 */
	CUSTOMER_TO_USER("customer_to_user:",4);
	/**
	 * redisKey
	 */
	public String key;
	/**
	 * 数据类型类型（0 string;1 list;2 zset;3 set;4 hash）
	 */
	public Integer dateType;

	private RedisKeyEnums(String key, Integer dateType) {
		this.key = key;
		this.dateType = dateType;
	}
}
