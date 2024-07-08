package com.customer.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2023年7月25日 下午5:10:09 
*/
@Data
public class BaseEntity {
	@ApiModelProperty(value = "创建人")
	@TableField(fill=FieldFill.INSERT)
	private String creator;
	
	@ApiModelProperty(value = "更新人")
	@TableField(fill= FieldFill.INSERT_UPDATE)
	private String updater;
	
	@ApiModelProperty(value = "创建时间")
	@TableField(fill=FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdAt;
	
	@ApiModelProperty(value = "更新时间")
	@TableField(fill= FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatedAt;
}
