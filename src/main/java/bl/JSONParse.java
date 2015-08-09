package bl;

import beans.Location;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;


/**
 *
 * @author patzineubi
 * 
 */
public class JSONParse {

    public String request;
    public JSONObject jsonobj;

    public JSONParse(String s) {
        
        try {
            //now parse
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(s);
            jsonobj = (JSONObject) obj;
            
            System.out.println("object: "+jsonobj);

        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(JSONParse.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

    public LinkedList<Location> convert() {

        LinkedList<Location> list = new LinkedList<Location>();
        
        JSONArray jsonObject1 = (JSONArray) jsonobj.get("snappedPoints");
         //JSONObject jsonObject2 = (JSONObject)jsonObject1.get(0);
         System.out.println(jsonObject1);
//            JSONObject jsonObject3 = (JSONObject)jsonObject2.get("geometry");
//            JSONObject location = (JSONObject) jsonObject3.get("location");
//
//            System.out.println( "Lat = "+location.get("lat"));
//            System.out.println( "Lng = "+location.get("lng"));

        
        
        return null;
    }

    public static void main(String[] args) {

    }

}
