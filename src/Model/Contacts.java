package Model;

public class Contacts {
    public int contactId;
    public String contactName;
    public String email;

    /**
     * @param contactId Integer contact ID associated with an contact object.
     * @param contactName String contact name associated with an contact object.
     * @param email String contact email associated with a contact object.
     *
     * Constructor that creates an contacts object.
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * @return returns the Integer ContactID associated with a contact object.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @return returns the String ContactName associated with a contact object.
     */
    public String getContactName() {
        return contactName;
    }

}
