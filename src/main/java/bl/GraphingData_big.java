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
        // HÃ¶he und Breite des Panels ermitteln
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
        g2.draw(new Line2D.Double(PAD - 5, (PAD), PAD + 5, (PAD)));
        // y/(getMax()/100)
        // z ist der Abstand zwischen den hunderter Schritten auf der y-Ache
        // Zeichnen der Einheiten auf der y-Achse
        int z = (h - (PAD * 2)) / ((int) getMax() / 100);
        for (int i = 0; i < getMax() - 1 / 100; i++) {

            g2.draw(new Line2D.Double(PAD - 5, (h - PAD) - (z * i), PAD + 5, (h - PAD) - (z * i)));
            g2.drawString(i * 100 + "", PAD / 2, (h - PAD) - (z * i) + 5);
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
            if (i % 8 == 0) {
                g2.setPaint(Color.BLACK);
                // Jeder 5. Punkt wird beschriftet
                g2.drawString(hoehen.get(i).getHoehe() + "", x1 - 15, (y1 + 10));
                // Bei jedem 5. Punkt wird eine Markierung bei der x-Achse gesetzt
                g2.draw(new Line2D.Double(x1, h - PAD + 5, x1, h - PAD - 5));
                g2.drawString(hoehen.get(i).getName(), x1-10, h-PAD+20);
                g2.setPaint(Color.RED.brighter());
            }
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * hoehen.get(i + 1).getHoehe();
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // Das maximum der Daten wird herausgesucht um die HÃ¶he des Diagramms zu ermitteln
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
