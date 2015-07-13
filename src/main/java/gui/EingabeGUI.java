/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Location;
import bl.GeocodingAPI;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import org.jxmapviewer.*;
import static org.jxmapviewer.JXMapKit.DefaultProviders.OpenStreetMaps;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author Veronika
 */
public class EingabeGUI extends javax.swing.JFrame {

    /**
     * Creates new form EingabeGUI
     */
    private GeocodingAPI geo;
    private Location A;
    private Location B;
    public EingabeGUI() 
    {
        initComponents();
        geo = new GeocodingAPI();
        this.rb_2D.setSelected(true);
        this.MainMap.setDefaultProvider(OpenStreetMaps);
        MainMap.setAddressLocation(new GeoPosition(47.066667, 15.433333));
        
            
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tf_OrtsnameA = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_XKoordA = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_YKoordA = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tf_OrtsnameB = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tf_XKoordB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_YKoordB = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        MainMap = new org.jxmapviewer.JXMapKit();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        rb_2D = new javax.swing.JRadioButton();
        rb_3D = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lab_Distance = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lab_Duration = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mi_Start = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ort A"));
        jPanel4.setLayout(new java.awt.GridLayout(2, 2));

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Ortsname");
        jPanel7.add(jLabel1);
        jPanel7.add(tf_OrtsnameA);

        jPanel4.add(jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Koordinaten"));
        jPanel8.setLayout(new java.awt.GridLayout(2, 2));

        jLabel2.setText("X");
        jPanel8.add(jLabel2);
        jPanel8.add(tf_XKoordA);

        jLabel3.setText("Y");
        jPanel8.add(jLabel3);
        jPanel8.add(tf_YKoordA);

        jPanel4.add(jPanel8);

        jPanel1.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Ort B"));
        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setText("Ortsname");
        jPanel6.add(jLabel4);
        jPanel6.add(tf_OrtsnameB);

        jPanel5.add(jPanel6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Koordinaten"));
        jPanel9.setLayout(new java.awt.GridLayout(2, 2));

        jLabel5.setText("X");
        jPanel9.add(jLabel5);
        jPanel9.add(tf_XKoordB);

        jLabel6.setText("Y");
        jPanel9.add(jLabel6);
        jPanel9.add(tf_YKoordB);

        jPanel5.add(jPanel9);

        jPanel1.add(jPanel5);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(MainMap, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(1, 3));

        jPanel10.setLayout(new java.awt.GridLayout(2, 1));

        rb_2D.setText("2D");
        jPanel10.add(rb_2D);

        rb_3D.setText("3D");
        jPanel10.add(rb_3D);

        jPanel3.add(jPanel10);

        jPanel11.setLayout(new java.awt.GridLayout(1, 2));

        jLabel7.setText("Distanz");
        jPanel11.add(jLabel7);

        lab_Distance.setText("0000");
        jPanel11.add(lab_Distance);

        jPanel3.add(jPanel11);

        jPanel12.setLayout(new java.awt.GridLayout(1, 2));

        jLabel9.setText("Dauer");
        jPanel12.add(jLabel9);

        lab_Duration.setText("0h 00min");
        jPanel12.add(lab_Duration);

        jPanel3.add(jPanel12);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

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
        Location a=null;
        Location b= null;
        // Prüfen
        if(!this.tf_OrtsnameA.getText().equals(""))
        {
            a=geo.OrtToKoord(this.tf_OrtsnameA.getText());
            this.tf_XKoordA.setText(a.getxKoord()+"");
            this.tf_YKoordA.setText(a.getyKoord()+"");
        }
        else if(!this.tf_XKoordA.getText().equals("")&&!this.tf_YKoordA.getText().equals(""))
        {
           
            String xS = this.tf_XKoordA.getText();
            
            String yS = this.tf_YKoordA.getText();
            double x = Double.parseDouble(xS);
            double y = Double.parseDouble(yS);
            double[] dfeld = {x,y};
            a=geo.KoordToOrt(dfeld);
            this.tf_OrtsnameA.setText(a.getName());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Bitte Ort A angeben!");
        }
        if(!this.tf_OrtsnameB.getText().equals(""))
        {
            b=geo.OrtToKoord(this.tf_OrtsnameB.getText());
            this.tf_XKoordB.setText(b.getxKoord()+"");
            this.tf_YKoordB.setText(b.getyKoord()+"");
        }
        else if(!this.tf_XKoordB.getText().equals("")&&!this.tf_YKoordB.getText().equals(""))
        {
            String xS = this.tf_XKoordB.getText();
            double x = Double.parseDouble(xS);
            String yS = this.tf_YKoordB.getText();
            double y = Double.parseDouble(yS);
            double[] dfeld = {x,y};
            b=geo.KoordToOrt(dfeld);
            this.tf_OrtsnameB.setText(b.getName());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Bitte Ort B angeben!");
        }
        
        String dur = geo.LocationToDistance(a, b);
        String[] spl = dur.split("-");
        this.lab_Distance.setText(spl[1]);
        this.lab_Duration.setText(spl[0]);
//        Set<Waypoint> waypoints = new HashSet<Waypoint>();
//    waypoints.add(new Waypoint(41.881944,-87.627778);
//    waypoints.add(new Waypoint(40.716667,-74));
//    
//    //crate a WaypointPainter to draw the points
//    WaypointPainter painter = new WaypointPainter();
//    painter.setWaypoints(waypoints);
//    MainMap.getMainMap().setOverlayPainter(painter);
       
         this.MainMap.setAddressLocation(new GeoPosition(a.getxKoord(),a.getyKoord()));
    }//GEN-LAST:event_mi_StartActionPerformed

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
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EingabeGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private org.jxmapviewer.JXMapKit MainMap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lab_Distance;
    private javax.swing.JLabel lab_Duration;
    private javax.swing.JMenuItem mi_Start;
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
