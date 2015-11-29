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
        A1_L = new Landmark(0,0,0,0,0,0,0,"origin");
        B1_L = new Landmark(0,0,0,0,0,0,0,"origin");
        A1_U = new Landmark(0,0,0,0,0,0,0,"origin");
        B1_U = new Landmark(0,0,0,0,0,0,0,"origin");

        set1.put(A1_L.getID(), A1_L);

    }

    @Test
    public void testName() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        p.setUniversalCoordinates(set1);
    }
}
