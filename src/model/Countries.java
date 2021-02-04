/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author jeffd
 */
public class Countries {
    
    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private Timestamp Last_Update;
    private String Last_Updated_By;

    public Countries() {
    }

    public Countries(int Country_ID, String Country, LocalDateTime Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By) {
        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int Country_ID) {
        this.Country_ID = Country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
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
    
    @Override public String toString() { return Country; }
    
    
    
}
