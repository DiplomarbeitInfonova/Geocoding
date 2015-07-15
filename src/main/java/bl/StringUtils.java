/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bl;

/**
 *
 * @author David
 */
public class StringUtils {
    
    public static String correctLettersForAPI(String name)
    {
        String correctName=name;
        
        correctName = correctName.replace("ä", "ae");
        correctName = correctName.replace("ü", "ue");
        correctName = correctName.replace("ö", "oe");
        correctName = correctName.replace("ß", "ss");
        
        return correctName;
    }
    
    public static String deleteSpaces(String name)
    {
        String correctName=name;
        
        name = name.replaceAll(" ", ";");
        
        return correctName;
    }
    
}
