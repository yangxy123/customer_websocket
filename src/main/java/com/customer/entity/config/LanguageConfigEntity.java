package com.customer.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.customer.entity.BaseEntity;

import lombok.Data;

/** 
 * AI智能回答语言配置
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:15:41 
*/
@Data
@TableName("language_config")
public class LanguageConfigEntity extends BaseEntity{
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
	
	/**
	 * 语言
	 */
	private String language;

	/**
	 * 对应国家
	 */
	private String country;

	/**
	 * 状态（0停用，1启用）
	 */
	private Integer status;
}
