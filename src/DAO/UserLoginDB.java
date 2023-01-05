package DAO;

import Model.FirstLevelDivision;
import Model.User;
import Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginDB {

    /**
     * @return returns a observablelist of all users stored in the queried database.
     *
     * This method queries the connected database for all users stored in the user table. Returning an
     * ObservableList of user objects representing each row in the database.
     */
    public static ObservableList <User> getAllUsers() {
        ObservableList <User> allUsers = FXCollections.observableArrayList();
        User user;

        try {
            String sql = "SELECT * FROM client_schedule.users;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString("User_Name"), rs.getInt("User_ID"),
                        rs.getString("Password"));
                allUsers.add(user);
            }
            ps.close();
            return allUsers;

        } catch (SQLException e ) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return null;
    }
}
