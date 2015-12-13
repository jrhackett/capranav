package feed;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

//TODO IDEAS : Include Title, Date/Time, and location in bar, with two buttons: "Add waypoint" and "More Info"
//TODO IDEAS : The first is obvious, the second creates a pop-up with the full info, including description and a link to orgsync

/**
 * Created by ikeandmike on 12/11/15.
 *
 * The Feed class is used to create and manage the OrgSync feed of events
 * Usage is really simple:
 *
 * Feed f = new Feed(int numEvents)
 * Feeds are iterable, so just use an enhanced for loop ie. for (Event e : f) to grab all the Events
 * Most of the methods in this class are only for generating the Feed, and are therefore private
 */
public class Feed implements Iterable<Event> {
    private ArrayList<Event> events;

    public Feed(int numEvents) {
        events = new ArrayList<>();
        generateEvents(numEvents);
    }

    private void generateEvents(int numEvents) {
        String raw = "";
        Document doc = null;
        String link = "https://api.orgsync.com/api/v3/communities/412/events.rss?key=rEXxNgt53SYC0O0mepnBQWgk" +
                      "IjaoXcoOGyDjxiHUv6o&per_page=" + numEvents + "&upcoming=true";
        try {
            raw = getXML(link);           //Fetch raw XML data from OrgSync's website
            doc = loadXMLFromString(raw); //Convert raw data into a nice document thingy
        }
        catch (Exception e) { e.printStackTrace(); } //Who knows

        for (int i = 0; i < numEvents; i++) {
            events.add(makeEvent(doc, i)); //Get the events from doc, store in list
        }
    }

    //Fetch raw XML data from OrgSync's website
    private static String getXML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    //Convert raw data into a nice document thingy
    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

    //Create a single event based on index into the file
    private static Event makeEvent(Document doc, int i) {
        //Note: First title and description are always some generic "WPI Events" kind of thing, so i+1
        String title = getField(doc, "title", i+1);
        String location = getField(doc, "event:location", i);
        String description = getField(doc, "description", i+1);
        String sDate = getField(doc, "event:startdate", i);
        String eDate = getField(doc, "event:enddate", i);
        String link = getField(doc, "link", i+1);
        return new Event(title, location, description, sDate, eDate, link);
    }

    //Get the value of a field in XML document
    private static String getField(Document doc, String tag, int i) {
        return doc.getElementsByTagName(tag).item(i).getTextContent();
    }

    public Iterator<Event> iterator() {
        return events.iterator();
    }

    //Sample code prints the most recent 5 events to stdout
    public static void main(String args[]) {
        Feed f = new Feed(5);
        for (Event e : f) {
            System.out.println(e);
        }
    }
}
