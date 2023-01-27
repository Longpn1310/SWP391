/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import dbContext.ConnectionContext;
import entity_user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trung
 */
public class RegisterDao {

    public int registerUser(User user) throws SQLException, ClassNotFoundException {
        int result = 0;

        Connection connection = ConnectionContext.getConnection();

        String INSERT = "INSERT INTO user "
                + "(full_name, email, password, role_id, mobile) "
                + "VALUES (?,?,?,?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(
                INSERT, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        try {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, 4);
            preparedStatement.setString(5, user.getMobile());

            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return result;
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
}
