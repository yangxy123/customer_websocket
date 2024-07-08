package com.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
 * 消息实体
* @author yangxy
* @version 创建时间：2024年5月29日 上午11:42:47 
*/
@Data
public class MsgDto {
	/**
	 * 消息类型(0客服消息，1普通消息，2转人工)
	 */
	@ApiModelProperty(value = "消息类型(0客服消息，1普通消息，2转人工，3系统错误提示消息,4系统通知,5财务待审通知)")
	private Integer type;
	
	/**
	 * 消息内容类型类型(0文本，1图片)
	 */
	@ApiModelProperty(value = "消息内容类型类型(0文本，1图片)")
	private Integer textType;
	
	@ApiModelProperty(value = "消息内容")
	private String text;
	
	@ApiModelProperty(value = "消息发送人标识")
	private String sender;
	
	@ApiModelProperty(value = "消息接收人标识")
	private String receiver;
}
