/*
 * Qfolder.java
 *
 * Created on 15 Ќо€брь 2004 г., 8:14
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Qfolder extends Proc  implements IProc{

    /** Creates a new instance of Qfolder */
    public Qfolder() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        map.put("88","ѕакет запросов");
        
        mapE.put("88","Batch of queries");
        bForce.add("880");           
    }
    
    
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 88:return "–езультаты выполнени€ всех запросов папки " +
                " (укажите один параметр дл€ подстановки во все запросы," +
                " например: ID объекта, который мы анализируем)";
            }
            case 1:switch(k){
                case 88:return "All queries in the batch will be executed" +
                " (you can set one parameter for all the queries," +
                " like ID of object to analyse)";
            }        
        }
        return "";
    }
    
    public String result(String fun,String par) throws Exception {
        if(!fun.equals("88"))return "";        
        this.par = par; 
        String q = "select o.id,o.code,o.name,q.body " +
        " from query q," +
                
//                " object o where o.parent="+id+                
                Substitution.fromNwhere(false,id,"o","o2")+
                
                " and o.id=q.id " +
        " order by o.code ";
        List<Box> ar = q2ar(q);
        
        q = "select o2.id,o2.code,o2.name,q.body from " +
        " query q, " +
                
//                " object o,object o2 where " +
//        " o.id2>0 and o2.id=o.id2 and o.parent="+id+
                Substitution.fromNwhere(true,id,"o2","o")+
                
                " and o.id=q.id " +
        " order by o.code ";
        ar.addAll(q2ar(q));
        
        String s = "";
        for(int i=0;i<ar.size();i++){
            Box b = (Box)ar.get(i);
            int r = pp.getN(b.body);
            s+="<tr><td>"+b.code+"</td><td>"+b.name+
                "</td><td>"+r+"</td></tr>";
        }
        return "<table border=2>"+s+"</table>";
    } 

    List<Box> q2ar(String q)throws Exception{
        Substitution sub = new Substitution(pp);
        List<Box> ar = new ArrayList<Box>();        
        Result r=new Result();
        String c="",n="",b="";
        int i;
        try{
            pp.select(r,45,q);
            while(r.rs.next()){
                i = r.rs.getInt("id");
                c = r.rs.getString("code");
                n = r.rs.getString("name");
                b = r.rs.getString("body");
                ar.add(new Box(i,c,n,sub.substitute(b,par)));
            }      
        }finally{r.close();}
        return ar;
    }
    
    
    class Box{
        int id;
        String code;
        String name;
        String body;
        Box(int id,String code,String name,String body){
            this.id=id;
            this.code=code;
            this.name=name;
            this.body = body;
        }
    }
    
    public static String qListDesc(XPump pp,int id)throws Exception{
        String s = "";
        List<Integer> II = pp.getNN("select id from object where parent="+id+" order by code ");
        for(Integer k:II){
            String Lid = pp.getS("select id from object where id="+k);
            String Lcode = pp.getS("select code from object where id="+k);
            String Lname = pp.getS("select name from object where id="+k);
            s+="<tr><td><a href='bottom.jsp?id="+Lid+"&k=99' target=2>"+Lcode+
                    "</a></td><td>"+Lname+
                    "</td></tr>";
        }        
        
        
        return s;
    }
    
    
}
