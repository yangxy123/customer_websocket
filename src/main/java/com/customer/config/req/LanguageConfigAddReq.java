package com.customer.config.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.customer.entity.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
 * AI智能回答语言配置
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:15:41 
*/
@Data
public class LanguageConfigAddReq{
	@NotBlank(message = "语言不能为空")
	@ApiModelProperty(value = "语言",required = true)
	private String language;

	@NotBlank(message = "对应国家不能为空")
	@ApiModelProperty(value = "对应国家",required = true)
	private String country;

	@NotNull(message = "状态不能为空")
	@ApiModelProperty(value = "状态（0停用，1启用）",required = true)
	private Integer status;
}
