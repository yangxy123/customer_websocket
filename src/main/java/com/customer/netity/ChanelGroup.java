package com.customer.netity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import io.netty.channel.Channel;

/** 
 * netty 通道管理
* @author yangxy
* @version 创建时间：2024年5月29日 上午11:13:15 
*/
public class ChanelGroup {
	/*
	 * 存放用户与Chanel的对应信息
	 */
	public static Map<String,Channel> userChanelMap = Maps.newConcurrentMap();
	
	/*
	 * 财务审核通知管道
	 */
	public static Map<String,Channel> financeChanelMap = Maps.newConcurrentMap();
	
	/*
	 * 客服介入等待管道
	 */
	public static Map<String,Channel> customerChanelMap = Maps.newConcurrentMap();
	
//	/**
//	 * 客服服务用户对应
//	 */
//	public static Map<String,List<String>> serviceUser = Maps.newConcurrentMap();
}
