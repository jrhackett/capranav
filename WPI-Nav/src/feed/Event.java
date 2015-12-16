package feed;

/**
 * Created by ikeandmike on 12/10/15.
 *
 * This class stores the information about a single event
 */
public class Event implements Comparable<Event> {
    private String title;
    private String location;
    private String description;
    private String tweet; //Shorter version of description
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

        if (description.length() > 150) this.tweet = reduceDescription(150);
        else                            this.tweet = description;
    }

    public String toString() {
        return "\n" + this.getTitle() + "\n" + this.getDateInfo() + "\n";
    }

    //Determines appropriate date info
    public String getDateInfo() {
        if (sDate.sameDate(eDate)) {
            if (eDate.emptyTime() && !sDate.emptyTime()) { return sDate.getDate() + " at " + sDate.getTime(); } //End has no interesting time info, but start does
            else if (!sDate.emptyTime()) { return sDate.getDate() + " " + sDate.getTime() + " to " + eDate.getTime(); } //Both have interesting time info, print both
            else { return sDate.getDate(); } //Neither have an interesting time, print neither
        }
        else { return sDate.getDate() + " to " + eDate.getDate(); } //Different dates, just give date range
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getTweet() { return tweet; }

    public Date getsDate() {
        return sDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public String getLink() {
        return link;
    }

    public int compareTo (Event other) {
        return sDate.compareTo(other.sDate);
    }

    //Reduces the description to be at most chars characters, with "..." appearing at the end
    private String reduceDescription(int chars) {
        return description.substring(0, description.indexOf(" ", chars+1)) + "...";
    }
}
