package com.customer.netity;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * netty服务类
 * 
 * @author yangxy
 * @version 创建时间：2024年5月29日 上午10:37:25
 */
@Slf4j
@Component
public class NettyServer {
	@Value("${webSocket.netty.port}")
	private int port;
	@Autowired
	private ProjectInitializer nettyInitializer;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workGroup;

	/**
	 * 启动netty服务
	 * 
	 * @author yangxy
	 * @version 创建时间：2024年5月29日 上午10:40:18
	 */
	@PostConstruct
	public void start() {
		new Thread(() -> {
			// bossGroup辅助客户端的tcp连接请求, workGroup负责与客户端之前的读写操作
			bossGroup = new NioEventLoopGroup();
			workGroup = new NioEventLoopGroup();

			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup);
			// 设置NIO类型的channel
			bootstrap.channel(NioServerSocketChannel.class);
			// 设置监听端口
			bootstrap.localAddress(new InetSocketAddress(port));
			// 设置管道
			bootstrap.childHandler(nettyInitializer);
			// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
			ChannelFuture channelFuture = null;
			try {
				channelFuture = bootstrap.bind().sync();
				log.info("Server started and listen on:{}", channelFuture.channel().localAddress());
				// 对关闭通道进行监听
				channelFuture.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * 释放资源
	 */
	@PreDestroy
	public void destroy() throws InterruptedException {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully().sync();
		}
		if (workGroup != null) {
			workGroup.shutdownGracefully().sync();
		}
	}
}
