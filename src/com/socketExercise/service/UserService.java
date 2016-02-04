package com.socketExercise.service;

import com.socketExercise.entity.User;
import com.socketExercise.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuanyin on 16/2/1.
 */
public class UserService {
    private Connection connection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    public boolean login(User user) {
        String sql = "SELECT * FROM tb_user WHERE username=? AND password=?";
        connection = DBUtil.getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(resultSet, pstmt, connection);
        }
        return false;
    }

    public void register(User user) {
        String sql = "INSERT INTO tb_user(username,password) VALUES (?,?)";
        try {
            connection = DBUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(resultSet, pstmt, connection);
        }
    }
}
