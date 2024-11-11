import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class CastMasterSAXParser extends DefaultHandler {
    private static final int BATCH_SIZE = 10;
    private final List<String> batch;
    private boolean isRecording = false;
    private StringBuilder castXml = new StringBuilder();

    public CastMasterSAXParser() {
        this.batch = new ArrayList<>(BATCH_SIZE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("m")) {
            isRecording = true;
            castXml.setLength(0);
        }
        if (isRecording) {
            castXml.append("<").append(qName).append(">");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (isRecording) {
            String content = new String(ch, start, length)
                    .replaceAll("\\s+", " ")
                    .trim();
            castXml.append(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (isRecording) {
            castXml.append("</").append(qName).append(">");
            if (qName.equalsIgnoreCase("m")) {
                batch.add(castXml.toString());
                castXml.setLength(0);
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
        CastWorkerDOMParser worker = new CastWorkerDOMParser();
        worker.process(batch);
        batch.clear();
    }
}
