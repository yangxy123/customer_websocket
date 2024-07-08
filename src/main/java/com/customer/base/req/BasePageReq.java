package com.customer.base.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:57:56 
*/
@Data
public class BasePageReq {
	@ApiModelProperty(value = "显示行数（默认10）")
	private int pageSize = 10;
	
	@ApiModelProperty(value = "页码（默认1）")
	private int pageNum = 1;
}
