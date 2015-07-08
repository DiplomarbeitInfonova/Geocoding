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
        Location ort;
        
        String requestUrl = "https://maps.googleapis.com/maps/api/geocode/xml?address="+name+"&key="+apiKey;
        
        try {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            String answer = sendObject.read();
            System.out.println(answer);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren des Ortes zu Koordinaten");
        }
        
        ort = new Location(name, 0.0, 0.0);
        
        return ort;
    }
    public String KoordToOrt(double[] koordinaten)
    {
        String requestUrl="https://maps.googleapis.com/maps/api/geocode/xml?latlng="+koordinaten[0]+","+koordinaten[1]+"&key="+apiKey;
        
        try {
            SendToMapsAPI sendObject = new SendToMapsAPI(requestUrl);
            String answer = sendObject.read();
            System.out.println(answer);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Konvertieren des Ortes zu Koordinaten");
        }
        return "";
    }
    
    public static void main(String[] args) {
        GeocodingAPI api = new GeocodingAPI();
        api.OrtToKoord("Ligist");
        double[] k = {46.9917246, 15.2107184};
        api.KoordToOrt(k);
    }
}
