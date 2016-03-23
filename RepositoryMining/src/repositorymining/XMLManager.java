package repositorymining;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author ruudandriessen
 */
public class XMLManager {

    public static void load(PageProcessor processor) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        File file = new File("/Users/ruudandriessen/Downloads/Posts.xml");
        PostHandler handler = new PostHandler(processor);    
            
        try (MonitoredInputStream mis = new MonitoredInputStream(new FileInputStream(file), 13107200*4)) {
            SAXParser parser = factory.newSAXParser();
            mis.addChangeListener( new ChangeListener() { @Override public void stateChanged(ChangeEvent e) {
              SwingUtilities.invokeLater( new Runnable() { @Override public void run() {
                  System.out.println("Progress: " + humanReadableByteCount(mis.getProgress(), true)); 
              }});
           }});
           
            // Start parsing. Listener would call Swing event thread to do the update.
            parser.parse(mis, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done, results: " + handler.toString());
    }
    
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}