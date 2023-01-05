/*
 * Sql.java
 *
 * Created on 24 Февраль 2005 г., 14:07
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.charset.*;
import j5feldman.ex.*;

/**
 *
 * @author Jacob Feldman
 */
public class Sql {
    boolean dbg=false;
    XPump pp;
    String path;
    File f;
    final static String encUtf="UTF-8";    
    final static String enc1251="Windows-1251";    
    /** Creates a new instance of Sql */
    
    public Sql(XPump pp){
        this.pp = pp;
    }
    public Sql(XPump pp,File f){
        this.pp = pp;
        this.f = f;
    }
    
    void parse(List<String> list,String qq)throws Exception{
        StringTokenizer t = new StringTokenizer(qq,"\n");
        while(t.hasMoreTokens()){
            String Sx = t.nextToken();
            String S = noComments(Sx);
                    S = S.trim();
            if(S.equals(""))continue;
            int i = S.indexOf("drop"); if(i>=0) throw new Exception("drop?");
            i = S.indexOf("schema"); if(i>=0) throw new Exception("schema?");
            list.add(S);
        }
    }
    
    public void area2db(String qq,boolean dbg)throws Exception{////
        this.dbg=dbg;
        List<String> list = new ArrayList<String>();
        parse(list,qq);
        if(list.size()==0)return;
        
        int k=list.get(0).indexOf("FILES");
        if(k<0)exList(list); else exFiles(list);
        
        pp.initFat();
    }
    
    void exFiles(List<String> list)throws Exception{
        int N = list.size();
        for(int i=1;i<N;i++){
            String last = "";
            String encoding = this.enc1251;//encUtf;            
            int k=list.get(i).indexOf("SCRIPT");
            int j=list.get(i).indexOf("SQL");              
            if(k>=0){last = sR(list.get(i),"SCRIPT");encoding = enc1251;}
            else if(j>=0){last = sR(list.get(i),"SQL");encoding = encUtf;} else continue;
            
            List<String> list2 = new ArrayList<String>();
            String fp = f.getParent()+"\\"+last;
            System.out.println("\n:: "+fp);
            File ff = new File(fp);
            String buf;
            BufferedReader br=null;
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(ff),
                    Charset.forName(encoding)));
            while(null!=(buf=br.readLine())){
                buf = noComments(buf);
                buf = buf.trim();                
                if(buf.equals(""))continue;
                list2.add(buf);
            }
            System.out.println("\n size="+list2.size());
     
