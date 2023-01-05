/*
 * Field.java
 *
 * Created on 3 ƒекабрь 2004 г., 15:34
 */

package j5feldman;
import j5feldman.proc.basement.DbFormat;
import j5feldman.proc.basement.PhantomParent;
import j5feldman.proc.basement.Result;
import j5feldman.schema.Schema;
import java.util.*;
import java.sql.*;
import j5feldman.ex.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Field extends TabCol{
    private String sValue="";
    List<String> qvars;
    List<String> qcodes;
    String head;
    List<Integer> kvars;
    public List<Integer> kvars()throws Exception{return kvars;}
    public void setValue(String val)throws Exception{

        sValue = val!=null?val:"";
    }
    String dt_name;
    int dtype;
    public String reftab;
    public boolean bhRef=false;//virtual shortcuts
    public boolean bhRefBlind=true;//virtual shortcuts is blind
    String fk_name;
    /** Creates a new instance of Field */
    public Field(XPump pp,Table tab,String name,String dt_name,int dtype,String reftab,String fk_name) {
        super(pp,tab,name);
        this.dt_name=dt_name;
        this.dtype=dtype;
        this.reftab=reftab;
        this.fk_name=fk_name;
    }
    public Field(XPump pp,String tabname,String name,String dt_name,int dtype,String reftab,String fk_name) {
        super(pp,tabname,name);
        this.dt_name=dt_name;
        this.dtype=dtype;
        this.reftab=reftab;
        this.fk_name=fk_name;
    }
    
    public Field(XPump pp,String tabname,String name,String dt_name,int dtype) {
        super(pp,tabname,name);
        this.dt_name=dt_name;
        this.dtype=dtype;
    }
    
    public Field(XPump pp,String tabname,String name) {
        super(pp,tabname,name);
        System.out.println("Table:"+tabname);
    }
    public Field(XPump pp,String tabname,String name,int dtype) {
        super(pp,tabname,name);
        this.dtype=dtype;
    }
    public int dtype(){
        return dtype;
    }

    public String fvalue(GuiTree.GuiNode gn, String Val,boolean sh) throws Exception{
        return fvalue(gn,Val,sh,false);
    }
    public String fvalue(GuiTree.GuiNode gn, String Val,boolean sh,boolean noe) throws Exception{
        String val = Val;//sh?getShadowValue(gn):Val;
//        if(val.equals("-"))val=" ";
        if(isPwd())return "********"+tdE(gn,noe);
        if(reftab==null||reftab.equals("type"))///////////////////////////
            return val+tdE(gn,noe);
        else{
            try{
                int key = Integer.parseInt(val);
                P2Node pn = P2Node.getP2Node(pp,FTree.ObjectTree,key);
                return pn.codeNameUnder()+tdE(gn,noe);///////////////////////////////////////////////////////////////
            }catch(Exception e){
                throw new Exception("tabcol:"+tabcol+"bad int val:"+val);
            }
        }
    }
    
    public String fvalue(int oid,String val) throws Exception{
        if(isPwd())return "********";
        if(reftab==null)///////////////////////////
            return val;
        else{
            try{
                int key = Integer.parseInt(val);
                P2Node pn = P2Node.getP2Node(pp,FTree.ObjectTree,key);
                return pn.codeNameUnder();/////////////////////////////////////////////////////////////////////////
            }catch(Exception e){
                throw new Exception("tabcol:"+tabcol+"bad int val:"+val);
            }
        }
    }    
    public String ftype(GuiTree.GuiNode gn,boolean noe)throws Exception{
        String s=(reftab==null
                ?dtype(dt_name)
                :refCodeName(reftab)
                )+tdE(gn,noe);
        return s;
    }
    
    String dtype(String s){
        if(s.equals("LONGVARCHAR"))return "TEXT";
        if(s.equals("BOOLEAN"))return "YES-NO";
        return s;
    }
    
    String refCodeName(String s)throws Exception{
        String ss = "";
        String q = "select name from type where code='" +s+"'";
        String q2 = "select id from type where code='" +s+"'";
        String name = pp.getS(q);
        int ID = pp.getN(q2);
        if(name==null||name.equals(""))return s;
        ss = s+"("+name+")";
        return "<a href='root.jsp?id=" +ID+"' target=left title='set root here'>"+ss+"</a>";
    }
    
    public String c(){
        return "c";
    }

    String tdB()throws Exception{
        String s = "</td><td>";
        s+="<a href='line.jsp?id=A0&tab="+tabname+"&col="+name+
                "&dtype="+dtype+"' title='edit base value'>B</a>";
        return s;
    }
    public String baseValue(String val)throws Exception{
        if(reftab==null||reftab.equals("type"))///////////////////////////
            return val+tdB();
        else{
            int key = Integer.parseInt(val);
            P2Node pn = P2Node.getP2Node(pp,FTree.ObjectTree,key);
            return pn.codeName()+tdB();
        }
    }
    
//    public String editCell() throws Exception{
//        return editCell(tabcol);
//    }
    
   
    public String editCell()throws Exception{
        String S;
        if(isKey()){
            int i=0;
            try{
                i=Integer.parseInt(sValue);
            }catch(Exception e){}
            S=refInput(i);
        } else{
            if(isPwd()){
                S="<td valign=bottom><input type=password name='"+tabcol+"' value='"+sValue+"' "+
                        "></td>";
            }else if(isBoolean()){
                S="<td valign=bottom><input type=checkbox name='"+tabcol+"' "+
                        (sValue!=null&&sValue.equals("true")||sValue.equals("+")?"checked":"")+"></td>";
            }else{

                if(sValue==null)sValue="";else
                if(sValue.equals("null"))sValue="";               
                if(sValue.length()<40){
                    S="<td valign=bottom><input size=40 type=text name='"+tabcol+"' value='"+apo(sValue)+"' "+///////////////////////////
                            blur()+
                            "></td>";}else{
                    int L = sValue.length();
                    int N = L/40 + 1;
                    S="<td valign=bottom><textarea name='"+tabcol+"' rows="+N+" cols=60>"+
                            apo(sValue)+"</textarea></td>";
                            }
            }
        }
        return S+"<input type=hidden name=tabcol value=" +tabcol+">";
    }
    
    String refInput(int i)throws Exception{
            int N = pp.getN("select count(*) from "+reftab);
            if(N<pp.page2)
            return "<td valign=bottom><select name='"+tabcol+
                    "' onChange='openTable(this,\""+reftab+"\")'>"+
                    P2Node.refList(pp,reftab,i)+
                    "</select></td>";
            String S="<td valign=bottom><input size=40 type=text name='"+tabcol+"' "
                        +blurInt()+"></td>";
            return S;
    }
    
    public String addCell() throws Exception{
        String S;
        if(isKey()){
            S=refInput(0);
        } else{
            if(isPwd()){
                S="<td valign=bottom><input type=password name='"+tabcol+"' value='********' "+
                        "></td>";
            }else if(isBoolean()){
                S="<td valign=bottom><input type=checkbox name='"+tabcol+"' ></td>";
            }else{
                S="<td valign=bottom><input size=40 type=text name='"+tabcol+"' "
                        +blur()+"></td>";
            }
        }
        return S;
    }
    public boolean isKey(){
        return reftab!=null&&!reftab.equals("");
    }
   public void setKey()throws Exception{
        String q = "select ref_table from SYS_INFO.sUSRFKeyInfo,SYS_INFO.sUSRForeignColumns "+
                " where id=fk_id and fcolumn='"+name+
                "' and \"table\"='"+tabname+"' and \"schema\"='"+pp.schema+"'";
         reftab=pp.getS(q);
         String q2 = "select "+name+" from "+tabname+" where id=0";
         sValue=pp.getS(q2);
    }
   public boolean keyCol()throws Exception{
        String q = "select count(*) from SYS_INFO.sUSRFKeyInfo,SYS_INFO.sUSRForeignColumns "+
                " where id=fk_id and fcolumn='"+name+
                "' and \"table\"='"+tabname+"' and \"schema\"='"+pp.schema+"'";
        return pp.getN(q)>0;
    }
    boolean isPwd(){
        return name.equals("pwd");
    }
    public boolean isBoolean()throws Exception{
        return dtype==16;
    }
    public static String apo(String s)throws Exception{
        String s2 = s.replaceAll("[']","''");
        return s2.replaceAll("[&]","&amp;");
        //return s2.replaceAll("[&][#]","&amp;#");      
    }
    
    String blurInt(){
        return " title='Integer:0123456789' " +
                " onBlur=\" if(!value.match(/^\\d*$/)) alert(value+\' -not integer\')\" ";
    }
       
    String blur(){
        switch(dtype){
            case 4:/*integer*/ return
                    " title='Integer:0123456789' " +
                    " onBlur=\" if(!value.match(/^\\d*$/)) alert(value+\' -not integer\')\" ";
            case 91:/*date*/ return
                    " title='YYYY-MM-DD' onBlur=\" if(value.length>0 &&"+
                    " !value.match(/^\\d{4}[-]\\d{2}[-]\\d{2}$/)" +
                    " || 12<(0+value.substring(5,7)) || 31<(0+value.substring(8,10)) "+
                    " || 1>(0+value.substring(5,7)) || 1>(0+value.substring(8,10)) " +
                    " ) "+
                    " alert(value+\' -not YYYY-MM-DD\')\" ";
            case 92:/*time*/ return
                    " title='HH:MM:SS' onBlur=\" if(value.length>0 &&"+
                    " !value.match(/^\\d{2}[:]\\d{2}[:]\\d{2}$/)" +
                    " || 24 <(0+value.substring(0,2))" +
                    " || 60 <(0+value.substring(3,5)) " +
                    " || 60 <(0+value.substring(6,8)) " +
                    " ) "+
                    " alert(value+\' -not HH:MM:SS\')\" ";
            case 93:/*date+time*/ return
                    " title='YYYY-MM-DD HH:MM:SS'  " +
                    " onBlur=\" if(value.length>0 && "+
                    " !value.match(/^\\d{4}[-]\\d{2}[-]\\d{2}[ ]\\d{2}[:]\\d{2}[:]\\d{2}$/) "+
                    " || 12<(0+value.substring(5,7)) || 31<(0+value.substring(8,10)) "+
                    " || 1>(0+value.substring(5,7)) || 1>(0+value.substring(8,10)) " +
                    " || 24 <(0+value.substring(11,13)) " +
                    " || 60 <(0+value.substring(14,16)) " +
                    " || 60 <(0+value.substring(17,19)) " +
                    " ) "+                    
                    " alert(value+\' -not YYYY-MM-DD HH:MM:SS\')\" ";
            default:        return "";
        }
        
    }
    
    static int shift(int i,int r,int N){
        int n = (i+r)%N;
        return n;
    }
    
    static int shift2(int i,int r,int N){
        int n = (i-r+N*r)%N;
        return n;
    }
    public String value(){
//        if(dtype==16&&sValue==null)return null;
        if(sValue==null) sValue="";
        sValue = sValue.trim();
        switch(dtype){
            case 1://
            case -1:
            case 91:
            case 93:
            case 92:
            case 12:return "'"+sValue+"'";//varchar
            case 16:return (sValue.equals("")?"false":"true");
            default:return sValue;
        }
    }
    
    public void setCase()throws Exception{
        String q = "select o.id from "+reftab+
                " t,object o where o.id=t.id and o.id>0 order by o.code";
        kvars = pp.getNN(q);
    }
    
    
    public void  setUnique(boolean b)throws Exception{
        if(getUnique()==b)return;
        String q = b?
            "alter table "+tabname+" add constraint uq_"+tabname+"_"+name+" unique ("+name+")"
                :"alter table "+tabname+" drop constraint uq_"+tabname+"_"+name;
        pp.exec(q);
    }
    public boolean getUnique()throws Exception{
        String uq = "uq_"+tabname+"_"+name;
        String q = "select count(*) from SYS_INFO.sUSRUniqueInfo where name='"+uq+"'";
        return pp.getN(q)>0;
    }
    public String lineForm()throws Exception{
        int id=0;
        return "<input type=hidden name=id value=A"+id+">"+
                "<tr><td>"+getRus()+"</td>"+editCell()+"</tr>";
    }
    
    
    public String lineForm(int id)throws Exception{
        String q2="select sql_type from sUSRTableColumns where \"schema\"='" +pp.schema+
                "' and \"table\"='" +tabname+
                "' and \"column\"='" +name+"'";
        String q = "select ref_table from  " +
                " sUSRForeignColumns,sUSRFKeyInfo where " +
                " id=fk_id and \"schema\"='" +pp.schema+
                "' and \"table\"='" +tabname+
                "' and fcolumn='" +name+ "'";
        reftab = pp.getSinfo(q);  
        dtype = pp.getNinfo(q2);
        setValue(pp.getS("select "+name+" from "+tabname+" where id="+id));
        return "<input type=hidden name=id value=A"+id+">"+
               "<input type=hidden name=dtype value="+dtype+">"+ 
                "<tr><td>"+getRus()+"</td>"+editCell()+"</tr>";
    }  
    
    public static Field getField(XPump pp,String tab,String col)throws Exception{//id,code,name
        String q = "select type_desc,sql_type from sUSRTableColumns where "+
                " \"schema\"='"+pp.schema+"' and \"table\"='"+tab+"' " +
                " and \"column\"='" +col+"'";       
        String q2 = "select ref_table,name from  " +
                " sUSRForeignColumns,sUSRFKeyInfo where " +
                " id=fk_id and \"schema\"='" +pp.schema+
                "' and \"table\"='" +tab+
                "' and fcolumn='" +col+
                "'";
        
        Result r=new Result();
        Result r2=new Result();
                
        String dtype_name;
        int dtype;
        String pktable;
        String fk_name;
        Field f=null;
        try{
            pp.selectInfo(r,1,q);
            r.rs.next();
            dtype_name = r.rs.getString("type_desc");
            dtype = r.rs.getInt("sql_type");
            
            f = new Field
                    (pp,tab,col,dtype_name,dtype);
            r.close();///////////////////////
            pp.selectInfo(r2,2,q2);
            r2.rs.next();
           
            f.reftab=r2.rs.getString("ref_table");
            f.fk_name=r2.rs.getString("name");
            
        if(f.reftab!=null){
            f.bhRef = PhantomParent.hasPhantomChildren(pp,tab,col);//pp.getN(q3)>0;  
            if(f.bhRef){
                    String q5="select blind from refhh where tab='" +tab+ "' and col='" +col+ "'";
                    f.bhRefBlind = pp.getB(q5);
//                if(true)throw new Exception("=="+q5+"==");
            }
        }
        }finally{r.close();r2.close();}
        return f;
    }
    
    public String baseForm(boolean b) throws Exception {
        if(b)dtype=16;
        return lineForm()+"<input type=hidden name=typecode value="+tabname+">";   
    }
    public String baseForm() throws Exception {
        return lineForm()+"<input type=hidden name=typecode value="+tabname+">";
    }
    public void addTC(String xname,String more)throws Exception{
        pp.initFat(tabname);
        String q2=null;
        TabCol.setRus(pp,tabname,name,xname.trim());
        if(!Character.isJavaIdentifierStart(more.charAt(0)))addField(more);
        else addKey(more);
    }
    
    void addField(String sd)throws Exception{
        int d = Integer.parseInt(sd);
        String format = DbFormat.d2t(d);
        String q = "alter table "+tabname+" add column "+name+" "+format+" ";
        if(format.equals("4") )q+=" default 0 ";
        if(format.equals("16"))q+=" default false ";
        try{
            pp.exec(q);
        }catch(Exception e){
            throw new ExTabCol(pp.iLang);
        }
    }
    
    void addKey(String reftable)throws Exception{     
        String q = "alter table "+tabname+" add column "+name+" int default 0 ";
        String q3=
                "alter table "+tabname+" add constraint fk_"+tabname+"_"+name+
                " foreign key ("+name+")references "+reftable;
        try{
            pp.exec(q);
        }catch(Exception e){
            throw new ExTabCol(pp.iLang);
        }
        try{
            pp.exec(q3);
        }catch(Exception e){
            throw new ExRefTable(pp.iLang);
        }
    }
    public void drop()throws Exception{
        pp.initFat(tabname);
        String q =
                " select name from "+
                " sUSRFKeyInfo,sUSRForeignColumns where id=fk_id and "+
                " \"schema\"='"+pp.schema+
                "' and fcolumn='"+name+"' and \"table\"='"+tabname+"' ";
        String fkname = pp.getSinfo(q);
        if(fkname!=null&&!fkname.equals("")){
            q = "alter table "+tabname+" drop constraint "+fkname;
            pp.exec(q);
        }
        q = "alter table "+tabname+" drop column "+name;
        pp.exec(q);
        q = "delete from translator where tabcol='"+tabcol+"'";
        pp.exec(q);
    }
    
    public String linePoint(String rc,int k)throws Exception{
        setCase();
        String txt = getRus();
        qvars = new ArrayList();
        qcodes = new ArrayList();
        int h = txt.indexOf("<ul>");
        head = h<0?txt:txt.substring(0,h);
        List<String> keys = pp.getSS(
                "select code from object o,"+reftab+
                " r where o.id=r.id and o.id>0 order by code");
        for(String code:keys){
            String start = "<li>";
            String start2 = "("+code+")";
            String finish = "</li>";
            int ii,i,j;
            ii = txt.indexOf(start);
            i = txt.indexOf(start2,ii);
            j = txt.indexOf(finish,i);
            if(i>=0 && j>0){
                int i2 = i+start2.length();
                String qv = txt.substring(i2,j);
                qv = qv.trim();
                qvars.add(qv);
                qcodes.add(code);
                txt = txt.substring(j);
            }
        }
        
        String s = head+"<ul>";
        String ss = "";
        
        int N = qvars.size();
        if(N>0){
            int r = Integer.parseInt(rc);
            for(int j=0;j<N;j++){
                s+="<li>("+qcodes.get(j)+")"+
                        qvars.get(shift(j,r,N))+"</li>";

            }
            int ix=-2;
            int idx=0;
            int iy=-2;

            int iS=0;
            try{
                iS = Integer.parseInt(sValue);
            }catch(Exception e){throw new Exception("val:"+sValue+":rus:"+txt);}
            if(iS>0){
                ix = shift2(Table.idx(iS,kvars),r,N);
                iy = kvars.get(ix);
                sValue = ""+iy;
            }
        }
        ss = editCell();
        return "<tr><td>"+s+"</ul></td>"+ss+"</tr>";
    }
    
    public boolean isAuto(){
        return false;
//        tabcol.equals("process::seed")||
//        tabcol.equals("process::father")||
//        tabcol.equals("process::author");
    }
    public int nMix()throws Exception{ 
        if(!keyCol())return -1;
        if(name.charAt(0)!='q') return -1;
        try{return Integer.parseInt(name.substring(1));}
        catch(Exception e){return -1;}            
    }  
    
    public String dtName()throws Exception{return dt_name;}
    public String refTab()throws Exception{
        return reftab;
    }       
    
    String hhBox(){
        String s = 
      reftab==null?"":
          "<input type=checkbox title='phantom link' " +(bhRef?" CHECKED ":"")+
                "onClick='javascript:f(\""+reftab+"\",\""+tabname+"\",\""+name+"\"," +!bhRef+")'>"+
          "<input type=checkbox title='is blind' " +(bhRefBlind?" CHECKED ":"")+
                "onClick='javascript:fblind(\""+tabname+"\",\""+name+"\"," +!bhRefBlind+")'>"
                ;
        
        return "</td><td>" +s+ "</td><td>";
    }
    
    public void setRefhh(boolean b){
        bhRef = b;
    }    
    public void setRefhhBlind(boolean b){
        bhRefBlind = b;
    }    
}
