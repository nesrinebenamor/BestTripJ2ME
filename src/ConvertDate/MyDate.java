/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConvertDate;

import java.util.Date;


/**
 *
 * @author USER
 */
public  class MyDate {
    
    public static boolean dateSupCurrentDate(Date date)
    {
        //Date currentDate = new Date();
        String currentDate = getMyDate(new Date().toString());
        String dateString = getMyDate(date.toString());
        int currentDateInt = Integer.parseInt(currentDate.substring(0,4)+currentDate.substring(5,7)+currentDate.substring(8,10)) ;
        int dateInt = Integer.parseInt(dateString.substring(0,4)+dateString.substring(5,7)+dateString.substring(8,10)) ;
       
        return dateInt>currentDateInt;
    }
   
    public static String getMyDate(String date)
    {
        
            
           String year = date.substring(date.length()-4);
           
             String day = date.substring(8,10);
            
            String month = date.substring(4,7);
            
            if(month.equals("Jan"))
            {
                month="01";
            }
            if(month.equals("Feb"))
            {
                month="02";
            }
            if(month.equals("Mar"))
            {
                month="03";
            }
            if(month.equals("Apr"))
            {
                month="04";
            }
            if(month.equals("May"))
            {
                month="05";
            }
            if(month.equals("Jun"))
            {
                month="06";
            }
            if(month.equals("Jul"))
            {
                month="07";
            }
            if(month.equals("Aug"))
            {
                month="08";
            }
            if(month.equals("Sep"))
            {
                month="09";
            }
            if(month.equals("Oct"))
            {
                month="10";
            }
            if(month.equals("Nov"))
            {
                month="11";
            }
            if(month.equals("Dec"))
            {
                month="12";
            }
            return year+"-"+month+"-"+day;
    }
    
}
