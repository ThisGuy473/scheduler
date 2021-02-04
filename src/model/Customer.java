/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DAO.CustomerDAO;
import java.security.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jeffd
 */
public class Customer{
    
    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
    
    public Customer() {}
    //public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, LocalDateTime Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By, int Division_ID)
    public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        //this.Create_Date = Create_Date;
        //this.Created_By = Created_By;
        //this.Last_Update = Last_Update;
        //this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;
    }
    
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
        
    }
 
    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID = Customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String Customer_Name) {
        this.Customer_Name = Customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String Postal_Code) {
        this.Postal_Code = Postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDateTime Create_Date) {
        this.Create_Date = Create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String Created_By) {
        this.Created_By = Created_By;
    }

    public Timestamp getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(Timestamp Last_Update) {
        this.Last_Update = Last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String Last_Updated_By) {
        this.Last_Updated_By = Last_Updated_By;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int Division_ID) {
        this.Division_ID = Division_ID;
    }
    
    @Override public String toString() { return Customer_Name; }
      
}
