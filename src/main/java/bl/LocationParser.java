package bl;

import beans.Leg;
import beans.Location;
import java.util.LinkedList;

/**
 *
 * @author Dominik
 */
public class LocationParser
{

    private GeocodingAPI geocoding;

    public LocationParser()
    {
        geocoding = new GeocodingAPI();
    }

    /**
     * Diese Methode wandelt die übergebene Liste von Legs (wird beim HTML- Request benötigt)
     * in eine Liste von Locations um. 
     * 
     * @param leglist
     * @return 
     */
    public LinkedList<Location> LegtoLocation(LinkedList<Leg> leglist)
    {
        //Author Dominik
        LinkedList<Location> returnlist = new LinkedList<Location>();
        double[] koordinaten = new double[2];
        for (int i = 0; i < leglist.size(); i++)
        {
            koordinaten[0] = (double) leglist.get(i).getEnd_loc_lat();
            koordinaten[1] = (double) leglist.get(i).getEnd_loc_lng();
            Location l = new Location("", koordinaten[0], koordinaten[1]);
            returnlist.add(l);
            //System.out.println(returnlist.get(i).toString());
        }
        return returnlist;
    }
}
