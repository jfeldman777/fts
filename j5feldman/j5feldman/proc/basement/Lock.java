/*
 * Lock.java
 *
 * Created on 25 Май 2007 г., 13:50
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import j5feldman.ex.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class Lock {
    XPump pp;
    /** Creates a new instance of Lock */
    public Lock(XPump pp) {
        this.pp = pp;
    }
    
    public void set(int id,int var)throws Exception{
        long L = System.currentTimeMillis();
        switch(var){
            case 1: 
                set(true,id);
                return;//lock
            case 2: 
                set(false,id);
                return;//lock     
            case 3: 
                List<Integer> lp = P3Node.allDown30(pp,id,L+20000);///max=300 
                for(int i:lp){
                    set(true,i);
                }
                return;//lock tree
            case 4: 
                List<Integer> lp2 = P3Node.allDown30(pp,id,L+20000);///max=300 
                for(int i:lp2){
                    set(false,i);
                }               
                return;//unlock tree
            default: throw new ExNoPars();
        }
    }
    
    public void set(boolean yes,int id)throws Exception{
           pp.exec("update object set fin=" +(yes?"true":"false")+
                   " where id="+id);
    }
    
    public boolean get(int id)throws Exception{
        return pp.getB("select fin from object where id="+id);
    }
}
