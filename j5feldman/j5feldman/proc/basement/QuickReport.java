/*
 * QuickReport.java
 *
 * Created on 31 Август 2005 г., 10:37
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package j5feldman.proc.basement;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import java.io.*;
/**
 *
 * @author Jacob Feldman
 */
public class QuickReport extends Proc{
    
    /** Creates a new instance of QuickReport */
    public QuickReport() {
    }
    
    public QuickReport(XPump pp) {
        this.pp = pp;
    }
       
    public String header(List<String> arc,int n){
        String s="<!---header start---><table border=1 bgcolor=white><tr><td align=right>#</td>";
        for(int i=n;i<arc.size();i++){
            s+="<td><b>"+arc.get(i)+"</b></td>";
        }
        s+="</tr>";
        return s;
    }
    String footer(){return "</table><!-- footer end -->";}
    String line(List<String> ar,int n,int count){
        String s = "<tr><td align=right>"+count+"</td>";
        for(int i=n;i<ar.size();i++){
            s+="<td>"+ar.get(i)+"</td>";
        }
        s+="</tr>";
        return s;
    }
    boolean theSameTable(List<String> ar,List<String> ar2,int n){
        if(n==0)return true;
        for(int i=0;i<n;i++){
            String s = ar.get(i);
            if(s==null)s="";
            String s2= ar2.get(i);
            if(s2==null)s2="";
            if(!s.equals(s2))return false;
        }
        return true;
    }
    String keysOnTheTop(List<String> ar,List<String> arc,int n){
        if(n==0)return "";
        String s="<hr><!--ar2head start--><table border=0>";
        for(int i=0;i<n;i++){
            String sa = ar.get(i);
            String sc = arc.get(i);
            s += "<tr><td align=right>"+sc+"</td><td>=</td><td align=left>"+sa+"</td></tr>";
        }
        s+="</table><!--ar2head end-->";
        return s;
    }
    
    void ppRS(Result r,String q)throws Exception{
        try{
            pp.select(r,39,q);
        }catch(Exception e){
            throw new Exception("::"+q+"::"+e.getMessage());
        }//finally{r.close();}
    }
    
//    public String familyTab(int oid)throws Exception{
//        String s = "";
//        String q = "select * from object";
//        return s;
//    }   
    
public String packNcut(String q,int nn)throws Exception{
    return packNcut(q,nn,true);
}
    
    public String packNcut(String q,int nn,boolean withID)throws Exception{
        int count = 0;
        int n = nn>0?nn:0;
        List<String> arc=new ArrayList<String>(),//cols
                ars=new ArrayList<String>(),//strings
                olds;//previous table
        String s = "";
        Result r = new Result();
        try{
            ppRS(r,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            for(int i=0;i<N;i++){
                String colname = md.getColumnName(i+1);
                if(!withID&&colname.equals("id"))continue;
                arc.add(colname);
                ars.add("");//previous = olds
            }
            boolean first = true;
            int NN=0;
            while(r.rs.next()){//lines
                NN++;
                if(NN>200)break;
                olds = ars;
                ars = new ArrayList<String>();
                for(int i=0;i<N;i++){//cols
                    String x = r.rs.getString(i+1);
                    if(x==null)x="&nbsp;";
                    else if(x.equals("null"))x="&nbsp;";
                    else if(x.equals("false"))x="-";
                    else if(x.equals("true"))x="+";
                    ars.add(x);
                }
                if(!theSameTable(ars,olds,n)){
                    count=0;
                    if(!first)s+=footer();
                    if(nn==0)s+="<hr><table border=1><!-- n==0 ---->";
                    s+=keysOnTheTop(ars,arc,n)+header(arc,n);
                    first=false;
                }else if(first){
                    count=0;
                    s+="<hr><!--- first ---->"+header(arc,n);
                    first=false;
                }
                s+=line(ars,n,++count);
            }
            
            if(NN>0)s+="</table><!-- getQN --->";
        }finally{r.close();}
        return s;
    }
    
}
