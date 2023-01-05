/*
 * Anktest.java
 *
 * Created on 5 Октябрь 2006 г., 8:33
 * Auhor J.Feldman
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import j5feldman.proc.basement.Proc;
import j5feldman.proc.basement.IProc;
/**
 *
 * @author Jacob Feldman
 */
public class Anktest  extends Proc implements IProc{
    
    /** Creates a new instance of Anktest */
    public Anktest() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);      
        map.put("1","Все об этом бланке");
        mapE.put("1","All about this page");
    }
    
    public String parDesc(int id,int k){
        switch(pp.iLang){ 
            case 0:switch(k){
                case 1:return "Все об этом бланке: %, ошибки";
            }
            case 1:switch(k){
                case 1:return "All about the page^ %, errors";
            }            
        }
        
        return "";
    }
    public String result(String fun,String par) throws Exception {
        this.par = par; 
        int f = Integer.parseInt(fun);
        if(f!=1)return "";
        
        Testfolder TF = new Testfolder();
        TF.init(pp,-1);
        return TF.resOne(""+id);    
    }
}
