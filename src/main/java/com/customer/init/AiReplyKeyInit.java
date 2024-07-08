package com.customer.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.customer.config.service.AiReplyConfigService;
import com.customer.config.service.LanguageConfigService;
import com.customer.contants.AiKeyContants;
import com.customer.entity.config.AiReplyConfigEntity;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.enums.RedisKeyEnums;
import com.customer.enums.StatusEnums;

/** 
 * AI智能回答关键词初始化
* @author yangxy
* @version 创建时间：2024年6月3日 上午9:48:37 
*/
@Component
public class AiReplyKeyInit implements CommandLineRunner {
	@Autowired
	private AiReplyConfigService aiReplyConfigService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<AiReplyConfigEntity> list = aiReplyConfigService.list(new QueryWrapper<AiReplyConfigEntity>().lambda().eq(AiReplyConfigEntity::getStatus, StatusEnums.ENABLE.status));
		for(AiReplyConfigEntity aiReplyConfigEntity : list) {
			List<String> keys = Arrays.asList(aiReplyConfigEntity.getKeyword().split(","));
			for (String key : keys) {
				AiKeyContants.keyMap.put(key, aiReplyConfigEntity.getId());
			}
		}
	}

}
