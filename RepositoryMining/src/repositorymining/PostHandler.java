package repositorymining;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ruudandriessen
 */
public class PostHandler extends DefaultHandler {

    private final PageProcessor processor;
    private Post post;
    private StringBuilder stringBuilder;
    private FileWriter writer;
    private int matches;
    private int count;
    private ArrayList<String> order;

    public PostHandler(PageProcessor processor) {
        this.processor = processor;
        matches = 0;
        count = 0;
        order = new ArrayList<>();
        try {
            writer = new FileWriter("./out/Posts_CPP_Python.csv");
        } catch (IOException ex) {
            Logger.getLogger(PostHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<String> keyTypes = new ArrayList<>();
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        stringBuilder = new StringBuilder();
        String tags = attributes.getValue("Tags");
        if (tags != null) {
            if (tags.contains("<c++>") || tags.contains("<python>"))  {
                count++;
                String result = "";
                if (matches == 0) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String key = attributes.getQName(i);
                        result += (i == 0 ? "" : ",") + key;
                        order.add(key);
                    }
                    result += "\n";
                }
                for (int i = 0; i < order.size(); i++) {
                    String key = order.get(i);
                    String value = attributes.getValue(key);
                    if (key.equals("Body")) {
                        // Remove all code tags
                        value = value.replaceAll("<code>[\\s\\S]*<\\/code>", "");
                        // Strip all html tags
                        value = value.replaceAll("(?i)<[^>]*>", " ").replaceAll("\\s+", " ").trim();
                    }
                    if (value != null)
                        value = value.replaceAll("\"", " ");
                    result += (i == 0 ? "" : ",") + "\"" + value + "\"";    
                }
                result += "\n";
                matches++;
                try {
                    writer.append(result);
                } catch (IOException ex) {
                    Logger.getLogger(PostHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        stringBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println("--END---");
    }
    
    public static String escapeHTML(String s) {
        if (s == null) return s;
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
    
    @Override
    public String toString() {
        return matches + " / " + count;
    }

}
