/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.CustomerDAO.allCustomers;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import utils.DBConnection;

/**
 *This class handles and the Appointment CRUD operations
 * @author jeffd
 */
public class AppointmentDAO {
    
    private final static Connection conn = DBConnection.conn;
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    //public static ObservableList<Appointment> dateRangeAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static ObservableList<String> allAppointmentTypes = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    
    /**
     * This will return all the appointments that are in the Database
     * @return allAppointments
     */
    public static ObservableList<Appointment> allAppointments()
    {
        try{
            String getAppointments = "SELECT * FROM appointments";
            PreparedStatement ps = conn.prepareStatement(getAppointments);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                allAppointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allAppointments;
    }
    /**
     * This will store/make the new appointment in the database.
     * @param appointment_title this is the title for the appointment.
     * @param appointment_description this is the description for the appointment.
     * @param appointment_location this is the location for the appointment.
     * @param appointment_type this is the type of appointment.
     * @param appointment_start this finds the appointment start date.
     * @param appointment_end this finds the appointment end date.
     * @param created_by this is who the appointment was created by.
     * @param last_Updated_By this is who last updated the appointment.
     * @param appointment_customer this is the customer who the appointment is for.
     * @param appointment_user this is the user 
     * @param appointment_contact this is the contact for the appointment
     */
    public static void newAppointment( String appointment_title, String appointment_description, String appointment_location, String appointment_type, LocalDateTime appointment_start, LocalDateTime appointment_end, String created_by, String last_Updated_By, int appointment_customer, int appointment_user, int appointment_contact)
    {
       try{
            String newAppointment = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(newAppointment);

                ps.setString(1, appointment_title);
                ps.setString(2, appointment_description);
                ps.setString(3, appointment_location);
                ps.setString(4, appointment_type);
                ps.setTimestamp(5, Timestamp.valueOf(appointment_start));
                ps.setTimestamp(6, Timestamp.valueOf(appointment_end));
                ps.setString(7, created_by);
                ps.setString(8, last_Updated_By);
                ps.setInt(9, appointment_customer);
                ps.setInt(10, appointment_user);
                ps.setInt(11, appointment_contact);
                ps.execute();
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
    }
    /**
     * This will update an already existing appointment based on the selected item from the table view
     * @param appointment_ID this is the auto generated appointment_ID
     * @param appointment_title this is the title of the appointment
     * @param appointment_description this is the description of the appointment
     * @param appointment_location this is the location of the appointment
     * @param appointment_type this is the type of appointment 
     * @param appointment_start this is the start date and time of the appointment
     * @param appointment_end this is the end date and time of the appointment
     * @param created_by this is who created the appointment
     * @param last_Updated_By this is who last updated the appointment
     * @param appointment_customer this is the customer who the appointment is for
     * @param appointment_user this is the user who logs in
     * @param appointment_contact This is the contact for the appointment
     */
    public static void updateAppointment( int appointment_ID, String appointment_title, String appointment_description, String appointment_location, String appointment_type, LocalDateTime appointment_start, LocalDateTime appointment_end, String created_by, String last_Updated_By, int appointment_customer, int appointment_user, int appointment_contact)
    {
        try{
            String newAppointment = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Created_By=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID ="+appointment_ID;
            PreparedStatement ps = conn.prepareStatement(newAppointment);
                
                ps.setString(1, appointment_title);
                ps.setString(2, appointment_description);
                ps.setString(3, appointment_location);
                ps.setString(4, appointment_type);
                ps.setTimestamp(5, Timestamp.valueOf(appointment_start));
                ps.setTimestamp(6, Timestamp.valueOf(appointment_end));
                ps.setString(7, created_by);
                ps.setString(8, last_Updated_By);
                ps.setInt(9, appointment_customer);
                ps.setInt(10, appointment_user);
                ps.setInt(11, appointment_contact);
                ps.execute();
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    /**
     * This will delete the selected appointment based on appointmentID and will remove it from the database
     * @param appointment_ID 
     */
    public static void deleteAppointment(int appointment_ID)
    {
        try{    
            String deleteAppointment = "DELETE FROM appointments WHERE Appointment_Id ='"+appointment_ID+"'";
            PreparedStatement ps = conn.prepareStatement(deleteAppointment);
            ps.execute(deleteAppointment);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }    

    }
    
    public static int overlapsAppointment(LocalDateTime newstart, LocalDateTime newend, int customer_id)
    {
        try{
            ZoneId localZoneId = ZoneId.systemDefault();

            LocalTime businessStartTime = LocalTime.of(8, 00);
            LocalTime businessEndTime = LocalTime.of(22, 00);

            ZonedDateTime appointmentStartDateTime = newstart.atZone(localZoneId).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime appointmentEndDateTime = newend.atZone(localZoneId).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime businessStartDateTime = ZonedDateTime.of(newstart, ZoneId.of("UTC"));
            ZonedDateTime businessEndDateTime = ZonedDateTime.of(newend, ZoneId.of("UTC"));

            LocalDateTime businessStart = businessStartDateTime.toLocalDateTime();
            LocalDateTime appointmentStart = appointmentStartDateTime.toLocalDateTime();
            LocalDateTime businessEnd = businessEndDateTime.toLocalDateTime();
            LocalDateTime appointmentEnd = appointmentEndDateTime.toLocalDateTime();
            
            Timestamp S1 = Timestamp.valueOf(appointmentStart);
            Timestamp E1 = Timestamp.valueOf(appointmentEnd);
            String findOverlap = "SELECT COUNT(*) FROM appointments WHERE Customer_ID="+customer_id+" and (End >= '"+S1+"' AND Start <= '"+E1+"')";                                                                                 
            PreparedStatement ps = conn.prepareStatement(findOverlap);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int numOfAppts = rs.getInt("count(*)");
                return numOfAppts;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * This will find all appointments within a given date time range
     * @param start this is the start of the range
     * @param end this is the end of the range
     * @return all Appointments in between
     */
    public static ObservableList<Appointment> getDateRange(LocalDateTime start, LocalDateTime end)
    {
        try{
            Timestamp startDateTime = Timestamp.valueOf(start);
            Timestamp endDateTime = Timestamp.valueOf(end);
            String getDate = "SELECT * FROM appointments WHERE Start >= '"+startDateTime+"' and end <= '"+endDateTime+"'";
            PreparedStatement ps = conn.prepareStatement(getDate);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
               
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Appointment appointment = new Appointment();
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                allAppointments.add(appointment);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allAppointments;
    }
    /**
     * This will get all appointments by contact
     * @param contact_ID this will query the database using the contact_ID
     * @return appointments
     */ 
    public static ObservableList<Appointment> appointmentsByContact(int contact_ID)
    {
        try{
            String getAppointmentsByContact = "SELECT * FROM appointments WHERE Contact_Id ="+contact_ID;
            PreparedStatement ps = conn.prepareStatement(getAppointmentsByContact);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return appointments;
    }
    /**
     * This will get all appointments by type
     * @return allAppointmentTypes
     */
    public static ObservableList<String> getAppointmentTypes()
    {
        try
        {
            String appointmentType = "SELECT DISTINCT Type FROM appointments";
            PreparedStatement ps = conn.prepareStatement(appointmentType);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
               String type = new String();
               type = rs.getString("Type");
               allAppointmentTypes.add(type);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return allAppointmentTypes;
    }
    /**
     * This will get the monthly report based on appointments per month
     * @param appointmentType this uses the appointment types
     * @param start uses the start localDateTime to find the appointments per month
     * @param end this is the end date to find appointments per month
     * @return typeByMonth
     */  
    public static int getMonthlyReport(String appointmentType, LocalDateTime start, LocalDateTime end)
    {
        Timestamp startValue = Timestamp.valueOf(start);
        Timestamp endValue = Timestamp.valueOf(end);
        try
        {
            String getAppointmentCount = "SELECT COUNT(*) FROM appointments WHERE Type ='"+appointmentType+"'AND Start >='"+startValue+"'AND End <='"+endValue+"'";
            PreparedStatement ps = conn.prepareStatement(getAppointmentCount);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int typeByMonth = rs.getInt("count(*)");
                return typeByMonth;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }  
    /**
     * gets the count of appointments by customer 
     * @param customer_ID uses the customer ID to find appointments
     * @return appointments
     */
    public static int getCountOfAppointmentsByCustomer(int customer_ID)
    {
        try
        {
            String getAppointmentCount = "SELECT count(*) from appointments where Customer_ID="+customer_ID;
            PreparedStatement ps = conn.prepareStatement(getAppointmentCount);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int appts = rs.getInt("count(*)");
                return appts;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }  
    /**
     * This finds appointments by customer.
     * @param customer_ID this uses customer_ID to find appointments using the customer
     * @return allAppointments
     */
    public static ObservableList<Appointment> appointmentsByCustomer(int customer_ID)
    {
        try{
            String getAppointmentsByCustomer = "SELECT * FROM appointments WHERE Customer_Id="+customer_ID;
            PreparedStatement ps = conn.prepareStatement(getAppointmentsByCustomer);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                allAppointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allAppointments;
    }
    /**
     * This is where we get all the appointments a user has.
     * @param user_ID this is the user ID 
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAppointmentByUser(int user_ID)
    {
        try{
            String getAppointmentsByUser = "SELECT * FROM appointments WHERE USER_Id ="+user_ID;
            PreparedStatement ps = conn.prepareStatement(getAppointmentsByUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                allAppointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allAppointments;
    }
    
    
    /**
     * This gets all appointments by month
     * @return monthAppointments
     */
    public static ObservableList<Appointment> getAppointmentsMonth()
    {
        try{
            String getAppointmentMonth = "select * from appointments WHERE Start >= LAST_DAY(CURDATE()) + INTERVAL 1 DAY - INTERVAL 1 MONTH AND Start <= LAST_DAY(CURDATE()) + INTERVAL 1 DAY + INTERVAL 7 HOUR";
            PreparedStatement ps = conn.prepareStatement(getAppointmentMonth);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                monthAppointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return monthAppointments;
    }
    /**
     * This gets all the appointments for a week
     * @return weekAppointments
     */
    public static ObservableList<Appointment> getAppointmentsWeek()
    {
        try{
            String getAppointmentsByWeek = "select * from appointments WHERE Start >= curdate() AND Start <= curdate() + INTERVAL 7 DAY";
            PreparedStatement ps = conn.prepareStatement(getAppointmentsByWeek);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Appointment appointment = new Appointment();
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                appointment.setAppointment_ID(rs.getInt("Appointment_ID"));
                appointment.setTitle(rs.getString("Title"));
                appointment.setDescription(rs.getString("Description"));
                appointment.setLocation(rs.getString("Location"));
                appointment.setType(rs.getString("Type"));
                appointment.setStart(startDate.toLocalDateTime());
                appointment.setClose(endDate.toLocalDateTime());
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setContact(ContactDAO.getContactName(rs.getInt("Contact_ID")));
                appointment.setUser_ID(rs.getInt("User_ID"));
                appointment.setCustomer_ID(rs.getInt("Customer_ID"));
                weekAppointments.add(appointment);
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return weekAppointments;
    }
}
