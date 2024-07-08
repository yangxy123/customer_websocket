package com.customer.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.baomidou.mybatisplus.extension.service.IService;

/** 
 * 日志记录注解
* @author yangxy
* @version 创建时间：2023年7月25日 下午3:49:00 
*/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperate {
	/**
	 * 操作名称
	* @author yangxy
	* @version 创建时间：2023年7月25日 下午3:50:49 
	* @return
	 */
	String operate();
	
	/**
	 * 操作类型(add 新增，edit 修改，del 删除,other 其他),默认为other
	* @author yangxy
	* @version 创建时间：2023年9月18日 上午10:36:17 
	* @return 
	 */
	String type() default "other";
	
	/**
	 * 
	* @author yangxy
	* @version 创建时间：2023年9月18日 上午11:03:32 
	* @return
	 */
	Class<? extends IService<?>> service() ;
}
