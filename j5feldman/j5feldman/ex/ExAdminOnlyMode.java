/*
 * ExAdminOnlyMode.java
 *
 * Created on 17 Май 2006 г., 9:03
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExAdminOnlyMode  extends ExFTS{
    
    /** Creates a new instance of ExAdminOnlyMode */
    public ExAdminOnlyMode() {
    }
    
    public ExAdminOnlyMode(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "Admin only mode.";
            default:return "Система сейчас работает только с администратором.";
        }
        
    }
}
