package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Josh on 11/29/2015.
 */
public class TranslationTesting {
    INode A1_L, A2_L, A3_L, A4_L;
    INode B1_L, B2_L, B3_L, B4_L;
    INode A1_U, A2_U, A3_U, A4_U;
    INode B1_U, B2_U, B3_U, B4_U;

    HashMap<Integer, INode> set1 = new HashMap<> ();
    HashMap<Integer, INode> set2 = new HashMap<> ();
    HashMap<Integer, INode> set3 = new HashMap<> ();
    HashMap<Integer, INode> set4 = new HashMap<> ();

    @Before
    public void setUp() throws Exception {
        A1_L = new Landmark(0,0,0,0,0,0,0,"origin1");
        B1_L = new Landmark(1,0,0,0,0,0,0,"origin2");
        A1_U = new Landmark(2,0,0,0,0,0,0,"origin3");
        B1_U = new Landmark(3,0,0,0,0,0,0,"origin4");

        set1.put(A1_L.getID(), A1_L);
        set1.put(B1_L.getID(), B1_L);
        set1.put(A1_U.getID(), A1_U);
        set1.put(B1_U.getID(), B1_U);

    }

    @Test
    public void testInitial() throws Exception {
        HashMap<Integer,INode> testMap1 = set1;
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial zeros test",testMap1,p.setUniversalCoordinates(set1));
    }
}
