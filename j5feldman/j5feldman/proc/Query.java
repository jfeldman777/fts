/*
 * Query.java
 *
 * Created on 6 Апрель 2004 г., 15:11
 */

package j5feldman.proc;
import j5feldman.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author  Jacob Feldman / Фельдман Яков Адольфович
 */
public class Query extends QuickReport implements IProc{  
    String par2 = "";
    String table(){
        return " query ";
    }
    final static String typecode = "query";
    String desc;
    /** Creates a new instance of Query */
    public Query(){}
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        desc=pp.getS("select pardesc from" +
                table()+//" query " +
                "where id="+id)+"@"+
                 pp.getS("select params from" +
                table()+//" query " +
                "where id="+id);
        map.put("99",desc);
        mapE.put("99",desc);       
    }
 
   
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 99:return ("Выполняем запрос: " + desc);
            }
            case 1:switch(k){
                case 99:return ("Execute query: " + desc);
            }        
        }
        return "";
    }

    String getSql()throws Exception{
        String q = "select body from" +
                table()+//" query " +
                "where id="+id;//ID;

        String qq = pp.getS(q);  
        return qq;
    }
  

//    public String description() {
//        return "@-one substitution, =; - many for @#";
//          =...|; for tree down ids(,,,)
//    }    


    
    public String result(String fun,String par) throws Exception {
        if(!fun.equals("99"))return "";
        par2=pp.getS("select params from " +
                    table()+//"query" +
                    " where id="+id);
        String sql = getSql();
        Substitution sub = new Substitution(pp);
        String Qx = sub.substituteNN(sql,par,par2);
        String q = "select headlevel from " +
                table()+//" query " +
                " where id="+id;//keys to cut 
        int N = pp.getN(q); 

        return packNcut(Qx,N)+"<hr>"+Qx+"<hr>";
    }
}