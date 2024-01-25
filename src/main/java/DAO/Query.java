package DAO;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class Query {

    //method for checking the username against the password for the LogInController
    public static String checkCredentials() {
        return "SELECT Password FROM users WHERE User_Name = ?";
    }

    /**
     * Validates user's credentials.
     *
     * @param userName the username in the database
     * @param password the password in the database
     * @return true if the credentials are valid, false otherwise
     */

    public static boolean isValidLogin(String userName, String password) {

        Connection conn = null;

        try {
            // connect to database
            conn = JDBC.openConnection();

            //use checkCredentials query from Query class
            PreparedStatement pstmt = conn.prepareStatement(Query.checkCredentials());
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                // Check password
                String retrievedPassword = rs.getString("Password");
                if (retrievedPassword != null && retrievedPassword.equals(password)) {
                    return true; // The password matches
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //prints stack trace of SQLException
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            JDBC.closeConnection(conn);
        }
        return false;
    }

    // Method to get user details by username
    public static Users getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE User_Name = ?";

        try (Connection conn = JDBC.openConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("User_ID");
                    String foundUsername = rs.getString("User_Name");
                    String password = rs.getString("Password"); // Handle with care, consider security implications

                    return new Users(userId, foundUsername, password);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle exception
        }
        return null; // Return null if user is not found or in case of an exception
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

    public static ObservableList<ReportAppointment> getTotalAppointmentsByTypeAndMonth() {
        ObservableList<ReportAppointment> reportData = FXCollections.observableArrayList();

        String query = "SELECT COUNT(*), Type, MONTH(Start) as Month FROM appointments GROUP BY Type, MONTH(Start);";
        try (Connection conn = JDBC.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("Type");
                int month = rs.getInt("Month");
                int count = rs.getInt(1); // COUNT(*) is the first column
                reportData.add(new ReportAppointment(type, month, count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reportData;
    }


}

