package j5feldman;
import j5feldman.proc.basement.CreateObj;
import j5feldman.proc.basement.Find;
import j5feldman.proc.basement.Lock;
import j5feldman.proc.basement.PhantomParent;
import j5feldman.proc.basement.Sql;
import j5feldman.proc.basement.SqlExport;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.nio.charset.*;
import j5feldman.proc.*;
import j5feldman.proc.basement.*;
import j5feldman.proc.basement.Result;
import j5feldman.proc.basement.Icons;
import j5feldman.ex.*;
import j5feldman.schema.Schema;
import j5feldman.profile.*;
/**
 * @author Jacob Feldman
 */
public class Session{
    public void x() throws ExFTS{
        //if(false)throw new ExFTS();
    }
    public boolean notDemo(){
        return pp.notDemo;
    }
    
    public String dict(String x){
        return pp.dict(x);
    }
    
    public Profile profile;
    
    String ER = "R/";
    int iLang = 0;
    public int ver = 0;
    public void setER(String L)throws Exception{
        ER = L;
        if(L.equals("E/")){
            iLang=1;
        };
        if(L.equals("F/")){
            iLang=2;
        };   
        if(L.equals("D/")){
            iLang=3;
        };         
    }
    
    public synchronized void treepub(String sid)throws Exception{
        int iRoot = Integer.parseInt(sid);
        TreePub tp = new TreePub(pp,iRoot,objTree);
        tp.writeFiles();
    }
    
    public String ER(){
        return ER;
    }
    
    public static boolean adminOnly=false;
//    public void setSqlOut(boolean b){
//        pp.sqlOut = b;
//    }
//    public boolean help=false;
    public //ObjectGT 
    IGuiTree objTree; 
    TypeGT typeTree; 
    InhGT inhTree;
    ArTree arTree;
    NTypeTree ntypeTree;
    XPump pp;
    String uname;
    private IGuiTree tn2t(String tn)throws Exception{
        char c = tn.charAt(0);
        switch(c){
            case 'a':
            case 'A':
            case 'ф'://russ 
            case 'f':
            case 'а'://russ
                return objTree;
            case 't':
            case 'T':
                return typeTree;
            case 'h':
            case 'H':
                return inhTree;
            case 'n':
            case 'N':
                return ntypeTree;
        }
        
        return objTree;
    }
    boolean isAbs(String tn){
        char c = tn.charAt(0);
        switch(c){
            case 'A':   return true;
            case 'T':   return true;            
            case 'H':   return true;
            case 'N':   return true;
        }
        return false;
    }
    private int tn2in(String tn)throws Exception{
        if(Character.isDigit(tn.charAt(0)))
            return Integer.parseInt(tn);
        return Integer.parseInt(tn.substring(1));
    }
    private int tn2n(String tn)throws Exception{   
        IGuiTree t = tn2t(tn);        
        int in = tn2in(tn);

        if(isAbs(tn))return t.id2n(in); 
        else
        return t.num2n(in);
    }
    private int tn2id(IGuiTree t,String tn)throws Exception{
        if(Character.isDigit(tn.charAt(0)))
            return Integer.parseInt(tn);        
        int in = Integer.parseInt(tn.substring(1));
        return isAbs(tn)?in:t.num2id(in);
    }
    private int tn2id(String tn)throws Exception{
        if(Character.isDigit(tn.charAt(0))) return Integer.parseInt(tn);
        IGuiTree t = tn2t(tn);
        int in = Integer.parseInt(tn.substring(1));
        return isAbs(tn)?in:t.num2id(in);
    }    
   
    public void drop(String tn)throws Exception{
        IGuiTree t = tn2t(tn);
        t.drop(tn2n(tn));
    }
    public void collapse(String tn)throws Exception{
        IGuiTree t = tn2t(tn);
        t.collapse(tn2n(tn));
    }
    public synchronized void expand(String tn)throws Exception{
        IGuiTree t = tn2t(tn);
        t.expand(tn2n(tn));
    }
    public synchronized String login(String dbconfig,String schema,String root,String user,String pwd)throws Exception{
        return login(dbconfig,schema,root,user,pwd,0);
    }
    
