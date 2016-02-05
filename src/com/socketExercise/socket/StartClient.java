package com.socketExercise.socket;

/**
 * 启动客户端
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class StartClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.showMainMenu();
    }
}
