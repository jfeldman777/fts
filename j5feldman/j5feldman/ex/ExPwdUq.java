/*
 * ExPwdUq.java
 *
 * Created on 10 Май 2006 г., 11:35
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExPwdUq extends ExFTS{
    
    /** Creates a new instance of ExPwdUq */
    public ExPwdUq() {
    }
    
    public ExPwdUq(int lan) {
        super(lan);
    } 
    
    public String fts(){
        switch(lan){
            case 1:return "This password is unaccepatable, try another one.";
            default:return "Этот пароль использовать нельзя, попробуйте другой.";
        }
        
    }    
    
}
