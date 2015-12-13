package feed;

/**
 * Created by ikeandmike on 12/10/15.
 *
 * This class stores the information about a single event
 */
public class Event {
    private String title;
    private String location;
    private String description;
    private Date sDate;
    private Date eDate;
    private String link; //Link to OrgSync Event Page

    public Event(String title, String location, String description, String sDate, String eDate, String link) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.sDate = new Date(sDate);
        this.eDate = new Date(eDate);
        this.link = link;
    }

    public String toString() {
        return title + "\n" + description + "\n" + location + "\n" + getDateInfo() + "\n" + link;
    }

    //Determines appropriate date info
    public String getDateInfo() {
        if (sDate.sameDate(eDate)) {
            if (eDate.emptyTime() && !sDate.emptyTime()) { return sDate.getDate() + " at " + sDate.getTime(); } //End has no interesting time info, but start does
            else if (!sDate.emptyTime()) { return sDate.getDate() + " from " + sDate.getTime() + " to " + eDate.getTime(); } //Both have interesting time info, print both
            else { return sDate.getDate(); } //Neither have an interesting time, print neither
        }
        else { return "From " + sDate.getDate() + " to " + eDate.getDate(); } //Different dates, just give date range
    }

}
