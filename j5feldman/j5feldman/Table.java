/*
 * Table.java
 *
 * Created on 3 ƒекабрь 2004 г., 15:24
 */

package j5feldman;
import j5feldman.proc.basement.DbFormat;
import j5feldman.proc.basement.PhantomParent;
import j5feldman.proc.basement.Result;
import j5feldman.ex.*;
import java.util.*;
import java.sql.*;
import java.io.*;
//import javax.servlet.http.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Table {
    XPump pp;
    public String name;
    public List<ITabCol> fields=new ArrayList<ITabCol>();
    SortedMap<String,String> map;
    SortedMap<String,String> map2;    
    /** Creates a new instance of Table */
    public Table(XPump pp,String name,boolean all)throws Exception {
        this(pp,name,all,false);
    }
    
    public Table(XPump pp,String name,boolean all,boolean abc)throws Exception {
        this.name = name;
        this.pp = pp;
        //fields.addAll(
        getFields(pp,name,abc);//);
        if(all){
            fields.addAll(getPics());
            fields.addAll(getDocs());
        }
    }
    public Table(XPump pp,String name)throws Exception {
        this.name = name;
        this.pp = pp;
//        fields.addAll(getPics());
        fields.addAll(getDocs());    
    }
void editBase(SortedMap<String,String> map)throws Exception{
        map.remove("x");
        map.remove("y");        
        String o = qUpdate(map);
        pp.exec(o);
}

 String mySql()throws Exception{
     String s = "//select id from "+name+" where \n";
        int N=0;
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){             
                String ss;
                if(f.dtype()==DbFormat.INTEGER.getInt() || f.dtype()==DbFormat.REAL.getInt()){
                    ss=f.name()+"=? ";

                }else if(f.isBoolean()){  
                    ss= f.name()+" ";
                }else ss= f.name()+"='?'";
                if(N++>0)s+="\n AND ";
                s+=" t." + ss + "//"+f.getRus();
            }
        }  
        return s;
    }

 String pointView(int oid,String r)throws Exception{
        map = line2map(oid);
        PNode node = pp.getNode(FTree.ObjectTree,oid);
        String s = "";
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){
                f.setValue(map.get(f.name()));                  
                int iBase = 0;
                if(f.isKey()){
                    iBase = pp.getN
                    ("select "+f.name()+" from "+node.typeCode()+" where id=0");   
                    if(iBase>0){
                        int m=f.nMix();
              
                        if(m>=0){                    
                                int n = m%r.length();
                                s += f.linePoint(r.substring(n,n+1),m); 
                        }else{  
                            s += "<tr><td>"+f.getRus()+"</td>"+f.editCell()+"</tr>";
                        }
                    }
                }else{  
                    s += "<tr><td>"+f.getRus()+"</td>"+f.editCell()+"</tr>";
                }

            }
        }  
        return s;
    }