    public synchronized String login(String dbconfig,String schema,String root,String user2,String pwd,int ver)throws Exception{
        String user = user2;
        this.uname = user;
        this.ver = ver;
        pp = new XPump(iLang,ER);

        pp.connect(dbconfig,schema);
        pp.rootPath = root;
        int tid=0;
        String hello="";

        Upgrade up = new Upgrade(pp);
        up.goTask();///////////////////////////////////////////////////////////// 
        
        if(user.equals("demo")&&pwd.equals("demo")){
            user="o";
            pp.notDemo=false;
            pp.meid=0;
            hello="DemoUser";
        }else{


            int m = pp.getN("select count(*) from object o,xuser u where "+
                    " o.id=u.id and o.code='"+user.trim()+"' and u.pwd='"+pwd.trim()+"'");
            if(m==0)
                return "guest";

            pp.meid = pp.getN("select o.id from object o,xuser u where "+
                    " o.id=u.id and o.code='"+user.trim()+"' and u.pwd='"+pwd.trim()+"'");
            setLastVisit();
            if(pp.meid>0 && adminOnly)throw new ExAdminOnlyMode(pp.iLang);
//            pp.exec("update object set last_update=dateob() where id="+pp.meid);
            pp.setLastUpdate(pp.meid); 

            tid = pp.getN("select o.type from object o where "+
                    " o.id="+pp.meid);
            hello = pp.getS("select hello from xuser where "+
                " id="+pp.meid);
        }

             
        if(ver==0){
            objTree = new ObjectGT(pp,pp.meid);
            typeTree = new TypeGT(pp,tid);
            inhTree = new InhGT(pp,0);
        }else if(ver==1){
            objTree = new ObjectGTqf(pp,pp.meid);
        }else if(ver==2){
            objTree = new ObjectGTnof(pp,pp.meid);
        }else throw new Exception("unknown version...");

        profile = new Profile(pp);
        profile.initTask(); 
        
        return hello==null||hello.equals("")?"Sir/Mm":hello;
    }
    
    public synchronized void loginD(String dbconfig,String schema,String root,String code)throws Exception{
       if(adminOnly)throw new ExAdminOnlyMode(pp.iLang); 
       if(pp==null){
            pp = new XPump(iLang,ER);
            pp.connect(dbconfig,schema);
            pp.rootPath = root;
        }
        String q = "select count(*) from xuser where id=0 and pwd='"+code.trim()+"'";
        if(pp.getN(q)==0){throw new ExIdCode(pp.iLang);}
        pp.meid=0;
        objTree = new ObjectGT(pp,0);      
            typeTree = new TypeGT(pp,0);
            inhTree = new InhGT(pp,0);        
    } 
    
    public synchronized void login2(String dbconfig,String schema,String root,String sid,String code)throws Exception{
       if(adminOnly)throw new ExAdminOnlyMode(pp.iLang); 
       if(pp==null){
            pp = new XPump(iLang,ER);
            pp.connect(dbconfig,schema);
            pp.rootPath = root;
        }
        String q = "select count(*) from object where id="+sid.trim()+" and code='"+code.trim()+"'";
        if(pp.getN(q)==0){throw new ExIdCode(pp.iLang);}
    }
    public String showTree()throws Exception{
        return this.objTree.show(); 
    }
    
    public String showTree(String tn)throws Exception{
        IGuiTree t = tn2t(tn);
        return t.show(); 
    } 
    public IGuiNode getGuiNode(String tn)throws Exception{
        IGuiTree t = this.objTree;
        if(Character.isDigit(tn.charAt(0))) return t.getGuiNode(Integer.parseInt(tn));
            
        t = tn2t(tn);
        return isAbs(tn)? t.getGuiNode(tn2id(tn)):
                         t.getNode(tn2n(tn));
    }
    
    public IGuiNode getGuiNode(int id)throws Exception{
        IGuiTree t = this.objTree;
        return t.getGuiNode(id);

    }
    
    public boolean isZ(){
        return pp.meid==0&&ver==0;
    }
    public String typeImgCode(String typecode)throws Exception{
        return "<img alt='"+typecode+"' "+
                " border=0 src='"+pp.iconpath()+
                pp.icon(typecode)+".gif'>&nbsp;"+typecode;
    }
    public String typeImgCodeName(String typecode)throws Exception{
        String q ="select name from type where code='" +typecode+"'";
        String name = pp.getS(q);
        String nameRcode = name==null||name.equals("")?typecode:name;
        return "<img alt='"+typecode+"' border=0 src='"+pp.iconpath()+
                pp.icon(typecode)+".gif'>&nbsp;"+nameRcode;
    }
    
