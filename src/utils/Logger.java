/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 *
 * @author jeffd
 */
public class Logger {
   
    private static final String FILENAME = "src/utils/login_activty.txt";

    public Logger() {}
    
    public static void log (String username, boolean loginSuccess) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
             
            BufferedWriter buffwriter = new BufferedWriter(fw);
            PrintWriter password = new PrintWriter(buffwriter)) {
                
                password.println(ZonedDateTime.now() + " USER_ID: " + username + " Login Attempt: " + (loginSuccess ? " Successful" : " Failed"));
                
        } catch (IOException e) {
               
            e.printStackTrace();
            
        }
    }
}
