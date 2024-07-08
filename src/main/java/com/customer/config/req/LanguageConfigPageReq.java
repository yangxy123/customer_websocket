package com.customer.config.req;

import com.customer.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:33:34 
*/
@Data
public class LanguageConfigPageReq extends BasePageReq{
	@ApiModelProperty(value = "状态（0停用，1启用）")
	private Integer status;
}
