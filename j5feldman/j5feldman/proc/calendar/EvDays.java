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
public class EvDays {
    int WIDTH=50;
    /** Creates a new instance of EvDays */
    public EvDays(int width) {
        WIDTH = width;
    }
    String calY(){
        DayCounter counter = new DayCounter();        
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i==0){
                s+= counter.getY(); 
            }else  s+= counter.getYtd();  
  
            counter.step();
        }
        return "<tr><td align=right>Y</td><td>"+s+"</td></tr>";         
    }
    
    String calM(){
        DayCounter counter = new DayCounter();         
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i==0){
                s+= counter.getM();    
            }else s+= counter.getMtd(); 
            
            counter.step();
        }
        return "<tr><td align=right>M</td><td>"+s+"</td></tr>";        
    }
    
    String calD1(){
        DayCounter counter = new DayCounter(); 
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i>0)s+=counter.td();
            s+=counter.getD1();
            counter.step();
        }
        return "<tr><td align=right>D</td><td>"+s+"</td></tr>";
    }
    
    String calD2(){
        DayCounter counter = new DayCounter(); 
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i>0)s+=counter.td();
            s+=counter.getD2();
            counter.step();
        }        
        return "<tr><td align=right>D</td><td>"+s+"</td></tr>";
    }
    String calD3(){
        DayCounter counter = new DayCounter(); 
        String s = "";
        for(int i=0;i<WIDTH;i++){
            if(i>0)s+=counter.td();
            s+=counter.getDofW();
            counter.step();
        }        
        return "<tr><td align=right>W</td><td>"+s+"</td></tr>";
    }
    

    public String calendar(){
        return calY()+calM()+calD1()+calD2()+calD3();
    }    
}
