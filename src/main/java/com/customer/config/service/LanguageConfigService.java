package com.customer.config.service;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.extension.service.IService;
import com.customer.config.req.LanguageConfigAddReq;
import com.customer.config.req.LanguageConfigEditReq;
import com.customer.config.req.LanguageConfigPageReq;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午7:41:02 
*/
public interface LanguageConfigService extends IService<LanguageConfigEntity>{
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<PageResp<LanguageConfigEntity>> page(LanguageConfigPageReq languageConfigPageReq);
	
	/**
	 * 新增
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> add(LanguageConfigAddReq languageConfigAddReq);
	
	/**
	 * 修改
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> edit(LanguageConfigEditReq languageConfigEditReq);
	
	/**
	 * 删除
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> del(Long id);
}
