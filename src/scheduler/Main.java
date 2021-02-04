/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;


/**
 *Main class for Scheduler. Scheduler is simple and modern way to track your
 * appointments based on contacts and customers. 
 * Two Lambda expressions and be found in the AddAppointmentController in the 
 * DeleteAppointment method. The other can be found in the CustomerScreenController
 * in the DeleteCustomer method.
 * @author jeffd
 */
public class Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       
       DBConnection.startConnection();
       //Locale.setDefault(new Locale("fr"));
       launch(args);
       DBConnection.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/controller/LoginScreen.fxml"));
       Scene scene = new Scene(root);
       stage.setTitle("Scheduler");
       stage.setScene(scene);
       stage.show(); 
    }
    
}
  