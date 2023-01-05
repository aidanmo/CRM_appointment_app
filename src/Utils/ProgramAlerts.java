package Utils;

import DAO.AppointmentDB;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProgramAlerts {

    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private static Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * @param title String value passed for dialog box title.
     * @param header String value passed for dialog box header.
     * @param content String value passed for dialog box content.
     * This method creates an error alert taking three strings as parameters to populate the content.
     */
    public static void errorAlertCreator (String title, String header, String content) {
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        errorAlert.showAndWait();
    }

    /**
     * @param title String value passed for dialog box title.
     * @param header String value passed for dialog box header.
     * @param content String value passed for dialog box content.
     * This method creates an information alert taking three strings as parameters to populate the content.
     *
     */
    public static void informationAlertCreator (String title, String header, String content) {
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(header);
        infoAlert.setContentText(content);
        infoAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        infoAlert.showAndWait();
    }

    /**
     * @param title String value passed for dialog box title.
     * @param header String value passed for dialog box header.
     * @param content String value passed for dialog box content.
     * @param button String value passed for dialog box button content.
     *
     * This method is used to create an alert using the language_property bundle that way every piece of content in the
     * dialog box can be translated into either english or french. This method takes four Strings to populate title,
     * header, content, and button.
     */
    public static void errorAlertLogin (String title, String header, String content, String button) {
        ButtonType okButton = new ButtonType(button, ButtonBar.ButtonData.OK_DONE);
        Alert loginError = new Alert(Alert.AlertType.ERROR, content, okButton);
        loginError.setTitle(title);
        loginError.setHeaderText(header);

        Optional <ButtonType> result = loginError.showAndWait();

        if (result.get() == okButton) {
            loginError.close();
        }
    }

    /**
     * This method is used to alert the customer if there is any appointments within 15 minutes of logging in. Using an
     * ObservableList of appointments it checks if any fall between a timestamp of the instance from logging in and 15
     * from that instance. If no appointments fall into that bounds it provides feedback that there are no upcoming
     * appointments.
     */
    public static void upcomingAppointmentAlert() {
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus15Min = LocalDateTime.now().plusMinutes(15);
        System.out.println(nowPlus15Min);


        boolean appIn15 = false;
        //Used to provide feedback if no appointment start time is found between now and nowPlus15Min

        for (Appointment appointment: allAppointments) {
            if (appointment.getStartTime().isAfter(now) && appointment.getStartTime().isBefore(nowPlus15Min)) {
                ProgramAlerts.informationAlertCreator("Appointment Reminder", "Upcoming Appointment Alert"
                        , "Appointment ID: " + appointment.getAppointmentID() + " is scheduled within the next fifteen " +
                                "minutes on " + appointment.getStartTime().toLocalDate() + " at "
                                + appointment.getStartTime().toLocalTime() + ".");
                appIn15 = true;
            }
        }

        if(!appIn15) {
            ProgramAlerts.informationAlertCreator("Appointment Reminder", "Upcoming Appointment Alert",
                    "You have no upcoming appointments.");
        }
    }
}
