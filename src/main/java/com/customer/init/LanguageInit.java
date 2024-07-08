package com.customer.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.customer.config.service.LanguageConfigService;
import com.customer.contants.LanguageContants;
import com.customer.entity.config.LanguageConfigEntity;
import com.customer.enums.StatusEnums;

/** 
 * 语言配置初始化
* @author yangxy
* @version 创建时间：2024年6月3日 上午9:56:23 
*/
@Component
public class LanguageInit implements CommandLineRunner {
	@Autowired
	private LanguageConfigService languageConfigService;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		List<LanguageConfigEntity> list = languageConfigService.list(new QueryWrapper<LanguageConfigEntity>().lambda().eq(LanguageConfigEntity::getStatus, StatusEnums.ENABLE.status));
		for(LanguageConfigEntity languageConfigEntity : list) {
			LanguageContants.languageMap.put(languageConfigEntity.getLanguage(), languageConfigEntity.getLanguage());
		}
	}

}
