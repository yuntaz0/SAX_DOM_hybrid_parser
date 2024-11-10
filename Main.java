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
    // private static final String starsDataLocation = "./stanford-movies/casts124.xml";
    public static void main(String[] args) {
        try {
            File inputFile = new File(moviesDataLocation);
            System.out.println("FILE: SUCCESS");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            MasterSAXParser masterParser = new MasterSAXParser();
            xmlReader.setContentHandler(masterParser);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile), encoding);
            InputSource inputSource = new InputSource(reader);

            System.out.println("Starting XML parsing...");
            xmlReader.parse(inputSource);

            System.out.println("XML parsing and processing completed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
