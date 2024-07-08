package com.customer.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.entity.system.MemberCustomerChatEntity;
import com.customer.resp.ApiResp;
import com.customer.system.mapper.MemberCustomerChatMapper;
import com.customer.system.req.ChatPageReq;
import com.customer.system.service.MemberCustomerChatService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午11:00:39 
*/
@Service
public class MemberCustomerChatServiceImpl extends ServiceImpl<MemberCustomerChatMapper, MemberCustomerChatEntity> implements MemberCustomerChatService {

	@Override
	public ApiResp<MemberCustomerChatEntity> page(ChatPageReq chatPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<MemberCustomerChatEntity> lambda = new QueryWrapper<MemberCustomerChatEntity>().lambda();
		
		if(!StringUtils.isEmpty(chatPageReq.getMemberName())) {
			lambda.like(MemberCustomerChatEntity::getMemberName, chatPageReq.getMemberName());
		}
		if(!StringUtils.isEmpty(chatPageReq.getCustomerName())) {
			lambda.like(MemberCustomerChatEntity::getCustomerName, chatPageReq.getCustomerName());
		}
		lambda.orderByDesc(MemberCustomerChatEntity::getCreatedAt);
		PageHelper.startPage(chatPageReq.getPageNum(), chatPageReq.getPageSize());
		Page<MemberCustomerChatEntity> page = (Page<MemberCustomerChatEntity>) list(lambda);
		return ApiResp.page(page);
	}

}
