/*
 * ExMustBeQueryAddr.java
 *
 * Created on 17 ��� 2006 �., 9:44
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExMustBeQueryAddr   extends ExFTS{
    
    /** Creates a new instance of ExMustBeQueryAddr */
    public ExMustBeQueryAddr() {
    }
    public ExMustBeQueryAddr(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "Address must be for Query-Object node.";
            default:return "����� ������ ��������������� �������-�������.";
        }
        
    }    
}
