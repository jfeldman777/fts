package j5feldman;
import j5feldman.dbf.DbfImport;
import j5feldman.proc.basement.PhantomParent;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.http.*; 
import j5feldman.ex.*;
import j5feldman.proc.basement.*;
import java.net.*;
public abstract class GuiTree{
//    GuiTree iamGuiTree;
    FTree ft;
    XPump pp;
    List<GuiNode> nodes=new ArrayList<GuiNode>();
    public List<GuiNode> nodes(){
        return nodes;
    }
    GuiNode root;
    public IGuiNode root(){
        return root;
    }
    int NUM=0;
    
    public boolean isObj(){
        return ft==FTree.ObjectTree;
    }
    
    public boolean isType(){
        return ft==FTree.InheritanceTree||ft==FTree.TypeTree;
    }
    GuiNode getRoot(){
        return nodes.get(0);
    }
    String getRootCode()throws Exception{
        return getRoot().code();
    }
    public class GuiNode extends PNode implements IGuiNode{ 
        public String boxTypeView()throws Exception{
            if(ft==FTree.ObjectTree)return "";
            if(ft==FTree.InheritanceTree)return "";
            String[] cols = {"imadeit","itcamefrom","about"};
            String s = "";
            for(int i=0;i<cols.length;i++){
                String q = "select " + cols[i] +
                        " from type where id="+id;
                String val = pp.getS(q);
                TypeField tf = new TypeField(pp, cols[i]);
                s+="<tr><td>"+tf.getRus()+"</td><td>"+
                        tf.fvalue(this,val, false)+"</td></tr>"; 
            }
            return s;            
        }
        
        String lastUpdate;
        public String lastUpdate(){
            return lastUpdate;
        }
        
        public String baseForm()throws Exception{
            Table t = new Table(pp,code,false,abc());//abc
            String s=t.baseForm()+
                    "<input type=hidden name=typecode value="+code+">";
            return s;
        }
        public String baseView()throws Exception{
            Table t = new Table(pp,code,false,abc());
            return t.baseView();
        }
        
        boolean isObj(){
            return ft==FTree.ObjectTree;
        }
        
        public boolean isType(){
            return ft==FTree.InheritanceTree||ft==FTree.TypeTree;
        }
        
        public FTree getFT(){
            return ft;
        }
        //////////////////////////////////////////////////////////////
        GuiNode parentNode;
        
        GuiNode parentNode(){
            return parentNode;
        }
        
        boolean shadow=false;
        boolean pseudo=false;
        boolean collapsed=true;
        int depth=0;
        protected String icon;
        int num;
        boolean hasChildren=true;
        int iChildren=0;
        
        public int num(){
            return num;
        }
        
        public boolean hasChildren(){
            return hasChildren;
        }
        
        public void setPseudo(){
            pseudo=true;
        }
        ////////////////////////////////////////////////////////////
        GuiNode(int id,String code,String name,int parent,
                int id2,int type,String typecode,String icon,
                boolean abstr,boolean abc,boolean fin, String lastUpdate){
            super(id,code,name,parent,id2,type,typecode,abstr,abc, fin); 
            this.icon = icon;
            this.lastUpdate = lastUpdate;
            num=isObj()?NUM++:id;
        }
    GuiNode(int id,String code,String name,GuiNode parentNode,
                int id2,int type,String typecode,String icon,
                boolean abstr,boolean abc,boolean fin, String lastUpdate){
            super(id,code,name,parentNode.id(),id2,type,typecode,abstr,abc, fin); 
            this.parentNode = parentNode;
            this.icon = icon;
            this.lastUpdate = lastUpdate;            
            num=isObj()?NUM++:id;
        }
    
    public DbfImport getDbfImport(int parent,String fname)throws Exception{
        if(!typecode.equals("dbf_import"))throw new ExMustBeDbfImport();
        return new DbfImport(pp,fname,this,"IBM866",parent); 
    }
    
    
        
    public String saveWxml(boolean show)throws Exception{
        File f = new File(pp.pubPathWt()+type+".xml");
        if(!f.exists())throw new ExWordTemplate(pp.iLang);
        
        RwFile rw = new RwFile();
        String Q = rw.read(f,true);
        
        SortedMap<String,String> map = FTree.InheritanceTree.box2wt(pp,this);
        Substitution sub = new Substitution(pp);
        String res = sub.wordSub(Q,map);
        
        if(show)return res;
        
        File f2 = new File(pp.pubPathWd()+id()+".xml");

        rw.write(res,f2);
        return pp.pubpathWd()+id()+".xml";
    }    

