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
import com.customer.config.req.AiReplyConfigAddReq;
import com.customer.config.req.AiReplyConfigEditReq;
import com.customer.config.req.AiReplyConfigPageReq;
import com.customer.config.service.AiReplyConfigService;
import com.customer.config.service.LanguageConfigService;
import com.customer.entity.config.AiReplyConfigEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:27:54 
*/
@RestController
@Api(tags = "AI智能回复配置管理")
@RequestMapping("/config/aireply")
public class AiReplyConfigController {
	@Autowired
	private AiReplyConfigService aiReplyConfigService;
	
	@HasLogin
	@GetMapping("/page")
	@ApiOperation(value = "分页查询")
	public ApiResp<PageResp<AiReplyConfigEntity>> page(AiReplyConfigPageReq aiReplyConfigPageReq){
		return aiReplyConfigService.page(aiReplyConfigPageReq);
	}

	@HasLogin
	@PostMapping("/add")
	@ApiOperation(value = "新增")
	public ApiResp<String> add(@RequestBody @Valid AiReplyConfigAddReq languageConfigAddReq){
		return aiReplyConfigService.add(languageConfigAddReq);
	}

	@HasLogin
	@PostMapping("/edit")
	@ApiOperation(value = "修改")
	public ApiResp<String> edit(@RequestBody @Valid AiReplyConfigEditReq languageConfigEditReq){
		return aiReplyConfigService.edit(languageConfigEditReq);
	}
	
	@HasLogin
	@GetMapping("/del/{id}")
	@ApiOperation(value = "删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "id",required = true)
	})
	public ApiResp<String> del(@PathVariable("id") Long id){
		return aiReplyConfigService.del(id);
	}
	
	@HasLogin
	@GetMapping("/getContentExample")
	@ApiOperation(value = "获取AI自动回复内容示例")
	public ApiResp<String> getContentExample(){
		return aiReplyConfigService.getContentExample();
	}
}
