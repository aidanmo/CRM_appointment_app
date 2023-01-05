package DAO;

import Model.Customer;
import Utils.IDConverter;
import Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerDB {

    /**
     * @return returns a observablelist of all customers stored in the queried database.
     *
     * This method queries the connected database for all Customers stored in the customer table. Returning an
     * ObservableList of customer objects representing each row in the database.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList customers = FXCollections.observableArrayList();
        Customer customer;

        try {
            String sql = "SELECT * FROM client_schedule.customers;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customer = new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),
                        rs.getString("Address"), rs.getString("Postal_Code"),
                        rs.getString("Phone"), rs.getInt("Division_ID"),
                        IDConverter.divisionIdToCountryName(rs.getInt("Division_ID")),
                        IDConverter.divisionIdToDivisionName(rs.getInt("Division_ID")));
                customers.add(customer);
            }
            ps.close();
            return customers;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param customer Customer object passed as a parameter.
     * @return returns a integer value representing how many rows were changed in the database after the update
     * @throws SQLException
     *
     * This method deletes the Appointment object passed as a parameter from the connected database.
     */
    public static int deleteCustomer(Customer customer) throws SQLException {
        int customerId = customer.getId();

        String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = " + customerId + ";";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }

    /**
     * @param customer Customer object passed as a parameter.
     * @throws SQLException
     *
     * This method adds the Customer object passed as a parameter to the database connected.
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getZip());
        ps.setString(5, customer.getPhoneNumber());
        ps.setInt(6, customer.getId());
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, "Administrator");
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(10, 1);

        ps.executeUpdate();
    }

    /**
     * @param customer Customer object passed as a parameter.
     * @throws SQLException
     *
     * This method updates the columns of a specified customer in the customer table of the connected database.
     * This uses the customer ID to identify which customer is being updated.
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getZip());
        ps.setString(4, customer.getPhoneNumber());
        ps.setInt(5, customer.getDivisionId());
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(7, 1);
        ps.setInt(8, customer.getId());

        ps.execute();
    }






}
