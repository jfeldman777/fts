/*
 * ExTabCol.java
 *
 * Created on 10 ��� 2006 �., 11:29
 * Auhor J.Feldman
 */

package j5feldman.ex;

/**
 *
 * @author Jacob Feldman
 */
public class ExTooBig extends ExFTS{
    
    /** Creates a new instance of ExTabCol */
    public ExTooBig() {
    }
    
    public ExTooBig(int lan) {
        super(lan);
    }    
    
    public String fts(){
        switch(lan){
            case 1:return "The tree you ask to analyse is too big... try to diminish it, change the root etc..";
            default:return "������������� ����� ������ ������� ����� - " +
                    "���������� ��� ���������, ��������, ������� ������ ������ ������.";
        }
        
    }
}