    public synchronized void setFin(HttpServletRequest req)throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        icons.setFin(req); 
    }
    
    public String showFinIcons()throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        return icons.showFin();  
    }
    public String showFreeIcons()throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        return icons.showFree(); 
    }
    public String showTypeIcons(boolean noabstr)throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        return icons.showTypes(noabstr); 
    }    
    
    public void deleteIcon(String name)throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        icons.delete(name);
    }
    public long uploadIcon(HttpServletRequest req)throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        return icons.save(req);
    }
    public void renameIcon(String name,String name2)throws Exception{
        Icons icons = new Icons(pp,pp.iconPath(),null);
        icons.rename(name,name2);
    }
    public String find(String tn,String crit,boolean sh)throws Exception{
        IGuiTree t = tn2t(tn);
        Find f = new Find(pp,t.getFT());
        return f.find(crit,sh);
    }
    
    public String findMe(String tabname,String crit)throws Exception{
        Find f = new Find(pp,FTree.ObjectTree);
        return f.findMe(tabname,f.cleanMe(crit));
    }
    
    public void setRootUp(int id)throws Exception{   
        int parent = id==pp.meid?id                
                :pp.getN("select parent from object where id="+id);
        objTree = new ObjectGT(pp,parent);
                objTree.expand(0);
    }
    
    public void setRootUpInh(int id)throws Exception{   
        int parent = pp.getN("select parent2 from type where id="+id);
        inhTree = new InhGT(pp,parent);
        inhTree.expand(0);
    }    
    
    public void setRootUpType(int id)throws Exception{   
        int parent = pp.getN("select parent from type where id="+id);
        typeTree = new TypeGT(pp,parent);
        typeTree.expand(0);
    }    
    
    
    public void showTreePath(int id)throws Exception{   
        objTree = new ObjectGT(pp,pp.meid);
                objTree.showPath(id);
    }
    
    public void showTreePathInh(int id)throws Exception{   
        inhTree = new InhGT(pp,0);
        inhTree.showPath(id);
    }    
    
    public void showTreePathType(int id)throws Exception{   
        typeTree = new TypeGT(pp,0);
        typeTree.showPath(id);
    } 
    
    public void setRoot(String tn)throws Exception{
        int tid=0;
        char c=tn.charAt(0);
        int L = tn.length();
        if(L>1){
            IGuiTree t = tn2t(tn);
            int id = tn2id(t,tn);
            switch(c){
                case 't':case 'T':
                    tid=id==2?0:id;   
                    typeTree = new TypeGT(pp,tid);
                    break;
                case 'a':case 'A':
                    objTree = new ObjectGT(pp,id);
                    break;
                case 'h':case 'H':
                    inhTree = new InhGT(pp,id);
                    break;
                case 'n':case 'N':
                    ntypeTree = new NTypeTree(pp,id,ntypeTree.obj.id(),objTree);
                    break;
            }
        }else switch(c){
            case 't':case 'T':tid = pp.getN
                    ("select o.type from object o where o.id="+pp.meid);
            if(tid==2)tid=0;
            typeTree = new TypeGT(pp,tid);
            break;
            case 'a':case 'A':
                objTree = new ObjectGT(pp,pp.meid);
                break;
            case 'h': case 'H':
                inhTree = new InhGT(pp,0);
                break;
            case 'n':case 'N':
                ntypeTree = new NTypeTree(pp,0,ntypeTree.obj.id(),objTree);
                break;
        }
    }
    
    public String quickReport(String q)throws Exception{
        QuickReport qp = new QuickReport();
        qp.init(pp,0);
        return qp.packNcut(q,0);
    }
    public boolean isShadow(String tab,String col)throws Exception{
        return pp.isShadow(tab,col);
    }
    public synchronized void setShadow(String tab,String col,boolean b,char tctype)throws Exception{
//        String q = Schema.qShadowcols; 
//        try{pp.exec(q);}catch(Exception e){
//                    System.out.println("Session:349:");
//        }
        String qDel = "delete from shadowcols where tab='"+tab+"' and col='" +col+  "'"; 
        String qIns = "insert into shadowcols (tab,col,tctype)values('" +tab+
                "','" + col + "','" + tctype +  "')";
        String[] QQ = {qDel,qIns};
        if(b){
            pp.addShadow(tab,col,tctype);
        }else{
            pp.delShadow(tab, col);
            QQ[1]=null;
        }
        pp.exec(QQ);
    }
    
    public ITabCol getTabCol(String tabcol,char tctype)throws Exception{
        int i = tabcol.indexOf("::");
        String tab=tabcol.substring(0,i);
        String col=tabcol.substring(i+2);
        return getTabCol(tab,col,tctype);
    }
    public ITabCol getTabCol(String xtab,String xcol,char tctype)throws Exception{
        String tab = xtab.trim();
        String col = xcol.trim();
        switch(tctype){
            case 'c':return new Field(pp,tab,col);
            case 'd':return new Doc(pp,tab,col);
            case 'p':return new Pic(pp,tab,col);
            case 't':return new TypeField(pp,col);
            default:throw new Exception("unknown tabcol type!");
        }
    }
    public String lineForm(String tn,String tabname,String colname)throws Exception{
        Field f = new Field(pp,tabname,colname);
        int id = tn2id(objTree,tn);
        return f.lineForm(id);
    }

    public void editUlist(HttpServletRequest req)throws Exception{
        SortedMap<String,String> m = GuiTree.req2map(req);
        String tab=null;
        String col=null;
        String head=(String)m.get("head");
        m.remove("head");
        m.remove("x");
        m.remove("y");
        String s=head+"<ul>";
        for(String key:m.keySet()){
            if(key.equals("tab")) tab=m.get(key);
            else if(key.equals("col")) col=m.get(key);
            else s+="<li>("+key+")&nbsp;"+m.get(key)+"</li>";
        }
        s+="</ul>";
        UList u = new UList(pp,tab);
        u.edit(s,col);
    }
    public void admPwd(String s)throws Exception{
        pp.exec("update xuser set pwd='"+s.trim()+"' where id=0");
    }
    public synchronized long upload(HttpServletRequest req)throws Exception{
        XUpload u = new XUpload(pp);
        return u.save(req);
    }
    
    public void deleteU(String path)throws Exception{
        XUpload u = new XUpload(pp);
        u.deleteU(path);
    }

    public String inhRootCode()throws Exception{
        return inhTree.getRootCode();
    }
    public void editType(HttpServletRequest req)throws Exception{
        SortedMap<String,String> m = GuiTree.req2map(req);
        for(String key:m.keySet()){
            if(key.equals("id"))continue;
            if(key.equals("typecode"))continue;  
            if(key.equals("x"))continue;  
            if(key.equals("y"))continue;  
            ITabCol tc = getTabCol(key,'c');
            tc.setRus(m.get(key));
        }
    }
    public String getName(int id)throws Exception{
        return pp.getS("select name from object where id="+id);
    }
    public synchronized void editPointFla(HttpServletRequest req)throws Exception{
        SortedMap<String,String> m = GuiTree.req2map(req);
        String sid = m.get("object::id"); 
        if(sid==null){sid=m.get("id");m.put("object::id",sid);}         
        int id = Integer.parseInt(sid);//m.get("object::id")); 
        String basetab = m.get("basetab");
        Table tab = new Table(pp,basetab,false);
        String o = tab.qUpdate(m,false); 
        pp.exec(o);
//        pp.exec("update object set last_update=dateob() where id="+sid);
        pp.setLastUpdate(sid); 

    }
    public synchronized void editPointX(HttpServletRequest req)throws Exception{
        SortedMap<String,String> m = GuiTree.req2map(req);
        String dbconfig = m.get("dbconfig");
        String schema = m.get("schema");
        String root = m.get("root");
        String code = m.get("code");  
        String sid = m.get("object::id"); 
        if(sid==null){sid=m.get("id");m.put("object::id",sid);} 
        int id = Integer.parseInt(sid);//m.get("object::id")); 
        if(id==0)throw new ExAdminOnlyMode(pp.iLang);

            login2(dbconfig,schema,root,sid,code);

        PNode node = pp.getNode(FTree.ObjectTree,id);
        Table tab = new Table(pp,node.typeCode(),false);
        String o = tab.qUpdate(m); 
        pp.exec(o);
//        pp.exec("update object set last_update=dateob() where id="+sid);
        pp.setLastUpdate(sid); 
    }
    
    public synchronized void xConnect(HttpServletRequest req)throws Exception{
        SortedMap<String,String> m = GuiTree.req2map(req);
        String dbconfig = m.get("dbconfig");
        String schema = m.get("schema");
        String root = m.get("root");
        String code = m.get("code"); 
        if(code==null)code = m.get("pwd"); 
        String sid = m.get("object::id"); 
        if(sid==null)sid=m.get("id"); 

            login2(dbconfig,schema,root,sid,code);
    }    
    public String pointXEditForm(int id)throws Exception{
        String r = (""+Math.random()).substring(2);
        String s = "<input type=hidden name=object::id value="+id+">"+
                "<input type=hidden name=r value="+r+">";
        PNode node = pp.getNode(FTree.ObjectTree,id);
        Table t = new Table(pp,node.typeCode(),false,node.abc());//abc
        return s+t.pointView(id,r);
    }
    public synchronized String pointPtab(String dbconfig,String schema,String root,String sid,String code)throws Exception{
            login2(dbconfig,schema,root,sid,code);
        int id = Integer.parseInt(sid);
        String s = "";
        Result r=new Result();
        int i=-1;
        String c="",nm="";
        String q = "select o.id,o.code,o.name from object o where o.id2=0 and o.parent="+id+
                " order by code ";
        try{
            pp.select(r,1,q); 
            while(r.rs.next()){
                i = r.rs.getInt("id");
                c = r.rs.getString("code");
                nm = r.rs.getString("name");
                s+="<tr><td><a href='editform.jsp?id="+i+
                        "&dbconfig="+dbconfig+
                        "&schema="+schema+"&code="+c+
                        "&root="+root+
                        "' target=right>"+c+"/"+nm+
                        "</a></td></tr>";
            }
        }finally{r.close();}
        return s;
    }
    public synchronized String pointQtab(String dbconfig,String schema,String root,String sid,String code//,String pars
            )throws Exception{
            login2(dbconfig,schema,root,sid,code);
        int id = Integer.parseInt(sid);
        String s = "";
        Result r=new Result();
        int i=-1;
        String c="",n="";
        String q = "select o.id,o.code,o.name from object o,type t where " +
                " t.id=o.type and "+
                " o.id2=0 and o.parent="+id+
                " order by o.code ";
        try{
            pp.select(r,2,q); 
            while(r.rs.next()){
                i = r.rs.getInt("id");
                c = r.rs.getString("code");
                n = r.rs.getString("name");
                s+=qTabLine(i,c,n);
            }
        }finally{r.close();}
        
        q = "select o2.id,o2.code,o2.name from object o,object o2,type t where " +
                " t.id=o2.type  and "+
                " o.id2=o2.id and o.id2>0 and o.parent="+id+
                " order by o2.code ";
        try{
            pp.select(r,3,q); 
            while(r.rs.next()){
                i = r.rs.getInt("id");
                c = r.rs.getString("code");
                n = r.rs.getString("name");
                s+=qTabLine(i,c,n);
            }
        }finally{r.close();}
        return s;
    }
    
    String qTabLine(int i,String c,String n){
            return "<tr><td><a href='../proc/frame.jsp?id="+i+
                        "' target=right>"+c+(n!=null&&!n.equals("")?"("+n+")":"")+
                        "</a></td></tr>";
    }
    
    public String showArTree()throws Exception{
        return arTree.show();
    }
    public synchronized void deleteArNode(HttpServletRequest req)throws Exception{
        arTree.delete(req);
    }
    public void recordAR(String q)throws Exception{
        arTree.record(q);
    }
    public String pathAR(String snum)throws Exception{
        int num = Integer.parseInt(snum);
        return arTree.path(num);
    }
    public String getFieldsTab(String snum)throws Exception{
        int num = Integer.parseInt(snum);
        return arTree.getFieldsTab(num);
    }
    public void setFieldsTab(String snum,int pos,int ord,String col)throws Exception{
        int num = Integer.parseInt(snum);
        arTree.setFieldsTab(num,pos,ord,col);
    }
    public void normalAR()throws Exception{
        arTree.normal();
    }
    public void normal2AR()throws Exception{
        arTree.normal2();
    }
    public void initArTree(String tn,String rootTable)throws Exception{
        int qid = tn2id(objTree,tn);
        arTree = new ArTree(pp,rootTable,qid);
    }
    public String arTreeQtxt(){
        return arTree.qtxt;
    }
    
    public String faceAR()throws Exception{
        return arTree.obj.face(pp);
    }
    public String faceObj(int id)throws Exception{
        PNode obj = pp.getNode(FTree.ObjectTree,id);
        return obj.face(pp);
    }
    public void collapseAR(String snum)throws Exception{
        int num = Integer.parseInt(snum);
        arTree.collapse(arTree.num2n(num));
    }
    public synchronized void expandAR(String snum)throws Exception{
        int num = Integer.parseInt(snum);
        arTree.expand(arTree.num2n(num));
    }
    private List<String> list2ar(String list)throws Exception{
        List<String> ar = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(list,"+");
        while(st.hasMoreTokens()){
            String token = st.nextToken();
            ar.add(adr2name(token));
        }
        return ar;
    }
    private String adr2name(String tn)throws Exception{
        int id = tn2id(tn);
        String q = "select name from object where id="+id;
        String q2= "select o2.name from object o,object o2 where o.id2=o2.id and o.id="+id;
        String qi= "select id2 from object where id="+id;
        int i=pp.getN(qi);
        return pp.getS(i>0?q2:q);
    }
  

    

    public void initNTree(String tn)throws Exception{
        int id = tn2id(objTree,tn);
        String typecode = inhRootCode();
        int type = pp.getN("select id from type where code='"+typecode+"'");
        ntypeTree = new NTypeTree(pp,type,id,objTree);
    }
    public void editTypeSet(String tn)throws Exception{
        ntypeTree.editTypeSet(tn2n(tn));
    }
    public void editTypeCenter(String tn)throws Exception{
        int parent = ntypeTree.editTypeCenter(tn2n(tn)); 
        objTree.refreshId(parent);
    }
    public String nTypeObj()throws Exception{
        return P2Node.pathInh(pp,ntypeTree.obj.type())+"//"+ntypeTree.obj.face(pp);
    }

    public synchronized String procResult(String fun,int id,String arg)throws Exception{
            long L1 = System.currentTimeMillis();
            String s = "";
            List<IProc> list = pp.getProc(id);
            for(IProc proc:list){
                s += proc.result(fun,arg); 
            }
            long L2 = System.currentTimeMillis();
            s+=""+(L2-L1)+" msec;"                                                                                                                                                    ;
            return s;
        }
        
        public String getUser(){
            return uname;
        }
        
        public String getSchema(){
            return pp.schema;
        }
        
        private long lMillis;
        public void setMillis(){
            lMillis = System.currentTimeMillis();
        }
        
        public Long getMillis(){
            long L = System.currentTimeMillis();
            long DL = L - lMillis;
            lMillis = 0;
            return DL;
        }
        public String getKproc(int id)throws Exception{
            List<IProc> list = pp.getProc(id);
            
            return list.get(list.size()-1).getK1(); 
        }
        
        public String getKproc(int id,boolean force)throws Exception{
            List<IProc> list = pp.getProc(id);            
            for(int i=list.size()-1;i>=0;i--){
                IProc p = list.get(i);
                String x=p.getK1(force);
                if(x!=null)return x;
            }
            return "???"; 
        }        
        public synchronized String procPublish(String fun,int id,String arg)throws Exception{
            String s = "";
            List<IProc> list = pp.getProc(id);
            for(IProc proc:list){
                s += proc.publish(fun,arg); 
            }
            return s;
        }
        public String parDesc(int id,int k)throws Exception{
            String s = "";
            List<IProc> list = pp.getProc(id);
            for(IProc proc:list){
                s += proc.parDesc(id,k);
            }
            return s;
        }
        public String parDescType(int id,int k)throws Exception{
            String s = "";
            List<IProc> list = pp.getTypeProc(id,0);
            for(IProc proc:list){
                s += proc.parDesc(id,k);
            }
            return s;
        }     
        public boolean hasForce(int id)throws Exception{
            boolean s = false;
            List<IProc> list = pp.getProc(id);
            for(IProc proc:list){
                s|=proc.hasForce();
            }
            return s;
        }
        public String procDesc(int id)throws Exception{
                return procDesc(id,false);
        }
        
        public String descQfolder(int id)throws Exception{
                return Qfolder.qListDesc(pp,id);
        }
        
        public String procDesc(int id,boolean force)throws Exception{
          
            String s = "";
            List<IProc> list = pp.getProc(id);
            for(IProc proc:list){
                s += proc.descriptionT(id,force);
            }
            return s;
        }
        
        public String procTypeDesc(int id,boolean force)throws Exception{
            String s = "";
            List<IProc> list = pp.getTypeProc(id,0);
            for(IProc proc:list){
                s += proc.descriptionT(id,force);
            }
            return s;
        }

        public String getTime(){
            GregorianCalendar calendar = new GregorianCalendar();
            String s =  "//Time:"+calendar.get(Calendar.HOUR_OF_DAY)+"hh "+
                                calendar.get(Calendar.MINUTE)+"mm "+
                                calendar.get(Calendar.SECOND)+"ss "+
                                calendar.get(Calendar.MILLISECOND)+"msc";
            return s;
        }

        public synchronized void file2db(String path,boolean utf)throws Exception{
            File scriptFile = new File(path);
            RwFile rw = new RwFile(); 
            String qq = rw.read(scriptFile,utf); 
            Sql sql = new Sql(pp,scriptFile);  
            System.out.println("\n::: "+qq);
            sql.area2db(qq,false);
        }
       
        public void area2db(String qq)throws Exception{
                area2db(qq,false);
        }

        public void area2db(String qq,boolean dbg)throws Exception{/////
            Sql sql = new Sql(pp); 
            sql.area2db(qq,dbg); 
        }
        
        public void shutdown()throws Exception{
            pp.exec("shutdown");
        }
        public String db2area(int id)throws Exception{
            SqlExport sql = new SqlExport(pp);
            return sql.db2area(id);
        }
        public String db20area(int id)throws Exception{
            SqlExport sql = new SqlExport(pp);
            return sql.db20area(id);
        }
        
    public String openTable(String typecode,int id)throws Exception{
        String S = "";
        List<P2Node> L = P2Node.getP2after(pp,typecode,id);
        for(P2Node pn:L){
            S+="<tr><td><input type=radio name=r onClick='set(" +pn.id()+
                    ",\"" +pn.codeName()+
                    "\")'></td><td>"+pn.codeName()+"</td></tr>";
        }
        return S;
    }
    
    public void setMax(String s)throws Exception{
        int i=s.indexOf(";");
        if(i<0)throw new ExNoPars();
        int j=s.lastIndexOf(";");
        if(j<0 || i==j)throw new ExNoPars();
            pp.page = Integer.parseInt(s.substring(0,i));
            pp.page2= Integer.parseInt(s.substring(i+1,j));
            pp.page3= Integer.parseInt(s.substring(j+1));
    }
    
    public String getMax(){
        return ""+pp.page+";"+pp.page2+";"+pp.page3;
    }
    
    
    public String showDicons()throws Exception{
        Icons ii = new Icons(pp,pp.iconPath(),null);
        return ii.showD();
    }
    
    public String showDOicons()throws Exception{
        Icons ii = new Icons(pp,pp.iconPath(),null);
        return ii.showDobj();
    }
    
    public synchronized void hh(String ref,String tab,String col,String on)throws Exception{
        String q = "delete from refhh where tab='" +tab+ "' and col='" +col+ "'";
        String q2 = "insert into refhh (tab,col,ref)values('" +tab+ "','" +col+ "','" +ref+
                "')";
        boolean b = on==null||on.equals("");
            pp.exec(b?q:q2);
            Table table = pp.getTable(tab);
            table.setRefhh(col,!b); 
    }   
    public synchronized void hhBlind(String tab,String col,String on)throws Exception{
        boolean b = on!=null&&!on.equals("");        
        String q = "update refhh set blind="+(b?"true":"false")+
        " where tab='" +tab+ "' and col='" +col+ "'";
//        if(true)throw new Exception("=="+q+"==");
            pp.exec(q);
            Table table = pp.getTable(tab);
            table.setRefhhBlind(col,b); 
    }  
    
    public String mySql(String typecode)throws Exception{
            Table table = pp.getTable(typecode);
            String s = table.mySql();
            return s;
    }

