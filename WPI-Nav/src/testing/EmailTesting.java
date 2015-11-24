package testing;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Email;

import java.util.ArrayList;

/**
 * Created by Henry on 11/14/2015.
 */
public class EmailTesting {
    Email testE;

    @Before
    public void setUp() throws Exception {
        //TODO Set as your own email when testing
        testE = new Email("");
    }
/*
    @Test
    public void sendBasicEmail() {
        assertEquals("Send Basic Text Email", true, testE.sendEmail("This is a test email!", false));
    }
*/
    @Test
    public void sendHTMLEmail() {
        ArrayList<String> directions = new ArrayList<>();
        directions.add("Go west 1000 kilometers");
        directions.add("Take a hop and a skip");
        directions.add("Apply to hogwarts");
        directions.add("Choose crazy Peeta instead of Chris Hemsworth");
        directions.add("Live happily ever after, the end");

        String body = Email.generateBody(directions, "Fuller", "AK");

        assertEquals("Send HTML Email", true, testE.sendEmail(body, true));
    }


}
