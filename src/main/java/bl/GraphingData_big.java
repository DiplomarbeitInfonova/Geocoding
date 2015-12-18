/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
        // Höhe und Breite des Panels ermitteln
        int w = getWidth();
        int h = getHeight();
        // Zeichnen der Y.Achse        
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Zeichnen der X-Achse
        System.out.println(w + " " + h);
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        // Beschriftung der Achsen

        g2.drawString("Höhe", PAD * 3 / 4, PAD * 3 / 4);
        g2.drawString("Ort", w - PAD + (PAD * 1 / 4), h - PAD + 5);

        // Zeichnen der Einheiten
        // Zeichnen des Abschlusses der y-Achse
        g2.draw( new Line2D.Double(PAD-5, (PAD), PAD+5, (PAD)));
        // y/(getMax()/100)
        // z ist der Abstand zwischen den hunderter Schritten auf der y-Ache
        int z = (h - (PAD * 2)) / ((int) getMax() / 100);
        for (int i = 0; i < getMax()-1 / 100; i++) {
            
            g2.draw(new Line2D.Double(PAD - 5, (h-PAD) - (z*i), PAD + 5, (h-PAD) - (z*i)));
        }
        
        
        System.out.println(z);
        // Linie zeichnen
        double xInc = (double) (w - 2 * PAD) / (hoehen.size() - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.RED.brighter());
        
        for (int i = 0; i < hoehen.size() - 1; i++) {
            // Sollte keine höhe zurückgegeben worden sein, wird die Höhe des vorherigen Punkes verwendet
            double hoehe = hoehen.get(i).getHoehe();
            if (hoehen.get(i).getHoehe() == 0) {
                hoehe = hoehen.get(i - 1).getHoehe();
            }
            
            float x1 = (float) (PAD + i * xInc);
            float y1 = (float) (h - PAD - scale * hoehen.get(i).getHoehe());
            
            g2.drawString(hoehen.get(i).getHoehe()+"", x1, (y1+10));
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * hoehen.get(i + 1).getHoehe();
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // Das maximum der Daten wird herausgesucht um die Höhe des Diagramms zu ermitteln
    private double getMax() {
        double max = -Integer.MAX_VALUE;
        for (int i = 0; i < hoehen.size(); i++) {
            if (hoehen.get(i).getHoehe() > max) {
                max = hoehen.get(i).getHoehe();
            }
        }
        return max;
    }
}
