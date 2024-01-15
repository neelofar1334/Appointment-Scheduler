package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class First_level_divisions {

    private final SimpleIntegerProperty divisionId;
    private final SimpleStringProperty divisionName;
    private final SimpleIntegerProperty countryId;


    public First_level_divisions(int divisionId, String divisionName, int countryId) {
        this.divisionId = new SimpleIntegerProperty(divisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.countryId = new SimpleIntegerProperty(countryId);
    }

    public int getDivisionId() {
        return divisionId.get();
    }

    public void setDivisionId(int divisionId) {
        this.divisionId.set(divisionId);
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public void setDivisionName(String divisionName) {
        this.divisionName.set(divisionName);
    }

    public int getCountryId() {
        return countryId.get();
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    //method to display division/state names as text for combo box
    public String toString() {
        return getDivisionName();
    }
}
