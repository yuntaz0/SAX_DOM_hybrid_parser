import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class Main {

    private static final String SQL_START = "sql_commands_start.sql";
    public static final String SQL_MOVIE = "movie_sql_commands.sql";
    private static final String SQL_END = "sql_commands_end.sql";
    public static final String SQL_STAR = "star_sql_commands.sql";
    public static final String SQL_CAST = "cast_sql_commands.sql";
    private static final String SQL_COMMIT = "commit_sql_command.sql";
    private static final String ENCODING = "ISO-8859-1";
    private static final String moviesDataLocation = "./stanford-movies/mains243.xml";
    private static final String starsDataLocation = "./stanford-movies/actors63.xml";
    private static final String castsDataLocation = "./stanford-movies/casts124.xml";

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter(SQL_COMMIT)) {
            writer.write("COMMIT;" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(SQL_START)) {
            writer.write("USE moviedb; SET GLOBAL autocommit = 0;" + System.lineSeparator()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File existMovieFile = new File(SQL_MOVIE);
            if (existMovieFile.exists()) {
                existMovieFile.delete();
                System.out.println("Existing file deleted: " + SQL_MOVIE);
            }
            File existStarsFile = new File(SQL_STAR);
            if (existStarsFile.exists()) {
                existStarsFile.delete();
                System.out.println("Existing file deleted: " + SQL_STAR);
            }
            File existCastsFile = new File(SQL_CAST);
            if (existCastsFile.exists()) {
                existCastsFile.delete();
                System.out.println("Existing file deleted: " + SQL_CAST);
            }

            File moviesFile = new File(moviesDataLocation);
            SAXParserFactory movieFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = movieFactory.newSAXParser();
            XMLReader movieXmlReader = saxParser.getXMLReader();
            MovieMasterSAXParser movieSaxParser = new MovieMasterSAXParser();
            movieXmlReader.setContentHandler(movieSaxParser);
            InputStreamReader movieReader = new InputStreamReader(new FileInputStream(moviesFile), ENCODING);
            System.out.println("MOVIE FILE: SUCCESS");
            InputSource inputSource = new InputSource(movieReader);
            System.out.println("Starting Movie XML parsing...");
            movieXmlReader.parse(inputSource);

            File starsFile = new File(starsDataLocation);
            SAXParserFactory starsFactory = SAXParserFactory.newInstance();
            SAXParser starsSaxParser = starsFactory.newSAXParser();
            XMLReader starsXmlReader = starsSaxParser.getXMLReader();
            StarMasterSAXParser starParser = new StarMasterSAXParser();
            starsXmlReader.setContentHandler(starParser);
            InputStreamReader starsReader = new InputStreamReader(new FileInputStream(starsFile), ENCODING);
            System.out.println("Stars FILE: SUCCESS");
            InputSource starsInputSource = new InputSource(starsReader);
            System.out.println("Starting Stars XML parsing...");
            starsXmlReader.parse(starsInputSource);

            File castsFile = new File(castsDataLocation);
            SAXParserFactory castsFactory = SAXParserFactory.newInstance();
            SAXParser castsSaxParser = castsFactory.newSAXParser();
            XMLReader castsXmlReader = castsSaxParser.getXMLReader();
            CastMasterSAXParser castParser = new CastMasterSAXParser();
            castsXmlReader.setContentHandler(castParser);
            InputStreamReader castsReader = new InputStreamReader(new FileInputStream(castsFile), ENCODING);
            System.out.println("Casts FILE: SUCCESS");
            InputSource castsInputSource = new InputSource(castsReader);
            System.out.println("Starting Casts XML parsing...");
            castsXmlReader.parse(castsInputSource);

            System.out.println("XML parsing and processing completed." + System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(SQL_END)) {
            writer.write("SET GLOBAL autocommit = 1;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
