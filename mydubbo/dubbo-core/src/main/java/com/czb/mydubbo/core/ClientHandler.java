package com.czb.mydubbo.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

//用于读取服务端发来的信息
public class ClientHandler extends ChannelInboundHandlerAdapter {
    public StringBuffer resultMessage;

    ClientHandler(StringBuffer sb){
        resultMessage = sb;
    }

    // 客户端与服务端，连接成功
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送消息
    }

    // 只是读数据，没有写数据的话
    // 需要自己手动的释放的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        try {
            Response response = (Response) msg;
            resultMessage.append(response.body);
            System.out.println("客户端收到服务端的返回:"+response.body);

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

}

