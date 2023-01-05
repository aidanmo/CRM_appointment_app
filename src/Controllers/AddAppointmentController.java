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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    @FXML
    private ComboBox customerIdCBox;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<String> contactCBox;

    @FXML
    private DatePicker datePicker;

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

    /**
     * @param event Action Event object button click
     * @throws IOException
     *
     * This method switches scene back to AppointmentMenu.fxml.
     */
    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToAppointmentMenu(event);
    }

    /**
     * @param event Action Event object button clicked.
     * @throws SQLException
     * @throws IOException
     *
     * Adds Appointment object based on values of fields on AddAppointment form to the connected database.
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        ObservableList <Appointment> allAppointments;
        allAppointments = FXCollections.observableArrayList();
        allAppointments = AppointmentDB.getAllAppointments();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = datePicker.getValue();
        LocalTime start = LocalTime.parse(startTimeCBox.getValue(), format);
        LocalTime end = LocalTime.parse(endTimeCBox.getValue(), format);

        int appointmentId = Integer.valueOf(appointmentIdTxt.getText());
        String contact = contactCBox.getValue();
        int customerId = Integer.valueOf(String.valueOf(customerIdCBox.getValue()));

        LocalDateTime startTime = LocalDateTime.of(date, start);
        LocalDateTime endTime = LocalDateTime.of(date, end);
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String title = titleTxt.getText();
        String type = typeTxt.getText();
        int userId = Integer.valueOf(userIdTxt.getText());

        if (EntryValidator.validIntervals(startTime, endTime,
                allAppointments, customerId)) {
            Appointment createdAppointment = new Appointment(appointmentId, title, description, location, type, startTime,
                    endTime, customerId, userId, contact);
            try {
                AppointmentDB.addAppointment(createdAppointment);
            } catch (SQLException e) {
                System.out.println("ERROR: " + e);
            }
            ScreenSwitcher.goToAppointmentMenu(event);
        }
    }

    /**
     * populated contactCBOX with all contacts from the contact table in the DB. While also populating the Start and End
     * Time CBox with every time available between 0:00 and 24:00.
     */
    public void populateComboBoxes () {
        ObservableList<String> appointmentTime = FXCollections.observableArrayList();
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
        customerIdCBox.setItems(customers);



    }

    /**
     * @param url
     * @param resourceBundle
     *
     * Populates the Combo Boxes on initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBoxes();
        appointmentIdTxt.setText(String.valueOf(Appointment.generateAppointmentId(AppointmentDB.getAllAppointments())));

    }
}
