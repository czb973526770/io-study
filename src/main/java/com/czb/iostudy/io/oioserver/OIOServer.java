package com.czb.iostudy.io.oioserver;

import com.czb.iostudy.NioDemoConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ：chenzb
 * @date ：2020/1/20 2:02
 * @description：
 */
public class OIOServer {
    static class Handler implements Runnable {
        final Socket socket;

        Handler (Socket s) {
            socket = s;
        }

        public void run () {
            try {
                while (true){
                    byte[] input = new byte[NioDemoConfig.SERVER_BUFFER_SIZE];
                    /* 读取数据 */
                    int read = socket.getInputStream().read(input);
                    /* 处理业务逻辑，获取处理结果 */
                    byte[] output = "收到数据".getBytes();
                    /* 写入结果 */
                    socket.getOutputStream().write(output);
                }
            } catch (IOException ex) {
                /*处理异常*/
                ex.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(NioDemoConfig.SOCKET_SERVER_PORT);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                //创建新线程来handle
                //或者，使用线程池来处理
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}