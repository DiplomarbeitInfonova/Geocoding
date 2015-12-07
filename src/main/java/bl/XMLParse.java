// author Veronika Gößler und David Kollegger
package bl;

import beans.Leg;
import beans.Location;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParse
{

    private static Document xmlDoc;
    private String xmlString;
    private GeocodingAPI geo;
    private double[] koordinaten = new double[2];

    public Document loadXMLFromString() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(this.xmlString));
        return builder.parse(is);
    }

    public XMLParse(String requestUrl)
    {
        this.xmlString = requestUrl;
        try
        {
            xmlDoc = this.loadXMLFromString();
            geo = new GeocodingAPI();
        } catch (Exception ex)
        {
            Logger.getLogger(XMLParse.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Fehler im Constructor XMLParse");
        }
    }

    public Location xmlToLocation()
    {
        double x = 0;
        double y = 0;
        Location loc = null;
        String locName = "";

        Element root = xmlDoc.getDocumentElement();

        NodeList results = root.getElementsByTagName("result");
        for (int i = 0; i < results.getLength(); i++)
        {
            Element e = (Element) results.item(i);

            NodeList addressComponent = e.getElementsByTagName("address_component");
            for (int j = 0; j < addressComponent.getLength(); j++)
            {
                Element eName = (Element) addressComponent.item(j);
                String responseName = eName.getTextContent();
                String name = responseName.split("\n")[1];
                String type = responseName.split("\n")[3];
//                System.out.println("Name: "+name+"|Type: "+type);
                if (type.trim().equals("locality") || type.trim() == "locality")
                {
                    locName = name;
                }
            }

            NodeList geometry = e.getElementsByTagName("geometry");
            for (int j = 0; j < geometry.getLength(); j++)
            {
                Element e2 = (Element) geometry.item(i);
                NodeList locations = e.getElementsByTagName("location");
                for (int k = 0; k < locations.getLength(); k++)
                {
                    Element e3 = (Element) locations.item(i);
                    String responseKoord = e3.getTextContent();
                    String[] split = responseKoord.split("\n");

                    for (int l = 0; l < split.length; l++)
                    {
                        String string = split[l];

                        if (l == 1)
                        {
                            x = Double.parseDouble(string.trim());
                        } else if (l == 2)
                        {
                            y = Double.parseDouble(string.trim());
                        }
                    }
                }
            }

            loc = new Location(locName, x, y,0);

            return loc;
        }
        return null;
    }

    public double xmlElevationInformation()
    {
        double elevation;

//        System.out.println("Ausgabe des xmlStrings");
//        System.out.println(this.xmlString);
//        
        Element root = xmlDoc.getDocumentElement();

        NodeList results = root.getElementsByTagName("result");
        if(results == null)
        {
            return 0.0;
        }
        for (int i = 0; i < results.getLength(); i++)
        {
            Element e = (Element) results.item(i);
            NodeList elevationList = e.getElementsByTagName("elevation");
            Element eElevation = (Element) elevationList.item(0);
            elevation = Double.parseDouble(eElevation.getTextContent());
            //System.out.println(elevation);
            
            return elevation;
        }

        return 0.0;
    }

    public String xmlToDistanceAndDuration()
    {
        String duration = "";
        String distance = "";

        Element root = xmlDoc.getDocumentElement();
        NodeList row = root.getElementsByTagName("row");
        for (int i = 0; i < row.getLength(); i++)
        {
            Element e = (Element) row.item(i);
            NodeList elementList = e.getElementsByTagName("element");
            for (int j = 0; j < elementList.getLength(); j++)
            {
                Element e1 = (Element) elementList.item(j);
                NodeList durationList = e1.getElementsByTagName("duration");
                for (int k = 0; k < durationList.getLength(); k++)
                {
                    Element eDuration = (Element) durationList.item(i);
                    NodeList durationText = eDuration.getElementsByTagName("text");
                    Element dText = (Element) durationText.item(0);
                    duration = dText.getTextContent();
//                    System.out.println(duration);
                }
                NodeList distanceList = e1.getElementsByTagName("distance");
                for (int k = 0; k < distanceList.getLength(); k++)
                {
                    Element eDistance = (Element) distanceList.item(i);
                    NodeList distanceText = eDistance.getElementsByTagName("text");
                    Element dText = (Element) distanceText.item(0);
                    distance = dText.getTextContent();
                }
            }
        }

        return duration + "-" + distance;
    }

    public LinkedList<Leg> xmlFromDistanceAPItoLocations()
    {

        LinkedList<Leg> list = new LinkedList<Leg>();
        Element root = xmlDoc.getDocumentElement();
        NodeList namenList = root.getElementsByTagName("step");

        for (int i = 0; i < namenList.getLength(); i++)
        {

            Element elem = (Element) namenList.item(i);

            NodeList end_loc = elem.getElementsByTagName("end_location");
            NodeList duration = elem.getElementsByTagName("duration");
            NodeList distance = elem.getElementsByTagName("distance");
            NodeList html_instruction = elem.getElementsByTagName("html_instructions");

            for (int k = 0; k < distance.getLength(); k++)
            {
                //
                Element elem_end_loc = (Element) end_loc.item(k);
                Element elem_distance = (Element) distance.item(k);
                Element elem_duration = (Element) duration.item(k);
                //
                NodeList end_loc_lat = elem_end_loc.getElementsByTagName("lat");
                NodeList end_loc_lng = elem_end_loc.getElementsByTagName("lng");
                Element endloclat = (Element) end_loc_lat.item(k);
                Element endloclng = (Element) end_loc_lng.item(k);
                //
                NodeList distance_value = elem_distance.getElementsByTagName("value");
                Element elem_distance_value = (Element) distance_value.item(k);
                NodeList duration_value = elem_duration.getElementsByTagName("value");
                Element elem_duration_value = (Element) duration_value.item(k);
                //
                Element html_instr = (Element) html_instruction.item(k);
                //
                String str_distance = elem_distance_value.getTextContent();
                float f_distance = Float.parseFloat(str_distance);

                String str_duration = elem_duration_value.getTextContent();
                int i_duration = Integer.parseInt(str_duration);
                //
                String str_endloclat = endloclat.getTextContent();
                float f_endloclat = Float.parseFloat(str_endloclat);

                String str_endloclng = endloclng.getTextContent();
                float f_endloclng = Float.parseFloat(str_endloclng);
                //             
                String str_html_instruction = html_instr.getTextContent();
                //
                GeocodingAPI a = new GeocodingAPI();
                double[] koordinaten =
                {
                    f_endloclat, f_endloclng
                };
                //
                Location l = a.KoordToOrt(koordinaten);
                //
                a.getElevationInformation(l);
                double d_elevation = l.getHoehe();
                //
                list.add(new Leg(f_distance, i_duration, f_endloclat, f_endloclng, str_html_instruction, d_elevation));
            }

        }
        return list;

    }

    public LinkedList<Location> xmlFromRoadsAPI()
    {

        LinkedList<Location> list = new LinkedList<Location>();
        Element root = xmlDoc.getDocumentElement();
        NodeList nList = root.getElementsByTagName("snappedPoints");

        for (int i = 0; i < nList.getLength(); i++)
        {
            Element eElement = (Element) nList.item(i);
            NodeList loc = eElement.getElementsByTagName("location");

            for (int k = 0; k < loc.getLength(); k++)
            {
                Element e = (Element) loc.item(k);
                //System.out.println("\nCurrent Element :" + loc.item(k).getNodeName());
                String latitude = e.getElementsByTagName("latitude").item(k).getTextContent();
                double lat = Double.parseDouble(latitude);
                String longitude = e.getElementsByTagName("longitude").item(k).getTextContent();
                double lng = Double.parseDouble(longitude);
                koordinaten[0] = lat;
                koordinaten[1] = lng;
                Location l = geo.KoordToOrt(koordinaten);
                geo.getElevationInformation(l);
                list.add(l);
               
            }
             //System.out.println(list.get(i).toString());
        }
        
        
        for(Location l:list){
            System.out.println(l.toString());
        }
        return list;
    }

    public static void main(String[] args)
    {

        // Test xmlToLocation
//        XMLParse xml = new XMLParse("<GeocodeResponse>\n"
//                + "<status>OK</status>\n"
//                + "<result>\n"
//                + "<type>locality</type>\n"
//                + "<type>political</type>\n"
//                + "<formatted_address>8563 Ligist, Österreich</formatted_address>\n"
//                + "<address_component>\n"
//                + "<long_name>Ligist</long_name>\n"
//                + "<short_name>Ligist</short_name>\n"
//                + "<type>locality</type>\n"
//                + "<type>political</type>\n"
//                + "</address_component>\n"
//                + "<address_component>\n"
//                + "<long_name>Gemeinde Ligist</long_name>\n"
//                + "<short_name>Gemeinde Ligist</short_name>\n"
//                + "<type>administrative_area_level_3</type>\n"
//                + "<type>political</type>\n"
//                + "</address_component>\n"
//                + "<address_component>\n"
//                + "<long_name>Voitsberg</long_name>\n"
//                + "<short_name>Voitsberg</short_name>\n"
//                + "<type>administrative_area_level_2</type>\n"
//                + "<type>political</type>\n"
//                + "</address_component>\n"
//                + "<address_component>\n"
//                + "<long_name>Steiermark</long_name>\n"
//                + "<short_name>Steiermark</short_name>\n"
//                + "<type>administrative_area_level_1</type>\n"
//                + "<type>political</type>\n"
//                + "</address_component>\n"
//                + "<address_component>\n"
//                + "<long_name>Österreich</long_name>\n"
//                + "<short_name>AT</short_name>\n"
//                + "<type>country</type>\n"
//                + "<type>political</type>\n"
//                + "</address_component>\n"
//                + "<address_component>\n"
//                + "<long_name>8563</long_name>\n"
//                + "<short_name>8563</short_name>\n"
//                + "<type>postal_code</type>\n"
//                + "</address_component>\n"
//                + "<geometry>\n"
//                + "<location>\n"
//                + "<lat>46.9917246</lat>\n"
//                + "<lng>15.2107184</lng>\n"
//                + "</location>\n"
//                + "<location_type>APPROXIMATE</location_type>\n"
//                + "<viewport>\n"
//                + "<southwest>\n"
//                + "<lat>46.9841131</lat>\n"
//                + "<lng>15.1947110</lng>\n"
//                + "</southwest>\n"
//                + "<northeast>\n"
//                + "<lat>46.9993350</lat>\n"
//                + "<lng>15.2267258</lng>\n"
//                + "</northeast>\n"
//                + "</viewport>\n"
//                + "</geometry>\n"
//                + "<place_id>ChIJ6-otqdzNb0cREftguuHp0q0</place_id>\n"
//                + "</result>\n"
//                + "</GeocodeResponse>");
//        Location l = xml.xmlToLocation();
        //  Test Elevation Information
        XMLParse xml = new XMLParse("https://roads.googleapis.com/v1/snapToRoads?path=46.72281265258789,15.648141860961914|46.99358367919922,15.411750793457031|47.02824401855469,15.413273811340332|47.032527923583984,15.412571907043457|47.03444290161133,15.411592483520508|47.06233596801758,15.423318862915039|47.06652069091797,15.427214622497559|47.066871643066406,15.43134593963623|47.06647872924805,15.431346893310547|47.06653594970703,15.43171215057373|47.06958770751953,15.434045791625977|47.06946563720703,15.436347961425781|47.068878173828125,15.43686294555664|47.06978225708008,15.438536643981934|47.07022476196289,15.438182830810547&interpolate=true&key=AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo");

        LinkedList<Location> list = xml.xmlFromRoadsAPI();
        for (Location l : list)
        {
            System.out.println(l.toString());
        }

    }
}