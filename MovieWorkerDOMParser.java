import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovieWorkerDOMParser {

    private String startingId;
    private List<Movie> movies = new ArrayList<>();

    public MovieWorkerDOMParser(String startingId) {
        this.startingId = startingId;
    }

    public String process(List<String> batch) {
        for (String directorXml : batch) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(directorXml)));
                doc.getDocumentElement().normalize();
                Element directorfilmsElement = doc.getDocumentElement();

                NodeList dirnameList = directorfilmsElement.getElementsByTagName("dirname");
                String dirname = parseDirector(dirnameList);

                NodeList filmsList = directorfilmsElement.getElementsByTagName("film");
                for (int i = 0; i < filmsList.getLength(); i++) {
                    Element filmElement = (Element) filmsList.item(i);
                    NodeList filmNameList = filmElement.getElementsByTagName("t");
                    String filmName = parseFilmName(filmNameList);
                    NodeList filmYearList = filmElement.getElementsByTagName("year");
                    Integer filmYear = parseYear(filmYearList);
                    NodeList filmGenresList = filmElement.getElementsByTagName("cats");
                    ArrayList<String> filmGenres = parseGenres(filmGenresList);
                    NodeList filmIdList = filmElement.getElementsByTagName("fid");
                    String filmId = "SFM" + startingId;
                    startingId = filmId;
                    Random random = new Random();
                    float filmPrice = (10 + random.nextInt(191)) / 10.0f;
                    float filmRating = random.nextInt(101) / 10.0f;
                    int filmVotes = 100 + random.nextInt(901);

                    Movie movie = new Movie(filmId);
                    movie.setTitle(filmName);
                    movie.setYear(filmYear);
                    movie.setDirector(dirname);
                    movie.setGenres(filmGenres);
                    movie.setPrice(filmPrice);
                    movie.setRating(filmRating);
                    movie.setVotes(filmVotes);
                    movies.add(movie);
                }

                InsertMovieBatch insertMovieBatch = new InsertMovieBatch();
                insertMovieBatch.batchInsertMovies(movies);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return startingId;
    }

    private static Integer parseYear(NodeList yearNodeList) {
        if (yearNodeList == null || yearNodeList.getLength() == 0) {
            System.err.println("No year found");
            return null;
        }

        String yearText = yearNodeList.item(0).getTextContent().trim();

        if (yearText.matches("\\d{4}")) {
            return Integer.valueOf(yearText);
        } else {
            System.err.println("Invalid year format: " + yearText);
            return null;
        }
    }

    private static String parseDirector(NodeList directorNodeList) {
        if (directorNodeList == null || directorNodeList.getLength() == 0) {
            System.err.println("No director found");
            return null;
        }

        String directorName = directorNodeList.item(0).getTextContent().trim();

        if (!directorName.isEmpty()) {
            return directorName;
        } else {
            System.err.println("Director name is empty");
            return null;
        }
    }

    private static String parseFilmName(NodeList filmNameNodeList) {
        if (filmNameNodeList == null || filmNameNodeList.getLength() == 0) {
            System.err.println("No film name found");
            return null;
        }

        String filmName = filmNameNodeList.item(0).getTextContent().trim();

        if (!filmName.isEmpty()) {
            return filmName;
        } else {
            System.err.println("Film name is empty");
            return null;
        }
    }

    private static ArrayList<String> parseGenres(NodeList genresNodeList) {
        ArrayList<String> genres = new ArrayList<>();

        if (genresNodeList == null || genresNodeList.getLength() == 0) {
            System.err.println("No genres found");
            return genres;
        }

        for (int i = 0; i < genresNodeList.getLength(); i++) {
            Node genreNode = genresNodeList.item(i);
            String genre = genreNode.getTextContent().trim();

            if (!genre.isEmpty()) {
                genres.add(genre);
            } else {
                System.err.println("Empty genre found");
            }
        }

        return genres;
    }
}
