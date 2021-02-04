/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import DAO.AppointmentDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.fxml.FXMLLoader.load;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.User;


/**
 * FXML Controller class
 *This class handles all the login needs
 * @author jeffd
 */
public class LoginScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    ResourceBundle rb;
    Locale locale;
    @FXML private Label loginTitle;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Button loginSignIn;
    @FXML private Label loginCurrentLocation;
    @FXML private Label loginLocation;

    /**
     * This will handle the login including validating the password with the database
     * checking for 15 min appointment alerts and validating that all input is correct.
     * @param event this event handles the login 
     */
    public void handleLogin(ActionEvent event) {
        
        try{
            String userName = loginUsername.getText();
            String userPassword = loginPassword.getText();
            ObservableList<User> currentuser = UserDAO.currentuser(userName);
            
            if (userName.length() > 20)
            {
                ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle(rb.getString("AlertTitle"));
                alert.setHeaderText(rb.getString("AlertMaxSize"));
                alert.showAndWait();
                loginUsername.setText("");
                loginPassword.setText("");
            }
            if (userPassword.length() > 20)
            {
                ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle(rb.getString("AlertTitle"));
                alert.setHeaderText(rb.getString("AlertMaxSize"));
                alert.showAndWait();
                loginUsername.setText("");
                loginPassword.setText("");
            }
                if(UserDAO.validateUser(userName))
                {
                    for (User user : currentuser)
                    {

                            if(UserDAO.validatePassword(user.getUser_ID(), userPassword))
                            {    
                                Alert15MinAlert(user.getUser_ID());
                                Parent appointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
                                Scene appointmentScene = new Scene(appointmentParent);

                                //This line gets the Stage info
                                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                                window.setScene(appointmentScene);
                                window.centerOnScreen();
                                window.show();

                            }

                        else
                        {
                            ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setTitle(rb.getString("AlertTitle"));
                            alert.setHeaderText(rb.getString("AlertText"));
                            alert.showAndWait();
                            loginUsername.setText("");
                            loginPassword.setText("");
                        }
                    }

                }
                else
                {
                    ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle(rb.getString("AlertTitle"));
                    alert.setHeaderText(rb.getString("AlertText"));
                    alert.showAndWait();
                    loginUsername.setText("");
                    loginPassword.setText("");  
                }



            } 
            catch (Exception e)
                {
                    e.printStackTrace();
                }

        }
        /**
         * This method will check if there are any appointments in the next 15mins and display a message if there is an appointment.
         * @param user_ID this takes the user ID and gets the appointments by user 
         */
        private static void Alert15MinAlert(int user_ID)
        {
            ObservableList<Appointment> appointmentsByUser = AppointmentDAO.getAppointmentByUser(user_ID);
            //ObservableList<Appointment> alert15Appointments = FXCollections.observableArrayList();
            //LocalDateTime localDateTime = LocalDateTime.now();
            ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
            boolean hasAppt = false; 

            for (Appointment appointment : appointmentsByUser)
            {
                LocalDateTime startTime = appointment.getStart();
                LocalDateTime currentTime = LocalDateTime.now();
                long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
                int apptID = appointment.getAppointment_ID();
                LocalDateTime dateTime = appointment.getStart();

                if(timeDifference <= 15 && timeDifference > 0)
                {
                    hasAppt = true;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Upcoming Appointment!!");
                    alert.setHeaderText("Appointment_ID: " + apptID + "   Start Date/Time: " + dateTime);
                    alert.showAndWait();

                }

            }
            if (hasAppt == false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Notification");
                alert.setHeaderText("You have an no upcoming Appointments");
                alert.showAndWait();
            }
        }
    
   

    /**
     * Initializes the controller class, and username and password fields
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //ResourceBundle rb = ResourceBundle.getBundle("Languages/lang", Locale.getDefault());
        
        this.locale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("Languages/lang", this.locale);
        loginTitle.setText(this.rb.getString("Title"));
        loginUsername.promptTextProperty().set(this.rb.getString("Username"));
        loginPassword.promptTextProperty().set(this.rb.getString("Password"));
        loginSignIn.setText(this.rb.getString("Enter"));
        loginCurrentLocation.setText(this.rb.getString("Current"));
        loginLocation.setText(this.rb.getString("Location"));
        
    }    
    
}
