package moviemaking;

import baseclasses.Movie;

import java.util.Stack;
import java.util.TreeSet;

public class IdGenerator {

    final TreeSet<Long> idsSet;
    long maxValue;

    public IdGenerator(Stack<Movie> collection) {
        if (collection.isEmpty()) {
            this.idsSet = new TreeSet<>();
            this.maxValue = 0;
        } else {
            this.idsSet = new TreeSet<>();
            for (Movie movie : collection) {
                idsSet.add(movie.getId());
            }
            this.maxValue = idsSet.last();
        }
    }

    public long generateId() {
        long id = maxValue + 1;
        if ( idsSet.add(id) ) {
            return id;
        } else {
            this.maxValue++ ;
            return generateId();
        }
    }

}

