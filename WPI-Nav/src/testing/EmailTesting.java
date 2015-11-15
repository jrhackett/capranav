package testing;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Email;
/**
 * Created by Henry on 11/14/2015.
 */
public class EmailTesting {
    Email testE;
/*
    public EmailTesting(){
        try{
            this.setUp();
        }catch(Exception e){

        }

    }
*/
    @Before
    public void setUp() throws Exception {
        testE = new Email("hjwheelermackta@wpi.edu");
    }

    @Test
    public void emailTest1() {
        assertEquals("Failure to send email", true, testE.sendEmail("This is a test email!"));
    }


}
