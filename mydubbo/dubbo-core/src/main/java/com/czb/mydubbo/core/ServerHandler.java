package com.czb.mydubbo.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 用于获取客户端发送的信息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // 用于获取客户端发来的数据信息
        Request request = (Request) msg;
        System.out.println("Server接受的客户端的信息 :" + request.toString());

        Object bean = ServiceContainer.get(request.getClassName());

        Method method = bean.getClass().getDeclaredMethod(request.getMethodName(), request.getTypes());
        Object object = method.invoke(bean,request.getParams());

        Response response = new Response((String) object);

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        // cause.printStackTrace();
        ctx.close();
    }
}
