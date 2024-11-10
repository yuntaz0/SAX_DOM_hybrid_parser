import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CastWorkerDOMParser {

    private List<Cast> casts = new ArrayList<>();

    public void process(List<String> batch) {
        for (String castXml : batch) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(castXml)));
                doc.getDocumentElement().normalize();
                Element castElement = doc.getDocumentElement();

                NodeList starNameList = castElement.getElementsByTagName("a");
                String starId = parseStarId(starNameList);

                NodeList fidList = castElement.getElementsByTagName("f");
                String fid = parseFid(fidList);

                if (fid != null && starId != null) {
                    Cast cast = new Cast(fid, starId);
                    casts.add(cast);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InsertCastBatch insertCastBatch = new InsertCastBatch();
        insertCastBatch.batchInsertCasts(casts);
    }

    private static String parseStarId(NodeList starNameList) {
        if (starNameList == null || starNameList.getLength() == 0) {
            System.err.println("No actor name found");
            return null;
        }

        String starId = starNameList.item(0).getTextContent().trim();

        if (!starId.isEmpty()) {
            if (starId.contains("\\")) {
                return null;
            }
            if (starId.length() > 20) {
                starId = starId.substring(0, 20);
            }
            return starId.replaceAll("\\s", "");
        } else {
            System.err.println("Actor name is incorrect");
            return null;
        }
    }

    private static String parseFid(NodeList fidList) {
        if (fidList == null || fidList.getLength() == 0) {
            System.err.println("No actor name found");
            return null;
        }

        String actorName = fidList.item(0).getTextContent().trim();

        if (!actorName.isEmpty()) {
            return "SFM" + actorName;
        } else {
            System.err.println("Actor name is empty");
            return null;
        }
    }
}
