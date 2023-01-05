/*
 * CreateObj.java
 *
 * Created on 7 Март 2007 г., 9:35
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.nio.charset.*;
import j5feldman.proc.*;
import j5feldman.ex.*;
import j5feldman.schema.Schema;
/**
 *
 * @author Jacob Feldman
 */
public class CreateObj {
    XPump pp;
    IGuiTree objTree;   
    int parentId;
    String typecode;
    /** Creates a new instance of CreateObj */
    public CreateObj(XPump pp,IGuiTree objTree,int parentId,String typecode) {
        this.pp = pp;
        this.objTree = objTree;
        this.parentId = parentId;
        this.typecode = typecode;
    }
    
    public int create(int patternID,int iFun)throws Exception{
        switch(iFun){
            case 11: return createN(patternID);//"Создать объекты по типу (укажите количество)";   
            case 13: return createFolderAbove();//"Создать папку между этим объектом и его родителем";             
            case 15: return createShortcut(patternID);
//            case 21: return createAs;//"Создать по типу и имени-образцу: несколько:a(+a)";
            
            case 22: return nameList(patternID);//"Создать по типу и имени-образцу: адрес родителя";
            
            case 31: return copyObjTree(patternID,true,true);//"Скопировать дерево:адрес корня: коды новые";
            case 32: return copyObjTree(patternID,true,false);//"Скопировать дерево:адрес корня: коды старые";
            case 41: return copyObjTree(patternID,false,true);//"Скопировать дерево без корня: адрес старого корня:коды новые";
            case 42: return copyObjTree(patternID,false,false);
//"Скопировать дерево без корня: адрес старого корня:коды старые";
            case 43: return copyLayer(patternID,true);//"Скопировать слой: адрес старого отца:коды новые";
            case 44: return copyLayer(patternID,false);//"Скопировать слой: адрес старого отца:коды старые";
//"Скопировать дерево без корня: адрес старого корня:коды старые";            
            case 51: return copyNrefresh(patternID,true);//"Скопировать объект: адрес: код новый";
            case 52: return copyNrefresh(patternID,false);//"Скопировать объект: адрес: код старый";
            
            case 61: return types2objects(false);//"Создать дерево по дереву типов, коды = коды типов";
            case 62: return types2objects(true);//"Создать дерево по дереву типов, коды = новые номера";
            
//            case 71: typecode = pp.getS("select code from type where id="+patternID);
//                return createBlankAs("");//"Создать новый объект по номеру типа";

//            case 80: return fromFile;//"Создать объекты согласно скрипту в файле";
            case 91: return fromQuery(patternID);//"Создать ярлыки на попавших в запрос(адрес):параметры в имени";
                }        
        return 0;
    }
    
    public int createFolderAbove()throws Exception{
        int granpa = pp.getN("select parent from object where id="+parentId);
        SortedMap<String,String> map = new TreeMap<String,String>();
        map.put("parent",""+granpa);
        map.put("typecode","folder");        
        List<String> qqA = new ArrayList<String>();
        int ID = pp.getNewId(FTree.ObjectTree);  
        qqA.addAll(qqInsert(map,ID));
        qqA.add("update object set parent="+ID+" where id="+parentId);
        pp.exec(qqA); 
        objTree.refreshId(granpa);
        return 1;
    }
    
     public int createFolderAboveNoRefresh()throws Exception{
        int granpa = pp.getN("select parent from object where id="+parentId);
        SortedMap<String,String> map = new TreeMap<String,String>();
        map.put("parent",""+granpa);
        map.put("typecode","folder");        
        List<String> qqA = new ArrayList<String>();
        int ID = pp.getNewId(FTree.ObjectTree);  
        qqA.addAll(qqInsert(map,ID));
        qqA.add("update object set parent="+ID+" where id="+parentId);
        pp.exec(qqA); 
        return ID;
    }
    
