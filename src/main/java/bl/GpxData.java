package bl;

import beans.Location;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author patzineubi
 *
 */
public class GpxData {

    private String request;
    private XmlPullParser parser;
    private GeocodingAPI geo;
    private InputStream in;

    public GpxData(XmlPullParser parse, InputStream inputStream) {
        this.geo = new GeocodingAPI();
        this.parser = parse;
        this.in = inputStream;
    }

    /**
     * Parses the waypoint (wpt tags) data into native objects from a GPX
     * stream.
     */
    private List<Location> loadGpxData(XmlPullParser parser, InputStream gpxIn)
            throws XmlPullParserException, IOException {
        // We use a List<> as we need subList for paging later
        List<Location> list = new LinkedList<>();
        parser.setInput(gpxIn, null);
        parser.nextTag();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            double[] koordinaten = new double[2];
            koordinaten[0] = Double.valueOf(parser.getAttributeValue(null, "lat"));
            koordinaten[1] = Double.valueOf(parser.getAttributeValue(null, "lon"));

            if (parser.getName().equals("wpt")) {
                // Save the discovered lat/lon attributes in each <wpt>
                list.add(geo.KoordToOrt(koordinaten));
            }
            // Otherwise, skip irrelevant data
        }
        return list;
    }

    public static void main(String[] args) {

    }

}
