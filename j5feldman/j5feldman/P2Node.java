/*
 * P2Node.java
 *
 * Created on 6 ƒекабрь 2004 г., 8:14
 */

package j5feldman;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import j5feldman.ex.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class P2Node extends P20Node{

    protected int parent;
    protected boolean abstr=false;
    protected boolean abc; 
    protected boolean fin = false; 
    protected int type;      
      protected int id2=0;
    public int id2(){return id2;}  
    public boolean fin(){return fin;}
    /** Creates a new instance of P2Node */
    P2Node(){}
    public P2Node(int id,String code,String name,int parent,boolean abstr,boolean abc,boolean fin, int id2) {
        super(id,code,name);
        this.parent = parent;
        this.abstr = abstr;
        this.abc = abc; 
        this.fin = fin;
         this.id2 = id2;        
    }
    
    public String nameOrigin(XPump pp)throws Exception{
        return id2>0?pp.getS("select name from object where id="+id2):name;
    }
    
    public String codeOrigin(XPump pp)throws Exception{
        return id2>0?pp.getS("select code from object where id="+id2):code;
    }
    
       public int idOrigin(){
        return id2>0?id2:id;
       } 
       
       public int idOrigin(boolean isH){
        return isH?idOrigin():id2;
       } 
       
    public int type(){
        return type;
      }
    
    public void setType(int type){
        this.type = type;
    }
    
    public boolean abc(){
        return abc;
    }
    

    public int parent() {
        return parent;
    }
 
    public static String list2path(List<P2Node> list){
        String s = "";
        for(P2Node node:list){
            s+="/"+node.code();
        }
        return s;
    }
    public static String find(XPump pp,FTree ft,String code)throws Exception{
        List<P2Node> list = getP2NodeS(pp,ft,code);
        return list2path(list);
    }
    
    public static P2Node getP2Node(XPump pp,FTree ft,int id)throws Exception{
        String q = ft.q2node(id);
        return getP2Node(pp,q);
    }
    static P2Node getP2Node(XPump pp,String q)throws Exception{
        P2Node P = null;
        Result r=new Result();
        try{
            pp.select(r,10,q);
            r.rs.next();
            P = getItem(r.rs);
        }catch(Exception e){throw new Exception("P2Node:85:"+q); }
        finally{r.close();}
        return P;
    }
    static List<P2Node> getInhParents(XPump pp,int xid)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        if(xid==0)return list;
        try{
            String q = "select p.id,p.code,p.name,p.parent2 parent,p.abstr,p.abc,p.fin,p.id22 id2 " +
                    " from type p,type t where t.parent2=p.id and " +
                    " t.id2=0 and (t.id="+xid+" or t.id22="+xid+")";
            
            pp.select(r,11,q);
            while(r.rs.next()){
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
    static List<P2Node> getP2All(XPump pp,String typecode)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{
            String q = FTree.q2all(typecode);
            pp.select(r,12,q);
            while(r.rs.next()){ 
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
    static List<P2Node> getP2All(XPump pp,String typecode,int id)throws Exception{
        Result r=new Result();
        Result r2=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{
            String q = FTree.q2all(typecode, id, true);
            pp.select(r,13,q);
            while(r.rs.next()){ 
                list.add(getItem(r.rs));
            }
            String q2 = FTree.q2all(typecode, id, false);
            r.close();////////////////////////////////////////////
            pp.select(r2,14,q2);
            int i=0;
            while(r2.rs.next()){ 
                if(i++<pp.page)list.add(getItem(r2.rs));
                else{
                    list.add(getMore());
                    break;
                }
            }            
        } finally{r.close();r2.close();}
        return list;
    }
    static List<P2Node> getP2after(XPump pp,String typecode,int id)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{         
            String q2 = FTree.q2all(typecode, id, false);
            pp.select(r,15,q2);
            int i=0;
            while(r.rs.next()){ 
                if(i++>=pp.page)list.add(getItem(r.rs));
            }            
        } finally{r.close();}
        return list;
    }     
    public static List<P2Node> getP2NodeS(XPump pp,FTree ft,String code)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{
            String q = ft.q2nodeS(code);
            pp.select(r,16,q);
            while(r.rs.next()){              
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
    public static List<P2Node> getP2NodeS(XPump pp,String q)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{
            pp.select(r,17,q);
            while(r.rs.next()){                 
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
    public static P2Node getP2Child(XPump pp,FTree ft,int parent,String code)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        String q="";
        try{
            q = ft.q2child(parent,code);
            pp.select(r,18,q);
            r.rs.next();
            return getItem(r.rs);
        }catch(Exception e){
            throw new Exception("//NO GOOD CHILDREN:q: "+q+"//");
        }finally{r.close();}
    }
    static List<P2Node> getP2Children(XPump pp,FTree ft,int parent)throws Exception{
        String q = ft.q2children(parent);
        return getP2Children(pp,q);
    }

    public static P2Node getItem(ResultSet rs)throws Exception{
                int id=rs.getInt("id");
                String code=rs.getString("code");
                String name=rs.getString("name");
                int parent = rs.getInt("parent");
                boolean abstr = rs.getBoolean("abstr");
                boolean abc = rs.getBoolean("abc");
                 boolean fin = rs.getBoolean("fin");
                int id2 = rs.getInt("id2"); 
                
                return new P2Node(id,code,name,parent,abstr,abc,fin, id2);
    }
    public static P2Node getRoot()throws Exception{
                int id=0;
                String code="o";
                String name="no data";
                int parent = 0;
                boolean abstr = false;
                boolean abc = false;
                int id2 = 0;                    
                return new P2Node(id,code,name,parent,abstr,abc,false, id2);
    }
    public static P2Node getMore()throws Exception{
                int id=-1;
                String code="more";
                String name="...";
                int parent = -1;
                boolean abstr = false;
                boolean abc = false;
                int id2 = 0;                    
                return new P2Node(id,code,name,parent,abstr,abc,false, id2);
    }    
    static List<P2Node> getP2Children(XPump pp,String q)throws Exception{
        Result r=new Result();
        List<P2Node> list = new ArrayList<P2Node>();
        try{
            pp.select(r,19,q);
            while(r.rs.next()){              
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
    public boolean isAbstr(){
        return abstr;
    }
    static List<P2Node> path2list(XPump pp,FTree ft,String path)throws Exception{
        List<P2Node> list=new ArrayList<P2Node>();
        int parent=0;
        StringTokenizer st = new StringTokenizer(path,"/");
        while (st.hasMoreTokens()) {
            String code = st.nextToken();
            P2Node node = getP2Child(pp,ft,parent,code);
            if(node==null)throw new Exception("bad path:"+path);
            parent = node.id();
            list.add(node);
            
        }
        
        return list;
    }
    public static int path2id(XPump pp,FTree ft,String path)throws Exception{
        int parent=0;
        StringTokenizer st = new StringTokenizer(path,"/");
        while (st.hasMoreTokens()) {
            String code = st.nextToken();
            P2Node node = getP2Child(pp,ft,parent,code);
            if(node==null)throw new Exception("bad path:"+path);
            parent = node.id();
        }
        return parent;
    }
    public static List<P2Node> allDown(XPump pp,FTree ft,int parent,long maxMilliseconds)throws Exception{
        List<P2Node> list=new ArrayList<P2Node>();
        P2Node node;
        list.add(node=getP2Node(pp,ft,parent));
        
        List<P2Node> children = getP2Children(pp,ft,parent);
        if(children.size()==0)
            return list;

        for(P2Node root:children){
            list.addAll(allDown(pp,ft,root.id()));
            if(System.currentTimeMillis()>maxMilliseconds)
            throw new ExTooBig(pp.iLang);            
        }
        return list;
    }    
    public static List<P2Node> allDown(XPump pp,FTree ft,int parent)throws Exception{
        List<P2Node> list=new ArrayList<P2Node>();
        P2Node node;
        list.add(node=getP2Node(pp,ft,parent));
        
        List<P2Node> children = getP2Children(pp,ft,parent);
        if(children.size()==0)
            return list;
        for(P2Node root:children){
            list.addAll(allDown(pp,ft,root.id()));
        }
        return list;
    }
    static public String refList(XPump pp,String reftab,int id) throws Exception{
        String S = "";
        List<P2Node> children = getP2All(pp,reftab, id);/////////////////////1
        for(P2Node pn:children){
            S+="<option value="+pn.id()+" "+(pn.id()==id?" SELECTED ":"")+" >"+
                    s80(pn)+
                    //pn.codeName()+
                    "</option>";
        }
        return S;
    }
    
    static String s80(P2Node pn){
        String s = pn.codeName();
        int L = s.length();
        return L<81?s:s.substring(0,80)+"...";
    }
    
    public static List<P2Node> pathUp(XPump pp,String tabname)throws Exception{
        int id = pp.getN("select id from type where code='"+tabname+"'");
        return pathUp(pp,FTree.InheritanceTree,id);
    }

    public static List<P2Node> pathUp(XPump pp,FTree ft,int id)throws Exception{
        List<P2Node> list=new ArrayList<P2Node>();
        P2Node node;
        list.add(node=getP2Node(pp,ft,id));
        if(id==pp.meid||id<=0)
            return list;
        list.addAll(0,pathUp(pp,ft,node.parent));
        return list;
    }
    public static List<P2Node> pathUp(XPump pp,FTree ft,int id,int iroot)throws Exception{
        List<P2Node> list=new ArrayList<P2Node>();
        P2Node node;
        list.add(node=getP2Node(pp,ft,id));
        if(id==pp.meid||id<=0||id==iroot)
            return list;
        list.addAll(0,pathUp(pp,ft,node.parent,iroot));
        return list;
    }
    public static String list2path(XPump pp,FTree ft,int id)throws Exception{
        return list2path(pathUp(pp,ft,id));
    }
    
    static String pathInh(XPump pp,int type)throws Exception{
        return list2path(pathUp(pp,FTree.InheritanceTree,type));
    }
    
    static boolean inPath(List<P2Node> list,P2Node p){
        for(P2Node pl:list){
            if(p.id()==pl.id())return true;
        }
        return false;
    }
}
