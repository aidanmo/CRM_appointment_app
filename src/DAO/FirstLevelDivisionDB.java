package DAO;

import Model.FirstLevelDivision;
import Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDB {

    /**
     * @return returns a observablelist of all FirstLevelDivisions stored in the queried database.
     *
     * This method queries the connected database for all FirstLevelDivisions stored in the FirstLevelDivision table.
     * Returning an ObservableList of FirstLevelDivision objects representing each row in the database.
     */
    public static ObservableList <FirstLevelDivision> getFirstLevelDivisions () {

        ObservableList <FirstLevelDivision> firstLevelDivisions = FXCollections.observableArrayList();
        FirstLevelDivision firstLvl;

        try {
            String sql = "SELECT * FROM client_schedule.first_level_divisions;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                firstLvl = new FirstLevelDivision(rs.getInt("Division_ID"),
                        rs.getString("Division"), rs.getInt("Country_ID"));
                firstLevelDivisions.add(firstLvl);
            }
            ps.close();
            return firstLevelDivisions;

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
}
