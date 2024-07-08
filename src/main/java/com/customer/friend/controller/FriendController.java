package com.customer.friend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.annotations.HasLogin;
import com.customer.friend.service.FriendService;
import com.customer.resp.ApiResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yangxy
 * @version 创建时间：2024年5月31日 下午7:50:47
 */
@RestController
@Api(tags = "客服操作接口")
@RequestMapping("/customer")
public class FriendController {
	@Autowired
	private FriendService friendService;

	@HasLogin
	@GetMapping("/friends")
	@ApiOperation(value = "获取当前客服正在服务的会员列表")
	public ApiResp<List<String>> getFriends() {
		return friendService.getFriends();
	}
	
	@HasLogin
	@GetMapping("/getWaitFriends")
	@ApiOperation(value = "获取等待人工的会员列表")
	public ApiResp<List<String>> getWaitFriends(){
		return friendService.getWaitFriends();
	}

	@HasLogin
	@GetMapping("/intervene/{userName}")
	@ApiOperation(value = "客服进入服务")
	@ApiImplicitParams(@ApiImplicitParam(name = "userName",value = "会员账号", required = true))
	public ApiResp<String> intervene(@PathVariable("userName") String userName) {
		return friendService.intervene(userName);
	}

	@HasLogin
	@GetMapping("/quit/{userName}")
	@ApiOperation(value = "客服退出服务")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userName",value = "会员账号",required = true)
	})
	public ApiResp<String> quit(@PathVariable("userName") String userName){
		return friendService.quit(userName);
	}
}
