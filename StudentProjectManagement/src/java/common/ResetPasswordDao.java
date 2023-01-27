/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import dbContext.ConnectionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity_user.User;
import java.util.ArrayList;

/**
 *
 * @author trung
 */
public class ResetPasswordDao {
    
     public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionContext.getConnection();
        try {
            String sql = "SELECT * FROM user where email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("user_id") != null) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setRollNumber(rs.getString("roll_number"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setMobile(rs.getString("mobile"));
                    user.setAvatarLink(rs.getString("avatar_link"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setStatus(rs.getBoolean("status"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return null;
    }
     
     public User getUserById(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionContext.getConnection();
        try {
            String sql = "SELECT * FROM user where user_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("email") != null) {
                    User user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setRollNumber(rs.getString("roll_number"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setMobile(rs.getString("mobile"));
                    user.setAvatarLink(rs.getString("avatar_link"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setStatus(rs.getBoolean("status"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return null;
    }
     
     public boolean checkEmailExist(String email) throws SQLException, ClassNotFoundException  {
        ArrayList<String> emailList = new ArrayList<>();
        Connection connection = ConnectionContext.getConnection();

        try {

            String sql = "SELECT email FROM user "
                    + "WHERE email = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String fetchedEmail = rs.getString(1);
                emailList.add(fetchedEmail);
            }
        }catch (SQLException ex) {
            System.out.println("....");
        }
        if(emailList.size() == 0) {
            return false;
        }else{
            return true;
        }
    }
     
     public int changePassword(int userId, String password) {
        String query = "UPDATE `spm391_bl5`.`user`\n"
                + "SET `password` =? \n"
                + "WHERE `user_id` =?;";
        int row = 0;
        try {
            Connection cnn = (new ConnectionContext()).getConnection();
            PreparedStatement ps = cnn.prepareStatement(query);
            ps.setString(1, password);
            ps.setInt(2, userId);
            row = ps.executeUpdate();
            cnn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return row;
    }
}
