/*
 * ExScriptLine.java
 *
 * Created on 24 ��� 2006 �., 14:03
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExScriptLine extends ExFTS{
    public String line;
    /** Creates a new instance of ExScriptLine */
    public ExScriptLine(String line) {
        super();
        this.line = line;
    }
    
    public ExScriptLine(String line,int lan) {
        super(lan);
        this.line = line;
    }
    
    public String fts(){
        switch(lan){
            case 1:return "Script problems.";
            default:return "� �������� ��������.";
        }
        
    }    
}
