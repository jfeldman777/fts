/*
 * Substitution.java
 *
 * Created on 10 Август 2006 г., 8:21
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
import java.sql.*;
import java.io.*;
/**
 *
 * @author Jacob Feldman
 */
public class Substitution {
    XPump pp=null;
    /** Creates a new instance of Substitution */
    public Substitution(XPump pp) {
        this.pp = pp;
    }
       String treeDownList(String val)throws Exception{
        if(pp==null || val.charAt(val.length()-1)!='|')return val;
        String v = val.substring(0,val.length()-1);
        int j = 0;
        try{j=Integer.parseInt(v);}catch(Exception e){return val;}
        List<Integer> LL = new ArrayList<Integer>();
        LL.add(j);
        for(int i=0;i<LL.size();i++){
            String q = "select id from object where id>0 and " +
                    " id2=0 and parent="+LL.get(i);
            LL.addAll(pp.getNN(q));
            String q2 = "select o.id from object o,object o2 where o.id>0 and " +
                    " o.id=o2.id2 and o2.id>0 and o2.parent="+LL.get(i);
            LL.addAll(pp.getNN(q2));            
        }
        String s = "";
        boolean start = true;
        for(Integer J:LL){
            if(start){start=false;}else{s+=",";}
            s+=J;
        }
        return s;
    }
       
    public String wordSub(String src,String var,String val)throws Exception{
//        String sub = "&lt;"+var+"&gt;";
        String sub = var;        
        int i = src.indexOf(sub);
        if(i<0) return src;
        String S = src.substring(0,i)+val+src.substring(i+sub.length());  
//        System.out.println(S);
        return wordSub(S,var,val);
    }         
    public String substitute(String src,String var,String val)throws Exception{
        String sub = "@"+var+"#";
        int i = src.indexOf(sub);
        if(i<0) return src;
        String S = src.substring(0,i)+treeDownList(val)+src.substring(i+sub.length());
        return substitute(S,var,val);
    }   
    public String substitute(String src,String sub){
        if(sub==null||sub.trim().equals("")) return src;
        int i = src.indexOf("@");
        if(i<0) return src;
        String S = src.substring(0,i)+sub+src.substring(i+1);
        return S;//substitute(S,sub);
    } 
    
    static public SortedMap<String,String> fromString(String par)throws Exception{
        SortedMap<String,String> m = new TreeMap<String,String>();
        return fromString(m,par);
    }
    
    static public SortedMap<String,String> fromString(SortedMap<String,String> m,String par)throws Exception{
        if(par==null||par.equals(""))return m;
        
        int k = par.indexOf("=");
        if(k<0){
            return null;
        }
        StringTokenizer t = new StringTokenizer(par,";");
        while(t.hasMoreTokens()){
            String S = t.nextToken();
            int i = S.indexOf("=");
            if(i>0){
                String var = S.substring(0,i);
                String val = S.substring(i+1);
                m.put(var,val);
            }
        }
        return m;
    }    

    static public SortedMap<String,String> fromString2(String par)throws Exception{
        SortedMap<String,String> m = new TreeMap<String,String>();
        return fromString2(m,par);
    }
    
    static public SortedMap<String,String> fromString2(SortedMap<String,String> m,String par)throws Exception{
        if(par==null||par.equals(""))return m;
        
        int k = par.indexOf("=");
        if(k<0){
            return null;
        }
        StringTokenizer t = new StringTokenizer(par,";");
        while(t.hasMoreTokens()){
            String S = t.nextToken();
            int i = S.indexOf("=");
            if(i>0){
                String var = S.substring(0,i);
                String value = S.substring(i+1);
                m.put(value,var);//////////////////////////////////
            }
        }
        return m;
    }     
    
    public String substituteNN(String Q,String def,String par) throws Exception {//vice versa!
        SortedMap<String,String> m = new TreeMap<String,String>();
        SortedMap<String,String> mdef=fromString(m,def);
        
        String Qd = "";
        if(mdef==null){
            return substitute(Q,def);
        }else Qd = substituteNN(Q,mdef);
        
        SortedMap<String,String> mpar=fromString(m,par);        
        if(mpar==null){
            return substitute(Qd,par);
        }

        return substituteNN(Qd,mpar);
    }
    
    public String substituteNN(String Q,String par) throws Exception {
        SortedMap<String,String> m = new TreeMap<String,String>();
        SortedMap<String,String> mpar=fromString(m,par);        
        return substituteNN(Q,mpar);
    } 
    
   
    public String substituteNN(String Q,SortedMap<String,String> mpar) throws Exception {
        SortedMap<String,String> m = new TreeMap<String,String>();
        String Q2 = new String(Q);
        for(String var:mpar.keySet()){
                Q2 = substitute(Q2,var,mpar.get(var));
        }
        return Q2;
    }    
    
    public String wordSub(String Q,SortedMap<String,String> mpar) throws Exception {
        SortedMap<String,String> m = new TreeMap<String,String>();
        String Q2 = new String(Q);
        for(String var:mpar.keySet()){
            String var2 = "|"+var+"|";
            Q2 = wordSub(Q2,var,mpar.get(var));
        }
        return Q2;
    }   
    
    public static String fromAlt(boolean sh,String name1,String name2,String name12){
        String s = " object "+name1+", object "+name2+" ";
            if(sh) s+=",object "+name12+" ";
        return s;
    }
    public static String fromAlt(boolean sh,String name,String name12){
        String s = " object "+name+" ";
            if(sh) s+=",object "+name12+" ";
        return s;
    }    
    public static String whereAlt(boolean sh,String name1,String name2,String name12){
        String s = " "+name1+".id="+name2+".parent " +
                " and "+name2+".id2=0 and " +name2+".id>0 ";
            if(sh) s = " "+name1+".id="+name12+".parent and  "+
                    name2+".id="+name12+".id2 and "+name12+".id2>0 ";       
        return s;
    }
    
    public static String whereAlt(boolean sh,int parent,String name,String name12){
        String s = " "+name+".parent="+parent+" "+
                " and "+name+".id2=0 and " +name+ ".id>0 ";        
            if(sh) s = " "+name12+".parent="+parent+
                    " and  "+
                    name+".id="+name12+".id2 and "+name12+".id2>0 ";       
        return s;
    }
    
    public static String fromNwhere(boolean sh,String name1,String name2,String name12){
        return fromAlt(sh,name1,name2,name12)+" where "+whereAlt(sh,name1,name2,name12);
    }
    
    public static String fromNwhere(boolean sh,int parent,String name,String name12){
        return fromAlt(sh,name,name12)+" where "+whereAlt(sh,parent,name,name12);
    }    
}
