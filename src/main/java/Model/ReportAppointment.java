package Model;

/**
 * Class containing information for total number of customer appts by type and month.
 */
public class ReportAppointment {
    private String type;
    private int month;
    private int count;

    public ReportAppointment(String type, int month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    public String getType() { return type; }
    public int getMonth() { return month; }
    public int getCount() { return count; }
}
