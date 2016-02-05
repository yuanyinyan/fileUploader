package com.socketExercise.util;

import java.sql.*;

/**
 * 数据库工具类
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class DBUtil {
    public static final String URL = "jdbc:mysql://localhost:3306/fileUploader";
    public static final String USER = "root";
    public static final String PASSWORD = "yywjm999569";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
