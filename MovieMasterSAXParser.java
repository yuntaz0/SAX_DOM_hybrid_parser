import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class MovieMasterSAXParser extends DefaultHandler {
    private static final int BATCH_SIZE = 10;
    private final List<String> batch;
    private boolean isRecording = false;
    private StringBuilder directorFilmsXml = new StringBuilder();
    private String startingId = "0000000000";

    public MovieMasterSAXParser() {
        this.batch = new ArrayList<>(BATCH_SIZE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("directorfilms")) {
            isRecording = true;
            directorFilmsXml.setLength(0);
        }
        if (isRecording) {
            directorFilmsXml.append("<").append(qName).append(">");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isRecording) {
            String content = new String(ch, start, length)
                    .replaceAll("\\s+", " ")
                    .trim();
            directorFilmsXml.append(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (isRecording) {
            directorFilmsXml.append("</").append(qName).append(">");
            if (qName.equalsIgnoreCase("directorfilms")) {
                batch.add(directorFilmsXml.toString());
                directorFilmsXml.setLength(0);
                isRecording = false;

                if (batch.size() == BATCH_SIZE) {
                    try {
                        processBatch(batch);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void endDocument() {
        if (!batch.isEmpty()) {
            processBatch(batch);
            batch.clear();
        }
    }

    private void processBatch(List<String> batch) {
        MovieWorkerDOMParser worker = new MovieWorkerDOMParser(startingId);
        startingId = worker.process(batch);
        batch.clear();
    }
}
