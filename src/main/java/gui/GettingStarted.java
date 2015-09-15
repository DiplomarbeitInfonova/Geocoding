/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author enisl
 */
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

public class GettingStarted extends ApplicationTemplate
{
    public static class AppFrame extends ApplicationTemplate.AppFrame
    {
        public AppFrame()
        {
            super(true, true, false);

            // Add graticule
            insertBeforePlacenames(this.getWwd(), new LatLonGraticuleLayer());

            // Update layer
            this.getLayerPanel().update(this.getWwd());
        }
    }

    public static void main(String[] args)
    {
        ApplicationTemplate.start("Getting Started with NASA World Wind", AppFrame.class);
    }
}