package bl;

import java.io.*;
import java.net.*;

// Author Veronika Gößler

/*
 Wird der URL als HTML Request an die Google Maps API gesendet und 
 die Response zurückgegeben.
 */
public final class SendToMapsAPI
{

    private static final int BUFFER_SIZE = 1024 * 10;
    private static final int ZERO = 0;
    private final byte[] dataBuffer = new byte[BUFFER_SIZE];
    private final URL urlObject;

    public SendToMapsAPI(String urlString) throws MalformedURLException
    {
        this.urlObject = new URL(urlString);
    }

    public String read()
    {
        final StringBuilder sb = new StringBuilder();
        try
        {
            final BufferedInputStream in = new BufferedInputStream(urlObject.openStream());
            int bytesRead = ZERO;
            while ((bytesRead = in.read(dataBuffer, ZERO, BUFFER_SIZE)) >= ZERO)
            {
                sb.append(new String(dataBuffer, ZERO, bytesRead));
            }
        } catch (UnknownHostException e)
        {
            return null;
        } catch (IOException e)
        {
            return null;
        }

        return sb.toString();
    }

    //Usage:
    public static void main(String[] args)
    {
        try
        {
            //SendToMapsAPI url = new SendToMapsAPI("http://maps.googleapis.com/maps/api/distancematrix/json?origins=48.37735,16.32328&destinations=46.9644,15.47922");
            SendToMapsAPI url = new SendToMapsAPI("https://roads.googleapis.com/v1/snapToRoads/?path=46.72281265258789,%2015.648141860961914|46.99358367919922,%2015.411750793457031|47.02824401855469,%2015.413273811340332|47.032527923583984,%2015.412571907043457|47.03444290161133,%2015.411592483520508|47.06233596801758,%2015.423318862915039|47.06652069091797,%2015.427214622497559|47.066871643066406,%2015.43134593963623|47.06647872924805,%2015.431346893310547|47.06653594970703,%2015.43171215057373|47.06958770751953,%2015.434045791625977|47.06946563720703,%2015.436347961425781|47.068878173828125,%2015.43686294555664|47.06978225708008,%2015.438536643981934|47.07022476196289,%2015.438182830810547&interpolate=true&key=AIzaSyDI6ex1fUOJKjomDnoe97atKcWyxDotOEo");
            String contents = url.read();

            if (contents != null)
            {
                System.out.println(contents);
            } else
            {
                System.out.println("ERROR!");
            }
        } catch (MalformedURLException e)
        {
            System.out.println("Check you the url!");
        }
    }
}
