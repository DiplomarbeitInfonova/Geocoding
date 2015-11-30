/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Leg;
import beans.Location;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author David
 */
public class GeocodingAPI
{

    private XMLParse xmlp;
    public final String apiKey = "AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo";

    /*
     Die Methode findet zum eingegeben Ort die passenden
     x- und y- Koordinaten und liefert diese in einem 
     double-Feld zurück
     Beispiel-Link: https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
    
     Key: AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo   
     */
    public Location OrtToKoord(String name)
    {
        double[] koordinaten = new double[2];
        Location ort = null;
        name = StringUtils.deleteSpaces(name);
        name = StringUtils.correctLettersForAPI(name);
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + name + "&key=" + apiKey;
        String answer = "";

        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            answer = sendObject.read();
//            System.out.println(answer);
            xmlp = new XMLParse(answer);
            ort = xmlp.xmlToLocation();
            ort.setName(name);
        } catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren des Ortes zu Koordinaten");
        }

        return ort;
    }

    public Location KoordToOrt(double[] koordinaten)
    {
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=" + koordinaten[0] + "," + koordinaten[1] + "&key=" + apiKey;
        Location ort = null;
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            String answer = sendObject.read();
//            System.out.println(answer);
            xmlp = new XMLParse(answer);
            ort = xmlp.xmlToLocation();
//            System.out.println("koordtoOrt\n"+ort.toString());
        } catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren der Koordinaten zum Ort");
        }
        return ort;
    }

    public String LocationToDistance(Location a, Location b)
    {
        String request = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + a.getxKoord() + "," + a.getyKoord() + "&destinations=" + b.getxKoord() + "," + b.getyKoord();
        String response = "";
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
//            System.out.println(answer);
            xmlp = new XMLParse(answer);
            response = xmlp.xmlToDistanceAndDuration();

        } catch (MalformedURLException ex)
        {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    public void getElevationInformation(Location l)
    {
        String request = "https://maps.googleapis.com/maps/api/elevation/xml?locations=" + l.getxKoord() + "," + l.getyKoord() + "&key=" + apiKey;
        double response = 0;
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            xmlp = new XMLParse(answer);
            response = xmlp.xmlElevationInformation();
        } catch (MalformedURLException ex)
        {
//            System.out.println(ex);
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(response);
        l.setHoehe(response);
    }

    public LinkedList<Location> getWaypoints(String l1, String l2)
    {
        String request = "https://maps.googleapis.com/maps/api/directions/xml?origin=" + l1 + "&destination=" + l2 + "&key=" + apiKey;
        LinkedList<Leg> response = new LinkedList<Leg>();
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            xmlp = new XMLParse(answer);
            response = xmlp.xmlFromDistanceAPItoLocations();
            LocationParser parser = new LocationParser();
            return parser.LegtoLocation(response);

        } catch (MalformedURLException ex)
        {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LinkedList<Location> getWaypointsMitRoadsAPI(LinkedList<Location> waypoints) throws XmlPullParserException, IOException
    {
        String request = "https://roads.googleapis.com/v1/snapToRoads?path=";

        for (int i = 0; i < waypoints.size(); i++)
        {
            if (i < waypoints.size() - 1)
            {
                request = request + waypoints.get(i).getxKoord() + "," + waypoints.get(i).getyKoord() + "|";
            } else
            {
                request = request + waypoints.get(i).getxKoord() + "," + waypoints.get(i).getyKoord();
            }
        }
        request = request + "&interpolate=true&key=" + apiKey;

        System.out.println("request: " + request);

        LinkedList<Location> response = new LinkedList<Location>();
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            JSONObject json = null;

            json = new JSONObject(answer);

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root>";
            xml += XML.toString(json) + "</root>";
            System.out.println("xml: " + xml);
            xmlp = new XMLParse(xml);
            response = xmlp.xmlFromRoadsAPI();

        } catch (MalformedURLException ex)
        {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex)
        {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    public void loescheDoppelteWerte(LinkedList<Location> list)
    {
        
    }

    public static void main(String[] args)
    {

        GeocodingAPI api = new GeocodingAPI();
//        LinkedList<Leg> test = api.getWaypoints("Mureck", "Ligist");
//        for (int i = 0; i < test.size(); i++)
//        {
//            System.out.println("ag: "+test.get(i).toString() + "\n");
//        }

        //String s = api.getElevationInformation(api.OrtToKoord("Mureck"));
        //System.out.println("help: "+s);
        //System.out.println("Test: "+test);
        //System.out.println(api.OrtToKoord("Ligist").toString());
        double[] k =
        {
            47.066667, 15.433333
        };
        Location l = api.KoordToOrt(k);
//        System.out.println(l.toString());

    }
}
