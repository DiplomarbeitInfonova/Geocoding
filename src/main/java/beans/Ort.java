/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

/**
 *
 * @author David
 */
public class Ort {
    
    private String name;
    private double xKoord;
    private double yKoord;

    public Ort(String name, double xKoord, double yKoord) {
        this.name = name;
        this.xKoord = xKoord;
        this.yKoord = yKoord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getxKoord() {
        return xKoord;
    }

    public void setxKoord(double xKoord) {
        this.xKoord = xKoord;
    }

    public double getyKoord() {
        return yKoord;
    }

    public void setyKoord(double yKoord) {
        this.yKoord = yKoord;
    }
    
    
    
}
