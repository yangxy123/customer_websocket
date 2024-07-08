package com.customer.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/** 
 * 创建人、修改人、创建时间、修改时间自动填充
* @author yangxy
* @version 创建时间：2023年7月25日 下午5:06:33 
*/
@Component
public class MybatisHandler implements MetaObjectHandler{

	@Override
	public void insertFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		this.setFieldValByName("createdAt", new Date(), metaObject);
		this.setFieldValByName("updatedAt", new Date(), metaObject);
//		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
//		if(!ObjectUtils.isEmpty(loginUser)) {
//			this.setFieldValByName("creator", loginUser.getUserName(), metaObject);
//		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		this.setFieldValByName("updatedAt", new Date(), metaObject);
//		LoginUserDto loginUser = SecurityFrameworkUtils.getLoginUser();
//		if(!ObjectUtils.isEmpty(loginUser)) {
//			this.setFieldValByName("updater", loginUser.getUserName(), metaObject);
//		}
	}

}
