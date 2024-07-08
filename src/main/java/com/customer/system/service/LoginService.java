package com.customer.system.service;

import javax.servlet.http.HttpServletRequest;

import com.customer.resp.ApiResp;
import com.customer.system.req.LoginReq;
import com.customer.system.resp.LoginResp;

/** 
* @author yangxy
* @version 创建时间：2024年6月1日 上午11:22:58 
*/
public interface LoginService {
	/**
	 * 登录
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:23:44 
	* @param loginReq
	* @return
	 */
	public ApiResp<LoginResp> login(LoginReq loginReq);
	
	/**
	 * 登出
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午11:23:47 
	* @return
	 */
	public ApiResp<String> logout(HttpServletRequest request);
}
