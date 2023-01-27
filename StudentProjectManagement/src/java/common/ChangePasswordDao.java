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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity_user.User;

/**
 *
 * @author trung
 */
public class ChangePasswordDao {
    public static int changePassword(String email, String newPassword) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String UPDATE_PASSWORD = "UPDATE user"
                + "  SET password=? "
                + "  WHERE email=? ";

        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);

        try {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);

            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
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
}
