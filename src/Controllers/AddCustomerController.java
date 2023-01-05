package Controllers;

import DAO.CountryDB;
import DAO.CustomerDB;
import DAO.FirstLevelDivisionDB;
import Model.*;
import Utils.IDConverter;
import Utils.ScreenSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    private AnchorPane addCustomerBackground;

    @FXML
    private TextField cityTxt;

    @FXML
    private ComboBox<String> countryCBox;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField phoneNumberTxt;

    @FXML
    private ComboBox<String> stateCBox;

    @FXML
    private TextField streetAddressTxt;

    @FXML
    private TextField zipcodeTxt;

    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToCustomerMenu(event);
    }

    /**
     * @param event Action Event object button clicked.
     * @throws SQLException
     * @throws IOException
     *
     * Adds Customer object based on values of fields on AddCustomer form to the connected database.
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        int customerId = Integer.valueOf(customerIdTxt.getText());
        String name = customerNameTxt.getText();
        String address = streetAddressTxt.getText() + ", " + cityTxt.getText();
        String zip = zipcodeTxt.getText();
        String phoneNumber = phoneNumberTxt.getText();
        String country = countryCBox.getValue();
        String state = stateCBox.getValue();

        Customer createdCustomer = new Customer(customerId, name, address, zip, phoneNumber,
                IDConverter.divisionNameToDivisionId(state), country, state);

        CustomerDB.addCustomer(createdCustomer);

        ScreenSwitcher.goToCustomerMenu(event);
    }

    /**
     * @param event Action event object representing value selected on CountryCbox
     *
     * Updates values of first level  division based on selected country. This way only FirstLevelDivisions contained by
     * the specified country are present in the stateCbox. This uses the Country ID value of a FirstLevelDivision object.
     * Using the methods in the IDConverter class it is able to convert between the two.
     */
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        String countryName = countryCBox.getValue();
        ObservableList <String> allProvidences = FXCollections.observableArrayList();

        for (FirstLevelDivision div: FirstLevelDivisionDB.getFirstLevelDivisions()) {
            if (IDConverter.countryNameToCountryId(countryName) == div.getCountryId()) {
                allProvidences.add(div.getDivisionName());
            }

        }
        stateCBox.setItems(allProvidences);
    }

    /**
     * Populates the stateCBox and countryCBox by using the getAllCountries() and getFirstLevelDivisions() methods in
     * order to return an ObservableList of each respectively. Using those ObservableLists to populate each ComboBox.
     *
     */
    public void populateComboBoxes() {
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        ObservableList <String> allProvidences = FXCollections.observableArrayList();

        for (Country country: CountryDB.getAllCountries()) {
            allCountries.add(country.getCountryName());
        }
        for (FirstLevelDivision div: FirstLevelDivisionDB.getFirstLevelDivisions()) {
            allProvidences.add(div.getDivisionName());
        }

        countryCBox.setItems(allCountries);
        stateCBox.setItems(allProvidences);
    }

    /**
     * @param url
     * @param resourceBundle
     *
     * Populates the Combo Boxes on initialization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBoxes();
        customerIdTxt.setText(String.valueOf(Customer.generateCustomerId(CustomerMenuController.getAllCustomers())));
    }
}
