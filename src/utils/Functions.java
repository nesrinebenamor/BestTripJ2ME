/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Amine
 */
public class Functions {
    public static Date StringToDate(String dateInput){
        
        Calendar cal = Calendar.getInstance();
        
        int startPos = dateInput.indexOf('-', 0);
        cal.set(Calendar.YEAR, Integer.parseInt(dateInput.substring(0, startPos)));
        
        int nextPos = dateInput.indexOf('-', startPos+1);
        //decembre 11
        cal.set(Calendar.MONTH, Integer.parseInt(dateInput.substring(startPos+1, nextPos))-1);
        cal.set(Calendar.DATE, Integer.parseInt(dateInput.substring(nextPos+1)));
        
        return cal.getTime();
    }
    
     public static Date StringTimeToDate(String dateInput){
        
        Calendar cal = Calendar.getInstance();
        
        int startPos = dateInput.indexOf('-', 0);
        cal.set(Calendar.YEAR, Integer.parseInt(dateInput.substring(0, startPos)));
        
        int nextPos = dateInput.indexOf('-', startPos+1);
        //decembre 11
        cal.set(Calendar.MONTH, Integer.parseInt(dateInput.substring(startPos+1, nextPos))-1);
        cal.set(Calendar.DATE, Integer.parseInt(dateInput.substring(nextPos+1,nextPos+3)));
        
        return cal.getTime();
    }
     
     public static String DateToString(Date dateInput){
         Calendar cc = Calendar.getInstance();
         cc.setTime(dateInput);
         
         return cc.get(Calendar.YEAR )+"-"+(cc.get(Calendar.MONTH)+1)+"-"+cc.get(Calendar.DATE);
     }
     
     public static String DateTimeToString(Date dateInput){
         Calendar cc = Calendar.getInstance();
         cc.setTime(dateInput);
         
         return cc.get(Calendar.YEAR )+"-"+(cc.get(Calendar.MONTH)+1)+"-"+cc.get(Calendar.DATE)+" "+cc.get(Calendar.HOUR)+":"+cc.get(Calendar.MINUTE)+":"+cc.get(Calendar.SECOND)+".000000";
     }
     
     public static String[] ConstructTable(String str)
    {
        String[] t = new String[10000];
        int i=0;
        int j=0;
        int cout =0;
        String mot ="";
        while (i<str.length())
        {
            mot = str.charAt(i)+"";
           if (   t[j]==null)
           {
                  t[j]= "";
           }
            if (mot.equals(","))
            {
                i++;
                j++;
                mot = "";
            }
            else
            {
              
               t[j]+= mot ;
               i++;
            }
        }
        return t ; 
    }
    
    public static String[] ConvertTable(String[] tab , int number)
    {
        String [] t = new String[number];
        for (int i=0;i<number;i++)
        {
            t[i]=tab[i];
        }
        return t ; 
    }
}