    public List<IGuiNode> pathUp()throws Exception{
        List<IGuiNode> list=new ArrayList<IGuiNode>();
        list.add(this);
        if(id==pp.meid||parentNode==null)
            return list;
        list.addAll(0,parentNode.pathUp());
        return list;
    } 
        
    public String fpath(String tab,String col,char tctype)throws Exception{
        String myPath = tctype=='p'?pp.picPath():pp.docPath();
        return myPath+"/"+tab+"/"+col+"/"+id()+TabCol.getExt(col);
    }
    
        public String innerLBtypes() throws Exception{
            String xtypecode = typecode;
            int xtype = type;
            int idx = id;
            if(xtypecode.equals("folder"))
            while(xtype == type){
                idx = pp.getN("select parent from object where id="+idx); 
                xtype = pp.getN("select type from object where id="+idx);
            }
            String S = "";
            String q = "select id,code,name,parent,abstr,abc,fin,id2 from type " +
                    " where id!=2  and id!=0 and id2=0 and not abstr and id22=0 and parent="+xtype+
                    " order by code ";
            List<P2Node> children = P2Node.getP2NodeS(pp,q);
            for(P2Node pn:children){
                S+="<option value="+pn.code()+">"+pn.codeName()+
                        "</option>";
            }
            q = "select t.id, t.code,t.name,s.parent,t.abstr,t.abc,t.fin,t.id2 from type t,type s " +
                    " where t.id!=2 and t.id!=0 and s.id2>0 and s.id2=t.id and s.parent="+xtype+
                    " order by t.code ";
            List<P2Node> children2 = P2Node.getP2NodeS(pp,q);
            for(P2Node pn:children2){
                S+="<option value="+pn.code()+">"+pn.codeName()+
                        "</option>";
            }
            P2Node pn = P2Node.getP2Node(pp,FTree.TypeTree,2);
            S+="<option value="+pn.code()+">"+pn.codeName()+
                        "</option>";
            return S;
        }
        
        public boolean isPseudo() {
            return pseudo;
        }
        
        public boolean isShadow() {
            return shadow;
        }
        
        public String typeImgCode2()throws Exception { 
            String name = pp.getS("select name from type where code='" +typecode+"'");
            if(name==null||name.equals(""))name=typecode;
            return  "<a href='/fel3/obj/do/word2.jsp?id=" +id()+
                    "'>"+
                    "<img alt='"+typecode+"' "+
                    " border=0 src='"+pp.iconpath()+
                    icon+".gif'>&nbsp;"+name+"</a>"
                    ;
        }
        
        public String typeImgCode()throws Exception { 
            String name = pp.getS("select name from type where code='" +typecode+"'");
            if(name==null||name.equals(""))name=typecode;
            return "<img alt='"+typecode+"' "+
                    " border=0 src='"+pp.iconpath()+
                    icon+".gif'>&nbsp;"+name;
        }
        
        public String typeImgCode(XPump pp)throws Exception { 
            String name = pp.getS("select name from type where code='" +typecode+"'");
            if(name==null||name.equals(""))name=typecode;
            return "<img alt='"+typecode+"' "+
                    " border=0 src='"+pp.iconpath()+
                    icon+".gif'>&nbsp;"+name;
        }
        public String face() {
            return "<img alt='"+typecode+"' "+
                    " border=0 src='"+pp.iconpath()+
                    icon+".gif'>&nbsp;"+code;
        }
        public String faceName() {
            return "<img alt='"+typecode+"' "+
                    " border=0 src='"+pp.iconpath()+
                    icon+".gif'>&nbsp;"+codeName();
        }        
        String icon(){
            return icon;
        }
        
        public boolean isAbc() {
            return abc;
        }
        
        public void setAbc(boolean val)throws Exception {
            abc=val;
            String q = "update type set abc="+
                    (val?1:0)+" where id="+id;
            pp.exec(q);
        }
        
        public boolean isBlind() {
            return isObj()&& isAbstr();
        }
        
        public void setBlind(boolean val)throws Exception {
            abstr=val;
            String q = "update object set abstr="+
                    (val?1:0)+" where id="+id;
            pp.exec(q);
        }        
        public String getFunctionDesc() throws Exception {
            return "????";
        }
        
