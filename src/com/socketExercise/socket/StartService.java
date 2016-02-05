package com.socketExercise.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 启动服务器
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class StartService {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8800);
            Socket socket = null;
            System.out.println("*****服务器即将启动,等待客户端连接*****");
            while (true) {
                socket = serverSocket.accept();
                ServiceThread thread = new ServiceThread(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
