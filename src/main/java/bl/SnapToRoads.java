package bl;

import java.io.InputStream;

/**
 *
 * @author patzineubi
 *
 */
public class SnapToRoads {

    

    public SnapToRoads(InputStream inputStream) {
       
    }

    /**
 * Snaps the points to their most likely position on roads using the Roads API.
 */
//private LinkedList<Location> snapToRoads(GeoApiContext context) throws Exception {
//    LinkedList<Location> snappedPoints = new LinkedList<Location>();
//
//    int offset = 0;
//    while (offset < mCapturedLocations.size()) {
//        // Calculate which points to include in this request. We can't exceed the APIs
//        // maximum and we want to ensure some overlap so the API can infer a good location for
//        // the first few points in each request.
//        if (offset > 0) {
//            offset -= PAGINATION_OVERLAP;   // Rewind to include some previous points
//        }
//        int lowerBound = offset;
//        int upperBound = Math.min(offset + PAGE_SIZE_LIMIT, mCapturedLocations.size());
//
//        // Grab the data we need for this page.
//        LatLng[] page = mCapturedLocations
//                .subList(lowerBound, upperBound)
//                .toArray(new LatLng[upperBound - lowerBound]);
//
//        // Perform the request. Because we have interpolate=true, we will get extra data points
//        // between our originally requested path. To ensure we can concatenate these points, we
//        // only start adding once we've hit the first new point (i.e. skip the overlap).
//        SnappedPoint[] points = RoadsApi.snapToRoads(context, true, page).await();
//        boolean passedOverlap = false;
//        for (SnappedPoint point : points) {
//            if (offset == 0 || point.originalIndex >= PAGINATION_OVERLAP - 1) {
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
