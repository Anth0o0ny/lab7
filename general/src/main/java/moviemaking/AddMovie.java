package moviemaking;

import baseclasses.Coordinates;
import baseclasses.Movie;
import baseclasses.MpaaRating;
import baseclasses.Person;
import sub.StringConstants;
import input.InputArgumentTester;

import java.util.Collections;
import java.util.Date;
import java.util.Stack;

public class AddMovie {


    public static Movie makeMovie() {

        InputArgumentTester iat = new InputArgumentTester();

        String name = iat.assignInputName() ;
        Double x = iat.assignInputX();
        Float y = iat.assignInputY();
        Coordinates coordinates = new Coordinates(x, y);
        Date date = new Date();
        Long oscCount = iat.assignInputOscarCount();
        long budget = iat.assignInputBudget();
        String tagline = iat.assignTagline();
        MpaaRating rate = CreatePerson.chooseRating();
        Person person = CreatePerson.ctreatePerson();

        return new Movie( name, coordinates, date, oscCount, budget, tagline, rate, person);
    }
}
