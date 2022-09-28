package baseclasses;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Stack;


@XmlRootElement(name = "movies")
@XmlAccessorType(XmlAccessType.FIELD)

public class MoviesCollection {

    @XmlElement(name = "movie", type = Movie.class)
    private Stack<Movie> collection = new Stack<>();

    public Stack<Movie> getCollection() {
        return collection;
    }

    public void setCollection(Stack<Movie> collection) {
        this.collection = collection;
    }
}
