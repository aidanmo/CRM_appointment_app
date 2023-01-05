package Model;

public class FirstLevelDivision {
    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * @param divisionId Integer division ID associated with a FirstLevelDivision object.
     * @param divisionName String divisionName associated with a FirstLevelDivision object.
     * @param countryId Integer country ID associated with a FirstLevelDivision object.
     *
     * Constructor used to create an FirstLevelDivision object taking 3 variables as parameters.
     */
    public FirstLevelDivision (int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * @return return the Integer divisonID associated with a FirstLevelDivision object.
     */
    public int getDivisionId() { return divisionId; }

    /**
     * @return returns the String divisionName associated with a FirstLevelDivision object.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @return returns Integer countryId associated with a FirstLevelDivision object.
     */
    public int getCountryId() {
        return countryId;
    }
}
