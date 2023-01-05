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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentMenuController implements Initializable {


    public TableView <Appointment> appointmentMenuTbl;
    public TableColumn <Appointment, Integer> appointmentIdCol;
    public TableColumn <Appointment, String> titleCol;
    public TableColumn <Appointment, String> descriptionCol;
    public TableColumn <Appointment, String> locationCol;
    public TableColumn <Appointment, String> typeCol;
    public TableColumn <Appointment, LocalDateTime> startCol;
    public TableColumn <Appointment, LocalDateTime> endCol;
    public TableColumn <Appointment, Integer> customerIdCol;
    public TableColumn <Appointment, Integer> userIdCol;
    public TableColumn <Appointment, Integer> contactCol;
    @FXML
    private RadioButton AllAppointmentsRBtn;

    @FXML
    private RadioButton MonthlyAppointmentsRBtn;

    @FXML
    private ToggleGroup SelectedAppointmentView;

    @FXML
    private RadioButton WeeklyAppointmentsRBtn;

    private static ObservableList <Appointment> appointments;

    private static Appointment selectedAppointment;

    /**
     * @return returns the Appointment object that is selected from the tableview.
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * @param actionEvent ActionEvent object radio button selected.
     *
     * This method populates the table view with all Appointments inside the Appointment database.
     */
    public void onActionAllAppointments(ActionEvent actionEvent) {
        appointmentMenuTbl.setItems(appointments);
    }

    /**
     * @param actionEvent ActionEvent object radio button selected.
     *
     * This method populates the table view with a ObservableList of Appointment that have startTime's that fall
     * between now and one month from now.
     */
    public void onActionMonthlyAppointments(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonth = now.plusMonths(1);
        ObservableList <Appointment> monthly = FXCollections.observableArrayList();

        appointments.forEach(appointment -> {
            if (appointment.getStartTime().isBefore(oneMonth) && appointment.getStartTime().isAfter(now)) {
                monthly.add(appointment);
            }
        });
        appointmentMenuTbl.setItems(monthly);
    }

    /**
     * @param actionEvent ActionEvent object radio button selected.
     * LAMBDA #1 - uses a java lambda instead of a for loop to add each appointment object to and ObservableList if
     * it's LocalDateTime startTime value falls in between now and one week from now.
     *
     * This method populates the table view with a ObservableList of Appointment that have startTime's that fall
     * between now and one week from now.
     */
    public void onActionWeeklyAppointments(ActionEvent actionEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonth = now.plusWeeks(1);
        ObservableList <Appointment> weekly = FXCollections.observableArrayList();
        //Lambda 1:
        appointments.forEach(appointment -> {
            if (appointment.getStartTime().isBefore(oneMonth) && appointment.getStartTime().isAfter(now)) {
                weekly.add(appointment);
            }
        });
        appointmentMenuTbl.setItems(weekly);
    }

    /**
     * @param event ActionEvent object button clicked.
     * @throws IOException
     *
     * Changes scene to the AddAppointment form.
     */
    public void onActionAdd(ActionEvent event) throws IOException {
        ScreenSwitcher.goToAddAppointment(event);
    }

    /**
     * @param event ActionEvent object button clicked.
     * @throws IOException
     *
     * This method checks if the selectedAppointment value is null providing an error message if it is. If not null the
     * method will switch scenes to the ModifyAppointment form.
     */
    public void onActionModify(ActionEvent event) throws IOException {
        selectedAppointment = appointmentMenuTbl.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            ProgramAlerts.errorAlertCreator("Error", "Empty Selection", "You must select a valid " +
                    "appointment to make modifications.");
        } else {
            ScreenSwitcher.goToModifyAppointments(event ,selectedAppointment);
        }
    }

    /**
     * @param event ActionEvent object button clicked.
     *
     * This method checks if the selectedAppointment value is null providing an error message if it is. If it's not null
     * the method will provide a confirmation dialog box confirming the deletion of the selectedAppointment. Once
     * confirmed the selectedAppointment will be deleted from the database.
     */
    public void onActionDelete(ActionEvent event) {
        selectedAppointment = appointmentMenuTbl.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            ProgramAlerts.errorAlertCreator("Error", "Empty Selection", "You must select a valid " +
                    "appointment to make deletions.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected appointment with appointment" +
                    " id: " + selectedAppointment.getAppointmentID());
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                try {
                    AppointmentDB.deleteAppointment(selectedAppointment);
                    appointments.remove(selectedAppointment);
                    appointmentMenuTbl.setItems(appointments);
                    ProgramAlerts.informationAlertCreator("Deletion Success",
                            "Appointment Deletion Successful", "Appointment with appointment ID: " +
                            selectedAppointment.getAppointmentID() + "and type:" + selectedAppointment.getAppointmentType()
                                    + " was successfully deleted.");
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * @param event ActionEvent button clicked
     * @throws IOException
     *
     * Changes scene to the MainMenu exiting the Appointment menu.
     */
    public void onActionExit(ActionEvent event) throws IOException {
        ScreenSwitcher.goToMainMenu(event);
    }

    /**
     * @param url
     * @param resourceBundle
     *
     * Sets Items of tableview with an ObservableList of all appointments upon Initialization of the scene.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = FXCollections.observableArrayList();
        appointments = AppointmentDB.getAllAppointments();

        appointmentMenuTbl.setItems(appointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentLocation"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

}