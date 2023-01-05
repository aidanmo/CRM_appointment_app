package Model;

import javafx.collections.ObservableList;

public class Customer {

    private Integer customerId;
    private String name;
    private String address;
    private String zip;
    private String phoneNumber;
    private int divisionId;
    private String countryName;
    private String divisionName;


    /**
     * @param customerId Integer customer Id associated with a Customer object.
     * @param name String customer name associated with a Customer object.
     * @param address String customer address associated with a Customer object.
     * @param zip String customer zip associated with a Customer object.
     * @param phoneNumber String customer phoneNumber associated with a Customer object.
     * @param divisionId Integer division ID associated with a Customer object.
     * @param countryName String countryName associated with a Customer object.
     * @param divisionName String divisionName associated with a Customer object.
     *
     * Constructor method used to create a customer object. Takes 8 variables as parameters.
     */
    public Customer(Integer customerId, String name, String address, String zip, String phoneNumber,
                     int divisionId, String countryName, String divisionName) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    /**
     * @param customers Takes an ObservableList of customer objects as a parameter.
     * @return returns a Integer value.
     *
     * This method generates a customer ID integer by looping through all the existing customers and checking
     * their associated ID. Once it finds the customer object with the highest customer ID integer value the method
     * will increment it by one and return the value.
     */
    public static int generateCustomerId(ObservableList<Customer> customers) {
        int greatest = 0;

        for (Customer customer: customers) {
            if (customer.getId() > greatest) {
                greatest = customer.getId();
            }
        }
        greatest++;
        return greatest;
    }

    /**
     * @return returns the Integer customerId associated with the customer object.
     */
    public Integer getId() {
        return customerId;
    }

    /**
     * @param id Integer variable passed as a parameter.
     *
     * This method is used to set the value of the Integer associated with a customer object
     */
    public void setId(Integer id) {
        customerId = customerId;
    }

    /**
     * @return returns the String name associated with a customer object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name String name passed as a parameter.
     *
     * This method is used to set the value of the String name associated with a customer object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns the String address associated with a customer object.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return returns the String address associated with a customer object.
     */
    public String getZip() {
        return zip;
    }

    /**
     * @return returns the String phoneNumber associated with a customer object.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return returns the Integer divisionID associated with a customer object.
     */
    public int getDivisionId() {
        return divisionId;
    }

}
