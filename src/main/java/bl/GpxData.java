package bl;

import beans.Location;
import java.io.ByteArrayInputStream;
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

            if (this.parser.getName().equals("location")) {
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
        String str ="<root>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>46.72281262826016</latitude>\n" +
"            <longitude>15.648141346812551</longitude>\n" +
"        </location>\n" +
"        <originalIndex>0</originalIndex>\n" +
"        <placeId>ChIJW-h3kFegb0cRSEPXmIoej4A</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>46.9935836671382</latitude>\n" +
"            <longitude>15.41175110533128</longitude>\n" +
"        </location>\n" +
"        <originalIndex>1</originalIndex>\n" +
"        <placeId>ChIJtRVvPIK1b0cRs63q7FmFAXM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.028244</latitude>\n" +
"            <longitude>15.413273399999996</longitude>\n" +
"        </location>\n" +
"        <originalIndex>2</originalIndex>\n" +
"        <placeId>ChIJJSrZiK7Kb0cRh9MA5hnjsQE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.032527099999996</latitude>\n" +
"            <longitude>15.412572099999995</longitude>\n" +
"        </location>\n" +
"        <originalIndex>3</originalIndex>\n" +
"        <placeId>ChIJAW2wva3Kb0cRcOfUyA5B-Fc</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.032527099999996</latitude>\n" +
"            <longitude>15.412572099999995</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0agTn63Kb0cRcXAiFGzpGHE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0326117</latitude>\n" +
"            <longitude>15.412709800000004</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0agTn63Kb0cRcXAiFGzpGHE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.032724300000005</latitude>\n" +
"            <longitude>15.412882000000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0agTn63Kb0cRcXAiFGzpGHE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.032724300000005</latitude>\n" +
"            <longitude>15.412882000000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJE2g0da3Kb0cR8KXGgAyZRzM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0329185</latitude>\n" +
"            <longitude>15.413021099999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJE2g0da3Kb0cR8KXGgAyZRzM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.032982600000004</latitude>\n" +
"            <longitude>15.413053999999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJE2g0da3Kb0cR8KXGgAyZRzM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0330586</latitude>\n" +
"            <longitude>15.413084300000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJE2g0da3Kb0cR8KXGgAyZRzM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0330586</latitude>\n" +
"            <longitude>15.413084300000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0331376</latitude>\n" +
"            <longitude>15.4130789</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.03320519999999</latitude>\n" +
"            <longitude>15.413063899999996</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.033309499999994</latitude>\n" +
"            <longitude>15.413027500000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0333961</latitude>\n" +
"            <longitude>15.4129873</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0334788</latitude>\n" +
"            <longitude>15.412937200000005</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJp_f9cq3Kb0cRMRITjFslsAw</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0334788</latitude>\n" +
"            <longitude>15.412937200000005</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJy45nbq3Kb0cRfZkhdtr3Krs</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0335708</latitude>\n" +
"            <longitude>15.412833500000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJy45nbq3Kb0cRfZkhdtr3Krs</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.03364879999999</latitude>\n" +
"            <longitude>15.412726</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJy45nbq3Kb0cRfZkhdtr3Krs</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.03364879999999</latitude>\n" +
"            <longitude>15.412726</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJxZbqZa3Kb0cR75a2lCG2XuA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0337253</latitude>\n" +
"            <longitude>15.4125497</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJxZbqZa3Kb0cR75a2lCG2XuA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.033762499999995</latitude>\n" +
"            <longitude>15.412438800000004</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJxZbqZa3Kb0cR75a2lCG2XuA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0338035</latitude>\n" +
"            <longitude>15.412265300000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJxZbqZa3Kb0cR75a2lCG2XuA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0338035</latitude>\n" +
"            <longitude>15.412265300000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0338601</latitude>\n" +
"            <longitude>15.412180599999996</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.033953</latitude>\n" +
"            <longitude>15.4119479</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0339951</latitude>\n" +
"            <longitude>15.4118665</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0340509</latitude>\n" +
"            <longitude>15.411792000000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0340795</latitude>\n" +
"            <longitude>15.411762799999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.034112</latitude>\n" +
"            <longitude>15.411734000000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.03421259999999</latitude>\n" +
"            <longitude>15.4116673</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.034335</latitude>\n" +
"            <longitude>15.411622799999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.034441799999996</latitude>\n" +
"            <longitude>15.411592100000004</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Rzun7LKb0cRo15pMKPkA2g</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.034441799999996</latitude>\n" +
"            <longitude>15.411592100000004</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ68gKtLLKb0cRYEAgvvgxpmA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.03444291246125</latitude>\n" +
"            <longitude>15.4115921878948</longitude>\n" +
"        </location>\n" +
"        <originalIndex>4</originalIndex>\n" +
"        <placeId>ChIJ68gKtLLKb0cRYEAgvvgxpmA</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.062335339455615</latitude>\n" +
"            <longitude>15.423319596328707</longitude>\n" +
"        </location>\n" +
"        <originalIndex>5</originalIndex>\n" +
"        <placeId>ChIJ35cnNm41bkcRra9PdU-C4o8</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0665214</latitude>\n" +
"            <longitude>15.427215</longitude>\n" +
"        </location>\n" +
"        <originalIndex>6</originalIndex>\n" +
"        <placeId>ChIJaYwH63A1bkcRjBUTRl_ajGM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06677169999999</latitude>\n" +
"            <longitude>15.428802899999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJaYwH63A1bkcRjBUTRl_ajGM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06677169999999</latitude>\n" +
"            <longitude>15.428802899999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJNdROonA1bkcRMP11jKxH9fQ</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0667547</latitude>\n" +
"            <longitude>15.429253200000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJNdROonA1bkcRMP11jKxH9fQ</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.066756600000005</latitude>\n" +
"            <longitude>15.429382099999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJNdROonA1bkcRMP11jKxH9fQ</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.066768499999995</latitude>\n" +
"            <longitude>15.4295204</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJNdROonA1bkcRMP11jKxH9fQ</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06677690000001</latitude>\n" +
"            <longitude>15.430074900000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJNdROonA1bkcRMP11jKxH9fQ</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06677690000001</latitude>\n" +
"            <longitude>15.430074900000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJi34GDHo1bkcR0E8Yn1iOLNE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.066780900000005</latitude>\n" +
"            <longitude>15.430854799999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJi34GDHo1bkcR0E8Yn1iOLNE</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.066780900000005</latitude>\n" +
"            <longitude>15.430854799999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ4w2nEXo1bkcR_NPpQdP6ckU</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.066848199999995</latitude>\n" +
"            <longitude>15.431203</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ4w2nEXo1bkcR_NPpQdP6ckU</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06687105860844</latitude>\n" +
"            <longitude>15.43134614080449</longitude>\n" +
"        </location>\n" +
"        <originalIndex>7</originalIndex>\n" +
"        <placeId>ChIJ4w2nEXo1bkcR_NPpQdP6ckU</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0668711</latitude>\n" +
"            <longitude>15.431346400000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ4w2nEXo1bkcR_NPpQdP6ckU</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0668711</latitude>\n" +
"            <longitude>15.431346400000002</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJwey7Fno1bkcRqeEWuNgMxEk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06660970000001</latitude>\n" +
"            <longitude>15.431357400000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJwey7Fno1bkcRqeEWuNgMxEk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06660970000001</latitude>\n" +
"            <longitude>15.431357400000001</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ4ZpWFHo1bkcR4MBkFo_WUp8</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0664772</latitude>\n" +
"            <longitude>15.4313468</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ4ZpWFHo1bkcR4MBkFo_WUp8</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0664772</latitude>\n" +
"            <longitude>15.4313468</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJCZ-CFXo1bkcRSCXzArX2TRM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06647729809248</latitude>\n" +
"            <longitude>15.43134739870153</longitude>\n" +
"        </location>\n" +
"        <originalIndex>8</originalIndex>\n" +
"        <placeId>ChIJCZ-CFXo1bkcRSCXzArX2TRM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06653699845788</latitude>\n" +
"            <longitude>15.431711780230387</longitude>\n" +
"        </location>\n" +
"        <originalIndex>9</originalIndex>\n" +
"        <placeId>ChIJCZ-CFXo1bkcRSCXzArX2TRM</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695875</latitude>\n" +
"            <longitude>15.434045399999997</longitude>\n" +
"        </location>\n" +
"        <originalIndex>10</originalIndex>\n" +
"        <placeId>ChIJQTKMtXk1bkcRgq_6QaBPVT8</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695875</latitude>\n" +
"            <longitude>15.434045399999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJJ3T9rHk1bkcRUjscsGKuV7A</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069558</latitude>\n" +
"            <longitude>15.434655699999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJJ3T9rHk1bkcRUjscsGKuV7A</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069558</latitude>\n" +
"            <longitude>15.434655699999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695385</latitude>\n" +
"            <longitude>15.435150700000003</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06953120000001</latitude>\n" +
"            <longitude>15.435276000000004</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695173</latitude>\n" +
"            <longitude>15.4355795</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695177</latitude>\n" +
"            <longitude>15.4355953</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069513799999996</latitude>\n" +
"            <longitude>15.435659299999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJv9kzAHk1bkcRqSCmVlNKz2c</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069513799999996</latitude>\n" +
"            <longitude>15.435659299999998</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Q6gAnk1bkcRZ0_D6ssPXRk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695062</latitude>\n" +
"            <longitude>15.4357594</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJ0Q6gAnk1bkcRZ0_D6ssPXRk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695062</latitude>\n" +
"            <longitude>15.4357594</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJR8174ng1bkcRm4UTNA1wdKk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0694941</latitude>\n" +
"            <longitude>15.4360982</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJR8174ng1bkcRm4UTNA1wdKk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069465900000004</latitude>\n" +
"            <longitude>15.4363476</longitude>\n" +
"        </location>\n" +
"        <originalIndex>11</originalIndex>\n" +
"        <placeId>ChIJR8174ng1bkcRm4UTNA1wdKk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069465900000004</latitude>\n" +
"            <longitude>15.4363476</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJQ9e-IXk1bkcRAF5roA41Fdc</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0688775</latitude>\n" +
"            <longitude>15.4368626</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJQ9e-IXk1bkcRAF5roA41Fdc</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0688775</latitude>\n" +
"            <longitude>15.4368626</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJL1CCKHk1bkcRIT8S3OSttRk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06887785286665</latitude>\n" +
"            <longitude>15.436863296209822</longitude>\n" +
"        </location>\n" +
"        <originalIndex>12</originalIndex>\n" +
"        <placeId>ChIJL1CCKHk1bkcRIT8S3OSttRk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069337499999996</latitude>\n" +
"            <longitude>15.437770199999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJL1CCKHk1bkcRIT8S3OSttRk</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069337499999996</latitude>\n" +
"            <longitude>15.437770199999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJpaUX1Hg1bkcRAkjBgi4fFFY</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695422</latitude>\n" +
"            <longitude>15.438193499999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJpaUX1Hg1bkcRAkjBgi4fFFY</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0695422</latitude>\n" +
"            <longitude>15.438193499999997</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJvX7C0ng1bkcR9bVUoQKpOm4</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.069702199999995</latitude>\n" +
"            <longitude>15.438442499999999</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJvX7C0ng1bkcR9bVUoQKpOm4</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0697822</latitude>\n" +
"            <longitude>15.4385365</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJvX7C0ng1bkcR9bVUoQKpOm4</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.0697822</latitude>\n" +
"            <longitude>15.4385365</longitude>\n" +
"        </location>\n" +
"        <placeId>ChIJuer_y3g1bkcRdVXeIG4b754</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.06978220305446</latitude>\n" +
"            <longitude>15.438536497570434</longitude>\n" +
"        </location>\n" +
"        <originalIndex>13</originalIndex>\n" +
"        <placeId>ChIJuer_y3g1bkcRdVXeIG4b754</placeId>\n" +
"    </snappedPoints>\n" +
"    <snappedPoints>\n" +
"        <location>\n" +
"            <latitude>47.07022523137837</latitude>\n" +
"            <longitude>15.438184102941596</longitude>\n" +
"        </location>\n" +
"        <originalIndex>14</originalIndex>\n" +
"        <placeId>ChIJuer_y3g1bkcRdVXeIG4b754</placeId>\n" +
"    </snappedPoints>";
        
        
        InputStream inputstream = new ByteArrayInputStream(str.getBytes());
        
//        /**-35.274346,149.050000|-35.278012,149.129583|-35.280329,149.129073|-35.280999,149.129293|-35.281441,149.129846*/
//        double [] koordinaten = new double[2];
//        koordinaten[0]=-35.274346;
//        koordinaten[1]=149.050000;
//        list.add(geo.KoordToOrt(koordinaten));
    }
    
}
