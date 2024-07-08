package com.customer.api.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
 * 消息实体
* @author yangxy
* @version 创建时间：2024年5月29日 上午11:42:47 
*/
@Data
public class AllUserMsgReq {
	/**
	 * 消息类型(0客服消息，1普通消息，2转人工)
	 */
	@NotNull(message = "消息类型不能为空")
	@ApiModelProperty(value = "消息类型(3系统错误提示消息,4系统通知)")
	private Integer type;
	
	/**
	 * 消息内容类型类型(0文本，1图片)
	 */
	@NotNull(message = "消息内容类型类型不能为空")
	@ApiModelProperty(value = "消息内容类型类型(0文本，1图片)")
	private Integer textType;
	
	@NotBlank(message = "消息内容不能为空")
	@ApiModelProperty(value = "消息内容")
	private String text;
}
