package com.customer.system.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.annotations.HasLogin;
import com.customer.entity.system.UserInfoEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;
import com.customer.system.req.EditPwdReq;
import com.customer.system.req.ResetPwdReq;
import com.customer.system.req.UserInfoAddReq;
import com.customer.system.req.UserInfoEditReq;
import com.customer.system.req.UserInfoPageReq;
import com.customer.system.service.UserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yangxy
 * @version 创建时间：2024年5月30日 下午8:25:45
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/system/user")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;

	@HasLogin
	@GetMapping("/page")
	@ApiOperation(value = "分页查询")
	public ApiResp<PageResp<UserInfoEntity>> page(UserInfoPageReq userInfoPageReq) {
		return userInfoService.page(userInfoPageReq);
	}

//	@HasLogin
	@PostMapping("/add")
	@ApiOperation(value = "新增")
	public ApiResp<String> add(@RequestBody @Valid UserInfoAddReq userInfoAddReq) {
		return userInfoService.add(userInfoAddReq);
	}

	@PostMapping("/edit")
	@ApiOperation(value = "修改")
	public ApiResp<String> edit(@RequestBody @Valid UserInfoEditReq userInfoEditReq) {
		return userInfoService.edit(userInfoEditReq);
	}

	@HasLogin
	@GetMapping("/del/{userId}")
	@ApiOperation(value = "删除")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", required = true) })
	public ApiResp<String> del(@PathVariable("userId") Long userId) {
		return userInfoService.del(userId);
	}

	@HasLogin
	@PostMapping("/restpwd")
	@ApiOperation(value = "重置密码")
	public ApiResp<String> restPwd(@RequestBody @Valid ResetPwdReq resetPwdReq){
		return userInfoService.restPwd(resetPwdReq);
	}
	
	@HasLogin
	@PostMapping("/editpwd")
	@ApiOperation(value = "修改密码")
	public ApiResp<String> editPwd(@RequestBody @Valid EditPwdReq editPwdReq){
		return userInfoService.editPwd(editPwdReq);
	}
}
