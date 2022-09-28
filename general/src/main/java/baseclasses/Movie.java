package baseclasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@XmlRootElement(name = "movie")
@XmlAccessorType(XmlAccessType.FIELD)

public class Movie implements Comparable<Movie>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private long budget; //Значение поля должно быть больше 0
    private String tagline; //Длина строки не должна быть больше 158, Поле может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person screenwriter;
    private String login;


    public Movie(String name, Coordinates coordinates, java.util.Date creationDate, Long oscarsCount,
                 long budget, String tagline, MpaaRating mpaaRating, Person screenwriter) {
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setOscarsCount(oscarsCount);
        setBudget(budget);
        setTagline(tagline);
        setMpaaRating(mpaaRating);
        setScreenwriter(screenwriter);
    }
    public Movie(long id, String name, Double x, Float y, Date creationDate, Long oscarCount, long budget,
                 String tagline, String mpaaRating, String personName, float height, String hairColor,
                 String nationality, String login){
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        this.creationDate = creationDate;
        this.oscarsCount = oscarCount;
        this.budget = budget;
        this.tagline = tagline;
        this.mpaaRating = MpaaRating.fromString(mpaaRating);
        this.screenwriter = new Person(personName, height, hairColor, nationality);
        this.login = login;


    }


    @Override
    public int compareTo(Movie movie) {
        return (this.getName().compareTo(movie.getName()));
    }

    @Override
    public String toString() {
        return "Фильм: " + name + ", id = " + id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
