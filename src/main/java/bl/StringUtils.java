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

    public static String correctLettersForAPI(String name)
    {
        String correctName;
        correctName = name.replaceAll("Ä", "Ae");
        correctName = name.replaceAll("Ü", "Ue");
        correctName = name.replaceAll("Ö", "Oe");
        correctName = name.replaceAll("ä", "ae");
        correctName = name.replaceAll("ü", "ue");
        correctName = name.replaceAll("ö", "oe");
        correctName = name.replaceAll("ß", "ss");
        return correctName;
    }

    public static String deleteSpaces(String name)
    {
        return name.replaceAll(" ", ";");
    }

}
