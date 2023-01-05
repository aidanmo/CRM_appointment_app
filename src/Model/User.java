package Model;

public class User {
    private String userName;
    private Integer userID;
    private String password;

    /**
     * @param userName String userName associated with a user object.
     * @param userID Integer userID associated with a user object.
     * @param password String password associated with a user object.
     *
     * Constructor used to create an User object taking 3 variables as parameters.
     */
    public User(String userName, Integer userID, String password) {
        this.userName = userName;
        this.userID = userID;
        this.password = password;
    }

    /**
     * @return returns String userName associated with a user object.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return returns String password associated with a user object.
     */
    public String getPassword() {
        return password;
    }
}
