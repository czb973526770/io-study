package com.czb.iostudy.reactor;

import com.crazymakercircle.util.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author ：chenzb
 * @date ：2020/2/21 3:01
 * @description：
 */
public class EchoHandler implements Runnable {
    private final SocketChannel channel;
    private final SelectionKey sk;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    private static final int RECIEVING = 0, SENDING = 1;
    private int state = RECIEVING;

    public EchoHandler (SocketChannel channel, Selector selector) throws IOException {
        this.channel = channel;
        channel.configureBlocking(false);
        //新连接注册事件
        sk = channel.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        //立即返回选择器
        selector.wakeup();
    }

    @Override
    public void run () {
        try {
            if (state == SENDING) {  //发送状态
                //响应回客户端
                channel.write(byteBuffer);
                byteBuffer.clear();
                sk.interestOps(SelectionKey.OP_READ);
                state = RECIEVING;
            } else if (state == RECIEVING) { //接收状态
                //从通道读
                int length = 0;
                while ((length = channel.read(byteBuffer)) > 0) {
                    Logger.info(new String(byteBuffer.array(), 0, length));
                }
                //读完后，准备开始写入通道,byteBuffer切换成读模式
                byteBuffer.flip();
                //读完后，注册write就绪事件
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后,进入发送的状态
                state = SENDING;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