        public String exportBaseVal()throws Exception{
            String t = typecode;
            Table tt = new Table(pp,t,false);
            String q = "select * from "+t+" where id=0 ";
            String s = "SCRIPT \nSET \n\n";
            Map<String,String> m = tt.line2map(0);
            for(ITabCol f:tt.fields){
                if(f.name().equals("id"))continue;
                String x = m.get(f.name());
                if(f.isKey()){
                    String y = pp.getS("select code from object where id="+x);
                    s+=t+"::"+f.name()+" BASE "+f.refTab()+":"+y+"\n";
                }else{
                    s+=t+"::"+f.name()+" BASE "+x+"\n";
                }              
            }
            return s;
        }
        public String boxView(boolean noe)throws Exception{
            return FTree.InheritanceTree.boxView(pp,this,noe);
        }
        public String boxView()throws Exception{
            return FTree.InheritanceTree.boxView(pp,this);
        }
        public void setName(String name)throws Exception{

            this.name = name;
            String q = "update type set name='"+name+"' where id="+id;
            pp.exec(q);
        }
        public String editForm() throws Exception {
            String s = "<input type=hidden name='object::id' value="+
                    id()+">"+
                    "<input type=hidden name='id' value="+
                    t()+num+">"+
                    "<input type=hidden name=typecode value="+
                    typeCode()+">";
                return s+FTree.InheritanceTree.editForm(pp,this);
        }
        
        public String editFormShadow() throws Exception {
            String s = "<input type=hidden name='object::id' value="+
                    id()+">"+
                    "<input type=hidden name='id' value="+
                    t()+num+">"+
                    "<input type=hidden name=typecode value="+
                    typeCode()+">";
                return s+FTree.InheritanceTree.editFormShadow(pp,this);
        }
        public void createType(String xcode,String xname,String inh,boolean abstr,String icon)
                throws Exception{
            if(xcode==null)return;
            xcode = xcode.trim();
            if(xcode.equals(""))return;
            int inhID;
            if(!abstr){
                String q2 = "select id from type where code='"+inh+"'";
                inhID = pp.getN(q2);
                if(inhID<0)throw new ExSuperType(pp.iLang);
            }else{
                inhID=id;
            }
            int ID = pp.getNewId(ft);
            xname = xname!=null?xname.trim():"";
            
            String q = "insert into type(id,parent,code,name,parent2,abstr)"+
                    "values("+ID+","+(abstr?0:id)+",'"+xcode+"','"+
                    xname+"',"+inhID+","+abstr+")";
            
            String q2 = null,q3=null;
            if(!abstr){
                q2 = "create table "+xcode+
                    " (id int, constraint pk_"+xcode+
                    " primary key(id),"+
                    " constraint fk_"+xcode+
                    " foreign key(id) references object "+
                    " on delete cascade) ";
                q3 = "insert into "+xcode+" (id)values(0)";
            }
            String q4 = null;
            if(icon!=null){
                q4="update type set icon='"+icon+"' where id="+ID;
            }
            String[] QQ = new String[]{q,q2,q3,q4};
            try{
                pp.exec(QQ);
            }catch(Exception e){
                if(e.getMessage()!=null&&
                        e.getMessage().indexOf("uq_type")>0)throw new ExTypeCode(pp.iLang);
                else throw e;
            }
            
            refreshParentId(parent);
        }
        
        
        public void doInsert(HttpServletRequest req) throws Exception {
            doInsert(req2map(req));
        }
        public void doInsert(SortedMap<String,String> map) throws Exception {
            doInsert(map,true) ;
        }
       
        public void doInsert(SortedMap<String,String> map,boolean refresh) throws Exception {
            List<String> QQ = new ArrayList<String>(); 
            String typecode = map.get("typecode");
            int type;
            try{
                type = Integer.parseInt(typecode);
            }catch(Exception e){
                type = pp.getN("select id from type where code='"+typecode+"'");
            }
            doInsert(type,QQ,map,refresh);
            pp.exec(QQ);
            if(refresh)refreshParentId(parent);
        }
        
