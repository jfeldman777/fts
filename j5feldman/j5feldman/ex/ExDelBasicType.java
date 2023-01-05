/*
 * ExTabCol.java
 *
 * Created on 10 Май 2006 г., 11:29
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExDelBasicType extends ExFTS{
    
    /** Creates a new instance of ExTabCol */
    public ExDelBasicType() {
    }
    
    public ExDelBasicType(int lan) {
        super(lan);
    }    
    
    public String fts(){
        switch(lan){
            case 1:return "You cannot delete type of basics ";
            default:return "Базовый тип удалить нельзя ";
        }
        
    }
}
