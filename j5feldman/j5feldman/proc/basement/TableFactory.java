/*
 * TableFactory.java
 *
 * Created on 28 Декабрь 2005 г., 11:44
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class TableFactory {
    public Map<String,Table> map;
    boolean all;
    boolean abc;
    /** Creates a new instance of TableFactory */
    public TableFactory(boolean all,boolean abc) {
        this.all = all;
        this.abc = abc;
        map = new HashMap<String,Table>();
    }
    
    public TableFactory() {
        this.all = false;
        this.abc = false;
        map = new HashMap<String,Table>();
    }
    
    public Table getTable(XPump pp,String typecode)throws Exception{
        Table t = map.get(typecode);
        if(t==null){
            t = new Table(pp,typecode,all,abc);
            map.put(typecode,t);
        }
        return t;
    }
   
}
