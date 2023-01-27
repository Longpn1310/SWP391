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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UserDao {

    public UserDao() {
    }

    public List<User> getAllUser() throws SQLException, ClassNotFoundException {

        List<User> list = new ArrayList<User>();
        Connection connection = ConnectionContext.getConnection();

        // Tạo đối tượng Statement chuyên để đọc dữ liệu.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
        ResultSet rs = statement.executeQuery("SELECT * FROM user");

        // Duyệt trên kết quả trả về.
        while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
            User user = new User();
            user.setUserId(rs.getInt(1));
            user.setRollNumber(rs.getString(2));
            user.setFullName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setMobile(rs.getString(5));
            user.setPassword(rs.getString(6));
            user.setAvatarLink(rs.getString(7));
            user.setRoleId(rs.getInt(8));
            user.setStatus(rs.getBoolean(9));
            user.setNote(rs.getString(10));
            list.add(user);
        }
        connection.close();
        return list;
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
                    user.setMobile(rs.getString("mobile"));
                    user.setPassword(rs.getString("password"));
                    user.setAvatarLink(rs.getString("avatar_link"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setStatus(rs.getBoolean("status"));
                    user.setNote(rs.getString("note"));
                    return user;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        return null;
    }
    public User getUserByID(String id) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionContext.getConnection();
        try {
            String sql = "SELECT * FROM user where user_id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
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
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
