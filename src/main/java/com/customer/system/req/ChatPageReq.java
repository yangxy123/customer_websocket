package com.customer.system.req;

import com.customer.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 下午3:24:30 
*/
@Data
public class ChatPageReq extends BasePageReq{
	@ApiModelProperty(value = "会员名称")
	private String memberName;
	
	@ApiModelProperty(value = "客服名称")
	private String customerName;
	
}
