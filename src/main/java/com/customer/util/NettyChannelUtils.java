package com.customer.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/** 
 * netty 通道处理工具类
* @author yangxy
* @version 创建时间：2024年6月1日 上午9:51:46 
*/
public class NettyChannelUtils {
	/**
	 * 根据key从通道中获取对应信息
	* @author yangxy
	* @version 创建时间：2024年5月31日 下午6:57:27 
	* @param ctx
	* @param key
	* @return
	 */
	public static String getInfoFromChannel(Channel channel,String key) {
		AttributeKey<String> aAttributeKey = AttributeKey.valueOf(key);
		String info = channel.attr(aAttributeKey).get();
		return info;
	}
	
	/**
	 * 向通道添加属性
	* @author yangxy
	* @version 创建时间：2024年5月31日 下午7:11:34 
	* @param ctx
	* @param key
	* @param value
	 */
	public static void setInfoToChannel(Channel channel,String key,String value) {
		AttributeKey<String> attributeKey = AttributeKey.valueOf(key);
		channel.attr(attributeKey).setIfAbsent(value);
	}
	
	/**
	 * 根据key移除通道信息
	* @author yangxy
	* @version 创建时间：2024年5月31日 下午7:27:38 
	* @param channel
	* @param key
	 */
	public static void removeInfoFromChannel(Channel channel,String key) {
		AttributeKey<String> attributeKey = AttributeKey.valueOf(key);
		channel.attr(attributeKey).remove();
	}
}