            exList(list2);
        }
    }
    
    void exList(List<String> list)throws Exception{
        System.out.println(list);
        int k=list.get(0).indexOf("QUERY");  
        if(k>=0){
            query2db(list);
            return;
        }
        int i=list.get(0).indexOf("SCRIPT");    
        if(i>=0){
            script2db(list);
        }else{
            pp.exec(list);
        }
    }
    
    void query2db(List<String> list)throws Exception{
        List<String> QQ = new ArrayList<String>();
        List<String> Qx = new ArrayList<String>();
        String S2 = list.get(0);
        String pat = "QUERY";
        QScriptor qSc = new QScriptor(sL(S2, pat), sR(S2, pat));        
        String line = "???";
        try{
            for(String S:list){
            line = S;
            line = line.trim();
            if(line.equals("")) continue;
            if(line.charAt(0)=='/') continue;
            S = noComments(S);
                    if(S.indexOf(pat="INTO2")>=0){
                        qSc.addInto2(sL(S,pat),sR(S, pat));continue;
                    }//incl-2            
                    if(S.indexOf(pat="INTO")>=0){
                        qSc.addInto(sL(S,pat),sR(S, pat));continue;
                    }//included
                    if(S.indexOf(pat="KEYREF2")>=0){
                        qSc.addOf(sL(S, pat), sR(S, pat)); continue;
                    }//key-ref
                    if(S.indexOf(pat="KEYREF")>=0){
                        qSc.addOf(sL(S, pat), sR(S, pat)); continue;
                    }//key-ref
                    if(S.indexOf(pat="OF")>=0){
                        qSc.addOf(sL(S, pat),sR(S, pat));
                    }
            }
        }catch(Exception e){throw new ExScriptLine(line,pp.iLang);} 
        List<String> L =  qSc.qqx(dbg);
        if(dbg)pp.exec2(L);//exception
        pp.exec(L);        
    }
    
    void script2db(List<String> list)throws Exception{
        List<String> QQ = new ArrayList<String>();
        List<String> Qx = new ArrayList<String>();
        code2id = new TreeMap<String,Integer>();        
        String pat;
        boolean start = true;
        String line = "???";
        //try{
            for(String S:list){
            line = S;
            line = line.trim();
            if(line.equals("")) continue;
            if(line.charAt(0)=='/') continue;
            S = noComments(S);
            int i=0;
            if(S.indexOf("SCRIPT")==0) continue;
                if(start){//first parts = types
                  if((S.indexOf("SET"))>=0){
                        if(!dbg)pp.exec(QQ);
                        setMap(); 
                        start = false;
                        continue;
                    }else Qx.addAll(qqxType(S.trim(),-(++i))); 
                }
                    if(S.indexOf(pat="ICON")>=0){
                        Qx.add(qxIcon(sL(S, pat),sR(S,pat)));continue;
                    }//
            
//                    if(S.indexOf(pat="FLASH")>=0){
//                        Qx.add(qxFlash(sL(S,pat)));continue;
//                    }//            
            
                    if(S.indexOf(pat="UNIQUE")>=0){
                        Qx.add(qxUnique(sL(S, pat)));continue;
                    }//            
                    if(S.indexOf(pat="BASE")>=0){
                        Qx.add(qxBase(sL(S, pat),sR(S,pat)));continue;
                    }//
                    if(S.indexOf(pat="VALUE")>=0){
                        Qx.add(qxValue(sL(S, pat),sR(S,pat)));continue;
                    }//object-shadow              
                    if(S.indexOf(pat="OBJECT2")>=0){
                        Qx.add(qxObj2(sL(S, pat),sR(S,pat)));continue;
                    }//object-shadow             
                    if(S.indexOf(pat="OBJECT")>=0){///////////////////MOVE?????????
                        Qx.addAll(qqxObj(sL(S,pat),sR(S,pat)));continue;
                    }//new-object
                    if(S.indexOf(pat="FROM2")>=0){
                        Qx.addAll(qqxInh2(iL(S,pat),iR(S,pat)));continue;
                    }//inh-2
                    if(S.indexOf(pat="FROM")>=0){
                        Qx.addAll(qqxInh(iL(S,pat),iR(S,pat)));continue;
                    }//inherited
                    if(S.indexOf(pat="PIC")>=0){////////////////////////PIC2???????????
                        Qx.addAll(cxPic(sLL(S),sLR(S,pat),sR(S,pat)));continue;
                    }
                    if(S.indexOf(pat="DOC")>=0){/////////////////////////DOC2???????????
                        Qx.addAll(cxDoc(sLL(S),sLR(S,pat),sR(S,pat)));continue;
                    }    
                    if(S.indexOf(pat="INTO2")>=0){
                        Qx.add(qxType2(iL(S,pat),iR(S,pat)));continue;
                    }//incl-2            
                    if(S.indexOf(pat="INTO")>=0){
                        Qx.add(qxType(iL(S,pat),iR(S,pat)));continue;
                    }//included
                    if(S.indexOf(pat="KEYREF2")>=0){
                        Qx.addAll(qqxKey(sLL(S),sLR(S,pat),sR(S,pat),true)); continue;
                    }//key-ref
                    if(S.indexOf(pat="KEYREF")>=0){
                        Qx.addAll(qqxKey(sLL(S),sLR(S,pat),sR(S,pat),false)); continue;                        
                    }//key-ref
///////////////////////////////////////////////IMADEIT, ITCAMEFROM, ABOUT
                    if(S.indexOf(pat="IMADEIT")>=0){
                        Qx.add(qxType(sL(S,pat),"imadeit",sR(S,pat))); continue;                        
                    }//i made it
                    if(S.indexOf(pat="ITCAMEFROM")>=0){
                        Qx.add(qxType(sL(S,pat),"itcamefrom",sR(S,pat))); continue;                         
                    }//it came from
                    if(S.indexOf(pat="ABOUT")>=0){
                        Qx.add(qxType(sL(S,pat),"about",sR(S,pat))); continue;                           
                    }//about
             /////////////////////////////////////////////
            
            ////////////////////////DELETEGREEN, DLETEYELLOW, DELETEBLUE
                    for(String pt:DbFormat.formats){
                        String pt2=pt+"2";
                        if(S.indexOf(pt2)>=0){
                            Qx.addAll(qxField(sLL(S),sLR(S,pt2),pt,sR(S,pt2),true));continue;
                        }
                        if(S.indexOf(pt)>=0){
                            Qx.addAll(qxField(sLL(S),sLR(S,pt),pt,sR(S,pt),false));continue;
                        }
                    }                
            }
        //}catch(Exception e){throw new ExScriptLine(line);}
        
        if(dbg)pp.exec2(Qx);
    }

    String qxType(String type,String col,String txt)throws Exception{
        String q = "update type set "+ col + "='"+txt+
                "' where code='" + type+"'";
        if(dbg)return q;
        pp.exec(q);
        return "";
    }
    
    void shadowCol(String tab,String col,char x)throws Exception{
        char[] xx = new char[]{x};
        String s = new String(xx);
        String qIns = "insert into shadowcols (tab,col,tctype)values('" +tab+
                "','" + col + "','" +s+ "')";
        try{
            pp.exec(qIns);
            pp.addShadow(tab,col,x);
        }catch(Exception e){}
    }    
    
    List<String> cxPic(String tab,String col,String name)throws Exception{
        return cxPicDoc(tab,col,name,true);
}    
    List<String> cxDoc(String tab,String col,String name)throws Exception{
        return cxPicDoc(tab,col,name,false);
}    
    List<String> cxPicDoc(String tab,String col,String name,boolean pic)throws Exception{
        List<String> qq = new ArrayList<String>();           
        shadowCol(tab, col,'d');
        if(name!=null&&!name.equals("")){
            qq.add("delete from translator where tabcol='"+tab+"::"+col+"'");
            qq.add("insert into translator (rus,tabcol)values('"+name+"','"+tab+"::"+col+"')");
        }        
        String path = pic?pp.picPath():pp.docPath() + "/"+tab+"/"+col;
        File file = new File(path);
        if(!dbg){
            file.mkdirs();
            pp.exec(qq);            
            return new ArrayList<String>();
        }
        return qq;
    }

    List<String> qqxObj(String typecode,String R)throws Exception{
        String path = sL(R, "NAME");
        try{
            int child = P2Node.path2id(pp,FTree.ObjectTree,path); 
        }catch(Exception e){
            SortedMap<String,String> map = new TreeMap<String,String>();
            map.put("object::name",sR(R, "NAME"));
            return qqxObj(typecode,path,map);
        }
        return new ArrayList<String>();
    }
    List<String> qqxObj(String typecode,String path,SortedMap<String,String> map)
    throws Exception{
        String pathL = path.substring(0, path.lastIndexOf("/"));
        String pathR = path.substring(path.lastIndexOf("/")+1).trim();
        int parent = P2Node.path2id(pp,FTree.ObjectTree,pathL); 
        
        int ID = pp.getNewId(FTree.ObjectTree);        
        map.put("object::code",pathR);
        map.put("xuser::pwd", pathR);
        P2Node pn = 
              P2Node.getP2NodeS(pp,FTree.InheritanceTree,typecode).get(0);
        List<P2Node> typecodes = 
              FTree.trailsUp(pp,pn.id());                     
        List<String> QQ = new ArrayList<String>();

            for(P2Node p2:typecodes){
                if(!p2.isAbstr()){
                    Table t = pp.fat.getTable(pp,p2.code());

                    QQ.add(t.qInsert(map,ID,parent,pn.id()));
                }
            }
            if(dbg) return QQ;
            pp.exec(QQ);
            return new ArrayList<String>();       
        
    }
    
    String qxUnique(String tabcol)throws Exception{
        String tab = sL(tabcol, "::");
        String col = sR(tabcol, "::");
        String q = "alter table "+ tab + " add constraint uq_" +tab+"_"+col+
                " unique (" + col+")";
        if(dbg)return q;
        pp.exec(q);
        return "";
    }
    List<String> qxField(String tab,String col,String format,String R,boolean shadow)throws Exception{
        if(shadow)shadowCol(tab, col,'c');
        List<String> qq = new ArrayList<String>();        
        String q = "alter table "+tab+" add column "+col+" "+format+" ";
        qq.add(q);
        if(format.equals("4"))q+=" default 0 ";
        if(format.equals("16"))q+=" default false ";
        
        if(R!=null&&!R.equals("")){
            qq.add("delete from translator where tabcol='"+tab+"::"+col+"'");
            qq.add("insert into translator (rus,tabcol)values('"+R+"','"+tab+"::"+col+"')");
        }
        if(dbg)return qq;
        pp.exec(qq);
        return new ArrayList<String>();
    }
    List<String> qqxKey(String tab,String col,String R,boolean shadow)throws Exception{
        List<String> qq = new ArrayList<String>();            
        if(shadow)shadowCol(tab, col,'c');
        String ref = sL(R, "NAME");
        String RR = sR(R,"NAME");
        
        String q = "alter table "+tab+" add column "+col+" int default 0 ";
        String q3= "alter table "+tab+" add constraint fk_"+tab+"_"+col+
                " foreign key ("+col+")references "+ref;     
        qq.add(q);
        qq.add(q3);
        if(RR!=null&&!R.equals("")){
            qq.add("delete from translator where tabcol='"+tab+"::"+col+"'");
            qq.add("insert into translator (rus,tabcol)values('"+RR+"','"+tab+"::"+col+"')");
        }
        
        if(dbg) return qq;
        pp.exec(qq);
        return new ArrayList<String>();
    }
    String qxType(int child,int parent)throws Exception{
        String q = "update type set parent="+ parent + "  where id=" + child;
        if(dbg)return q;
        pp.exec(q);
        return "";
    }
    List<String> qqxInh(int child,int parent)throws Exception{
        String q = "update type set parent2="+ parent + "  where id=" + child;
        List<P2Node> typecodes = 
              FTree.trailsUp(pp,parent);                     
        List<String> QQ = new ArrayList<String>();
        QQ.add(q);
        String childType = pp.getS("select code from type where id="+child);
        List<Integer> II = pp.getNN("select id from "+childType);
        for(int i:II){
            for(P2Node p2:typecodes){
                if(!p2.isAbstr()){
                    if(p2.code().equals("xuser"))
                        QQ.add("insert into xuser(id,pwd)values("+i+",'"+i+"')");
                    else if(!p2.code().equals("object"))
                        QQ.add("insert into "+p2.code()+" (id)values(" +i+
                            ")");
                }
            }
        }
        if(dbg) return QQ;
        pp.exec1(QQ);
        return new ArrayList<String>();
    }
    String qxType2(int originId,int parentId)throws Exception{
        int ID = pp.getNewId(FTree.TypeTree);        
        String q = FTree.TypeTree.qMakeShortcut(originId,parentId,ID,false);
        if(isCircle(originId,parentId,FTree.TypeTree))
            throw new Exception("bad circle!");        
        if(dbg)return q;
        pp.exec(q);
        return "";
    }
    List<String> qqxInh2(int originId,int parentId)throws Exception{
        FTree ft = FTree.InheritanceTree;  
        if(isCircle(originId,parentId,ft))throw new Exception("bad circle!");
        List<String> qq = new ArrayList<String>();

        int ID = pp.getNewId(ft);
        String q2=ft.qMakeShortcut(originId,parentId,ID,false);
        if(!dbg)pp.exec(q2);else qq.add(q2);
  
        String typecode = pp.getS
         ("select t.code from type t where t.id=" + originId);
            List<Integer> ii = pp.getNN("select id from "+typecode+" where id>0 ");
            List<P2Node> list = P2Node.pathUp(pp,ft,parentId);
            for(P2Node p2:list){
                if(!p2.isAbstr()&&p2.id()>0){
                    String tname = p2.code();
                    for(int k:ii){
                        String q = "insert into "+tname+"(id) values ("+k+")";
                        try{
                            if(!dbg)pp.exec(q);else qq.add(q);                            
                        }catch(Exception e){}
                    }
                }
        }    
            return qq;
    }  
    String qxValue(String sL,String sR)throws Exception{
        int ID = P2Node.path2id(pp,FTree.ObjectTree,sL); 
        String TabCol = sL(sR, "=");
        String Val = sR(sR, "=");
        String Tab=sLL(TabCol);
        String Col=sLR(TabCol);
        String baseVal = "0";
        String refTab = "";
        String keyVal = sR(Val,":");
        if(!keyVal.equals("")){
            refTab = sL(Val, ":");
            baseVal = pp.getS("select o.id from object o," +refTab+
                    " t where o.id=t.id and code='" +keyVal+
                    "'");
        }
        else {
            baseVal = Val;
        } 

        String q = "update "+Tab+" set " +Col+
                "='" +baseVal+
                "' where id="+ID;
        if(dbg) return q;
        pp.exec(q);
        return "";
    }
    String qxBase(String sL,String sR)throws Exception{
        String baseVal = "0";
        String refTab = "";
        String keyVal = sR(sR,":");
        if(!keyVal.equals("")){
            refTab = sL(sR, ":");
            baseVal = pp.getS("select o.id from object o," +refTab+
                    " t where o.id=t.id and code='" +keyVal+
                    "'");
        }
        else {
            baseVal = sR;
        } 
        String tab = sL(sL, "::");
        String col = sR(sL, "::");
        String q = "update "+tab+" set " +col+
                "='" +baseVal+
                "' where id=0";
        if(dbg) return q;
        pp.exec(q);
        return "";
    }
