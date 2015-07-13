/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bl;

import beans.Location;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class GeocodingAPI {
    private XMLParse xmlp;
    final String apiKey = "AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo";
    
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
        Location ort=null;
        name = name.replaceAll("", ";");
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/xml?address="+name+"&key="+apiKey;
        String answer="";
        
        try {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            answer = sendObject.read();
            System.out.println(answer);
            xmlp = new XMLParse(answer);
            ort = xmlp.xmlToLocation();
            ort.setName(name);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren des Ortes zu Koordinaten");
        }
        
        return ort;
    }
    public Location KoordToOrt(double[] koordinaten)
    {
        String requestUrl="https://maps.googleapis.com/maps/api/geocode/xml?latlng="+koordinaten[0]+","+koordinaten[1]+"&key="+apiKey;
        Location ort = null;
        try {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            String answer = sendObject.read();
            System.out.println(answer);
            ort=xmlp.xmlToLocation();
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren der Koordinaten zum Ort");
        }
        return ort;
    }
    
    public String LocationToDistance(Location a, Location b)
    {
        String request="http://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + a.getxKoord() + "," + a.getyKoord() + "&destinations=" + b.getxKoord() + "," + b.getyKoord() ;
        String response="";
        try {
            SendToMapsAPI sendObject = new SendToMapsAPI(request);
            String answer = sendObject.read();
            System.out.println(answer);
            xmlp = new XMLParse(answer);
            response= xmlp.xmlToDistanceAndDuration();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GeocodingAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return response;
    }
    
    
    public static void main(String[] args) {
        GeocodingAPI api = new GeocodingAPI();
        System.out.println(api.OrtToKoord("Ligist").toString());
        double[] k = {46.9917246, 15.2107184};
        //api.KoordToOrt(k);
    }
}
