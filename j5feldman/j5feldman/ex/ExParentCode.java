/*
 * ExParentCode.java
 *
 * Created on 10 ��� 2006 �., 11:08
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExParentCode extends ExFTS{
    
    /** Creates a new instance of ExParentCode */
    public ExParentCode() {
    }
    
    public ExParentCode(int lan) {
        super(lan);
    }
    
    public String fts(){
        switch(lan){
            case 1:return "The child with the same code exists, another one with the same code is unacceptable.";
            default:return "� ����� �������� ��� ���� ������ ������� � ����� ����� (������� ������� ������).";
        }
        
    }    
}
