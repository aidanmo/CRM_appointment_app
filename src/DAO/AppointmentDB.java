package DAO;

import Model.Appointment;
import Utils.IDConverter;
import Utils.JDBC;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentDB {

    /**
     * @return returns a observablelist of all appointments stored in the queried database
     *
     * This method queries the connected database for all appointments stored in the appointment table. Returning an
     * ObservableList of Appointment objects representing each row in the database.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;

        try {
            String sql = "SELECT * FROM client_schedule.appointments;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appointment = new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Type"), rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"), rs.getInt("User_ID"),
                        IDConverter.contactIdToContactName(rs.getInt("Contact_ID")));
                appointments.add(appointment);
            }
            ps.close();
            return appointments;
        } catch (SQLException e) {
            System.out.println("Exception - " + e.getMessage());
            return null;
        }
    }


    /**
     * @param appointment Appointment object passed as a parameter.
     * @return returns a integer value representing how many rows were changed in the database after the update
     * statement was executed.
     * @throws SQLException
     *
     * This method deletes the Appointment object passed as a parameter from the connected database.
     */
    public static int deleteAppointment(Appointment appointment) throws SQLException {
        int appointmentId = appointment.getAppointmentID();

        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = " + appointmentId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    /**
     * @param appointment Appointment object passed as a parameter.
     * @throws SQLException
     *
     * This method adds the Appointment object passed as a parameter to the database connected.
     */
    public static void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type, " +
                "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setInt(1, appointment.getAppointmentID());
        ps.setString(2, appointment.getAppointmentTitle());
        ps.setString(3, appointment.getAppointmentDescription());
        ps.setString(4, appointment.getAppointmentLocation());
        ps.setString(5, appointment.getAppointmentType());
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getStartTime()));
        ps.setTimestamp(7, Timestamp.valueOf(appointment.getEndTime()));
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(9, "Administrator");
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(11, 1);
        ps.setInt(12, appointment.getCustomerID());
        ps.setInt(13, appointment.getUserID());
        ps.setInt(14, IDConverter.contactNameToContactId(appointment.getContact()));

        ps.executeUpdate();
    }

    /**
     * @param appointment Appointment object passed as a parameter.
     * @throws SQLException
     *
     * This method updates the columns of a specified appointment in the appointment table of the connected database.
     * This uses the appointment ID to identify which appointment is being updated.
     */
    public static void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET Title = ?, Location = ?, Description = ?, Type = ?, " +
                "Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, " +
                "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, appointment.getAppointmentTitle());
        ps.setString(2, appointment.getAppointmentLocation());
        ps.setString(3, appointment.getAppointmentDescription());
        ps.setString(4, appointment.getAppointmentType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(8, 1);
        ps.setInt(9, appointment.getCustomerID());
        ps.setInt(10, appointment.getUserID());
        ps.setInt(11, IDConverter.contactNameToContactId(appointment.getContact()));
        ps.setInt(12, appointment.getAppointmentID());

        ps.execute();
    }


}
