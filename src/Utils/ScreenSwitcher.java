package Utils;

import Controllers.ModifyAppointmentController;
import Controllers.ModifyCustomerController;
import Model.Appointment;
import Model.Customer;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Class containing methods that transition between different FXML files in the program.
 */
public class ScreenSwitcher {
    static Stage stage;
    static Parent scene;

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to MainMenu.fxml.
     */
    public static void goToMainMenu(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to AppointmentMenu.fxml.
     */
    public static void goToAppointmentMenu(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to CustomerMenu.fxml.
     */
    public static void goToCustomerMenu(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to ReportsMenu.fxml.
     */
    public static void goToReportsMenu(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/ReportsMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to LoginScreen.fxml.
     */
    public static void goToLoginScreen(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to AddAppointment.fxml.
     */
    public static void goToAddAppointment(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @param appointment Appointment object passed in-order to populate modify appointment form.
     * @throws IOException
     *
     * Changes scene to ModifyAppointment.fxml. Also utilizing the sendAppointment() method in order to populate the
     * fields on the modify appointment form.
     */
    public static void goToModifyAppointments(Event event ,Appointment appointment) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ScreenSwitcher.class.getResource("/Views/ModifyAppointment.fxml"));
        loader.load();

        ModifyAppointmentController maController = loader.getController();

        maController.sendAppointment(appointment);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to AddCustomer.fxml.
     */
    public static void goToAddCustomer(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @param customer Customer object passed in-order to populate modify customer form.
     * @throws IOException
     *
     * Changes scene to ModifyCustomer.fxml. Also utilizing the sendCustomer() method in order to populate the
     * fields on the modify customer form.
     */
    public static void goToModifyCustomers (Event event, Customer customer) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ScreenSwitcher.class.getResource("/Views/ModifyCustomer.fxml"));
        loader.load();

        ModifyCustomerController mcController = loader.getController();

        mcController.sendCustomer(customer);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to MonthReport.fxml.
     */
    public static void goToMonthReport(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/MonthReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to ContactReport.fxml.
     */
    public static void goToContactReport(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/ContactReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event ActionEvent object triggered by clicking related button.
     * @throws IOException
     *
     * Changes scene to CustomerAppointmentReport.fxml.
     */
    public static void goToCustomerAppointmentReport(Event event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(ScreenSwitcher.class.getResource("/Views/CustomerAppointmentReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }




}
