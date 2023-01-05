/*
 * ExBadLoop.java
 *
 * Created on 17 Май 2006 г., 11:01
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExBadLoop   extends ExFTS{
    
    /** Creates a new instance of ExBadLoop */
    public ExBadLoop() {
    }
    public ExBadLoop(int lan) {
            super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "No loop, please!";
            default:return "Мертвая петля, запрещено.";
        }
        
    }    
}
