package Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a contact in the app.
 * Stores information about a contact incl. ID, name, and email.
 */
public class Contacts {
    private final SimpleIntegerProperty contactId;
    private final SimpleStringProperty contactName;
    private final SimpleStringProperty contactEmail;

    /**
     * Constructs new contact object with the specified ID, name, and email.
     * @param contactId    The unique identifier for the contact.
     * @param contactName  The name of the contact.
     * @param contactEmail The email address of the contact.
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = new SimpleIntegerProperty(contactId);
        this.contactName = new SimpleStringProperty(contactName);
        this.contactEmail = new SimpleStringProperty(contactEmail);
    }

    /**
     * Getters and setters for contact object.
     * @return
     */
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
