package com.customer.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.customer.entity.system.MemberCustomerChatEntity;
import com.customer.resp.ApiResp;
import com.customer.system.req.ChatPageReq;

import io.swagger.annotations.ApiOperation;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午11:00:19 
*/
public interface MemberCustomerChatService extends IService<MemberCustomerChatEntity> {
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2024年6月24日 下午3:26:51 
	* @param chatPageReq
	* @return
	 */
	public ApiResp<MemberCustomerChatEntity> page(ChatPageReq chatPageReq);
}
