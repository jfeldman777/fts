/*
 * Counter.java
 *
 * Created on 18 ������ 2004 �., 13:17
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / �������� ���� ����������
 */
public class Counter {
    GregorianCalendar cal;
    boolean td=false;    
    /** Creates a new instance of Counter */
    public Counter() {
         cal=new GregorianCalendar();
    }
    public String td(){
        return td?"</td><td>":"";
    }
    public String getY(){
        return (""+cal.get(Calendar.YEAR)).substring(2);
    }
    
    public String getYtd(){
        return td?
        ("</td><td>"+(""+cal.get(Calendar.YEAR)).substring(2)) 
        :"";
    }    
    public String getM(){
        return ""+(cal.get(Calendar.MONTH)+1);
    }
    
    public String getMtd(){
        return td?"</td><td>"+(cal.get(Calendar.MONTH)+1):"";
    }    
}
