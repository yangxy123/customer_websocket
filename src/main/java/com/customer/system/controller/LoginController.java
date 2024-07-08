package com.customer.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.resp.ApiResp;
import com.customer.system.req.LoginReq;
import com.customer.system.resp.LoginResp;
import com.customer.system.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年6月1日 上午11:17:37 
*/
@RestController
@Api(tags = "用户登录登出")
@RequestMapping("/system")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	@ApiOperation(value = "登录")
	public ApiResp<LoginResp> login(@RequestBody @Valid LoginReq loginReq){
		return loginService.login(loginReq);
	}
	
	@GetMapping("/logout")
	@ApiOperation(value = "登出")
	public ApiResp<String> logout(HttpServletRequest request){
		return loginService.logout(request);
	}
}
