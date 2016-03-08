/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Veronika
 */
public class GraphingData_big extends JPanel {

    private LinkedList<Location> hoehen;

    public LinkedList<Location> getHoehen() {
        return hoehen;
    }

    public void setHoehen(LinkedList<Location> hoehen) {
        this.hoehen = hoehen;
    }

    final int PAD = 50;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // HÃ¶he und Breite des Panels ermitteln
        hoehen = sortiereAus();
        int w = getWidth();
        int h = getHeight();
        // Zeichnen der Y.Achse        
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Zeichnen der X-Achse

        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        // Beschriftung der Achsen

        g2.drawString("Höhe", PAD * 3 / 4, PAD * 3 / 4);
        g2.drawString("Ort", w - PAD + (PAD * 1 / 4), h - PAD + 5);

        // Zeichnen der Einheiten
        // Zeichnen des Abschlusses der y-Achse
        g2.draw(new Line2D.Double(PAD - 5, (PAD), PAD + 5, (PAD)));
        // y/(getMax()/100)
        // z ist der Abstand zwischen den hunderter Schritten auf der y-Ache
        // Zeichnen der Einheiten auf der y-Achse
        int max = (int) getMax();
        int z = (h - (PAD * 2)) / ((max / 100));
        for (int i = 0; i <= (max / 100)+1; i++) {

            g2.draw(new Line2D.Double(PAD - 5, (h - PAD) - (z * i), PAD + 5, (h - PAD) - (z * i)));
            g2.drawString(i * 100 + "", PAD / 2, (h - PAD) - (z * i) + 5);
            // Zeichnen der Strichlierten Einheitenlinie
            g2.setPaint(Color.gray);
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, 
                    BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
            g2.setStroke(dashed);
            g2.drawLine(PAD + 5, (h - PAD) - (z * i), w - PAD, (h - PAD) - (z * i));
            g2.setPaint(Color.black);
            // Zurücksetzen auf normale Linie
            g2.setStroke(new BasicStroke(1.5f));
        }
        
        // Linie zeichnen
        double xInc = (double) (w - 2 * PAD) / (hoehen.size() - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.RED.brighter());
        double lastx=0;
        
        int intervall = hoehen.size() / 10;
        for (int i = 0; i < hoehen.size() - 1; i++) {     
           
                float x1 = (float) (PAD + i * xInc);
                float y1 = (float) (h - PAD - scale * hoehen.get(i).getHoehe());
                
                if (i%intervall==0) {
                   
                    g2.setPaint(Color.BLACK);
                    // Jeder 10. Punkt wird beschriftet
                    // Bei jedem 10. Punkt wird eine Markierung bei der x-Achse gesetzt
                    g2.draw(new Line2D.Double(x1, h - PAD + 5, x1, h - PAD - 5));
                    g2.drawString(hoehen.get(i).getName(), x1 - 10, h - PAD + 20);
                    g2.setPaint(Color.RED.brighter());
                }

                double x2 = PAD + (i + 1) * xInc;
                double y2 = h - PAD - scale * hoehen.get(i + 1).getHoehe();
                g2.draw(new Line2D.Double(x1, y1, x2, y2));
                lastx = x2;   

        }
        // Der letzte Punkt soll immer Markiert werden
        g2.setColor(Color.black);
        g2.draw(new Line2D.Double(lastx, h - PAD + 5, lastx, h - PAD - 5));
        g2.drawString(hoehen.get(hoehen.size()-1).getName(), (int) (lastx - 10), h - PAD + 20);
    }

    // Das maximum der Daten wird herausgesucht um die HÃ¶he des Diagramms zu ermitteln
    private double getMax() {
        double max = 0;
        for (int i = 0; i < hoehen.size(); i++) {
            if (hoehen.get(i).getHoehe() > max) {
                max = hoehen.get(i).getHoehe();
            }
        }
        return max;
    }

    // Hier werden alle Nuller aussortiert, da bei einfachen überspringen der Höhendaten 
    // Löcher in dem Diagramm entstehen können da oft hintereinander keine Höhe kommt
    private LinkedList<Location> sortiereAus() {
        LinkedList<Location> newlist = new LinkedList<Location>();
        for(Location l:hoehen)
        {
            if(l.getHoehe()!=0)
            {
                newlist.add(l);
            }
        }
        return newlist;
    }
}
