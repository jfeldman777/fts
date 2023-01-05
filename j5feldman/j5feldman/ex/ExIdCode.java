/*
 * ExIdCode.java
 *
 * Created on 10 Май 2006 г., 11:08
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExIdCode extends ExFTS{
    
    /** Creates a new instance of ExIdCode */
    public ExIdCode() {
    }
    public ExIdCode(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "No such object in this DB. Check your input strings.";
            default:return "Такого объекта в этой базе нет. Проверьте вводимые строки.";
        }
        
    }    
}
