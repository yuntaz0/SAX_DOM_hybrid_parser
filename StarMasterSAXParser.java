import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class StarMasterSAXParser extends DefaultHandler {

    private static final int BATCH_SIZE = 100;
    private final List<String> batch;
    private boolean isRecording = false;
    private StringBuilder actorXml = new StringBuilder();
    private String startingId = "0000000000";

    public StarMasterSAXParser() { this.batch = new ArrayList<>(BATCH_SIZE); }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("actor")) {
            isRecording = true;
            actorXml.setLength(0);
        }
        if (isRecording) {
            actorXml.append("<").append(qName).append(">");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isRecording) {
            String content = new String(ch, start, length)
                    .replaceAll("\\s+", " ")
                    .trim();
            actorXml.append(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (isRecording) {
            actorXml.append("</").append(qName).append(">");
            if (qName.equalsIgnoreCase("actor")) {
                batch.add(actorXml.toString());
                actorXml.setLength(0);
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
        StarWorkerDOMParser worker = new StarWorkerDOMParser(startingId);
        startingId = worker.process(batch);
        batch.clear();
    }
}