//    String qxFlash(String sL)throws Exception{
//        String type = sL;
//        String q = "update type set flash=true where code='" +type+"'";
//
//        if(dbg) return q;
//        pp.exec(q);
//        return "";
//    } 
    String qxIcon(String sL,String sR)throws Exception{
        String type = sL;
        String icon = sR;
        String q = "update type set icon='" +icon+
                "' where code='" +type+"'";

        if(dbg) return q;
        pp.exec(q);
        return "";
    } 
    String qxObj2(String pathL,String pathR)throws Exception{
        int originId = P2Node.path2id(pp,FTree.ObjectTree,pathL);
        int parentId = P2Node.path2id(pp,FTree.ObjectTree,pathR);        
        int ID = pp.getNewId(FTree.ObjectTree);        
        String q = FTree.ObjectTree.qMakeShortcut(originId,parentId,ID,false);
        if(dbg) return q;
        pp.exec(q);
        return "";
    }
    SortedMap<String,Integer> code2id;
    void setMap()throws Exception{
        String q = "select id,code from type";
         Result r=new Result();
         String code="";
         int id;
         try{
            pp.select(r,25,q);
            while(r.rs.next()){
               id = r.rs.getInt("id"); 
               code = r.rs.getString("code");
               code2id.put(code,id);
            }      
         }finally{r.close();}
      }
    
    List<String> qqxTypeAbstr(String s,int i)throws Exception{
        String code = sL(s, "ABSTR");        
        String R = sR(s, "ABSTR");
            String[] QQ = {"insert into type (id,code,name,abstr)" +
                    "values(NEXTVAL('type_id'),'"+code+"','" + R + "',1)"};
            
        if(dbg){
            code2id.put(code, i);
            return Arrays.asList(QQ);
        }            
        pp.exec(QQ);
        return new ArrayList<String>();
    }
    List<String> qqxType(String s,int i)throws Exception{
        if(s.indexOf(" ABSTR ")>0) return qqxTypeAbstr(s,i);
                
        String code = sL(s, "NAME");        
        String R2 = sR(s, "NAME");
        String R = R2.trim();        

        if(pp.getN("SELECT count(*) FROM type where id=")>0){
            return new ArrayList<String>();
        }
        
        String[] QQ = {
"insert into type (id,code,name" +
        ")values(NEXTVAL('type_id'),'"+code+"','" + R +
        "')",
"create table "+code+"( id int,  constraint pk_"+code+
        " primary key (id),  constraint fk_"+code+
        " foreign key(id)references object  on delete cascade)",
"insert into "+code+"(id)values(0)"};
        if(dbg){
            code2id.put(code, i);
            return Arrays.asList(QQ);
        }
        pp.exec(QQ);
        return new ArrayList<String>();
    }

    String qInsert(int type)throws Exception{
        String s = "";    
        List<P20Node> list = P20Node.getP20type(pp,type);
        for(P20Node node:list){
            s+="insert into object (id,code,name,type,parent)values("+
            "NEXTVAL('object_id'),'"+node.code()+"','"+node.name()+"',"+type+",0)\n";
        }
        return s;
    }    
    String qInsert(String stype)throws Exception{
        int type = pp.getN("select id from type where code='"+stype+"'");
        String s = "";    
        List<P20Node> list = P20Node.getP20type(pp,type);
        for(P20Node node:list){
            s+="insert into object (id,code,name,type)select "+
            "NEXTVAL('object_id'),'"+node.code()+"','"+
                    node.name()+"',id from type where code='"+stype+"'" +
                    "\n";
        }
        return s;
    } 
    
    boolean isCircle(int id,int parentId,FTree ft)throws Exception{
        List<P2Node> path = P3Node.pathUp(pp,ft,parentId);
        P2Node pn = P2Node.getP2Node(pp,ft,id);
        return path.contains(pn);
    } 
    String noComments(String s){
        int i = s.indexOf("//");
        if(i<0)return s;
        return s.substring(0,i);
    }
    
    String sLR(String s)throws Exception{
        int i=s.indexOf("::");
        try{
            return s.substring(i+2).trim();
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }
    }    
    String sLR(String s,String pat)throws Exception{
        int i=s.indexOf("::");
        try{
            int j=s.indexOf(pat);
            return s.substring(i+2, j).trim();
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }        
    }
    String sLL(String s)throws Exception{
        int i=s.indexOf("::");
        try{
            return s.substring(0, i).trim();
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }        
    }
    String sR(String s,String pat)throws Exception{
        int i=s.indexOf(pat);
        if(i<0)return "";
        try{
            return s.substring(i+pat.length()).trim();
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }           
    }    
    String sL(String s,String pat)throws Exception{
        int i=s.indexOf(pat);
        if(i<0)return s;
        try{
            return s.substring(0,i).trim();
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }           
    } 
    int iR(String s,String pat)throws Exception{
        int i=s.indexOf(pat);
        try{
            return code2id.get(s.substring(i+pat.length()).trim());
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }
    }    
    int iL(String s,String pat)throws Exception{
        int i=s.indexOf(pat);
        try{
            return code2id.get(s.substring(0,i).trim());
        }catch(Exception e){
            throw new Exception("//s:"+s+"//"+e.getMessage());
        }
    }    
}
