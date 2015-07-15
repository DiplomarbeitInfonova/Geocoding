package beans;

import java.util.Date;

/**
 *
 * @author patzineubi
 */
public class Leg {

    @Override
    public String toString() {
        return "Leg{" + "distance=" + distance + "km, duration=" + duration + "s, end_loc_lat=" + end_loc_lat + ", end_loc_lng=" + end_loc_lng + ", html_instruction=" + html_instruction + ", polylinge_point=" + polylinge_point + ", start_loc_lat=" + start_loc_lat + ", start_loc_lng=" + start_loc_lng + ", travel_mode=" + travel_mode + '}';
    }

    private float distance;
    private int duration;
    private float end_loc_lat;
    private float end_loc_lng;
    private String html_instruction;
    private String polylinge_point;
    private float start_loc_lat;
    private float start_loc_lng;
    private String travel_mode;

    public Leg(float distance, int duration, float end_loc_lat, float end_loc_lng, String html_instruction, String polylinge_point, float start_loc_lat, float start_loc_lng, String travel_mode) {
        this.distance = distance;
        this.duration = duration;
        this.end_loc_lat = end_loc_lat;
        this.end_loc_lng = end_loc_lng;
        this.html_instruction = html_instruction;
        this.polylinge_point = polylinge_point;
        this.start_loc_lat = start_loc_lat;
        this.start_loc_lng = start_loc_lng;
        this.travel_mode = travel_mode;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getEnd_loc_lat() {
        return end_loc_lat;
    }

    public void setEnd_loc_lat(float end_loc_lat) {
        this.end_loc_lat = end_loc_lat;
    }

    public float getEnd_loc_lng() {
        return end_loc_lng;
    }

    public void setEnd_loc_lng(float end_loc_lng) {
        this.end_loc_lng = end_loc_lng;
    }

    public String getHtml_instruction() {
        return html_instruction;
    }

    public void setHtml_instruction(String html_instruction) {
        this.html_instruction = html_instruction;
    }

    public String getPolylinge_point() {
        return polylinge_point;
    }

    public void setPolylinge_point(String polylinge_point) {
        this.polylinge_point = polylinge_point;
    }

    public float getStart_loc_lat() {
        return start_loc_lat;
    }

    public void setStart_loc_lat(float start_loc_lat) {
        this.start_loc_lat = start_loc_lat;
    }

    public float getStart_loc_lng() {
        return start_loc_lng;
    }

    public void setStart_loc_lng(float start_loc_lng) {
        this.start_loc_lng = start_loc_lng;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }
}
