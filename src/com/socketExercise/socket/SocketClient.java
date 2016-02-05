package com.socketExercise.socket;

import com.socketExercise.entity.File;
import com.socketExercise.entity.User;
import com.socketExercise.util.CommandTransfer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 显示主菜单
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class SocketClient {
    private Scanner input = new Scanner(System.in);
    private Socket socket = null;

    public void showMainMenu() {
        System.out.println("******欢迎使用文件上传器*******");
        System.out.println("1.登录\n2.注册\n3.退出");
        System.out.println("****************************");
        System.out.println("请选择:");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                showLogin();
                break;
            case 2:
                showRegister();
                break;
            case 3:
                System.out.println("再见,感谢使用!");
                System.exit(0);
                break;
            default:
                System.out.println("输入有误!");
                System.exit(0);
        }
    }

    public void showLogin() {
        User user = new User();
        CommandTransfer transfer = new CommandTransfer();
        int count = 0;
        while (true) {
            count++;
            if (count > 3) {
                System.out.println("您已连续三次登录失败,程序退出!");
                System.exit(0);
            }
            System.out.println("请输入用户名:");
            user.setUsername(input.next());
            System.out.println("请输入密码:");
            user.setPassword(input.next());
            transfer.setCmd("login");
            transfer.setData(user);

            try {
                socket = new Socket("localhost", 8800);
                sendData(transfer);
                transfer = getData();
                System.out.println(transfer.getResult());
                System.out.println("**************************");
                if (transfer.getFlag()) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showUpLoadFile();
    }

    public void showRegister() {
        User user = new User();
        CommandTransfer transfer = new CommandTransfer();
        while (true) {
            System.out.println("请输入用户名:");
            user.setUsername(input.next());
            System.out.println("请输入密码:");
            user.setPassword(input.next());
            System.out.println("请再次确认密码:");
            String rePassword = input.next();
            if (!user.getPassword().equals(rePassword)) {
                System.out.println("两次密码输入不一致!");
                System.out.println("*************************");
            }
            transfer.setCmd("register");
            transfer.setData(user);

            try {
                socket = new Socket("localhost", 8800);
                sendData(transfer);
                transfer = getData();
                System.out.println(transfer.getResult());
                System.out.println("*************************");
                if (transfer.getFlag()) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showLogin();
    }

    public void showUpLoadFile() {
        System.out.println("请输入上传文件的绝对路径(如e:/dog.jpg)");
        String path = input.next();
        File file = null;
        String fname = path.substring(path.lastIndexOf("/") + 1);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            byte[] fcontent = new byte[fis.available()];
            file = new File(fname, fcontent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        CommandTransfer transfer = new CommandTransfer();
        transfer.setCmd("uploadFile");
        transfer.setData(file);
        try {
            socket = new Socket("localhost", 8800);
            sendData(transfer);
            transfer = getData();
            System.out.println(transfer.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(CommandTransfer transfer) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(transfer);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommandTransfer getData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return (CommandTransfer) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
