/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Veronika
 * 
 * Klass um das Höhendiagramm klein zu zeichnen
 * Extends JPanel damit gleich direkt gezeichnet werden kann
 */
public class GraphingData_small extends JPanel
{

    private LinkedList<Double> daten;
   
    public LinkedList<Double> getDaten() {
        return daten;
    }

    public void setDaten(LinkedList<Double> daten) {
        this.daten = daten;
    }

    
    final int PAD = 20;

    protected void paintComponent(Graphics g)
    {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        // aussortieren
        daten = sotiereAus();
        // Höhe und Breite des Panels ermitteln
        int w = getWidth();
        int h = getHeight();
        // Zeichnen der Y.Achse        
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        // Zeichnen der X-Achse
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Beschriftungen zeichnen
        String s = "Höhe";
        float sy = PAD + ((h - 2 * PAD) - s.length() * sh) / 2 + lm.getAscent();
        for (int i = 0; i < s.length(); i++)
        {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float) font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw) / 2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
       
        s = "Ort";
        sy = h - PAD + (PAD - sh) / 2 + lm.getAscent();
        float sw = (float) font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw) / 2;
        g2.drawString(s, sx, sy);
        // Linie zeichnen
        double xInc = (double) (w - 2 * PAD) / (daten.size() - 1);
        double scale = (double) (h - 2 * PAD) / getMax();
        g2.setPaint(Color.RED.brighter());
        for (int i = 0; i < daten.size() - 1; i++)
        {
            double x1 = PAD + i * xInc;
            double y1 = h - PAD - scale * daten.get(i);
            double x2 = PAD + (i + 1) * xInc;
            double y2 = h - PAD - scale * daten.get(i+1);
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // Das maximum der Daten wird herausgesucht um die Höhe des Diagramms zu ermitteln
    private double getMax()
    {
        double max = -Integer.MAX_VALUE;
        for (int i = 0; i < daten.size(); i++)
        {
            if (daten.get(i) > max)
            {
                max = daten.get(i);
            }
        }
        return max;
    }

    private LinkedList<Double> sotiereAus() {
        LinkedList<Double> newlist = new LinkedList<Double>();
        for(double d : daten)
        {
            if(d!=0)
            {
                newlist.add(d);
            }
        }
        return newlist;
    }
}
