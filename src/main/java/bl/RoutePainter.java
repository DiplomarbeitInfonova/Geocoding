package bl;

import beans.Location;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Painter;
import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author Dominik
 */
public class RoutePainter extends WaypointPainter
{
    //author Dominik
    private JXMapKit mapkit;
    private List<GeoPosition> region = new ArrayList<GeoPosition>();

    public RoutePainter(JXMapKit mp, LinkedList<Location> points)
    {
        mapkit = mp;

        for (int i = 0; i < points.size(); i++)
        {
            region.add(new GeoPosition(points.get(i).getxKoord(), points.get(i).getyKoord()));
        }

    }

    public Painter<JXMapViewer> getPainter()
    {
        return lineOverlay;
    }
    Painter<JXMapViewer> lineOverlay = new Painter<JXMapViewer>()
    {

        public void paint(Graphics2D g, JXMapViewer map, int w, int h)
        {

            g = (Graphics2D) g.create();
            //convert from viewport to world bitmap
            Rectangle rect = mapkit.getMainMap().getViewportBounds();
            g.translate(-rect.x, -rect.y);

            //do the drawing
            g.setColor(Color.RED);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(2));

            int lastX = -1;
            int lastY = -1;
            for (GeoPosition gp : region)
            {
                //convert geo to world bitmap pixel
                Point2D pt = mapkit.getMainMap().getTileFactory().geoToPixel(gp, mapkit.getMainMap().getZoom());
                if (lastX != -1 && lastY != -1)
                {
                    g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                }
                lastX = (int) pt.getX();
                lastY = (int) pt.getY();
            }
            g.dispose();

        }
    };
}
