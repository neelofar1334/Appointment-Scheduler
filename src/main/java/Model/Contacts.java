package Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contacts {
    private final SimpleIntegerProperty contactId;
    private final SimpleStringProperty contactName;
    private final SimpleStringProperty contactEmail;

    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = new SimpleIntegerProperty(contactId);
        this.contactName = new SimpleStringProperty(contactName);
        this.contactEmail = new SimpleStringProperty(contactEmail);
    }

    public int getContactId() {
        return contactId.get();
    }

    public void setContactId(int contactId) {
        this.contactId.set(contactId);
    }

    public String getContactName() {
        return contactName.get();
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public String getContactEmail() {
        return contactEmail.get();
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail.set(contactEmail);
    }
}
