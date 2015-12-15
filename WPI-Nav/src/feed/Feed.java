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
import java.util.Collections;
import java.util.Iterator;

//TODO Racism and Inclusion "0:30 pm"

//TODO IMPORTANT : If feed.isEmpty() is true, it means that an internet connection couldn't be made so don't display feed stuff

/**
 * Created by ikeandmike on 12/11/15.
 *
 * The Feed class is used to create and manage the OrgSync feed of events
 * Usage is really simple:
 *
 * Feed f = new Feed(int numEvents)
 * IMPORTANT: BEFORE USE, CHECK f.isEmpty() should return false
 * Feeds are iterable, so just use an enhanced for loop ie. for (Event e : f) to grab all the Events
 * Most of the methods in this class are only for generating the Feed, and are therefore private
 */
public class Feed implements Iterable<Event> {
    private ArrayList<Event> events;

    public Feed(int numEvents) {
        events = new ArrayList<>();
        if (numEvents <= 100) generateEvents(numEvents, 1);
        else {
            int i = 1;
            while (numEvents > 0) {
                generateEvents(100, i);
                numEvents -= 100; i++;
            }
        }
        Collections.sort(events);
    }

    private void generateEvents(int numEvents, int pageNum) {
        String raw;
        Document doc = null;
        String link = "https://api.orgsync.com/api/v3/communities/412/events.rss?key=rEXxNgt53SYC0O0mepnBQWgk" +
                      "IjaoXcoOGyDjxiHUv6o&per_page=" + numEvents + "&upcoming=true&page=" + pageNum;
        try {
            raw = getXML(link);           //Fetch raw XML data from OrgSync's website
            if (raw == null) { return; }  //Empty ArrayList indicated couldn't connect
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
        HttpURLConnection conn;
        BufferedReader rd;
        String line;

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        }
        catch (Exception e) { return null; } //In case no internet connection
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

    public boolean isEmpty() { return events.isEmpty(); }

    //Sample code prints the most recent 5 events to stdout
    public static void main(String args[]) {
        Feed f = new Feed(5);
        System.out.println(f.isEmpty());
        for (Event e : f) {
            System.out.println(e);
        }
    }
}
