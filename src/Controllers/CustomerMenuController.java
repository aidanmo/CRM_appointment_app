package Controllers;

import Model.Appointment;
import DAO.AppointmentDB;
import Model.Customer;
import DAO.CustomerDB;
import Utils.ProgramAlerts;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerMenuController implements Initializable {

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneNumberCol;

    @FXML
    private TableColumn<Customer, String> stateCol;

    @FXML
    private TableColumn<Customer, String> zipCodeCol;

    private static ObservableList<Customer> customers;

    private static Customer selectedCustomer;

    /**
     * @return returns a ObservableList of all customers from the connected database.
     */
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    /**
     * @return returns a Customer object based on what customer is selected in the tableview
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }


    /**
     * @param event ActionEvent button clicked
     * @throws IOException
     *
     * Changes scene to the addCustomer form.
     */
    public void onActionAdd(ActionEvent event) throws IOException {
        ScreenSwitcher.goToAddCustomer(event);
    }

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Checks if selected customer value is null providing a error dialog box if it is. If not it will change scenes to
     * the ModifyCustomer form.
     */
    public void onActionModify(ActionEvent event) throws IOException {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            ProgramAlerts.errorAlertCreator("Error", "Empty Selection", "You must select a valid " +
                    "customer to make modifications.");
        } else {
            ScreenSwitcher.goToModifyCustomers(event, selectedCustomer);
        }
    }

    /**
     * @param event ActionEvent button clicked
     *
     * This method checks if the selectedCustomer value is null providing an error message if it is. After checking the
     * value of the selectedCustomer the method creates an ObservableList of Appointments that have a matching customer
     * ID with the selectedCustomer ID. If the Observable list of matchingIDs is greater than zero a error message
     * dialog box will be displayed notifying the user that inorder to delete a customer all appointments associated
     * with their customer ID must be deleted first. If all constraints are met a confirmation dialog box will be
     * displayed confirming the deletion of the customer from the connected database.
     *
     */
    public void onActionDelete(ActionEvent event) {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            ProgramAlerts.errorAlertCreator("Error", "Empty Selection", "You must select a valid " +
                    "customer to execute deletions.");
        } else {
            for (Appointment appointment: AppointmentDB.getAllAppointments()) {
                if (appointment.getCustomerID() == selectedCustomer.getId()) {
                    customerAppointments.add(appointment);
                }
            }
            if (customerAppointments.size() > 0) {
                ProgramAlerts.errorAlertCreator("Error", "Customer Appointment Error",
                        "All Appointments associated with customer must be deleted before removing a customer.");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete the selected customer with customer" +
                        " ID: " + selectedCustomer.getId());
                Optional<ButtonType> confirmation = alert.showAndWait();

                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    try {
                        CustomerDB.deleteCustomer(selectedCustomer);
                        customers.remove(selectedCustomer);
                        customerTableView.setItems(customers);
                        ProgramAlerts.informationAlertCreator("Deletion Success",
                                "Customer Deletion Successful", "Customer with customer ID: " +
                                        selectedCustomer.getId() + " was successfully deleted.");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
        }

    }

    /**
     * @param event ActionEvent button clicked
     * @throws IOException
     *
     * Changes scene to the MainMenu exiting the CustomerMenuController.
     */
    public void onActionExit(ActionEvent event) throws IOException {
        ScreenSwitcher.goToMainMenu(event);
    }

    /**
     * @param url
     * @param resourceBundle
     * Populates the table view with a ObservableList of all customers contained in the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = FXCollections.observableArrayList();
        customers = CustomerDB.getAllCustomers();

        customerTableView.setItems(customers);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<>("Zip"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("CountryName"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
    }
}
