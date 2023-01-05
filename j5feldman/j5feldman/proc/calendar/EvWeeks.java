/*
 * EvDays.java
 *
 * Created on 18 јвгуст 2004 г., 8:06
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class EvWeeks {
    int WIDTH=50;
    /** Creates a new instance of EvDays */
    public EvWeeks(int width) {
        WIDTH = width;
    }
    String calY(){
        WeekCounter counter = new WeekCounter();        
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i==0){
                s+= counter.getY(); 
            }else{      
                s+= counter.getYtd(); 
            };    
            counter.step();
        }
        return "<tr><td align=right>Y</td><td>"+s+"</td></tr>";         
    }
    
    String calM(){
        WeekCounter counter = new WeekCounter();  
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i==0){
                s+= counter.getM();    
            }else{
                s+= counter.getMtd(); 
            };    
            counter.step();
        }
        return "<tr><td align=right>M</td><td>"+s+"</td></tr>";        
    }
    
    String calW1(){
        WeekCounter counter = new WeekCounter(); 
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i>0)s+=counter.td();
            s+=counter.getW1();
            counter.step();
        }        
        return "<tr><td align=right>W</td><td>"+s+"</td></tr>";
    }
    String calW2(){
        WeekCounter counter = new WeekCounter(); 
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i>0)s+=counter.td();
            s+=counter.getW2();
            counter.step();
        }        
        return "<tr><td align=right>W</td><td>"+s+"</td></tr>";
    }    

//    public String tdMonth(GregorianCalendar c,String x){
//        String s = "";
//        int iw = c.get(Calendar.WEEK_OF_MONTH);
//        s = iw<week?"</td><td>"+x:"";
//        if(iw!=week)week = iw;
//        return s;
//    }
//    public String bar(GregorianCalendar g,GregorianCalendar gstart,GregorianCalendar gend){
//        g.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
//        gstart.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
//        gend.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
//        return (g.after(gstart)&&g.before(gend)?"*":"-");
//    }
    
//    public String td(GregorianCalendar g){
//        g.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
//        return tdMonth(g,"");
//    } 
    public String calendar(){
         return calY()+calM()+calW1()+calW2();
    }       
}
