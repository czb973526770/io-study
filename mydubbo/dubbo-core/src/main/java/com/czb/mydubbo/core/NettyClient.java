package com.czb.mydubbo.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClient {

    private ChannelFuture channelFuture;

    private StringBuffer resultBuffer = new StringBuffer();


    /**
     * 连接服务器
     *
     * @param port
     * @param host
     * @throws Exception
     */
    public String send(int port, String host,Request request) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group)//
                    .channel(NioSocketChannel.class)//
                    .option(ChannelOption.TCP_NODELAY, true)//
                    .handler(new MyChannelHandler());//
            // 异步链接服务器 同步等待链接成功

            channelFuture = b.connect(host, port).sync();

            System.out.println("sendRequest:"+request.toString());

           // channelFuture.channel().writeAndFlush(request).addListener();

            channelFuture.channel().writeAndFlush(request);

            while(resultBuffer.length() == 0){
                System.out.println("=================");

                Thread.sleep(30);
            }

          //  System.out.println("after sendRequest:"+resultBuffer.toString());

            // 等待链接关闭
            channelFuture.channel().closeFuture().sync();
            return resultBuffer.toString();


        } finally {
            group.shutdownGracefully();
            System.out.println("释放线程...");
        }

    }

    /**
     * 网络事件处理器
     */
    private class MyChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // 添加自定义的编码器和解码器
            // 添加POJO对象解码器 禁止缓存类加载器
            ch.pipeline().addLast(
                    new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this
                            .getClass().getClassLoader())));
            // 设置发送消息编码器
            ch.pipeline().addLast(new ObjectEncoder());
            // 处理网络IO
            ch.pipeline().addLast(new ClientHandler(resultBuffer));// 处理网络IO
        }

    }

}

