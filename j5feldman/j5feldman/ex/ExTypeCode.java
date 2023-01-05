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
public class ExTypeCode extends ExFTS{
    
    /** Creates a new instance of ExTypeCode */
    public ExTypeCode() {
    }
    
    public ExTypeCode(int lan) {
        super(lan);
    } 
    
    public String fts(){
        switch(lan){
            case 1:return "Type with such code exists. Check the code.";
            default:return "Этот код типа уже занят.";
        }
        
    }    
}
