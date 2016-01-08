/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Leg;
import beans.Location;
import bl.GeocodingAPI;
import bl.GraphingData_small;
import bl.SnapToRoadsAPI;
import dal.CSVHandler;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static org.jxmapviewer.JXMapKit.DefaultProviders.OpenStreetMaps;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author Veronika, Dominik
 */
public class EingabeGUI extends javax.swing.JFrame {

    /**
     * Creates new form EingabeGUI
     */
    public GeocodingAPI geo;
    private Location startloc;
    private Location zielloc;
    private LinkedList<Location> locations = new LinkedList<>();
    private LinkedList<Leg> legs = new LinkedList<>();
    private CSVHandler csvhandler;

    public EingabeGUI() {
        initComponents();
        this.setLocationRelativeTo(null);
        geo = new GeocodingAPI();
        this.rb_2D.setSelected(true);
        this.MainMap.setDefaultProvider(OpenStreetMaps);
        csvhandler = new CSVHandler();
        MainMap.setAddressLocation(new GeoPosition(47.066667, 15.433333));
        ButtonGroup rbgroup = new ButtonGroup();
        rbgroup.add(rb_2D);
        rbgroup.add(rb_3D);
        EingabeGUI.updateStatus("Warten auf Eingabe..");
    }

    /**
     * Diese Methode zeichnet in die Karte alle Locations ein, welche in der
     * List, die übergeben wird, vorhanden sind. Übergibt man diese Liste an die
     * Klasse WaypointPainter, übernimmt sie das Zeichnen auf der Map.
     *
     * @param locations
     */
    public void addWaypoint(LinkedList<Location> locations) {
        //Author Dominik
        //Ein Set von Waypoints wird erstellt und die Locations werden eingefügt
        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        int i = 0;
        for (Location l : locations) {
            if (l != null) {
                EingabeGUI.updateStatus("Zeichne Wegpunkt " + i + " von " + locations.size());
                waypoints.add(new DefaultWaypoint(new GeoPosition(l.getxKoord(), l.getyKoord())));
                i++;
            }

        }

        //Ein Waypointpainter wird erstellt um die Punkte an der Karte anzuzeigen
        WaypointPainter painter = new WaypointPainter();
        //painter.setWaypoints(new HashSet<Waypoint>());
        //repaint();
        painter.clearCache();
        painter.setWaypoints(waypoints);

        MainMap.getMainMap().setOverlayPainter(painter);
        repaint();
        EingabeGUI.updateStatus("Zeichnen der Wegpunkte abgeschlossen");
    }

