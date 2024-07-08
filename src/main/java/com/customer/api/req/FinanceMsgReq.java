package com.customer.api.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
 * 消息实体
* @author yangxy
* @version 创建时间：2024年5月29日 上午11:42:47 
*/
@Data
public class FinanceMsgReq {
	@NotBlank(message = "消息内容不能为空")
	@ApiModelProperty(value = "消息内容")
	private String text;
}
