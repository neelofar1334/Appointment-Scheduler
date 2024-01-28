package Model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a customer entity in the app.
 * Stores info about a customer incl. their ID, name, address, phone number.
 */
public class Customers {

    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty custName;
    private final SimpleStringProperty custAddress;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty phoneNumber;
    private final SimpleIntegerProperty divisionId;
    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty divisionName;
    private final SimpleStringProperty countryName;

    /**
     * Constructs new customer object with these details:
     *
     * @param customerId The ID of the customer.
     * @param custName The name of the customer.
     * @param custAddress The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phoneNumber The phone number of the customer.
     * @param divisionId The ID of the division the customer belongs to.
     * @param countryId The ID of the country the customer belongs to.
     * @param divisionName The name of the division the customer belongs to.
     * @param countryName The name of the country the customer belongs to.
     */
    public Customers(int customerId, String custName, String custAddress, String postalCode, String phoneNumber, int divisionId, int countryId, String divisionName, String countryName) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.custName = new SimpleStringProperty(custName);
        this.custAddress = new SimpleStringProperty(custAddress);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.divisionId = new SimpleIntegerProperty(divisionId);
        this.countryId = new SimpleIntegerProperty(countryId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.countryName = new SimpleStringProperty(countryName);
    }

    /**
     * Getters and setters for customer object.
     * @return
     */
    public int getCustomerId() {
        return customerId.get();
    }

    public ReadOnlyIntegerProperty customerIdProperty() {
        return customerId;
    }

    public String getCustName() {
        return custName.get();
    }

    public void setCustName(String Title) {
        this.custName.set(Title);
    }

    public String getCustAddress() {
        return custAddress.get();
    }

    public void setCustAddress(String custAddress) {
        this.custAddress.set(custAddress);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public int getDivisionId() {
        return divisionId.get();
    }

    public void setDivisionId(int divisionId) {
        this.divisionId.set(divisionId);
    }


    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public void setDivisionName(String divisionName) {
        this.divisionName.set(divisionName);
    }

    public String getCountryName() {
        return countryName.get();
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

}
