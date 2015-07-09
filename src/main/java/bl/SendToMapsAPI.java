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
            SendToMapsAPI url = new SendToMapsAPI("http://maps.googleapis.com/maps/api/distancematrix/json?origins=48.37735,16.32328&destinations=46.9644,15.47922");
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