    public int fromFile(String fpath)throws Exception{
        File f = new File(fpath);
        if(!f.exists())throw new Exception("no such file:"+fpath);
        int type = pp.getN("select id from type where code='"+typecode+"'");
        
        BufferedReader br=null;
        List<String> qqA = new ArrayList<String>();    
        int M=0;
        try{
            br = new BufferedReader(new InputStreamReader(
                new FileInputStream(f),Charset.forName("windows-1251")));
            String s="";
            while(null!=(s=br.readLine())){
                M++;
                qqA.addAll(qqInsert(txt2map(s),typecode,""+parentId));
            }

        }finally{if(br!=null)br.close();}
        pp.exec(qqA);
        objTree.refreshParentId(parentId);
        return M;
    }
    
    SortedMap<String,String> txt2map(String txt){
        SortedMap<String,String> ar = new TreeMap<String,String>();
        StringTokenizer st = new StringTokenizer(txt,";");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            int i = token.indexOf("=");
            if(i<0)continue;
            ar.put(token.substring(0,i),token.substring(i+1,token.length()));
        }
        return ar;        
    }    
   
    private int types2objects(boolean bnum)throws Exception{
        String name = pp.getS("select name from object where id="+parentId);
        int typeRoot = pp.getN("select id from type where code='"+typecode+"'");
        List<P3Node> types = P3Node.allDown3(pp,FTree.TypeTree,
                P3Node.getP3Node(pp,FTree.TypeTree,typeRoot));
        List<String> qqA = new ArrayList<String>();
        Map<Integer,Integer> i2i = new HashMap<Integer,Integer>();
        for(P3Node typeNode:types){
            List<P2Node> upTables = FTree.trailsUp(pp,typeNode.id());//types
            int ID = pp.getNewId(FTree.ObjectTree);
            i2i.put(typeNode.id(),ID);

            SortedMap<String,String> map = new TreeMap<String,String>();
            Integer iParent = i2i.get(typeNode.parent());
            if(iParent==null) iParent = parentId;
            map.put("parent",""+iParent);
            map.put("typecode",typeNode.code());
            if(!bnum)map.put("object::code",typeNode.code());
            map.put("object::name",name);
            qqA.addAll(qqInsert(map,ID));
        }
        pp.exec(qqA);
        objTree.refreshParentId(parentId);
        return types.size();
    }
    
    private int fromQuery(int idOrigin)throws Exception{
        String Q = pp.getS("select body from query where id="+idOrigin);
        String parentName = pp.getS("select name from object where id="+parentId);
        String parDef = pp.getS("select params from query where id="+idOrigin);
        
        Substitution sub = new Substitution(pp);
        String Q2 = sub.substituteNN(Q,parentName,parDef);
        List<Integer> iiList = pp.getNN(Q2);
        for(int i:iiList){
            if(i>0)objTree.makeShortcutId(i,parentId, false);
        }
        objTree.collapseParentId(parentId);          
        return iiList.size();
    }
    
    int createShortcut(int patternID)throws Exception{
        System.out.println("patternID:"+patternID);/////////////////////
        int originID = pp.getN(FTree.ObjectTree.qOriginId(patternID));
        System.out.println("originID:"+originID);  //////////////////////
        objTree.makeShortcutId(originID,parentId,false);
        objTree.refreshParentId(parentId);
        return 1;
    }

    int createN(int N)throws Exception{
        SortedMap<String,String> map = new TreeMap<String,String>();
        map.put("parent",""+parentId);
        map.put("typecode",typecode);        
        List<String> qqA = new ArrayList<String>();
        for(int i=0;i<N;i++){
            qqA.addAll(qqInsert(map));
        }
        pp.exec(qqA);      
        objTree.refreshParentId(parentId);       
        return N;
    }

    int nameList(int patternID)throws Exception{
        SortedMap<String,String> map = new TreeMap<String,String>();        
        int iList = patternID;//tn2id(objTree,addr);
        String q = "select distinct oo2.name "+
                " from object oo2 "+
                " where oo2.name <>'' and oo2.name is not null and oo2.parent="+iList;
        String q2 = "select distinct oo2.name "+
                " from object oo,object oo2 "+
                " where oo2.name <>'' and oo2.name is not null and "+
                " oo.parent="+iList+" and oo.id2>0 and oo.id2=oo2.id";
        String q3 = "select distinct oo2.name "+
                " from object o,object o2,object oo,object oo2 "+
                " where oo2.name <>'' and oo2.name is not null and oo.parent=o2.id and o.id="+iList+
                " and o.id2>0 and o.id2=o2.id and oo.id2>0 and oo.id2=oo2.id";
        String q4 = "select distinct oo2.name "+
                " from object o, object oo2 "+
                " where oo2.name <>'' and oo2.name is not null and oo2.parent=o.id2 and o.id="+iList+
                " and o.id2>0";
        List<String> arNames =  pp.getSS(q);
        arNames.addAll(pp.getSS(q2));
        arNames.addAll(pp.getSS(q3));
        arNames.addAll(pp.getSS(q4));

        List<String> qqA = new ArrayList<String>();
        for(String name:arNames){
            map.put("object::name",name);
            qqA.addAll(qqInsert(map,typecode,""+parentId));
        }
        pp.exec(qqA);
        objTree.refreshParentId(parentId);   
        return arNames.size();
    }   
    
    private int copyLayer(int patternID,boolean bnum)throws Exception{
        List<Integer> II = pp.getNN("select id from object where id2==0 and parent="+patternID+" order by code ");
        for(int i:II){copyOne(i,bnum);}
        
        objTree.refreshParentId(parentId);
        return II.size();
    } 
    
    private int copyObjTree(int patternRoot,boolean copyRoot,boolean bnum)throws Exception{
//        int patternRoot = Session.tn2id(objTree,list);
        List<P3Node> patternNodes = P3Node.allDown3(pp,FTree.ObjectTree,
                P3Node.getP3Node(pp,FTree.ObjectTree,
                patternRoot));
        int newRoot=-1;   
        List<String> qqA = new ArrayList<String>();
        List<String> QQ = new ArrayList<String>();
        Map<Integer,Integer> i2i = new HashMap<Integer,Integer>();
        for(P2Node node:patternNodes){//objects
            if(!copyRoot&&node.id()==patternRoot)continue;
            int ID = pp.getNewId(FTree.ObjectTree);
            i2i.put(node.id(),ID);
            Integer iParent = i2i.get(node.parent());
            if(iParent==null){
                iParent = parentId;
                newRoot = ID;
            }
            QQ.addAll(qqInsertCopy(ID, iParent, node.type(),node.id(),bnum));
        }
        
        pp.exec(QQ);
        
        objTree.refreshParentId(parentId);
        return patternNodes.size();
    } 
    
    private int copyNrefresh(int pattern,boolean bnum)throws Exception{
        copyOne(pattern,bnum);
        objTree.refreshParentId(parentId);
        return 1;
    }
    
    private void copyOne(int pattern,boolean bnum)throws Exception{   
        int ID = pp.getNewId(FTree.ObjectTree);
        pp.exec(qqInsertCopy(ID, parentId, pattern,bnum));
    } 

    //////////////////////////////////////////
    String getLine(int n){
        switch(pp.iLang){
            case 1://Eng
                switch(n){
                    case 10:  return "Create-fill one object of type";                    
                    case 11:  return "Create objects of type (how many)";
                    case 13:  return "Insert folder above";                     
                    case 15:  return "Create shortcut (original address)";
                    case 21: return "Create objects of type and name-pattern: a(+a)";
                    case 22: return "Create objects of type and name-pattern: parent addr";
                    case 31: return "Copy tree: root addr: new codes";
                    case 32: return "Copy tree: root addr: old codes";
                    
                    case 41: return "Copy tree without root: old root addr: new codes";
                    case 42: return "Copy tree without root: old root addr: old codes";
                    
                    case 43: return "Copy layer: old parent addr: new codes";
                    case 44: return "Copy layer: old parent addr: old codes";
                    
                    case 50: return "Copy-edit one object:";                    
                    case 51: return "Copy object: addr: new code";
                    case 52: return "Copy object: addr: old code";
                    
                    case 61: return "Create tree as typetree: codes as type codes/name as root";
                    case 62: return "Create tree as typetree: new codes/name as root";
                    
                    case 70:  return "Create-fill one object: type-ID";
                    case 71:  return "Create one object: type-ID";            
                    
//                    case 81:  return "Script from file";
                    case 91:  return "Shortcuts from query(adr): pars from name";
                }
            default://Rus
                switch(n){
                    case 10:  return "Создать-наполнить один объект по типу";                   
                    case 11:  return "Создать объекты по типу (укажите количество)";
                    case 13:  return "Вставить папку выше";               
                    case 15:  return "Создать ярлык (адрес прототипа)";                    
                    case 21: return "Создать по типу и имени-образцу: несколько:a(+a)";
                    case 22: return "Создать по типу и имени-образцу: адрес группы";
                    case 31: return "Скопировать дерево:адрес корня: коды новые";
                    case 32: return "Скопировать дерево:адрес корня: коды старые";
                    case 41: return "Скопировать дерево без корня: адрес старого корня:коды новые";
                    case 42: return "Скопировать дерево без корня: адрес старого корня:коды старые";
                    
                    case 43: return "Копировать объекты: адрес группы: коды новые";
                    case 44: return "Копировать объекты: адрес группы: коды старые";                    
                    
                    case 50: return "Скопировать-изменить один объект";     
                    case 51: return "Скопировать объект: адрес: код новый";
                    case 52: return "Скопировать объект: адрес: код старый";

                    case 61: return "Создать дерево по дереву типов, коды = коды типов/имя с корня";
                    case 62: return "Создать дерево по дереву типов, коды = новые номера/имя с корня";
                    
                    case 70: return "Создать-изменить объект по номеру типа";                    
                    case 71: return "Создать новый объект по номеру типа";                    
//                    case 81: return "Создать объекты согласно скрипту в файле";
                    case 91: return "Создать ярлыки на попавших в запрос(адрес):параметры в имени";
                }
        }
        return "";
    }
    
    String gL(int iFun){
        return  "<option value="+iFun+">("+iFun+")"+getLine(iFun)+"</option>\n";
    }
    
    public String optionListA(){
        return gL(10)+gL(11)+gL(13)+
                gL(15)+gL(21)+gL(22)+gL(31)+gL(32)+
                gL(41)+gL(42)+gL(43)+gL(44)+
                gL(50)+gL(51)+gL(52)+gL(61)+gL(62);
    }
    
    public String optionListB(){
        return gL(70)+
                gL(71)+
                //gL(81)+
                gL(91);
    }
    
