package Utils;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static Utils.EntryValidator.isDuringBusinessHours;
import static Utils.EntryValidator.isOverlapping;
import static org.junit.Assert.*;

public class EntryValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsDuringBusinessHours() {

        LocalDateTime start = LocalDateTime.of(2022, Month.JANUARY, 15, 9, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.JANUARY, 15, 18, 0);
        boolean result = isDuringBusinessHours(start, end);
        assertFalse(result); // expected: appointment is during business hours

        start = LocalDateTime.of(2022, Month.JANUARY, 15, 7, 0);
        end = LocalDateTime.of(2022, Month.JANUARY, 15, 18, 0);
        result = isDuringBusinessHours(start, end);
        assertTrue(result); // expected: appointment starts before business hours

        start = LocalDateTime.of(2022, Month.JANUARY, 15, 9, 0);
        end = LocalDateTime.of(2022, Month.JANUARY, 15, 23, 0);
        result = isDuringBusinessHours(start, end);
        assertTrue(result); // expected: appointment ends after business hours

        start = LocalDateTime.of(2022, Month.JANUARY, 15, 7, 0);
        end = LocalDateTime.of(2022, Month.JANUARY, 15, 23, 0);
        result = isDuringBusinessHours(start, end);
        assertTrue(result); // expected: appointment starts and ends outside of business hours
    }

    @Test
    public void testIsOverlapping() {
        LocalDateTime start = LocalDateTime.of(2022, Month.JANUARY, 15, 9, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.JANUARY, 15, 18, 0);
        int customerId = 1;

        Appointment app1 = new Appointment(1, "breifing", "short",
                "Denver", "meeting", start, end, customerId, 1,
                "Jimmy");

        Appointment app2 = new Appointment(2, "breifing", "short",
                "Denver", "meeting", start.plusHours(1),  end.plusHours(1),
                customerId, 1, "Jimmy");

        Appointment app3 = new Appointment(3, "breifing", "short",
                "Denver", "meeting", start.minusHours(1), end.minusHours(1),
                customerId, 1, "Jimmy");

        Appointment app4 = new Appointment(4, "breifing", "short",
                "Denver", "meeting", start.plusHours(2), end.plusHours(2),
                customerId, 1, "Jimmy");

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.addAll(app1, app2, app3, app4);

        // Test overlapping appointments
        LocalDateTime newStart = LocalDateTime.of(2022, Month.JANUARY, 15, 10, 0);
        LocalDateTime newEnd = LocalDateTime.of(2022, Month.JANUARY, 15, 17, 0);
        boolean result = isOverlapping(newStart, newEnd, allAppointments, customerId);
        assertTrue(result); // expected: new appointment overlaps with existing appointments

        // Test non-overlapping appointments
        newStart = LocalDateTime.of(2022, Month.JANUARY, 15, 8, 0);
        newEnd = LocalDateTime.of(2022, Month.JANUARY, 15, 9, 0);
        result = isOverlapping(newStart, newEnd, allAppointments, customerId);
        assertFalse(result); // expected: new appointment does not overlap with existing appointments

        // Test appointments with different customerId
        int differentCustomerId = 2;
        result = isOverlapping(newStart, newEnd, allAppointments, differentCustomerId);
        assertFalse(result); // expected: new appointment does not overlap with existing appointments with different customerId
    }

}