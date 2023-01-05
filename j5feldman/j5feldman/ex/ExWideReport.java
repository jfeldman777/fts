/*
 * ExWideReport.java
 *
 * Created on 6 Март 2007 г., 9:00
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExWideReport extends ExFTS{
    
    /** Creates a new instance of ExWideReport */
    public ExWideReport() {
    }
    public ExWideReport(int lan) {
        super(lan);
    }
    public String fts(){
        switch(lan){
            case 1:return "Your report is too wide (>500).";
            default:return "Слишком широкая таблица (>500.";
        }
        
    }    
}
