import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class StarWorkerDOMParser {
    private List<Star> stars = new ArrayList<>();

    public void process(List<String> batch) {
        for (String actorXml : batch) {
            try{
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(actorXml)));
                doc.getDocumentElement().normalize();
                Element actorElement = doc.getDocumentElement();

                NodeList stagenameList = actorElement.getElementsByTagName("stagename");
                String stagename = parseStagename(stagenameList);
                NodeList birthYearList = actorElement.getElementsByTagName("dob");
                Integer birthYear = parseBirthYear(birthYearList);
                String starId;
                if (stagename == null || stagename.contains("\\")) {
                    continue;
                } else if (stagename.length() > 20) {
                    starId = stagename.substring(0,20).replaceAll("\\s", "_");
                } else {
                    starId = stagename.replaceAll("\\s", "");
                }
                stars.add(new Star(starId, stagename, birthYear));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InsertStarBatch insertStarBatch = new InsertStarBatch();
        insertStarBatch.batchInsertStars(stars);
    }

    private static String parseStagename(NodeList stagenameNodeList) {
        if (stagenameNodeList == null || stagenameNodeList.getLength() == 0) {
            System.err.println("No stage name found");
            return null;
        }

        String stagename = stagenameNodeList.item(0).getTextContent().trim();

        if (!stagename.isEmpty()) {
            return stagename;
        } else {
            System.err.println("Stage name is empty");
            return null;
        }
    }

    private static Integer parseBirthYear(NodeList birthYearNodeList) {
        if (birthYearNodeList == null || birthYearNodeList.getLength() == 0) {
            System.err.println("No birth year found");
            return null;
        }

        String birthYearStr = birthYearNodeList.item(0).getTextContent().trim();

        if (!birthYearStr.isEmpty()) {
            try {
                return Integer.parseInt(birthYearStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid birth year format: " + birthYearStr);
                return null;
            }
        } else {
            System.err.println("Birth year is empty");
            return null;
        }
    }
}
