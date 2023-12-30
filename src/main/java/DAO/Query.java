package DAO;

public class Query {

    //method for checking the username against the password
    public static String checkCredentials() {
        return "SELECT Password FROM users WHERE User_Name = ?";
    }
}
