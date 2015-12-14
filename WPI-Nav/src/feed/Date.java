package feed;

/**
 * Created by ikeandmike on 12/13/15.
 */
public class Date implements Comparable<Date> {
    private int month; // 1 = Jan, 2 = Feb...
    private String day;
    private String year;

    private int hour;
    private String min;
    private boolean isAM;

    //0th index is wasted to correspond with month numbers
    private static String[] months = { "", "Jan.", "Feb.", "March", "April", "May", "June", "July", "Aug.",
                                       "Sep.", "Oct.", "Nov.", "Dec." };

    //String in form 2015-12-01T11:28:12
    public Date(String date) {
        year = date.substring(0,4);
        month = Integer.parseInt(date.substring(5,7));
        day = date.substring(8,10);

        hour = Integer.parseInt(date.substring(11, 13));
        min = date.substring(14,16);

        if (hour == hour%12) isAM = true;
        else { isAM = false; hour = hour%12; }
    }

    public String toString() {
        return getDate() + " " + getTime();
    }

    public String getDate() {
        return months[month] + " " + day + ", " + year;
    }

    public String getTime() {
        return hour + ":" + min + ((isAM) ? " AM" : " PM");
    }

    //True if objects have same dates (could have different times)
    public boolean sameDate(Date other) {
        return this.month == other.month && this.day.equals(other.day) && this.year.equals(other.year);
    }

    public boolean emptyTime() {
        return this.hour == 0 && this.min.equals("00");
    }

    public int compareTo(Date other) {
        int hour241 = isAM ? hour : hour+12;
        int hour242 = other.isAM ? other.hour : other.hour+12;

        if      (year.compareTo(other.year) < 0) return -1;
        else if (year.compareTo(other.year) > 0) return 1;
        else if (month < other.month) return -1;
        else if (month > other.month) return 1;
        else if (day.compareTo(other.day) < 0) return -1;
        else if (day.compareTo(other.day) > 0) return 1;
        else if (hour241 < hour242) return -1;
        else if (hour241 > hour242) return 1;
        else if (min.compareTo(other.min) < 0) return -1;
        else if (min.compareTo(other.min) > 0) return 1;
        else return 0;
    }
}
