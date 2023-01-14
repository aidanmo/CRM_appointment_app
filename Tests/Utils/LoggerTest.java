package Utils;

import com.mysql.cj.log.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class LogTest {
    private File file;
    private String username = "testuser";

    @Before
    public void setUp() throws IOException {
        file = new File("/CRM_appointment_app/test_login_activity.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        //Creates a new test file to check if logs are being correctly printed.
    }

    @After
    public void tearDown() {
        file.delete();
        //Deletes test txt file after test is complete
    }

    @Test
    public void testLogSuccess() throws IOException {
        Logger.log(username, true);
        String content = new String(Files.readAllBytes(Paths.get("login_activity.txt")));
        assertTrue(content.contains("username: " + username + " | login Attempt: Successful"));
        assertTrue(content.contains("Time/Date: " + LocalDateTime.now().withNano(0)));
    }

    @Test
    public void testLogFailure() throws IOException {
        Logger.log(username, false);
        String content = new String(Files.readAllBytes(Paths.get("login_activity.txt")));
        assertTrue(content.contains("username: " + username + " | login Attempt: Failed"));
        assertTrue(content.contains("Time/Date: " + LocalDateTime.now().withNano(0)));
    }
}