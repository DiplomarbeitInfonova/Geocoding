package bl;

import beans.Location;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

    private XmlPullParser parser;
    private GeocodingAPI geo;
    private InputStream in;
    private double[] koordinaten = new double[2];

    public GpxData(InputStream inputStream) {
        this.geo = new GeocodingAPI();
        this.in = inputStream;
        System.out.println("initPullParser:");
        this.parser = new XmlPullParser() {

            @Override
            public void setFeature(String string, boolean bln) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean getFeature(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setProperty(String string, Object o) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object getProperty(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setInput(Reader reader) throws XmlPullParserException {
                parser.setInput(reader);
            }

            @Override
            public void setInput(InputStream in, String string) throws XmlPullParserException {
                parser.setInput(in, string);
            }

            @Override
            public String getInputEncoding() {
                return parser.getInputEncoding();
            }

            @Override
            public void defineEntityReplacementText(String string, String string1) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getNamespaceCount(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespacePrefix(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespaceUri(int i) throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespace(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getDepth() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getPositionDescription() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getLineNumber() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getColumnNumber() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isWhitespace() throws XmlPullParserException {
                return parser.isWhitespace();
            }

            @Override
            public String getText() {
                return parser.getText();
            }

            @Override
            public char[] getTextCharacters(int[] ints) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getNamespace() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getPrefix() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isEmptyElementTag() throws XmlPullParserException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getAttributeCount() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeNamespace(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeName(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributePrefix(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeType(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isAttributeDefault(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeValue(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getAttributeValue(String string, String string1) {
                return parser.getAttributeValue(string, string1);
            }

            @Override
            public int getEventType() throws XmlPullParserException {
                return parser.getEventType();
            }

            @Override
            public int next() throws XmlPullParserException, IOException {
                return parser.next();
            }

            @Override
            public int nextToken() throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void require(int i, String string, String string1) throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String nextText() throws XmlPullParserException, IOException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int nextTag() throws XmlPullParserException, IOException {
                return parser.nextTag();
            }
        };
    }

    /**
     * Parses the waypoint (wpt tags) data into native objects from a GPX
     * stream.
     */
    public LinkedList<Location> loadGpxData()
            throws XmlPullParserException, IOException {
        // We use a List<> as we need subList for paging later
        LinkedList<Location> list = new LinkedList<Location>();
        this.parser.setInput(this.in, null);
        this.parser.nextTag();

        while (this.parser.next() != XmlPullParser.END_DOCUMENT) {
            if (this.parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (this.parser.getName().equals("wpt")) {
                // Save the discovered lat/lon attributes in each <wpt>
                this.koordinaten[0] = Double.valueOf(this.parser.getAttributeValue(null, "lat"));
                System.out.println(Double.valueOf(this.parser.getAttributeValue(null, "lat")));
                this.koordinaten[1] = Double.valueOf(this.parser.getAttributeValue(null, "lon"));
                list.add(this.geo.KoordToOrt(this.koordinaten));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        LinkedList<Location> list = new LinkedList<Location>();

//        /**-35.274346,149.050000|-35.278012,149.129583|-35.280329,149.129073|-35.280999,149.129293|-35.281441,149.129846*/
//        double [] koordinaten = new double[2];
//        koordinaten[0]=-35.274346;
//        koordinaten[1]=149.050000;
//        list.add(geo.KoordToOrt(koordinaten));
    }

}