////////////////////////////////////////////////////////////////////////start
    String boxView(GuiTree.GuiNode gn)throws Exception{
        return boxView(gn,false);
    } 
    String boxView(GuiTree.GuiNode gn,boolean noe)throws Exception{
        String s="";
        if(gn.isType()){
            for(ITabCol f:fields){
                if(!f.isHidden()){
                   s+="<tr" +
                           (f.isShadow()?" bgcolor=yellow ":"") +
                           "><td>"+f.getRus()+"</td><td>"+f.ftype(gn,noe)+"</td></tr>";  
                }
            } 
        }else{
            map=line2map(gn.idOrigin());
            try{
                if(gn.isShadow())map2=line2map(gn.id());    
            }catch(Exception e){}
            for(ITabCol f:fields){
                if(!f.isHidden()){
                    boolean sh = f.isShadow();
                    String val = map.get(f.name());
                    if(gn.isShadow()&&sh&&map2!=null)   val = map2.get(f.name());
                    
                    s+="<tr valign=bottom " +
                           (sh?" bgcolor=yellow ":"") +
                            "><td>"+f.getRus()+"</td><td>"+
                            f.fvalue(gn,cleanMe(val),sh,noe)+ 
                            "</td></tr>"; 
                }
            }
        }
        return s;
    }
    String reportHeaderView(boolean inBrief)throws Exception{
        String s = "";
            for(ITabCol f:fields){
                if(!f.isHidden()){
                    boolean sh = f.isShadow();
                    s+="<td valign=bottom " +
                           (sh?" bgcolor=yellow ":"") +
                            ">"+
                            (inBrief?f.name():f.getRus())+
                            "</td>"; 
                }
            }    
        if(s.length()>500){
                System.out.println("WR:"+s);
                throw new ExWideReport(pp.iLang);
        }    
        return s;
    }    

    String reportLineView(int oid)throws Exception{
        String s = "";
            map=line2map(oid);
            for(ITabCol f:fields){
                if(!f.isHidden()){
                    boolean sh = f.isShadow();
                    String val = map.get(f.name());
                    boolean isCode = f.name().equals("code");
                    s+="<td valign=bottom " + (sh?" bgcolor=yellow ":"") +">"+
                            (isCode?"<a href='../root.jsp?id=A"+oid+"' target=left title=set-root>":"")+
                            f.fvalue(oid,cleanMe(val))+
                            (isCode?"</a>":"")+                            
                            "</td>"; 
                }
            }        
        return s;
    }
    String reportLineShadow(int oid)throws Exception{
        String s = "";
            map=line2map(oid);
            for(ITabCol f:fields){
                if(f.isShadow()){
                    String val = map.get(f.name());
                    s+="<td valign=bottom>"+f.fvalue(oid,cleanMe(val))+                     
                            "</td>"; 
                }
            }          
        return s;
    }
    
    String reportHeaderShadow(boolean inBrief)throws Exception{
        String s = "";
            for(ITabCol f:fields){
                if(f.isShadow()){
                    s+="<td valign=bottom>"+
                            (inBrief?f.name():f.getRus())+
                            "</td>"; 
                }
            }    
        if(s.length()>500){
                System.out.println("WR:"+s);
                throw new ExWideReport(pp.iLang);
        }    
        return s;
    }
    String editForm(GuiTree.GuiNode gn)throws Exception{
        String s="";
        if(gn.isObj()){
            map=line2map(gn.idOrigin());
            for(ITabCol f:fields){
                if(!f.isHidden()&&!f.isAuto()&&!f.isShadow()  && !f.readOnly(gn)){
                    f.setValue(map.get(f.name()));
                    s+="<tr><td>"+f.getRus()+"</td>"+f.editCell()+"</tr>";
                }
            }
        }else{
            if(!name.equals("object"))
            for(ITabCol f:fields){
                if(!f.isHidden()){
                    s+="<tr><td>"+f.typeEditForm()+"</td></tr>";
                }
            }
        }
        return s;
    }
    
     String editFormShadow(GuiTree.GuiNode gn)throws Exception{
        String s="";
        if(gn.isObj()){
            map=line2map(gn.idOrigin());
            for(ITabCol f:fields){
                if(!f.isHidden()&&!f.isAuto()&&
                        f.isShadow()////////////////////////////////////////////
                        && !f.readOnly(gn)){
                    f.setValue(map.get(f.name()));
                    s+="<tr><td>"+f.getRus()+"</td>"+f.editCell()+"</tr>";
                }
            }
        }
        return s;
    }   
    
////////////////////////////////////////////////////////////finish    
    String baseForm()throws Exception{
        String s="";
        map=line2map(0);
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){
                f.setValue(map.get(f.name()));
                s+="<tr><td>"+f.baseForm()+"</td></tr>";
            }
        }
        return s;
    }    

    String addForm()throws Exception{
        String s="";
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){
                s+="<tr " +(f.isShadow()?"bgcolor=yellow":"")+
                        "><td>"+f.getRus()+"</td>"+f.addCell()+"</tr>";
           }
        }
        return s;
    }    

    static String cut(String s,int L)throws Exception{
        String s2 = s.substring(0,L)+","+s.substring(L);
        return s2;
    }
    static String cutN(String s,boolean isReal)throws Exception{
        if(isReal) return cutN(s);
        return s;
    }
    static String cutN(String s)throws Exception{
        if(s==null)return "";
        String x=s;
        int k = x.indexOf(".");
        int L = x.length();
        if(k<0){
            k=L;
            x+=".00";
        }
        for(int i=k;i>0;i-=3){
            if(k>i)x = cut(x,i);
        }
        System.out.println("ddd:"+x);
        
        return x;
    }
    