//     public boolean hasFin(int id)throws Exception{
//        String q = "select fin from object o,type t where o.type=t.id and o.id="+id;
//        return pp.getB(q);
//    }
    
    public boolean getFin(int type)throws Exception{
        String q = "select fin from type where id="+type;
        return pp.getB(q);
    }
    
    public void setFin(int type,boolean f)throws Exception{
        String q = "update type set fin="+(f?"true":"false")+" where id="+type;
        pp.exec(q);
    }   
    
    public void setFin(String typecode)throws Exception{
        String q = "update type set fin=true where code='"+typecode+"'";
        pp.exec(q);
    }   
//    public boolean hasFlash(int id)throws Exception{
//        String q = "select flash from object o,type t where o.type=t.id and o.id="+id;
//        return pp.getB(q);
//    }
//    
//    public boolean getFlash(int type)throws Exception{
//        String q = "select flash from type where id="+type;
//        return pp.getB(q);
//    }
//    
//    public void setFlash(int type,boolean f)throws Exception{
//        String q = "update type set flash="+(f?"true":"false")+" where id="+type;
//        pp.exec(q);
//    }
    public String id2name(int oid)throws Exception{
        String q = "select name from object where id="+oid;
        return pp.getS(q);
    }
    public String type2code(int id)throws Exception{
        String q = "select code from type where id="+id;
        return pp.getS(q);
    }    
    
    public String id2typecode(int oid)throws Exception{
        String q = "select t.code from object o, type t where t.id=o.type and o.id="+oid;
        return pp.getS(q);
    } 
    
    public int idObjUp(int id)throws Exception{
        String q = "select parent from object where id="+id;     
        return pp.getN(q);
    }
    
    public int idObjUp(int id,int n)throws Exception{ 
        int j=id;
        for(int i=0;i<n;i++){
            j=idObjUp(j);
        }
        return j;
    }
    
    public String nameUp(int id,int n)throws Exception{
        int i=idObjUp(id,n);
        if(i==0)return "";
        String q = "select name from object where id="+i;
        return pp.getS(q);
    }
    
    private String adr2name(int id)throws Exception{
//        int id = Session.tn2id(tn); 
        String q = "select name from object where id="+id;
        String q2= "select o2.name from object o,object o2 where o.id2=o2.id and o.id="+id;
        String qi= "select id2 from object where id="+id;
        int i=pp.getN(qi);
        return pp.getS(i>0?q2:q);
    }    
    
    public synchronized int createObj(String stateTn,String typecode,String addr,String fun)throws Exception{
        long L = System.currentTimeMillis();
        System.out.println("create:"+L+":"+fun);
        int iFun = Integer.parseInt(fun);
        int parentId = tn2id(stateTn);
        int iPattern=1;
        IGuiNode nn;
        CreateObj cre = new CreateObj(pp,objTree,parentId,typecode); 
        switch(iFun){
            case 81:return cre.fromFile(addr);//(8)Создать объекты согласно скрипту в файле
            case 91:
                nn = getGuiNode(addr);                
                if(!nn.typeCode().equals("query"))throw new ExMustBeQueryAddr(pp.iLang);                
                iPattern = nn.idOrigin();
                return cre.create(iPattern,iFun);
            case 11:
                try{iPattern = Integer.parseInt(addr);
                }finally{return cre.create(iPattern,iFun);}
            case 21:
                nn = getGuiNode(stateTn); 
                List<String> LS = list2ar(addr);
                int K = 0;
                for(String nam:LS){
                    K++;
                    SortedMap<String,String> map = new TreeMap<String,String>();
                    map.put("object::name",""+nam);
                    map.put("parent",""+parentId);
                    map.put("typecode",typecode); 
                    nn.doInsert(map); 
                }
                return K;//(21)Создать по типу и имени-образцу:несколько:a(+a)
            case 71: try{
                            typecode = pp.getS("select code from type where id="+addr);
                            SortedMap<String,String> map = new TreeMap<String,String>();
                            map.put("parent",""+parentId);
                            map.put("typecode",typecode); 
                            nn = getGuiNode(stateTn); 
                            nn.doInsert(map);  
                        }catch(Exception e){
                            throw new ExTypeId(pp.iLang);
                        }                    
                return 1;   
            case 61:case 62:case 13:
                return cre.create(0,iFun);
            default:
           
                iPattern = tn2id(addr);
//                System.out.println("iPattern:"+iPattern);/////////////////////     
//                System.out.println("addr:"+addr);/////////////////////                     
                return cre.create(iPattern,iFun);
                
        }

    }

    public String optionListA(){
        CreateObj c = new CreateObj(pp,null,0,"");
        return c.optionListA();
    }
    
    public String optionListB(){
        CreateObj c = new CreateObj(pp,null,0,"");
        return c.optionListB();
    }    
    
    public String myHs(int id)throws Exception{
        return PhantomParent.myHs(pp,id);
    }
    
    public void setLastVisit()throws Exception{
        String q = "select last_update from object where id="+pp.meid;
        lastVisit = pp.getS(q);
    }
    public String lastVisit()throws Exception{
        return lastVisit;
    }
    String lastVisit;
    
    public String getVisit()throws Exception{
        String q = "select last_update from object where id="+pp.meid;
        return pp.getS(q);
    }
    
    public void lock(int id,int var)throws Exception{
        Lock L = new Lock(pp);
        L.set(id,var);
    }

     public boolean hasLock(int id)throws Exception{
        Lock L = new Lock(pp);
        return L.get(id);
    }
     

    public void makeShortcutList(int io,String parentTn)throws Exception{
    //////////////////////////////9999999999999///////////////////////////////////////////
        if(Character.isDigit(parentTn.charAt(0))){
            int i = Integer.parseInt(parentTn);    
            objTree.makeShortcutList(io,i);      
            return;
        }        
        
        IGuiNode gn = getGuiNode(parentTn);
        objTree.makeShortcutList(io,gn.idOrigin()); 
    }
    
