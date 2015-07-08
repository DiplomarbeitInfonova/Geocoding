/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bl;

/**
 *
 * @author David
 */
public class GeocodingAPI {
    
    
    /*
        Die Methode findet zum eingegeben Ort die passenden
        x- und y- Koordinaten und liefert diese in einem 
        double-Feld zurück
        Beispiel-Link: https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=API_KEY
    
        Key: AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo
    
       
    */
    public double[] OrtToKoord(String name)
    {
        double[] koordinaten = new double[2];
        
        
        
        
        return koordinaten;
    }
    public String KoordToOrt(double[] koordinaten)
    {
        //https://maps.googleapis.com/maps/api/geocode/json?address=Ligist&key=AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo
        
        
        return "";
    }
}
