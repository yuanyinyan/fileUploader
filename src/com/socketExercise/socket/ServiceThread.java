package com.socketExercise.socket;

import com.socketExercise.entity.File;
import com.socketExercise.entity.User;
import com.socketExercise.service.FileService;
import com.socketExercise.service.UserService;
import com.socketExercise.util.CommandTransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 服务器端线程处理类
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class ServiceThread extends Thread {
    Socket socket = null;
    UserService userService = new UserService();
    FileService fileService = new FileService();

    public ServiceThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            CommandTransfer transfer = (CommandTransfer) ois.readObject();
            transfer = execute(transfer);

            oos.writeObject(transfer);
            oos.flush();
            ois.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public CommandTransfer execute(CommandTransfer transfer) {
        String cmd = transfer.getCmd();
        if (cmd.equals("login")) {
            User user = (User) transfer.getData();
            boolean isCorrect = userService.login(user);
            transfer.setFlag(isCorrect);
            if (isCorrect) {
                transfer.setResult("登录成功!");
            } else {
                transfer.setResult("用户名或密码不正确,请重新登录!");
            }
        } else if (cmd.equals("register")) {
            User user = (User) transfer.getData();
            boolean isFlag = userService.register(user);
            transfer.setFlag(isFlag);
            if (isFlag) {
                transfer.setResult("注册成功!请登录");
            } else {
                transfer.setResult("注册失败!");
            }
        } else if (cmd.equals("uploadFile")) {
            File file = (File) transfer.getData();
            boolean isFlag = fileService.save(file);
            transfer.setFlag(isFlag);
            if (isFlag) {
                transfer.setResult("文件上传成功!");
            } else {
                transfer.setResult("文件上传失败!");
            }
        }
        return transfer;
    }
}
