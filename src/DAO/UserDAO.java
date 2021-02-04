/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.UserDAO.currentuser;
import java.sql.Connection;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import model.User;
import utils.Logger;


/**
 *This is my UserDAO class that handles all CRUD operations in regards to USERs
 * @author jeffd
 */
public class UserDAO {
    
    public static ObservableList<User> currentuser = FXCollections.observableArrayList();
    private final static Connection conn = DBConnection.conn;
    /**
     * This method checks the database to see if the password is valid.
     * @param user_ID this is the user ID and is checked against the database.
     * @param password this is the password and is checked against the database.
     * @return false
     * @throws SQLException throws a SQL exception because of the SQL statement
     */
    public static boolean validatePassword(int user_ID, String password) throws SQLException
    {

        String usernameInput =  "Select Password FROM users WHERE User_ID = '" + user_ID + "'";
        
        PreparedStatement statement = conn.prepareStatement(usernameInput);
        
        ResultSet rs = statement.executeQuery(usernameInput);
        
        while(rs.next())
        {
            if(rs.getString("Password").equals(password))
            {
                String id = String.valueOf(user_ID);
                Logger.log(id, true);
                statement.close();
                return true;
            } else {
                String id = String.valueOf(user_ID);
                Logger.log(id, false);
                statement.close();
                return false;
            }
            
        }
        
        return false;
        
    }
    /**
     * This method checks if the username is valid.
     * @param username this is used to check in the database if it exists.
     * @return true
     * @throws SQLException has a SQL statement.
     */
    public static boolean validateUser(String username) throws SQLException 
    {

        String usernameInput =  "SELECT * FROM users where User_Name='" + username + "'";
        
        PreparedStatement statement = conn.prepareStatement(usernameInput);
        
        ResultSet rs = statement.executeQuery(usernameInput);
        
        while(rs.next())
        {
            if(rs.getString("User_Name").equals(username))
                return true;
        }
        
        return false;
        
    }
    /**
     * This is a method that gets the current user based on username 
     * @param username this is used to query the database.
     * @return currentuser
     * @throws SQLException 
     */
    public static ObservableList<User> currentuser(String username) throws SQLException
    {
        String getUser = "SELECT * FROM users where User_Name='" + username + "'";
        PreparedStatement ps = conn.prepareStatement(getUser);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
           currentuser.clear();
           User user = new User();
           user.setUser_ID(rs.getInt("User_ID"));
           user.setUser_Name(rs.getString("User_Name"));
           currentuser.add(user);  
        }
        
        return currentuser;
    }
    
}
