package com.customer.entity.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.customer.entity.BaseEntity;

import lombok.Data;

/** 
 * AI紫东路回复配置
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:11:34 
*/
@Data
@TableName("ai_reply_config")
public class AiReplyConfigEntity extends BaseEntity {
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
	
	/**
	 * 状态（0停用，1启用）
	 */
	private Integer status;
	
	/**
	 * AI自动回复关键词（多个关键词用逗号隔开）
	 */
	private String keyword;
	
	/**
	 * AI自动回复内容
	 */
	private String content;
}
