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
public class ExEmptyParam extends ExFTS{
    
    /** Creates a new instance of ExTypeCode */
    public ExEmptyParam() {
    }
    
    public ExEmptyParam(int lan) {
        super(lan);
    } 
    
    public String fts(){
        switch(lan){
            case 1:return "Some mandatory parameter is empty. ";
            default:return "Обязательный параметр оказался пуст.";
        }
        
    }    
}