        public void doInsert(int type,List<String> QQ,SortedMap<String,String> map,boolean refresh) throws Exception {
            int ID = pp.getNewId(ft);          
            List<P2Node> typecodes = 
              FTree.trailsUp(pp,type);                     
   
            for(P2Node p2:typecodes){
                if(!p2.isAbstr()){
                    Table t = pp.fat.getTable(pp,p2.code());                    
                    QQ.add(t.qInsert(map,ID,id(),type));
                }
            }

        }
      public void doInsert(int type,List<String> QQ,List<TreeMap<String,String>> mapList,int parent) throws Exception {
          List<P2Node> typecodes = FTree.trailsUp(pp,type);  
          List<Table> tList = new ArrayList<Table>();
          for(P2Node p2:typecodes){
                if(!p2.isAbstr()){
                    Table t = pp.fat.getTable(pp,p2.code());                    
                    tList.add(t);
                }
          }
          for(SortedMap<String,String> map:mapList){ 
            int ID = pp.getNewId(ft);            
            for(Table tx:tList){               
                    QQ.add(tx.qInsert(map,ID,parent,type));
                }
            }

        }
      
      
         public void doInsert(int type,List<String> QQ,TreeMap<String,String> map,
                 int parent,List<Table> tList) throws Exception {
            int ID = pp.getNewId(ft);            
            for(Table tx:tList){               
                    QQ.add(tx.qInsert(map,ID,parent,type));
                }

        }     

        
        public void doUpdateLine(String tabcol,String val,int dtype)throws Exception{           
            String tabname = TabCol.getTab(tabcol);
            String colname = TabCol.getCol(tabcol);
            Field f = new Field(pp,tabname,colname,dtype);
            f.setValue(val);
            String q = "update "+tabname+" set "+colname+"="+f.value()+" where id="+id;
            String q3 = "insert into "+tabname+" (id,"+colname+")values(" +id+
                    ","+f.value()+")";
            
//            String q2= "update object set last_update=dateob() where id="+id;
            
            //try{
            String q5 = "select count(*) from "+tabname+" where id="+id;
            int N = pp.getN(q5);
            if(N>0)pp.exec(q);
            else pp.exec(q3);
//                pp.exec(q2); 
                
                pp.setLastUpdate(id);
                
            //}catch(Exception e){}
            if(tabcol.equals("object::code")||tabcol.equals("object::name"))
                refreshNodeId(id);
        }
        
        public void doUpdate(HttpServletRequest req) throws Exception {
            SortedMap<String,String> map = req2map(req);
            if(ft==FTree.ObjectTree){
                String typecode = map.get("typecode");
                if(typecode==null){
                    String stype = map.get("type");
                    if(stype!=null){
                        typecode = pp.getS("select code from type where id="+stype);
                    }else{
                        typecode=typeCode();
                    }
                }
                map.put("object::id",""+id());
                P2Node pn =
                  P2Node.getP2NodeS(pp,FTree.InheritanceTree,typecode).get(0);
                List<P2Node> typecodes = FTree.trailsUp(pp,pn.id());
    
                List<String> QQ = new ArrayList<String>();
                for(P2Node p2:typecodes){
                    if(!p2.isAbstr()){                    
                        Table t = pp.fat.getTable(pp,p2.code());
                        QQ.add(t.qUpdate(map));
                    }
                }
                pp.exec(QQ);//////////////////////////////////////////////////
                refreshParentId(parent);
            }else{
                List<String> ar = new ArrayList<String>();
                for(Map.Entry e:map.entrySet()){
                    if(e.getKey().equals("id"))continue;
                    String q = "delete from translator where tabcol='"+
                            e.getKey()+"'";
                    ar.add(q);
                    String q2 =
                            "insert into translator(tabcol,rus)values('"+
                            e.getKey()+"','"+e.getValue()+"')";
                    ar.add(q2);
                }
                pp.exec(ar);
            }
        }
        public String addForm(String typecode) throws Exception {
            String s = "<input type=hidden name=parent value="+
                    T()+id+">"+
                    "<input type=hidden name=typecode value="+
                    typecode+">";
            return s+FTree.InheritanceTree.addForm(pp,typecode);
        }
        public String desForm() throws Exception {
            String s = "<input type=hidden name=parent value="+
                    T()+id()+">"+
                    "<input type=hidden name=typecode value="+
                    typecode+">";
            Field f = new Field(pp,"object","code");            
            Field f2 = new Field(pp,"object","name");
            s+="<tr><td>"+f.getRus()+"</td>"+f.addCell()+"</tr>";
            s+="<tr><td>"+f2.getRus()+"</td>"+f2.addCell()+"</tr>";
            return s;
        }
        public String showIcons()throws Exception{
            Icons icons = new Icons(pp,pp.iconPath(),typeCode());
            return icons.show();
        }
        
        public void setIcon(String icon) throws Exception {

            String q = "update type set icon='"+icon+"' where id="+idOrigin();
            pp.exec(q);
            refreshParentId(id);
        }
        
   //////////////////////////////////////////guiNode end
    }
    public String show()throws Exception{
        String s="";
        for(GuiNode gn:nodes){
            s+= show(gn);
        }
        return s;
    }
    
