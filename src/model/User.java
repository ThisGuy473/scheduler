/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


/**
 *
 * @author jeffd
 */
public class User {
    
    private int User_ID;
    private String User_Name;
    private String password;       


    public User(){ }

    public User(int User_ID, String User_Name, String password) {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.password = password;
        
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String User_Name) {
        this.User_Name = User_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
            
}
