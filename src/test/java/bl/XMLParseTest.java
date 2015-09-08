/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bl;

import beans.Leg;
import beans.Location;
import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author David
 */
public class XMLParseTest {
    
    private XMLParse m_parser;
    
    
    public XMLParseTest() {
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
     * Test of xmlToLocation method, of class XMLParse.
     */
    @Test
    public void testXmlToLocation() {
        System.out.println("xmlToLocation");
        XMLParse instance = new XMLParse("https://maps.googleapis.com/maps/api/geocode/xml?address=Ligist&key=AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo");
        Location expResult = new Location("Ligist",46.9917246,15.2107184,0);
        Location result = instance.xmlToLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of xmlElevationInformation method, of class XMLParse.
     */
    @Test
    public void testXmlElevationInformation() {
        System.out.println("xmlElevationInformation");
        XMLParse instance = null;
        double expResult = 0.0;
        double result = instance.xmlElevationInformation();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of xmlToDistanceAndDuration method, of class XMLParse.
     */
    @Test
    public void testXmlToDistanceAndDuration() {
        System.out.println("xmlToDistanceAndDuration");
        XMLParse instance = null;
        String expResult = "";
        String result = instance.xmlToDistanceAndDuration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of xmlFromDistanceAPItoLocations method, of class XMLParse.
     */
    @Test
    public void testXmlFromDistanceAPItoLocations() {
        System.out.println("xmlFromDistanceAPItoLocations");
        XMLParse instance = null;
        LinkedList<Leg> expResult = null;
        LinkedList<Leg> result = instance.xmlFromDistanceAPItoLocations();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of xmlFromRoadsAPI method, of class XMLParse.
     */
    @Test
    public void testXmlFromRoadsAPI() {
        System.out.println("xmlFromRoadsAPI");
        XMLParse instance = null;
        LinkedList<Location> expResult = null;
        LinkedList<Location> result = instance.xmlFromRoadsAPI();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class XMLParse.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        XMLParse.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
