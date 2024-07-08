package com.customer.api.service;
/** 
* @author yangxy
* @version 创建时间：2024年6月24日 上午9:26:19 
*/

import com.customer.api.req.AllUserMsgReq;
import com.customer.api.req.FinanceMsgReq;
import com.customer.api.req.UserMsgReq;
import com.customer.resp.ApiResp;

public interface MsgApiService {
	/**
	 * 向所有在线会员推送消息
	* @author yangxy
	* @version 创建时间：2024年6月24日 上午9:37:20 
	* @return
	 */
	public ApiResp<String> sendAllUser(AllUserMsgReq allUserMsgReq);
	
	/**
	 * 向指定会员推送消息
	* @author yangxy
	* @version 创建时间：2024年6月24日 上午9:37:36 
	* @return
	 */
	public ApiResp<String> sendToUser(UserMsgReq userMsgReq);
	
	/**
	 * 向财务推送待审核通知
	* @author yangxy
	* @version 创建时间：2024年6月24日 上午9:48:59 
	* @param financeMsgReq
	* @return
	 */
	public ApiResp<String> sendFinanceAduit(FinanceMsgReq financeMsgReq);
}
