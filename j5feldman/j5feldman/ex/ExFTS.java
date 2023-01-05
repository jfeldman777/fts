/*
 * ExFTS.java
 *
 * Created on 12 Март 2007 г., 14:41
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExFTS extends Exception{
    int lan=0;
    /** Creates a new instance of ExFTS */
    public ExFTS() {
    }
    public ExFTS(int lan) {
        this.lan=lan;
    }    
    
    public String fts(){
        switch(lan){
            case 1:return "A problems of the list.";
            default:return "Проблема из списка.";
        }
        
    }
    
}
