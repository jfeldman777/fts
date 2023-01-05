package j5feldman;
import java.util.*;
import j5feldman.proc.basement.*;
import j5feldman.ex.*;
public enum FTree{
  ObjectTree{  
    String path(XPump pp,GuiTree.GuiNode gn)throws Exception{
        return gn.list2path(pp,this,gn.id());
    }      
    String t(){return "a";}
    public String abstr(){return "abstr,0 abc,fin";}///////////////////
    public String tabName(){return "object";}
    public String id2Name(){return "id2";}
    public String parentName(){return "parent";}
    String typeName(){return "type";}     
    public String abr(){return "obj";}
    public String T(){return "A";}
   String q3children1(int id){
    return "select o.type,o.id2,o.id,o.code,o.name,o.abstr,0 abc,o.fin,o.parent from " +
            
//            " object o where o.id>0 and o.id>0 and o.parent="+id+
            Substitution.fromNwhere(false,id,"o","o2")+
            
            " order by o.code";
   } 
   String q3children2(int id){
    return "select o.type,o.id2,o.id,o.code,o.name,if(o.abstr,true,o2.abstr) abstr,0 abc,o.fin," +
            " o2.parent from " +
            
//            " object o,object o2 where o2.id2>0 and o2.id2=o.id and o2.parent="+id+
            Substitution.fromNwhere(true,id,"o","o2")+
            
            " order by o.code";
   } 
   
   String q2children(int id){
    return "select id,code,name,parent,abstr,0 abc,fin,id2 " +
            " from object where id>0 and parent="+id+" order by code ";
   } 
  
   String qOriginNode(int id){
       return "select o.id,o.code,o.name,o.parent,o.id2,t.id type,t.abc,o.fin," +
               "o.abstr,t.code typecode,"+ //////////////////////
          " if(t.icon is null or t.icon='',t.code,t.icon) icon " +
               " ,o.last_update " +
               " from object o,type t "+
          " where t.id=o.type and o.id="+id;
   };
   
   String qGuiNode(int id){
       return "select s.id,o.code,o.name,s.parent,s.id2,t.id type,t.abc,s.fin," +
               "o.abstr,t.code typecode,"+ //////////////////////
          " if(t.icon is null or t.icon='',t.code,t.icon) icon " +
               " ,o.last_update " +
               " from object o,type t,object s "+
          " where t.id=o.type and ((o.id="+id+" and o.id2=0 and s.id=o.id) or "+
          " (o.id=s.id2 and s.id="+id+" and s.id2>0))";
     }    
   String qGuiChildren1(int id){
      return 
      "select o.id,o.code,o.name,o.parent,o.id2,t.id type,t.abc,o.fin," +
              "o.abstr,t.code typecode,"+ 
      " if(t.icon is null or t.icon='',t.code,t.icon) icon " +
              " ,o.last_update " +
              " from object o,type t "+
      " where o.id>0 and t.id=o.type and " +
              " o.parent="+id+" and o.id2=0 " +
              " order by o.code";
    }
   String qGuiChildren2(int id, boolean hOcode){
      return 
      "select s.id," +
              (hOcode?"s.code,":"o.code,")+
              "o.name,s.parent,s.id2,t.id type,t.abc,o.fin," +
              "if(o.abstr,o.abstr,s.abstr) abstr,t.code typecode,"+ 
      " if(t.icon is null or t.icon='',t.code,t.icon) icon " +
              " ,o.last_update " +
              " from object o,type t,object s "+
      " where t.id=o.type and " +
      " o.id=s.id2 and s.parent="+id+" and s.id2>0 " +
              " and s.id>0  order by o.code";
    }   
  },
//////////////////////////////////////////////////////////////////////////////////////////////
TypeTree{
   public String abstr(){return " abstr,abc,fin ";}              
   String t(){return "t";}   
   String typeName(){return "id";}
   public String tabName(){return "type";}
   public String id2Name(){return "id2";}
   public String parentName(){return "parent";}
   public String abr(){return "type";}
   public String T(){return "T";}
   String q3children1(int parent){
    return "select o.id type,o.id2,o.id,o.code,o.name,o.abstr,o.abc,o.fin,o.parent " +
            " from " +
            " type o,type o2 " +
            " where " +
            "  o.id2=0 and o2.id=o.id and o.id22=0 and o.parent="+parent+
            " order by o.code";
   }
   
   String q3children2(int parent){
    return "select o.id type,o.id2,o.id,o.code,o.name,o.abstr,o.abc,o.fin,o2.parent " +
            " from " +
            " type o,type o2 " +
            " where " +
            " o2.id22=0 and o2.id2>0 and o2.id2=o.id and o2.parent="+parent+
            "  order by o.code";
   }   
   String q2children(int parent){
    return "select id,code,name,parent,abstr,abc,fin,id2 from type where id22=0 and parent="+
            parent;
   } 
   String qOriginNode(int id){
       return "select id,code,name,parent,id2,id type,abc,fin," +
               "abstr,code typecode,"+ 
          " if(icon is null or icon='',code,icon) icon " +
              " ,code last_update " +
               " from type "+
          " where id="+id;
   }; 
   String qGuiNode(int id){
       return "select s.id,o.code,o.name,s.parent,s.id2,o.id type,o.abc,o.fin," +
               "o.abstr,o.code typecode,"+ 
          " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +
               " from type o,type s "+
          " where ((o.id="+id+" and o.id2=0 and s.id=o.id) or "+
          " (o.id=s.id2 and s.id="+id+" and s.id2>0)) and s.id22=0";
   }   
   String qGuiChildren1(int id){
      return 
      "select o.id,o.code,o.name,o.parent,o.id2,o.id type,o.abc,o.fin," +
              "o.abstr,o.code typecode,"+ 
      " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +
              " from type o "+
      " where not o.abstr and  o.code<>'folder' and " +
              " o.parent="+id+" and o.id2=0  " +
              "  and o.id>0 and o.id22=0 order by o.code";
    } 
   String qGuiChildren2(int id, boolean hOcode){
      return 
      "select s.id,o.code,o.name,s.parent,s.id2,o.id type,o.abc,o.fin," +
              "o.abstr,o.code typecode,"+ 
      " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +         
              " from type o,type s "+
      " where not s.abstr and  o.code<>'folder' and " +
      " o.id=s.id2 and s.parent="+id+" and s.id2>0 " +
              "  and s.id>0 and s.id22=0 order by o.code";
    }    
   List<String> qqDrop(XPump pp,int id)throws Exception{
       String q = "select code from type where id="+id;
       String tab = pp.getS(q);
       q = " select name from "+
          " sUSRFKeyInfo where "+
          " \"schema\"='"+pp.schema+"' and \"table\"='"+tab+"' ";
       List<String> fknames = pp.getSSinfo(q);
       List<String> QQ = new ArrayList<String>();
       for(String fkn:fknames){
          QQ.add("alter table "+tab+" drop constraint "+fkn);
       }
       QQ.add("drop table "+tab);
       QQ.add("delete from type where id="+id);   
       
       QQ.add("delete from translator where tabcol LIKE '"+tab+"::%'");   
       
       return QQ;    
    }
         },
//////////////////////////////////////////////////////////////////////////////////////////
  InheritanceTree{    
    public String abstr(){return " abstr,abc,fin ";}             
    public String abr(){return "inh";}
    
    SortedMap<String,String> box2wt(XPump pp,GuiTree.GuiNode gn)throws Exception{
        List<P2Node> list = trailsUp(pp,gn.type());
        String s="";
        SortedMap<String,String> map = new  TreeMap<String,String>();
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = pp.getTable(pn);
                tab.line2map2(gn.id(),map);        
            }  
        }
        return map;
    }
    String boxView(XPump pp,GuiTree.GuiNode gn)throws Exception{
        return boxView(pp,gn,false);
    }
    
    
    String boxView(XPump pp,GuiTree.GuiNode gn,boolean ee)throws Exception{
        List<P2Node> list = trailsUp(pp,gn.type());
        String s="";
        GuiTree.GuiNode gx = gn;
        if(gn.isObj()){
            while(gx.isPseudo()){
                P20Node p20 = P20Node.getParent(pp,gx.idOrigin());
                if(p20.id()==0)break;
                s="<tr><td></td><td>" +p20.codeNameUnder()+
                        "</td></tr>"+s;
                gx = gx.parentNode();
            }
            if(gx.isShadow()){
                P20Node p20 = P20Node.getParent(pp,gx.idOrigin());
                if(p20.id()>0)
                s="<tr><td></td><td>" +p20.codeNameUnder()+
                        "</td></tr>"+s;
            }
        }
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = pp.getTable(pn);
                
//                System.out.println("ftree:"+tab.name);
                
                s+=tab.boxView(gn,ee);
            }
        }
        return s;
    }
   
   public String reportHeaderView(XPump pp,int type,boolean inBrief)throws Exception{
        List<P2Node> list = trailsUp(pp,type);
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = pp.getTable(pn);
                s+=tab.reportHeaderView(inBrief);
            }
        }
        if(s.length()>500){
                System.out.println("WR:"+s);
                throw new ExWideReport(pp.iLang);
        }    
        return s;
    }
   
   public String reportHeaderShadow(XPump pp,int type,boolean inBrief)throws Exception{
        List<P2Node> list = trailsUp(pp,type);
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = pp.getTable(pn);
                s+=tab.reportHeaderShadow(inBrief);
            }
        }
        if(s.length()>500){
                System.out.println("WR:"+s);
                throw new ExWideReport(pp.iLang);
        }    
        return s;
    }
    public String reportLineView(XPump pp,int oid,int type)throws Exception{
        List<P2Node> list = trailsUp(pp,type);
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = pp.getTable(pn);
                s+=tab.reportLineView(oid);
            }
        }
        return s;
    }
    public String reportLineShadow(XPump pp,int oid,int type)throws Exception{
        List<P2Node> list = trailsUp(pp,type);
        String s="";
        for(P2Node pn:list){
            try{
                if(!pn.isAbstr()){
                    Table tab = pp.getTable(pn);
                    s+=tab.reportLineShadow(oid);
                }
            }catch(Exception e){}
        }
        return s;
    }   
    
    String editFormShadow(XPump pp,GuiTree.GuiNode gn)throws Exception{
        List<P2Node> list = trailsUp(pp,gn.type());
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){            
                Table tab = new Table(pp,pn.code(),!gn.isObj(),pn.abc());//false);
                s+=tab.editFormShadow(gn);
            }
        }
        return s;
    }
    
    String editForm(XPump pp,GuiTree.GuiNode gn)throws Exception{
        List<P2Node> list = trailsUp(pp,gn.type());
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){            
                Table tab = new Table(pp,pn.code(),!gn.isObj(),pn.abc());//false);
                s+=tab.editForm(gn);
            }
        }
        return s;
    }
    
    String addForm(XPump pp,String typecode)throws Exception{
        int type;
        try{
            type = Integer.parseInt(typecode);
        }catch(Exception e){
            type = pp.getN("select id from type where code='"+typecode+"'");
        }
        List<P2Node> list = trailsUp(pp,type);
        String s="";
        for(P2Node pn:list){
            if(!pn.isAbstr()){
                Table tab = new Table(pp,pn.code(),false,pn.abc());
                s+=tab.addForm();
            }
        }
        return s;
    }
    
    String t(){return "h";}
    String typeName(){return "id";}
    public String tabName(){return "type";}
    public String id2Name(){return "id22";}
    public String parentName(){return "parent2";}
    public String T(){return "H";}
   String q3children1(int parent){
    return "select o.id type,o.id22 id2,o.id,o.code,o.name,o.abstr,o.abc,o.fin,o.parent2 parent " +
            " from type o,type o2 " +
    " where (o.id22=0 and o2.id=o.id and o.id2=0 and o.parent2="+parent+")" +
            " order by o.code";
   }  
   
   String q3children2(int parent){
    return "select o.id type,o.id22 id2,o.id,o.code,o.name,o.abstr,o.abc,o.fin,o2.parent2 parent " +
            " from type o,type o2 " +
    " where " +
            " (o2.id2=0 and o2.id22<>0 and o2.id22=o.id and o2.parent2="+parent+") " +
            " order by o.code";
   }    
   String q2children(int parent){
    return "select t.id,t.code,t.name,t.parent,t.abstr,t.abc,t.fin,t.id22 id2 from " +
            " type t where t.id2=0 and t.parent="+parent;
   }
   
   String qOriginNode(int id){
       return "select id,code,name,parent2 parent," +
       "0 id2,id type,abc,fin," +
               "abstr,code typecode,"+ 
          " if(icon is null or icon='',code,icon) icon " +
              " ,code last_update " +
               " from type "+
          " where id="+id;
   };
   
   String qGuiNode(int id){
       return "select s.id,o.code,o.name,s.parent2 parent," +
       "s.id22 id2,o.id type,o.abc,o.fin," +
               "o.abstr,o.code typecode,"+ 
          " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +
               " from type o,type s "+
          " where ((o.id="+id+" and o.id22=0 and s.id=o.id) or "+
          " (o.id=s.id22 and s.id="+id+" and s.id22>0))  and s.id2=0";
    }
   String qGuiChildren1(int id){
      return 
      "select o.id,o.code,o.name,o.parent2 parent,o.id22 id2,o.id type,o.abc,o.fin," +
              "o.abstr,o.code typecode,"+ 
      " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +
              " from type o "+
      " where " +
              " o.parent2="+id+" and o.id22=0 " +
              " and o.id>0  and o.id2=0 order by o.code";
    } 
   
   String qGuiChildren2(int id, boolean hOcode){
      return 
      "select s.id,o.code,o.name,s.parent2 parent,s.id22 id2,o.id type,o.abc,o.fin," +
              "o.abstr,o.code typecode,"+ 
      " if(o.icon is null or o.icon='',o.code,o.icon) icon " +
              " ,o.code last_update " +
              " from type o,type s "+
      " where " +
      " o.id=s.id22 and s.parent2="+id+" and s.id22>0 " +
              " and s.id>0  and s.id2=0 order by o.code";
    }    
   List<String> qqDrop(XPump pp,int id)throws Exception{
       throw new Exception("no drop origin for inhTree"+id);
    }
         };
