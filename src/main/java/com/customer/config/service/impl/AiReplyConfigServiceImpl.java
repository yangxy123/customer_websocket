package com.customer.config.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.config.mapper.AiReplyConfigMapper;
import com.customer.config.req.AiReplyConfigAddReq;
import com.customer.config.req.AiReplyConfigEditReq;
import com.customer.config.req.AiReplyConfigPageReq;
import com.customer.config.service.AiReplyConfigService;
import com.customer.contants.AiKeyContants;
import com.customer.contants.LanguageContants;
import com.customer.entity.config.AiReplyConfigEntity;
import com.customer.enums.RedisKeyEnums;
import com.customer.enums.StatusEnums;
import com.customer.exception.BusinessException;
import com.customer.exception.ParamException;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;
import com.customer.util.RedisUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author yangxy
 * @version 创建时间：2024年5月30日 下午8:21:22
 */
@Service
public class AiReplyConfigServiceImpl extends ServiceImpl<AiReplyConfigMapper, AiReplyConfigEntity>
		implements AiReplyConfigService {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public ApiResp<PageResp<AiReplyConfigEntity>> page(AiReplyConfigPageReq aiReplyConfigPageReq) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<AiReplyConfigEntity> lambda = new QueryWrapper<AiReplyConfigEntity>().lambda();
		if (!ObjectUtils.isEmpty(aiReplyConfigPageReq.getStatus())) {
			lambda.eq(AiReplyConfigEntity::getStatus, aiReplyConfigPageReq.getStatus());
		}

		PageHelper.startPage(aiReplyConfigPageReq.getPageNum(), aiReplyConfigPageReq.getPageSize());
		Page<AiReplyConfigEntity> page = (Page<AiReplyConfigEntity>) list(lambda);
		return ApiResp.page(page);
	}

	@Override
	public ApiResp<String> add(AiReplyConfigAddReq languageConfigAddReq) {
		// TODO Auto-generated method stub
		checkContent(languageConfigAddReq.getContent());
		
		List<String> newKeys = Arrays.asList(languageConfigAddReq.getKeyword().split(","));
		
		List<String> existsKyes = newKeys.stream().filter(vo->AiKeyContants.keyMap.containsKey(vo)).collect(Collectors.toList());
		if(!existsKyes.isEmpty()) {
			throw new BusinessException("AI自动回复关键词"+JSON.toJSONString(existsKyes)+"已存在");
		}

		AiReplyConfigEntity addAiReplyConfigEntity = new AiReplyConfigEntity();
		BeanUtils.copyProperties(languageConfigAddReq, addAiReplyConfigEntity);
		save(addAiReplyConfigEntity);
		if (addAiReplyConfigEntity.getStatus() == StatusEnums.ENABLE.status) {
			String[] keys = addAiReplyConfigEntity.getKeyword().split(",");
			for (String key : keys) {
				AiKeyContants.keyMap.put(key, addAiReplyConfigEntity.getId());
				redisUtils.hset(RedisKeyEnums.AI_REPLY_KEY.key, key, addAiReplyConfigEntity.getContent());
			}
		}
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> edit(AiReplyConfigEditReq languageConfigEditReq) {
		// TODO Auto-generated method stub
		checkContent(languageConfigEditReq.getContent());
		
		AiReplyConfigEntity editAiReplyConfigEntity = getById(languageConfigEditReq.getId());
		if(ObjectUtils.isEmpty(editAiReplyConfigEntity)) {
			throw new BusinessException("数据不存在");
		}
		
		List<String> newKeys = Arrays.asList(languageConfigEditReq.getKeyword().split(","));
		List<String> oldKeys = Arrays.asList(editAiReplyConfigEntity.getKeyword().split(","));
		
		List<String> existsKyes = newKeys.stream().filter(vo->AiKeyContants.keyMap.containsKey(vo) && !oldKeys.contains(vo)).collect(Collectors.toList());
		if(!existsKyes.isEmpty()) {
			throw new BusinessException("AI自动回复关键词"+JSON.toJSONString(existsKyes)+"已存在");
		}
		
		BeanUtils.copyProperties(languageConfigEditReq, editAiReplyConfigEntity);
		updateById(editAiReplyConfigEntity);
		
		/**
		 * 清除
		 */
		for (String key : oldKeys) {
			AiKeyContants.keyMap.remove(key);
			redisUtils.hdel(RedisKeyEnums.AI_REPLY_KEY.key, key);
		}
		
		if(editAiReplyConfigEntity.getStatus() == StatusEnums.ENABLE.status) {
			String[] keys = editAiReplyConfigEntity.getKeyword().split(",");
			for (String key : keys) {
				AiKeyContants.keyMap.put(key, editAiReplyConfigEntity.getId());
				redisUtils.hset(RedisKeyEnums.AI_REPLY_KEY.key, key, editAiReplyConfigEntity.getContent());
			}
		}else if(editAiReplyConfigEntity.getStatus() == StatusEnums.UNENABLE.status) {
//			String[] keys = editAiReplyConfigEntity.getKeys().split(",");
//			for (String key : keys) {
//				AiKeyContants.keyMap.remove(key);
//				redisUtils.hdel(RedisKeyEnums.AI_REPLY_KEY.key, key);
//			}
		}else {
			throw new BusinessException("状态错误");
		}
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> del(Long id) {
		// TODO Auto-generated method stub
		AiReplyConfigEntity aiReplyConfigEntity = getById(id);
		removeById(id);
		if(!ObjectUtils.isEmpty(aiReplyConfigEntity)) {
			String[] keys = aiReplyConfigEntity.getKeyword().split(",");
			for (String key : keys) {
				AiKeyContants.keyMap.remove(key);
				redisUtils.hdel(RedisKeyEnums.AI_REPLY_KEY.key, key);
			}
		}
		return ApiResp.sucess();
	}

	@Override
	public ApiResp<String> getContentExample() {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		for(String key : LanguageContants.languageMap.keySet()) {
			jsonObject.put(key.toString(), "");
		}
		return ApiResp.sucess(jsonObject.toString());
	}
	
	@Override
	public ApiResp<String> getContent(String problem,String language) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(problem)) {
			return ApiResp.sucess("问题无效");
		}
		List<String> keyList = AiKeyContants.keyMap.keySet().stream().collect(Collectors.toList());
		// 按字符串长度排序
        Collections.sort(keyList, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        
        String key = "";
        for(int i=keyList.size() - 1;i>=0;i--) {
        	String indexKey = keyList.get(i);
        	if(problem.indexOf(indexKey) >= 0) {
        		key = indexKey;
        		break;
        	}
        }
        
        if(StringUtils.isEmpty(key)) {
        	return ApiResp.sucess("问题无效");
        }
        
        if(redisUtils.hasKey(RedisKeyEnums.AI_REPLY_KEY.key)) {
        	if(!redisUtils.hHasKey(RedisKeyEnums.AI_REPLY_KEY.key, key)) {
        		Long id = AiKeyContants.keyMap.get(key);
        		AiReplyConfigEntity aiReplyConfigEntity = getById(id);
        		redisUtils.hset(RedisKeyEnums.AI_REPLY_KEY.key, key, aiReplyConfigEntity.getContent());
        		String resContent = JSON.parseObject(aiReplyConfigEntity.getContent()).getString(language);
        		return ApiResp.sucess(resContent);
        	}
        }else {
        	List<AiReplyConfigEntity> list = list(new QueryWrapper<AiReplyConfigEntity>().lambda().eq(AiReplyConfigEntity::getStatus, StatusEnums.ENABLE.status));
        	String content = "";
        	for(AiReplyConfigEntity aiReplyConfigEntity : list) {
        		List<String> keys = Arrays.asList(aiReplyConfigEntity.getKeyword().split(","));
        		for(String key1 : keys) {
        			new Thread(()->{
        				redisUtils.hset(RedisKeyEnums.AI_REPLY_KEY.key, key1, aiReplyConfigEntity.getContent());
        			}).start();
        			
        			if(!StringUtils.isEmpty(key) && key.equals(key1)) {
        				content = aiReplyConfigEntity.getContent();
        			}
        		}
        	}
        	String resContent = JSON.parseObject(content).getString(language);
    		return ApiResp.sucess(resContent);
        }
        String content = (String) redisUtils.hget(RedisKeyEnums.AI_REPLY_KEY.key, key);
        String resContent = JSON.parseObject(content).getString(language);
		return ApiResp.sucess(resContent);
	}
	
	/**
	 * AI自动回复内容格式校验
	* @author yangxy
	* @version 创建时间：2024年5月31日 上午10:10:03 
	* @param content
	* @return
	 */
	private boolean checkContent(String content) {
		try {
			JSONObject jsonObject = JSON.parseObject(content);
			
			for(String key : LanguageContants.languageMap.keySet()) {
				String item = jsonObject.getString(key);
				if(StringUtils.isEmpty(item)) {
					throw new ParamException("AI自动回复内容缺少，语言："+key);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ParamException("AI自动回复内容格式错误，应为json格式");
		}
		return true;
	}
	
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
	}
}