    public void expand(int n)throws Exception{
        GuiNode parent = nodes.get(n);
        if(!parent.collapsed)
            return;
        //pp.hOcode=pp.
        List<GuiNode> children = getGuiChildren(parent);
        Object[] bbb = children.toArray();
        Arrays.sort(bbb);
        //for(GuiNode c : children){
        int K = n+1;
        for(int i=0;i<bbb.length;i++){
            GuiNode c = (GuiNode)bbb[i];
            c.parentNode = parent;
            c.depth = parent.depth+1;
            c.num = isObj()?NUM++:c.id;
            c.pseudo = parent.shadow||parent.pseudo;
            c.shadow = c.id2>0;

            c.iChildren=ft.iChildren(pp,c.idOrigin());
            c.hasChildren = (pp.meid==0||!c.isBlind()) && (c.iChildren>0);            

            if(isObj()&&PhantomParent.hasPhantomChildren(pp,c.typeCode()))
                c.hasChildren |=PhantomParent.hasPhantomChildren(pp,c.typeCode(),c.id);

            nodes.add(K++,c);
        }
        //nodes.addAll(n+1,children);/////////////////////////////////////////
        
        if(isObj()){
            if(PhantomParent.hasPhantomChildren(pp,parent.typeCode())){
                nodes.addAll(n+1,getPhantomChildren(parent));
            }            
        }
        
        parent.collapsed=false;
    }
    
    public void expand2(int id)throws Exception{
            int K = nodes.size()-1;
        GuiNode parent = nodes.get(K);
        GuiNode c = getGuiNode(id);

        c.parentNode = parent;
        c.depth = parent.depth+1;
        c.num = isObj()?NUM++:c.id;
        c.pseudo = false;
        c.shadow = false;

        c.iChildren = ft.iChildren(pp,id);
        c.hasChildren = (pp.meid==0||!c.isBlind()) && (c.iChildren>0);        
        nodes.add(c);
        
        parent.collapsed=false;
    }
    
    public void showPath(int id)throws Exception{
        List<P2Node> L2 = P2Node.pathUp(pp,ft,id);
        L2.remove(0);
        collapse(0);
        for(P2Node p:L2){
            expand2(p.id());
        }
}
    
    List<GuiNode> getPhantomChildren(GuiNode parent)throws Exception{
        List<GuiNode> gnList = new ArrayList<GuiNode>();
        PhantomParent fp = new PhantomParent(pp,parent);        
        List<PhantomParent.TabCol> tcList = fp.getTcList();
        for(PhantomParent.TabCol tc:tcList){
            gnList.addAll(getGuiNodeS(fp.qPhantoms(tc.tab,tc.col,tc.blind))); 
        }
        
        for(GuiNode c : gnList){
            c.parentNode = parent;
            c.depth = parent.depth+1;
            c.num = NUM++;//isObj()?NUM++:c.id;
            c.pseudo = true;//parent.shadow||parent.pseudo;
            c.shadow = false;//c.id2>0;

             c.iChildren = ft.iChildren(pp,c.idOrigin());
            c.hasChildren = (pp.meid==0||!c.isBlind()) && (c.iChildren>0);             
        }
        return gnList;
    }
    
    public GuiTree(XPump pp)throws Exception{
        this.pp = pp;
//        iamGuiTree = this;
    }
    
    public int n2originId(int n)throws Exception{
        int id = nodes.get(n).id();
        return pp.getN(ft.qOriginId(id));
    }
    
    public int num2id(int Num)throws Exception{
        if(!isObj())return Num;
        for(GuiNode gn:nodes){
            if(gn.num==Num)
                return gn.id();
        }
        throw new ExUnseen(pp.iLang);
    }
    public int num2n(int Num)throws Exception{
        String s = "//";
        
        
        for(int i=0;i<nodes.size();i++){
            if(nodes.get(i).num==Num)
                return i;
            
            s+=nodes.get(i).num+"/";
        }
        
        
        
        throw new ExUnseen(pp.iLang);
    }
    GuiNode num2node(int Num)throws Exception{
        for(GuiNode gn:nodes){
            if(gn.num==Num)
                return gn;
        }
        throw new ExUnseen(pp.iLang);
    }
    
    public GuiNode id2node(int ID)throws Exception{
        for(GuiNode gn:nodes){
            if(gn.id==ID)
                return gn;
        }
        throw new ExUnseen(pp.iLang);
    }
    public int id2nx(int ID)throws Exception{
        for(int i=0;i<nodes.size();i++){
            GuiNode gn = nodes.get(i);
            if(gn.id==ID)return i;
        }
        return 0;
    }
    public int id2n(int ID)throws Exception{
        for(int i=0;i<nodes.size();i++){
            GuiNode gn = nodes.get(i);
            if(gn.id==ID)return i;
        }
               
        throw new ExUnseen(pp.iLang);
    }
    public GuiNode getItem(ResultSet rs)throws Exception{
        return getGuiNode(rs);
    }
    
    protected GuiNode getGuiNode(String q)throws Exception{
        GuiNode gn=null;
        Result r=new Result();
        try{
            pp.select(r,20,q);
            r.rs.next();
            gn = getGuiNode(r.rs);
        } finally{r.close();}
        return gn;
    }
    
