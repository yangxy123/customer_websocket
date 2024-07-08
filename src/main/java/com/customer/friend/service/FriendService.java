package com.customer.friend.service;
/** 
* @author yangxy
* @version 创建时间：2024年5月31日 下午7:52:37 
*/

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.customer.resp.ApiResp;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

public interface FriendService {
	/**
	 * 获取当前客服正在服务的会员列表
	* @author yangxy
	* @version 创建时间：2024年5月31日 下午7:53:51 
	* @return
	 */
	public ApiResp<List<String>> getFriends();
	
	/**
	 * 客服进入服务
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午9:47:09 
	* @return
	 */
	public ApiResp<String> intervene(String userName);
	
	/**
	 * 客服退出服务
	* @author yangxy
	* @version 创建时间：2024年6月1日 上午9:50:19 
	* @param userName
	* @return
	 */
	public ApiResp<String> quit(String userName);
	
	/**
	 * 获取等待人工的会员列表
	* @author yangxy
	* @version 创建时间：2024年6月24日 上午10:40:10 
	* @return
	 */
	public ApiResp<List<String>> getWaitFriends();
}
