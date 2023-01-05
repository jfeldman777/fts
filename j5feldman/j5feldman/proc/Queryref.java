/*
 * Queryref.java
 *
 * Created on 14 Èþíü 2006 ã., 15:17
 * Auhor J.Feldman
 */

package j5feldman.proc;
import j5feldman.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author Jacob Feldman
 */
public class Queryref extends Query implements IProc{
    
    /** Creates a new instance of Queryref */
    public Queryref() {
    }
    String table(){
        return " queryref ";
    }
    final static String typecode = "queryref";
    
    

    String getSql()throws Exception{
        String q = "select body from " +
                " query q, queryref r " +
                " where q.id=r.rbody and r.id="+id;//ID;

        String qq = pp.getS(q);  
        return qq;
    }    
}