    public GuiNode getGuiNode(int id)throws Exception{
        String q=ft.qGuiNode(id);
        return getGuiNode(q);
    }
    
    public GuiNode getOriginNode(int id)throws Exception{
        String q=ft.qOriginNode(id);
        return getGuiNode(q);
    } 
    protected GuiNode getGuiNode(ResultSet rs)throws Exception{
        int id=rs.getInt("id");
        String code=rs.getString("code");
        String name=rs.getString("name");
        int parent=rs.getInt("parent");
        int id2=rs.getInt("id2");
        int type=rs.getInt("type");
        String typecode = rs.getString("typecode");
        String icon = rs.getString("icon");

        boolean abstr = rs.getBoolean("abstr");
        
              
        boolean abc = rs.getBoolean("abc");
        boolean fin = rs.getBoolean("fin");
        String lastUpdate = rs.getString("last_update");
        return new GuiNode
                (id,code,name,parent,id2,type,typecode,icon,abstr,abc,fin, lastUpdate);
    }
    

    
    public GuiNode getNode(int n)throws Exception{
        return nodes.get(n);
    }
    
    List<GuiTree.GuiNode> getGuiNodeS(ResultSet rs)throws Exception{
        List<GuiNode> list = new ArrayList<GuiNode>();
        while(rs.next()){
            list.add(getGuiNode(rs));
        }
        return list;
    }
    List<GuiNode> getGuiNodeS(String q)throws Exception{
        List<GuiNode> L = null;
        Result r=new Result();
        try{
            pp.select(r,21,q);
            L = getGuiNodeS(r.rs);
        } finally{r.close();}
        return L;
    }
    
    public List<GuiNode> getGuiChildren(String q)throws Exception{        
        return getGuiNodeS(q);
    }
   
    public List<GuiNode> getGuiChildren(GuiNode parent)throws Exception{
        int id = parent.idOrigin();
        String q1 = ft.qGuiChildren1(id); 
        String q2 = ft.qGuiChildren2(id,pp.isShadow("object","code"));        
        List<GuiNode> list = getGuiChildren(q1);
        List<GuiNode> list2 = getGuiChildren(q2);
        list.addAll(list2);
        return list;
    }
    
    public void collapse(int n)throws Exception{
        GuiNode parent = nodes.get(n);
        if(parent.collapsed) return;
        while(n<nodes.size()-1 && nodes.get(n).depth < 
                nodes.get(n+1).depth){
            nodes.remove(n+1);
        }
        parent.collapsed=true;
    }
    protected void refreshGranpa(GuiNode node)throws Exception{
        try{
            int nParent = id2n(node.parent);
            if(nParent==0){
                collapse(nParent);
                expand(nParent);
                return;
            }            
            GuiNode p = nodes.get(nParent);
            int granpa = id2n(p.parent);
            collapse(granpa);
            expand(granpa);
        } catch(Exception e){
        }
    }
    protected void refreshGranpa(int n)throws Exception{
        try{
            GuiNode node = nodes.get(n);
            int nParent = id2n(node.parent);
            if(nParent==0){
                collapse(nParent);
                expand(nParent);
                return;
            }            
            GuiNode p = nodes.get(nParent);
            int granpa = id2n(p.parent);
            collapse(granpa);
            expand(granpa);
        } catch(Exception e){
        }
    }

