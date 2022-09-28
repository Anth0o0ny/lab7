package baseclasses;



public enum Country {

    RUSSIA("Россия"),
    USA("США"),
    GERMANY("Германия"),
    ITALY("Италия"),
    NORTH_KOREA("Северная Корея");

    private final String title;

    Country(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

    public static Country fromString(String countryStr) {
        for (Country country : Country.values()) {
            if (country.toString().equalsIgnoreCase(countryStr))
                return country;
        }
        return null;
    }
}
