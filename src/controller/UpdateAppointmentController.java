/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionsDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

/**
 * FXML Controller class
 *This controller manages the updating of appointments in the database
 * @author jeffd
 */
public class UpdateAppointmentController implements Initializable {
    
    @FXML private TextField update_appointment_ID;
    @FXML private TextField update_appointment_Title;
    @FXML private TextField update_appointment_Type;
    @FXML private TextField update_appointment_Description;
    @FXML private TextField update_appointment_Location;
    @FXML private DatePicker update_appointment_StartDate;
    @FXML private DatePicker update_appointment_EndDate;
    @FXML private ComboBox<Contact> update_appointment_Contact;
    @FXML private ComboBox<Customer> update_appointment_Customer_Name;
    @FXML private ComboBox<String> update_appointment_StartTime;
    @FXML private ComboBox<String> update_appointment_EndTime;
    
    ObservableList <Customer> customers = CustomerDAO.allCustomers();
    ObservableList <Contact> contactlist = ContactDAO.allContacts();
    ObservableList <String> times2 = FXCollections.observableArrayList();
    /**
     * This event handles the updating of exiting appointments and will parse through the fields on the form
     * @param event 
     */
    public void updateAppointmentButton(ActionEvent event)
    {
        try{
            int id = Integer.parseInt(update_appointment_ID.getText());
            String title = update_appointment_Title.getText();
            int customer = update_appointment_Customer_Name.getValue().getCustomer_ID();
            String type = update_appointment_Type.getText();
            String description = update_appointment_Description.getText();
            String location = update_appointment_Location.getText();
            LocalDate startDate = update_appointment_StartDate.getValue();
            LocalDate endDate = update_appointment_EndDate.getValue();
            Contact desiredContact = update_appointment_Contact.getValue();
            int contact = ContactDAO.getContactID(desiredContact);
            int userID = UserDAO.currentuser.get(0).getUser_ID();
            String created_By = UserDAO.currentuser.get(0).getUser_Name();
            String last_Updated_By = UserDAO.currentuser.get(0).getUser_Name();
            String appStartTime = update_appointment_StartTime.getValue();
            String appEndTime = update_appointment_EndTime.getValue();
            
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
                invalidInputAlert += "Please select a start date that is before end date. \n";
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
            
            boolean isEndTimeBoxEmpty = update_appointment_EndTime.getSelectionModel().isEmpty();
            
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
            }else
            {


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

                if(overlap())
                {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.initModality(Modality.APPLICATION_MODAL);
                    errorAlert.setTitle("Appointment overlap!!");
                    errorAlert.setHeaderText("Overlapping appointments"); 
                    errorAlert.showAndWait();
                } 
                else
                {
                    AppointmentDAO.updateAppointment( id, title, description, location, type, startDateTime, endDateTime, created_By, last_Updated_By, customer, userID, contact);

                    update_appointment_Customer_Name.getItems().clear();
                    update_appointment_Contact.getItems().clear();
                    update_appointment_StartTime.getItems().clear();
                    update_appointment_EndTime.getItems().clear();

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
     * this button cancels the update form and returns to the main appointment screen
     * @param event this is the event handler for canceling the update screen
     * @throws IOException 
     */
    public void cancelButton(ActionEvent event) throws IOException
    {
        update_appointment_Customer_Name.getItems().clear();
        update_appointment_Contact.getItems().clear();
        update_appointment_StartTime.getItems().clear();
        update_appointment_EndTime.getItems().clear();
        Parent updateAppointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(updateAppointmentScene);
        window.centerOnScreen();
        window.show();
    }
    /**
     * This is responsible for sending/ getting the data from the appointments and filling the fields on the update form
     * @param selectedAppointment when used will get all required appointment data and send it to the update appointment form and populate the fields 
     */
    public void sendAppointmentData(Appointment selectedAppointment)
    {
        try{
            update_appointment_ID.setText(String.valueOf(selectedAppointment.getAppointment_ID()));    
            update_appointment_Title.setText(selectedAppointment.getTitle());
            update_appointment_Type.setText(selectedAppointment.getType());
            update_appointment_Description.setText(selectedAppointment.getDescription());
            update_appointment_Location.setText(selectedAppointment.getLocation());
            update_appointment_StartDate.setValue(LocalDate.from(selectedAppointment.getStart()));
            update_appointment_EndDate.setValue(LocalDate.from(selectedAppointment.getClose()));
            LocalTime StartTime = LocalTime.from(selectedAppointment.getStart());
            LocalTime EndTime = LocalTime.from(selectedAppointment.getClose());
            update_appointment_StartTime.setValue(String.valueOf(StartTime));
            update_appointment_EndTime.setValue(String.valueOf(EndTime));

            int cont = ContactDAO.getContactID(selectedAppointment.getContact_ID());
            for(Contact contacts : update_appointment_Contact.getItems())
            {
                if(contacts.getContact_ID() == cont)
                {
                    update_appointment_Contact.setValue(contacts);
                }
            }
            
            int cust = selectedAppointment.getCustomer_ID();
            for(Customer customer : update_appointment_Customer_Name.getItems())
            {
                if(customer.getCustomer_ID() == cust)
                {
                    update_appointment_Customer_Name.setValue(customer);
                }
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
               
    }
    /**
     * This method checks if any of the appointments overlap by user or contact 
     * and will not allow appointments that do overlap. It will also open a 
     * window with an alert to notify the user.
     * @return false 
     */
    private boolean overlap(){
        try{

            int customerId = update_appointment_Customer_Name.getValue().getCustomer_ID();
            String desiredStartTime = update_appointment_StartTime.getValue();
            String desiredEndTime = update_appointment_EndTime.getValue();
            LocalDate startDate = update_appointment_StartDate.getValue();
            LocalDate endDate = update_appointment_EndDate.getValue();
            LocalTime startTime = LocalTime.parse(desiredStartTime);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalTime endTime = LocalTime.parse(desiredEndTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            int overlaps = AppointmentDAO.overlapsAppointment(startDateTime, endDateTime, customerId);
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
    /**
     * This method calculates the time values between 08:00 and 22:00
     */
    public void timemaker2()
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
                        times2.add(time);
                    }
                    else{
                        String time = i + ":" + "0" + j;
                        times2.add(time);
                    }
                }
                
                if (j >= 10 && j <= 55)
                {
                    if(i < 10)
                    {
                        String time2 = "0" + i + ":" + j;
                        //System.out.println(time2);
                        times2.add(time2);
                    }
                    else{
                    String time2 = i + ":" + j;
                    //System.out.println(time2);
                    times2.add(time2);
                    }
                }
                
                if (j == 0)
                {
                    if(i < 10)
                    {
                        String time3 = "0" + i + ":00";
                        //System.out.println(time3);
                        times2.add(time3);
                    }
                    else{
                         String time3 = i + ":00";
                        //System.out.println(time3);
                        times2.add(time3);
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
     * Initializes the controller class and sets the comboboxes on the form.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CustomerDAO.allCustomers().clear();
        CustomerDAO.allCustomers();
        ContactDAO.allContacts().clear();
        ContactDAO.allContacts();
        update_appointment_Customer_Name.setItems(customers);
        update_appointment_Contact.setItems(contactlist);
        timemaker2();
        update_appointment_StartTime.setItems(times2);
        update_appointment_EndTime.setItems(times2);
    }    
    
}
