/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.awt.Graphics;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Veronika
 */
public class GraphingData_smallTest {
    
    public GraphingData_smallTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getData method, of class GraphingData.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        GraphingData_small instance = new GraphingData_small();
        LinkedList<Double> expResult = null;
        LinkedList<Double> result = instance.getDaten();
       // assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setData method, of class GraphingData.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        LinkedList<Double> data = null;
        GraphingData_small instance = new GraphingData_small();
        instance.setDaten(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paintComponent method, of class GraphingData.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        GraphingData_small instance = new GraphingData_small();
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
