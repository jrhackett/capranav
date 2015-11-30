package logic;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 * Created by Henry on 11/29/2015.
 */
public class InterestAndTransitionTesting {

    Interest i1;
    Interest i2;
    Bathroom b1;
    Bathroom b2;
    Elevator e1;
    Elevator e2;
    Food f1;
    Food f2;
    Landmark l1;
    Landmark l2;
    Room r1;
    Room r2;

    Stairs s1;
    Transition t1;
    TStairs a1;

    @Before
    public void setUp() throws Exception {
        i1 = new Interest(0, 1, 2, 3, 4, 5, 6, "Generic Interest");
        i2 = new Interest(1, 2, 3, 4, 5, 6, 7, 8, "Generic ID'd Interest");
        b1 = new Bathroom(2, 3, 4, 5, 6, 7, 8, "Bathroom");
        b2 = new Bathroom(3, 4, 5, 6, 7, 8, 9, 10, "ID'd Bathroom");

        e1 = new Elevator(4, 5, 6, 7, 8, 9 ,10);
        e2 = new Elevator(5, 6, 7, 8, 9, 10, 11, 12);

        f1 = new Food(6, 7, 8, 9, 10, 11, 12, "Food");
        f2 = new Food(7, 8, 9, 10, 11, 12, 13, 14, "ID'd Food");
        l1 = new Landmark(8, 9, 10, 11, 12, 13, 14, "Landmark");
        l2 = new Landmark(9, 10, 11, 12, 13, 14, 15, 16, "ID'd Landmark");
        r1 = new Room(10, 11, 12, 13, 14, 15, 16, "Room");
        r2 = new Room(11, 12, 13, 14, 15, 16, 17, 18, "ID'd Room");

        s1 = new Stairs(12, 13, 14, 15, 16, 17, 18);
        t1 = new Transition(1, 2, 3, 4, 5, 6, 7);
        a1 = new TStairs(1, 2, 3, 4, 5, 6, 7);


    }

    @Test
    public void isInterestingTest(){
        assertEquals("isInteresting returned incorrect value", true, i1.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, i2.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, b1.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, b2.isInteresting());

        assertEquals("isInteresting returned incorrect value", false, e1.isInteresting());
        assertEquals("isInteresting returned incorrect value", false, e2.isInteresting());

        assertEquals("isInteresting returned incorrect value", true, f1.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, f2.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, l1.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, l2.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, r1.isInteresting());
        assertEquals("isInteresting returned incorrect value", true, r2.isInteresting());

        assertEquals("isInteresting returned incorrect value", false, s1.isInteresting());
        assertEquals("isInteresting returned incorrect value", false, t1.isInteresting());
        assertEquals("isInteresting returned incorrect value", false, a1.isInteresting());

    }

    @Test
    public void isTransitionTest() {
        assertEquals("isTransition returned incorrect value", false, i1.isTransition());
        assertEquals("isTransition returned incorrect value", false, i2.isTransition());
        assertEquals("isTransition returned incorrect value", false, b1.isTransition());
        assertEquals("isTransition returned incorrect value", false, b2.isTransition());

        assertEquals("isTransition returned incorrect value", true, e1.isTransition());
        assertEquals("isTransition returned incorrect value", true, e2.isTransition());

        assertEquals("isTransition returned incorrect value", false, f1.isTransition());
        assertEquals("isTransition returned incorrect value", false, f2.isTransition());
        assertEquals("isTransition returned incorrect value", false, l1.isTransition());
        assertEquals("isTransition returned incorrect value", false, l2.isTransition());
        assertEquals("isTransition returned incorrect value", false, r1.isTransition());
        assertEquals("isTransition returned incorrect value", false, r2.isTransition());

        assertEquals("isTransition returned incorrect value", false, s1.isTransition());
        assertEquals("isTransition returned incorrect value", true, t1.isTransition());
        assertEquals("isTransition returned incorrect value", true, a1.isTransition());
    }

}
