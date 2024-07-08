package com.customer.system.req;

import com.customer.base.req.BasePageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年6月1日 上午10:54:27 
*/
@Data
public class UserInfoPageReq extends BasePageReq {
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "状态")
	private Integer status;
}