/////////////////////////////////////////////////////////////    
    List<String> qqInsert(SortedMap<String,String> map,String tcode,String parent) throws Exception {
        map.put("typecode",tcode);
        map.put("parent",parent);
        return qqInsert(map);
    } 
    private List<String> qqInsert(SortedMap<String,String> map) throws Exception {
        int ID = pp.getNewId(FTree.ObjectTree);
        return qqInsert(map,ID);
    }
    
    private List<String> qqInsert(SortedMap<String,String> map,int ID) throws Exception{ 
        String typecode = map.get("typecode");
        int parent = Integer.parseInt(map.get("parent"));
        if(map.get("object::code")==null)map.put("object::code",""); 
        
        P2Node pn = P2Node.getP2NodeS(pp,FTree.InheritanceTree,typecode).get(0);
        List<P2Node> tP2Nlist = P2Node.pathUp(pp,FTree.InheritanceTree,pn.id());
        List<String> QQ = new ArrayList<String>();

        for(P2Node p2:tP2Nlist){
            if(!p2.isAbstr()){ 
                Table t = pp.fat.getTable(pp,p2.code());

                QQ.add(t.qInsert(map,ID,parent,pn.id()));
            }
        }
        return QQ;
    }
    
    private List<String> qqInsertCopy(int ID,int parent,int iProto,boolean bnum) throws Exception{
        int type = pp.getN("select type from object where id="+iProto);      
        return qqInsertCopy(ID,parent,type,iProto,bnum);
    }
    
    private List<String> qqInsertCopy(int ID,int parent,int type,int iProto,boolean bnum) throws Exception{ 
        List<P2Node> tP2Nlist = P2Node.pathUp(pp,FTree.InheritanceTree,type);       
        List<String> QQ = new ArrayList<String>();

        for(P2Node p2:tP2Nlist){
            if(!p2.isAbstr()){ 
                Table t = pp.fat.getTable(pp,p2.code());

                SortedMap<String,String> map = t.line2map2(iProto);
                if(bnum)map.put("object::code",""); 
                QQ.add(t.qInsert(map,ID,parent,type));
            }
        }
        return QQ;
    }      
}
