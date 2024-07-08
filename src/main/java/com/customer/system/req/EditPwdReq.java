package com.customer.system.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年6月1日 上午10:50:27 
*/
@Data
public class EditPwdReq {
	@NotNull(message = "用户ID不能为空")
	@ApiModelProperty(value = "用户ID",required = true)
	private Long userId;
	
	@NotBlank(message = "新密码不能为空")
	@ApiModelProperty(value = "新密码",required = true)
	private String newPwd;

	@NotBlank(message = "旧密码不能为空")
	@ApiModelProperty(value = "旧密码",required = true)
	private String oldPwd;
}
