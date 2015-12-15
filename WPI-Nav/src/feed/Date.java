package feed;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    private String date; //Contains date in format yyyy-mm-dd

    //String in form 2015-12-01T11:28:12
    public Date(String date) {
        year = date.substring(0,4);
        month = Integer.parseInt(date.substring(5,7));
        day = date.substring(8,10);

        hour = Integer.parseInt(date.substring(11, 13));
        min = date.substring(14,16);

        if (hour == hour%12) isAM = true;
        else { isAM = false; hour = hour%12; }

        this.date = date.substring(0, 10);
    }

    public String toString() {
        return getDate() + " " + getTime();
    }

    public String getDate() {
        int flag = checkDate();
        if      (flag == 0) return "Today";
        else if (flag == 1) return "Tomorrow";
        else                return month + "-" + day;
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

    //Returns 0 for today, 1 for tomorrow, -1 for neither
    public int checkDate() {
        //Generate today's date today's date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        //Generate tomorrow's date
        long milis = System.currentTimeMillis();
        milis += (24 * 60 * 60 * 1000);
        java.util.Date date = new java.util.Date(milis);

        //Put dates into nice format
        String today = dateFormat.format(cal.getTime());
        String tomorrow = dateFormat.format(date);

        System.out.println(today + "\n" + tomorrow);

        //Comparisons
        if (this.date.equals(today))    return 0;
        if (this.date.equals(tomorrow)) return 1;
        else                            return -1;
    }

    public static void main(String args[]) {
        new Date("2015-12-14T00:00:00").checkDate();
    }
}
