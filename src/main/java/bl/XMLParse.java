// author Veronika Gößler
package bl;

import beans.Location;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParse {

    private static Document xmlDoc;
    private String xmlString;

    public Document loadXMLFromString() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(this.xmlString));
        return builder.parse(is);
    }

    public XMLParse(String requestUrl) {
        this.xmlString = requestUrl;
        try {
            xmlDoc = this.loadXMLFromString();
        } catch (Exception ex) {
            Logger.getLogger(XMLParse.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("                      Fehler im Constructor XMLParse");
        }
    }

    public Location xmlToLocation() {
        double x = 0;
        double y = 0;
        Location loc = null;

        Element root = xmlDoc.getDocumentElement();

        NodeList results = root.getElementsByTagName("result");
        for (int i = 0; i < results.getLength(); i++) {
            Element e = (Element) results.item(i);

            NodeList geometry = e.getElementsByTagName("geometry");
            for (int j = 0; j < geometry.getLength(); j++) {
                Element e2 = (Element) geometry.item(i);
                NodeList locations = e.getElementsByTagName("location");
                for (int k = 0; k < locations.getLength(); k++) {
                    Element e3 = (Element) locations.item(i);
                    String responseKoord = e3.getTextContent();
                    String[] split = responseKoord.split("\n");
                    
                    for (int l = 0; l < split.length; l++)
                    {
                        String string = split[l];
              
                        if (l == 1) {
                            x = Double.parseDouble(string.trim());
                        } else if (l == 2) {
                            y = Double.parseDouble(string.trim());
                        }
                    }
                }
            }
            loc = new Location("", x, y);
            return loc;
        }
        return null;
    }
    
    public String xmlToDistanceAndDuration()
    {
        String duration="";
        String distance="";
        
        Element root = xmlDoc.getDocumentElement();
        NodeList row = root.getElementsByTagName("row");
        for (int i = 0; i < row.getLength(); i++)
        {
            Element e = (Element) row.item(i);
            NodeList elementList = e.getElementsByTagName("element");
            for (int j = 0; j < elementList.getLength(); j++) {
                Element e1 = (Element)elementList.item(j);
                NodeList durationList = e1.getElementsByTagName("duration");
                for (int k = 0; k < durationList.getLength(); k++)
                {
                    Element eDuration = (Element)durationList.item(i);
                    NodeList durationText = eDuration.getElementsByTagName("text");
                    Element dText = (Element)durationText.item(0);
                    duration = dText.getTextContent();
                    System.out.println(duration);                    
                }
                NodeList distanceList = e1.getElementsByTagName("distance");
                for (int k = 0; k < distanceList.getLength(); k++)
                {
                    Element eDistance = (Element)distanceList.item(i);
                    NodeList distanceText = eDistance.getElementsByTagName("text");
                    Element dText = (Element)distanceText.item(0);
                    distance = dText.getTextContent();
                    System.out.println(distance);                    
                }
            }
        }
        
        return duration+"-"+distance;
    }
    
    public static void main(String[] args)
    {
        XMLParse xml = new XMLParse("<DistanceMatrixResponse>\n"
                + "<status>OK</status>\n"
                + "<origin_address>Hauptstraße 5, 2100 Leobendorf, Österreich</origin_address>\n"
                + "<destination_address>\n"
                + "Kalsdorf b.Graz Walther-Kamschal-Platz, 8401 Kalsdorf bei Graz, Österreich\n"
                + "</destination_address>\n"
                + "<row>\n"
                + "<element>\n"
                + "<status>OK</status>\n"
                + "<duration>\n"
                + "<value>7781</value>\n"
                + "<text>2 Stunden, 10 Minuten</text>\n"
                + "</duration>\n"
                + "<distance>\n"
                + "<value>223976</value>\n"
                + "<text>224 km</text>\n"
                + "</distance>\n"
                + "</element>\n"
                + "</row>\n"
                + "</DistanceMatrixResponse>");
        xml.xmlToDistanceAndDuration();
        
        
    }
}
