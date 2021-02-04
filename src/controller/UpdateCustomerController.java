/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.CountriesDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionsDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

/**
 * FXML Controller class
 *This is the class that updates the customer.
 * @author jeffd
 */
public class UpdateCustomerController implements Initializable {
    
    @FXML private TextField update_customer_ID;
    @FXML private TextField update_customer_Name;
    @FXML private TextField update_customer_Address;
    @FXML private TextField update_customer_Postalcode;
    @FXML private TextField update_customer_Phone;
    @FXML public ComboBox<Countries> customer_CountryBox;
    @FXML public ComboBox<FirstLevelDivisions> customer_StateBox;
    ObservableList<Customer> customer = FXCollections.observableArrayList();
    ObservableList <FirstLevelDivisions> firstLevelDivisions = FirstLevelDivisionsDAO.getAllFirstLevelDivisions();
    ObservableList <Countries> countries = CountriesDAO.getCountries();
    
    /**
     * This button will exit the update customer page and return to the main customer page.
     * @param event This will change the stage to the customer main page.
     * @throws IOException 
     */
    public void cancelButton(ActionEvent event) throws IOException
    {
        Parent updateAppointmentParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
        Scene updateAppointmentScene = new Scene(updateAppointmentParent);

        //This line gets the Stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(updateAppointmentScene);
        window.centerOnScreen();
        window.show();
    }
    /**
     * This sends the customer data and populates the form fields with the existing customer data.
     * @param selectedCustomer This is the selected customer.
     */
    public void sendCustomerData(Customer selectedCustomer)
    {
        try{

            update_customer_ID.setText(String.valueOf(selectedCustomer.getCustomer_ID()));
            update_customer_Name.setText(selectedCustomer.getCustomer_Name());
            update_customer_Address.setText(selectedCustomer.getAddress());
            update_customer_Postalcode.setText(selectedCustomer.getPostal_Code());
            update_customer_Phone.setText(selectedCustomer.getPhone());
            int div = selectedCustomer.getDivision_ID();
            for(FirstLevelDivisions firstLevelDivisions : customer_StateBox.getItems())
            {
                if(firstLevelDivisions.getDivision_ID() == div)
                {
                    customer_StateBox.setValue(firstLevelDivisions);
                }
            }
            
            
            customer_CountryBox.setItems(countries);
            int country_ID = FirstLevelDivisionsDAO.countryByDivision(div);
            for(Countries country : customer_CountryBox.getItems())
            {
                if(country.getCountry_ID() == country_ID)
                {
                    customer_CountryBox.setValue(country);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
               
    }
    /**
     * This will take the modified data from the form fields and send it to the database.
     * @param event This updates the customer records.
     */
    public void updateCustomerButton(ActionEvent event)
    {
        try{
            int id = Integer.parseInt(update_customer_ID.getText());
            String name = update_customer_Name.getText();
            String address = update_customer_Address.getText();
            String postalcode = update_customer_Postalcode.getText();
            String phone = update_customer_Phone.getText();
            //String create_date = UserDAO.currentuser().get(0).getUser_Name();
            String created_By = UserDAO.currentuser.get(0).getUser_Name();
            //String last_Update = null;
            String last_Updated_By = UserDAO.currentuser.get(0).getUser_Name();
            Countries country = customer_CountryBox.getValue();
            FirstLevelDivisions divs =  customer_StateBox.getValue();
            
            String invalidInputAlert = "";
            boolean error = true;
 
            if (name.length() == 0)
            {
                invalidInputAlert += "You must enter a customer name. \n";
                error = false;
            }
            if (name.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            if (address.length() == 0)
            {
                invalidInputAlert += "You must enter a customer Address. \n";
                error = false;
            }
            if (address.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            if (postalcode.length() == 0)
            {
                invalidInputAlert += "You must enter a customer postalcode. \n";
                error = false;
            }
            if (postalcode.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
             if (phone.length() == 0)
            {
                invalidInputAlert += "You must enter a customer phone. \n";
                error = false;
            }
            if (phone.length() > 50)
            {
                invalidInputAlert += "Exceeded 50 character limit! \n";
                error = false;
            }
            
            boolean isCountryBoxEmpty = customer_CountryBox.getSelectionModel().isEmpty();
      
            if (isCountryBoxEmpty == true)
            {
                invalidInputAlert += "Please select a Country \n";
                error = false;
            }
 
            boolean isStateBoxEmpty = customer_StateBox.getSelectionModel().isEmpty();
            
            if (isStateBoxEmpty == true)
            {
                invalidInputAlert += "Please select a State/Province \n";
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
                int div = divs.getDivision_ID();
                CustomerDAO.updateCustomer( id, name, address, postalcode, phone, created_By, last_Updated_By, div);

                firstLevelDivisions.clear();
                countries.clear();
                
                Parent updateAppointmentParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
                Scene updateAppointmentScene = new Scene(updateAppointmentParent);

                //This line gets the Stage info
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(updateAppointmentScene);
                window.centerOnScreen();
                window.show();
            }    
        } 
        catch (Exception e) 
        {
             e.printStackTrace();
        }
    }
    /**
     * This handles the country and division combo boxes.
     * @param event This sorts the first level division box based on the country box.
     */
    @FXML void selectedCusCountry(ActionEvent event)
    {
        try
        {
            if(customer_CountryBox.getValue() != null)
            {
                int selectedCountry = customer_CountryBox.getValue().getCountry_ID();
                FirstLevelDivisionsDAO.divisionsByCountry.clear();
                customer_StateBox.setItems(null);
                ObservableList <FirstLevelDivisions> firstLevelDivisions = FirstLevelDivisionsDAO.divisionsByCountry(selectedCountry);
                customer_StateBox.setItems(firstLevelDivisions); 
            }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class, table view, and combo boxes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customer_CountryBox.setItems(countries);
        customer_StateBox.setItems(firstLevelDivisions);
      
    }    
    
}
