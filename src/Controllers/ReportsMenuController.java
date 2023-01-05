package Controllers;
import Utils.ScreenSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class ReportsMenuController {

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Switches scene to ContactReport.
     */
    @FXML
    void onActionContactReport(ActionEvent event) throws IOException {
        ScreenSwitcher.goToContactReport(event);
    }


    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Switches scene to MonthReport.
     */
    @FXML
    void onActionMonthReport(ActionEvent event) throws IOException {
        ScreenSwitcher.goToMonthReport(event);

    }


    /**
     * @param actionEvent ActionEvent button clicked.
     * @throws IOException
     *
     * Switches scene to CustomerAppointmentReport.
     */
    public void onActionCustomerAppointmentReport(ActionEvent actionEvent) throws IOException {
        ScreenSwitcher.goToCustomerAppointmentReport(actionEvent);
    }

    /**
     * @param actionEvent ActionEvent button clicked.
     * @throws IOException
     *
     * Switches scene to MainMenu and exits user from the ReportsMenu.
     */
    public void onActionClose(ActionEvent actionEvent) throws IOException {
        ScreenSwitcher.goToMainMenu(actionEvent);
    }
}