//    public void makeShortcut(IGuiNode gn,String parentTn,boolean blind)throws Exception{
//            int i = Integer.parseInt(parentTn);      
//            objTree.makeShortcut(gn,i,blind);      
//            return; 
//    }
//    public void makeShortcutTN(String tn,String parentTn,boolean blind)throws Exception{
//        if(Character.isDigit(parentTn.charAt(0))){
//            int i = Integer.parseInt(parentTn);      
//            int n = tn2n(tn);
//            objTree.makeShortcutNum(n,i,blind);      
//            return;
//        }        
//        
//        IGuiNode gn = getGuiNode(parentTn);
//        IGuiTree t2 = tn2t(parentTn);  
//        if(t2.isObj()&&(gn.isShadow()||gn.isPseudo()))
//            throw new ExNoHook(pp.iLang);
//        IGuiTree t = tn2t(tn);
//        t.makeShortcutNum(tn2n(tn),tn2id(t,parentTn),blind);
//    }
//     public void move(String tn,String parentTn)throws Exception{
//         if(Character.isDigit(tn.charAt(0))){
//            int i = Integer.parseInt(parentTn);     
//            int n = Integer.parseInt(tn);
//            objTree.moveId(n,i);            
//            return;
//        }
//        
//        IGuiNode gn = getGuiNode(parentTn);
//        IGuiTree t2 = tn2t(parentTn);
//        if(t2.isObj()&&(gn.isShadow()||gn.isPseudo()))
//            throw new ExNoHook(pp.iLang);
//        IGuiTree t = tn2t(tn);        
//        int n = tn2n(tn);
//        t.move(n,tn2id(t,parentTn));
//    }    
    
    public synchronized void move(String tn,String parentTn)throws Exception{
        if(parentTn.length()<1)throw new ExEmptyParam(pp.iLang);
        if(Character.isDigit(parentTn.charAt(0))){
            int i = Integer.parseInt(parentTn);
            IGuiTree t = tn2t(tn);        
            int n = tn2n(tn);
            t.move(n,i);            
            return;
        }
        
        IGuiNode gn = getGuiNode(parentTn);
        IGuiTree t2 = tn2t(parentTn);
        if(t2.isObj()&&(gn.isShadow()||gn.isPseudo()))
            throw new ExNoHook(pp.iLang);
        IGuiTree t = tn2t(tn);        
        int n = tn2n(tn);
        t.move(n,tn2id(t,parentTn));
    }

    
    public synchronized void makeShortcutTN(String tn,String parentTn,boolean blind)throws Exception{
        if(Character.isDigit(parentTn.charAt(0))){
            int i = Integer.parseInt(parentTn);
            IGuiTree t = tn2t(tn);        
            int n = tn2n(tn);
            t.makeShortcutNum(n,i,blind);      
            return;
        }        
        
        IGuiNode gn = getGuiNode(parentTn);
        IGuiTree t2 = tn2t(parentTn);  
        if(t2.isObj()&&(gn.isShadow()||gn.isPseudo()))
            throw new ExNoHook(pp.iLang);
        IGuiTree t = tn2t(tn);
        t.makeShortcutNum(tn2n(tn),tn2id(t,parentTn),blind);
    } 
    
    public synchronized void moveUp(String tabcol)throws Exception{
        int i = tabcol.indexOf("::");
        String tab=tabcol.substring(0,i);
        String col=tabcol.substring(i+2);
        String q = "ALTER TABLE " +tab+
                " ALTER " +col+
                " MOVEUP";
        pp.exec(q);
        pp.initFat(tab);
    }
    
    public synchronized void moveDown(String tabcol)throws Exception{
        int i = tabcol.indexOf("::");
        String tab=tabcol.substring(0,i);
        String col=tabcol.substring(i+2);        
        String q = "ALTER TABLE " +tab+
                " ALTER " +col+
                " MOVEDOWN";
        pp.exec(q);
        pp.initFat(tab);
    }    
}