    public void refreshId(int id)throws Exception{
        try{int n = id2n(id);collapse(n);expand(n);}catch(Exception e){return;}
    }
    public void collapseParentId(int id)throws Exception{

            int N = id2nx(id);
            try{
                GuiNode node = nodes.get(N);
                int parentId = node.parent;
                int n = id2n(parentId);
                collapse(n);
            }catch(Exception e){
                collapse(N);
                return;
            }

    }
    public void refreshParentId(int id)throws Exception{
            int N = id2nx(id);  

                    GuiNode node = nodes.get(N);
                    if(N>0){
                        int parentId = node.parent;
                        int parentN = id2n(parentId);    
                        collapse(parentN);
                        expand(parentN);
                        N = id2n(id);                             
                    }
                    collapse(N);
                    expand(N);    

    }
    public void refreshNodeId(int id)throws Exception{
            int N = id2nx(id);  
                    GuiNode node = nodes.get(N);
                    if(N>0){
                        int parentId = node.parent;
                        int parentN = id2n(parentId);    
                        collapse(parentN);
                        expand(parentN);
                        N = id2n(id);                             
                    }
                    collapse(N);
    }
    boolean isShadow(int id)throws Exception{
        return !pp.isOrigin(ft,id);
    }
    public void drop(int n)throws Exception{

        GuiNode gn = nodes.get(n);
        int id = gn.id();
        
        if(gn.isAbstr()||isShadow(id)) pp.exec(ft.qDrop(id));
        else pp.exec(ft.qqDrop(pp,id));
        
        pp.exec("delete from refhh where tab='" +gn.code+"'");
        pp.exec("delete from shadowcols where tab='" +gn.code+"'");
        
        refreshGranpa(n);
    }
    boolean isCircle(int id,int parentId)throws Exception{
        List<P2Node> path = P3Node.pathUp(pp,ft,parentId);
        for(P2Node p2:path){
            if(p2.id==id)return true;
        }
        return false;
    }
    void checkMove(int id,int parentId)throws Exception{
        if(isCircle(id,parentId))
            throw new ExBadLoop(pp.iLang);
    }
    void checkMakeShortcut(int id,int parentId)throws Exception{
        if(isCircle(id,parentId))throw new ExBadLoop(pp.iLang);
    }
    

    
    public void move(int n,int parentId)throws Exception{
            int id = nodes.get(n).id();
            moveId(id,parentId);
    }
      
        
     public void moveId(int id,int parentId)throws Exception{       
        String q = "select count(*) from "+ft.tabName()+
                " where id="+parentId+
                " and (id2>0 "+(ft!=FTree.ObjectTree?" or id22>0 ":"")+")";
        if(pp.getN(q)!=0)throw new ExNoHook(pp.iLang);
                
        
        if(id==parentId)throw new ExBadLoop(pp.iLang);
        GuiNode gn = id2node(id);
        boolean isShadow = gn.isShadow();
        String theTable = gn.typeCode();
        
        checkMove(id,parentId);
        pp.exec(ft.qMove(id,parentId));
        
        refreshGranpa(gn);
        refreshParentId(parentId);
        if(isShadow)return;
        if(ft==FTree.InheritanceTree){
            List<Integer> ii = pp.getNN("select id from "+theTable+" where id>0 ");
            List<P2Node> list = P2Node.pathUp(pp,ft,parentId);
            for(P2Node p2:list){
                if(!p2.isAbstr()&&p2.id()>0){
                    String tname = p2.code();
                    for(int k:ii){
                        q = "insert into "+tname+"(id) values ("+k+")";
                        try{
                            pp.exec(q);
                        }catch(Exception e){}
                    }
                }
            }
        }
    }
    public void makeShortcutList(int io,int parent)throws Exception{
        String q = "select id from object where id2=0 and parent="+parent;
        String q2 = "select o.id from object o,object o2 where " +
                " o.id=o2.id2 and o2.id2>0 and o2.parent="+parent;
        List<Integer> ii = pp.getNN(q);
        List<Integer> ii2 = pp.getNN(q2);
        ii.addAll(ii2);
        for(Integer ioParent:ii){
            if(!isCircle(io,ioParent)){
                makeShortcutId(io,ioParent,false);
            }
        }
    }
    public void makeShortcutNum(int n,int parentId,boolean blind)throws Exception{
        int originId = n2originId(n);
        makeShortcutId(originId,parentId, blind);
        collapseParentId(parentId);        
    }
    
    public void makeShortcut(IGuiNode gn,int parentId,boolean blind)throws Exception{
        int originId = gn.idOrigin();
        makeShortcutId(originId,parentId, blind);
        collapseParentId(parentId);        
    }
    
