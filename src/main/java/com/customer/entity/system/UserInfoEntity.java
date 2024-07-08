package com.customer.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.customer.entity.BaseEntity;

import lombok.Data;

/** 
 * 用户表
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:17:50 
*/
@Data
@TableName("user_info")
public class UserInfoEntity extends BaseEntity {
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
	
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	private String telPhone;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 状态（0停用，2启用）
	 */
	private Integer status;
}
