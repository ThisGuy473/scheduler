/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.UserDAO.currentuser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;
import model.User;
import utils.DBConnection;

/**
 *
 * @author jeffd
 */
public class CountriesDAO {
    
    public static ObservableList<Countries> allCountries = FXCollections.observableArrayList();
    public static ObservableList<FirstLevelDivisions> allDiv = FirstLevelDivisionsDAO.getAllFirstLevelDivisions();
    
    private final static Connection conn = DBConnection.conn;
    
    public static ObservableList<Countries> getCountries()
    {
        try{    
            String getCustomerCountry = "SELECT * FROM countries";
            PreparedStatement ps = conn.prepareStatement(getCustomerCountry);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
               Countries country = new Countries();
               country.setCountry_ID(rs.getInt("Country_ID"));
               country.setCountry(rs.getString("Country"));
               allCountries.add(country);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return allCountries;
    }
    
    public static int getCountryID(String country_ID)
    {
        try
        {
            String countryID = "SELECT Country_ID FROM countries WHERE Country_ID=" +country_ID;
            PreparedStatement ps = conn.prepareStatement(countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                return rs.getInt("Country_ID");
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return 0;
    }
    
//    public static int getCustomersByCountry(int countryID)
//    {
//        try{
//            //FirstLevelDivisions div = FirstLevelDivisionsDAO.divisionsByCountry(countryID);
//            int getDivs = getDiv.divisionsByCountry(countryID);
//            String getCustomerCountry = "SELECT count(*) from  WHERE Country_ID="+countryID;
//            PreparedStatement ps = conn.prepareStatement(getCustomerCountry);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//            {
//               return rs.getInt("count(*)");
//                
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        return 0;
//    }
}