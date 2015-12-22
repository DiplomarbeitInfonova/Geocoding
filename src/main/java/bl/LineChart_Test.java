/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Location;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author Veronika
 */
public class LineChart_Test extends ApplicationFrame{

    public LineChart_Test(String title) {
        super(title);
        
    }
    private LinkedList<Location> hoehen;
    
    public void paint()
    {
        JFreeChart lineChart = ChartFactory.createLineChart("Hoehen", 
                "Orte", "Hoehen", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        
        ChartPanel chartPanel = new ChartPanel(lineChart);
        setContentPane(chartPanel);
    }
    
    
    public LinkedList<Location> getHoehen() {
        return hoehen;
    }

    public void setHoehen(LinkedList<Location> hoehen) {
        this.hoehen = hoehen;
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Location l : hoehen)
        {
            dataset.addValue(l.getHoehe(), "Route", l.getName());
        }
        return dataset;
    }
    
    public static void main(String[] args) {
        
    }
    
}
