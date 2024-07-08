package com.customer.config.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.annotations.HasLogin;
import com.customer.config.req.LanguageConfigAddReq;
import com.customer.config.req.LanguageConfigEditReq;
import com.customer.config.req.LanguageConfigPageReq;
import com.customer.config.service.LanguageConfigService;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午7:35:35 
*/
@RestController
@Api(tags = "AI智能回答语言配置管理")
@RequestMapping("/config/language")
public class LanguageConfigController {
	@Autowired
	private LanguageConfigService languageConfigService;

	@HasLogin
	@GetMapping("/page")
	@ApiOperation(value = "分页查询")
	public ApiResp<PageResp<LanguageConfigEntity>> page(LanguageConfigPageReq languageConfigPageReq){
		return languageConfigService.page(languageConfigPageReq);
	}

	@HasLogin
	@PostMapping("/add")
	@ApiOperation(value = "新增")
	public ApiResp<String> add(@RequestBody @Valid LanguageConfigAddReq languageConfigAddReq){
		return languageConfigService.add(languageConfigAddReq);
	}

	@HasLogin
	@PostMapping("/edit")
	@ApiOperation(value = "修改")
	public ApiResp<String> edit(@RequestBody @Valid LanguageConfigEditReq languageConfigEditReq){
		return languageConfigService.edit(languageConfigEditReq);
	}

	@HasLogin
	@GetMapping("/del/{id}")
	@ApiOperation(value = "删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "id",required = true)
	})
	public ApiResp<String> del(@PathVariable("id") Long id){
		return languageConfigService.del(id);
	}
}
