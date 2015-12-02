/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Veronika
 */
public class GraphinData_big extends JPanel{
    private LinkedList<Location> hoehen;

    public LinkedList<Location> getHoehen() {
        return hoehen;
    }

    public void setHoehen(LinkedList<Location> hoehen) {
        this.hoehen = hoehen;
    }

    public GraphinData_big(LinkedList<Location> hoehen) {
        this.hoehen = hoehen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
    }
    
}
