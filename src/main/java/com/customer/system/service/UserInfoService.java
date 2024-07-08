package com.customer.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.customer.entity.system.UserInfoEntity;
import com.customer.resp.ApiResp;
import com.customer.resp.PageResp;
import com.customer.system.req.EditPwdReq;
import com.customer.system.req.ResetPwdReq;
import com.customer.system.req.UserInfoAddReq;
import com.customer.system.req.UserInfoEditReq;
import com.customer.system.req.UserInfoPageReq;

/** 
* @author yangxy
* @version 创建时间：2024年5月30日 下午8:24:36 
*/
public interface UserInfoService extends IService<UserInfoEntity> {
	/**
	 * 分页查询
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:10 
	* @param userInfoPageReq
	* @return
	 */
	public ApiResp<PageResp<UserInfoEntity>> page(UserInfoPageReq userInfoPageReq);

	/**
	 * 新增
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:15 
	* @param userInfoAddReq
	* @return
	 */
	public ApiResp<String> add(UserInfoAddReq userInfoAddReq);

	/**
	 * 修改
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:18 
	* @param userInfoEditReq
	* @return
	 */
	public ApiResp<String> edit(UserInfoEditReq userInfoEditReq);

	/**
	 * 删除
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:23 
	* @param userId
	* @return
	 */
	public ApiResp<String> del(Long userId);
	
	/**
	 * 重置密码
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:26 
	* @param resetPwdReq
	* @return
	 */
	public ApiResp<String> restPwd(ResetPwdReq resetPwdReq);
	
	/**
	 * 修改密码
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:01:30 
	* @param editPwdReq
	* @return
	 */
	public ApiResp<String> editPwd(EditPwdReq editPwdReq);
}
