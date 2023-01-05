/*
 * XPump.java
 *
 * Created on 24 Ќо€брь 2004 г., 13:52
 */
package j5feldman;
import j5feldman.proc.basement.AdminDict;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import java.io.*;
import j5feldman.schema.Schema;
import j5feldman.proc.basement.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class XPump extends XConnector  //implements IxPump
{
    public void setLastUpdate(final int id)throws Exception{
        Thread t = new Thread(){
            public void run(){
                try{
                    yield();
                    String q = "update object set last_update=dateob() where id="+id;                    
                    exec(q); 
                }catch(Exception e){}
            }
        }; 
        t.start();
    }
    
    public void setLastUpdate(final String sid)throws Exception{
        Thread t = new Thread(){
            public void run(){
                try{
                    yield();
                    String q = "update object set last_update=dateob() where id="+sid;                    
                    exec(q); 
                }catch(Exception e){}
            }
        }; 
        t.start();
    }    
    
    public String dict(String x){
        return dict.get(x);
    }
    AdminDict dict = null;
    public XPump()throws Exception{ 
        this(0,"R/");
        dict = new AdminDict(0);
    }

    public String ER="R/"; 
    
    XPump(int iLang,String ER)throws Exception{
        this.iLang = iLang;
        this.ER = ER;
        dict = new AdminDict(iLang);
    }
    public int iLang = 0;
    
    public Table getTable(P2Node pn)throws Exception{
        return  pn.abc()?fatAllAbc.getTable(this,pn.code()):
                        fatAll.getTable(this,pn.code());
    }   
    
    public Table getTable(String typecode)throws Exception{
        return  fatAll.getTable(this,typecode);
    }     
   
    public TableFactory fat = new TableFactory();
    public TableFactory fatAll = new TableFactory(true,false);
    public TableFactory fatAllAbc = new TableFactory(true,true);
       
    public void initFat(String tabname)throws Exception{ 
        fat.map.put(tabname,null);
        fatAll.map.put(tabname,null);        
        fatAllAbc.map.put(tabname,null);   
    }  
    
    public void initFat(){
        fat = new TableFactory();
        fatAll = new TableFactory(true,false);
        fatAllAbc = new TableFactory(true,true);
    }  
    
    static int page = 50;
    static int page2 = 100;
    public static int page3 = 80;
    
    public List<TabColTctype> shadows;
    boolean mTree = false;
    public void setMtree(){
        mTree = true;
    }
    boolean notDemo=true;    

    public void initShadows()throws Exception{
        if(shadows!=null)return;

        shadows = new ArrayList<TabColTctype>();
        
        String q = "select tab,col,tctype from shadowcols";
        Result r=new Result();
        String tab="",col="",stctype="";
        try{
            select(r,26,q);       
            while(r.rs.next()){//lines
                tab = r.rs.getString("tab");    
                col = r.rs.getString("col");
                stctype = r.rs.getString("tctype");
                TabColTctype tt = new TabColTctype(tab,col, stctype.charAt(0));
                shadows.add(tt);
            }   
        }finally{r.close();}    
    }  

    public void addShadow(String tab,String col,char tctype)throws Exception{
            initShadows();
            delTabCol(shadows,tab,col);
            shadows.add(new TabColTctype(tab,col,tctype));
    }
    public void delShadow(String tab,String col)throws Exception{
            initShadows();
            delTabCol(shadows,tab,col);
    }
    
    public boolean isShadow(String tab,String col)throws Exception{
            initShadows();
            return hasTabCol(shadows,tab,col);
    }    
      String rootPath;
      int meid=-1;  
      public int meid(){
        return meid;
      }
        public String iconpath(){
            return "/feldman-root/icon/types/";
        }
        public String icon(String typecode)throws Exception{
            return getS("select if(icon is null or icon='',code,icon) " +
            " from type where code='"+typecode+"'");
        }
    
       public boolean isOrigin(FTree ft,int id)throws Exception {
         return getN(ft.qId2(id))==0;
      }
   
       public void moveNode(FTree ft,int id, int parent)throws Exception {
         exec(ft.qMove(id,parent));
      }

   
  public int getNewId(FTree ft)throws Exception{
     int N = 20000;
     int id = getN(ft.qNewId());     
     if(id>N)finalize();     
     return id;
  }
   
       public List<PNode> getNodeS(String q)throws Exception{
         List<PNode> L=null;
         Result r=new Result();
         try{
            select(r,27,q);
            L = getNodeS(r.rs);
         }finally{r.close();}
         return L;
      }
       public List<PNode> getNodeS(ResultSet rs)throws Exception{
         List<PNode> list = new ArrayList<PNode>();
         while(rs.next()){
            list.add(getNode(rs));
         }
         return list;
      }     
       public PNode getNode(FTree ft,int id)throws Exception{
         String q=ft.qGuiNode(id);
         return getNode(q);
      } 
             
       public PNode getNode(String q)throws Exception{
         PNode P=null;
           Result r=new Result();
         try{
            select(r,28,q);
            r.rs.next();
            P=getNode(r.rs);
         }finally{r.close();}
         return P;
      }
      
   
       PNode getNode(ResultSet rs)throws Exception{
         int id=rs.getInt("id");
         String code=rs.getString("code");
         String name=rs.getString("name");
         int parent=rs.getInt("parent");
         int id2=rs.getInt("id2");
         int type = rs.getInt("type");
         String typecode = rs.getString("typecode");
         boolean abstr = rs.getBoolean("abstr");
         boolean abc = rs.getBoolean("abc");
         boolean fin = rs.getBoolean("fin");
         return new PNode(id,code,name,parent,id2,type,typecode,abstr,abc, fin); 
      }
      
    public void log(String s)throws Exception{
        String q = "insert into errlog (err)values('"+apo(s)+"')";
        exec(q);
    }

    public void log(ArrayList ar)throws Exception{
        for(int i=0;i<ar.size();i++){
            String s = (String)ar.get(i);
            log(s);
        }
    }  
    
    static String apo(String s){
        return s.replace('\'','?');
    }
  
    /**absolute path*/
    String iconPath(){
        return rootPath+iconpath();
    }
    
   
    public String docPath(){
        return rootPath+docpath();
    } 
    public String picPath(){
        return rootPath + picpath();
    } 
    
    public String pubPathWt(){
        return rootPath + pubpath()+"wt/";
    }
    
    public String pubpathWd(){
        return pubpath()+"wd/";
    }
    
    public String pubPathWd(){
        return rootPath + pubpath()+"wd/";
    }        
    public String pubPath(){
        return rootPath + pubpath();
    } 
 
    public String pubpath(){
        return brrr()+"pub/"; 
    }        
    /**relative path*/
    String picpath(){
        return brrr()+"pic/"; 
    }
    String docpath(){
        return brrr()+"doc/"; 
    } 
    
    String brrr(){
        return "/feldman-root/system/"+schema+"/"; 
    }
  
public String getTableLine(String qselect)throws Exception{//arrayList of maps
        String q = qselect;
        String S = "";
        Result r=new Result();
        try{
            select(r,29,q);       
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            while(r.rs.next()){//lines
                S+="<tr>";
                HashMap map = new HashMap();
                for(int i=0;i<N;i++){//cols
                    String x = r.rs.getString(i+1); 
                    String colname = md.getColumnName(i+1); 
                    S+="<td>"+x+"</td>"; 
                }            
                S+="</tr>";
            }   
        }finally{r.close();}    
        return S;        
    }  

    List<String> myTypes(int objid)throws Exception{
        String q = "select code from type where id22=0 and id2=0 and not abstr";
        List<String> myTypes = getSS(q);
        for(int i=0;i<myTypes.size();i++){
            String tab=myTypes.get(i);
            String q2 = "select count(*) from "+tab+" where id="+objid;
            if(getN(q2)==0){myTypes.remove(i);i--;}
        }
        return myTypes;
    }
    
    class TabColTctype{
        String tab;
        String col;
        char tctype;
        
        TabColTctype(String tab,String col,char tctype){
            this.tab = tab;
            this.col = col;
            this.tctype = tctype;
        }
        
        
        boolean hasTab(String t){    
            return tab.equals(t);
        }
        
        boolean hasTabCol(String t,String c){
            return tab.equals(t) && col.equals(c);
        }
        char getTctype(String t,String c){
            if(hasTabCol(t,c))return tctype;
            return ' ';
        }
 
    }
    boolean hasTab(String t)throws Exception{
                initShadows();
            for(TabColTctype tt:shadows){
                if(tt.hasTab(t))return true;
            }
            return false;
    }
    boolean hasTab(List<TabColTctype> L,String t)throws Exception{
                initShadows();
            for(TabColTctype tt:L){
                if(tt.hasTab(t))return true;
            }
            return false;
    }
    void delTabCol(List<TabColTctype> L,String t,String c)throws Exception{
            for(int i=0;i<L.size();i++){
                TabColTctype tt = L.get(i);
                if(tt.hasTabCol(t,c)){
                    L.remove(i);return;
                }
            }
        } 
        boolean hasTabCol(List<TabColTctype> L,String t,String c){
            for(TabColTctype tt:L){
                if(tt.hasTabCol(t,c))return true;
            }
            return false;
        }   
        
        char getTctype(List<TabColTctype> L,String t,String c){
            for(TabColTctype tt:L){
                if(tt.hasTabCol(t,c))return tt.tctype;
            }
            return ' ';
        } 
        
        List<IProc> getProc(int id)throws Exception{
            int type = getN("select type from object where id="+id);
            return getTypeProc(type,id);
        }

        List<IProc> getTypeProc(int type,int id)throws Exception{
            List<IProc> arx = new ArrayList<IProc>();            
            List<P2Node> list = FTree.trailsUp(this,type);
            String xcode = "??";
            for(P2Node pn:list){
                IProc ipc=null;
                xcode = cC(pn.code());
                try{                    
                   ipc = getInstance(xcode);
                }catch(Exception e){
                    continue;
                }                    
                ipc.init(this,id);
                arx.add(ipc);
            }
            return arx;
        }        
    static String cC(String code)throws Exception{
        if(code.equals("object")) return "MyObject";
        String c = code.substring(0,1);
        return c.toUpperCase()+code.substring(1);
    }
    
    public static IProc getInstance(String code)throws Exception{
        Class cc = Class.forName("j5feldman.proc."+cC(code));
        Object o = cc.newInstance();
        return (IProc)o;////////////////
    }      


      
}
