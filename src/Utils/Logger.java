package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class Logger {

    private static final String file = "login_activity.txt";

    /**
     * @param username String gathered from username text-field located on the login form.
     * @param success Boolean result from isUserCredentialsValid confirming whether a set of matching username and
     * password was found in the user table.
     * @throws IOException
     * Prints a String of login attempt details to the Login_activity.txt when ever the user clicks login on the login
     * screen.
     */
    public static void log( String username, boolean success) throws IOException {
        String loginAttempt;
        try {
             FileWriter fw = new FileWriter(file, true);
             PrintWriter pw = new PrintWriter(fw);
             //Creating object that can return systems IP address.
             InetAddress IP =InetAddress.getLocalHost();

             if (success) {
                 loginAttempt = "Successful";
             } else {
                 loginAttempt = "Failed";
             }
             pw.append("username: " + username + " | login Attempt: " + loginAttempt + " | Time/Date: " +
                     LocalDateTime.now().withNano(0) + " | IP: " + IP.getHostAddress());
             pw.println("");
             pw.close();

        } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
