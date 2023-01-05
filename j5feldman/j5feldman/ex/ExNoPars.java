/*
 * ExNoPars.java
 *
 * Created on 25 Май 2007 г., 15:33
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExNoPars extends ExFTS{
    public ExNoPars(){}
    /** Creates a new instance of ExNoPars */
    public ExNoPars(int lan) {
            super(lan);
    }
    public String fts(){
        switch(lan){
            case 1:return "lack of pars";
            default:return "не хватает параметров";
        }
        
    }
}
