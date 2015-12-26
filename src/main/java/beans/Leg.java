package beans;

/**
 *
 * @author patzineubi
 * Klasse stellt ein Bein dar
 */
public class Leg
{

    @Override
    public String toString()
    {
        return "Leg{" + "distance=" + distance + "km, duration=" + duration + "s, end_loc_lat=" + end_loc_lat + ", end_loc_lng=" + end_loc_lng + ", html_instruction=" + html_instruction + ", elevation=" + elevation;
    }

    private float distance;
    private int duration;
    private float end_loc_lat;
    private float end_loc_lng;
    private String html_instruction;
    private double elevation;

    public Leg(float distance, int duration, float end_loc_lat, float end_loc_lng, String html_instruction, double elevation)
    {
        this.distance = distance;
        this.duration = duration;
        this.end_loc_lat = end_loc_lat;
        this.end_loc_lng = end_loc_lng;
        this.html_instruction = html_instruction;
        this.elevation = elevation;
    }

    public float getDistance()
    {
        return distance;
    }

    public void setDistance(float distance)
    {
        this.distance = distance;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public float getEnd_loc_lat()
    {
        return end_loc_lat;
    }

    public void setEnd_loc_lat(float end_loc_lat)
    {
        this.end_loc_lat = end_loc_lat;
    }

    public float getEnd_loc_lng()
    {
        return end_loc_lng;
    }

    public void setEnd_loc_lng(float end_loc_lng)
    {
        this.end_loc_lng = end_loc_lng;
    }

    public String getHtml_instruction()
    {
        return html_instruction;
    }

    public void setHtml_instruction(String html_instruction)
    {
        this.html_instruction = html_instruction;
    }

    public double getElevation()
    {
        return elevation;
    }

    public void setElevation(double elevation)
    {
        this.elevation = elevation;
    }
}
