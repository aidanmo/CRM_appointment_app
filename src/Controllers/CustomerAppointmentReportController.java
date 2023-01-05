package Controllers;

import Model.Appointment;
import DAO.AppointmentDB;
import Model.Customer;
import DAO.CustomerDB;
import Utils.IDConverter;
import Utils.ProgramAlerts;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAppointmentReportController implements Initializable {

    @FXML
    private ComboBox<String> CustomerCBox;

    @FXML
    private ComboBox<String> MonthCBox;

    @FXML
    private Label countLbl;

    @FXML
    private Label selectedCustomerLbl;

    @FXML
    private Label selectedMonthLbl;

    /**
     * @param event Action Event button clicked
     * @throws IOException
     *
     * Changes scene to ReportMenu and exits CustomerAppointmentReport.
     */
    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToReportsMenu(event);
    }

    /**
     * @param event Action Event button clicked.
     *
     * Provides a Integer count representing the amount of Appointments that have a matching month value for LocalDate
     * Time startTime and matching Integer customerID. These values are selected in the respective combo boxes.
     */
    @FXML
    void onActionCreateReport(ActionEvent event) {
        int count = 0;
        ObservableList <Appointment> allAppointments = AppointmentDB.getAllAppointments();

        if (MonthCBox.getValue() != null || CustomerCBox.getValue() != null) {
            String customerName = CustomerCBox.getValue();
            String month = MonthCBox.getValue();

            selectedCustomerLbl.setText(customerName);
            selectedMonthLbl.setText(month);

            for (Appointment appointment: allAppointments) {
                if (IDConverter.customerNameToCustomerId(customerName) == appointment.getCustomerID() &&
                        appointment.getStartTime().getMonth().toString().equalsIgnoreCase(month)) {
                    count++;
                }
            }
            countLbl.setText(String.valueOf(count));
        } else {
            ProgramAlerts.errorAlertCreator("Invalid Selection", "Invalid Month or Customer Selection",
                    "A valid month and Customer name must be selected to generate a report.");
        }

    }

    /**
     * Populates the two combo boxes with all customer names and all months in a year.
     */
    public void populateComboBoxes() {
        ObservableList <Customer> allCustomers = CustomerDB.getAllCustomers();
        ObservableList <String> customerNames = FXCollections.observableArrayList();

        for (Customer customer: allCustomers) {
            customerNames.add(customer.getName());
        }
        CustomerCBox.setItems(customerNames);

        ObservableList <String> monthList = FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November", "December");
        MonthCBox.setItems(monthList);

    }

    /**
     * @param url
     * @param resourceBundle
     *
     * Populates the two combo boxes upon initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { populateComboBoxes(); }
}
