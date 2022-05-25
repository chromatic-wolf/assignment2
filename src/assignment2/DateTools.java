/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2;

//import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.Date;

/**
 *
 * @author caleb
 */
public class DateTools {

    public static java.sql.Date StringToDate(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            java.util.Date d1 = sdf.parse(str);
            sdf.applyPattern("yyyy-mm-dd");
            String tmp = sdf.format(d1); //yyyy-[m]m-[d]d format to this format because mysql date class is stupid and only understands american freedom formats
            System.out.println("In StringToDate: " + tmp);
            return java.sql.Date.valueOf(tmp);
                
        } catch (ParseException ex) {
            System.out.println("Error date format wrong");
        }
        return null;
    }
    
    public static String SqlDateToString(java.util.Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            String dateStr = date.toString();
            System.out.println(dateStr);
            java.util.Date d1 = sdf.parse(dateStr);

            sdf.applyPattern("dd/mm/yyyy");
            String tmp = sdf.format(d1);
            System.out.println(d1.toString());
            return tmp;
        } catch (ParseException ex) {
            System.out.println("Error date format wrong");
            return null;
        }
    }
}


