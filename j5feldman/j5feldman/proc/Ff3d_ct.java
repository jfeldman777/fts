/*
 * Ff3d_ct.java
 *
 * Created on 1 Декабрь 2006 г., 8:06
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
public class Ff3d_ct extends Proc implements IProc{
    private boolean bestScore;
    /**
     * Creates a new instance of Ff3d_ct
     */
    public Ff3d_ct() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);      
        map.put("21","Лучший балл");
        map.put("22","Лучшее время");
        
        mapE.put("21","Best score total");
        mapE.put("22","Best time total");
    }
    public String parDesc(int id,int k){
        switch(pp.iLang){ 
            case 0:switch(k){
                case 21:return "Лучший балл";
                case 22:return "Лучшее время";
            }
            case 1:switch(k){
                case 21:return "Best score total";
                case 22:return "Best time total";
            }            
        }
        
        return "";
    } 
    
    public String result(String fun,String par) throws Exception {
        this.par = par; 
        int f = Integer.parseInt(fun);
        switch(f){
            case 21:case 22:break;
            default:return "";
        }
        bestScore = f==21;
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
                " o.code \"код\"," +
                " o.name \"имя\"," +
                " f.score_total \"общий балл\"," +
                " f.time_total \"общее время\"";
         switch(pp.iLang){
             case 1:s2=
                "select " +
                " o.code," +
                " o.name," +
                " f.score_total," +
                " f.time_total ";
         }
         
        String s3 = " from ff3d f, " +
                
//                //" object o, object o2" +                
//                Substitution.fromAlt(sh,"o","o2")+                    
//                " where " +    
//                //" o2.id2=o.id and o2.id2>0 and o2.parent ="+parent+               
//                Substitution.whereAlt(sh,parent,"o","o2")+      
                
                Substitution.fromNwhere(sh,parent,"o","o2")+
                " and o.id=f.id " +    
        " order by " +
                (bestScore? " f.score_total desc, f.time_total ":
                    " f.time_total, f.score_total desc ");
         
         return s2+s3;
    } 
}
