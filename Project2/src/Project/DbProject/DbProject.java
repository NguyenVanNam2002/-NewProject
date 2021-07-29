/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project.DbProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DbProject {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN_STRING
            = "jdbc:mysql://localhost/snack_shop";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

    }
     public static Connection getConnection(DbType type) throws SQLException{
        switch(type){
            case SQL:
                break;
            case SQLite:
//                return getConnectionSqlLite();
                break;
            default:
                return getConnection();
        }
        return getConnection();
    }
}
