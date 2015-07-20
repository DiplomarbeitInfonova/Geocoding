/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Leg;
import beans.Location;
import java.util.LinkedList;

/**
 *
 * @author Domi
 */
public class LocationParser {
    private GeocodingAPI geocoding;
    public LocationParser(){
        geocoding=new GeocodingAPI();
    }
    
    
    
    //String name, double xKoord, double yKoord, double hoehe
    
    public LinkedList<Location> LegtoLocation(LinkedList<Leg> leglist){
        LinkedList<Location> returnlist=new LinkedList<Location>();
        double[] koordinaten=new double[2];
        for (int i = 0; i < leglist.size(); i++) {
            koordinaten[0]=(double)leglist.get(i).getEnd_loc_lat();
            koordinaten[1]=(double)leglist.get(i).getEnd_loc_lng();
            returnlist.add(geocoding.KoordToOrt(koordinaten));
        }
        return returnlist;
    }
}
