package com.customer.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.dto.MsgDto;
import com.customer.netity.WebSocketHandler;
import com.customer.websocket.service.WebSocketService;

/** 
* @author yangxy
* @version 创建时间：2024年5月29日 下午12:22:05 
*/
@Service
public class WebSocketServiceImpl implements WebSocketService {
	@Autowired
	private WebSocketHandler webSocketHandler;
	
	@Override
	public boolean sendMsgToUser(String userId, MsgDto msgDto) {
		// TODO Auto-generated method stub
		return webSocketHandler.sendMsgToUser(userId, msgDto);
	}

	@Override
	public void sendMsgToAllUser(MsgDto msgDto) {
		// TODO Auto-generated method stub
		webSocketHandler.sendMsgToAllUser(msgDto);
	}

	@Override
	public void sendFinanceAduit(MsgDto msgDto) {
		// TODO Auto-generated method stub
		webSocketHandler.sendFinanceAduit(msgDto);
	}

}
