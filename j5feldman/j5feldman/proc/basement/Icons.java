/*
 * Icons.java
 *
 * Created on 4 јвгуст 2004 г., 11:38
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Icons extends XUpload{ 
    String typecode;
    String icon; 
    /** Creates a new instance of Icons */
    public Icons(XPump pp,String ppPath,String typecode)throws Exception {
        super(pp,ppPath);
        this.typecode = typecode;
        if(typecode!=null) icon = pp.icon(typecode); 
    } 
    public String show()throws Exception{
        String s = "";
        String s0 = "";
        String s1 = "";
        String name = "";
        String xname = "";    
        List<String> ss = pp.getSS("select distinct icon from type where icon is not null");
        List<String> ss2 = pp.getSS("select code from type where icon is null");
        ss.addAll(ss2);
            File dir = new File(ppPath);
            File[] ff = dir.listFiles();
            for(int i=0;i<ff.length;i++){
                name = ff[i].getName();
                int k = name.lastIndexOf('.');
                if(k<0)continue;
                String ext = name.substring(k+1, name.length());
                if(!ext.equalsIgnoreCase("gif"))continue;
                xname = name.substring(0,k);
                
                s1="<nobr>"+icon(xname)+
                    "<input type=RADIO name=file value='"+xname+"'"+
                (icon.equals(xname)?" CHECKED ":"")+">&nbsp;&nbsp;&nbsp;</nobr>";
                if(ss.contains(xname))
                    s0+=s1;
                else s+=s1;
            }            	
        return s0+"<hr>"+s;
    }
    public String showTypes(boolean noabstr)throws Exception{
        String s0 = "";
        String s1 = "";
        String name = "";
        String xname = "";    
        
        String q = "select id,code,name,if(icon is null,code,icon) as more from type where " +
                " not fin and " +
                (noabstr? " not abstr and ":"") +
                " id2=0 and id22=0 order by code ";
        List<P20Node> LL = P20Node.getP21list(pp,q);      

        for(P20Node p:LL){
                    s1="<nobr>"+icon2(p.more(),p.code())+
                    "<input type=RADIO name=inhicon value='"+p.code()+"' title='" +p.code()+
                            "' onclick='javascript: " +
                            " document.forms[0].inh.value=\""+p.code()+"\"; " +
                            " document.forms[0].code.value=\""+p.code()+"\"; " +
                            " document.forms[0].name.value=\""+p.name()+"\" " +                        
                            " ' >&nbsp;&nbsp;&nbsp;</nobr>";    
                    s0+=s1;
            }            	
        return s0;
    } 
    public String showFin()throws Exception{
        String s0 = "";
        String s1 = "";
        String name = "";
        String xname = "";    
        
        String q = "select if(fin,1,0) as id,code,name,if(icon is null,code,icon) as more from type where " +
                " not abstr and " +
                " id2=0 and id22=0 order by fin,code ";
        List<P20Node> LL = P20Node.getP21list(pp,q);      

        for(P20Node p:LL){
                    s1="<nobr>"+icon2(p.more(),p.code())+
                "<input type=CHECKBOX name='" +p.code()+"'"+
                            (p.id()>0?" CHECKED ":" ")+
                            " title='" +p.code()+
                            "' >&nbsp;&nbsp;&nbsp;</nobr>";    
                    s0+=s1;
            }            	
        return s0;
    } 
    
    public void setFin(HttpServletRequest req)throws Exception{
        SortedMap<String,String> map = GuiTree.req2map(req);
        for(String key:map.keySet()){
            String sv = map.get(key);
            boolean f = sv!=null;
            String q = "update type set fin="+(f?"true":"false")+" where code='" + key +"'";
            pp.exec(q);
        }
    }
    
    public String showFree()throws Exception{
        String s = "";
        String name = "";
        String xname = "";    
        List<String> ss = pp.getSS("select distinct icon from type where icon is not null");
        List<String> ss2 = pp.getSS("select code from type where icon is null");
        ss.addAll(ss2);
            File dir = new File(ppPath);
            File[] ff = dir.listFiles();
            for(int i=0;i<ff.length;i++){
                name = ff[i].getName();
                int k = name.lastIndexOf('.');
                if(k<0)continue;
                String ext = name.substring(k+1, name.length());
                if(!ext.equalsIgnoreCase("gif"))continue;
                xname = name.substring(0,k);
                if(ss.contains(xname))continue;
                s+="<nobr>"+icon(xname)+
                    "<input type=RADIO name=file value='"+xname+"'>&nbsp;&nbsp;&nbsp;</nobr>";
            }            	
        return s;
    }
    public String showDobj()throws Exception{
        String s = "";
        List<String> ss = pp.getSS("select code from type where id>0 and id2=0 and id22=0 order by code");     
        for(String typecode:ss){
            if(typecode.equals("xuser"))continue;
            if(typecode.equals("query"))continue;
            if(typecode.equals("qfolder"))continue;
            if(typecode.equals("queryref"))continue;            
            String iconname = pp.getS("select if(icon is null,code,icon) from type where " +
                    " code='"+typecode+"'");  
            String showname = pp.getS("select name from type where code='" +typecode+ "'");     
                    s+="<nobr><a href='javascript: f(\""+typecode+
                        "\")'>"+icon(iconname, typecode+"/"+showname)+
                    "</a>&nbsp;&nbsp;&nbsp;</nobr>";            
        }            	
        return s;
    }
    public  String showD()throws Exception{
        String s = "";
        String name = "";
        String xname = "";    
        List<String> ss = pp.getSS("select distinct icon from type where icon is not null");
        List<String> ss2 = pp.getSS("select code from type where icon is null");
        ss.addAll(ss2);
            File dir = new File(ppPath);
            File[] ff = dir.listFiles();
            for(int i=0;i<ff.length;i++){
                name = ff[i].getName();
                int k = name.lastIndexOf('.');
                if(k<0)continue;
                String ext = name.substring(k+1, name.length());
                if(!ext.equalsIgnoreCase("gif"))continue;
                xname = name.substring(0,k);

                s+="<nobr><a href='javascript: f(\""+xname+
                        "\")'>"+icon(xname, ss.contains(xname))+
                    "</a>&nbsp;&nbsp;&nbsp;</nobr>";
            }            	
        return s;
    }        
        public String icon(String xname,boolean inuse){
            String s = "<img alt='"+xname+"' "+
                " border=" +(inuse?"2":"0") +
                    " src="+pp.iconpath()+xname+".gif>";
            return s; 
        }

        public static String icon(XPump pp,String typecode)throws Exception{
            String iconname = pp.icon(typecode);
            String s = "<img alt='"+typecode+"' "+
                " border=0 src="+pp.iconpath()+iconname+".gif>";
            return s;
        }  
        
        public String icon(String iconname,String alt){
            String s = "<img alt='"+alt+"' "+
                " border=0 src="+pp.iconpath()+iconname+".gif>";
            return s; 
        }
        
        public String icon(String iconname){
            String s = "<img alt='"+iconname+"' "+
                " border=0 src="+pp.iconpath()+iconname+".gif>";
            return s; 
        }        
        
        public String icon2(String iconname,String alt){
            String s = "<img alt='" +alt+
                    "' border=0 src="+pp.iconpath()+iconname+".gif>";
            return s; 
        }        
        
        public void delete(String name)throws Exception{
            File f = new File(ppPath+name+".gif");
            f.delete();
        }
        public void rename(String name,String name2)throws Exception{
            File f = new File(ppPath+name+".gif");
            File f2 = new File(ppPath+name2+".gif");
            if(f2.exists())throw new Exception(f2.getAbsolutePath()+" exists (new item)");
            f.renameTo(f2);
        }     
}