    /**
     * Diese Methode zeichnet die übergebenen Locations auf der Karte (JXMapKit)
     * ein. Hierzu werden Koordinaten in Pixelpunkte umgerechnet.
     *
     * @param locations Die LinkedList, die alle Punkte des Weges enthält
     *
     */
    public void paintRoute(LinkedList<Location> locations) {
        //Autor Dominik
        EingabeGUI.updateStatus("Start des Zeichnens der Route");
        final List<GeoPosition> region = new ArrayList<>();

        for (Location location : locations) {
            region.add(new GeoPosition(location.getxKoord(), location.getyKoord()));
        }

        Painter<JXMapViewer> lineOverlay = new Painter<JXMapViewer>() {

            @Override
            public void paint(Graphics2D g, JXMapViewer object, int width, int height) {
                g = (Graphics2D) g.create();
                //convert from viewport to world bitmap
                Rectangle rect = EingabeGUI.MainMap.getMainMap().getViewportBounds();
                g.translate(-rect.x, -rect.y);

                //do the drawing
                g.setColor(Color.RED);
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2));

                int lastX = -1;
                int lastY = -1;
                int i = 0;
                for (GeoPosition gp : region) {
                    //Koordinaten zu Pixeln umrechnen

                    Point2D pt = EingabeGUI.MainMap.getMainMap().getTileFactory().geoToPixel(gp, EingabeGUI.MainMap.getMainMap().getZoom());
                    if (lastX != -1 && lastY != -1) {

                        g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                        EingabeGUI.updateStatus("Zeichne Routenpunkt " + i + " von " + region.size());

                        i++;
                    }

                    lastX = (int) pt.getX();
                    lastY = (int) pt.getY();

                }
                EingabeGUI.updateStatus("Zeichnen abgeschlossen");
            }
        };
        MainMap.getMainMap().setOverlayPainter(lineOverlay);
        EingabeGUI.updateStatus("Zeichnen der Route abgeschlossen");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panControls = new javax.swing.JPanel();
        panA = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tf_OrtsnameA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_XKoordA = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_YKoordA = new javax.swing.JTextField();
        panB = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tf_OrtsnameB = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tf_XKoordB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_YKoordB = new javax.swing.JTextField();
        panInfos = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        rb_2D = new javax.swing.JRadioButton();
        rb_3D = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lab_Distance = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lab_Duration = new javax.swing.JLabel();
        labstatus = new javax.swing.JLabel();
        panhoehe = new javax.swing.JPanel();
        lab_bitteklicken = new javax.swing.JLabel();
        panMap = new javax.swing.JPanel();
        MainMap = new org.jxmapviewer.JXMapKit();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_Start = new javax.swing.JMenuItem();
        mi_Neu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        miDataImport = new javax.swing.JMenuItem();
        miDataExport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1600, 1000));
        setPreferredSize(new java.awt.Dimension(304, 200));

        panControls.setMinimumSize(new java.awt.Dimension(300, 298));
        panControls.setPreferredSize(new java.awt.Dimension(300, 100));
        panControls.setLayout(new java.awt.GridLayout(4, 1));

        panA.setBorder(javax.swing.BorderFactory.createTitledBorder("Ort A"));
        panA.setLayout(new java.awt.GridLayout(2, 2));

        jPanel7.setLayout(new java.awt.GridLayout(2, 2));

        jLabel1.setText("Ortsname");
        jLabel1.setPreferredSize(new java.awt.Dimension(47, 7));
        jPanel7.add(jLabel1);

        tf_OrtsnameA.setPreferredSize(new java.awt.Dimension(6, 10));
        jPanel7.add(tf_OrtsnameA);
        jPanel7.add(jLabel8);

        panA.add(jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Koordinaten"));
        jPanel8.setLayout(new java.awt.GridLayout(2, 2));

        jLabel2.setText("X");
        jPanel8.add(jLabel2);
        jPanel8.add(tf_XKoordA);

        jLabel3.setText("Y");
        jPanel8.add(jLabel3);
        jPanel8.add(tf_YKoordA);

        panA.add(jPanel8);

        panControls.add(panA);

        panB.setBorder(javax.swing.BorderFactory.createTitledBorder("Ort B"));
        panB.setLayout(new java.awt.GridLayout(2, 1));

        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        jLabel4.setText("Ortsname");
        jPanel6.add(jLabel4);
        jPanel6.add(tf_OrtsnameB);
        jPanel6.add(jLabel10);

        panB.add(jPanel6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Koordinaten"));
        jPanel9.setLayout(new java.awt.GridLayout(2, 2));

        jLabel5.setText("X");
        jPanel9.add(jLabel5);
        jPanel9.add(tf_XKoordB);

        jLabel6.setText("Y");
        jPanel9.add(jLabel6);
        jPanel9.add(tf_YKoordB);

        panB.add(jPanel9);

        panControls.add(panB);

        panInfos.setLayout(new java.awt.GridLayout(4, 1));

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        rb_2D.setText("2D");
        jPanel10.add(rb_2D);

        rb_3D.setText("3D");
        jPanel10.add(rb_3D);

        panInfos.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(1, 2));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Distanz:");
        jPanel11.add(jLabel7);
        jPanel11.add(lab_Distance);

        panInfos.add(jPanel11);

        jPanel12.setLayout(new java.awt.GridLayout(1, 2));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Dauer:");
        jPanel12.add(jLabel9);
        jPanel12.add(lab_Duration);

        panInfos.add(jPanel12);
        panInfos.add(labstatus);

        panControls.add(panInfos);

        panhoehe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panhoeheMouseClicked(evt);
            }
        });
        panhoehe.setLayout(new java.awt.BorderLayout());
        panhoehe.add(lab_bitteklicken, java.awt.BorderLayout.PAGE_START);

        panControls.add(panhoehe);

        getContentPane().add(panControls, java.awt.BorderLayout.WEST);

        panMap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panMap.setLayout(new java.awt.BorderLayout());
        panMap.add(MainMap, java.awt.BorderLayout.CENTER);

        getContentPane().add(panMap, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        mi_Start.setText("Start");
        mi_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_StartActionPerformed(evt);
            }
        });
        jMenu1.add(mi_Start);

        mi_Neu.setText("Neue Abfrage");
        mi_Neu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_NeuActionPerformed(evt);
            }
        });
        jMenu1.add(mi_Neu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Daten");

        miDataImport.setText("Daten importieren");
        miDataImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDataImportActionPerformed(evt);
            }
        });
        jMenu2.add(miDataImport);

        miDataExport.setText("Daten exportieren");
        miDataExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDataExportActionPerformed(evt);
            }
        });
        jMenu2.add(miDataExport);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     *
     * Author: Dominik,Veronika Es wird von den Textfeldern der Input geholt und
     * in Locations umgewandelt danach wird von den Locations die Distanz und
     * die Dauer einer Fahrt berechnet. Wenn ein Ortsname eingegeben wurde,
     * werden die Textfelder nun mit den Koordinaten befüllt und vice versa.
     * Danach wird die Fahrtdauer in Stunden und die Distanz der Strecke in km
     * von der Klasse GeocodingAPI abgefragt und in das Label geschrieben.
     * Danach werden von der GeocodingAPI die Zwischenpunkte der Strecke mit
     * Übergabe der beiden Locations (a,b) abgefragt. Danach werden die
     * Locations um die Waypoints der RoadsAPI erweitert: D.h. es werden
     * zwischen den bereits bestehenden Punkten viele Zwischenpunkte
     * eingezeichnet, die das Zeichnen des Straßenverlaufs später erleichtern
     * sollen. Der Aufruf der Klasse SnapToRoadsAPI soll die bestehende Route an
     * den Straßenverlauf angleichen. ---KURZE SNAPTOROADS ERKLÄRUNG---
     *
     * Anschließend wird das Höhendiagramm gezeichnet. ---HÖHENDIAGRAMM ---
     *
     * Zum Schluss erfolgt der Aufruf der Methode paintRoute mit der Liste der
     * Locations als Übergabeparameter. Hier wird dann die Strecke auf der Karte
     * eingezeichnet.
     *
     *
     * @param evt
     */
    private void mi_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_StartActionPerformed
