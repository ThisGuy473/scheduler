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
import DAO.FirstLevelDivisionsDAO;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

/**
 * FXML Controller class
 *This is the reports screen controller and handles all the required reports
 * @author jeffd
 */
public class ReportsScreenController implements Initializable {
    
    @FXML private Button Customers;
    @FXML private Button Reports;
    @FXML private Button Appointments;
    @FXML private Button GenerateType;
    @FXML private Button GenerateCustomers;
    @FXML private TextField typeMonthResults;
    @FXML private TextField customerResults;
    @FXML private TableView<Appointment> reportsTableView;
    @FXML private TableColumn<Appointment, Integer> reportsAppointmentIDCol;
    @FXML private TableColumn<Appointment, String> reportsTitleCol;
    @FXML private TableColumn<Appointment, String> reportsTypeCol;
    @FXML private TableColumn<Appointment, String> reportsDescriptionCol;
    @FXML private TableColumn<Appointment, LocalDateTime> reportsStartCol;
    @FXML private TableColumn<Appointment, LocalDateTime> reportsEndCol;
    @FXML private TableColumn<Appointment, Integer> reportsCustomerIDCol;
    @FXML private ComboBox<String> reports_appointment_types;
    @FXML private ComboBox<Customer> reports_customer_Name;
    @FXML private ComboBox<Contact> reports_contact_Name;
    @FXML private DatePicker reportMonth;
    
    ObservableList <Customer> customers = CustomerDAO.allCustomers();
    ObservableList <Appointment> appointment = AppointmentDAO.allAppointments();
    ObservableList <Contact> contactlist = ContactDAO.allContacts();
    ObservableList <Countries> countries = CountriesDAO.getCountries();
    ObservableList<FirstLevelDivisions> divsByCountry = FirstLevelDivisionsDAO.getAllFirstLevelDivisions();
    ObservableList <Countries> country = FXCollections.observableArrayList();
    ObservableList <String> types = AppointmentDAO.getAppointmentTypes();
    
    /**
     * This is a navigation button that sends you to the customers page.
     * @param event on action this will send you to the customer page.
     * @throws IOException input output
     */
    public void menuCustomers(ActionEvent event) throws IOException
    {
        CustomerDAO.allCustomers().clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This is a navigation button that sends you to the reports page.
     * @param event this sends you to the reports page
     * @throws IOException input ouput exception
     */
    public void menuReports(ActionEvent event) throws IOException
    {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This is a navigation button that sends you to the appointments page.
     * @param event this sends you to the appointments page
     * @throws IOException input output
     */
    public void menuAppointments(ActionEvent event) throws IOException
    {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This is used to sort the month from the date picker.
     * @param event allows you to select a date
     * @throws IOException input output
     */
    public void monthSorter(ActionEvent event) throws IOException
    {

            if(reportMonth.getValue() != null)
            {
                reportsTableView.getItems().clear();
                LocalTime timeStart = LocalTime.of(00, 00);
                LocalTime timeEnd = LocalTime.of(12, 00);
                LocalDate dateStart = reportMonth.getValue();
                LocalDate dateEnd = dateStart.plusMonths(1);
                LocalDateTime appointmentStartDateTime = LocalDateTime.of(dateStart, timeStart);
                LocalDateTime appointmentEndDateTime = LocalDateTime.of(dateEnd, timeEnd);
                AppointmentDAO.getDateRange(appointmentStartDateTime, appointmentEndDateTime);
                reportsTableView.setItems(appointment);
            
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.initModality(Modality.APPLICATION_MODAL);
                errorAlert.setTitle("Warning: Empty Date Field");
                errorAlert.setHeaderText("No Month selected. Please choose a Month."); 
                errorAlert.showAndWait();
        
            }
        
    }
    /**
     * This will allow the user to select a contact and find appointments associated with that contact.
     * @param event this handles the finding of appointments in the database using the value from the combo box
     */
    public void reportsContactAppSorter(ActionEvent event)
    {
        try{
            appointment.clear();
            Contact desiredContact = reports_contact_Name.getValue();
            int contact = ContactDAO.getContactID(desiredContact);
            appointment = AppointmentDAO.appointmentsByContact(contact);
            reportsTableView.setItems(appointment);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * This handles finding the appointments by month and type.
     * @param event the method will sort appointments based on the type and month to get all appointments.
     */    
    @FXML void reportByMonthAndType(ActionEvent event)
    {
        //boolean isCustomerBoxEmpty = appointment_Customer_Name.getSelectionModel().isEmpty();


        //checks if type and month boxes are empty
        if(reports_appointment_types.getValue() != null && reportMonth.getValue() != null)
        {
            reports_appointment_types.setItems(types);
            LocalDate startReportDate = reportMonth.getValue();
            LocalTime reportTime = LocalTime.of(00,01);
            String appointmentType = reports_appointment_types.getValue();
            LocalDateTime startReportDateTime = LocalDateTime.of(startReportDate,reportTime);
            LocalDateTime endReportDateTime = startReportDateTime.plusMonths(1);
            int reportNumber = AppointmentDAO.getMonthlyReport(appointmentType,startReportDateTime,endReportDateTime);
            typeMonthResults.setText(String.valueOf(reportNumber));
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.initModality(Modality.APPLICATION_MODAL);
            errorAlert.setTitle("Warning: Empty Type or Date Field");
            errorAlert.setHeaderText("Please input value in both fields and press GENERATE!"); 
            errorAlert.showAndWait();
        }
    }
    /**
     * This will find all appointments associate with a selected customer.
     * @param event handles the action of filtering and finding the count of appointments. 
     */
    @FXML void reportAppointmentsByCustomer(ActionEvent event)
    {
        //Checks if combobox is empty
        if(reports_customer_Name.getValue() != null)
        {
            int customerID = reports_customer_Name.getValue().getCustomer_ID();
            int apptNumber = AppointmentDAO.getCountOfAppointmentsByCustomer(customerID);
            customerResults.setText(String.valueOf(apptNumber));
        }
    }
    

    /**
     * Initializes the controller class, reports table view, and sets the comboboxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        AppointmentDAO.getAppointmentTypes().clear();
        AppointmentDAO.getAppointmentTypes();
        CustomerDAO.allCustomers().clear();
        CustomerDAO.allCustomers();
        ContactDAO.allContacts().clear();
        ContactDAO.allContacts();
        reports_contact_Name.setItems(contactlist);
        reports_customer_Name.setItems(customers);
        reports_appointment_types.setItems(types);

        reportsAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        reportsTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        reportsTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        reportsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        reportsStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        reportsEndCol.setCellValueFactory(new PropertyValueFactory<>("Close"));
        reportsCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        
        
    }    
    
}
