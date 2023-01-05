/*
 * UList.java
 *
 * Created on 12 May 2004, 10:49
 */

package j5feldman.proc.basement;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.sql.*;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class UList {
    XPump pp;
    String tab;
    /** Creates a new instance of UList */
    public UList(XPump pp,String tab) {
        this.pp = pp;
        this.tab = tab;
    }
    
    String getHeader(String txt)throws Exception{
        int i = txt.indexOf("<ul>");
        String s = i<0?txt:txt.substring(0,i);
        String s2 = "<textarea  name=head>"+Field.apo(s)+"</textarea>";       
        return s2;
    } 
    String getUitem(String txt,String code)throws Exception{
        String start = "("+code+")";
        String finish = "</li>";
        int i = txt.indexOf(start);
        int j = txt.indexOf(finish,i);
        if(i<0 || j<0) return "";
        String s = Field.apo(txt.substring(i+start.length(),j));
        return s;
    }
    
    public String getForm(String col)throws Exception{
        String tabcol = tab+"::"+col;
        String txt = pp.getS("select rus from translator where tabcol='"+tabcol+"'");
        String form = getHeader(txt)+"<ul>";
        Field f = Field.getField(pp,tab,col);
        String q = "select o.code from object o,"+f.reftab+" t where o.id=t.id and o.id>0 order by o.code ";
        Result r=new Result();
        int id;
        String code;
        try{
            pp.select(r,37,q);
            while(r.rs.next()){
                code = r.rs.getString("code");
                form+=getArea(txt,code);
            }
        }catch(Exception e){System.out.println(q+":"+e.getMessage());        
        }finally{r.close();}
        return form+"</ul>"; 
    }
    
    String getArea(String txt,String code)throws Exception{
        return "<li>"+code+"<textarea name='"+code+
        "'>"+getUitem(txt,code)+"</textarea></li>";
    }
    
    public void edit(String txt,String col)throws Exception{
        String tabcol = tab+"::"+col;
        String[] QQ={
            "delete from translator where tabcol='"+tabcol+"'",
            "insert into translator (rus,tabcol)values('"+txt+"','"+tabcol+"')"     
        };
        pp.exec(QQ);
    } 

}
