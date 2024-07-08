package com.customer.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.req.AllUserMsgReq;
import com.customer.api.req.FinanceMsgReq;
import com.customer.api.req.UserMsgReq;
import com.customer.api.service.MsgApiService;
import com.customer.resp.ApiResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午9:25:13 
*/
@RestController
@RequestMapping("/msg")
@Api(tags = "对外接口")
public class MsgApiController {
	@Autowired
	private MsgApiService apiService;
	
	@PostMapping("/sendAllUser")
    @ApiOperation(value = "向所有在线会员推送消息")
	public ApiResp<String> sendAllUser(@RequestBody @Valid AllUserMsgReq allUserMsgReq){
		return apiService.sendAllUser(allUserMsgReq);
	}
	
	@PostMapping("/sendToUser")
    @ApiOperation(value = "向指定会员推送系统消息")
	public ApiResp<String> sendToUser(@RequestBody @Valid UserMsgReq userMsgReq){
		return apiService.sendToUser(userMsgReq);
	}
	
	@PostMapping("/sendFinanceAduit")
    @ApiOperation(value = "向财务推送待审核通知")
	public ApiResp<String> sendFinanceAduit(@RequestBody @Valid FinanceMsgReq financeMsgReq){
		return apiService.sendFinanceAduit(financeMsgReq);
	}
}
