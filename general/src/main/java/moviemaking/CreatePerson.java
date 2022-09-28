package moviemaking;

import baseclasses.Color;
import baseclasses.Country;
import baseclasses.MpaaRating;
import baseclasses.Person;
import input.InputArgumentTester;

public class CreatePerson {
    public static Person ctreatePerson() {
        InputArgumentTester iat = new InputArgumentTester();

        Color color = chooseColor();

        Country country = chooseCountry();

        return new Person(iat.assignInputPersonName(), iat.assignInputHeight(), color, country);
    }

    public static Color chooseColor() {
        InputArgumentTester iat = new InputArgumentTester();
        return iat.assignInputColor();
    }

    public static MpaaRating chooseRating() {
        InputArgumentTester iat = new InputArgumentTester();
        return iat.assignMpaaRating();
    }

    public static Country chooseCountry() {
        InputArgumentTester iat = new InputArgumentTester();
        return iat.assignInputCountry();
    }

}
