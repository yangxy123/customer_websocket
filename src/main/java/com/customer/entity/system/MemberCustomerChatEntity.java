package com.customer.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.customer.entity.BaseEntity;

import lombok.Data;

/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午10:57:56 
*/
@Data
@TableName("member_customer_chat")
public class MemberCustomerChatEntity extends BaseEntity {
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;
	
	/**
	 * 会员名称
	 */
	private String memberName;
	
	/**
	 * 客服名称
	 */
	private String customerName;
	
	/**
	 * 消息内容
	 */
	private String msgBody;
}
