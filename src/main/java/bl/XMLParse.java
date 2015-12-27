// author Veronika Gößler, David Kollegger, Patrizia Neubauer
package bl;

import beans.Leg;
import beans.Location;
import gui.EingabeGUI;
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

    /**
     * Patrizia
     * Die Antwort von der Google Distance API wird hier bearbeitet und in eine Liste von Legs umgeformt.
     * @return Eine Liste von Legs wird zurückgegeben.
     */
    public LinkedList<Leg> xmlFromDistanceAPItoLocations()
    {
        System.out.println("DIRECTIONS "
                + "API "
                + "RESPONSE"
                + "----------------------------"+this.xmlString+"\n");
        EingabeGUI.updateStatus("Antwort von Google wird verarbeitet");
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
            NodeList polyline = elem.getElementsByTagName("polyline");

            for (int k = 0; k < distance.getLength(); k++)
            {
                
                Element elem_end_loc = (Element) end_loc.item(k);
                Element elem_distance = (Element) distance.item(k);
                Element elem_duration = (Element) duration.item(k);
                Element elem_polyline = (Element) polyline.item(k);
                
                NodeList end_loc_lat = elem_end_loc.getElementsByTagName("lat");
                NodeList end_loc_lng = elem_end_loc.getElementsByTagName("lng");
                Element endloclat = (Element) end_loc_lat.item(k);
                Element endloclng = (Element) end_loc_lng.item(k);
                
                NodeList distance_value = elem_distance.getElementsByTagName("value");
                Element elem_distance_value = (Element) distance_value.item(k);
                NodeList duration_value = elem_duration.getElementsByTagName("value");
                Element elem_duration_value = (Element) duration_value.item(k);
                
                NodeList polyline_points = elem_polyline.getElementsByTagName("points");
                Element elem_polyline_points = (Element) polyline_points.item(k);
                System.out.println(elem_polyline_points.toString());
                
                Element html_instr = (Element) html_instruction.item(k);
                
                String str_distance = elem_distance_value.getTextContent();
                float f_distance = Float.parseFloat(str_distance);

                String str_duration = elem_duration_value.getTextContent();
                int i_duration = Integer.parseInt(str_duration);
                
                String str_endloclat = endloclat.getTextContent();
                float f_endloclat = Float.parseFloat(str_endloclat);

                String str_endloclng = endloclng.getTextContent();
                float f_endloclng = Float.parseFloat(str_endloclng);
                             
                String str_html_instruction = html_instr.getTextContent();
                
                String str_polyline_points = elem_polyline_points.getTextContent();
                        
                GeocodingAPI a = new GeocodingAPI();
                double[] koordinaten =
                {
                    f_endloclat, f_endloclng
                };

                Location l = new Location(koordinaten);

                a.getElevationInformation(l);
                double d_elevation = l.getHoehe();

                list.add(new Leg(f_distance, i_duration, f_endloclat, f_endloclng, str_html_instruction, d_elevation, str_polyline_points));
            }
        }
        return list;

    }

    /**
     * Patrizia
     * Die Antwort von der Google Roads API wird in eine Liste von Locations umgeformt.
     * @return Eine Liste von Locations wird zurückgegeben.
     */
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
                String latitude = e.getElementsByTagName("latitude").item(k).getTextContent();
                double lat = Double.parseDouble(latitude);
                String longitude = e.getElementsByTagName("longitude").item(k).getTextContent();
                double lng = Double.parseDouble(longitude);
                koordinaten[0] = lat;
                koordinaten[1] = lng;
                Location l = new Location(koordinaten);
                geo.getElevationInformation(l);
                list.add(l);  
            }
        }
        
        
        for(Location l:list){
            System.out.println(l.toString());
        }
        return list;
    }

    public static void main(String[] args)
    {

        //Test xmlToLocation
        XMLParse xml = new XMLParse("<DirectionsResponse>\n" +
" <status>OK</status>\n" +
" <route>\n" +
"  <summary>A9</summary>\n" +
"  <leg>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>46.7077629</lat>\n" +
"     <lng>15.7699030</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>46.7228121</lat>\n" +
"     <lng>15.6481414</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>ora|G{`g_B?B?XBzAJx@Fb@DRF^Jj@H^XhAL^\\fA\\bAXx@J\\Pn@Lf@Jj@Fj@@N@\\@f@?hA?z@?jAAdF?bDAj@?^?LA\\GbC?NEhBEhBA|AAb@?NCx@I~AGrAGnAG~@Ix@G`@Kd@WfAu@bCUx@Id@Gh@ANC|@EpBGtAGp@CZE\\M`A_AbGYpB[bBMd@YfA]`A[|@}@dC[x@o@|Aa@~@s@|Am@vAq@xAg@lAKP]|@O`@Od@Sl@Ql@Ql@Wz@I\\Kb@Kl@Kp@Gt@En@G`AAj@Ad@?hACr@A^Cj@KlAMrASpBQzAK|@EZKrAIxAEv@A`@EnBEhACXKx@OhACXIx@C`@Ez@GfCCrBA|@Ar@@\\?b@@d@FzBBjA?l@Al@Cd@Ex@E~@KlCIlBGdAM|BAb@Cx@?j@A`@@zA?dA?lB?lACzBCrBAdAErAKrBElAGlAAf@Ab@?@Af@@R@XBZBRF\\HZLp@J\\\\`BJl@Ff@BTB`@@^@T?d@?b@AZAd@Ev@Ez@A^?\\AL?@?L?H?R?j@@j@@l@BfABz@@n@Dv@Dx@Dn@B`@Db@BZDVFXF^FV\\~AHb@BTJt@Fj@Dn@FhBF`BDt@@ZB^Dh@Bf@L|@RrAb@nCRhARnAj@vD~@lGVbBLdAT~ABT@^?L?VCb@InACVSnCI`BOhCKjBCt@A~@ClDC~AA`@A^CXC\\EPCRSz@o@|CO|@E^E\\Eh@Gv@MzAGj@EZId@If@Op@Mh@KXIRKTQTQRYPYLYRGFKJMPKPEJGPCJ[lA_AhDc@xAq@|Ba@~AYjAS|@Qx@YpAKl@Kl@CLS`BOv@Mt@GRWx@Od@O^Yj@Sx@G`@It@I~AMxBEdAMdCMlECtACpCGnEEdDCtBEbCAXAf@I~@CTETUtAQhAK|@OnBYdEGx@Ad@I`CQvFEhBIjBKtCc@rPKzCUlFMjEIxBQlDc@nDStAmA`Hc@pBSz@s@xCaA~DSfAi@|CSnAS|@gAvDwFnPSh@Up@o@xA}A~Eu@|Be@fAU^U\\</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>573</value>\n" +
"     <text>10 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Head &lt;b&gt;west&lt;/b&gt; on &lt;b&gt;Grazer Str.&lt;/b&gt;/&lt;b&gt;B69&lt;/b&gt; toward &lt;b&gt;Stubenbergdamm&lt;/b&gt;&lt;div style=&quot;font-size:0.9em&quot;&gt;Continue to follow B69&lt;/div&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>9860</value>\n" +
"     <text>9.9 km</text>\n" +
"    </distance>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>46.7228121</lat>\n" +
"     <lng>15.6481414</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>46.9935824</lat>\n" +
"     <lng>15.4117510</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>qpd|G{go~Aq@BmBTq@NgAZq@ZkAp@qBnAURiAd@y@VcDtAyAt@yAt@uCfBc@XaJvH}A|AmBxB}BvCiAxAmAlBu@jAsBpDuDvHaBnEo@fB_@lAUv@e@`BU~@_@|Ak@fCa@zBO~@QbA[zB]fCO|A?BIr@Ed@]bEOpBSfCYbDi@|EYrBKt@UvAiAlGc@vBGV_@fBYfAk@zBk@tBe@`Bw@lCa@rA}A~Em@lBIR]fAyBlGmB`Fo@~Am@`BmChGQ^qAxCk@jAKPa@~@kAdCmA~BmCfFMRsAdCiBzCS^o@hA}@tAw@fA_@l@k@z@?@i@v@GH[b@y@hA{B~CkClDcC|CiEdF_CdCcCjCw@r@k@h@yAtAmBfBsBjBQLw@p@qAhA}AnAWRSNuB~AsA`AkCjBwBxAOJ_CzAmDzByA~@QJi@Zu@b@uBnAu@d@}ClBiDtBuChBiD|By@h@uFzDC@mDhCyFlEaBnAuDvC]XGDCBSPsDrCkBxAsB~AeAt@e@^eD~BkD|ByA~@kBhA]RIFSJ_CtAOJOH}@f@{@f@u@`@aD`BgAf@aH~C{CpAcDnA[LSFm@VgA^{Ah@yAd@gA^wA`@wAb@}Ab@iA\\yA^}A`@uA^_B`@gAXsAZgB`@_B\\}A\\sBb@eCh@{Cl@qP`DoAVw@Lqe@fJwE~@wE`A}Dx@oFjAuBf@uEjAyA^IBIBGBkBd@uCz@mBh@aDbA{@XyAf@aBj@uBv@{Bz@{D`B}BbAuAn@aBv@}At@qAp@cAh@uAv@}@d@qAt@cAl@{A|@oAv@y@f@y@h@y@j@w@j@a@XGDk@^}B`ByB`BsAdAuBbB{BhBqBdBoBfBsBnBiCfCuBvBgClCsAxAwBbCe@j@e@l@s@v@uDrEwDzEiBdCcDxEiA`BmCdE{BnDk@|@cBtC_CbEo@fAg@~@g@|@mBpDcB~C{EnJm@lAm@lA{GpNGLyA~CiA`CwAzCKTO\\uArCSd@cE|ImFdLqFnLaBnDINGLGNCDMXgBvD_BjDaE~I{BzEuA~CQZgCvF}AdDoAjCg@hAi@jAUd@w@bB}@nBm@pA[n@IPIRuCnGm@pA]t@iAbCqApC{@fBs@xAYl@gA|BaBjDiCrF_@v@oAnCmAfCk@pAqApC}@nBaCbFyBpEgAxBiAxB_AfBiArBm@fAABk@`AqBfDyDjGyA|BqBxCq@~@MP}@rA{DpFGHQTU\\gCpDi@v@qFnHy@fA[b@a@l@uEpGiCjDsAhBm@x@oCrD[d@g@p@_@d@wBrCuCrD_A`Aa@h@kAtAm@|@UXsAjBuAfBkDdEc@f@e@h@mApAkBnB_CfCqBpBs@p@sAjAaAv@q@j@[TUNm@b@oBrAy@f@_Aj@mAp@ULm@Zs@^}@b@kAh@kAd@_Bn@iA`@y@TE@}@ZoD`AeDl@wAVcD`@e@F{C`@A?gCNaCJaBF_A@aA@yB@eCBiCAQ?Q?e@AE?}IEE?S?k@?{ECiECaCC_FCO?oIGiCAkDCsE?{D?eID{BDaEDcEJcDJ}@Dq@BI@}@BaBLaDRyCTkHr@oCZuF|@yBb@wAV}Bb@aAR{A\\}A^gBb@eAVy@Tk@NuCt@sAb@cCx@_DhA}Bx@iBt@}CjA{Al@uB`A_Bt@UJaNjH[PiF~CoBnAi@\\GDw@h@_C|AyAbAc@Xk@b@sA~@mCjBeAv@s@h@[V}AjAiA~@mB|Aq@f@a@\\{AlA{AnA{@t@OLi@f@{@r@y@r@o@h@qBbBiA`A_@Zu@p@sAdAy@t@{@r@[Vg@`@a@\\qBbB]XkB`BoB`BaElDWTs@j@a@\\YV[Vy@r@a@\\m@f@uAlA}@t@q@j@iA`AeA|@gBzAeAz@OLa@^MJ]VYTqAhAgDrCoBbBkCxBwFzEu@n@aBtAc@`@e@^yTfRe@`@eFjEoB~AaI|GcBxAcJxHqFxEsNxLgAz@cEjDaBtAwBjB}KnJaI|GuAjAwE~DyBfBuSjQuOvMuAhAoAdAuFnEgDlCkEfDSNEBk@d@yCtB{B`BoAv@mD~BkDxB{ChBoBfAkB`AaAf@eD~A</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>1145</value>\n" +
"     <text>19 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Slight &lt;b&gt;right&lt;/b&gt; to merge onto &lt;b&gt;A9&lt;/b&gt; toward &lt;b&gt;Graz&lt;/b&gt;/&lt;b&gt;Deutschland&lt;/b&gt;&lt;div style=&quot;font-size:0.9em&quot;&gt;Partial toll road&lt;/div&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>36511</value>\n" +
"     <text>36.5 km</text>\n" +
"    </distance>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>46.9935824</lat>\n" +
"     <lng>15.4117510</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0282440</lat>\n" +
"     <lng>15.4132734</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>{ly}Gmba}A}An@oAh@{GlCuAf@MDwBr@uAd@}@Tu@T_AZaBb@gCn@}A\\}@R}A\\{B`@yAVoARq@LcC\\uD`@uDb@u@Do@D}@DiAFmDLgBFkBDuH?mBC_AA}DIeCKSAgE[oCU}BYoG{@_Di@q@MaAQuCs@sD{@q@OcBe@qC_A}@[AAOGiBo@kCaAeC}@oEcBcDmAy@[OGu@Y{D{AcA[i@OuA_@sAYe@IeBY</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>150</value>\n" +
"     <text>3 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Keep &lt;b&gt;left&lt;/b&gt; at the fork to stay on &lt;b&gt;A9&lt;/b&gt;&lt;div style=&quot;font-size:0.9em&quot;&gt;Toll road&lt;/div&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>3950</value>\n" +
"     <text>4.0 km</text>\n" +
"    </distance>\n" +
"    <maneuver>fork-left</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0282440</lat>\n" +
"     <lng>15.4132734</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0325271</lat>\n" +
"     <lng>15.4125721</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>oe`~G}ka}Am@Fq@EiC?_AB_ADy@HuARiB^E@e@JUJ[J_@Lk@PODO@OAKCOE</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>37</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Take exit &lt;b&gt;Graz&lt;/b&gt; on the &lt;b&gt;left&lt;/b&gt; toward &lt;b&gt;Graz&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>483</value>\n" +
"     <text>0.5 km</text>\n" +
"    </distance>\n" +
"    <maneuver>ramp-left</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0325271</lat>\n" +
"     <lng>15.4125721</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0344418</lat>\n" +
"     <lng>15.4115921</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>i`a~Gqga}ACIKQUa@g@[KEOEO?MBSDQFOHQTA@MPOb@ETG`@KPKZEPINEHCDEDEDIDIDYHSD</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>30</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>At the roundabout, take the &lt;b&gt;2nd&lt;/b&gt; exit onto the &lt;b&gt;Kärntner Str.&lt;/b&gt;/&lt;b&gt;B70&lt;/b&gt; ramp to &lt;b&gt;A9&lt;/b&gt;/&lt;b&gt;Slowenien&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>277</value>\n" +
"     <text>0.3 km</text>\n" +
"    </distance>\n" +
"    <maneuver>roundabout-right</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0344418</lat>\n" +
"     <lng>15.4115921</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0623348</lat>\n" +
"     <lng>15.4233186</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>gla~Gmaa}AQA]Dm@JmAX[J{Bp@q@PI@GDIFu@g@ICGAG?]UeAk@i@USKUIOGUIQIi@So@W]Mm@Uo@WaA[YK[KgAa@m@U[Mm@YWOiBs@e@S}CcAkBa@A?_@IwA[cB[qCi@mAUyAWaCc@}Bc@[EwCi@gCWSCu@IcBOwC[yCYQAqGo@gI{@aAMqAOMA]GOCYEYEoAOYEOGOEu@Wc@S_Ae@i@Uc@QaAa@qAi@YO[SIGQQ[[OSQUKSQ]Yg@[m@i@_Aw@{A_@{@O[IKEE_BeBMOA?MOOOAAUUECQEiAeA_@_@e@e@_@a@g@i@EE[_@UYKQW_@U_@</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>365</value>\n" +
"     <text>6 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Keep &lt;b&gt;right&lt;/b&gt;, follow signs for &lt;b&gt;Graz&lt;/b&gt;/&lt;b&gt;bahnhof&lt;/b&gt; and merge onto &lt;b&gt;Kärntner Str.&lt;/b&gt;/&lt;b&gt;B70&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>3329</value>\n" +
"     <text>3.3 km</text>\n" +
"    </distance>\n" +
"    <maneuver>keep-right</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0623348</lat>\n" +
"     <lng>15.4233186</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0665214</lat>\n" +
"     <lng>15.4272150</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>qzf~Gwjc}AYo@a@y@O]KQCECCEAMIuAk@i@QSKUMcBsAiCoBwBiByD{CC]AGAS</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>109</value>\n" +
"     <text>2 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Continue straight onto &lt;b&gt;Lazarettgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>565</value>\n" +
"     <text>0.6 km</text>\n" +
"    </distance>\n" +
"    <maneuver>straight</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0665214</lat>\n" +
"     <lng>15.4272150</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0668711</lat>\n" +
"     <lng>15.4313464</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>wtg~Gccd}Aq@{HByAAYA[AmB?{CGk@EYC]</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>42</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Continue onto &lt;b&gt;Rösselmühlgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>318</value>\n" +
"     <text>0.3 km</text>\n" +
"    </distance>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0668711</lat>\n" +
"     <lng>15.4313464</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0664772</lat>\n" +
"     <lng>15.4313468</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>}vg~G}|d}Ar@AX@</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>7</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;right&lt;/b&gt; onto &lt;b&gt;Griespl.&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>44</value>\n" +
"     <text>44 m</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-right</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0664772</lat>\n" +
"     <lng>15.4313468</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0665371</lat>\n" +
"     <lng>15.4317124</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>otg~G}|d}AEg@E_@</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>7</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;left&lt;/b&gt; toward &lt;b&gt;Griesgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>28</value>\n" +
"     <text>28 m</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-left</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0665371</lat>\n" +
"     <lng>15.4317124</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0695875</lat>\n" +
"     <lng>15.4340454</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>{tg~Ge_e}AwAcA_@[_@]II_@a@oAkAuBoBoA_Ak@YSEiAM</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>91</value>\n" +
"     <text>2 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;left&lt;/b&gt; onto &lt;b&gt;Griesgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>387</value>\n" +
"     <text>0.4 km</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-left</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0695875</lat>\n" +
"     <lng>15.4340454</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0694659</lat>\n" +
"     <lng>15.4363476</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>}gh~Gyme}ADyBBaB@Y@{@?C@K?S@y@@I?I@K@[</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>46</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;right&lt;/b&gt; onto &lt;b&gt;Tegetthoffbrücke&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>175</value>\n" +
"     <text>0.2 km</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-right</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0694659</lat>\n" +
"     <lng>15.4363476</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0688775</lat>\n" +
"     <lng>15.4368626</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>egh~Ge|e}AtBeB</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>12</value>\n" +
"     <text>1 min</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;right&lt;/b&gt; onto &lt;b&gt;Neutorgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>76</value>\n" +
"     <text>76 m</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-right</maneuver>\n" +
"   </step>\n" +
"   <step>\n" +
"    <travel_mode>DRIVING</travel_mode>\n" +
"    <start_location>\n" +
"     <lat>47.0688775</lat>\n" +
"     <lng>15.4368626</lng>\n" +
"    </start_location>\n" +
"    <end_location>\n" +
"     <lat>47.0703735</lat>\n" +
"     <lng>15.4394746</lng>\n" +
"    </end_location>\n" +
"    <polyline>\n" +
"     <points>och~Gk_f}A{AuDg@sA_@q@OSuByD</points>\n" +
"    </polyline>\n" +
"    <duration>\n" +
"     <value>104</value>\n" +
"     <text>2 mins</text>\n" +
"    </duration>\n" +
"    <html_instructions>Turn &lt;b&gt;left&lt;/b&gt; onto &lt;b&gt;Landhausgasse&lt;/b&gt;</html_instructions>\n" +
"    <distance>\n" +
"     <value>259</value>\n" +
"     <text>0.3 km</text>\n" +
"    </distance>\n" +
"    <maneuver>turn-left</maneuver>\n" +
"   </step>\n" +
"   <duration>\n" +
"    <value>2718</value>\n" +
"    <text>45 mins</text>\n" +
"   </duration>\n" +
"   <distance>\n" +
"    <value>56262</value>\n" +
"    <text>56.3 km</text>\n" +
"   </distance>\n" +
"   <start_location>\n" +
"    <lat>46.7077629</lat>\n" +
"    <lng>15.7699030</lng>\n" +
"   </start_location>\n" +
"   <end_location>\n" +
"    <lat>47.0703735</lat>\n" +
"    <lng>15.4394746</lng>\n" +
"   </end_location>\n" +
"   <start_address>Mureck, Austria</start_address>\n" +
"   <end_address>Graz, Austria</end_address>\n" +
"  </leg>\n" +
"  <copyrights>Map data ©2015 Google</copyrights>\n" +
"  <overview_polyline>\n" +
"   <points>ora|G{`g_B?\\NtC`@bCnApEtAjEb@nCBjEC`OInE]nOa@|GSfAmAjE_@~AIx@YvHwA~Ju@tEg@lBsC~HsDrIcCvFeAbD_AjDe@dEK|EUlE_A~Ia@`GQtFi@vEUxIArDLzGQlEk@~LGjD@|IOhK[vHA`BPfB`AlEZlCB~BSbGJtHRpERxBt@pD`@zCj@~LxAjJvCxRh@zEMxCq@rKO`DElFKzDa@~B_AzEK|@c@hFi@zCo@lBc@h@s@^{@x@]z@qDnMaBbHu@zDy@bFqAjD[zAStCo@rOc@x[i@`E]fCi@tHe@xM_A~[y@nUQlDc@nDaBvJmDfOeBrJgAvDwFnPi@zAmCxH{AdEk@|@_DXyBj@}BlAgCbBcC|@}FjCoF|CeKpIkEvEgEpFcCxDiHhNqCvHqBfHkAdFq@zDm@~Dm@dFm@`Hc@xFcA`KeCnOeB~HuD`NwDtLwCtI}C`I_HdPeDbH}H`OkFzIcCrDgCnDgGlImIbKgJnJmIvH_G~EiJ|GuL|HkEjCsNxIoRlMmVdRgL~I}LrIaL|GqIrEiJfE_I`DeDlAuIpCyIdC{I|ByJxBg]|Gqp@lM{VnF{TbGyKpDkPxGgJnEiH|DiHjEmEzCkHhFwLxJcNvMyJvKgMtOmG~IwEfHkKfQmFvJ{KjTgM`Xa[|p@uI`Rw_@xy@wb@n~@aK~SaG|KyIxNkGhJmU|[iVn\\{EpGcItJmFjHcIhJqKzKcFdEmFnDqEfCiGrCiFhBmF|A}FdAgJjAiGZaDHkMDsNG}k@]oK?aMJgRb@{EV{Hh@{LnAoJ`BuEz@cJtBaIrBuN`FyMpFsR~JkKrGuHdFsIfGwFlE}GpFqEzDkOjMg^tZe]rY{fB|zAsn@bi@{j@te@yTjQ_FnDkExCyIxFyLzGcGnCkJvDqHfCuGjBaK~BwIzAyH~@kFh@eJ`@sELcLCwKYwIq@mKuAqEw@wEeAeFkAuFeByDuAa]kMwGeBkCc@m@Fq@EiEByBNkF`A}Bv@_@F[ESOa@s@s@a@_@EcAZSV]t@Mv@Wl@Yp@_@Vm@No@B{Bd@wC|@mA`@_Ak@OAaDcBsFuBgJkDaCcAcEwAeFgAqYkFk[}CkQsBiBU_@McEgBqEmBsAiA_A{AwCqF_AiB}BeCm@k@QEiBeBsBwBuAkBaBgDY]aDsA{J{HyD{CC]C[q@{HByACu@AiGMeAC]r@AX@Eg@E_@wAcA_Ay@oFgFoA_Ak@Y}ASLqHHyCtBeB{AuDgAeCeCmE</points>\n" +
"  </overview_polyline>\n" +
"  <bounds>\n" +
"   <southwest>\n" +
"    <lat>46.7064798</lat>\n" +
"    <lng>15.4074965</lng>\n" +
"   </southwest>\n" +
"   <northeast>\n" +
"    <lat>47.0703735</lat>\n" +
"    <lng>15.7699030</lng>\n" +
"   </northeast>\n" +
"  </bounds>\n" +
" </route>\n" +
" <geocoded_waypoint>\n" +
"  <geocoder_status>OK</geocoder_status>\n" +
"  <type>locality</type>\n" +
"  <type>political</type>\n" +
"  <place_id>ChIJKeQFLpwLb0cRUTBd7aXrqKY</place_id>\n" +
" </geocoded_waypoint>\n" +
" <geocoded_waypoint>\n" +
"  <geocoder_status>OK</geocoder_status>\n" +
"  <type>locality</type>\n" +
"  <type>political</type>\n" +
"  <place_id>ChIJu2UwF4c1bkcRm93f0tGKjv4</place_id>\n" +
" </geocoded_waypoint>\n" +
"</DirectionsResponse>");
//                "<GeocodeResponse>\n"
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
//        
        LinkedList<Leg> list = new LinkedList<>();
        list = xml.xmlFromDistanceAPItoLocations();
        
        for(Leg l : list)
        {
            System.out.println(l.toString());
        }
        
        
        //Location l = xml.xmlToLocation();
        
        
        //  Test Elevation Information
//        XMLParse xml = new XMLParse("https://roads.googleapis.com/v1/snapToRoads?path=46.72281265258789,15.648141860961914|46.99358367919922,15.411750793457031|47.02824401855469,15.413273811340332|47.032527923583984,15.412571907043457|47.03444290161133,15.411592483520508|47.06233596801758,15.423318862915039|47.06652069091797,15.427214622497559|47.066871643066406,15.43134593963623|47.06647872924805,15.431346893310547|47.06653594970703,15.43171215057373|47.06958770751953,15.434045791625977|47.06946563720703,15.436347961425781|47.068878173828125,15.43686294555664|47.06978225708008,15.438536643981934|47.07022476196289,15.438182830810547&interpolate=true&key=AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo");
//
//        
//        
//        LinkedList<Location> list = xml.xmlFromRoadsAPI();
//        for (Location l : list)
//        {
//            System.out.println(l.toString());
//        }

    }
}