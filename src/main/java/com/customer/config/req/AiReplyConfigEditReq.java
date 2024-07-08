package com.customer.config.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年5月31日 上午8:46:10 
*/
@Data
public class AiReplyConfigEditReq {
	@NotNull(message = "id不能为空")
    private Long id;
	
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态（0停用，1启用）",required = true)
	private Integer status;

    @NotBlank(message = "AI自动回复关键词不能为空")
    @ApiModelProperty(value = "AI自动回复关键词（多个关键词用逗号隔开）",required = true)
	private String keyword;

    @NotBlank(message = "AI自动回复内容不能为空")
    @ApiModelProperty(value = "AI自动回复内容",required = true)
	private String content;
}
