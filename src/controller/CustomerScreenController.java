/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

/**
 * FXML Controller class
 *This class controls the customer screen
 * @author jeffd
 */
public class CustomerScreenController implements Initializable {
    
    @FXML private Button Customers;
    @FXML private Button Reports;
    @FXML private Button Appointments;
    @FXML private Button AddCustomer;
    @FXML private Button UpdateCustomer;
    @FXML private Button DeleteCustomer;
    
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> customerIdCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerAddressCol;
    @FXML private TableColumn<Customer, String> customerPostalCodeCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;
    @FXML private TableColumn<Customer, LocalDateTime> customerCreateDateCol;
    @FXML private TableColumn<Customer, String> custmerCreatedByCol;
    @FXML private TableColumn<Customer, Timestamp> customerLastUpdateCol;
    @FXML private TableColumn<Customer, String> customerLastUpdatedByCol;
    @FXML private TableColumn<Customer, Integer> customerDivisionIDCol;
    
    //Customer customer;
    Stage stage;
    
    
    
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Appointment> allAppointments = AppointmentDAO.allAppointments();
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<Customer> customer = FXCollections.observableArrayList();
    
    public CustomerScreenController(){}
    /**
     * This method sends the user to the customers page.
     * @param event this event changes the stage to customer screen.
     * @throws IOException 
     */
    public void menuCustomers(ActionEvent event) throws IOException
    {
        CustomerDAO.allCustomers.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This method sends the user to the reports page.
     * @param event this event changes the stage to the reports screen.
     * @throws IOException 
     */
    public void menuReports(ActionEvent event) throws IOException
    {
        CustomerDAO.allCustomers.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("ReportsScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This method sends the user to the appointments page.
     * @param event this event changes the stage to the appointments screen.
     * @throws IOException 
     */
    public void menuAppointments(ActionEvent event) throws IOException
    {
        CustomerDAO.allCustomers.clear();
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene appointmentScene = new Scene(appointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(appointmentScene);
        window.show();
    }
    /**
     * This method adds a customer based on the fields from the form.
     * @param event on action the method will change the stage to the add appointments screen.
     * @throws IOException 
     */
    public void addCustomer(ActionEvent event) throws IOException
    {
       CustomerDAO.allCustomers.clear();
       Parent addCustomerParent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
       Scene addCustomerScene = new Scene(addCustomerParent);

       //This line gets the Stage info
       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

       window.setScene(addCustomerScene);
       window.centerOnScreen();
       window.show(); 

    }
    /**
     * This method will send the user to the update customer screen.
     * @param event this will change the stage to the update customer stage.
     */
    public void updateCustomer(ActionEvent event)
    {
        try{
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
           
            if(selectedCustomer == null){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Warning");
                alert.setHeaderText("No Customer Selected"); 
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/controller/UpdateCustomer.fxml"));
                loader.load();

                //sends the data over to new window
                UpdateCustomerController updateCusController = loader.getController();
                //Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
                updateCusController.sendCustomerData(selectedCustomer);

                //sets the stage
                customerTableView.getItems().clear();
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
     * This method deletes the currently selected customer in the table view.
     * @param event this handles the deletion of the selected customer from the Observable list and the database. The Lambda is 
     * a great tool for shortening code and increasing efficiency. The Lambda 
     * made listening for the user to select an option easier. 
     */
    public void deleteCustomer(ActionEvent event)
    {
       try{
           
           Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
           //boolean existingAppointments = true;
           
           if(selectedCustomer == null){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Warning");
                alert.setHeaderText("No Customer Selected"); 
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setContentText("Are you sure you want to delete Customer?" );
                //This is where the lambda is used to wait for a response from the user.
                alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK){
                    boolean existingAppointments = true;
                    int num = selectedCustomer.getCustomer_ID();
                    if(CustomerDAO.hasAppointment(num))
                    {
                        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
                        warning.setTitle("Warning");
                        warning.setContentText("Customer " + selectedCustomer.getCustomer_Name() + " has scheduled Appointments. Would you still like to proceed?" );
                        Optional<ButtonType> results = warning.showAndWait();
                        if (results.get() == ButtonType.OK){
                            for (Appointment appointments : allAppointments){
                                if (appointments.getCustomer_ID() == num){
                                    AppointmentDAO.deleteAppointment(appointments.getAppointment_ID());
                                    
                                }
                            }
                            existingAppointments = false;
                            customerTableView.getItems().clear();
                            customerTableView.setItems(CustomerDAO.allCustomers());
                        }    
                    }
                    existingAppointments = false;
                    
                    if (existingAppointments == false){
                        CustomerDAO.deleteCustomer(num);
                        customerTableView.getItems().clear();
                        customerTableView.setItems(CustomerDAO.allCustomers());
                    }
                } else {
                    
                }
            }));    
            }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
  

    /**
     * Initializes the controller class, and customer table view.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               
            CustomerDAO.allCustomers().clear();
            
            
            customerTableView.setItems(CustomerDAO.allCustomers());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            customerDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        
        

    }    
    
}
