/*
 * TabCol.java
 *
 * Created on 9 ƒекабрь 2004 г., 8:17
 */

package j5feldman;
import j5feldman.proc.basement.UList;
import java.util.*;
import java.io.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
abstract public class TabCol implements ITabCol{
    Table tab;
    protected String name;
    protected String tabcol; 
    protected XPump pp;
    protected String tabname;
    /** Creates a new instance of TabCol */
    public TabCol(XPump pp,Table tab,String name) {
        this.pp = pp;
        this.tab=tab;
        this.name=name;
        tabcol=tab.name+"::"+name;        
        this.tabname = tab.name;
    }
    public TabCol(XPump pp,String tabname,String name) {
        this.pp = pp;
        this.name=name;
        tabcol=tabname+"::"+name;        
        this.tabname = tabname;
    }

    public boolean isShadow()throws Exception{
       return pp.isShadow(tabname,name);//false;//tabcol.equals("object::vho");
    }
    public boolean isHidden(){
        return 
        name.equals("id")||
        tabcol.equals("object::id2")||
        tabcol.equals("object::type")||
        tabcol.equals("object::parent")||
        tabcol.equals("object::vho")||
        tabcol.equals("object::abstr")||                
        tabcol.equals("object::creation_date")||
        tabcol.equals("object::fin")||   
                tabcol.equals("type::fin")||   
                tabcol.equals("xuser::toolbar")|| 
                tabcol.equals("xuser::buttons")|| 
                tabcol.equals("xuser::arrows")|| 
        tabcol.equals("object::last_update");
    }    
    boolean isOcode(){
        return tabcol.equals("object::code");
    }
    
    boolean isSystem(){
        return tabcol.equals("object::code")||
                    tabcol.equals("object::name")||
                    tabcol.equals("xuser::pwd")||
                    tabcol.equals("xuser::hello")||
//                    tabcol.equals("event::start_date")||
//                    tabcol.equals("event::end_date")|| 
//                    tabcol.startsWith("task")&&name.equals("fact_end")||
//                    tabcol.startsWith("task")&&name.equals("ended")||
//                    tabcol.startsWith("wait_")&&name.equals("task")||
                    tabcol.equals("query::pardesc")||
                    tabcol.equals("query::headlevel")||                
                    tabcol.equals("query::body");  
    }
    public boolean isKey(){
        return false;
    }

    abstract String c();
    public static String getRus(XPump pp,String tabname, String colname) throws Exception{
        String tc = tabname+"::"+colname;
        String q = "select rus from translator where tabcol='"+tc+"'";
        String s = pp.getS(q);
        if(s.equals(""))s=tc;
        return s;
    }  
 
    public static void setRus(XPump pp,String tabname,String colname,String val)throws Exception{
        if(val==null||val.equals(""))return;
        String tc = tabname+"::"+colname;
        String[] QQ={
            "delete from translator where tabcol='"+tc+"'",
            "insert into translator (rus,tabcol)values('"+val.trim()+"','"+tc+"')"
        };
        pp.exec(QQ);
    }  
    public static String getRus(XPump pp,String tabcol) throws Exception{
        String q = "select rus from translator where tabcol='"+tabcol+"'";
        String s = pp.getS(q);
        if(s.equals(""))s=tabcol;
        return s;
    }  
    
    public String getRus() throws Exception{
        String q = "select rus from translator where tabcol='"+tabcol+"'";
        String s = pp.getS(q);
        if(s.equals(""))s=tabcol;
        return s;
    }  
 
    public void setRus(String val)throws Exception{
        if(val==null||val.equals(""))return;
        String[] QQ={
            "delete from translator where tabcol='"+tabcol+"'",
            "insert into translator (rus,tabcol)values('"+val.trim()+"','"+tabcol+"')"
        };
        pp.exec(QQ);
    } 
    public void  setUnique(boolean b)throws Exception{
        throw new Exception("no unique for doc/pic");
    }
    public boolean getUnique() throws Exception{
        throw new Exception("no unique for doc/pic");
    }    
    boolean myOwn(GuiTree.GuiNode gn)throws Exception{
        boolean bf =  !gn.isPseudo()
                &&!isAuto()
                &&!isHidden()
                &&(!gn.isShadow()||isShadow());
        boolean cf = gn.num()==0 && tabcol.equals("object::code");
        if(gn.getFT()==FTree.TypeTree) return isOcode()||pp.meid==0&&!isSystem();
        if(gn.getFT()==FTree.ObjectTree) return !cf && bf;                
        return false;
    }

    public boolean readOnly(GuiTree.GuiNode gn){
        return gn.num()==0 && tabcol.equals("object::code");
    }
    
    String tdE(GuiTree.GuiNode gn)throws Exception{
        return tdE(gn,!myOwn(gn));
    }    
    String tdE(GuiTree.GuiNode gn,boolean noe)throws Exception{
        if(noe)return "";
        if(!pp.notDemo)return "";        
        if(pp.mTree)return "";
        String s = "";
        if(myOwn(gn)){
            s+="</td><td><a href='line.jsp?id=a"+gn.num()
            +"&tab="+tabname+"&col="+name+"&tctype="+c()+
                    "' title='" +tabcol+
                    "'>E</a>";
        }
        return s;
    }
    public boolean keyCol() throws Exception {
        return false;
    }
    public void setKey()throws Exception{}
    public boolean isBoolean()throws Exception{return false;}    
    public String baseForm() throws Exception {
        throw new Exception("no base value for files");
    }
    public String baseForm(boolean b) throws Exception {
        throw new Exception("no base value for files");
    }
    public String name(){
        return name;
    }
    
