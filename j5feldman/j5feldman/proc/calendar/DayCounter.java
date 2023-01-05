/*
 * DayCounter.java
 *
 * Created on 18 јвгуст 2004 г., 13:19
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class DayCounter extends Counter implements ICounter{
    
    /** Creates a new instance of DayCounter */
    public DayCounter() {
        super();
    }
    
    public void step(){
        cal.add(Calendar.DAY_OF_YEAR,1);
        td = cal.get(Calendar.DAY_OF_MONTH)==1; 
    }
    public String getD1(){
        int dw = cal.get(Calendar.DAY_OF_MONTH);  
        return ""+dw/10;
    }
    
    public String getD2(){
        int d1 = cal.get(Calendar.DAY_OF_MONTH);  
        int d2 = d1%10;//d1-(d1/10)*10;        
        return ""+d2;
    } 
    
    public String getDofW(){
        int dw = cal.get(Calendar.DAY_OF_WEEK)-1;  
        if(dw>5)return "=";
        if(dw<1)return "=";
        return ""+dw;
    }
    
    public String bar(GregorianCalendar start, GregorianCalendar end) {
        return cal.after(start)&&cal.before(end)?"*":"-";
    }
    
}
