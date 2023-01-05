/*
 * ExSuperType.java
 *
 * Created on 10 Май 2006 г., 14:16
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExSuperType extends ExFTS{
    
    /** Creates a new instance of ExSuperType */
    public ExSuperType() {
    }
    
    public ExSuperType(int lan) {
    }
    
    public String fts(){
        switch(lan){
            case 1:return "bad supertype.";
            default:return "Не существующий тип для наследования.";
        }
        
    }    
}
