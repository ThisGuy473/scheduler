/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author jeffd
 */
public class DBConnection {
    
    //jdbc parts
    private static final String protocol = "jdbc";
    private static final String vendername = "mysql";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ06ocR";
    
    //jdbc urL
    private static final String jdbcURL = protocol + ":" + vendername + ":" + ipAddress;
    
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    
    public static java.sql.Connection conn = null;
    
    private static final String username = "U06ocR";
    private static final String password = "53688824812";
    
    
    public static void startConnection()
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);  
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful!");
        }
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void closeConnection()
    {
       try
       {
           conn.close();
           System.out.println("Connection Closed!");
       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }
    }
    
}
