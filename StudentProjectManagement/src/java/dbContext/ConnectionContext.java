/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class ConnectionContext {
    
    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {
        return getMySQLConnection();
    }
    
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {

        String hostName = "localhost";
        String dbName = "spm391_bl5";
        String userName = "root";
        String password = "123456789";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
            String userName, String password) throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
