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
public class StringUtils
{

    /**
     * Author: Dominik
     * Da in den Textfeldern der GUI auch Umlaute eingegeben werden können, diese aber nicht von Google verarbeitet werden können, 
     * muss man alle Umlaute/Scharfe S usw. ersetzen. 
     * @param name
     * @return 
     */
    public static String correctLettersForAPI(String name)
    {
        String correctName = name;
        correctName = correctName.replaceAll("Ä", "Ae");
        correctName = correctName.replaceAll("Ü", "Ue");
        correctName = correctName.replaceAll("Ö", "Oe");
        correctName = correctName.replaceAll("ä", "ae");
        correctName = correctName.replaceAll("ü", "ue");
        correctName = correctName.replaceAll("ö", "oe");
        correctName = correctName.replaceAll("ß", "ss");
        return correctName;
    }

    public static String deleteSpaces(String name)
    {
        return name.replaceAll(" ", ";");
    }
    
    public String correctLettersFromAPI(String name)
    {
        String correctName=name;
        correctName = correctName.replaceAll("Ãœ", "Ü");
        correctName = correctName.replaceAll("Ã¤", "ä");
        correctName = correctName.replaceAll("Ã¶", "ö");
        correctName = correctName.replaceAll("Ã–", "Ö");
        correctName = correctName.replaceAll("ÃŸ", "ß");
        correctName = correctName.replaceAll("Ã¼", "ü");
        
        if(correctName.length()>30)
            correctName=correctName.substring(0, 30)+".";
        return correctName;
    }

}
