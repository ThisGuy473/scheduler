/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jeffd
 */
public class Contact {
    
    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    public Contact() {
    }

    public Contact(int Contact_ID, String Contact_Name, String Email) {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }

    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int Contact_ID) {
        this.Contact_ID = Contact_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String Contact_Name) {
        this.Contact_Name = Contact_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    @Override public String toString() { return Contact_Name; }
    
}
