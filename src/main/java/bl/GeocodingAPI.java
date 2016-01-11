package bl;

import beans.Leg;
import beans.Location;
import gui.EingabeGUI;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
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

    private Aufrufzaehler zaehler = new Aufrufzaehler(System.getProperty("user.dir")+File.separator+"resources"+File.separator+"counter.csv");
    private XMLParse xmlp;
    public final String apiKey = "AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo";
    public static int geocodingcounter = 0;
    public static int distancematrixcounter = 0;
    public static int elevationcounter = 0;
    public static int directionscounter = 0;
    /*
     Die Methode findet zum eingegeben Ort die passenden
     x- und y- Koordinaten und liefert diese in einem 
     double-Feld zurück
     Beispiel-Link: https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY    
     Key: AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo   
     */

    public Location OrtToKoord(String name)
    {
        EingabeGUI.updateStatus("Koordinaten zu Ortsnamen werden abgefragt");
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
            System.out.println(answer);
            xmlp = new XMLParse(answer);
            ort = xmlp.xmlToLocation();
            ort.setName(name);
        } catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren des Ortes zu Koordinaten");
        }
        GeocodingAPI.geocodingcounter += 1;
        zaehler.writeOnFile("geo", geocodingcounter);
        
        return ort;
    }

    /**
     * dominik Diese Methode parsed ein übergebenes Feld mit double-
     * Koordinaten(Longitude, Latitude) in ein Location- Objekt. Dadurch wird
     * ein Request an die Google geocoding API geschickt, welche auf Übergabe
     * der Koordinaten Namen und genaue Adresse zurück.
     *
     * @param koordinaten
     * @return
     */
    public Location KoordToOrt(double[] koordinaten)
    {
        
        //EingabeGUI.updateStatus("Koordinaten werden zu Ortsnamen umgewandelt");
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=" + koordinaten[0] + "," + koordinaten[1] + "&key=" + apiKey;
        Location ort = null;
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            String answer = sendObject.read();
            GeocodingAPI.geocodingcounter += 1;
            zaehler.writeOnFile("geo", geocodingcounter);
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

    /**
     * dominik Diese Methode gibt die Distanz zwischen zwei Orten zurück.
     * Wiederum werden die 4 Koordinaten zu Google geschickt und die Distanz und
     * Fahrtdauer der Strecke zwischen diesen Locations zurückgegeben.
     *
     * @param a
     * @param b
     * @return
     */
    public String[] LocationToDistance(Location a, Location b)
    {
        EingabeGUI.updateStatus("Distanz zwischen Orten wird abgefragt");
        String request = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + a.getxKoord() + "," + a.getyKoord() + "&destinations=" + b.getxKoord() + "," + b.getyKoord();
        String response = "";
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            GeocodingAPI.distancematrixcounter += 1;
            zaehler.writeOnFile("distance", distancematrixcounter);
//            System.out.println(answer);
            xmlp = new XMLParse(answer);
            response = xmlp.xmlToDistanceAndDuration();

        } catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim berechnen der Distanz");
        }

        return response.split("-");
    }

    /**
     * dominik Diese Methode gibt die Höhe einer übergebenen Location l zurück.
     * Durch einen Request an die Google ElevationAPI mit übergebenen Longitude,
     *
     * Langitude- Koordinaten wird die Seehöhe zurückgegeben.
     *
     * @param l
     */
    public double getElevationInformation(Location l)
    {
        /*
         48,208423 
         y-Koord: 16,373996 
         */
        EingabeGUI.updateStatus("Höhendaten zu Ort wird abgefragt");
        String request = "https://maps.googleapis.com/maps/api/elevation/xml?locations=" + l.getxKoord() + "," + l.getyKoord() + "&key=" + apiKey;
        double response = 0;
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            GeocodingAPI.elevationcounter += 1;
            zaehler.writeOnFile("elevation", elevationcounter);
            xmlp = new XMLParse(answer);
            response = xmlp.xmlElevationInformation();
        } catch (MalformedURLException ex)
        {

            JOptionPane.showMessageDialog(null, "Fehler beim herausfiltern der Höhe");
        }

        l.setHoehe(response);
        return response;

    }

    /**
     * Patrizia Diese Methode ruft Locations mit der Google Maps API ab.
     *
     * @param l1
     * @param l2
     * @return Eine Liste mit Locations wird zurückgegeben.
     */
    public LinkedList<Leg> getWaypoints(String l1, String l2)
    {
        EingabeGUI.updateStatus("Wegpunkte werden von Google abgefragt");

        String request = "https://maps.googleapis.com/maps/api/directions/xml?origin=" + l1 + "&destination=" + l2 + "&key=" + apiKey;
        LinkedList<Leg> response = new LinkedList<Leg>();
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            GeocodingAPI.directionscounter += 1;
            zaehler.writeOnFile("direction", directionscounter);

            EingabeGUI.updateStatus("Anfrage an Google gesendet");
            xmlp = new XMLParse(answer);
            response = xmlp.xmlFromDistanceAPItoLocations();
//            LocationParser parser = new LocationParser();
//            return parser.LegtoLocation(response);
            return response;

        } catch (MalformedURLException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim herausfiltern der Waypoints");
        }
        return null;
    }

    /**
     * Patrizia Mithilfe der Google Roads API wird eine Liste von Waypoints
     * abgerufen.
     *
     * @param waypoints
     * @return Eine Liste von Waypoints wird zurückgegeben.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public LinkedList<Location> getWaypointsMitRoadsAPI(LinkedList<Location> waypoints) throws XmlPullParserException, IOException
    {
        EingabeGUI.updateStatus("Wegpunkte werden mittels RoadsAPI erweitert");
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

        //System.out.println("Roads API request: " + request);
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

        } catch (MalformedURLException | JSONException ex)
        {
            JOptionPane.showMessageDialog(null, "Fehler beim Herausholen der Waypoints mit der RoadsAPI");
        }
        return response;
    }

    /**
     * Patrizia Diese Methode löscht von der übergebenen Liste die doppelten
     * Werte.
     *
     * @param list
     * @return Die bearbeitete Liste wird zurückgegeben.
     */
    public LinkedList<Location> loescheDoppelteWerte(LinkedList<Location> list)
    {
        EingabeGUI.updateStatus("Löschen doppelter Einträge aus der Liste");
        for (int i = 0; i < list.size(); i++)
        {
            Location l = list.get(i);

            for (int j = 0; j < list.size(); j++)
            {
                if (i != j)
                {
                    if (l.getxKoord() == list.get(j).getxKoord() && l.getyKoord() == list.get(j).getyKoord())
                    {
                        list.remove(i);

                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws XmlPullParserException, IOException
    {

//        GeocodingAPI api = new GeocodingAPI();
//        LinkedList<Location> test = api.getWaypoints("Mureck", "Ligist");
//        for (int i = 0; i < test.size(); i++)
//        {
//            System.out.println("ag: "+test.get(i).toString() + "\n");
//        }

        //String s = api.getElevationInformation(api.OrtToKoord("Mureck"));
        //System.out.println("help: "+s);
        //System.out.println("Test: "+test);
        //System.out.println(api.OrtToKoord("Ligist").toString());
        double[] k
                 =
                {
                    47.066667, 15.433333
                };
        // Location l = api.KoordToOrt(k);
//        System.out.println(l.toString());

//        for(int i = 0; i < test.size(); i++)
//        {
//            System.out.println(test.get(i).getxKoord()+" "+test.get(i).getyKoord());
//        }
//        
//        api.loescheDoppelteWerte(test);
//        System.out.println("nach dem Löschen: ");
//        for(int i = 0; i < test.size(); i++)
//        {
//            System.out.println(test.get(i).getxKoord()+" "+test.get(i).getyKoord());
//        }
//        LinkedList<Location> list = api.getWaypointsMitRoadsAPI(test);
//
//        for (int i = 0; i < list.size(); i++)
//        {
//            System.out.println(list.get(i).getxKoord() + " " + list.get(i).getyKoord());
//        }
//
//        api.loescheDoppelteWerte(list);
//        System.out.println("nach dem Löschen: ");
//        for (int i = 0; i < list.size(); i++)
//        {
//            System.out.println(list.get(i).getxKoord() + " " + list.get(i).getyKoord());
//        }

    }
}
