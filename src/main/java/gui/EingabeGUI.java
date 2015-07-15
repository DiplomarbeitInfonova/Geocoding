/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Location;
import bl.GeocodingAPI;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import static org.jxmapviewer.JXMapKit.DefaultProviders.OpenStreetMaps;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author Veronika
 */
public class EingabeGUI extends javax.swing.JFrame
{

    /**
     * Creates new form EingabeGUI
     */
    private GeocodingAPI geo;
    private Location a;
    private Location b;
    private LinkedList<Location> locations = new LinkedList<>();

    public EingabeGUI()
    {
        initComponents();
        this.setLocationRelativeTo(null);
        geo = new GeocodingAPI();
        this.rb_2D.setSelected(true);
        this.MainMap.setDefaultProvider(OpenStreetMaps);
        MainMap.setAddressLocation(new GeoPosition(47.066667, 15.433333));
        ButtonGroup rbgroup = new ButtonGroup();
        rbgroup.add(rb_2D);
        rbgroup.add(rb_3D);
    }

    public void addWaypoint(LinkedList<Location> locations)
    {
        //Ein Set von Waypoints wird erstellt und die Locations werden eingefügt
        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        for (Location l : locations)
        {
            waypoints.add(new DefaultWaypoint(new GeoPosition(l.getxKoord(), l.getyKoord())));
        }

        //Ein Waypointpainter wird erstellt um die Punkte an der Karte anzuzeigen
        WaypointPainter painter = new WaypointPainter();
        painter.setWaypoints(waypoints);
        MainMap.getMainMap().setOverlayPainter(painter);
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
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
        panhoehe = new javax.swing.JPanel();
        panMap = new javax.swing.JPanel();
        MainMap = new org.jxmapviewer.JXMapKit();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_Start = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("Nach Hier");
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 900));

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

        panInfos.setLayout(new java.awt.GridLayout(3, 1));

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        rb_2D.setText("2D");
        jPanel10.add(rb_2D);

        rb_3D.setText("3D");
        jPanel10.add(rb_3D);

        panInfos.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(1, 2));

        jLabel7.setText("Distanz");
        jPanel11.add(jLabel7);

        lab_Distance.setText("0000");
        jPanel11.add(lab_Distance);

        panInfos.add(jPanel11);

        jPanel12.setLayout(new java.awt.GridLayout(1, 2));

        jLabel9.setText("Dauer");
        jPanel12.add(jLabel9);

        lab_Duration.setText("0h 00min");
        jPanel12.add(lab_Duration);

        panInfos.add(jPanel12);

        panControls.add(panInfos);
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

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     Es wird von den Textfeldern der Input geholt und in Locations umgewandelt
     danach wird von den Locations die Distanz und die Dauer einer Fahrt berechnet.
     Wenn ein Ortsname eingegeben wurde, werden die Textfelder nun mit den Koordinaten befüllt
     und vice versa.
     */
    private void mi_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_StartActionPerformed
        // Prüfen
        if (!this.tf_OrtsnameA.getText().equals(""))
        {
            a = geo.OrtToKoord(this.tf_OrtsnameA.getText());
            this.tf_XKoordA.setText(a.getxKoord() + "");
            this.tf_YKoordA.setText(a.getyKoord() + "");
        } else if (!this.tf_XKoordA.getText().equals("") && !this.tf_YKoordA.getText().equals(""))
        {
            String xS = this.tf_XKoordA.getText();
            String yS = this.tf_YKoordA.getText();
            double x = Double.parseDouble(xS);
            double y = Double.parseDouble(yS);
            double[] dfeld =
            {
                x, y
            };
            a = geo.KoordToOrt(dfeld);
            this.tf_OrtsnameA.setText(a.getName());
        } else
        {
            JOptionPane.showMessageDialog(this, "Bitte Ort A angeben!");
            return;
        }
        if (!this.tf_OrtsnameB.getText().equals(""))
        {
            b = geo.OrtToKoord(this.tf_OrtsnameB.getText());
            this.tf_XKoordB.setText(b.getxKoord() + "");
            this.tf_YKoordB.setText(b.getyKoord() + "");
        } else if (!this.tf_XKoordB.getText().equals("") && !this.tf_YKoordB.getText().equals(""))
        {
            String xS = this.tf_XKoordB.getText();
            double x = Double.parseDouble(xS);
            String yS = this.tf_YKoordB.getText();
            double y = Double.parseDouble(yS);
            double[] dfeld =
            {
                x, y
            };
            b = geo.KoordToOrt(dfeld);
            this.tf_OrtsnameB.setText(b.getName());
        } else
        {
            JOptionPane.showMessageDialog(this, "Bitte Ort B angeben!");
            return;
        }
        String dur = geo.LocationToDistance(a, b);
        String[] spl = dur.split("-");
        this.lab_Distance.setText(spl[1]);
        this.lab_Duration.setText(spl[0]);
        locations.clear();
        locations.add(a);
        locations.add(b);
        this.addWaypoint(locations);
    }//GEN-LAST:event_mi_StartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new EingabeGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jxmapviewer.JXMapKit MainMap;
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
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lab_Distance;
    private javax.swing.JLabel lab_Duration;
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

}
