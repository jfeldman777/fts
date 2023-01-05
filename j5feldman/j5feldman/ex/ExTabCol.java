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
public class ExTabCol extends ExFTS{
    
    /** Creates a new instance of ExTabCol */
    public ExTabCol() {
    }
    public ExTabCol(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "The column with this name exists.";
            default:return "Столбец с таким именем уже создан.";
        }
        
    }    
}
