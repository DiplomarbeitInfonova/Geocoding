/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Leg;
import beans.Location;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author David
 */
public class GeocodingAPI
{

    private XMLParse xmlp;
    private final String apiKey = "AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo";
    private XmlPullParser parser;

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
//            System.out.println(answer);
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
    
    public void initializeXMLPullParser()
    {
        parser = new XmlPullParser() {

            @Override
            public void setFeature(String string, boolean bln) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean getFeature(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setProperty(String string, Object o) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object getProperty(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setInput(Reader reader) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setInput(InputStream in, String string) throws XmlPullParserException {
                parser.setInput(in, string);
            }

            @Override
            public String getInputEncoding() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void defineEntityReplacementText(String string, String string1) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getNamespaceCount(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespacePrefix(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespaceUri(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespace(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getDepth() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getPositionDescription() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getLineNumber() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getColumnNumber() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isWhitespace() throws XmlPullParserException {
                return parser.isWhitespace();
            }

            @Override
            public String getText() {
                return parser.getText();
            }

            @Override
            public char[] getTextCharacters(int[] ints) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespace() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getPrefix() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isEmptyElementTag() throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getAttributeCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeNamespace(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeName(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributePrefix(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeType(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isAttributeDefault(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeValue(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeValue(String string, String string1) {
                return parser.getAttributeValue(string, string1);
            }

            @Override
            public int getEventType() throws XmlPullParserException {
                return parser.getEventType();
            }

            @Override
            public int next() throws XmlPullParserException, IOException {
                return parser.next();
            }

            @Override
            public int nextToken() throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void require(int i, String string, String string1) throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String nextText() throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int nextTag() throws XmlPullParserException, IOException {
                return parser.nextTag();
            }
        };
    }
    
    public LinkedList<Location> getWaypointsMitRoadsAPI(LinkedList<Location> waypoints) throws XmlPullParserException, IOException
    {
        String request = "https://roads.googleapis.com/v1/snapToRoads?path=";
        
        for(int i = 0; i < waypoints.size(); i++)
        {
            if(i<waypoints.size()-1)
            {
                request=request+ waypoints.get(i).getxKoord()+","+waypoints.get(i).getyKoord()+"|";
            } else
            {
                request=request+ waypoints.get(i).getxKoord()+","+waypoints.get(i).getyKoord();
            }
        }
        request = request+"&interpolate=true&key=" + apiKey;
        
        
        System.out.println("request: "+request);
        
        LinkedList<Location> response = new LinkedList<Location>();
        try
        {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read(); 
            
            initializeXMLPullParser();
            InputStream inputstream = new ByteArrayInputStream(answer.getBytes(StandardCharsets.UTF_8));
            GpxData gpx = new GpxData(parser, inputstream);
            response = gpx.loadGpxData();
            System.out.println(response);
            return response;

        } catch (MalformedURLException ex)
        {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        double[] k = {47.066667, 15.433333};
      Location l=  api.KoordToOrt(k);
//        System.out.println(l.toString());
      
      
    }

   
}
