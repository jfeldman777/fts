/*
 * ExUnseen.java
 *
 * Created on 16 Май 2006 г., 13:25
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExUnseen extends ExFTS{
    
    /** Creates a new instance of ExUnseen */
    public ExUnseen() {
    }
    
    public ExUnseen(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "The node you work with on the right must be seen in the tree on the left.";
            default:return "Узел с которым вы работаете справа должен быть виден в дереве слева.";
        }
        
    }    
}
