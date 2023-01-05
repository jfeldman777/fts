/*
 * P3Node.java
 *
 * Created on 4 ƒекабрь 2004 г., 15:23
 */
package j5feldman;
import j5feldman.proc.basement.Result;
import java.sql.*;
import java.util.*;
import j5feldman.ex.*;
/**
 *
 * @author  ‘ельдман яј
 */
public class P3Node extends P2Node{

    /** Creates a new instance of P3Node */
    public P3Node(int id, String code, String name,int parent
            ,int id2,int type,
            boolean abstr,boolean abc, boolean fin){
        super(id,code,name,parent,abstr,abc,fin,id2);     
         this.type = type;         
    }
    
       public boolean shadow(){
         return id2>0;}
       

      public static P3Node getP3Node(XPump pp,FTree ft,int id)throws Exception{
         P3Node P = null; 
         Result r=new Result();
         try{
             String q = ft.q3node(id);
             pp.select(r,30,q);
             r.rs.next();             
             P = getItem(r.rs);
         }
         finally{r.close();}
         return P;
    }
      static List<P3Node> getP3NodeS(XPump pp,FTree ft,String code)throws Exception{
         Result r=new Result();
         List<P3Node> list = new ArrayList<P3Node>();          
         try{
             String q = ft.q3nodeS(code);
             pp.select(r,31,q);
             while(r.rs.next()){    
                 list.add(getItem(r.rs));
             }
         }
         finally{r.close();}
         return list;
    }      
      static P3Node getP3Child(XPump pp,FTree ft,int parent,String code)throws Exception{
         P3Node P = null;
         Result r=new Result();
         try{
             String q = ft.q3child(parent,code);
             pp.select(r,32,q);
            r.rs.next();                
            P = getItem(r.rs);
         }finally{r.close();}
         return P;
    } 
      
      static List<P3Node> getP3Children(XPump pp,FTree ft,int parent)throws Exception{
             String q1 = ft.q3children1(parent);
             String q2 = ft.q3children2(parent);             
             List<P3Node> list = getP3Children(pp,q1);
             List<P3Node> list2 = getP3Children(pp,q2);
             list.addAll(list2);
             return list;
      }
      static List<P3Node> getP3Children(XPump pp,String q)throws Exception{   
         Result r=new Result();
         List<P3Node> list = new ArrayList<P3Node>(); 
         try{
             pp.select(r,33,q);
             while(r.rs.next()){                  
                 list.add(getItem(r.rs));
             }
         }finally{r.close();}
         return list;
    }
      
    public static P3Node getItem(ResultSet rs)throws Exception{
                 String code=rs.getString("code");
                 String name=rs.getString("name");
                 int id=rs.getInt("id");
                 int type = rs.getInt("type");
                 int id2 = rs.getInt("id2");
                 boolean abstr = rs.getBoolean("abstr");
                 boolean abc = rs.getBoolean("abc");
                 boolean fin = rs.getBoolean("fin");
                 int parent = rs.getInt("parent");
            return new P3Node(id,code,name,parent,id2,type,abstr,abc, fin);
    }
      
    static List<P3Node> getParent3Siblings(XPump pp,int xid)throws Exception{
        Result r=new Result();
        List<P3Node> list = new ArrayList<P3Node>();
        if(xid==0)return list;
        try{
            String q = "select p.id,p.code,p.name,p.parent2 parent,p.abstr,p.abc,p.fin,0 id2 " +
                    " from type p,type t where t.parent2=p.id and " +
                    " t.id2=0 and (t.id="+xid+" or t.id22="+xid+")";
            pp.select(r,34,q);
            while(r.rs.next()){
                list.add(getItem(r.rs));
            }
        } finally{r.close();}
        return list;
    }
  public static List<P3Node> allDown3(XPump pp,FTree ft,P3Node parent)throws Exception{
     List<P3Node> list=new ArrayList<P3Node>();
     list.add(parent);

     List<P3Node> children = getP3Children(pp,ft,parent.id());
     if(children.size()==0)
        return list;
     for(P3Node root:children){
        list.addAll(allDown3(pp,ft,root)); 
     }
     return list;
  }

  public static List<Integer> allDown30(XPump pp,int parent,long millis)throws Exception{
     List<Integer> list=new ArrayList<Integer>();
     list.add(parent);

     List<Integer> children = pp.getNN("select id from object where parent="+parent);
     if(children.size()==0)
        return list;
     for(Integer root:children){
        list.addAll(allDown30(pp,root,millis)); 
            if(System.currentTimeMillis()>millis)
            throw new ExTooBig(pp.iLang);         
     }
     return list;
  }  
}