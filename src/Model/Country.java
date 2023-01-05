package Model;

public class Country {
    private int countryId;
    private String countryName;

    /**
     * @param countryId Integer variable associated with the ID of a country object.
     * @param countryName String vairbale associated with the name of a country object.
     *
     * Constructor used to create a country object.
     */
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * @return return the Integer ID associated with a country object.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @return return the String name associated with a country object.
     */
    public String getCountryName() {
        return countryName;
    }
}
