package DAO;

import Model.Country;
import Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDB {

    /**
     * @return returns a observablelist of all countries stored in the queried database.
     *
     * This method queries the connected database for all Countries stored in the country table. Returning an
     * ObservableList of country objects representing each row in the database.
     */
    public static ObservableList <Country> getAllCountries() {
        ObservableList <Country> countries = FXCollections.observableArrayList();
        Country country;

        try {
            String sql = "SELECT * FROM client_schedule.countries;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
                countries.add(country);
            }
            ps.close();
            return countries;
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
}
