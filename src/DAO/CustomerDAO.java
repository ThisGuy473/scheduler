/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.CountriesDAO.allCountries;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Customer;
import utils.DBConnection;

/**
 *This class handles all CRUD operations for the customer class.
 * @author jeffd
 */
public class CustomerDAO {
    
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private final static Connection conn = DBConnection.conn;
    /**
     * THis method will get all customers from the database
     * @return allCustomers this will return all customers from the database
     * @throws SQLException 
     */
    public static ObservableList<Customer> getCustomer() throws SQLException
    {
        String getCustomer = "SELECT * FROM customers";
        PreparedStatement ps = conn.prepareStatement(getCustomer);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
           Customer customer = new Customer();
           customer.setCustomer_ID(rs.getInt("Customer_ID"));
           customer.setCustomer_Name(rs.getString("Customer_Name"));
           allCustomers.add(customer);  
        }
        
        
        return allCustomers;
    }
    /**
     * This will get the division id for the customers from the database.
     * @return allCustomers
     * @throws SQLException 
     */
    public static ObservableList<Customer> getCustomerIDs() throws SQLException
    {
        String getCustomer = "SELECT Division_ID FROM customers";
        PreparedStatement ps = conn.prepareStatement(getCustomer);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
           Customer customer = new Customer();
           customer.setDivision_ID(rs.getInt("Division_ID"));
           allCustomers.add(customer);  
        }
        
        
        return allCustomers;
    }
    /**
     * This retrieves all customers from the database
     * @return allCustomers
     */
    public static ObservableList<Customer> allCustomers()
    {
        try{
            String getCustomers = "SELECT * FROM customers";
            PreparedStatement ps = conn.prepareStatement(getCustomers);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Customer customer = new Customer();
                customer.setCustomer_ID(rs.getInt("Customer_ID"));
                customer.setCustomer_Name(rs.getString("Customer_Name"));
                customer.setAddress(rs.getString("Address"));
                customer.setPostal_Code(rs.getString("Postal_Code"));
                customer.setPhone(rs.getString("Phone"));
                customer.setDivision_ID(rs.getInt("Division_ID"));
                allCustomers.add(customer);
                
            }
        }
        catch (Exception e)
        {
           e.printStackTrace(); 
        }
        
        return allCustomers;
    }
    /**
     * This method adds new customers to the database.
     * @param Customer_Name this is the customer name field
     * @param Address this is the customer address field
     * @param Postal_Code this is the customer postal code
     * @param Phone this is the customer phone
     * @param Created_By this is the user that created the entry
     * @param Last_Updated_By this is who last updated the customer
     * @param Div_ID this is the division id for the customer
     * @throws SQLException 
     */
    public static void newCustomer( String Customer_Name, String Address, String Postal_Code, String Phone, String Created_By, String Last_Updated_By, int Div_ID) throws SQLException
    {
        String newCustomer = "INSERT INTO customers (Customer_Name, Address, Postal_code, Phone, Created_By, Last_Updated_By, Division_ID)" + "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(newCustomer);

            ps.setString(1, Customer_Name);
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setString(5, Created_By);
            ps.setString(6, Last_Updated_By);
            ps.setInt(7, Div_ID);
            ps.execute();

    }
    /**
     * This method will update the selected customer
     * @param Customer_ID this is the customer id field
     * @param Customer_Name this is the customer name field 
     * @param Address this is the customer address field
     * @param Postal_Code this is the customers updated postal code
     * @param Phone this is the customers phone
     * @param Created_By this is who created the customer
     * @param Last_Updated_By this is who last updated the customer
     * @param Div_ID this is the customers division id
     * @throws SQLException 
     */
    public static void updateCustomer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, String Created_By, String Last_Updated_By, int Div_ID) throws SQLException
    {
        String newCustomer = "UPDATE customers SET Customer_Name=?, Address=?, Postal_code=?, Phone=?, Created_By=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID='"+Customer_ID+"'";
        PreparedStatement ps = conn.prepareStatement(newCustomer);

            ps.setString(1, Customer_Name);
            ps.setString(2, Address);
            ps.setString(3, Postal_Code);
            ps.setString(4, Phone);
            ps.setString(5, Created_By);
            ps.setString(6, Last_Updated_By);
            ps.setInt(7, Div_ID);
            ps.execute();

    }
    /**
     * This handles the deleting of the customer.
     * @param Customer_ID this is the ID used to search the database
     */
    public static void deleteCustomer(int Customer_ID)
    {
        try{    
            String deleteCustomer = "DELETE FROM customers WHERE Customer_Id ='"+Customer_ID+"'";
            PreparedStatement ps = conn.prepareStatement(deleteCustomer);
            ps.execute(deleteCustomer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }    

    }
    /**
     * This will get the Customers name based on their ID
     * @param customer_ID this is the Id used to search the database
     * @return customer
     */ 
    public static String getCustomerName(int customer_ID)
    {
        try{
            String getCustomerName =  "SELECT Contact_Name FROM customers WHERE Customer_ID ='"+customer_ID+ "'";
            PreparedStatement ps = conn.prepareStatement(getCustomerName);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String customer = rs.getString("Customer_Name");
                return customer;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This method checks if the customer has an appointment using the customer ID
     * @param customer_ID this is the ID used to search the database
     * @return true, false
     */
    public static boolean hasAppointment(int customer_ID)
    {
        try{
            String hasAppointments = "SELECT Customer_ID FROM appointments WHERE Customer_ID = '"+customer_ID+"'";
            PreparedStatement ps = conn.prepareStatement( hasAppointments);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                if(rs.getInt("Customer_ID") == customer_ID)
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
//    public static ObservableList<Customer> allCustomersByDivision()
//    {
//        try{
//            String getCustomers = "SELECT count(*) from  WHERE Division_ID="+divID;;
//            PreparedStatement ps = conn.prepareStatement(getCustomers);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//            {
//                Customer customer = new Customer();
//                customer.setCustomer_ID(rs.getInt("Customer_ID"));
//                customer.setCustomer_Name(rs.getString("Customer_Name"));
//                customer.setAddress(rs.getString("Address"));
//                //customer.setCusState(FirstLevelDivisionDAO.FLDName(rs.getInt("Division_Id")));
//                //customer.setCusCountry(CountriesDAO.getCountryName(rs.getInt("Division_Id")));
//                customer.setPostal_Code(rs.getString("Postal_Code"));
//                customer.setPhone(rs.getString("Phone"));
//                customer.setDivision_ID(rs.getInt("Division_ID"));
//                allCustomers.add(customer);
//                
//            }
//        }
//        catch (Exception e)
//        {
//           e.printStackTrace(); 
//        }
//        
//        return allCustomers;
//    }
    
}