    abstract public String fvalue(GuiTree.GuiNode gn, String val,boolean sh,boolean ee)throws Exception;
    abstract public String fvalue(GuiTree.GuiNode gn, String val,boolean sh)throws Exception;
    public String fvalue(int oid, String val)throws Exception{return "";}
    
    abstract public String ftype(GuiTree.GuiNode gn,boolean noe)throws Exception;
    public void setValue(String val)throws Exception{}
    public void setValue(int id,String val)throws Exception{}    
    public String value(){return "";}

    public String baseValue(String val) throws Exception{
        return "";
    }
    public String typeEditForm()throws Exception{
        if(isHidden())return "";
        String s = "";//"<tr><td>";
        s+=tabcol;
        s+="</td><td>";
        String q = "select rus from translator where tabcol='"+tabcol+"'";
        s+="<input type=text name="+tabcol+" value='"+Field.apo(pp.getS(q))+"'>";
        s+="</td><td>"+
        (isSystem()?"":"<a href='drop.jsp?tabcol="+tabcol+"&tctype="+c()+
        "'><img src='/feldman-root/style/" +
                pp.ER+//"R/" +
                "type/delete.gif' alt='drop the column' border=0></a>");
        s+=hhBox();
        s+="</td><td>" +
                "<a href='moveup.jsp?tabcol=" +tabcol+
                "' ><img src=/feldman-root/style/type/up.gif border=0></a>"+
                "<a href='movedown.jsp?tabcol=" +tabcol+
            "' ><img src=/feldman-root/style/type/down.gif border=0></a>";
        //s+="</td></tr>";
        return s;
    }
    
    String hhBox(){
        return "</td><td></td><td>";
    }
    
    public List<Integer> kvars()throws Exception{throw new Exception("no kvars for doc/pic");}
    public String addCell()throws Exception{throw new Exception("no addcell for doc/pic");}
    public String editCell()throws Exception{throw new Exception("no editCell for doc/pic");}
    public void setCase()throws Exception{throw new Exception("no setCase for doc/pic");}
    public String value(int id)throws Exception{return "";}
    String bridge(){
        return tabname+"/"+name+"/";
    }
    String getExt()throws Exception{
        try{
            int i = name.lastIndexOf(".");
            return name.substring(i);
        }catch(Exception e){
            throw new Exception("--POINT IN DOC.EXT?--");
        }
    }   
    
    static String getExt(String name){
        int i = name.lastIndexOf(".");
//        assert i>0:"no ext:"+name;
        return name.substring(i);
    }     
    public void drop()throws Exception{
        pp.initFat(tabname);        
        String q = "delete from translator where tabcol='"+tabcol+"'";
        pp.exec(q);
        String q2 = "delete from refhh where tab='"+tabname+"' and col='" +name+
                "'";
        pp.exec(q2);
        String path = myPath()+"/"+tabname+"/"+name;
        
        File dir = new File(path);
        if(dir.exists() && dir.isDirectory()){
            File[] fa = dir.listFiles();
            int N = fa.length;
            for(int i=0;i<N;i++){
                File f = fa[i];
                f.delete();
            }
            dir.delete();
        }

    }
    
    String myPath()throws Exception{
        throw new Exception("my path?");
    }
    
    public void addTC(String xname,String z)throws Exception{
        pp.initFat(tabname);
        TabCol.setRus(pp,tabname,name,xname.trim());
        String path = myPath()+"/"+tabname+"/"+name;
        File file = new File(path);
        file.mkdirs();
    }  
    
    public String linePoint(String rc,int k)throws Exception{
        throw new Exception("line point for fields only!");
    }
    
    public boolean isAuto(){return false;}
    public String tabcol(){return tabcol;}
    public String getUform()throws Exception{
        UList u = new UList(pp,tabname);
        return u.getForm(name);
    } 
    public int dtype()throws Exception{
        throw new Exception("no dtype");
    }
    public int nMix()throws Exception{
        return -1;
    }
    public String dtName()throws Exception{
        throw new Exception("no dtName");
    }
    public String refTab()throws Exception{
        throw new Exception("no reftab");
    }
    
    public boolean hasRus()throws Exception{
        return pp.getN
      ("select count(*) from translator where tabcol='"+tabname+"::"+name+"'")>0;
    }
    
    public void setRefhh(boolean b){}
    public void setRefhhBlind(boolean b){}    
    
    public static String getTab(String s)throws Exception{
        int i=s.indexOf("::");
        if(i<0)return s;
        try{
            return s.substring(0,i);
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }           
    }    
    
    public static String getCol(String s)throws Exception{
        int i=s.indexOf("::");
        if(i<0)return "";
        try{
            return s.substring(i+2);
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }           
    }     
}
