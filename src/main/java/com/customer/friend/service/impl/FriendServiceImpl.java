package com.customer.friend.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.customer.dto.MsgDto;
import com.customer.enums.NettyChannelInfoEnums;
import com.customer.enums.RedisKeyEnums;
import com.customer.exception.BusinessException;
import com.customer.friend.service.FriendService;
import com.customer.netity.ChanelGroup;
import com.customer.netity.WebSocketHandler;
import com.customer.resp.ApiResp;
import com.customer.util.NettyChannelUtils;
import com.customer.util.RedisUtils;
import com.customer.util.SecurityFrameworkUtils;
import com.google.common.collect.Lists;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/** 
* @author yangxy
* @version 创建时间：2024年5月31日 下午7:52:57 
*/
@Service
public class FriendServiceImpl implements FriendService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private WebSocketHandler webSocketHandler;

	@Override
	public ApiResp<List<String>> getFriends() {
//		// TODO Auto-generated method stub
		String userName = SecurityFrameworkUtils.getLoginUser().getUserName();
		List<String> list = Lists.newArrayList();
		if(redisUtils.hasKey(RedisKeyEnums.CUSTOMER_TO_USER+userName)) {
			Map<Object, Object> hmget = redisUtils.hmget(RedisKeyEnums.CUSTOMER_TO_USER+userName);
			list = hmget.keySet().stream().map(Object::toString).collect(Collectors.toList());
		}
		return ApiResp.sucess(list);
	}
	
	public ApiResp<List<String>> getWaitFriends(){
		List<String> list = Lists.newArrayList();
		for(String key : ChanelGroup.userChanelMap.keySet()) {
			Channel channel = ChanelGroup.userChanelMap.get(key);
			String infoFromChannel = NettyChannelUtils.getInfoFromChannel(channel, NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
			if(StringUtils.isEmpty(infoFromChannel)) {
				list.add(NettyChannelUtils.getInfoFromChannel(channel,NettyChannelInfoEnums.CHANNEL_USER_NAME.key));
			}
		}
		return ApiResp.sucess(list);
	}

	@Override
	public ApiResp<String> intervene(String userName) {
		// TODO Auto-generated method stub
		Channel channel = ChanelGroup.userChanelMap.get(userName);
		if(ObjectUtils.isEmpty(channel)) {
			throw new BusinessException("用户未在线");
		}
		
		String customerName = SecurityFrameworkUtils.getLoginUser().getUserName();
		String infoFromChannel = NettyChannelUtils.getInfoFromChannel(channel, NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
		
		if(StringUtils.isEmpty(infoFromChannel)) {
			throw new BusinessException("其他客服人员正在对接该会员");
		}
		
		NettyChannelUtils.setInfoToChannel(channel, NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key, customerName);
		ChanelGroup.userChanelMap.put(userName, channel);
		
		MsgDto msgDto = new MsgDto();
		msgDto.setReceiver(userName);
		msgDto.setSender(customerName);
		msgDto.setText("你好，客服"+customerName+"很高兴为你服务。请问有什么问题需要咨询");
		msgDto.setType(0);
		webSocketHandler.sendMsgToUser(userName, msgDto);
		
		redisUtils.hset(RedisKeyEnums.CUSTOMER_TO_USER.key+customerName, msgDto.getReceiver(), msgDto.getReceiver());
		redisUtils.expire(RedisKeyEnums.CUSTOMER_TO_USER.key+customerName, 30*60);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> quit(String userName) {
		// TODO Auto-generated method stub
		Channel channel = ChanelGroup.userChanelMap.get(userName);
		if(ObjectUtils.isEmpty(channel)) {
			throw new BusinessException("用户未在线");
		}
		
		NettyChannelUtils.removeInfoFromChannel(channel, NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
		ChanelGroup.userChanelMap.put(userName, channel);
		String customerName = SecurityFrameworkUtils.getLoginUser().getUserName();
		MsgDto resMsgDto = new MsgDto();
		resMsgDto.setReceiver(userName);
		resMsgDto.setSender(customerName);
		resMsgDto.setText("客服"+customerName+"已离开");
		resMsgDto.setType(0);
		channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(resMsgDto)));
		redisUtils.hdel(RedisKeyEnums.CUSTOMER_TO_USER+customerName, userName);
		return ApiResp.sucess();
	}

}
