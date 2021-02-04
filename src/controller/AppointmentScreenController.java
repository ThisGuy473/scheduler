/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import static controller.CustomerScreenController.allAppointments;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author jeffd
 * This controller manages the appointment screen 
 */
public class AppointmentScreenController implements Initializable {
    
    
    @FXML private Button Customers;
    @FXML private Button Reports;
    @FXML private Button Appointments;
    @FXML private Button ScheduleButton;
    @FXML private Button UpdateButton;
    @FXML private Button DeleteButton;
    
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML private TableColumn<Appointment, String> appointmentContactCol;
    @FXML private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentStartDateCol;
    @FXML private TableColumn<Appointment, LocalDateTime> appointmentEndDateCol;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerIDCol;

    @FXML private RadioButton week_radiobutton;
    @FXML private RadioButton month_radiobutton;
    @FXML private RadioButton all_radiobutton;
    @FXML private ToggleGroup dateRangeToggleGroup;
    ObservableList <Appointment> allAppointments = AppointmentDAO.allAppointments();
    ObservableList <Appointment> weekAppointment = AppointmentDAO.getAppointmentsWeek();
    ObservableList <Appointment> monthAppointment = AppointmentDAO.getAppointmentsMonth();
    
    Stage stage;
    
    //public static ObservableList<Appointment> appointments = AppointmentDAO.allAppointments();
    
    /**This is the method that send you to the customer screen
     * 
     * @param event This event is the user navigating to different pages using the customer menu button
     * @throws IOException 
     */
    public void menuCustomers(ActionEvent event) throws IOException
    {
        AppointmentDAO.allAppointments.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**This is the menu reports button
     * 
     * @param event This is the event that sends the user to the Reports screen
     * @throws IOException 
     */
    public void menuReports(ActionEvent event) throws IOException
    {
        AppointmentDAO.allAppointments.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**This the the event for the button that sends the user to the appointments page
     * 
     * @param event this is the event for sending the user to the appointments page
     * @throws IOException 
     */
    public void menuAppointments(ActionEvent event) throws IOException
    {
        AppointmentDAO.allAppointments.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**This is the add appointment event that will send the user to the add appointment page
     * 
     * @param event this event will send the user to the addAppointment page
     * @throws IOException 
     */
    public void addAppointment(ActionEvent event) throws IOException
    {
        try{
            AppointmentDAO.allAppointments.clear();
            Parent appointmentParent = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
            Scene addAppointmentScene = new Scene(appointmentParent);

            //This line gets the Stage info
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(addAppointmentScene);
            window.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
    }
    /**
     * This will update the selected item in the tableview so it can be edited
     * @param event this event will send the user to the update appointment page
     * @throws IOException 
     */
    public void updateAppointment(ActionEvent event) throws IOException
    {
        try{
            Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
           if(selectedAppointment == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Warning");
                alert.setHeaderText("No Appointment Selected"); 
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/controller/UpdateAppointment.fxml"));
                loader.load();

                //sends the data over to new window
                UpdateAppointmentController updateAppointmentController = loader.getController();
                
                updateAppointmentController.sendAppointmentData(selectedAppointment);

                AppointmentDAO.allAppointments.clear();

                //sets the stage
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.centerOnScreen();
                stage.show();
            }        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * This event deletes the currently selected item from the database and 
     * observable list using a lambda to wait for a user response. The Lambda is 
     * a great tool for shortening code and increasing efficiency. The Lambda 
     * made listening for the user to select an option easier.
     * @param event this handles the delete action event 
     */
    public void deleteAppointment(ActionEvent event)
    {
           Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
           if(selectedAppointment == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Warning");
                alert.setHeaderText("No Appointment Selected"); 
                alert.showAndWait();
            } else {
               
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setContentText("Are you sure you want to delete appointment? ");
                //This is where the lambda is used to wait for a response from the user.
                alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    try{
                        int id = selectedAppointment.getAppointment_ID();
                        AppointmentDAO.deleteAppointment(id);
                        AppointmentDAO.allAppointments().clear();
                        appointmentTableView.setItems(AppointmentDAO.allAppointments());
                        Alert deletedAlert = new Alert(Alert.AlertType.ERROR);
                        deletedAlert.initModality(Modality.APPLICATION_MODAL);
                        deletedAlert.setTitle("Deleted!!");
                        deletedAlert.setHeaderText("Appointment ID: " + selectedAppointment.getAppointment_ID() + " Appointment Type: " + selectedAppointment.getType() + " Deleted!!"); 
                        deletedAlert.showAndWait();
                    }catch(Exception e){
                        e.printStackTrace();
                    }    
                } else {
                    
                }
            }));
            }  

    }

    /**
     * This sorts the appointments according to week if the week radio button is selected
     * @param event handles the week sorting event
     * @throws IOException 
     */
    public void weekSorter(ActionEvent event) throws IOException
    {

        if(week_radiobutton.isSelected())
        {
            AppointmentDAO.getAppointmentsWeek().clear();
            appointmentTableView.getItems().clear();
            AppointmentDAO.getAppointmentsWeek();
            appointmentTableView.setItems(weekAppointment);
        }
       
    }
    /**
     * This sorts the Appointments by month
     * @param event this event gets the data from the database 
     * @throws IOException 
     */
    public void monthSorter(ActionEvent event) throws IOException
    {

        if(month_radiobutton.isSelected())
        {
            appointmentTableView.getItems().clear();
            AppointmentDAO.allAppointments.clear();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusMonths(1);
            LocalTime zeroedTime = LocalTime.of(00,00);
            LocalTime midnight = LocalTime.of(11, 59);
            LocalDateTime apptStart = LocalDateTime.of(startDate,zeroedTime);
            LocalDateTime apptEnd = LocalDateTime.of(endDate,midnight);
            AppointmentDAO.getDateRange(apptStart,apptEnd);
            appointmentTableView.setItems(allAppointments);
            //AppointmentDAO.getAppointmentsMonth().clear();
            //appointmentTableView.getItems().clear();
            //AppointmentDAO.getAppointmentsMonth();
            //appointmentTableView.setItems(monthAppointment);
        }
    }
    /**
     * This puts all the appointments in the database into the table view.
     * @param event this event will return allAppointments.
     * @throws IOException 
     */
    public void allSorter(ActionEvent event) throws IOException
    {
        if(all_radiobutton.isSelected())
        {
            AppointmentDAO.allAppointments().clear();
            appointmentTableView.getItems().clear();
            //AppointmentDAO.allAppointments();
            appointmentTableView.setItems(AppointmentDAO.allAppointments());
        }
    }

    /**
     * Initializes the controller class, appointment table, and radio buttons
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        dateRangeToggleGroup = new ToggleGroup();
        this.all_radiobutton.setSelected(true);
        this.all_radiobutton.setToggleGroup(dateRangeToggleGroup);
        this.week_radiobutton.setToggleGroup(dateRangeToggleGroup);
        this.month_radiobutton.setToggleGroup(dateRangeToggleGroup);
        AppointmentDAO.allAppointments().clear();
        appointmentTableView.setItems(AppointmentDAO.allAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appointmentStartDateCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentEndDateCol.setCellValueFactory(new PropertyValueFactory<>("Close"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

    }    
    
}
