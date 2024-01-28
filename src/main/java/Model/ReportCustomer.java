package Model;

/**
 * Class containing information for total number of customer appts.
 */
public class ReportCustomer {
    private String customerName;
    private int numberOfAppointments;

    public ReportCustomer(String customerName, int numberOfAppointments) {
        this.customerName = customerName;
        this.numberOfAppointments = numberOfAppointments;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
    }
}

