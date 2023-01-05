package Controllers;

import Model.Appointment;
import DAO.AppointmentDB;
import DAO.ContactDB;
import Model.Contacts;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ContactReportController implements Initializable {

    @FXML
    private TableColumn<Appointment,Integer> appointmentIdCol;

    @FXML
    private ComboBox<String> contactCBox;

    @FXML
    private TableView<Appointment> contactReportCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    private ObservableList <Appointment> matchingContact;

    /**
     * @param event ActionEvent button clicked
     * @throws IOException
     *
     * Switches scene to the ReportMenu exiting the ContactReport form.
     */
    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToReportsMenu(event);
    }

    /**
     * @param event ActionEvent button clicked
     *
     * Creates and ObservableList of all appointments that match the contact String selected in the contactCBox. It does
     * this by looping through an ObservableList of all appointments checking idf the String contact value is matching.
     * Creating a new ObservableList of matches and setting the items of the tableview to that new ObservableList of
     * matches.
     */
    @FXML
    void onActionCreateReport(ActionEvent event) {
        ObservableList <Appointment> allAppointments = AppointmentDB.getAllAppointments();
        matchingContact = FXCollections.observableArrayList();

        if (contactCBox.getValue() != null) {
            for (Appointment appointment: allAppointments) {
                if (contactCBox.getValue().equalsIgnoreCase(appointment.getContact())) {
                    matchingContact.add(appointment);
                }
            }

        }
        contactReportCol.setItems(matchingContact);

    }

    /**
     * Populates the contactCBox with an ObservableList of all String contacts. This is done by cycling through an
     * ObservableList of all conatact objects and adding the String contact value to a new ObservableList.
     */
    public void populateComboBox () {
        ObservableList <Contacts> allContacts = ContactDB.getAllContacts();
        ObservableList <String> contactNames = FXCollections.observableArrayList();

        for (Contacts contact: allContacts) {
            contactNames.add(contact.getContactName());
        }

        contactCBox.setItems(contactNames);
    }

    /**
     * @param url
     * @param resourceBundle
     *
     * Populates the contactCBox with all contact Strings and populates the TableView upon initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList <Appointment> allAppointments = FXCollections.observableArrayList();
        populateComboBox();

        contactReportCol.setItems(allAppointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
    }
}
