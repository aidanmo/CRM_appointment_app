package DAO;

import Model.Contacts;
import Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    /**
     * @return returns a observablelist of all contacts stored in the queried database.
     *
     * This method queries the connected database for all contacts stored in the contact table. Returning an
     * ObservableList of contact objects representing each row in the database.
     */
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList <Contacts> contacts = FXCollections.observableArrayList();
        Contacts contact;

        try {
            String sql = "SELECT * FROM client_schedule.contacts;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contact = new Contacts(rs.getInt("Contact_ID"), rs.getString("Contact_Name"),
                        rs.getString("Email"));
                contacts.add(contact);
            }
            ps.close();
            return contacts;

        } catch (SQLException e) {
            System.out.println("ERORR: " + e.getMessage());
            return null;
        }
    }
}
