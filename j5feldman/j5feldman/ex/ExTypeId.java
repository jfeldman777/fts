/*
 * ExTypeCode.java
 *
 * Created on 10 Май 2006 г., 11:09
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExTypeId extends ExFTS{
    
    /** Creates a new instance of ExTypeCode */
    public ExTypeId() {
    }
    
    public ExTypeId(int lan) {
        super(lan);
    } 
    
    public String fts(){
        switch(lan){
            case 1:return "Type with such ID does NOT exists. ";
            default:return "Этот ID типа не существует.";
        }
        
    }    
}
