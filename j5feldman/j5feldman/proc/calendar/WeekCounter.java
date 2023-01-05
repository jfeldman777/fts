/*
 * WeekCounter.java
 *
 * Created on 18 јвгуст 2004 г., 12:50
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class WeekCounter extends Counter implements ICounter{
    int week=-1;
    /** Creates a new instance of WeekCounter */
    public WeekCounter() {
        super();
        cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY); 
    }
    
    public void step(){
        cal.add(Calendar.WEEK_OF_YEAR,1);
        int w = -2;
        if(week!=(w=cal.get(Calendar.WEEK_OF_MONTH))){
            td = w<week; 
            week = w;
        }
    }
    

    
    public String getW1(){
        int dw = cal.get(Calendar.WEEK_OF_YEAR);  
        return ""+dw/10;
    }
    
    public String getW2(){
        int d1 = cal.get(Calendar.WEEK_OF_YEAR);  
        int d2 = d1%10;//d1-(d1/10)*10;        
        return ""+d2;
    }
    
    
    public String bar(GregorianCalendar start, GregorianCalendar end) {
        start.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        end.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
        return (cal.after(start)&&cal.before(end)?"*":"-");
    }    
}
