/*
 * Task.java
 *
 * Created on 17 Август 2004 г., 8:27
 */

package j5feldman.proc;
import j5feldman.*;
import java.util.*;
import java.sql.*;
import j5feldman.proc.basement.Proc;
import j5feldman.proc.basement.IProc;
/**
 *
 * @author  Jacob Feldman / Фельдман Яков Адольфович
 */
public class Task extends Proc implements IProc{
    
    /** Creates a new instance of Task */
    public Task() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        //"p-bad plan, a-all tasks, n-not finished tasks"
        map.put("11","противоречия в планировании");
        map.put("12","все задачи");
        map.put("13","незавершенные задачи");
        
        mapE.put("11","bad planning");
        mapE.put("12","all tasks");
        mapE.put("13","tasks to do");
    }
       
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 11:return "Противоречия в планировании";
                case 12:return "Все задачи";
                case 13:return "НЕзаконченные задачи";            
            }

            case 1:switch(k){
                case 11:return "Bad planning";
                case 12:return "All tasks";
                case 13:return "Tasks to do";            
            }        
        }
        return "";
    }
    
    public String result(String fun,String par) throws Exception {
        this.par = par; 
        int f = Integer.parseInt(fun);
        switch(f){
            case 11:case 12:case 13: break;
            default: return "";
        }
        
        if(f==11)return pResult();
        boolean bn = f==13;
        String s = "";
        List<P2Node> ar = P2Node.allDown(pp,FTree.ObjectTree,id);
        for(P2Node p:ar){
            int j = p.id();
            String q =
 "select o.code,e.start_date,e.end_date,t.fact_end from task t,object o,event e where "+
 " o.id=e.id and t.id=o.id and o.id="+j+(bn?" and not t.ended ":"");
            s+=pp.getTableLine(q);
        }
        switch(pp.iLang){
            case 1:return 
        "Jobs "+(bn?"to do":"- all")+
"<table border=2><tr><td><b>job</b></td>"+
"<td><b>start</b></td><td><b>finish plan</b></td>" +
                "<td><b>finish fact</b></td></tr>"+s+"</table><hr>";
        }
        return 
        "Работы "+(bn?"незаконченные":"все")+
"<table border=2><tr><td><b>работа</b></td>"+
"<td><b>начало</b></td><td><b>окончание план</b></td>" +
                "<td><b>окончание факт</b></td></tr>"+s+"</table><hr>";
        
    }   

    String pResult()throws Exception{
        String s1 = "",s2 = "", s3 = "";
        String code = pp.getS
        ("select t.code from object o,type t where o.type=t.id and o.id="+id);
        if(code.indexOf("task")!=0)
            throw new Exception("code "+code+" must start as .task. ");
        String add = code.substring(4);
        List<P2Node> ar = P2Node.allDown(pp,FTree.ObjectTree,id);
        for(P2Node p:ar){
            int j = p.id();
            String q1 =           
" select ow.code,op.code,ep.start_date, "+
" ok.code,ek.end_date from wait"+add+" w,"+
" object ow,object op,event ep, object ok,event ek where "+
" ep.id=op.id and ow.parent=op.id "+
" and ow.id=w.id and ok.id=ek.id and w.task=ok.id and ek.end_date>ep.start_date "+
" and w.id="+j;
            s1+=pp.getTableLine(q1);
            
            String q2="select op.code,ep.start_date,oc.code,ec.start_date "+
            " from object op,object oc,event ep,event ec where "+
            " ec.start_date<ep.start_date "+
            " and op.id=ep.id and oc.id=ec.id and oc.parent=op.id and op.id="+j;
            
            s2+=pp.getTableLine(q2);
            
            String q3="select op.code,ep.end_date,oc.code,ec.end_date "+
            " from object op,object oc,event ep,event ec where "+
            " ec.end_date>ep.end_date "+
            " and op.id=ep.id and oc.id=ec.id and oc.parent=op.id and op.id="+j; 
            
            s3+=pp.getTableLine(q3);            
        }
        switch(pp.iLang){       
            case 1:return "<b>Bad planning:</b><br>"+
                "bad dependancies"+
                "<table border=2><tr><td><b>it depends</b></td>"+
        "<td><b>who is wainting</b></td><td><b>start plan</b></td>"+
        "<td><b>wait for</b></td><td><b>finish plan</b></td></tr>"+s1+"</table><hr>"+
                "bad start"+
                "<table border=2><tr><td><b>Higher job</b></td>"+
        "<td><b>start plan</b></td><td><b>Lower job</b></td>"+
        "<td><b>start plan</b></td></tr>"+s2+"</table><hr>"+
                "bad finish"+
                "<table border=2><tr><td><b>Higher job</b></td>"+
        "<td><b>finish plan</b></td><td><b>Lower job</b></td>"+
        "<td><b>finish plan</b></td></tr>"+s3+"</table><hr>";        
        }
        return "<b>Противоречия при планировании</b><br>"+
                    "нарушение зависимости"+
                    "<table border=2><tr><td><b>зависимость</b></td>"+
            "<td><b>кто ждет</b></td><td><b>начало план</b></td>"+
            "<td><b>кого ждет</b></td><td><b>окончание план</b></td></tr>"+s1+"</table><hr>"+
                    "неправильное начало"+
                    "<table border=2><tr><td><b>Старшая работа</b></td>"+
            "<td><b>начало план</b></td><td><b>младшая работа</b></td>"+
            "<td><b>начало план</b></td></tr>"+s2+"</table><hr>"+
                    "неправильное окончание"+
                    "<table border=2><tr><td><b>Старшая работа</b></td>"+
            "<td><b>окончание план</b></td><td><b>младшая работа</b></td>"+
            "<td><b>окончание план</b></td></tr>"+s3+"</table><hr>";         
    }
}