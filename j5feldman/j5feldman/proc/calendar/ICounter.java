/*
 * ICounter.java
 *
 * Created on 18 ������ 2004 �., 13:35
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / �������� ���� ����������
 */
public interface ICounter {
    void step();
    String td();
    String bar(GregorianCalendar start,GregorianCalendar end);
}
