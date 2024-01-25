package DAO;

import Model.Appointments;
import Model.Dialogs;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import static DAO.AppointmentQuery.getAllAppointmentsForUser;

public class User_DAO_Impl {

    // Converts from a specific time zone to UTC. don't call this for tableview
    public static LocalDateTime convertToUTC(LocalDateTime localTime, ZoneId userTimeZone) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime localZonedDateTime = localTime.atZone(userTimeZone);
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(utcZone);
        return utcZonedDateTime.toLocalDateTime();
    }

    // Converts from UTC to a specific time zone
    public static LocalDateTime convertFromUTC(LocalDateTime utcTime, ZoneId userTimeZone) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZonedDateTime utcZonedDateTime = utcTime.atZone(utcZone);
        ZonedDateTime targetZonedDateTime = utcZonedDateTime.withZoneSameInstant(userTimeZone);
        return targetZonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime convertToET(LocalDateTime localDateTime, ZoneId userTimeZone) {
        // Eastern Time Zone ID
        ZoneId etZoneId = ZoneId.of("America/New_York");

        // Convert the LocalDateTime to ZonedDateTime in the local time zone
        ZonedDateTime localZonedDateTime = localDateTime.atZone(userTimeZone);

        // Convert the ZonedDateTime from the local time zone to Eastern Time
        ZonedDateTime etZonedDateTime = localZonedDateTime.withZoneSameInstant(etZoneId);

        // Return the LocalDateTime part of the converted ZonedDateTime
        return etZonedDateTime.toLocalDateTime();
    }

    public static LocalDateTime convertFromET(LocalDateTime etDateTime, ZoneId userTimeZone) {
        // Eastern Time Zone ID
        ZoneId etZoneId = ZoneId.of("America/New_York");

        // Convert the ET LocalDateTime to ZonedDateTime in Eastern Time
        ZonedDateTime etZonedDateTime = etDateTime.atZone(etZoneId);

        // Convert the ZonedDateTime from Eastern Time to the target time zone
        ZonedDateTime targetZonedDateTime = etZonedDateTime.withZoneSameInstant(userTimeZone);

        // Return the LocalDateTime part of the converted ZonedDateTime
        return targetZonedDateTime.toLocalDateTime();
    }

    // Converts UTC time to the user's local time zone
    public static LocalDateTime convertToLocalTime(LocalDateTime utcTime) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(userTimeZone).toLocalDateTime();
    }

    public static boolean isWithinBusinessHours(LocalDateTime etTime) {
        LocalTime BUSINESS_START_ET = LocalTime.of(8, 0); // 8:00 AM ET
        LocalTime BUSINESS_END_ET = LocalTime.of(22, 0); // 10:00 PM ET
        LocalTime time = etTime.toLocalTime(); // Extract the time part
        return !time.isBefore(BUSINESS_START_ET) && !time.isAfter(BUSINESS_END_ET);
    }


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
            LocalDateTime appointmentStartUtc = appointment.getStart(); // should be in UTC
            System.out.println("Processing appointment ID: " + appointment.getAppointmentId() + ", Start (UTC): " + appointmentStartUtc);

            // Convert from UTC to user's local time
            LocalDateTime appointmentStartLocal = User_DAO_Impl.convertFromUTC(appointmentStartUtc, userTimeZone);
            System.out.println("Appointment Start (Local): " + appointmentStartLocal);

            if (!upcomingAppointmentFound && appointmentStartLocal.isAfter(nowLocal) && appointmentStartLocal.isBefore(nowPlus15Minutes)) {
                // Appointment within the next 15 minutes
                Dialogs.showUpcomingAppointmentAlert(appointment.getAppointmentId(), appointmentStartLocal);
                upcomingAppointmentFound = true;
            }
        }

        if (!upcomingAppointmentFound) {
            Dialogs.showNoUpcomingAppointmentsAlert();
            System.out.println("No upcoming appointments within the next 15 minutes.");
        }
    }

}
