/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Veronika
 */
public class GraphingData_big extends JPanel{
    private LinkedList<Location> hoehen;

    public LinkedList<Location> getHoehen() {
        return hoehen;
    }

    public void setHoehen(LinkedList<Location> hoehen) {
        this.hoehen = hoehen;
    }

   
  
    protected void paintComponent(Graphics g) {
                super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        // Höhe und Breite des Panels ermitteln
        int w = getWidth();
        int h = getHeight();
        for(Location l : hoehen)
        {
            System.out.println(l.getHoehe());   
        }
    }
    
}
