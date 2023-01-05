package Controllers;

import Model.Appointment;
import DAO.AppointmentDB;
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

public class MonthReportController implements Initializable {

    @FXML
    private ComboBox<String> monthCBox;

    @FXML
    private ComboBox<String> typeCBox;

    @FXML
    private Label countLbl;

    @FXML
    private Label monthLbl;

    @FXML
    private Label typeLbl;

    /**
     * @param event ActionEvent button clicked.
     *
     * Provides a Integer count representing the amount of Appointments that have a matching month value for LocalDate
     * Time startTime and matching String Type. These values are selected in the respective combo boxes.
     */
    @FXML
    void onActionCreateReport(ActionEvent event) {
        int count = 0;
        ObservableList <Appointment> allAppointments = AppointmentDB.getAllAppointments();

        if (monthCBox.getValue() != null || typeCBox.getValue() != null) {
            String type = typeCBox.getValue();
            String month = monthCBox.getValue();

            typeLbl.setText(type);
            monthLbl.setText(month);

            for (Appointment appointment: allAppointments) {
                if (appointment.getAppointmentType().equals(type) &&
                        appointment.getStartTime().getMonth().toString().equalsIgnoreCase(month)) {
                    count++;
                }
            }
            countLbl.setText(String.valueOf(count));
        } else {
            ProgramAlerts.errorAlertCreator("Invalid Selection", "Invalid Month or Type Selection",
                    "A valid month and type must be selected to generate a report.");
        }
    }

    /**
     * @param actionEvent ActionEvent button clicked.
     * @throws IOException
     *
     * Changes scene to ReportMenu and exits MonthReport.
     */
    public void onActionClose(ActionEvent actionEvent) throws IOException {
        ScreenSwitcher.goToReportsMenu(actionEvent);
    }

    /**
     * Populates the two combo boxes with all type values present in the database and all months in a year.
     */
   public void populateReportComboBoxes () {
        ObservableList <Appointment> allAppointments = AppointmentDB.getAllAppointments();

        ObservableList <String> typeList = FXCollections.observableArrayList();
        for (Appointment appointment: allAppointments) {
            if (typeList.contains(appointment.getAppointmentType())) {
                //skipping iteration so there are no duplicates in the combo box
                continue;
            }
            typeList.add(appointment.getAppointmentType());
        }
        typeCBox.setItems(typeList);


        ObservableList <String> monthList = FXCollections.observableArrayList("January", "February", "March",
                "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthCBox.setItems(monthList);
    }


    /**
     * @param url
     * @param resourceBundle
     *
     * Populates the two combo boxes upon initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateReportComboBoxes();
    }

}
