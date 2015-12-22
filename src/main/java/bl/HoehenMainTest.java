/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import gui.HoehenPanel;
import java.util.LinkedList;

/**
 *
 * @author Veronika
 */
public class HoehenMainTest {

    /**
     * @param args the command line arguments
     */
    //public Location(String name, double xKoord, double yKoord, double hoehe)
    public static void main(String[] args) {
        // Klasse um ohne zugriff auf Google Maps das große Höhendiagramm auszutesten
        // es werden TestLocations erstellt und der HoehenPanel GUI übergeben
        LinkedList<Location> ll = new LinkedList<Location>();
        Location l = new Location("Ligist",42.9917246,15.2107184,600);
        Location l2= new Location("Ligist - Steiberg",46.9717246,15.3107184,580);
        Location l3 = new Location("Ligist-Dietenberg",46.9617246,15.3507184,560);
        Location l4 = new Location("Ligist-Grabenwart",46.9517246,15.4057184,480);
        Location l5 = new Location("Krottendorf-Ligist",46.9417246,15.4107184,550);
        Location l6 = new Location("Krottendorf",46.9017246,15.4207184,500);
        Location l7 = new Location("Krottendorf-Gaisfeld",46.8917246,15.4307184,450);
        Location l8 = new Location("St. Johann",46.8717246,15.4407184,480);
        Location l9 = new Location("Köppling",46.8617246,15.4417184,300);
        Location llast = new Location("Soeding",46.8691334,15.4502561,0);
        ll.add(l);
        ll.add(l2);
        ll.add(l4);
        ll.add(l5);
        ll.add(l6);
        ll.add(l7);
        ll.add(l8);
        ll.add(l9);
        ll.add(l3);
        ll.add(l4);
        ll.add(l5);
        ll.add(l6);
        ll.add(l7);
        ll.add(l8);
        ll.add(l9);
        ll.add(llast);
//        HoehenPanel pan = new HoehenPanel(ll);
//        pan.setVisible(true);
        LineChart_Test chart = new LineChart_Test("Hoehen");
        chart.setHoehen(ll);
        chart.paint();
        chart.pack();
        chart.setVisible(true);
    }
    
}
