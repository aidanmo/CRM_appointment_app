package Controllers;

import Utils.EntryValidator;
import Utils.Logger;
import Utils.ProgramAlerts;
import Utils.ScreenSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    public Button loginButton;
    public Button cancelBtn;
    public Label userZoneLbl;
    public Label timeZoneLbl;
    public Button exitBtn;
    @FXML
    private Label passwordLbl;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label usernameLbl;

    @FXML
    public Label userLoginLbl;

    @FXML
    private TextField usernameTxt;

    /**
     * @param url
     * @param resource language_property holds strings to set label text based on user's locale.
     *
     * Sets the String text value of all labels upon initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resource) {
        Locale locale = Locale.getDefault();
        userZoneLbl.setText(ZoneId.systemDefault().toString());
        resource = ResourceBundle.getBundle("language_property/loginScreen", locale);
        userLoginLbl.setText(resource.getString("userLoginLbl"));
        usernameLbl.setText(resource.getString("userNameLbl"));
        passwordLbl.setText(resource.getString("passwordLbl"));
        timeZoneLbl.setText(resource.getString("timeZoneLbl"));
        loginButton.setText(resource.getString("loginButton"));
        cancelBtn.setText(resource.getString("cancelBtn"));
        loginButton.setText(resource.getString("loginButton"));
        exitBtn.setText(resource.getString("exitBtn"));
    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * This method logs the user into the program. It uses the isUSerCredentialsValid() method in order to check the
     * user table of the database to see if there is a match. If no match is found it will provide a error message in
     * either english or french depending on the user's locale by using the language_property resource bundle. If a
     * matching user is found or not found based on the text field value the method will call the log() method from
     * Logger class creating a entry for each successful or failed attempt in the login_activity.txt file.
     */
    public void onActionLogin(ActionEvent event) throws IOException {
        Locale locale = Locale.getDefault();
        ResourceBundle resource;
        resource = ResourceBundle.getBundle("language_property/loginScreen", locale);

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if (EntryValidator.isUserCredentialsValid(username, password)) {
            Logger.log(username, true);
            ScreenSwitcher.goToMainMenu(event);
            ProgramAlerts.upcomingAppointmentAlert();
        } else {
            ProgramAlerts.errorAlertLogin(resource.getString("loginFailedTitle"),
                    resource.getString("loginFailedHeader"), resource.getString("loginFailedContent"),
                    resource.getString("loginFailedOkayButton"));
            Logger.log(username, false);
        }

    }

    /**
     * @param event ActionEvent button clicked
     *
     * Clears text from both usernameTxt field and passwordTxt field.
     */
    public void onActionClear(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
    }

    /**
     * @param event ActionEvent button clicked
     *
     * Closes program.
     */
    public void onExit(ActionEvent event) {
        System.exit(0);
    }
}
