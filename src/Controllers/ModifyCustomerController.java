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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class ModifyCustomerController {

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

    /**
     * @param event ActionEvent button clicked.
     * @throws IOException
     *
     * Switches scene back to CustomerMenu
     */
    @FXML
    void onActionClose(ActionEvent event) throws IOException {
        ScreenSwitcher.goToCustomerMenu(event);
    }

    /**
     * @param event Action Event button clicked.
     * @throws IOException
     *
     * This method updates a row in the connected database based on the Customer ID of the selected customer
     * passed to the ModifyCustomer form.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int customerId = Integer.valueOf(customerIdTxt.getText());
        String name = customerNameTxt.getText();
        String address = streetAddressTxt.getText() + ", " + cityTxt.getText();
        String zip = zipcodeTxt.getText();
        String phone = phoneNumberTxt.getText();
        int divisionId = IDConverter.divisionNameToDivisionId(stateCBox.getValue());
        String country = countryCBox.getValue();
        String state = stateCBox.getValue();

        Customer createdCustomer = new Customer(customerId, name, address, zip, phone, divisionId, country, state);

        try {
            CustomerDB.updateCustomer(createdCustomer);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        ScreenSwitcher.goToCustomerMenu(event);
    }

    /**
     * @param actionEvent Action event object representing value selected on CountryCbox.
     *
     * Updates values of first level  division based on selected country. This way only FirstLevelDivisions contained by
     * the specified country are present in the stateCbox. This uses the Country ID value of a FirstLevelDivision object.
     * Using the methods in the IDConverter class it is able to convert between the two.
     */
    public void onActionCountrySelected(ActionEvent actionEvent) {
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
     *
     * Populates the stateCBox and countryCBox by using the getAllCountries() and getFirstLevelDivisions() methods in
     * order to return an ObservableList of each respectively. Using those ObservableLists to populate each ComboBox.
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
     * @param customer Customer object from CustomerMenu screen.
     *
     * This method is used to populate all the fields and CBoxes with the values associated with the selected
     * Customer chosen on the CustomerMenu page.
     */
    public void sendCustomer(Customer customer) {
        Customer selectedCustomer = CustomerMenuController.getSelectedCustomer();

        populateComboBoxes();

        //Splitting address and city so they can be populated into their respective boxes.
        String[] address = selectedCustomer.getAddress().split(", ");


        customerIdTxt.setText(String.valueOf(selectedCustomer.getId()));
        customerNameTxt.setText(selectedCustomer.getName());
        streetAddressTxt.setText(address[0]);
        zipcodeTxt.setText(selectedCustomer.getZip());
        phoneNumberTxt.setText(selectedCustomer.getPhoneNumber());
        countryCBox.setValue(IDConverter.divisionIdToCountryName(selectedCustomer.getId()));
        stateCBox.setValue(IDConverter.divisionIdToDivisionName(selectedCustomer.getDivisionId()));
        if (address.length > 1) {
            //if Statement to check if there is no city added to the street address to avoid index out of bounds
            cityTxt.setText(address[1]);
        } else {
            cityTxt.setText("");
        }
    }

}
