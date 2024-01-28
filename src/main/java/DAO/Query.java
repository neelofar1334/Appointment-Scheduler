package DAO;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Contains query methods for log in and customer controllers
 */
public class Query {

    /**
     * Provides the SQL query for checking user credentials.
     * @return SQL query string.
     */
    public static String checkCredentials() {
        return "SELECT Password FROM users WHERE User_Name = ?";
    }

    /**
     * Validates user's login credentials.
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

    /**
     * Gets user details by username.
     * @param username The username to search for.
     * @return User object if found, null otherwise.
     */
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
        }
        return null;
    }

    /**
     * Gets all customers from the database.
     * @return ObservableList of all customers.
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

    /**
     * Gets all countries from the database.
     * @return ObservableList of all countries.
     */
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

    /**
     * Gets a specific division by its ID.
     * @param divisionId The division ID to search for.
     * @return First_level_divisions object if found, null otherwise.
     */
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

    /**
     * Gets all divisions associated with a specific country.
     *
     * @param countryId The ID of the country.
     * @return ObservableList of divisions for the given country.
     */
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

    /**
     * Adds a new customer to the database.
     *
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param divisionId The division ID of the customer.
     * @return True if the customer is successfully added, false otherwise.
     */
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

    /**
     * Updates an existing customer in the database.
     *
     * @param customerId The ID of the customer to update.
     * @param name The new name of the customer.
     * @param address The new address of the customer.
     * @param postalCode The new postal code of the customer.
     * @param phone The new phone number of the customer.
     * @param divisionId The new division ID of the customer.
     * @return True if the customer is successfully updated, false otherwise.
     */
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

    /**
     * Deletes all appointments associated with a specific customer.
     * @param customerId The ID of the customer whose appointments are to be deleted.
     * @return True if appointments are successfully deleted, false otherwise.
     */
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

    /**
     * Deletes a customer from the database.
     * @param customerId The ID of the customer to delete.
     * @return True if the customer is successfully deleted, false otherwise.
     */
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

    /**
     * Gets all contacts from the database.
     * @return ObservableList of all contacts.
     */
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


}

