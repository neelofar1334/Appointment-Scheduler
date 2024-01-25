package Model;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;

public class Appointments {

    private final SimpleIntegerProperty appointmentId;
    private final SimpleStringProperty Title;
    private final SimpleStringProperty Description;
    private final SimpleStringProperty Location;
    private final SimpleStringProperty Type;
    private final SimpleObjectProperty<LocalDateTime> Start;
    private final SimpleObjectProperty<LocalDateTime> End;
    private final SimpleIntegerProperty Customer_ID;
    private final SimpleIntegerProperty User_ID;
    private final SimpleIntegerProperty Contact_ID;

    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.Title = new SimpleStringProperty(title);
        this.Description = new SimpleStringProperty(description);
        this.Location = new SimpleStringProperty(location);
        this.Type = new SimpleStringProperty(type);
        this.Start = new SimpleObjectProperty<>(start);
        this.End = new SimpleObjectProperty<>(end);
        this.Customer_ID = new SimpleIntegerProperty(customerId);
        this.User_ID = new SimpleIntegerProperty(userId);
        this.Contact_ID = new SimpleIntegerProperty(contactId);
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public ReadOnlyIntegerProperty appointmentIdProperty() {
        return appointmentId;
    }


    public String getTitle() {
        return Title.get();
    }

    public void setTitle(String Title) {
        this.Title.set(Title);
    }

    public String getDescription() {
        return Description.get();
    }

    public void setDescription(String Description) {
        this.Description.set(Description);
    }

    public String getLocation() {
        return Location.get();
    }

    public void setLocation(String Location) {
        this.Location.set(Location);
    }

    public String getType() {
        return Type.get();
    }

    public void setType(String Type) {
        this.Type.set(Type);
    }

    public LocalDateTime getStart() {
        return Start.get();
    }

    public void setStart(LocalDateTime Start) {
        this.Start.set(Start);
    }

    public LocalDateTime getEnd() {
        return End.get();
    }

    public void setEnd(LocalDateTime End) {
        this.End.set(End);
    }

    public int getCustomer_ID() {
        return Customer_ID.get();
    }

    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID.set(Customer_ID);
    }

    public int getUser_ID() {
        return User_ID.get();
    }

    public void setUser_ID(int User_ID) {
        this.User_ID.set(User_ID);
    }

    public int getContact_ID() {
        return Contact_ID.get();
    }

    public void setContact_ID(int Contact_ID) {
        this.Contact_ID.set(Contact_ID);
    }
}