//Author Dominik, Veronika
        EingabeGUI.labstatus.setText("Verarbeite Eingaben");
        
        try {
            
            if (this.getLocationsfromTextfields()) {
                this.fillTextfields();
                EingabeGUI.updateStatus("Starten der Abfrage an Google");
                String[] durationarray = geo.LocationToDistance(startloc, zielloc);

                this.lab_Distance.setText(durationarray[1]);
                this.lab_Duration.setText(durationarray[0]);
                //locations = geo.getWaypoints(a.getName(), b.getName());
                // ~Patrizia
                //locations = geo.getWaypoints(startloc.getName(), zielloc.getName());
                
                legs = geo.getWaypoints(startloc.getName(), zielloc.getName());
                // ~Patrizia
                /*
                * Dieser Abschnitt ist für das Aufrufen des Polyline-Algos
                */
                legs = geo.getWaypoints(startloc.getName(), zielloc.getName());
                
                for(Leg leg:legs)
                {
                    System.out.println(leg.toString());
                }

                LinkedList<Location> helplocation = new LinkedList<>();
                for (int l = 0; l < legs.size(); l++) {
                    if (legs.get(l).getPolyline() != null || legs.get(l).getPolyline() != "") 
                    {
                        System.out.println("Polyline: "+legs.get(l).getPolyline());
                        helplocation = decodePoly(legs.get(l).getPolyline(),7);
                        //helplocation = decode(legs.get(l).getPolyline(),7,true);
                        
                        for (int i = 0; i < helplocation.size(); i++) {
                            //System.out.println(helplocation.get(i).getxKoord()+" "+helplocation.get(i).getyKoord());
                            locations.add(helplocation.get(i));
                        }
                    }
                }
                System.out.println("Location-Size: " + locations.size());
                
                /*
                locations = geo.getWaypointsMitRoadsAPI(locations);
                //System.out.println("Länge der Liste: " + locations.size());
                locations = geo.loescheDoppelteWerte(locations);
                //System.out.println("Länge der Liste nach Löschen: " + locations.size());
                SnapToRoadsAPI snap = new SnapToRoadsAPI(locations);
*/
//            GeoApiContext apicontext = new GeoApiContext();
//            apicontext.setApiKey(geo.apiKey);
//            apicontext.setQueryRateLimit(100,0);
//
//            List<SnappedPoint> snappedList = snap.snapToRoads(new GeoApiContext().setApiKey(geo.apiKey));
//            LinkedList<Location> list = snap.convertFromLatLngToLocation(snappedList);
//            Map<String, SpeedLimit> speedlimitMap = snap.getSpeedLimits(apicontext, snappedList);
//
//            for (String key : speedlimitMap.keySet()) {
//                System.out.print("Key: " + key + " - ");
//                System.out.print("Value: " + speedlimitMap.get(key) + "\n");
//            }
                //locations.add(a);
                //locations.add(b);
                //this.addWaypoint(locations);
                // Ein Höhendiagramm wird erstellt und in das Panel eingebunden.
                // ~Veronika
                GraphingData_small diagramm = new GraphingData_small();
                LinkedList<Double> hoehen = this.locationsToDoubleList();
                diagramm.setDaten(hoehen);
                this.panhoehe.add(diagramm, BorderLayout.CENTER);
                this.lab_bitteklicken.setText("Für mehr Informationen bitte hier klicken");
                panhoehe.repaint();
                System.out.println(locations.size());
                paintRoute(locations);
                EingabeGUI.updateStatus("Zeichnen abgeschlossen");
                this.printCounters();
//            this.addWaypoint(list);
            }
        } catch (Exception ex) {
            Logger.getLogger(EingabeGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_mi_StartActionPerformed

    public void printCounters(){
        System.out.println("Counter- Statistics\n---\n"
                + "GeocodingAPI: "+GeocodingAPI.geocodingcounter+"\n"
                + "DirectionsAPI: "+GeocodingAPI.directionscounter+"\n"
                + "ElevationAPI: "+GeocodingAPI.elevationcounter+"\n"
                + "DistanceMatrixAPI: "+GeocodingAPI.distancematrixcounter+"\n---");
    }
    
    /**
     * Author Dominik Diese Methode befüllt das Feld zur Statuseingabe in der
     * GUI mit der übergebenen Meldung.
     *
     * @param status
     */
    public static void updateStatus(String status) {
        EingabeGUI.labstatus.setText(status);
        EingabeGUI.labstatus.repaint();
    }
    // Author Veronika
    // Hier wird überprüft ob die Felder richtg ausgefüllt worden sind
    // Und danach wird die Location von google abgefragt

    private boolean getLocationsfromTextfields() {
       boolean correctlyfilled=true;
        if (!this.tf_OrtsnameA.getText().equals("")) {
            if (startloc == null) {
                startloc = geo.OrtToKoord(this.tf_OrtsnameA.getText());
            }

        } else if (!this.tf_XKoordA.getText().isEmpty() && !this.tf_YKoordA.getText().isEmpty()) {
            String xS = this.tf_XKoordA.getText();
            String yS = this.tf_YKoordA.getText();
            double x = Double.parseDouble(xS);
            double y = Double.parseDouble(yS);
            double[] dfeld
                    = {
                        x, y
                    };
            startloc = geo.KoordToOrt(dfeld);
        } else {
            JOptionPane.showMessageDialog(this, "Bitte Ort A angeben!");
            correctlyfilled = false;
        }

        if (!this.tf_OrtsnameB.getText().equals("")) {
            if (zielloc == null) {
                zielloc = geo.OrtToKoord(this.tf_OrtsnameB.getText());
            }

        } else if (!this.tf_XKoordB.getText().equals("") && !this.tf_YKoordB.getText().equals("")) {
            String xS = this.tf_XKoordB.getText();
            double x = Double.parseDouble(xS);
            String yS = this.tf_YKoordB.getText();
            double y = Double.parseDouble(yS);
            double[] dfeld
                    = {
                        x, y
                    };
            zielloc = geo.KoordToOrt(dfeld);

        } else {
            JOptionPane.showMessageDialog(this, "Bitte Ort B angeben!");
            correctlyfilled = false;
        }
        return correctlyfilled;
    }

    private void fillTextfields() {
        // Prüfen ob alle Felder richtig ausgefüllt wurden ~ Veronika

        this.tf_XKoordA.setText(startloc.getxKoord() + "");
        this.tf_YKoordA.setText(startloc.getyKoord() + "");
        this.tf_OrtsnameA.setText(startloc.getName());

        this.tf_XKoordB.setText(zielloc.getxKoord() + "");
        this.tf_YKoordB.setText(zielloc.getyKoord() + "");
        this.tf_OrtsnameB.setText(zielloc.getName());

    }


    private void panhoeheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panhoeheMouseClicked
        // Onklick event für das Höhenpanel.
        // Wurden noch keine Locations berechnen geschieht bei Klick auf das Panel nichts
        // Gibt es schon Locations bzw. Höhen wird eine zweite GUI aufgerufen die mit den Locations weiterarbeitet
        // ~Veronika
        if (locations.size() != 0) {
          
            HoehenPanel hoehenpanel = new HoehenPanel(locations);
            hoehenpanel.setVisible(true);
            this.printCounters();
        }
    }//GEN-LAST:event_panhoeheMouseClicked

    private void mi_NeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_NeuActionPerformed
        //Onklick Methode für das Menu Item NeuActionPerformed
        // Will der Benutzer eine neue Abfrage machen wird die Alte Frage verworfen und 
        // alles auf Anfangszustand zurückgestellt 
        //~Veronika
        initComponents();
        this.setLocationRelativeTo(null);
        geo = new GeocodingAPI();
        this.rb_2D.setSelected(true);
        this.MainMap.setDefaultProvider(OpenStreetMaps);
        MainMap.setAddressLocation(new GeoPosition(47.066667, 15.433333));
        ButtonGroup rbgroup = new ButtonGroup();
        rbgroup.add(rb_2D);
        rbgroup.add(rb_3D);
        locations = new LinkedList<>();
        this.tf_OrtsnameA.setText("");
        this.tf_OrtsnameB.setText("");
        this.tf_XKoordA.setText("");
        this.tf_XKoordB.setText("");
        this.tf_YKoordA.setText("");
        this.tf_YKoordB.setText("");
        this.lab_Distance.setText("");
        this.lab_Duration.setText("");

    }//GEN-LAST:event_mi_NeuActionPerformed
    /**
     * Author: Dominik Beim Klick auf den Menüpunkt Daten -> von Datei einlesen
     * öffnet sich ein FileChooser, in dem man die .CSV- Datei auswählt, die die
     * zu importierenden Koordinaten enthält. Erfolgt der Import problemlos,
     * wird angezeigt wie viele Locations importiert wurden, danach kann man
     * auswählen ob man nun eine Route zeichnen, Waypoints setzen oder den
     * Vorgang abbrechen will. Die entsprechende Aktion wird danach
     * durchgeführt.
     *
     * @param evt
     */
    private void miDataImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDataImportActionPerformed
        //Dominik
        EingabeGUI.updateStatus("Daten werden importiert");
        Object[] optionen1 = {"Koordinatenpaar [X;Y]", "Location [Name;X;Y;Höhe]", "Abbrechen"};
        final int format = JOptionPane.showOptionDialog(null,
                "In welchem Format sind die Locations abgespeichert?",
                "Quellformat auswählen",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                optionen1,
                optionen1[0]);

        locations = this.csvhandler.importLocsfromCSV(format);

        EingabeGUI.updateStatus("Daten wurden imporiert");
        JOptionPane.showMessageDialog(null, locations.size() + " Locations wurden erfolgreich importiert");
        //, new Object[]{"Route zeichen","Waypoint- Marker setzen","Abbrechen"}
        Object[] optionen2 = {"Route zeichen", "Waypoint- Marker setzen", "Abbrechen"};
        int todo = JOptionPane.showOptionDialog(null,
                "Welche Aktion soll durchgeführt werden?",
                "Aktion auswählen",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                optionen2,
                optionen2[0]);

        this.startloc = locations.getFirst();
        this.zielloc = locations.getLast();
        this.fillTextfields();
        EingabeGUI.updateStatus("Datenimport abgeschlossen");
        GraphingData_small diagramm = new GraphingData_small();
                LinkedList<Double> hoehen = this.locationsToDoubleList();
                diagramm.setDaten(hoehen);
                this.panhoehe.add(diagramm, BorderLayout.CENTER);
                this.lab_bitteklicken.setText("Für mehr Informationen bitte hier klicken");
                panhoehe.repaint();
        switch (todo) {
            case 0:

                this.paintRoute(locations);
                break;
            case 1:

                this.addWaypoint(locations);
                break;
            case 2:
                break;
            default:
                break;
        }

    }//GEN-LAST:event_miDataImportActionPerformed
    /**
     * //Author Dominik Diese Funktion exportiert die vorhandenen Locations.
     * Anfangs kann man wiederum wählen, in welchem Format man die Daten
     * speichern will (Nur Koordinatenpaar x;y oder als Location mit
     * Namen;x;y;höhe). Danach kann man mittels FileChooser aussuchen, wo man
     * die Daten speichern will.
     *
     * @param evt
     */
    private void miDataExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDataExportActionPerformed
        //Dominik
        EingabeGUI.updateStatus("Daten werden exportiert");
        Object[] optionen1 = {"Koordinatenpaar [X;Y]", "Location [Name;X;Y;Höhe]", "Abbrechen"};
        final int format = JOptionPane.showOptionDialog(null,
                "In welchem Format sollen die Locations abgespeichert werden?",
                "Zielformat wählen",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                optionen1,
                optionen1[0]);

        this.csvhandler.exportLocstoCSV(format, locations);
        EingabeGUI.updateStatus("Datenexport abgeschlossen");
    }//GEN-LAST:event_miDataExportActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EingabeGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static org.jxmapviewer.JXMapKit MainMap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lab_Distance;
    private javax.swing.JLabel lab_Duration;
    private javax.swing.JLabel lab_bitteklicken;
    public static javax.swing.JLabel labstatus;
    private javax.swing.JMenuItem miDataExport;
    private javax.swing.JMenuItem miDataImport;
    private javax.swing.JMenuItem mi_Neu;
    private javax.swing.JMenuItem mi_Start;
    private javax.swing.JPanel panA;
    private javax.swing.JPanel panB;
    private javax.swing.JPanel panControls;
    private javax.swing.JPanel panInfos;
    private javax.swing.JPanel panMap;
    private javax.swing.JPanel panhoehe;
    private javax.swing.JRadioButton rb_2D;
    private javax.swing.JRadioButton rb_3D;
    private javax.swing.JTextField tf_OrtsnameA;
    private javax.swing.JTextField tf_OrtsnameB;
    private javax.swing.JTextField tf_XKoordA;
    private javax.swing.JTextField tf_XKoordB;
    private javax.swing.JTextField tf_YKoordA;
    private javax.swing.JTextField tf_YKoordB;
    // End of variables declaration//GEN-END:variables

