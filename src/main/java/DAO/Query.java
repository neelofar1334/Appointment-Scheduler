package DAO;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;


public class Query {

    //method for checking the username against the password for the LogInController
    public static String checkCredentials() {
        return "SELECT Password FROM users WHERE User_Name = ?";
    }

    //method for loading the Appointments TableView for the AppointmentController
    public static ObservableList<Appointments> getAllAppointments() {

        ObservableList<Appointments> appointments = FXCollections.observableArrayList(); //hold all appt objects

        try
            (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments");
             ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");
                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");

                    Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                    appointments.add(appointment);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return appointments;
        }

    /**
     * method for loading all customers
     * @return
     */
    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customers = FXCollections.observableArrayList(); //hold all customer objects

        String sql = "SELECT customers.*, first_level_divisions.Division, countries.Country, countries.Country_ID " +
                "FROM customers " +
                "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";


        try
                (Connection conn = JDBC.openConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("Country_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");

                Customers customer = new Customers(customerId, custName, custAddress, postalCode, phoneNumber, divisionId, countryId, divisionName, countryName);
                customers.add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //method for loading all countries from database into list
    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        try
                (Connection conn = JDBC.openConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM countries");
                 ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Countries country = new Countries(countryId, countryName);
                allCountries.add(country);

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allCountries;
    }

    //method for getting states/divisions by their IDs for the modifyCustomerController
    public static First_level_divisions getDivisionById(int divisionId) {

        First_level_divisions firstLevelDivisions = null;
        int countryId = -1;

        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT *, Country_ID FROM first_level_divisions WHERE Division_ID = ?")) {
            stmt.setInt(1, divisionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String divisionName = rs.getString("Division");
                    firstLevelDivisions = new First_level_divisions(divisionId, divisionName, countryId);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return firstLevelDivisions;
    }

    //method for getting divisions by countryID
    public static ObservableList<First_level_divisions> getAllDivisionsByCountry(int countryId) {
        ObservableList<First_level_divisions> divisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, countryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                First_level_divisions division = new First_level_divisions(divisionId, divisionName, countryId);
                divisions.add(division);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    //method for adding a customer
    public static boolean addCustomer(String name, String address, String postalCode, String phone, int divisionId) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, postalCode);
            pstmt.setString(4, phone);
            pstmt.setInt(5, divisionId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method for updating a customer
    public static boolean updateCustomer(int customerId, String name, String address, String postalCode, String phone, int divisionId) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, postalCode);
            pstmt.setString(4, phone);
            pstmt.setInt(5, divisionId);
            pstmt.setInt(6, customerId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete appointments for a specific customer
    public static boolean deleteAppointmentsByCustomerId(int customerId) {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method to delete customers without appts
    public static boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        try
                (Connection conn = JDBC.openConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contacts");
                 ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts contacts = new Contacts(contactId, contactName, contactEmail);
                allContacts.add(contacts);

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    // Method to get existing contact name for a specific existing appointment
    public String getContactNameForAppointment(int appointmentId) {

        String contactName = null;
        String sql = "SELECT contacts.Contact_Name " +
                "FROM appointments " +
                "JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE appointments.Appointment_ID = ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId); // Set the appointment ID

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return contactName;
    }

    //method for adding an appt
    public static boolean addAppointment(String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateTimeStr = start.format(formatter);
        String endDateTimeStr = end.format(formatter);

        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, location);
            pstmt.setString(4, type);
            pstmt.setString(5, startDateTimeStr);
            pstmt.setString(6, endDateTimeStr);
            pstmt.setInt(7, customerId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, contactId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //method for adding an appt
    public static boolean updateAppointment(String title, String description, String location, String type,
                                            LocalDateTime start, LocalDateTime end, int customerId, int userId,
                                            int contactId, int appointmentId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateTimeStr = start.format(formatter);
        String endDateTimeStr = end.format(formatter);

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " + // Removed extra parenthesis
                "WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, location);
            pstmt.setString(4, type);
            pstmt.setString(5, startDateTimeStr);
            pstmt.setString(6, endDateTimeStr);
            pstmt.setInt(7, customerId);
            pstmt.setInt(8, userId);
            pstmt.setInt(9, contactId);
            pstmt.setInt(10, appointmentId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    //maps contact name to its ID
    public static int getContactIdByName(String contactName) {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contactName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Contact_ID");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1; // not found indicator
    }

    // Method to delete selected appointment
    public static boolean deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}

