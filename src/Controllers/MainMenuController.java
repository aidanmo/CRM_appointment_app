package Controllers;
import DAO.AppointmentDB;
import Model.Appointment;
import Utils.ProgramAlerts;
import Utils.ScreenSwitcher;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {


    public Label timeZoneLbl;
    public Button appointmentsMenuBtn;

    /**
     * @param url
     * @param resourceBundle
     *
     * Sets timeZoneLbl to user location upon initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        timeZoneLbl.setText(ZoneId.systemDefault().toString());

    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Changes scenes to the AppointmentMenu.
     */
    public void onActionAppointmentsMenu(ActionEvent event) throws IOException {
        ScreenSwitcher.goToAppointmentMenu(event);
    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Changes scenes to the CustomerMenu.
     */
    public void onActionCustomersMenu(ActionEvent event) throws IOException {
        ScreenSwitcher.goToCustomerMenu(event);
    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Changes scenes to the ReportsMenu.
     */
    public void onActionReportsMenu(ActionEvent event) throws IOException {
        ScreenSwitcher.goToReportsMenu(event);
    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Logs out of program and changes scene to LoginScreen.
     */
    public void OnActionLogOut(ActionEvent event) throws IOException {
        ScreenSwitcher.goToLoginScreen(event);
    }
}
