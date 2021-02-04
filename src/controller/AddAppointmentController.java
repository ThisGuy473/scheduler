/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CountriesDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Countries;
import model.Customer;

/**
 * FXML Controller class
 *This controller is used to add appointments
 * @author jeffd
 */
public class AddAppointmentController implements Initializable {
    
    Parent parent;
    Stage stage;
    
    @FXML private TextField appointment_Title;
    @FXML private TextField appointment_Type;
    @FXML private TextField appointment_Description;
    @FXML private TextField appointment_Location;
    @FXML private DatePicker appointment_StartDate;
    @FXML private DatePicker appointment_EndDate;
    @FXML private ComboBox<Contact> appointment_Contact;
    @FXML private ComboBox<Customer> appointment_Customer_Name;
    @FXML private ComboBox<String> appointment_StartTime;
    @FXML private ComboBox<String> appointment_EndTime;
    @FXML private Label inputValidationLabel;
    
    ObservableList <Customer> customers = CustomerDAO.allCustomers();
    ObservableList <Contact> contactlist = ContactDAO.allContacts();
    ObservableList <String> times = FXCollections.observableArrayList();
    /**
     * This will save the appointment
     * @param event this event saves and validates all the input in the fields
     */
    public void saveAppointmentButton(ActionEvent event)
    {
        try{
          
            String title = appointment_Title.getText();
            int customer = appointment_Customer_Name.getValue().getCustomer_ID();
            String type = appointment_Type.getText();
            String description = appointment_Description.getText();
            String location = appointment_Location.getText();
            LocalDate startDate = appointment_StartDate.getValue();
            LocalDate endDate = appointment_EndDate.getValue();
            Contact desiredContact = appointment_Contact.getValue();
            int contact = ContactDAO.getContactID(desiredContact);
            int userID = UserDAO.currentuser.get(0).getUser_ID();
            String created_By = UserDAO.currentuser.get(0).getUser_Name();
            String last_Updated_By = UserDAO.currentuser.get(0).getUser_Name();
            String appStartTime = appointment_StartTime.getValue();
            String appEndTime = appointment_EndTime.getValue();
            
            String invalidInputAlert = "";
            boolean error = true;

            if (description.length() == 0)
            {
                invalidInputAlert += "You must enter a appointment description. \n";
                error = false;
            }
            if (description.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            if (title.length() == 0)
            {
                invalidInputAlert += "You must enter a appointment title. \n";
                error = false;
            }
            if (title.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            if (type.length() == 0)
            {
                invalidInputAlert += "You must enter a appointment type. \n";
                error = false;
            }
            if (type.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
             if (location.length() == 0)
            {
                invalidInputAlert += "You must enter a appointment location. \n";
                error = false;
            }
            if (location.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            if (startDate == null)
            {
                invalidInputAlert += "Please select an start date. \n";
                error = false;
            }
            if (startDate.isBefore(endDate))
            {
                invalidInputAlert += "Please select an start date that is before end date. \n";
                error = false;
            }
            if (endDate == null)
            {
                invalidInputAlert += "Please select an end date. \n";
                error = false;
            }
            if (endDate.isAfter(startDate))
            {
                invalidInputAlert += "Please select an end date that is after Start Date. \n";
                error = false;
            }
            
            boolean isContactBoxEmpty = appointment_Contact.getSelectionModel().isEmpty();
      
            if (isContactBoxEmpty == true)
            {
                invalidInputAlert += "Please select an Contact. \n";
                error = false;
            }
 
            boolean isCustomerBoxEmpty = appointment_Customer_Name.getSelectionModel().isEmpty();
            
            if (isCustomerBoxEmpty == true)
            {
                invalidInputAlert += "Please select a Customer. \n";
                error = false;
            }
            
            boolean isStartTimeBoxEmpty = appointment_StartTime.getSelectionModel().isEmpty();
            
            if (isStartTimeBoxEmpty == true)
            {
                invalidInputAlert += "Please select a Start Time. \n";
                error = false;
            }
            
            boolean isEndTimeBoxEmpty = appointment_EndTime.getSelectionModel().isEmpty();
            
            if (isEndTimeBoxEmpty == true)
            {
                invalidInputAlert += "Please select a End Time. \n";
                error = false;
            }
            
            if (error == false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("INPUT ERROR");
                alert.setHeaderText("Please correct the following errors to proceed: ");
                alert.setContentText(invalidInputAlert);
                Optional<ButtonType> rs = alert.showAndWait();
                
            }else{

                ZoneId localZoneId = ZoneId.systemDefault();

                LocalTime startTime = LocalTime.parse(appStartTime);
                LocalTime endTime = LocalTime.parse(appEndTime);

                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                LocalTime businessStartTime = LocalTime.of(8, 00);
                LocalTime businessEndTime = LocalTime.of(22, 00);

                ZonedDateTime appointmentStartDateTime = startDateTime.atZone(localZoneId).withZoneSameInstant(ZoneId.of("US/Eastern"));
                ZonedDateTime appointmentEndDateTime = endDateTime.atZone(localZoneId).withZoneSameInstant(ZoneId.of("US/Eastern"));
                ZonedDateTime businessStartDateTime = ZonedDateTime.of(startDate, businessStartTime, ZoneId.of("US/Eastern"));
                ZonedDateTime businessEndDateTime = ZonedDateTime.of(startDate, businessEndTime, ZoneId.of("US/Eastern"));

                LocalDateTime businessStart = businessStartDateTime.toLocalDateTime();
                LocalDateTime appointmentStart = appointmentStartDateTime.toLocalDateTime();
                LocalDateTime businessEnd = businessEndDateTime.toLocalDateTime();
                LocalDateTime appointmentEnd = appointmentEndDateTime.toLocalDateTime();

                if(overlap() == true)
                {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.initModality(Modality.APPLICATION_MODAL);
                    errorAlert.setTitle("Appointment overlap!!");
                    errorAlert.setHeaderText("Overlapping appointments"); 
                    errorAlert.showAndWait();
                } 
                else
                {
                    AppointmentDAO.newAppointment( title, description, location, type, startDateTime, endDateTime, created_By, last_Updated_By, customer, userID, contact);

                    appointment_Customer_Name.getItems().clear();
                    appointment_Contact.getItems().clear();
                    appointment_StartTime.getItems().clear();
                    appointment_EndTime.getItems().clear();

                    Parent updateAppointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
                    Scene updateAppointmentScene = new Scene(updateAppointmentParent);

                    //This line gets the Stage info
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(updateAppointmentScene);
                    window.centerOnScreen();
                    window.show();
                }
            }
        }    
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * This cancels the addAppointment window 
     * @param event this event sends you back to the appointment screen
     * @throws IOException 
     */
    public void cancelButton(ActionEvent event) throws IOException
    {
        Parent updateAppointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(updateAppointmentScene);
        window.centerOnScreen();
        window.show();
    }
    /**
     * This checks to see if there is an overlap in the appointments trying to be scheduled with those that already exist
     * @return false
     */
    public boolean overlap(){
        try{
            
            int customerId = appointment_Customer_Name.getValue().getCustomer_ID();
            String desiredStartTime = appointment_StartTime.getValue();
            String desiredEndTime = appointment_EndTime.getValue();
            LocalDate startDate = appointment_StartDate.getValue();
            LocalDate endDate = appointment_EndDate.getValue();
            LocalTime startTime = LocalTime.parse(desiredStartTime);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalTime endTime = LocalTime.parse(desiredEndTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            int overlaps = AppointmentDAO.overlapsAppointment(startDateTime, endDateTime, customerId);
            System.out.println(overlaps);
            if(overlaps >= 1)    
            {   
                return true;
            }
           
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public void timemaker()
    {
        try{

            int hours = 21;
            int mins = 60;
            int i = 8;

            // outer loop
            while (i <= hours) {
              // inner loop
              for (int j = 0; j <= mins; j=j+5) {
                if (j < 10 && j > 1)
                {
                    if(i < 10)
                    {
                        String time = "0" + i + ":" + "0" + j;
                        times.add(time);
                    }
                    else{
                        String time = i + ":" + "0" + j;
                        times.add(time);
                    }
                }
                
                if (j >= 10 && j <= 55)
                {
                    if(i < 10)
                    {
                        String time2 = "0" + i + ":" + j;
                        //System.out.println(time2);
                        times.add(time2);
                    }
                    else{
                    String time2 = i + ":" + j;
                    //System.out.println(time2);
                    times.add(time2);
                    }
                }
                
                if (j == 0)
                {
                    if(i < 10)
                    {
                        String time3 = "0" + i + ":00";
                        //System.out.println(time3);
                        times.add(time3);
                    }
                    else{
                        String time3 = i + ":00";
                        //System.out.println(time3);
                        times.add(time3);
                    }
                }
              }
              ++i;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CustomerDAO.allCustomers().clear();
        CustomerDAO.allCustomers();
        ContactDAO.allContacts().clear();
        ContactDAO.allContacts();
        appointment_Customer_Name.setItems(customers);
        appointment_Contact.setItems(contactlist);
        appointment_Customer_Name.getSelectionModel().selectFirst();
        appointment_Contact.getSelectionModel().selectFirst();
        timemaker();
        appointment_StartTime.setItems(times);
        appointment_StartTime.getSelectionModel().selectFirst();
        appointment_EndTime.setItems(times);
        appointment_EndTime.getSelectionModel().selectFirst();
    }    
    
}