/////////////////////////////////////////////////////////////////////////////////////////////////
   abstract String qOriginNode(int id);
   abstract String qGuiNode(int id);
   abstract String qGuiChildren1(int parent);
   abstract String qGuiChildren2(int parent, boolean hOcode);
   public abstract String tabName();
   public abstract String id2Name();
   public abstract String parentName();
   abstract String typeName();
   String qNewId(){
//     assert this!=InheritanceTree:"no new ID for InhTree! ";
     return "SELECT NEXTVAL('"+tabName()+"_id') ";
  }

  String qId2(int id){
     return  "select "+id2Name()+" from "+tabName()+" where id="+id;
  }

  public String qOriginId(int id){
     return  "select if("+id2Name()+"=0,id,"+id2Name()+") from "+tabName()+" where id="+id;
  }   	
  String qMove(int child, int parent){
     return "update "+tabName()+" set "+parentName()+"="+parent+" where id="+child;
  }
  String qDrop(int id){
     return "delete from "+tabName()+" where id="+id;  
  }
  List<String> qqDrop(XPump pp,int id)throws Exception{
     List<String> ss = new ArrayList<String>();
     ss.add("delete from "+tabName()+" where id="+id);  
     return ss;
  }  

  public String qMakeShortcut(int originId,int parentId,int ID,boolean blind){
     return "insert into "+tabName()+" (id,code,"+id2Name()+","+parentName()+"" +
             " ,abstr)values("+
        +ID+",'_"+originId+"_"+parentId+
             "',"+originId+","+parentId+"," +(blind?1:0)+
             " )";
  }

  boolean hasChildren(XPump pp,int id)throws Exception{
    String q = "select count(*) from "+tabName()+" where id>0 and "+parentName()+"="+id;
    return pp.getN(q)>0;
   }
  
   int iChildren(XPump pp,int id)throws Exception{
    String q = "select count(*) from "+tabName()+" where id>0 and "+parentName()+"="+id;
    return pp.getN(q);
   }
  
   abstract String t();
   String q3node(int id){
    return "select " +abstr()+","+
             parentName()+
             " parent,"+typeName()+
             " type,"+id2Name()+
             " id2,id,code,name from " + tabName()+" where id="+id;
   }
   
  String q3child(int parent,String code){
    return "select " +abstr()+","+
                typeName()+
             " type,"+id2Name()+
             " id2,id,name from " + tabName()+
             " where "+parentName()+"="+parent+" and code='"+code+"'";
   }
   
  abstract String q3children1(int id);
  abstract String q3children2(int id);
  String q3nodeS(String code){
    return "select " +abstr()+","+
             parentName()+
             " parent,"+typeName()+
             " type,"+id2Name()+
             " id2,id,code,name from " + tabName()+" where code='"+code+"'";
   }
   String q2node(int id){
    return "select id,code,name," +abstr()+
            ","+parentName()+" parent " +
            ","+id2Name()+" id2 " +
            " from " + tabName()+" where id="+id;
   }
   
   String q2child(int parent,String code){
    return "select id,code,name,"+abstr()+","+parentName()+" parent " +
            ","+id2Name()+" id2 " +
            " from " + tabName()+
             " where "+parentName()+"="+parent+" and code='"+code+"'";
   }
   
   abstract String q2children(int parent);
   static String q2all(String typecode){
    return "select o.id,o.code,o.name,o.parent,o.abstr,0 abc,o.fin,o.id2 " +/////////////
            " from object o,"+typecode+
    " t where t.id=o.id and o.id2=0 order by o.code";
   } 
   static String q2all(String typecode,int id,boolean include){
    return "select o.id,o.code,o.name,o.parent,o.abstr,0 abc,o.fin,o.id2 " +/////////////
            " from object o,"+typecode+
    " t where t.id=o.id and o.id2=0 and " +
            (include?" (o.id=0 or o.id=" + id + ") order by o.id "
            :" (o.id<>0 and o.id<>" +id+ ") order by o.code,o.id ");
   }
   String q2nodeS(String code){
    return "select id,code,name,"+abstr()+","+parentName()+" parent " +
            ","+id2Name()+" id2 " +
            " from " + tabName()+" where code='"+code+"'";
   }
   
  
   public static List<P2Node> trailsUp(XPump pp,int id)throws Exception{    
       P2Node p2 = P2Node.getP2Node(pp,InheritanceTree,id);
       P2Node pn = P2Node.getP2Node(pp,InheritanceTree,p2.idOrigin());
       List<P2Node> list = new ArrayList<P2Node>();
       P2Node p = P2Node.getP2Node(pp,InheritanceTree,0);
       list.add(p);
       if(pn.id()>0){///////////////////////////////////////////////////////////////////////////
           list.add(pn); 
           for(int i=1;i>0;i--){
            List<P2Node> parents = P2Node.getInhParents(pp,list.get(i).id()); 
            for(P2Node node:parents){
               if(!contains(list,node)){
                   list.add(1,node);
                   i++;
               }
            }
           }
       }
       return list;
    }   
    
   static boolean contains(List<P2Node> list,P2Node p){
    for(P2Node px:list){
        if(px.id()==p.id())return true;
    }
    return false;
   } 
    SortedMap<String,String> box2wt(XPump pp,GuiTree.GuiNode gn)throws Exception{
        throw new Exception("box2wt for InhTree only!");}   
    String boxView(XPump pp,GuiTree.GuiNode gn)throws Exception{
        throw new Exception("boxView for InhTree only!");}
    String boxView(XPump pp,GuiTree.GuiNode gn,boolean ee)throws Exception{
        throw new Exception("boxView for InhTree only!");}    
    String editForm(XPump pp,GuiTree.GuiNode gn)throws Exception{
        throw new Exception("editForm for InhTree only!");} 
    
    String editFormShadow(XPump pp,GuiTree.GuiNode gn)throws Exception{
        throw new Exception("editForm for InhTree only!");}    
    
    String addForm(XPump pp,String typecode)throws Exception{
        throw new Exception("addForm for InhTree only!");} 
    public abstract String abr();
    public abstract String T();
    String path(XPump pp,GuiTree.GuiNode gn)throws Exception{
        return gn.code();
    }
    public String reportHeaderShadow(XPump pp,int type,boolean inBrief)throws Exception{
        throw new Exception("for InhTree only!"); 
    }
    public String reportHeaderView(XPump pp,int type,boolean inBrief)throws Exception{
        throw new Exception("for InhTree only!"); 
    }
    public String reportLineView(XPump pp,int oid,int type)throws Exception{
        throw new Exception("for InhTree only!"); 
    }
    public String reportLineShadow(XPump pp,int oid,int type)throws Exception{
        throw new Exception("for InhTree only!"); 
    }
    public abstract String abstr();
}