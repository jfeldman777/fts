/*
 * P20Node.java
 *
 * Created on 2 Март 2005 г., 10:15
 */

package j5feldman;
import j5feldman.proc.basement.*;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
/**
 *
 * @author Jacob Feldman
 */
public class P20Node  implements Comparable{
    public int id() {
        return id;
    }
    String path;
    protected int id=-1;    
    protected String code=null;
    protected String name=null;
    protected String more=null;
    /** Creates a new instance of P20Node */
    P20Node(){}
    public P20Node(int id,String code,String name,String more) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.more = more;
    }
    public P20Node(String code,String name) {
        this.code = code;
        this.name = name;
    }
    public P20Node(int id,String code,String name) {
        this.id=id;
        this.code = code;
        this.name = name;
    }    
    public String code() {
        return code;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public String more(){
        return more;
    }
    
    public String name() {
        return name;
    }
    public String nameRcode(){
        return (name==null||name.equals(""))?code:name;
    } 
    public String codeName(){
        return code + (name==null||name.equals("")?"":"("+name+")");
    }    
    public String codeNameUnder(){
        if(id==0)return "?";//name();
        return "<a href='object2.jsp?id="+id+"' title='go inside'>"+codeName()+"</a>";
    }
    
    public int compareTo(Object o) {
        return code().compareTo(((P20Node)o).code());
    } 
    public static List<P20Node> getP20type(XPump pp,int type)throws Exception{
        String q = "select id,code,name from object where type="+type+" order by code ";
        return getP20list(pp,q);
    }    
    static P20Node getP20(XPump pp,String q)throws Exception{
        P20Node P = null;
        Result r=new Result();
        try{
            pp.select(r,36,q);
            r.rs.next();
            int id = r.rs.getInt("id");
            String code=r.rs.getString("code");
            String name=r.rs.getString("name");
            P = new P20Node(id,code,name);
        }finally{r.close();}
        return P;
    }
    static P20Node getParent(XPump pp,int id)throws Exception{
        String q = "select p.id,p.code,p.name from object o,object p " +
                " where p.id=o.parent and o.id=" +id;
        return getP20(pp,q);
    } 
    static P20Node getP20(XPump pp,int id)throws Exception{
        String q = "select id,code,name from object where id=" +id;
        return getP20(pp,q);
    } 
    
    public static List<P20Node> getP20children(XPump pp,int parent)throws Exception{
        String q = "select id,code,name from object where id2==0 and parent="+parent+" order by code ";
        return getP20list(pp,q);
    }  
    public static List<P20Node> getP20parent2(XPump pp,int parent)throws Exception{
        String q = "select o.id,o.code,o.name from " +
                Substitution.fromNwhere(true,parent,"o","o2")+
                //" object where id2>0 and parent="+parent+" " +
                " order by o.code ";
        return getP20list(pp,q);
    }  
    public static List<P20Node> getP20list(XPump pp,String q)throws Exception{
        Result r=new Result();
        List<P20Node> list = new ArrayList<P20Node>();
        try{
            pp.select(r,35,q);
            while(r.rs.next()){
                int id=r.rs.getInt("id");
                String code=r.rs.getString("code");
                String name=r.rs.getString("name");
                list.add(new P20Node(id,code,name));
            }
        }finally{r.close();}
        return list;
    }     
    
    public static List<P20Node> getP21list(XPump pp,String q)throws Exception{
        Result r=new Result();
        List<P20Node> list = new ArrayList<P20Node>();
        try{
            pp.select(r,35,q);
            while(r.rs.next()){
                int id=r.rs.getInt("id");
                String code=r.rs.getString("code");
                String name=r.rs.getString("name");
                String more=r.rs.getString("more");
                list.add(new P20Node(id,code,name,more));
            }
        }finally{r.close();}
        return list;
    } 
    
     
}
