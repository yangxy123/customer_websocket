package com.customer.system.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yangxy
 * @version 创建时间：2024年6月1日 上午10:46:41
 */
@Data
public class UserInfoEditReq {
	@NotNull(message = "id不能为空")
	private Long id;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名",required = true)
	private String userName;

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	@ApiModelProperty(value = "手机号",required = true)
	private String telPhone;

	/**
	 * 真实姓名
	 */
	@NotBlank(message = "真实姓名不能为空")
	@ApiModelProperty(value = "真实姓名",required = true)
	private String realName;

	/**
	 * 状态（0停用，2启用）
	 */
	@NotNull(message = "状态不能为空")
	@ApiModelProperty(value = "状态（0停用，2启用",required = true)
	private Integer status;
}
