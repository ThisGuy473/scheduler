/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.AppointmentDAO.allAppointments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import utils.DBConnection;

/**
 *This class handles all CRUD operations for the Contact class.
 * @author jeffd
 */
public class ContactDAO {
    
    private final static Connection conn = DBConnection.conn;
    public static ObservableList<Contact> Contacts = FXCollections.observableArrayList();
    /**
     * This gets all the contacts from the database.
     * @return Contacts.
     */
    public static ObservableList<Contact> allContacts()
    {
        try{
            String getContacts = "SELECT * FROM contacts";
            PreparedStatement ps = conn.prepareStatement(getContacts);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Contact getContact = new Contact();
                getContact.setContact_Name(rs.getString("Contact_Name"));
                getContact.setContact_ID(rs.getInt("Contact_ID"));
                Contacts.add(getContact);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Contacts;
    }
    
    /**
     * This will get the contact name from the database using the contactID.
     * @param contact_ID This is the contacts contact ID
     * @return contact.
     */
    public static String getContactName(int contact_ID)
    {
        try{
            String getContactName =  "SELECT Contact_Name FROM contacts WHERE Contact_ID ='"+contact_ID+ "'";
            PreparedStatement ps = conn.prepareStatement(getContactName);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                String contact = rs.getString("Contact_Name");
                return contact;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getContactID(String contact_name)
    {
        try{
            String getContactID =  "SELECT Contact_ID FROM contacts WHERE Contact_Name ='"+contact_name+ "'";
            PreparedStatement ps = conn.prepareStatement(getContactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int contactID = rs.getInt("Contact_ID");
                return contactID;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
//    public static String getContact(String contactName)
//    {
//        try{
//            String getContactName =  "select * from contacts where Contact_Name='"+contactName+"'";
//            PreparedStatement ps = conn.prepareStatement(getContactName);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//            {
//                String contact = rs.getString("Contact_Name");
//                return contact;
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
    /**
     * This gets the contact ID for the selected parameter.
     * @param desiredContact
     * @return getContact_ID.
     */
    public static int getContactID(Contact desiredContact)
    {
        try{
            String getContactID = "SELECT Contact_ID FROM contacts WHERE Contact_Name ='"+desiredContact+ "'";
            PreparedStatement ps = conn.prepareStatement(getContactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int getContact_ID = rs.getInt("Contact_ID");
                return getContact_ID;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
}
