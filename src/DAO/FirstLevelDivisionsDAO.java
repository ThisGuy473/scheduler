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
import model.FirstLevelDivisions;
import model.User;
import utils.DBConnection;

/**
 *
 * @author jeffd
 */
public class FirstLevelDivisionsDAO {
    
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions = FXCollections.observableArrayList();
    public static ObservableList<FirstLevelDivisions> divisionsByCountry = FXCollections.observableArrayList();
    private final static Connection conn = DBConnection.conn;
    
    
    public static ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions()
    {
        try{
            String getAllCustomerDivision = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = conn.prepareStatement(getAllCustomerDivision);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
               FirstLevelDivisions division = new FirstLevelDivisions();
               division.setDivision_ID(rs.getInt("Division_ID"));
               division.setDivision(rs.getString("Division"));
               getAllFirstLevelDivisions.add(division);  
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return getAllFirstLevelDivisions;
    }
    
    public static ObservableList<FirstLevelDivisions> divisionsByCountry(int selectedCountry)
    {
        try
        {
            String divisionByCountry = "SELECT * FROM first_level_divisions WHERE country_ID = '" + selectedCountry + "'";
            PreparedStatement ps = conn.prepareStatement(divisionByCountry);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                FirstLevelDivisions division = new FirstLevelDivisions();
                division.setDivision(rs.getString("Division"));
                division.setDivision_ID(rs.getInt("Division_ID"));
                division.setCountry_ID(rs.getInt("Country_ID"));
                divisionsByCountry.add(division);  
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return divisionsByCountry;
    }
    
    public static int countryByDivision(int selectedDivision)
    {
        try
        {
            String countryByDivision = "SELECT Country_ID FROM first_level_divisions WHERE division_ID ='"+selectedDivision+"'";
            PreparedStatement ps = conn.prepareStatement(countryByDivision);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int countryByDivisionInt = rs.getInt("Country_ID");
                return countryByDivisionInt;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    
}
