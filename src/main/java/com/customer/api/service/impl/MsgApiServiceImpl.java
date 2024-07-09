package com.customer.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.api.req.AllUserMsgReq;
import com.customer.api.req.FinanceMsgReq;
import com.customer.api.req.UserMsgReq;
import com.customer.api.service.MsgApiService;
import com.customer.dto.MsgDto;
import com.customer.exception.ParamException;
import com.customer.netity.WebSocketHandler;
import com.customer.resp.ApiResp;

import lombok.extern.slf4j.Slf4j;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午9:27:05 
*/
@Slf4j
@Service
public class MsgApiServiceImpl implements MsgApiService {
	@Autowired
	private WebSocketHandler webSocketHandler;

	@Override
	public ApiResp<String> sendAllUser(AllUserMsgReq allUserMsgReq) {
		// TODO Auto-generated method stub
		if(allUserMsgReq.getType() != 3 && allUserMsgReq.getType() != 4) {
			throw new ParamException("消息类型错误");
		}
		
		if(allUserMsgReq.getTextType() != 0 && allUserMsgReq.getTextType() != 1) {
			throw new ParamException("消息内容类型类型错误");
		}
		MsgDto msgDto = new MsgDto();
		BeanUtils.copyProperties(allUserMsgReq, msgDto);
		webSocketHandler.sendMsgToAllUser(msgDto);
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> sendToUser(UserMsgReq userMsgReq) {
		// TODO Auto-generated method stub
		log.info("====>2");
		if(userMsgReq.getType() != 3 && userMsgReq.getType() != 4) {
			throw new ParamException("消息类型错误");
		}
		
		if(userMsgReq.getTextType() != 0 && userMsgReq.getTextType() != 1) {
			throw new ParamException("消息内容类型类型错误");
		}
		log.info("====>1");
		MsgDto msgDto = new MsgDto();
		BeanUtils.copyProperties(userMsgReq, msgDto);
		webSocketHandler.sendMsgToUser(userMsgReq.getReceiver(), msgDto,userMsgReq.getIsClose());
		log.info("====>3");
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> sendFinanceAduit(FinanceMsgReq financeMsgReq) {
		// TODO Auto-generated method stub
		MsgDto msgDto = new MsgDto();
		BeanUtils.copyProperties(financeMsgReq, msgDto);
		msgDto.setTextType(0);
		msgDto.setType(5);
		webSocketHandler.sendFinanceAduit(msgDto);
		return ApiResp.sucess();
	}

}
