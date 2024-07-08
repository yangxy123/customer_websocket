package com.customer.websocket.service;

import com.customer.dto.MsgDto;

/** 
 * websocket操作服务类
* @author yangxy
* @version 创建时间：2024年5月29日 下午12:19:40 
*/
public interface WebSocketService {
	/**
	 * 给指定会员用户发消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userCode 用户编码
	 * @param messageDto 消息
	 * @return
	 */
	public boolean sendMsgToUser(String userId, MsgDto msgDto) ;
	
	/**
	 * 给所有用户发消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userCode 用户编码
	 * @param messageDto 消息
	 * @return
	 */
	public void sendMsgToAllUser(MsgDto msgDto);
	
	/**
	 * 发送财务审核通知消息
	 * 
	 * @author yangxy
	 * @version 创建时间：2023年3月8日 上午10:17:05
	 * @param userCode 用户编码
	 * @param messageDto 消息
	 * @return
	 */
	public void sendFinanceAduit(MsgDto msgDto);
}
