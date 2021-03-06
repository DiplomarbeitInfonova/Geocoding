package bl;

import beans.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.RoadsApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.SnappedPoint;
import com.google.maps.model.SpeedLimit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author patrizianeubauer
 *
 */
public class SnapToRoadsAPI {

    private LinkedList<Location> list;
    private List<LatLng> snappedListe;
    private GeocodingAPI geo;

    public SnapToRoadsAPI(LinkedList<Location> liste) {
        this.list = liste;
        geo = new GeocodingAPI();
        snappedListe = new LinkedList<LatLng>();

        for (Location l : list) {
            snappedListe.add(new LatLng(l.getxKoord(), l.getyKoord()));
        }
    }

    /**
     * Snapped die Punkte zu der höchstwahrscheinlichsten Position auf dem
     * Straßenverlauf mit der Roads API.
     *
     * @param context
     * @return Gibt eine Liste vom Typ SnappedPoints zurück. Diese Klasse
     * SnappedPoints wird von Google mit der Library com.google.maps zur
     * Verfügung gestellt.
     * @throws Exception
     */
    public List<SnappedPoint> snapToRoads(GeoApiContext context) throws Exception {
        List<SnappedPoint> snappedPoints = new ArrayList<>();

        int offset = 0;
        while (offset < snappedListe.size()) {
            // Calculate which points to include in this request. We can't exceed the APIs
            // maximum and we want to ensure some overlap so the API can infer a good location for
            // the first few points in each request.
            if (offset > 0) {
                offset -= 5;   // Rewind to include some previous points
            }
            int lowerBound = offset;
            int upperBound = Math.min(offset + 100, snappedListe.size());

            // Grab the data we need for this page.
            LatLng[] page = snappedListe
                    .subList(lowerBound, upperBound)
                    .toArray(new LatLng[upperBound - lowerBound]);

            // Perform the request. Because we have interpolate=true, we will get extra data points
            // between our originally requested path. To ensure we can concatenate these points, we
            // only start adding once we've hit the first new point (i.e. skip the overlap).
            SnappedPoint[] points = RoadsApi.snapToRoads(context, page).await();
            boolean passedOverlap = false;
            for (SnappedPoint point : points) {
                if (offset == 0 || point.originalIndex >= 100 - 1) {
                    passedOverlap = true;
                }
                if (passedOverlap) {
                    snappedPoints.add(point);
                }
            }

            offset = upperBound;
        }

        return snappedPoints;
    }

    /**
     * Konvertiert eine Liste vom Typ LatLng zu einer Liste vom Typ Location.
     * Der Typ LatLng wird von der Google Library com.google.maps zur Verfügung
     * gestellt.
     *
     * @param latlngList
     * @return Gibt eine Liste vom Typ Location zurück.
     */
    public LinkedList<Location> convertFromLatLngToLocation(List<SnappedPoint> latlngList) {
        LinkedList<Location> list = new LinkedList<Location>();

        double koordinaten[] = new double[2];
        int i = 0;
        for (SnappedPoint point : latlngList) {
            koordinaten[0] = point.location.lat;
            koordinaten[1] = point.location.lng;
            System.out.println(point.location.lat + " " + point.location.lng);

            list.add(i, new Location("",koordinaten[0],koordinaten[1],0));
            i++;
        }

        return list;
    }

    /**
     * Retrieves speed limits for the previously-snapped points. This method is
     * efficient in terms of quota usage as it will only query for unique
     * places.
     *
     * Note: Speed Limit data is only available with an enabled Maps for Work
     * API key.
     */
    public Map<String, SpeedLimit> getSpeedLimits(GeoApiContext context, List<SnappedPoint> points)
            throws Exception {
        Map<String, SpeedLimit> placeSpeeds = new HashMap<>();

        // Pro tip: save on quota by filtering to unique place IDs
        for (SnappedPoint point : points) {
            placeSpeeds.put(point.placeId, null);
        }

        String[] uniquePlaceIds
                = placeSpeeds.keySet().toArray(new String[placeSpeeds.keySet().size()]);

        // Loop through the places, one page (API request) at a time.
        for (int i = 0; i < uniquePlaceIds.length; i += 100) {
            String[] page = Arrays.copyOfRange(uniquePlaceIds, i,
                    Math.min(i + 100, uniquePlaceIds.length));

            // Execute!
            SpeedLimit[] placeLimits = RoadsApi.speedLimits(context, page).await();
            for (SpeedLimit sl : placeLimits) {
                placeSpeeds.put(sl.placeId, sl);
            }
        }

        return placeSpeeds;
    }

    public static void main(String[] args) {

    }

}
