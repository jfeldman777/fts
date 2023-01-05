/*
 * Group.java
 *
 * Created on 22 Февраль 2007 г., 13:20
 * Auhor J.Feldman
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class Group extends Proc implements IProc{
    /** Creates a new instance of Group */
    public Group() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);      
        map.put("39","Все научные работы");
        
        mapE.put("39","Researches");
    }
    
    public String parDesc(int id,int k){
        switch(pp.iLang){ 
            case 0:switch(k){
                case 39:return "Все научные работы";
            }
            case 1:switch(k){
                case 39:return "Researches";
            }            
        }
        
        return "";
    }
    public String result(String fun,String par) throws Exception {
        this.par = par; 
        int f = Integer.parseInt(fun);
        switch(f){
            case 39:break;
            default:return "";
        }
        
        String s = "";
        QuickReport qr = new QuickReport(pp);
        int nn = 0;
        s+=  qr.packNcut(q(id,false),nn)+"<hr><!--- 1-2 --->";
        
        s+=  qr.packNcut(q(id,true),nn);
        
        return s;
    }    
    
    
    String q(int parent,boolean sh){
         String s2=
                "select " +
                "s.first_name \"Имя\"," +
                 "s.last_name \"фамилия\"," +
                "r.title \"заглавие\"," +
                 "e.start_date \"дата начала\"," +
                 " t.last_name \"учитель\"";
         switch(pp.iLang){
             case 1:s2=
                "select " +
                "s.first_name,s.last_name," +
                "r.title,e.start_date," +
                     " t.last_name teacher ";
         }
         String s3=" from " +
                 " object st, person s, person t,research r, event e," +                 
//                 Substitution.fromAlt(sh,"pf","rs","rs2")+  
//                " where " +
//                  Substitution.whereAlt(sh,"pf","rs","rs2")+                
                Substitution.fromNwhere(sh,"pf","rs","rs2")+                 
                 
                " and st.parent="+parent+
                " and pf.parent=st.id" +                
                " and rs.id=r.id " +
                " and st.id=s.id " +
                " and r.id=e.id " +
                " and t.id=r.teacher " +
                " order by r.title "
                 ;  
         return s2+s3;
    }
    
}
