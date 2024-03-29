package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a country for the combobox.
 * Stores information about a country incl. its ID and name.
 */
public class Countries {

    private final SimpleIntegerProperty countryId;
    private final SimpleStringProperty countryName;


    public Countries(int countryId, String countryName) {
        this.countryId = new SimpleIntegerProperty(countryId);
        this.countryName = new SimpleStringProperty(countryName);
    }

    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    public String getCountryName() {
        return countryName.get();
    }

    public void setCountryName(String countryName) {
        this.countryName.set(countryName);
    }

    public String toString() {
        return getCountryName();
    }
}
