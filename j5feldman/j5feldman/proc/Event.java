/*
 * Event.java
 *
 * Created on 17 Август 2004 г., 8:26
 */

package j5feldman.proc;
import j5feldman.*;
import java.util.*;
import j5feldman.proc.calendar.*;
import j5feldman.proc.basement.Proc;
import j5feldman.proc.basement.IProc;
//import java.sql.*;
/**
 *
 * @author  Jacob Feldman / Фельдман Яков Адольфович
 */
public class Event extends Proc implements IProc{
    int WIDTH = 50;
//    char t;
    /** Creates a new instance of Event */
    public Event() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);      
        map.put("1","Дни ");
        map.put("2","Недели ");
        
        mapE.put("1","Days ");
        mapE.put("2","Weeks ");
    }
    
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 1:return "Календарь по дням (число дней от сего дня вперед)";
                case 2:return "Календарь по неделям (число недель вперед)";
            }
            case 1:switch(k){
                case 1:return "Календарь по дням (число дней от сего дня вперед)";
                case 2:return "Календарь по неделям (число недель вперед)";
            }        
        }
        return "";
    }    
    public String result(String fun,String par) throws Exception {
        this.par = par; 
        if(!fun.equals("1")&&!fun.equals("2"))return ""; 
        boolean D = Integer.parseInt(fun)==1;
        
        WIDTH = Integer.parseInt(par);
        GregorianCalendar c = new GregorianCalendar();
        EvCalendar ev = new EvCalendar(WIDTH,D);
        
        String s = ev.calendar(); 
        List<P2Node> ar = P2Node.allDown(pp,FTree.ObjectTree,id);
        for(P2Node p:ar){
            int j = p.id();
            String q = "select count(*) from event where id="+j;

            if(pp.getN(q)>0)
            try{
                s+="<tr><td>"+p.code()+"</td>";
                java.sql.Date d_start = 
         pp.getD("select start_date from event where id="+j);
                java.util.Date start = new java.util.Date(d_start.getTime());
                
                java.sql.Date d_end = 
         pp.getD("select end_date from event where id="+j);
                java.util.Date end = new java.util.Date(d_end.getTime()); 
                String ss = "";
                ICounter counter;
                if(D) counter = new DayCounter();
                else counter = new WeekCounter();
                
                
                for(int i2=0;i2<WIDTH;i2++){
                    GregorianCalendar gstart = new GregorianCalendar();
                    gstart.setTime(start);
                    GregorianCalendar gend = new GregorianCalendar();
                    gend.setTime(end);
                    gend.add(GregorianCalendar.DAY_OF_YEAR,1);
                    if(i2>0)ss+=counter.td(); 
                    ss+=counter.bar(gstart,gend); 
                    counter.step();
                }
                
                s+="<td>"+ss+"</td>";
            }catch(Exception e){
                    pp.log(q+":"+e.getMessage());  
            }//undefined dates
            finally
                {s+="</tr>";}
        }

        return "<font face=monospace><table border=1>"+s+"</table></font><hr>";
        
    }  

}
