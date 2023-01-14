package Utils;

import DAO.UserLoginDB;
import Model.Appointment;
import Model.User;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EntryValidator {

    /**
     * @param start LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @param end LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @return returns a boolean on whether the start and end times passed fall between office hours of the New York
     * office location.
     *
     * The method converts the start and end times passed as parameters to EST time, which are then compared to the
     * New York office's operating hours of 8:00 - 22:00. the method returns false if the times passed don't fall
     * outside of business hours. Whiles displaying an error message and return true if the times passed fall outside of
     * business hours.
     */
    public static boolean isDuringBusinessHours(LocalDateTime start, LocalDateTime end) {
        final LocalTime open = LocalTime.MIN.plusHours(8);
        final LocalTime close = LocalTime.MIN.plusHours(22);

        LocalDateTime startEST = TimeConverter.getLocalDateTimeInEST(start);
        LocalDateTime endEST = TimeConverter.getLocalDateTimeInEST(end);

        if (startEST.toLocalTime().isBefore(open) || startEST.toLocalTime().isAfter(close) ||
                endEST.toLocalTime().isBefore(open) || endEST.toLocalTime().isAfter(close)) {
            ProgramAlerts.errorAlertCreator("Appointment Error", "Office Hours Conflict",
                    "Appointments must be scheduled during office hours Monday - Friday 8:00 - 22:00");
            return true;
        }
        return false;
    }

    /**
     * @param start LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @param end LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @param allAppointments ObservableArrayList of appointment objects collected from the SQL database
     * @param customerId Integer associated with customer
     * @return Returns a boolean value depending if the the start and end times overlap with any existing appointment.
     *
     * The method checks the start and end times passed against the ObservableList of all appointments where the
     * customer ID is the same as the one passed. Thus verifying a single customer ID does not have two appointments
     * that overlap with each other.
     */
    public static boolean isOverlapping(LocalDateTime start, LocalDateTime end,
                                        ObservableList <Appointment> allAppointments ,int customerId) {
        if(allAppointments==null || allAppointments.isEmpty()){
            ProgramAlerts.errorAlertCreator("Appointment Error", "No Appointment Available",
                    "No Appointment is available");
            return true;
        }
        if (allAppointments.stream()
                .filter(appointment -> customerId == appointment.getCustomerID())
                .anyMatch(appointment -> (start.isBefore(appointment.getEndTime()) && appointment.getStartTime().isBefore(end)))) {
            ProgramAlerts.errorAlertCreator("Appointment Error", "Overlapping Appointments Conflict",
                    "Appointment times " + start.toLocalTime() + " - " + end.toLocalTime() +
                            " conflict with existing appointment ");
            return true;
        }
        return false;
    }

    /**
     * @param start LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @param end LocalDateTime object gathered from the modify appointment and add appointment forms.
     * @param allAppointments ObservableArrayList of appointment objects collected from the SQL database
     * @param customerId Integer associated with customer
     * @return Returns a boolean value depending if both scheduling constraints are met.
     *
     * validIntervals() calls both the isOverlapping() and isDuringBusinessHours() methods to make sure the appointment
     * created meets both scheduling constraints. Condensing both into one method that covers this aspect of entry
     * validation.
     */
    public static boolean validIntervals (LocalDateTime start, LocalDateTime end,
                                          ObservableList <Appointment> allAppointments, int customerId) {
        if (!isDuringBusinessHours(start, end) && !isOverlapping(start, end, allAppointments, customerId)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param username String value gathered from the username text-field on the login form.
     * @param password String value gathered from the password text-field on the login form.
     * @return returns a boolean determining whether a matching set of username and password was found in the data base.
     *
     * This method takes a username and password String and checks against the customer database for a set of matches.
     * Returning true is a match is found and false if one is not found.
     */
    public static boolean isUserCredentialsValid (String username, String password) {
        ObservableList <User> allUsers = UserLoginDB.getAllUsers();

        for (User user: allUsers) {
            if (username.equals(user.getUserName()) && password.equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
