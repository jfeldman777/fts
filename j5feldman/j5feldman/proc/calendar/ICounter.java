/*
 * ICounter.java
 *
 * Created on 18 јвгуст 2004 г., 13:35
 */

package j5feldman.proc.calendar;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public interface ICounter {
    void step();
    String td();
    String bar(GregorianCalendar start,GregorianCalendar end);
}
