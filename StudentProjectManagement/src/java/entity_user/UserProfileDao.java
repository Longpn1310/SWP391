/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_user;

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
public class UserProfileDao {
    public User getUserProfile(String email) throws SQLException, ClassNotFoundException {

        User user = new User();

        Connection connection = ConnectionContext.getConnection();

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String GET_USER = "Select * FROM user WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER);
//        ResultSet rs;
        try {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
//                user.setUserId(rs.getInt(1));
                user.setRollNumber(rs.getString(2));
                user.setFullName(rs.getString(3));
                user.setMobile(rs.getString(5));
            }
//        user = preparedStatement.executeUpdate();
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

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
            Logger.getLogger(UserProfileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return null;
    }
    
    public int updateUserProfile(User user) throws SQLException, ClassNotFoundException {
        User updatedUser = new User();

        int result = 0;
        Connection connection = ConnectionContext.getConnection();

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        String UPDATE_USER = "UPDATE user"
                + "  SET roll_number=?, full_name=?, mobile=? "
                + "  WHERE user_id=? ";

        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);

        try {

            preparedStatement.setString(1, user.getRollNumber());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setString(3, user.getMobile());
            preparedStatement.setInt(4, user.getUserId());

            result = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
