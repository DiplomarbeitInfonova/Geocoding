/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;
/*

*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Aufrufzaehler {
    
    private int counter;
    private String path;
    private final int max = 1000;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private String text;

    public Aufrufzaehler(String path)
    {
        this.path = path;
        System.out.println("Aufrufezähler:");
        System.out.println("Path: "+path);
    }
    
    private void prepareWriting()
    {
        Date d = java.util.Calendar.getInstance().getTime();
        
        text = sdf.format(d);
        
    }
    
    public void writeOnFile(String art, int count)
    {
        this.prepareWriting();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            
            counter = 0;
            counter = count;
            text+=art + "-"+counter;
            bw.write(text);
            
        } catch (IOException ex) {
        
            System.out.println("Aufrufezaehler:");
            System.out.println("Fehler beim Schreiben auf File!!");
            System.out.println(ex.toString());
        }
    }
    
    private int count() throws IOException, ParseException
    {
        int aktCounter=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            while(!(line = br.readLine()).isEmpty())
            {
                String [] numbers = line.split("-");
                Date d = sdf.parse(numbers[0]);
                if(d.equals(java.util.Calendar.getInstance().getTime()))
                {
                    aktCounter+=Integer.parseInt(numbers[1]);
                }
                
            }
            
            counter = aktCounter;
            
        } catch (FileNotFoundException ex) {
            
            System.out.println("Aufrufzaehler: ");
            System.out.println("File nicht gefunden!!");
            System.out.println(ex.toString());
            
        }
        
        return counter;
    }
    
 
    public static void main(String[] args) {
        
    }
    
    
}
