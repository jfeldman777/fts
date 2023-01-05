/*
 * ExRefTable.java
 *
 * Created on 10 Май 2006 г., 14:04
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExRefTable extends ExFTS{
    
    /** Creates a new instance of ExRefTable */
    public ExRefTable() {
    }
    
    public ExRefTable(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "bad reference type.";
            default:return "несуществующий тип для ссылки.";
        }
        
    }    
    
}
