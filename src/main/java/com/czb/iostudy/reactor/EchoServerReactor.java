package com.czb.iostudy.reactor;

import com.czb.iostudy.NioDemoConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ：chenzb
 * @date ：2020/2/21 1:26
 * @description：
 */
public class EchoServerReactor implements Runnable {
    Selector selector;
    ServerSocketChannel serverSocketChannel;

    public static void main (String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
    EchoServerReactor () throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(
                NioDemoConfig.SOCKET_SERVER_IP,
                NioDemoConfig.SOCKET_SERVER_PORT);
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        //监听接收状态的通道
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new AcceptorHandler());
    }

    @Override
    public void run () {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (SelectionKey sk : keySet) {
//                  Reactor负责 调度 收到的事件
                    dispatch(sk);
                }
                keySet.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void dispatch (SelectionKey sk) {
        Runnable runnable = (Runnable) sk.attachment();
        runnable.run();
    }

    class AcceptorHandler implements Runnable {
        @Override
        public void run () {
            SocketChannel socketChannel = null;
            try {
                socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new EchoHandler(socketChannel, selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