//    public static void main(String[] arg)throws Exception{
//        String s = "3456789.89";
//        String x = cutN(s);
//        System.out.println(x);
//    }
    
    String cleanMe(String val)throws Exception{
                if(val==null)return "";
                if(val.equals("null"))return "";
                if(val.equals("true"))return"+";
                if(val.equals("false"))return "-";
                return val;
    }
    
    public SortedMap<String,String> line2map(int id)throws Exception{
        String q = "select * from "+name+" where id="+id;
        Result r=new Result();
        SortedMap<String,String> map=new TreeMap<String,String>();
        try{    
            pp.select(r,23,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            r.rs.next();
            for(int i=0;i<N;i++){
                String x = r.rs.getString(i+1); 
                String colname = md.getColumnName(i+1);
                boolean isReal = md.getColumnType(i+1)==Types.REAL;
//                if(isReal)System.out.println("ccc:"+x);
                map.put(colname,cutN(x,isReal)); ////////////////////////
            }                    
        }catch(Exception e){
            throw new Exception("/q="+q+"//"+e.getMessage());
        }finally{r.close();}
        return map;
    }

    public SortedMap<String,String> line2map2(int id)throws Exception{
        SortedMap<String,String> map=new TreeMap<String,String>();
        line2map2(id,map);
        return map;
    }
    public void line2map2(int id,SortedMap<String,String> map)throws Exception{
        String q = "select * from "+name+" where id="+id;
        Result r=new Result();

        try{    
            pp.select(r,24,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            r.rs.next();
            for(int i=0;i<N;i++){
                String x = r.rs.getString(i+1); 
                String colname = md.getColumnName(i+1); 
                boolean isReal = md.getColumnType(i+1)==Types.REAL;                
                map.put(name+"::"+colname,cutN(x,isReal));//////////////////////////////////// 
            }                    
        }catch(Exception e){throw new Exception("/q="+q+"//"+e.getMessage());
        }finally{r.close();}
        
    }
    
    public String qUpdate(SortedMap<String,String> map)throws Exception{
        return qUpdate(map,true);
    }      
    
    String tabcolname(String col,boolean btab){
        return btab?
            name+"::"+col
                :col;
    }
    
    public String qUpdate(SortedMap<String,String> map,boolean btabname)throws Exception{      
        String r = map.get("r");
        String S="";
        String q="update "+name+" set ";
        String ID = map.get("object::id");
        int n = 0;
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){
                f.setValue(map.get(tabcolname(f.name(),btabname)));
                S=f.value();
//                if(S==null)continue;
                
                boolean bm;
                int m=0;
                try{
                    m = Integer.parseInt(f.name().substring(1));
                    bm=true;
                }catch(Exception e){
                    bm=false;
                }
                
                try{                
                    int iS = Integer.parseInt(S);
                    if(f.isKey()&&iS==-1){
                        S="0";
                        iS=0;
                    }
                                        
                    if(bm && iS>0 && r!=null&&f.name().charAt(0)=='q'&&f.isKey()){              
                        int nn = m%r.length();
                        f.setCase();
                        int ix=-2;
                        int nnn;
                        int N = f.kvars().size();
                        ix = Field.shift(
                            idx(iS,f.kvars()),
                            (nnn=Integer.parseInt(r.substring(nn,nn+1))),N); 
                        int iy = f.kvars().get(ix);
                        S = ""+iy;
                    }
                }catch(Exception e){}

                if(!S.equals("''")&&!S.equals("")){
                    if(n>0)q+=",";
                    if(S.equals("'-'"))S="''";
                    q+=f.name()+"="+S;
                    n++;
                }
            }
        }
        if(name.equals("object")){
            if(n>0)q+=",";
            q+="last_update=dateob() ";
            q+=" where id="+ID;            
            return q;
        }else
        if(n>0){
            q+=" where id="+ID;            
            return q;
        }
        else return null;
    }  
    
    static int idx(int i,List<Integer> ii){
        for(int j=0;j<ii.size();j++){
            if(ii.get(j)==i)return j;
        }
        return -1;
    }     

 public String qInsert(SortedMap<String,String> map,int ID,int parentId,int type)throws Exception{
        String q="insert into "+name+" (";
        String qf="id";
        String qv=""+ID;     
        String S="";
        if(name.equals("object")){
            qf+=",type,parent";
            qv+=","+type+","+parentId;   
        }
//        }else       
//        if(name.equals("process")){
//          int seed = pp.getN
//          ("select seed from process where id="+parentId);
//                        if(seed<=0)seed = ID;
//          qf+=",seed,father,author";
//          qv+=","+seed+","+parentId+","+pp.meid;  
//        }
            
        for(ITabCol f:fields){
            if(!f.isHidden()){
                String val = map.get(name+"::"+f.name());
                
                if(f.tabcol().equals("object::code")&&(val==null||val.equals(""))){
                        S="'"+ID+"'";
                        qf+=","+f.name();
                        qv+=","+S;
                        continue;
                    } 

                
                if(val!=null){
                    f.setValue(val);
                    S=f.value();  
                    if(f.tabcol().equals("xuser::pwd")
                            && S.equals("'********'")){
                        S="'"+ID+"'";
                    }
                    if(!S.equals("")&&!S.equals("''")){
                        qf+=","+f.name();
                        qv+=","+S;
                    }
                }
            }
        }

        q+=qf+") values ("+qv+" ) ";
        return q;
    }
    public String qInit(int ID)throws Exception{
        String q="update " +name+ " set "; 
        if(name.equals("object")){
            q+=" last_update=creation_date where id="+ID;            
            return q;
        }
        boolean start=true;
        for(ITabCol f:fields){
            if(f.isHidden())continue;
            if(start){start=false;}else{q+=",";}
            if(f.isKey()){
                q+=f.name()+"=0";
            }else{
                q+=f.name()+DbFormat.d2f(f.dtype()).setInit();  
            }
        }
        q+=" where id="+ID;
        return q;
    }
    String baseView()throws Exception{
        SortedMap<String,String> map = line2map(0);
        String s="";
        for(ITabCol f:fields){
            if(!f.isHidden()&&!f.isAuto()){
                String val = map.get(f.name());
                s+="<tr valign=bottom><td>"+f.getRus()+"</td><td>"+f.baseValue(val)+"</td></tr>";
            }
        }
        return s;
    }


    void setFieldRef(String col,String reftab,String fk_name,boolean bhRef,boolean blind){
        for(ITabCol tc: fields){
            if(tc.name().equals(col)){
                Field f = (Field)tc;
                f.reftab = reftab;
                f.fk_name = fk_name;
                f.bhRef = bhRef;
                f.bhRefBlind = blind;
                return;
            }
        }
    }

