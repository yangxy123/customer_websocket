package com.customer.config.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.config.mapper.LanguageConfigMapper;
import com.customer.config.req.LanguageConfigAddReq;
import com.customer.config.req.LanguageConfigEditReq;
import com.customer.config.req.LanguageConfigPageReq;
import com.customer.config.service.LanguageConfigService;
import com.customer.contants.LanguageContants;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.enums.RedisKeyEnums;
import com.customer.enums.StatusEnums;
import com.customer.exception.BusinessException;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;
import com.customer.util.RedisUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.lettuce.core.RedisURI;

/**
 * @author yangxy
 * @version 创建时间：2024年5月30日 下午8:23:00
 */
@Service
public class LanguageConfigServiceImpl extends ServiceImpl<LanguageConfigMapper, LanguageConfigEntity>
		implements LanguageConfigService {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public ApiResp<PageResp<LanguageConfigEntity>> page(LanguageConfigPageReq languageConfigPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<LanguageConfigEntity> lambda = new QueryWrapper<LanguageConfigEntity>().lambda();
		if (!ObjectUtils.isEmpty(languageConfigPageReq.getStatus())) {
			lambda.eq(LanguageConfigEntity::getStatus, languageConfigPageReq.getStatus());
		}

		PageHelper.startPage(languageConfigPageReq.getPageNum(), languageConfigPageReq.getPageSize());
		Page<LanguageConfigEntity> page = (Page<LanguageConfigEntity>) list(lambda);
		return ApiResp.page(page);
	}

	@Override
	public ApiResp<String> add(LanguageConfigAddReq languageConfigAddReq) {
		// TODO Auto-generated method stub

		LanguageConfigEntity languageConfigEntity = getOne(new QueryWrapper<LanguageConfigEntity>().lambda()
				.eq(LanguageConfigEntity::getCountry, languageConfigAddReq.getCountry()));
		if (!ObjectUtils.isEmpty(languageConfigEntity)) {
			throw new BusinessException("当前国家已配置");
		}

		LanguageConfigEntity addLanguageConfigEntity = new LanguageConfigEntity();
		BeanUtils.copyProperties(languageConfigAddReq, addLanguageConfigEntity);
		save(addLanguageConfigEntity);
		
		if(addLanguageConfigEntity.getStatus() == StatusEnums.ENABLE.status) {
			LanguageContants.languageMap.put(addLanguageConfigEntity.getLanguage(), addLanguageConfigEntity.getLanguage());
		}
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> edit(LanguageConfigEditReq languageConfigEditReq) {
		// TODO Auto-generated method stub

		LanguageConfigEntity languageConfigEntity = getOne(new QueryWrapper<LanguageConfigEntity>().lambda()
				.eq(LanguageConfigEntity::getCountry, languageConfigEditReq.getCountry())
				.ne(LanguageConfigEntity::getId, languageConfigEditReq.getId()));
		if (!ObjectUtils.isEmpty(languageConfigEntity)) {
			throw new BusinessException("当前国家已配置");
		}

		LanguageConfigEntity editLanguageConfigEntity = getById(languageConfigEditReq.getId());
		if (ObjectUtils.isEmpty(editLanguageConfigEntity)) {
			throw new BusinessException("数据不存在");
		}
		String oldLanguage = editLanguageConfigEntity.getLanguage();
		BeanUtils.copyProperties(languageConfigEditReq, editLanguageConfigEntity);
		updateById(editLanguageConfigEntity);
		
		if(editLanguageConfigEntity.getStatus() == StatusEnums.UNENABLE.status) {
			LanguageContants.languageMap.remove(oldLanguage);
		}else if(editLanguageConfigEntity.getStatus() == StatusEnums.ENABLE.status){
			LanguageContants.languageMap.put(editLanguageConfigEntity.getLanguage(), editLanguageConfigEntity.getLanguage());
		}else {
			throw new BusinessException("状态错误");
		}
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> del(Long id) {
		// TODO Auto-generated method stub
		LanguageConfigEntity languageConfigEntity = getById(id);
		removeById(id);
		if(!ObjectUtils.isEmpty(languageConfigEntity)) {
			LanguageContants.languageMap.remove(languageConfigEntity.getLanguage());
		}
		return ApiResp.sucess();
	}

}
