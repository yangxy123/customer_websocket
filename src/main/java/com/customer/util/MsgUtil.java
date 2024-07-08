package com.customer.util;
/** 
 * websocket 消息工具类
* @author yangxy
* @version 创建时间：2024年6月3日 上午8:47:03 
*/

import com.customer.dto.MsgDto;
import com.customer.enums.NettyMsgTextTypeEnums;
import com.customer.enums.NettyMsgTypeEnums;

public class MsgUtil {
	
	/**
	 * 创建系统错误提示消息
	* @author yangxy
	* @version 创建时间：2024年6月3日 上午8:56:02 
	* @param text 消息内容
	* @return
	 */
	public static MsgDto errorPromptMsg(String text) {
		MsgDto msgDto = new MsgDto();
		msgDto.setType(NettyMsgTypeEnums.ERROR_PROMPT_MSG.type);
		msgDto.setTextType(NettyMsgTextTypeEnums.TEXT.textType);
		msgDto.setText(text);
		msgDto.setSender("system");
		return msgDto;
	}
	
//	/**
//	 * 
//	* @author yangxy
//	* @version 创建时间：2024年6月3日 上午9:00:05 
//	* @param type
//	* @param text
//	* @param sender
//	* @param receiver
//	* @return
//	 */
//	public static MsgDto customerMsg(int type,String text,String sender,String receiver) {
//		
//	}
}
