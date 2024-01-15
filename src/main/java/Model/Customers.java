package Model;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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
