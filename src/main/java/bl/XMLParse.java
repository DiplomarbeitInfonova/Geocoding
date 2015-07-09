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
                        if(l==0||l==3)
                        {
                            if(l==1)
                            {
                                x = Double.parseDouble(string.trim());
                            }
                            else if(l==2)
                            {
                                y = Double.parseDouble(string.trim());
                            }
                        }
                        System.out.println(l+" "+string);
                    }
                }
            }
            loc = new Location("", x, y);
            return loc;
        }
        return null;
    }

    

    
    
    public static void main(String[] args)
    {
        XMLParse xml = new XMLParse("");
        
        
        
        
    }
}
