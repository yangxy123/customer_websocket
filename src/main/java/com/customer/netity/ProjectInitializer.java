package com.customer.netity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/** 
 * netty管道初始化
* @author yangxy
* @version 创建时间：2024年5月29日 上午10:46:08 
*/
@Component
public class ProjectInitializer extends ChannelInitializer<SocketChannel> {
	/**
     * webSocket协议名
     */
    private static final String WEBSOCKET_PROTOCOL = "WebSocket";
    
    @Autowired
    private WebSocketHandler webSocketHandler;
    
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        ch.pipeline().addLast("http-decoder", new HttpServerCodec());
        // 加入ObjectAggregator解码器，作用是他会把多个消息转换为单一的FullHttpRequest或者FullHttpResponse
        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
        // 加入chunked 主要作用是支持异步发送的码流（大文件传输），但不专用过多的内存，防止java内存溢出
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast(webSocketHandler);
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket", "WebSocket", true, 65536 * 10));
	}

}
