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

}
