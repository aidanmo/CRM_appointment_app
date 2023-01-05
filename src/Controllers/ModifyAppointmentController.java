package Controllers;

import DAO.CustomerDB;
import Model.Appointment;
import DAO.AppointmentDB;
import DAO.ContactDB;
import Model.Contacts;
import Model.Customer;
import Utils.EntryValidator;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModifyAppointmentController {
    @FXML
    private ComboBox customerIDCBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> contactCBox;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<String> endTimeCBox;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<String> startTimeCBox;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextField userIdTxt;

    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToAppointmentMenu(event);
    }

    /**
     * @param event ActionEvent button clicked
     * @throws IOException
     * Lambda #2- This java lambda replaces a for loop with more readable code using the removeif() method to remove a
     * Appointment object from the ObservableList of all appointments if it has the same appointmentID Integer Value.
     * This is done so the selected appointment is not taken into account when checking for time overlaps.
     *
     * This method updates a row in the connected database based on the appointment ID of the selected appointment
     * passed to the ModifyAppointment form. This method also checks for time overlaps between appointments and that the
     * appointment modifications fall within business hours.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        Appointment selectedAppointment = AppointmentMenuController.getSelectedAppointment();

        ObservableList <Appointment> allAppointments;
        allAppointments = FXCollections.observableArrayList();
        allAppointments = AppointmentDB.getAllAppointments();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = datePicker.getValue();
        LocalTime start = LocalTime.parse(startTimeCBox.getValue(), format);
        LocalTime end = LocalTime.parse(endTimeCBox.getValue(), format);

        int appointmentId = Integer.valueOf(appointmentIdTxt.getText());
        String contact = contactCBox.getValue();
        int customerId = Integer.valueOf(String.valueOf(customerIDCBox.getValue()));

        LocalDateTime startTime = LocalDateTime.of(date, start);
        LocalDateTime endTime = LocalDateTime.of(date, end);
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String title = titleTxt.getText();
        String type = typeTxt.getText();
        int userId = Integer.valueOf(userIdTxt.getText());

        //removing selected appointment from list so it doesn't compare time intervals to itself and flag for overlaps.
        //second lambda expression
        allAppointments.removeIf(appointment -> appointment.getAppointmentID() == appointmentId);

        if (EntryValidator.validIntervals(startTime, endTime,
                allAppointments, customerId)) {
            Appointment createdAppointment = new Appointment(appointmentId, title, description, location, type, startTime,
                    endTime, customerId, userId, contact);
            try {
                AppointmentDB.updateAppointment(createdAppointment);
            } catch (SQLException e) {
                System.out.println("ERROR: " + e);
            }
            ScreenSwitcher.goToAppointmentMenu(event);
        }

    }

    /**
     * Populates the StartTimeCBox and endTimeCBox with times that fall within the range of 0:00 and 23:45. Also
     * populating the contactCBox with an ObservableList of all contacts in the connected database.
     */
    public void populateComboBoxes () {
        ObservableList <String> appointmentTime = FXCollections.observableArrayList();
        ObservableList <String> contacts = FXCollections.observableArrayList();
        ObservableList <String> customers = FXCollections.observableArrayList();


        LocalTime first = LocalTime.MIN;
        LocalTime last = LocalTime.MAX.minusMinutes(15);


        while (first.isBefore(last)) {
            appointmentTime.add(String.valueOf(first));
            first = first.plusMinutes(15);
        }
        startTimeCBox.setItems(appointmentTime);
        endTimeCBox.setItems(appointmentTime);

        for (Contacts contact: ContactDB.getAllContacts()) {
            contacts.add(String.valueOf(contact.getContactName()));
        }
        contactCBox.setItems(contacts);

        for (Customer customer: CustomerDB.getAllCustomers()) {
            customers.add(String.valueOf(customer.getId()));
        }
        customerIDCBox.setItems(customers);

    }

    /**
     * @param appointment Appointment object from Appointment menu screen.
     * This method is used to populate all the fields and CBoxes with the values associated with the selected
     * Appointment chosen on the AppointmentMenu page.
     */
    public void sendAppointment(Appointment appointment) {
        Appointment selectedAppointment = AppointmentMenuController.getSelectedAppointment();

        populateComboBoxes();

        titleTxt.setText(selectedAppointment.getAppointmentTitle());
        appointmentIdTxt.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        customerIDCBox.setValue(String.valueOf(selectedAppointment.getCustomerID()));
        descriptionTxt.setText(selectedAppointment.getAppointmentDescription());
        locationTxt.setText(selectedAppointment.getAppointmentLocation());
        contactCBox.setValue(selectedAppointment.getContact());
        typeTxt.setText(selectedAppointment.getAppointmentType());
        userIdTxt.setText(String.valueOf(1));
        datePicker.setValue(LocalDate.from(selectedAppointment.getEndTime()));
        startTimeCBox.setValue(String.valueOf(LocalTime.from(selectedAppointment.getStartTime())));
        endTimeCBox.setValue(String.valueOf(LocalTime.from(selectedAppointment.getEndTime())));
    }




}
