
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
    INode C1_L, C2_L;
    INode C1_U, C2_U;
    INode D1_L, D2_L;
    INode D1_U, D2_U;

    HashMap<Integer, INode> local1 = new HashMap<> ();
    HashMap<Integer, INode> local2 = new HashMap<> ();
    HashMap<Integer, INode> local3 = new HashMap<> ();
    HashMap<Integer, INode> local4 = new HashMap<> ();

    HashMap<Integer, INode> universal1 = new HashMap<> ();
    HashMap<Integer, INode> universal2 = new HashMap<> ();

    @Before
    public void setUp() throws Exception {
        //~~~~~~~~~~~~~~~~~(ID, x, y, z, xuni, yuni, zuni, string)
        A1_L = new Landmark(0,1,2,0,0,0,0,"A1 local");
        B1_L = new Landmark(1,1,5,0,0,0,0,"B1 local");
        A1_U = new Landmark(2,60,20,0,60,20,0,"A1 universal");
        B1_U = new Landmark(3,30,20,0,30,20,0,"B1 universal");

        C1_L = new Landmark(4,5,2,0,0,0,0,"C1");
        D1_L = new Landmark(5,5,5,0,0,0,0,"D1");
        C1_U = new Landmark(4,5,2,0,60,60,0,"C1");
        D1_U = new Landmark(5,5,5,0,30,60,0,"D1");

        local1.put(C1_L.getID(), C1_L);
        local1.put(D1_L.getID(), D1_L);

        universal1.put(C1_U.getID(), C1_U);
        universal1.put(D1_U.getID(), D1_U);

        double valu = Math.sqrt(50);
        double val =((int) ((valu * 100.0) + ((valu < 0.0) ? -0.5 : 0.5))) / 100.0;
        A2_L = new Landmark(0,0,0,0,0,0,0,"A1 local");
        B2_L = new Landmark(1,val,val,0,0,0,0,"B1 local");
        A2_U = new Landmark(2,10,5,0,10,5,0,"A1 universal");
        B2_U = new Landmark(3,0,5,0,0,5,0,0,"B1 universal");

        C2_L = new Landmark(4,0,val,0,0,0,0,"C1");
        D2_L = new Landmark(5,val,0,0,0,0,0,"D1");
        C2_U = new Landmark(4,0,val,0,5,0,0,"C1");
        D2_U = new Landmark(5,val,0,0,5,10,0,"D1");

        local2.put(C2_L.getID(), C2_L);
        local2.put(D2_L.getID(), D2_L);

        universal2.put(C2_U.getID(), C2_U);
        universal2.put(D2_U.getID(), D2_U);



    }

@Test
    public void testInitial1() throws Exception {
        HashMap<Integer, INode> testHashEq = universal1;
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test1",universal1,testHashEq);
    }


    @Test
    public void testInitial2() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test2",universal1,p.setUniversalCoordinates(local1));
        //assertEquals("Initial test2", universal1, p.setUniversalCoordinates(local1));
        //assertEquals("Initial test2", universal1.get(5).getID(), p.setUniversalCoordinates(local1).get(5).getID());

    }

    @Test
    public void testInitial3() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test3",universal1.get(4).getX_univ(),p.setUniversalCoordinates(local1).get(4).getX_univ(), 0);
    }

    @Test
    public void testInitial4() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test4",0,local1.get(4).getX_univ(), 0);
    }

    @Test
    public void testInitial5() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test5",universal1.get(4).getY_univ(),p.setUniversalCoordinates(local1).get(4).getY_univ(), 0);
    }

    @Test
    public void testInitial6() throws Exception {
        Translate p = new Translate(A1_L, B1_L, A1_U, B1_U);
        assertEquals("Initial test6", universal1.get(4).getZ_univ(), p.setUniversalCoordinates(local1).get(4).getZ_univ(), 0);
    }

    @Test
    public void testTranslate1() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("Translate test 22222", universal2, p.setUniversalCoordinates(local2));
    }



    @Test
    public void testTest1() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test1",universal2.get(4).getX_univ(),p.setUniversalCoordinates(local2).get(4).getX_univ(), 0);
    }

    @Test
    public void testTest2() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test2",universal2.get(4).getY_univ(),p.setUniversalCoordinates(local2).get(4).getY_univ(), 0);
    }

    @Test
    public void testTest3() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test3", universal2.get(4).getZ_univ(), p.setUniversalCoordinates(local2).get(4).getZ_univ(), 0);
    }

    @Test
    public void testTest4() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test1D",universal2.get(5).getX_univ(),p.setUniversalCoordinates(local2).get(5).getX_univ(), 0);
    }

    @Test
    public void testTest5() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test2D",universal2.get(5).getY_univ(),p.setUniversalCoordinates(local2).get(5).getY_univ(), 0);
    }

    @Test
    public void testTest6() throws Exception {
        Translate p = new Translate(A2_L, B2_L, A2_U, B2_U);
        assertEquals("2nd round test3D", universal2.get(5).getZ_univ(), p.setUniversalCoordinates(local2).get(5).getZ_univ(), 0);
    }


}



