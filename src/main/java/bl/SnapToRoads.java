package bl;

import beans.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.RoadsApi;
import com.google.maps.model.SnappedPoint;
import java.io.InputStream;
import java.util.LinkedList;

/**
 *
 * @author patzineubi
 *
 */
public class SnapToRoads {

    private LinkedList<Location> list;

    public SnapToRoads(LinkedList<Location> liste) {
       this.list = liste;
    }

//    /**
// * Snaps the points to their most likely position on roads using the Roads API.
// */
//private LinkedList<Location> snapToRoads(GeoApiContext context) throws Exception {
//    LinkedList<Location> snappedPoints = new LinkedList<Location>();
//
//    int offset = 0;
//    while (offset < list.size()) {
//        // Calculate which points to include in this request. We can't exceed the APIs
//        // maximum and we want to ensure some overlap so the API can infer a good location for
//        // the first few points in each request.
//        if (offset > 0) {
//            offset -= 100;   // Rewind to include some previous points
//        }
//        int lowerBound = offset;
//        int upperBound = Math.min(offset + 100, list.size());
//
//        // Grab the data we need for this page.
//        Location[] page = list
//                .subList(lowerBound, upperBound)
//                .toArray(new Location[upperBound - lowerBound]);
//
//        // Perform the request. Because we have interpolate=true, we will get extra data points
//        // between our originally requested path. To ensure we can concatenate these points, we
//        // only start adding once we've hit the first new point (i.e. skip the overlap).
//        //LinkedList<Location> points = SnapToRoads.snapToRoads(context, true, page).await();
//        boolean passedOverlap = false;
//        for (Location point : points) {
//            if (offset == 0 || point.getxKoord() >= 100 - 1) {
//                passedOverlap = true;
//            }
//            if (passedOverlap) {
//                snappedPoints.add(point);
//            }
//        }
//
//        offset = upperBound;
//    }
//
//    return snappedPoints;
//}
    
    

    public static void main(String[] args) {
        
    }
    
}
