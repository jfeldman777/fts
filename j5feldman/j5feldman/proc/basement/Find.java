/*
 * Find.java
 *
 * Created on 8 ƒекабрь 2004 г., 8:45
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Find {
    FTree ft;
    XPump pp;
    /** Creates a new instance of Find */
    public Find(XPump pp,FTree ft) {
        this.pp = pp;
        this.ft = ft;
    }
    public String find(String crit)throws Exception{
        String s="";
        String q = "select id,code,name,"+ft.parentName()+
                " parent ," +ft.abstr()+
                " ,0 id2 from "+ft.tabName()+" where "+crit;
        List<P2Node> p2s = P2Node.getP2NodeS(pp,q);
        for(P2Node p2:p2s){
            s+="<tr><td>"+
                    P2Node.list2path(pp,ft,p2.id())+//xo.pathUpCode()+
                    "</td>" +
                    "<td>"+
                    "<a href='root.jsp?id="+ft.T()+p2.id()+"' target=left>"+
                    "<img src=/feldman-root/style/"+ft.abr()+"/here.gif border=0>"+
                    "</a></td>" +
                    "<td>"+
                    "<a href='oldroot.jsp' target=left>"+
                    "<img src=/feldman-root/style/"+ft.abr()+"/back.gif border=0>"+
                    "</a></td>" +
                    
                    "</tr>";
        }
        return s;
    }
    public String find2(String crit)throws Exception{
        String s="";
        String q = "select s.id,o.code,o.name,s."+ft.parentName()+
                " parent " +
                ",0 id2 "+
                " from "+ft.tabName()+" o,"+ft.tabName()+
                " s where 0<s."+ft.id2Name()+
                " and o.id=s."+ft.id2Name()+" and "+crit;
        List<P2Node> p2s = P2Node.getP2NodeS(pp,q);
        for(P2Node p2:p2s){
            s+="<tr><td>"+
                    P2Node.list2path(pp,ft,p2.id())+//xo.pathUpCode()+
                    "</td><td>"+
                    "<a href='root.jsp?id="+ft.T()+p2.id()+
                    "' target=left>"+
                    "<img src=/feldman-root/style/"+ft.abr()+
                    "/here.gif border=0>"+
                    "</a></td></tr>";
        }
        return s;
    }
    
    public String find(String crit,boolean shortcuts)throws Exception{
        return shortcuts?find2(crit):find(crit);
    }
    
    public String cleanMe(String sql){
        String s = "";
        StringTokenizer t = new StringTokenizer(sql,"\n");
        while(t.hasMoreTokens()){
            String sx = t.nextToken();
            int i = sx.indexOf("//");
            if(i<0)s+=sx; else s+=sx.substring(0,i);
        }
        return s;
    }
    
    public String findMe(String tabname,String crit)throws Exception{
        String s="";
        String q = "select o.id,o.code,o.name,o.parent ,o.abstr,o.id2,0 as abc,o.fin from" +
                " object o, "+tabname+" t where o.id=t.id and "+crit;
        List<P2Node> p2s = P2Node.getP2NodeS(pp,q);
        for(P2Node p2:p2s){
            s+="<tr><td>"+
                    P2Node.list2path(pp,ft,p2.id())+//xo.pathUpCode()+
                    "</td>" +
                    "<td>"+
                    "<a href='root.jsp?id="+ft.T()+p2.id()+"' target=left>"+
                    "<img src=/feldman-root/style/"+ft.abr()+"/here.gif border=0>"+
                    "</a></td>" +
                    "<td>"+
                    "<a href='oldroot.jsp' target=left>"+
                    "<img src=/feldman-root/style/"+ft.abr()+"/back.gif border=0>"+
                    "</a></td>" +
                    
                    "</tr>";
        }
        return s;
    }
}