    public void makeShortcutId(int originId,int parentId,boolean blind)throws Exception{
        checkMakeShortcut(originId,parentId);
        int ID = pp.getNewId(ft);
        List<String> qq = new ArrayList<String>();
        try{
        pp.exec(ft.qMakeShortcut(originId,parentId,ID, blind));//////////shadows!!!!
        }catch(Exception e){throw new ExParentCode(pp.iLang);}
        if(ft==FTree.ObjectTree){
            int type = pp.getN("select type from object where id="+originId);
            List<P2Node> L = FTree.trailsUp(pp,type); 
            for(P2Node p:L){
                if(pp.hasTab(p.code)){ 
                    if(p.code.equals("object")){
//                        String q = "insert into object (id,code,type)values(" + ID +",'_" +ID+"'," +type+")";
//                        qq.add(q);
                    }else{
                        String q = "insert into " + p.code + " (id)values(" + ID +")";
                        qq.add(q);
                    }
                }
            }

        }else
        if(ft==FTree.InheritanceTree){
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
                            pp.exec(q);
                        }catch(Exception e){}
                    }
                }
            }
        }
        try{
            pp.exec(qq);
        }catch(Exception e){
            System.out.println(e.getMessage());
            pp.exec2(qq);
        }
    }
    public static String iconImg(String img){
        return "<img border=0 src=/feldman-root/icon/"+img+".gif border=0>";
    }
    public boolean isOpen(GuiNode node)throws Exception{return true;}
    String bridge(){
        return "";
    }
    String show(GuiNode gn)throws Exception{
        String S,sL="",sR="";
        for(int k=0;k<gn.depth;k++){sL+=iconImg("line")+"</td><td>";}//minus
        sL+=gn.collapsed
                ?gn.hasChildren?
                    "<a title='" +gn.iChildren+
                //"click to see next level" +
                "' href='" +
                (gn.iChildren < pp.page3
                ?"expand.jsp?id="+t()+gn.num
                :"javascript:alert(" +gn.iChildren+");")
                +"'>"+iconImg("plus")+
                "</a></td><td>"
                :iconImg("treelines")+"</td><td>"
                :"<a title='click to hide next level' href=collapse.jsp?id="+
                t()+gn.num+">"+iconImg("minus")+"</a></td><td>";
        String a=isOpen(gn)?
            "<a title='"+//gn.depth+
                gn.lastUpdate()+
                "' target=right  href='" +
                bridge()+//""
                "object.jsp?id="+t()+gn.num+
                "'>":"";
        String a2 = isOpen(gn)?"</a>":"";
        String r60,r31,r1,r2,r3,r4,r5,r6,r7,r8,r9,r56="";
        
        r1 = icon(gn);
        r2 = "</td><td nowrap  colspan="+(20-gn.depth)+ ">&nbsp;";
        r3 = (gn.pseudo||gn.shadow?"<i>":"");
        r31= (gn.abstr?"<b>":"");
        r4 = a+gn.code()+ a2;
//        String name = name20(gn);///////////////////////////////////////////
        

        if(gn.id()==0)r5="";else
        r5 = (gn.name().equals("")?"":"("+name20(gn)+")");
        
        r60= (gn.abstr?"</b>":"");
        r6 = (gn.pseudo?"</i>":"");
        r7 = "";//icon2(node);
        r8 = HB(gn);
        r9 = "</td></tr>";
        
        sR=r1+r2+r3+r31+ r4+r5+r56+ r60+r6+r7+r8+r9;
        S="<tr><td>"+sL+sR;
        return S;
    }
    
    String name20(GuiNode gn){
        int L = gn.name().length();
        
//        return L<21?gn.name():
//            gn.name().substring(0,20)+"<a title='" +gn.name()+"'>...</a>";
        
        return "<a title='" +gn.name()+"'>"+
                (L<21?gn.name():gn.name().substring(0,20)+"...") +
                "</a>";
    }
    
    String HB(GuiNode node) throws Exception {return "";}
    String icon(GuiNode node) throws Exception{
        String code = node.typeCodeName(pp);
        String icon = node.icon();
        if(pp.mTree)
            return  "<img border=0 src="+pp.iconpath()+icon+".gif alt='"+node.id()+"'>";
        if(ft==FTree.InheritanceTree)
            if(pp.meid!=0)//node.isShadow()||node.isPseudo())
                return "<img border=0 src="+pp.iconpath()+icon+".gif>";
             else return 
            "<a href='icon/showicon.jsp?id="+t()+node.num+
                "' title='set icon' target=right>" +
                 "<img border=0 src="+pp.iconpath()+icon+".gif></a>";
                else 
                    return        
            "<a href='proc/frame.jsp?id="+t()+node.num+
                "' title='" + code +"/"+node.type+
                "' target=right><img border=0 src="+pp.iconpath()+icon+".gif></a>";
    }
       
    /**request to map*/
    public static SortedMap<String,String> req2map(HttpServletRequest req){
        SortedMap<String,String> map = new TreeMap<String,String>();
        Map<String,String[]> m = req.getParameterMap();
        for(String key:m.keySet()){
            String sv = m.get(key)[0];
            map.put(key, space210(sv));
        }
        return map;
    }
    static String space21(String s){
        if(s.indexOf("  ")<0)  return s;
        else return space21(s.replaceAll("[ ][ ]", " "));
    }
    
    static String space210(String s){
        return space21(s.trim());
    }
    
    public FTree getFT(){
        return ft;
    }
   
    abstract String T();
    abstract String t();
     
    public static List<Table> getTableList(int type,XPump pp) throws Exception {
          List<P2Node> typecodes = FTree.trailsUp(pp,type);  
          List<Table> tList = new ArrayList<Table>();
          for(P2Node p2:typecodes){
                if(!p2.isAbstr()){
                    Table t = pp.fat.getTable(pp,p2.code());                    
                    tList.add(t);
                }
          }
          return tList;
    }    
}