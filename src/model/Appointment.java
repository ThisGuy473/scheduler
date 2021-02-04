/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *This class manages appointments. 
 * @author jeffd
 */
public class Appointment 
{
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime Close;
    private int Customer_ID;
    private int User_ID;
    private String Contact;
    /**
     * This is an empty appointment constructor
     */
    public Appointment() {}
    /**
     * This is the appointment constructor. This accepts an objects variables.
     * @param Appointment_ID This is a int of appointment if.
     * @param Title This is a String of appointment title.
     * @param Description This is a String of the appointment description.
     * @param Location This is a String of the location.
     * @param Type This is a string of the Type.
     * @param Start This is a LocalDateTime of the Start of the appointment.
     * @param Close This is a LocalDateTime of the Close of the appointment.
     * @param Customer_ID This is an Integer of the customer Id.
     * @param User_ID This is an Integer of the userID.
     * @param Contact This is a String of the Contact.
     */
    public Appointment(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime Close, int Customer_ID, int User_ID, String Contact) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        //this.Created_By = Created_By;
        this.Start = Start;
        this.Close = Close;
        //this.Create_Date = Create_Date;
        //this.Last_Updated_By = Last_Updated_By;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact = Contact;
    }
    /**
     * This is the appointment ID getter.
     * @return int Returns appointment id.
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }
    /**
     * THis is the appointment setter.
     * @param Appointment_ID This is the appointment ID.
     */
    public void setAppointment_ID(int Appointment_ID) {
        this.Appointment_ID = Appointment_ID;
    }
    /**
     * This is appointment title getter
     * @return String Title.
     */
    public String getTitle() {
        return Title;
    }
    /**
     * This is the appointment title setter.
     * @param title This is the title.
     */
    public void setTitle(String title) {
        this.Title = title;
    }
    /**
     * This is the description getter.
     * @return String description.
     */
    public String getDescription() {
        return Description;
    }
    /**
     * This is the appointment setter.
     * @param Description This is the appointment description.
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }
    /**
     * This is the location getter.
     * @return String location.
     */
    public String getLocation() {
        return Location;
    }
    /**
     * This is the location setter.
     * @param Location This is the appointment location. 
     */
    public void setLocation(String Location) {
        this.Location = Location;
    }
    /**
     * This is the appointment type getter.
     * @return String type.
     */
    public String getType() {
        return Type;
    }
    /**
     * This is the appointment type setter.
     * @param Type This is the appointment type.
     */
    public void setType(String Type) {
        this.Type = Type;
    }
    /**
     * This is the appointment Start getter.
     * @return LocalDateTime Start.
     */
    public LocalDateTime getStart() {
        return Start;
    }
    /**
     * This is the appointment Start setter.
     * @param Start This is the appointment start.
     */
    public void setStart(LocalDateTime Start) {
        this.Start = Start;
    }
    /**
     * This is the appointment Close getter.
     * @return LocalDateTime Close.
     */
    public LocalDateTime getClose() {
        return Close;
    }
    /**
     * This is the appointment Close setter.
     * @param Close This is the appointment close.
     */
    public void setClose(LocalDateTime Close) {
        this.Close = Close;
    }
    /**
     * This is the appointment customerID getter.
     * @return int Customer_ID.
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }
    /**
     * This is the appointment customer ID setter.
     * @param Customer_ID This is the appointment customer ID
     */
    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID = Customer_ID;
    }
    /**
     * This is the appointment user ID getter.
     * @return int User_ID.
     */
    public int getUser_ID() {
        return User_ID;
    }
    /**
     * This si the appointment userID setter.
     * @param User_ID This is the appointment user ID. 
     */
    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }
    /**
     * This is the appointment contact id getter.
     * @return int Contact.
     */
    public String getContact_ID() {
        return Contact;
    }
    /**
     * This is the appointment contactID setter.
     * @param Contact This is the appointment contact ID.
     */
    public void setContact_ID(String Contact) {
        this.Contact = Contact;
    }
    /**
     * This is the appointment Contact getter.
     * @return String Contact
     */
     public String getContact() {
        return Contact;
    }
     /**
      * This is the appointment Contact setter.
      * @param Contact This is the Appointment contact.
      */
    public void setContact(String Contact) {
        this.Contact = Contact;
    }
    
    
    
   
}
