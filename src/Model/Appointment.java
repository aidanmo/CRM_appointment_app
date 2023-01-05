package Model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public int customerID;
    public int userID;
    public String contact;

    /**
     * @param appointmentID Integer Appointment ID identifying appointment object.
     * @param appointmentTitle String name for appointment object.
     * @param appointmentDescription String description of appointment object.
     * @param appointmentLocation String location for appointment object.
     * @param appointmentType String type for appointment object.
     * @param startTime LocalDateTime associated with start time for appointment object.
     * @param endTime LocalDateTime associated with end time for appointment object.
     * @param customerID Integer to identify the customer associated with a appointment object.
     * @param userID Integer to identify the user who created the appointment object.
     * @param contact String contact associated with an appointment object.
     *
     * Constructor used to create an appointment object taking 10  variables as parameters.
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                       String appointmentLocation, String appointmentType, LocalDateTime startTime,
                       LocalDateTime endTime, int customerID, int userID, String contact) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contact = contact;
    }

    /**
     * @param appointments Takes an observablelist of appointment objects as a parameter.
     * @return returns a Integer value.
     *
     * This method generates a appointment ID integer by looping through all the existing appointments and checking
     * their associated ID. Once it finds the appointment with the highest appointment ID integer value the method will
     * increment it by one and return the value.
     */
    public static int generateAppointmentId(ObservableList <Appointment> appointments) {
        int greatest = 0;

        for (Appointment app: appointments) {
            if (app.getAppointmentID() > greatest) {
                greatest = app.getAppointmentID();
            }
        }
        greatest++;
        return greatest;
    }

    /**
     * @return returns the AppointmentID Integer associated with an appointment object.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return returns the AppointmentTitle String associated with an appointment object.
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * @return returns the AppointmentDescription String associated with an appointment object.
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * @return returns the AppointmentLocation String associated with an appointment object.
     */
    public String getAppointmentLocation() { return appointmentLocation; }

    /**
     * @return returns the AppointmentType String associated with an appointment object.
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * @return returns the AppointmentStartTime LocalDateTime associated with an appointment object.
     */
    public LocalDateTime getStartTime() { return startTime; }

    /**
     * @param startTime takes a LocalDateTime object as a parameter
     *
     * Sets the LocalDateTime startTime associated with an appointment object.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return returns the AppointmentEndTime LocalDateTime associated with an appointment object.
     */
    public LocalDateTime getEndTime() { return endTime; }

    /**
     * @param endTime takes a LocalDateTime object as a parameter
     *
     * Sets the LocalDateTime endTime associated with an appointment object.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return returns the CustomerID Integer associated with an appointment object.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return returns the userID Integer associated with an appointment object.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @return returns the contact String associated with an appointment object.
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact takes a String as a parameter.
     *
     * Sets the String contact associated with an appointment object.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}
