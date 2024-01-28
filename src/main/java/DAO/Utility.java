package DAO;

import Model.Appointments;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import static DAO.AppointmentQuery.getAllAppointmentsForUser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Utility class with methods for time conversions, validations, and logging.
 */
public class Utility {

    /**
     * Converts a local date/time from a specific time zone to UTC.
     * @param localTime The local time to be converted.
     * @param userTimeZone The user's time zone.
     * @return The local date/time in UTC.
     */
    public static LocalDateTime convertToUTC(LocalDateTime localTime, ZoneId userTimeZone) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime localZonedDateTime = localTime.atZone(userTimeZone);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(utcZone);
        return utcZonedDateTime.toLocalDateTime();
    }

    /**
     * Converts local date/time from a specific time zone to Eastern Time (ET).
     * @param localDateTime The local date and time.
     * @param userTimeZone The user's current time zone.
     * @return The local date/time in Eastern Time.
     */
    public static LocalDateTime convertToET(LocalDateTime localDateTime, ZoneId userTimeZone) {

        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZonedDateTime localZonedDateTime = localDateTime.atZone(userTimeZone);
        ZonedDateTime etZonedDateTime = localZonedDateTime.withZoneSameInstant(etZoneId);
        return etZonedDateTime.toLocalDateTime();
    }

    /**
     * Converts local date/time from Eastern Time (ET) to a specific time zone.
     * @param etDateTime The Eastern Time date and time.
     * @param userTimeZone The target time zone.
     * @return The local date/time in the target time zone.
     */
    public static LocalDateTime convertFromET(LocalDateTime etDateTime, ZoneId userTimeZone) {

        ZoneId etZoneId = ZoneId.of("America/New_York");
        ZonedDateTime etZonedDateTime = etDateTime.atZone(etZoneId);
        ZonedDateTime targetZonedDateTime = etZonedDateTime.withZoneSameInstant(userTimeZone);
        return targetZonedDateTime.toLocalDateTime();
    }

    /**
     * Checks if a given local date/time in Eastern Time (ET) is within business hours.
     * @param etTime The time in Eastern Time to check.
     * @return True if the time is within business hours, false otherwise.
     */
    public static boolean isWithinBusinessHours(LocalDateTime etTime) {
        LocalTime BUSINESS_START_ET = LocalTime.of(8, 0); // 8:00 AM ET
        LocalTime BUSINESS_END_ET = LocalTime.of(22, 0); // 10:00 PM ET
        LocalTime time = etTime.toLocalTime(); // Extract the time part
        return !time.isBefore(BUSINESS_START_ET) && !time.isAfter(BUSINESS_END_ET);
    }

    /**
     * Checks for upcoming appointments within the next 15 minutes for a logged-in user.
     * @param userId The ID of the user.
     * @param userTimeZone The user's time zone.
     */
    public static void checkForUpcomingAppointments(int userId, ZoneId userTimeZone) {

        LocalDateTime nowLocal = LocalDateTime.now(userTimeZone); // Current local time
        LocalDateTime nowPlus15Minutes = nowLocal.plusMinutes(15); // Time in 15 minutes

        System.out.println("Checking for upcoming appointments...");
        System.out.println("Current local time: " + nowLocal);
        System.out.println("Time in 15 minutes: " + nowPlus15Minutes);

        List<Appointments> appointments = getAllAppointmentsForUser(userId);
        System.out.println("Number of appointments fetched: " + appointments.size());

        boolean upcomingAppointmentFound = false;
        for (Appointments appointment : appointments) {
            LocalDateTime appointmentStartUtc = appointment.getStart();
            System.out.println("Processing appointment ID: " + appointment.getAppointmentId() + ", Start (UTC): " + appointmentStartUtc);

            if (!upcomingAppointmentFound && appointmentStartUtc.isAfter(nowLocal) && appointmentStartUtc.isBefore(nowPlus15Minutes)) {
                // Appointment within the next 15 minutes
                Dialogs.showUpcomingAppointmentAlert(appointment.getAppointmentId(), appointmentStartUtc);
                upcomingAppointmentFound = true;
            }
        }

        if (!upcomingAppointmentFound) {
            Dialogs.showNoUpcomingAppointmentsAlert();
            System.out.println("No upcoming appointments within the next 15 minutes.");
        }
    }

    /**
     * Logs a user's login activity.
     */
    public class LoginActivityLogger {

        public static void logActivity(String username, boolean isSuccess) {
            String fileName = "login_activity.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String logEntry = String.format("Login Attempt: %s | Date/Time: %s | Success: %s%n",
                        username, now.format(formatter), isSuccess ? "Yes" : "No");

                writer.write(logEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
