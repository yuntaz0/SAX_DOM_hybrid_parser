import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {
    public static final String encoding = "ISO-8859-1";
    private static final String moviesDataLocation = "./stanford-movies/mains243.xml";
    private static final String starsDataLocation = "./stanford-movies/actors63.xml";
    public static void main(String[] args) {
        try {
            File movieFile = new File(moviesDataLocation);
            SAXParserFactory movieFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = movieFactory.newSAXParser();
            XMLReader movieXmlReader = saxParser.getXMLReader();
            MovieMasterSAXParser movieSaxParser = new MovieMasterSAXParser();
            movieXmlReader.setContentHandler(movieSaxParser);
            InputStreamReader movieReader = new InputStreamReader(new FileInputStream(movieFile), encoding);
            System.out.println("MOVIE FILE: SUCCESS");
            InputSource inputSource = new InputSource(movieReader);
            System.out.println("Starting Movie XML parsing...");
            movieXmlReader.parse(inputSource);
//
//            File starsFile = new File(starsDataLocation);
//            SAXParserFactory starsFactory = SAXParserFactory.newInstance();
//            SAXParser starsSaxParser = starsFactory.newSAXParser();
//            XMLReader starsXmlReader = starsSaxParser.getXMLReader();
//            StarMasterSAXParser starParser = new StarMasterSAXParser();
//            starsXmlReader.setContentHandler(starParser);
//            InputStreamReader starsReader = new InputStreamReader(new FileInputStream(starsFile), encoding);
//            System.out.println("Stars FILE: SUCCESS");
//            InputSource starsInputSource = new InputSource(starsReader);
//            System.out.println("Starting Stars XML parsing...");
//            starsXmlReader.parse(starsInputSource);

            System.out.println("Stars XML parsing and processing completed.");

            System.out.println("XML parsing and processing completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
