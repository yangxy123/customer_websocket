package com.customer.netity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.customer.config.service.AiReplyConfigService;
import com.customer.contants.ResCodeContants;
import com.customer.dto.MsgDto;
import com.customer.entity.system.MemberCustomerChatEntity;
import com.customer.enums.NettyChannelInfoEnums;
import com.customer.enums.NettyUserTypeEnums;
import com.customer.enums.RedisKeyEnums;
import com.customer.resp.ApiResp;
import com.customer.system.service.MemberCustomerChatService;
import com.customer.util.MsgUtil;
import com.customer.util.NettyChannelUtils;
import com.customer.util.RedisUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * netty 消息处理器 连接示例socket = new
 * WebSocket('ws://localhost:8889/webSocket','123_1')
 * 
 * @author yangxy
 * @version 创建时间：2024年5月29日 上午10:52:14
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private AiReplyConfigService aiReplyConfigService;
	@Autowired
	private MemberCustomerChatService memberCustomerChatService;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		boolean linkFlag = false;
		if (msg instanceof FullHttpRequest) {
			// 获取请求头中的身份验证令牌
			FullHttpRequest request = (FullHttpRequest) msg;
			String uri = request.uri();
			Map paramMap = getUrlParams(uri);
			// 如果url包含参数，需要处理
			if (uri.contains("/websocket?")) {
				String newUri = uri.substring(0, uri.indexOf("?"));
				request.setUri(newUri);
				String token = (String) paramMap.get("token");
				String[] args = token.split("&");
				// 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
				String userName = args[0];
				NettyChannelUtils.setInfoToChannel(ctx.channel(), NettyChannelInfoEnums.CHANNEL_USER_NAME.key, userName);
				// 将用户类型作为自定义属性加入到channel中，方便随时channel中获取用户ID
				String userType = args[1];
				NettyChannelUtils.setInfoToChannel(ctx.channel(), NettyChannelInfoEnums.CHANNEL_USERTYPE.key,userType);
				// 将用户语言作为自定义属性加入到channel中，方便随时channel中获取用户ID
				String language = args[2];
				NettyChannelUtils.setInfoToChannel(ctx.channel(), NettyChannelInfoEnums.CHANNEL_LANGUAGE.key, language);
				if (NettyUserTypeEnums.REGULAR_USER.userType.equals(userType)) {// 用户
					if (!ChanelGroup.userChanelMap.containsKey(userName)) {
						ChanelGroup.userChanelMap.put(userName, ctx.channel());
					}
				} else if (NettyUserTypeEnums.FINANCE_USER.userType.equals(userType)) {// 财务
					if (!ChanelGroup.financeChanelMap.containsKey(userName)) {
						ChanelGroup.financeChanelMap.put(userName, ctx.channel());
					}
				} else if (NettyUserTypeEnums.CUSTOMER_USER.userType.equals(userType)) {// 客服
					if (!ChanelGroup.customerChanelMap.containsKey(userName)) {
						ChanelGroup.customerChanelMap.put(userName, ctx.channel());
					}
				}else {
					super.channelRead(ctx, msg);
					ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgUtil.errorPromptMsg("用户类型错误"))));
					ctx.channel().close();
					return;
				}
				linkFlag = true;
			}
		}else if (msg instanceof TextWebSocketFrame) {
			// 正常的TEXT消息类型
			TextWebSocketFrame frame = (TextWebSocketFrame) msg;
//            log.info("客户端收到服务器数据：{}------当前通道数量----{}",frame.text(),NettyChannelHandlerPool.channelGroup.size());
		}
		super.channelRead(ctx, msg);
		if (linkFlag) {
			ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(ApiResp.sucess("连接成功"))));
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		// TODO Auto-generated method stub
		MsgDto msgDto = JSON.toJavaObject(JSON.parseObject(msg.text()), MsgDto.class);
		if (ObjectUtils.isEmpty(msgDto.getType())) {
			log.error("消息发送失败,消息类型不能为空");
			ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgUtil.errorPromptMsg("消息发送失败,消息类型不能为空"))));
			return;
		}
		
		if(msgDto.getType() == -1) {
			return;
		}else if (msgDto.getType() == 0) {// 客服消息
			String customerName = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
					NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
			if (StringUtils.isEmpty(customerName)) {// AI客服智能回答
				String language = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
						NettyChannelInfoEnums.CHANNEL_LANGUAGE.key);
				ApiResp<String> apiResp = aiReplyConfigService.getContent(msgDto.getText(), language);

				if (ResCodeContants.SUCESS.equals(apiResp.getResCode())) {
					MsgDto resMsgDto = new MsgDto();
					resMsgDto.setReceiver(msgDto.getReceiver());
					resMsgDto.setSender("system");
					resMsgDto.setText(apiResp.getResultSet());
					resMsgDto.setType(0);
					ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(resMsgDto)));
					
					String infoFromChannel = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
							NettyChannelInfoEnums.CHANNEL_USER_NAME.key);
					MemberCustomerChatEntity memberCustomerChatEntity = new MemberCustomerChatEntity();
					memberCustomerChatEntity.setMemberName(infoFromChannel);
					memberCustomerChatEntity.setMsgBody(JSON.toJSONString(msgDto));
					memberCustomerChatService.save(memberCustomerChatEntity);
					return;
				}
			}
			
			String userType = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
					NettyChannelInfoEnums.CHANNEL_USERTYPE.key);
			MemberCustomerChatEntity memberCustomerChatEntity = new MemberCustomerChatEntity();
			
			if(userType.equals(NettyUserTypeEnums.REGULAR_USER.userType)) {
				Channel customerChannel = ChanelGroup.customerChanelMap.get(customerName);
				if (!ObjectUtils.isEmpty(customerChannel)) {
					customerChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
					return;
				}
				memberCustomerChatEntity.setCustomerName(customerName);
			}else if(userType.equals(NettyUserTypeEnums.CUSTOMER_USER.userType)) {
				if(StringUtils.isEmpty(msgDto.getReceiver())) {
					ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgUtil.errorPromptMsg("消息接收人不能为空"))));
					return;
				}
				Channel userChannel = ChanelGroup.userChanelMap.get(msgDto.getReceiver());
				if (!ObjectUtils.isEmpty(userChannel)) {
					userChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
					return;
				}
				memberCustomerChatEntity.setMemberName(msgDto.getReceiver());
			}
			memberCustomerChatEntity.setMsgBody(JSON.toJSONString(msgDto));
			memberCustomerChatService.save(memberCustomerChatEntity);
			log.error("{}未在线", msgDto.getReceiver());
		} else if (msgDto.getType() == 1) {// 普通消息
			if (msgDto.getType() == 1) {
				if (StringUtils.isEmpty(msgDto.getReceiver())) {
					log.error("消息发送失败,消息接收人不能为空");
					ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgUtil.errorPromptMsg("消息发送失败,消息接收人不能为空"))));
					return;
				}
			}
			
			Channel channel = ChanelGroup.userChanelMap.get(msgDto.getReceiver());
			if (ObjectUtils.isEmpty(channel)) {
				log.error("{}未在线", msgDto.getReceiver());
				return;
			}
			channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
		} else if (msgDto.getType() == 2) {// 2转人工
			msgDto.setText("用户" + msgDto.getSender() + "需要客服支持");
			for (String key : ChanelGroup.customerChanelMap.keySet()) {
				new Thread(() -> {
					ChanelGroup.customerChanelMap.get(key)
							.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
				}).start();
			}

		} else if (msgDto.getType() == 3) {//3系统错误提示消息
			if (StringUtils.isEmpty(msgDto.getReceiver())) {
				log.error("消息发送失败,消息接收人不能为空");
				return;
			}
			Channel userChannel = ChanelGroup.userChanelMap.get(msgDto.getReceiver());
			if (!ObjectUtils.isEmpty(userChannel)) {
				userChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
				return;
			}
		} else {
			log.error("消息发送失败,消息类型错误");
			ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(MsgUtil.errorPromptMsg("消息发送失败,消息类型错误"))));
			return;
		}
	}

	/**
	 * 发生异常触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		String userId = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
				NettyChannelInfoEnums.CHANNEL_USER_NAME.key);
		ChanelGroup.userChanelMap.remove(userId);
		ChanelGroup.financeChanelMap.remove(userId);
		ChanelGroup.customerChanelMap.remove(userId);
		ctx.close();
	}

	/**
	 * 关闭连接时触发
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		String userType = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
				NettyChannelInfoEnums.CHANNEL_USERTYPE.key);
		String userName = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
				NettyChannelInfoEnums.CHANNEL_USER_NAME.key);
		log.info("用户{}断开链接，用户类型{}",userName,userType);
		if (NettyUserTypeEnums.REGULAR_USER.userType.equals(userType)) {// 用户
			ChanelGroup.userChanelMap.remove(userName);
			String customerName = NettyChannelUtils.getInfoFromChannel(ctx.channel(),
					NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
			redisUtils.hdel(RedisKeyEnums.CUSTOMER_TO_USER + customerName, userName);
		} else if (NettyUserTypeEnums.FINANCE_USER.userType.equals(userType)) {// 财务
			ChanelGroup.financeChanelMap.remove(userName);
		} else if (NettyUserTypeEnums.CUSTOMER_USER.userType.equals(userType)) {// 客服
			ChanelGroup.customerChanelMap.remove(userName);
			Map<Object, Object> hmget = redisUtils.hmget(RedisKeyEnums.CUSTOMER_TO_USER.key + userName);
			// 清除客服对应连接用户绑定的客服
			if (!ObjectUtils.isEmpty(hmget)) {
				for (Object key : hmget.keySet()) {
					Channel channel = ChanelGroup.userChanelMap.get(key.toString());
					if (!ObjectUtils.isEmpty(channel)) {
						NettyChannelUtils.removeInfoFromChannel(channel,
								NettyChannelInfoEnums.CHANNEL_CUSTOMER_NAME.key);
						ChanelGroup.userChanelMap.put(key.toString(), channel);
					}
				}
			}
			redisUtils.del(RedisKeyEnums.CUSTOMER_TO_USER.key + userName);
		}
	}

	/**
	 * 给指定会员用户发消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userName 用户名
	 * @param msgDto   消息
	 * @return
	 */
	public boolean sendMsgToUser(String userName, MsgDto msgDto) {
		if (ChanelGroup.userChanelMap.containsKey(userName)) {
			ChanelGroup.userChanelMap.get(userName).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
			return true;
		}
		log.error("会员{}未在线", userName);
		return false;
	}
	
	/**
	 * 给指定会员用户发消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userName 用户名
	 * @param msgDto   消息
	 * @param isClose   是否断开会员websocket链接
	 * @return
	 */
	public boolean sendMsgToUser(String userName, MsgDto msgDto,Boolean isClose) {

		if (ChanelGroup.userChanelMap.containsKey(userName)) {
			ChanelGroup.userChanelMap.get(userName).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
			
			if(isClose) {
				ChanelGroup.userChanelMap.get(userName).close();
			}
			return true;
		}
		log.error("会员{}未在线", userName);
		return false;
	}

	/**
	 * 给所有用户发消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userCode   用户编码
	 * @param messageDto 消息
	 * @return
	 */
	public void sendMsgToAllUser(MsgDto msgDto) {
		for (String key : ChanelGroup.userChanelMap.keySet()) {
			new Thread(() -> {
				ChanelGroup.userChanelMap.get(key).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
			}).start();
		}
	}

	/**
	 * 发送财务审核通知消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userCode   用户编码
	 * @param messageDto 消息
	 * @return
	 */
	public void sendFinanceAduit(MsgDto msgDto) {
		for (String key : ChanelGroup.financeChanelMap.keySet()) {
			new Thread(() -> {
				ChanelGroup.financeChanelMap.get(key).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgDto)));
			}).start();
		}
	}
	
	private static Map<String,String> getUrlParams(String url) {
		Map<String, String> map = new HashMap<>();
		url = url.replace("?", ";");
		if (!url.contains(";")) {
			return map;
		}
		if (url.split(";").length > 0) {//token=123&1&2
			String string = url.split(";")[1];
			String key = string.split("=")[0];
			String value = string.split("=")[1];
			map.put(key, value);
			return map;

		} else {
			return map;
		}
	}
}
