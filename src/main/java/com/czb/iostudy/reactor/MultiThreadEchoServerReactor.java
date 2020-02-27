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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：chenzb
 * @date ：2020/2/21 1:26
 * @description：
 */
public class MultiThreadEchoServerReactor {
    ServerSocketChannel serverSocketChannel;
    Selector[] selectors = new Selector[2];
    SubReactor[] subReactors;

    private AtomicInteger next = new AtomicInteger(0);

    public static void main (String[] args) throws IOException {
        MultiThreadEchoServerReactor multiThreadEchoServerReactor = new MultiThreadEchoServerReactor();
        multiThreadEchoServerReactor.startServer();
    }

    public void startServer () {
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    MultiThreadEchoServerReactor () throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();

        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(
                NioDemoConfig.SOCKET_SERVER_IP,
                NioDemoConfig.SOCKET_SERVER_PORT);
        serverSocketChannel.socket().bind(address);
        serverSocketChannel.configureBlocking(false);
        //监听接收状态的通道
        SelectionKey sk = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
        sk.attach(new AcceptorHandler());
        //第一个子反应器，一子反应器负责一个选择器
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        //第二个子反应器，一子反应器负责一个选择器
        SubReactor subReactor2 = new SubReactor(selectors[1]);
        subReactors = new SubReactor[]{subReactor1, subReactor2};

    }


    private void dispatch (SelectionKey sk) {
        Runnable runnable = (Runnable) sk.attachment();
        runnable.run();
    }

    //反应器
    class SubReactor implements Runnable {
        //每条线程负责一个选择器的查询
        final Selector selector;

        public SubReactor (Selector selector) {
            this.selector = selector;
        }

        public void run () {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it = keySet.iterator();
                    while (it.hasNext()) {
                        //Reactor负责dispatch收到的事件
                        SelectionKey sk = it.next();
                        dispatch(sk);
                    }
                    keySet.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    class AcceptorHandler implements Runnable {
        @Override
        public void run () {
            SocketChannel socketChannel = null;
            try {
                socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    //若需要将反应器线程的连接与分派处理器分离，则控制selectors[i]
                    new MultiThreadEchoHandler(socketChannel, selectors[next.get()]);
                }
                if (next.incrementAndGet() == selectors.length) {
                    next.set(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
