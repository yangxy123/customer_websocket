package com.customer.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.customer.config.req.AiReplyConfigAddReq;
import com.customer.config.req.AiReplyConfigEditReq;
import com.customer.config.req.AiReplyConfigPageReq;
import com.customer.config.req.LanguageConfigAddReq;
import com.customer.config.req.LanguageConfigEditReq;
import com.customer.config.req.LanguageConfigPageReq;
import com.customer.entity.config.AiReplyConfigEntity;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:20:52 
*/
public interface AiReplyConfigService extends IService<AiReplyConfigEntity> {
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<PageResp<AiReplyConfigEntity>> page(AiReplyConfigPageReq aiReplyConfigPageReq);
	
	/**
	 * 新增
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> add(AiReplyConfigAddReq languageConfigAddReq);
	
	/**
	 * 修改
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> edit(AiReplyConfigEditReq languageConfigEditReq);
	
	/**
	 * 删除
	* @author yangxy
	* @version 创建时间：2024年5月30日 下午8:43:03 
	* @param id
	* @return
	 */
	public ApiResp<String> del(Long id);
	
	/**
	 * 获取AI自动回复内容示例
	* @author yangxy
	* @version 创建时间：2024年5月31日 上午9:53:40 
	* @return
	 */
	public ApiResp<String> getContentExample();
	
	/**
	 * 根据用户问题按照语言获取AI智能回复内容
	* @author yangxy
	* @version 创建时间：2024年5月31日 上午10:15:48 
	* @param language 语言
	* @param problem 用户问题
	* @return
	 */
	public ApiResp<String> getContent(String problem,String language);
}
