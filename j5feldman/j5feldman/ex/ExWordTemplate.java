/*
 * ExWordTemplate.java
 *
 * Created on 10 ������ 2006 �., 11:29
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExWordTemplate extends ExFTS{
    
    /** Creates a new instance of ExWordTemplate */
    public ExWordTemplate() {
    }
    
    public ExWordTemplate(int lan) {
        super(lan);
    }   
    public String fts(){
        switch(lan){
            case 1:return "No Word Template when printing.";
            default:return "� ��� ��� ������� ��� ������.";
        }
        
    }    
}
