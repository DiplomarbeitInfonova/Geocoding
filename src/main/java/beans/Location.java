/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import bl.StringUtils;

/**
 *
 * @author David
 */
public class Location {
    
    private String name;
    private double xKoord;
    private double yKoord;
    private double hoehe;

    public double getHoehe() {
        return hoehe;
    }

    public void setHoehe(double hoehe) {
        this.hoehe = hoehe;
    }

    public Location(String name, double xKoord, double yKoord, double hoehe) {
        this.name = StringUtils.correctLettersForAPI(name);
        this.xKoord = xKoord;
        this.yKoord = yKoord;
        this.hoehe = hoehe;
    }
    
    public Location(String name, double xKoord, double yKoord) {
        this.name = StringUtils.correctLettersForAPI(name);
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

    @Override
    public String toString() {
        return String.format("Location=\n\tName: %s\n\t x-Koord: %f \n\t y-Koord: %f \n\t Hoehe: %f", this.name, this.xKoord, this.yKoord, this.hoehe);
    }
    
}
