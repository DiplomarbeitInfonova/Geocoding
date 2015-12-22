/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import beans.Location;
import gui.EingabeGUI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Domi
 */
public class CSVHandler {
    public CSVHandler(){
        
    }
    
    public LinkedList<Location> importLocsfromCSV(int format){
        
        
    LinkedList<Location> loclist=new LinkedList<Location>();
         if (format != 2) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\resources"));
            fc.setDialogTitle("Quelldatei auswählen");
            fc.showOpenDialog(null);

            File f = fc.getSelectedFile();
            BufferedReader br;
            LinkedList<Location> locsfromfile = new LinkedList<Location>();

            try {

                br = new BufferedReader(new FileReader(f));

                String line = "";
             
                while ((line = br.readLine()) != null) {

                    line = line.replace("\"", "");

                    String[] splits = line.split(";");
                    Location l = new Location();
                    if (format == 0) {
                        if (splits.length == 4) {
                            l = new Location("-", Double.parseDouble(splits[1]), Double.parseDouble(splits[2]), 0.0);
                        } else if (splits.length == 2) {
                            l = new Location("-", Double.parseDouble(splits[0]), Double.parseDouble(splits[1]), 0.0);
                        }

                    } else if (format == 1) {
                        l = new Location(splits[0], Double.parseDouble(splits[1]), Double.parseDouble(splits[2]), Double.parseDouble(splits[3]));
                    }

                    locsfromfile.add(l);

                }
                loclist=locsfromfile;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EingabeGUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EingabeGUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
           
    }
          return loclist;

    }
    
    
    public void exportLocstoCSV(int format, LinkedList<Location> locations){
        if (format != 2) {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\resources"));
            fc.setDialogTitle("Zieldatei auswählen");
            fc.showSaveDialog(null);

            File f = fc.getSelectedFile();

            BufferedWriter bw;

            try {

                bw = new BufferedWriter(new FileWriter(f));

                String line = "";
                int i;
                for (i = 0; i < locations.size(); i++) {
                    if (format == 0) {
                        bw.write(locations.get(i).getxKoord() + ";" + locations.get(i).getyKoord());
                    } else if (format == 1) {
                        bw.write(locations.get(i).toCSVRow());
                    }

                    bw.newLine();
                }

                bw.close();
                JOptionPane.showMessageDialog(null, i + " Locations wurden erfolgreich exportiert");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(EingabeGUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EingabeGUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    }
