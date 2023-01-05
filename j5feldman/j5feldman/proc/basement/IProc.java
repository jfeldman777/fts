/*
 * IProc.java
 *
 * Created on 6 јпрель 2004 г., 15:16
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public interface IProc {
    String result(String fun,String par)throws Exception;
    String publish(String fun,String par)throws Exception;  
    void init(XPump pp,int id)throws Exception;
    String description();
    String descriptionT(int id); 
    String descriptionT(int id,boolean force);     
//    String descriptionW(int num); 
    String ulTreePub();
    String parDesc(int id,int k);
    String getK1()throws Exception;
    String getK1(boolean force)throws Exception;    
    Set<String> getKeySet();
    boolean hasForce();
    boolean isForce(String fun);
}
