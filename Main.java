import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class Main {

    public static final String TEMP_FILE_1 = "sql_commands_start.sql";
    public static final String TEMP_FILE_2 = "movie_sql_commands.sql";
    public static final String TEMP_FILE_3 = "sql_commands_end.sql";
    public static final String TEMP_FILE_4 = "star_sql_commands.sql";
    public static final String encoding = "ISO-8859-1";
    private static final String moviesDataLocation = "./stanford-movies/mains243.xml";
    private static final String starsDataLocation = "./stanford-movies/actors63.xml";

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter(TEMP_FILE_1)) {

            System.out.println("Writing file " + TEMP_FILE_1);
            writer.write("USE moviedb; SET GLOBAL autocommit = 0;" + System.lineSeparator() +
                    "DROP TABLE IF EXISTS stage_movies;" + System.lineSeparator() +
                    "DROP TABLE IF EXISTS stage_stars;" + System.lineSeparator() +
                    "CREATE TABLE stage_movies (" + System.lineSeparator() +
                    "    id VARCHAR(10) PRIMARY KEY, " + System.lineSeparator() +
                    "    title VARCHAR(100) DEFAULT '' NOT NULL, " + System.lineSeparator() +
                    "    year INT," + System.lineSeparator() +
                    "    director VARCHAR(100) DEFAULT '' NOT NULL);" + System.lineSeparator() +
                    "CREATE TABLE stage_stars (" + System.lineSeparator() +
                    "    id VARCHAR(20) PRIMARY KEY," + System.lineSeparator() +
                    "    name VARCHAR(100) DEFAULT '' NOT NULL," + System.lineSeparator() +
                    "    birthYear INT);" + System.lineSeparator()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File movieFile = new File(TEMP_FILE_2);
            if (movieFile.exists()) {
                movieFile.delete();
                System.out.println("Existing file deleted: " + TEMP_FILE_2);
            }
            File starFile = new File(TEMP_FILE_4);
            if (starFile.exists()) {
                starFile.delete();
                System.out.println("Existing file deleted: " + TEMP_FILE_2);
            }
//            File movieFile = new File(moviesDataLocation);
//            SAXParserFactory movieFactory = SAXParserFactory.newInstance();
//            SAXParser saxParser = movieFactory.newSAXParser();
//            XMLReader movieXmlReader = saxParser.getXMLReader();
//            MovieMasterSAXParser movieSaxParser = new MovieMasterSAXParser();
//            movieXmlReader.setContentHandler(movieSaxParser);
//            InputStreamReader movieReader = new InputStreamReader(new FileInputStream(movieFile), encoding);
//            System.out.println("MOVIE FILE: SUCCESS");
//            InputSource inputSource = new InputSource(movieReader);
//            System.out.println("Starting Movie XML parsing...");
//            movieXmlReader.parse(inputSource);

            File starsFile = new File(starsDataLocation);
            SAXParserFactory starsFactory = SAXParserFactory.newInstance();
            SAXParser starsSaxParser = starsFactory.newSAXParser();
            XMLReader starsXmlReader = starsSaxParser.getXMLReader();
            StarMasterSAXParser starParser = new StarMasterSAXParser();
            starsXmlReader.setContentHandler(starParser);
            InputStreamReader starsReader = new InputStreamReader(new FileInputStream(starsFile), encoding);
            System.out.println("Stars FILE: SUCCESS");
            InputSource starsInputSource = new InputSource(starsReader);
            System.out.println("Starting Stars XML parsing...");
            starsXmlReader.parse(starsInputSource);

            System.out.println("Stars XML parsing and processing completed.");

            System.out.println("XML parsing and processing completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(TEMP_FILE_3)) {
            writer.write("SET GLOBAL autocommit = 1;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