// Author Veronika
    /**
     * Diese Methode fügt alle Höhen der Locations- Membervariable zu einer
     * Liste von Strings zusammen. Diese wird somit verwendet um das
     * Höhendiagramm zu zeichnen.
     *
     * @return
     */
    private LinkedList<Double> locationsToDoubleList() {
        EingabeGUI.updateStatus("Höhenliste wird erstellt");
        //Aus den Locations wird eine LinkedList vom Typ Double ausgelesen um die Daten in das Höhendiagramm leichter zu verarbeiten
        // ~Veronika
        LinkedList<Double> dlist = new LinkedList<Double>();

        for (int i = 0; i < locations.size(); i++) {
            double akthoehe=locations.get(i).getHoehe();
            if(akthoehe==0){
               geo.getElevationInformation(locations.get(i));
               akthoehe=locations.get(i).getHoehe();
            }
            dlist.add(locations.get(i).getHoehe());
        }
        return dlist;
    }
    
//    private LinkedList<Location> decodePoly(String encoded) {
//
//        LinkedList<Location> poly = new LinkedList<>();
//        int index = 0, len = encoded.length();
//        int lat = 0, lng = 0;
//
//        while (index < len) {
//            int b, shift = 0, result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lat += dlat;
//
//            shift = 0;
//            result = 0;
//            do {
//                b = encoded.charAt(index++) - 63;
//                result |= (b & 0x1f) << shift;
//                shift += 5;
//            } while (b >= 0x20);
//            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
//            lng += dlng;
//            
//            double [] feld = new double[2];
//            feld[0] = (int)(((double) lat / 1E5) * 1E6);
//            feld[1] = (int)(((double) lng / 1E5) * 1E6);
//            System.out.println(feld[0]+" "+feld[1]);
//           
//            Location l = new Location("a",feld[0],feld[1],200);
//            poly.add(l);    
//        }
//        return poly;
//    }

    public static LinkedList<Location> decodePoly(String encodedString, int precision) {
        LinkedList<Location> polyline = new LinkedList<>();
        int index = 0;
        int len = encodedString.length();
        double lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            Location p = new Location("a",lat/100000, lng/100000,200);
            System.out.println(p.toString());
            polyline.add(p);
        }

        return polyline;
    }
    
    // mit Höhe
    public static LinkedList<Location> decode(String encodedString, int precision, boolean hasAltitude) {
        int index = 0;
        int len = encodedString.length();
        int lat = 0, lng = 0, alt = 0;
        LinkedList<Location> polyline = new LinkedList<>();
        	//capacity estimate: polyline size is roughly 1/3 of string length for a 5digits encoding, 1/5 for 10digits. 

        while (index < len) {
            int b, shift, result;
            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = result = 0;
            do {
                b = encodedString.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            if (hasAltitude){
                shift = result = 0;
                do {
                    b = encodedString.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dalt = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                alt += dalt;
            }
            
            Location p = new Location("a",lat*precision, lng*precision, alt/100);
            polyline.add(p);
        }
        
        //Log.d("BONUSPACK", "decode:string="+len+" points="+polyline.size());

        return polyline;
    }
    
}
