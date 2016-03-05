/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Leg;
import beans.Location;
import gui.EingabeGUI;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Veronika
 */
public class GeocodingAPITest {
    
    private GeocodingAPI api;
    public GeocodingAPITest() {
        api= new GeocodingAPI();
    }

    // In der Methode wird getestet ob bei Übergabe des Wertes Ligist die 
    // richtigen Koordinaten zurückgegeben werden 
    @Test
    public void testOrtToKoordLigistReturnsRightCoords() {
        EingabeGUI gui = new EingabeGUI();
        System.out.println("OrtToKoord");
        String name = "Ligist";
        double expxKoord = 46.9917246;
        double expyKoord = 15.2107184;
        Location result = api.OrtToKoord(name);
      
        Assert.assertEquals(expxKoord, result.getxKoord(),1);
        Assert.assertEquals(expyKoord, result.getyKoord(),1);
    }
    
    // In dieser Methode wird getestet ob bei eingeben eines unbekannten
    // Ortes null zurückgegeben wird
    @Test
    public void testOrtToKoordskljflsdkfjReturnsNull() {
        EingabeGUI gui = new EingabeGUI();
        System.out.println("OrtToKoord");
        String name = "skljflsdkfj";       
        Location result = api.OrtToKoord(name);
        Assert.assertNull(result);
       
    }

    // In dieser Methode wird getestet ob bei Eingabe von Koordinaten 
    // die richtige Ortsbezeichnung ausgelesen wird
    @Test
    public void testKoordToOrtReturnsRightOrt() {
        EingabeGUI gui = new EingabeGUI();
        double[] koordinaten = {47.066749,15.433306};
        GeocodingAPI instance = new GeocodingAPI();
        String expResult = "Graz";
        Location result = instance.KoordToOrt(koordinaten);
        assertEquals(expResult, result.getName().trim());
    }

    // In dieser Methode wird getestet ob bei eingeben unbekannter Koordinaten
    // null zurückgegeben wird
    @Test
    public void testKoordToOrt00ReturnsNull() {
        EingabeGUI gui = new EingabeGUI();
        double[] werte = {0,0};
        Location result = api.KoordToOrt(werte);
        Assert.assertNull(result);
       
    }
    
    // In dieser Methode wird überprüft ob die Distanz zwischen zwei Locations
    // richtig zurückgegeben wird
    @Test
    public void testLocationToDistanceReturnsRightDistance() {
        EingabeGUI gui = new EingabeGUI();
        Location a = api.OrtToKoord("Mureck");
        Location b = api.OrtToKoord("Graz");
        
        String[] expResult = {"45 mins","56.3 km"};
        String[] result = api.LocationToDistance(a, b);
        assertArrayEquals(expResult, result);
    }

    // In dieser Methode wird getestet ob bei Übergeben von falschen Locations
    // Eine Leere Array zurückgegeben wird
    @Test
    public void testLocationToDistanceNullReturnsEmptyDistance() {
        EingabeGUI gui = new EingabeGUI();
        Location a = null;
        Location b = null;
        
        String[] expResult = {"",""};
        String[] result = api.LocationToDistance(a, b);
        assertArrayEquals(expResult, result);
    }
    
    // In dieser Methode wird getestet ob bei Übergabe einer Location die
    // richtige Höhe zurückgegeben wird
    @Test
    public void testGetElevationInformationReturnsRightElevation() {
        EingabeGUI gui = new EingabeGUI();
        Location l = api.OrtToKoord("Ligist");
        
        double expResult = 377.1265564;
        double result = api.getElevationInformation(l);
        assertEquals(expResult, result, 1);
        
    }

    // In dieser Methode wird getestet ob bei Übergabe einer falschen Location
    // als Höhe 0 zurückgegeben wird
    @Test
    public void testGetElevationInformationNullReturns0Elevation() {
        EingabeGUI gui = new EingabeGUI();
        Location l = null;
        
        double expResult = 0;
        double result = api.getElevationInformation(l);
        assertEquals(expResult, result, 1);
        
    }
    
    // In dieser Methode wird getestet ob bei Einer Übergabe von falschen Legs
    // eine Leere Liste zurückkommt
    @Test
    public void testGetWaypointsNullReturnsEmptyList() {
        EingabeGUI gui = new EingabeGUI();
        
        String l1 = "";
        String l2 = "";
        GeocodingAPI instance = new GeocodingAPI();
        LinkedList<Leg> expResult = new LinkedList<Leg>();
        LinkedList<Leg> result = instance.getWaypoints(l1, l2);
        assertEquals(expResult, result);
        
    }

    // In dieser Methode wird getestet ob bei Einer Übergabe von falschen Waypoints
    // eine Leere Liste zurückkommt
    @Test
    public void testGetWaypointsMitRoadsAPINullReturnsEmptyList() throws Exception {
        EingabeGUI gui = new EingabeGUI();
        
        LinkedList<Location> waypoints = null;
        LinkedList<Location> expResult = new LinkedList<Location>();
        LinkedList<Location> result = api.getWaypointsMitRoadsAPI(waypoints);
        assertEquals(expResult, result);

    }

    // In dieser Methode wird getestet ob bei Übergabe einer Leeren Liste die
    // gleiche Liste wieder zrurück kommt
    @Test
    public void testLoescheDoppelteWerte() {
        EingabeGUI gui = new EingabeGUI();        
        LinkedList<Location> list = new LinkedList<Location>();
        LinkedList<Location> expResult = new LinkedList<Location>();
        LinkedList<Location> result = api.loescheDoppelteWerte(list);
        assertEquals(expResult, result);
    }

   
    
}
