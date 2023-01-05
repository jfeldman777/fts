/*
 * ExMustBeDbfImport.java
 *
 * Created on 17 Май 2006 г., 9:44
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExMustBeDbfImport   extends ExFTS{
    
    /** Creates a new instance of ExMustBeQueryAddr */
    public ExMustBeDbfImport() {
    }
    public ExMustBeDbfImport(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "Address must be for node with type: dbf_import .";
            default:return "Адрес должен соответствовать объекту типа dbf_import";
        }
        
    }    
}
