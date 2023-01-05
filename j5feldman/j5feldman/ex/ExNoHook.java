/*
 * ExNoHook.java
 *
 * Created on 17 Май 2006 г., 8:57
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExNoHook  extends ExFTS{
    
    /** Creates a new instance of ExNoHook */
    public ExNoHook() {
    }
    
    public ExNoHook(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "You cannot place something (node or shadow) under nothing (shadow or pseudo-node).";
            default:return "Нельзя моместить нечто (узел или его тень) под ничто (тень или псевдо-объект).";
        }
        
    }    
}
