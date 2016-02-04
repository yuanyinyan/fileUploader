package com.socketExercise.service;

import com.socketExercise.entity.File;
import com.socketExercise.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yuanyin on 16/2/1.
 */
public class FileService {
    private Connection connection = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    public void save(File file) {
        String sql = "INSERT INTO tb_file(fname,fcontent) VALUES (?,?)";
        connection = DBUtil.getConnection();
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, file.getFname());
            pstmt.setBytes(2, file.getFcontent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(resultSet, pstmt, connection);
        }
    }

}
