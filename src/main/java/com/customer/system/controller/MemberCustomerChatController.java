package com.customer.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.entity.system.MemberCustomerChatEntity;
import com.customer.resp.ApiResp;
import com.customer.system.req.ChatPageReq;
import com.customer.system.service.MemberCustomerChatService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 下午3:22:50 
*/
@RestController
@RequestMapping("/chat")
@Api(tags = "用户客服聊天信息接口")
public class MemberCustomerChatController {
	@Autowired
	private MemberCustomerChatService memberCustomerChatService;
	
	@GetMapping("/page")
	@ApiOperation(value = "分页查询")
	public ApiResp<MemberCustomerChatEntity> page(ChatPageReq chatPageReq){
		return memberCustomerChatService.page(chatPageReq);
	}
}
