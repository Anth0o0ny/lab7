package parse;

import baseclasses.Movie;
import baseclasses.MoviesCollection;
import moviemaking.AddMovie;
import moviemaking.IdGenerator;
import sub.StringConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Stack;

public class Parser {
    static StringBuilder sb = new StringBuilder();
    static String res;


    public static void parsingToObj(Stack<Movie> collection, String pathToFile) {

        File file = new File(pathToFile);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            int symb;
            while ((symb = bis.read()) != -1) {
                sb.append((char) symb);
            }
            res = sb.toString();
        } catch (IOException e) {
            System.out.println(StringConstants.StartTreatment.COLLECTION_INPUT_NOT_EXISTS);

        }

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(MoviesCollection.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            MoviesCollection moviesCollection = (MoviesCollection) jaxbUnmarshaller.unmarshal(new StringReader(res));

            for (Movie movie : moviesCollection.getCollection()) {
                collection.push(movie);
            }
            AddMovie.setIdGenerator(new IdGenerator(moviesCollection.getCollection()));
        } catch (JAXBException e) {
            System.out.println(StringConstants.StartTreatment.PARSE_FAILED);
            AddMovie.setIdGenerator(new IdGenerator(new MoviesCollection().getCollection()));

        }
    }

    public static void parsingToXml(MoviesCollection moviesCollection){
        try {
            JAXBContext context = JAXBContext.newInstance(MoviesCollection.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(moviesCollection, new FileOutputStream("movie.xml"));

        }catch(JAXBException | FileNotFoundException x){
            System.out.println(StringConstants.StartTreatment.COLLECTION_OUTPUT_NOT_EXISTS);
        }
    }


}