public void getFields(XPump pp,String tabname,boolean abc)throws Exception{//id,code,name
        String q = "select \"column\",type_desc,sql_type from sUSRTableColumns where "+
                " \"schema\"='"+pp.schema+"' and \"table\"='"+tabname+"' " +
                " and seq_no >0 order by " +
                (abc?" \"column\", " : "")+
                " seq_no ";       
        String q2 = "select fcolumn,ref_table,name from " +
                " sUSRForeignColumns,sUSRFKeyInfo where " +
                " id=fk_id and \"schema\"='" +pp.schema+
                "' and \"table\"='" +tabname+
                "' and fcolumn<>'id'";
        Result r=new Result();
        Result r2=new Result();
        String col_name;
        String dt_name;
        int dtype;
        String reftab;
        String fk_name;
        boolean bhRef=false;
        boolean bhRefBlind=true;
        fields.add(new Field(pp,tabname,"id","INTEGER",4)); 
        try{
            pp.selectInfo(r,3,q);
            int N=0;
            while(r.rs.next()){
                col_name = r.rs.getString("column");
                dt_name = r.rs.getString("type_desc");
                dtype = r.rs.getInt("sql_type");
                fields.add(new Field(pp,tabname,col_name,dt_name,dtype));  
                if(dtype==4)N++;
            }  
            r.close();/////////////////////
            if(N>0){
                pp.selectInfo(r2,4,q2);
                while(r2.rs.next()){
                    col_name = r2.rs.getString("fcolumn");
                    reftab = r2.rs.getString("ref_table");
                    fk_name = r2.rs.getString("name");
                    bhRef = PhantomParent.hasPhantomChildren(pp,tabname,col_name);
                    if(bhRef){
                        String q5="select blind from refhh where tab='" +tabname+ "' and col='" +col_name+ "'";
                        bhRefBlind = pp.getB(q5);
                    }
                    setFieldRef(col_name,reftab,fk_name,bhRef,bhRefBlind);  
                }  
            }            
        }finally{
            r.close();
            r2.close();
        }
    }    
    
    List<Pic> getPics(){
        ArrayList<Pic> ar = new ArrayList<Pic>();
        String path = pp.picPath()+name;
        File dir = new File(path);

        if(!dir.exists()) return ar;            
        if(!dir.isDirectory()) return ar;       
        File[] fa = dir.listFiles();
        int N = fa.length;                  
        for(int i=0;i<N;i++){
            File f = fa[i];
            String fname = f.getName();
            Pic up = new Pic(pp,name,fname);
            ar.add(up);
        }
        return ar;
    } 
    List<Doc> getDocs(){
        ArrayList<Doc> ar = new ArrayList<Doc>();
        String path = pp.docPath()+name;//type+table
        File dir = new File(path); 
        if(!dir.exists()) return ar;            
        if(!dir.isDirectory()) return ar; //type+table
        File[] fa = dir.listFiles();//type+table+field                
        for(int i=0;i<fa.length;i++){
            File f = fa[i];
            String fname = f.getName();//loop:+extension
            Doc up = new Doc(pp,name,fname);       
            ar.add(up);
        }
        return ar;
    }   
    
    public void setRefhh(String col,boolean b){
        for(ITabCol tc:fields){
            if(tc.name().equals(tc))tc.setRefhh(b);
        }
    }
    public void setRefhhBlind(String col,boolean b){
        for(ITabCol tc:fields){
            if(tc.name().equals(tc))tc.setRefhhBlind(b);
        }
    }    
}