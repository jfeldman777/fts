/*
 * Result.java
 *
 * Created on 24 Èþíü 2006 ã., 11:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package j5feldman.proc.basement;
import java.sql.*;
/**
 *
 * @author Administrator
 */
public class Result {
    public ResultSet rs;
    public PreparedStatement stmt;
    /** Creates a new instance of Result */
    public Result() {
    }
    public void close()throws Exception{
        if(stmt!=null)stmt.close();
    }
    
    
}
