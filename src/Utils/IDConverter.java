package Utils;

import DAO.ContactDB;
import DAO.CountryDB;
import DAO.CustomerDB;
import DAO.FirstLevelDivisionDB;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class IDConverter {

    /**
     * @param divId Integer values associated with the ID of a first level division object.
     * @return return String name associated with a division ID
     *
     * Method takes a divisionId Integer and converts it to the associated division name.
     */
    public static String divisionIdToDivisionName (int divId) {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        allDivisions = FirstLevelDivisionDB.getFirstLevelDivisions();

        for (FirstLevelDivision div: allDivisions) {
            if (div.getDivisionId() == divId) {
                return div.getDivisionName();
            }
        }
        System.out.println("Division ID does not exist in database");
        return null;
    }

    /**
     * @param countryId Integer associated with the Id variable of a country object.
     * @return returns the String name associated with a countryId
     *
     * The method takes a countryId Integer and converts it to the associated country name.
     */
    public static String countryIdToCountryName (int countryId) {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        allCountries = CountryDB.getAllCountries();

        for (Country country: allCountries) {
            if (country.getCountryId() == countryId) {
                return country.getCountryName();
            }
        }
        System.out.println("Country ID does not exist in database.");
        return null;
    }

    /**
     * @param divisionName String associated with the name of a firstleveldivision object.
     * @return returns the ID Integer associated with a firstleveldivision.
     *
     * This method takes a String name and converts it to the Integer Id associated with the firstleveldivision object.
     */
    public static int divisionNameToDivisionId (String divisionName) {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        allDivisions = FirstLevelDivisionDB.getFirstLevelDivisions();

        for (FirstLevelDivision div: allDivisions) {
            if (div.getDivisionName().equalsIgnoreCase(divisionName)) {
                return div.getDivisionId();
            }
        }
        System.out.println("Division ID does not exist in database.");
        return 0;
    }

    /**
     * @param countryName String name associated with a country object.
     * @return returns the country ID integer associated with the country name String passed.
     *
     * This method converts a country name String to the associated country ID Integer.
     */
    public static int countryNameToCountryId (String countryName) {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        allCountries = CountryDB.getAllCountries();

        for (Country country: allCountries) {
            if (country.getCountryName().equalsIgnoreCase(countryName)) {
                return country.getCountryId();
            }
        }
        System.out.println("Country ID does not exist in database");
        return 0;
    }

    /**
     * @param divsionId Integer associated with the divisionId of a firstleveldivision  object
     * @return returns the String country name related to the passed division ID.
     *
     * Converts a division ID and the associated firstleveldivision to the related country name.
     */
    public static String divisionIdToCountryName (int divsionId) {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

        allDivisions = FirstLevelDivisionDB.getFirstLevelDivisions();

        for (FirstLevelDivision div: allDivisions) {
            if (div.getDivisionId() == divsionId) {
                return countryIdToCountryName(div.getCountryId());
            }
        }
        System.out.println("Division ID does not exist in the database.");
        return null;
    }


    /**
     * @param customerName String name associated with a customer object.
     * @return returns the customer ID associated with a customer object based on the String name passed.
     *
     * Converts the String customer name passed to it's respective Integer customer ID.
     */
    public static int customerNameToCustomerId (String customerName) {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        allCustomers = CustomerDB.getAllCustomers();

        for(Customer customer: allCustomers) {
            if(customer.getName().toLowerCase().equals(customerName.toLowerCase())) {
                return customer.getId();
            }
        }
        System.out.println("Customer name does not exist in database");
        return 0;
    }

    /**
     * @param contactName String name associated with a contact object.
     * @return returns the Integer contact ID associated with a a contact object based on the String passed.
     *
     * Converts a String contact name to it's related Integer customer ID.
     */
    public static int contactNameToContactId (String contactName) {
        ObservableList <Contacts> allContacts = FXCollections.observableArrayList();

        allContacts = ContactDB.getAllContacts();

        for (Contacts contact: allContacts) {
            if (contactName.equalsIgnoreCase(contact.getContactName())) {
                return contact.getContactId();
            }
        }
        System.out.println("Contact name does not exist in database");
        return 0;
    }

    /**
     * @param contactId Integer contact ID associated with a contact object.
     * @return return the String contact name associated with the Integer contactID passed.
     *
     * This method converts a Integer contact Id to the associated contact name String.
     */
    public static String contactIdToContactName (int contactId) {
        ObservableList <Contacts> allContacts = FXCollections.observableArrayList();

        allContacts = ContactDB.getAllContacts();

        for (Contacts contact: allContacts) {
            if (contactId == contact.getContactId()) {
                return contact.getContactName();
            }
        }
        return null;
    }
}
