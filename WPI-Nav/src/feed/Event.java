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

    public Event(String title, String location, String description) {
        this.title = title;
        this.location = location;
        this.description = description;
    }

    public String toString() {
        return title + "\n" + description + "\n" + location;
    }

}
