/*
 * PhantomParent.java
 *
 * Created on 12 ќкт€брь 2006 г., 10:32
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
import java.sql.*;
/**
 *
 * @author Jacob Feldman
 */
public class PhantomParent {
    XPump pp;
    GuiTree.GuiNode parent;
    /** Creates a new instance of PhantomParent */
    public PhantomParent(XPump pp,GuiTree.GuiNode parent) {
        this.pp = pp;
        this.parent = parent;
    }
    
    public String qPhantoms(String tab,String col,boolean blind){
       String q = " select " +(blind?"true":"o.abstr") +
               " abstr,o.id,o.code,o.name,o.parent,o.id2,t.id type,t.abc,o.fin," +
                  " t.code typecode, if(t.icon is null or t.icon='',t.code,t.icon) icon " +
                  " ,o.last_update from object o,type t, " +tab+ " b "+
          " where o.id>0 and o.id=b.id and b." +col+"="+parent.idOrigin()+
               " and t.id=o.type and o.id2=0 order by o.code"; 
        return q;
    }
    public static String qnPhantoms(String tab,String col,int ID){
       String q = " select count(o.id) from object o,type t, " +tab+ " b "+
          " where o.id>0 and o.id=b.id and b." +col+"="+ID+
               " and t.id=o.type and o.id2=0 ";
        return q;
    }
            
    public List<TabCol> getTcList()throws Exception{
        return getTcList("select tab,col,blind from refhh where ref='"+parent.typeCode()+"'");
    }
    
    public static List<TabCol> getTcList(XPump pp,String q)throws Exception{
        List<TabCol> xList=new ArrayList<TabCol>();
        TabCol tc = null;
        Result r=new Result();
        try{
            pp.select(r,22,q);
            while(r.rs.next()){
                tc = getTC(r.rs);
                xList.add(tc);
            }
        } finally{r.close();}
        return xList;
    }
    
    private List<TabCol> getTcList(String q)throws Exception{
        List<TabCol> xList=new ArrayList<TabCol>();
        TabCol tc = null;
        Result r=new Result();
        try{
            pp.select(r,22,q);
            while(r.rs.next()){
                tc = getTC(r.rs);
                xList.add(tc);
            }
        } finally{r.close();}
        return xList;
    }

    static TabCol getTC(ResultSet rs)throws Exception{
        String tab=rs.getString("tab");
        String col=rs.getString("col");
        boolean blind=rs.getBoolean("blind");
        return new TabCol(tab,col,blind);
    }    
        
    static public boolean hasPhantomChildren(XPump pp,String ref)throws Exception{
        return pp.getN("select count(*) from refhh where ref='"+ref+"' ")>0;
    } 
    
    static public boolean hasPhantomChildren(XPump pp,TabCol tc)throws Exception{
        return hasPhantomChildren(pp,tc.tab,tc.col);
    }
    
    static public boolean hasPhantomChildren(XPump pp,TabCol tc,int ID)throws Exception{
        return hasPhantomChildren(pp,tc.tab,tc.col,ID);
    }
        
    static public boolean hasPhantomChildren(XPump pp,String tab,String col,int ID)throws Exception{
        return pp.getN(qnPhantoms(tab,col,ID))>0;
    }
        
    static public boolean hasPhantomChildren(XPump pp,String tab,String col)throws Exception{
        return pp.getN("select count(*) from refhh where tab='"+tab+"' and col='"+col+"'")>0;
    }
    
    public static boolean hasPhantomChildren(XPump pp, String ref,int ID)throws Exception{
        List<TabCol> tcList =  getTcList(pp,"select tab,col,blind from refhh where ref='"+ref+"'");       
        for(TabCol tc:tcList){
            if(hasPhantomChildren(pp,tc,ID))return true;
        }

        return false;
    }   
    
    public static class TabCol{
        public TabCol(String tab,String col,boolean blind){
            this.tab = tab;
            this.col = col;
            this.blind = blind;
//            this.blind=true;//
        }
        public String tab;
        public String col;
        public boolean blind;
    }
    
    static public String myHs(XPump pp,int id) throws Exception {
        String s = "<table border=1>";
        List<Integer> TL = pp.getNN("select distinct p.type from object o,object p where" +
                " p.id=o.parent and o.id2>0 and o.id2="+id);
        for(Integer t:TL){
          PNode ptp = pp.getNode("select o.id, o.code, o.name, o.parent, o.id2, o.type, t.code typecode,o.abstr," +
                  " false abc,o.fin " +
                  " from object o,type t where t.id=o.type and o.type="+t);
          List<PNode> L = pp.getNodeS
                  ("select p.id, p.code, p.name, p.parent, p.id2, p.type, t.code typecode,p.abstr, " +
                  " false abc, p.fin " +
                  " from object o,object p,type t where t.id=p.type and o.parent=p.id and " +
                  " o.id2>0 and o.id2="+id+" and p.type="+t+" order by p.code,p.name ");
          boolean start = true;        
          for(PNode p:L){
                if(start){
                    s+="<tr><td>"+p.typeCodeName(pp)+"</td><td>";
                    start=false;
                }
              
                s+="<a title='" +p.name()+
                        "'>"+p.code()+"</a>; ";
         }
          s+="</td></tr>";
        }
        
        return s+="</table>";
    }
}
