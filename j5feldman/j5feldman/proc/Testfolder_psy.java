/*
 * Testfolder_psy.java
 *
 * Created on 19 Январь 2006 г., 8:44
 * Auhor J.Feldman
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.awt.KeyEventDispatcher;
import java.util.*;
import java.sql.*;
import j5feldman.proc.basement.Proc;
import j5feldman.proc.basement.IProc;
/**
 *
 * @author Jacob Feldman
 */
public class Testfolder_psy extends Proc implements IProc{
//    final static String qKeySet = "create table keyset("+
//            " id int, "+
//            " meaning text default '', "+
//            " numbers text default '', " +
//            " constraint pk_keyset primary key (id), "+
//            " constraint fk_keyset foreign key(id)references object "+
//            " on delete cascade"+
//            ")"; 
//    
//    final static String tKeySet =
//            "insert into type (id,code,name)values(NEXTVAL('type_id'),'keyset','Ключи для пси-тестов')";
//    final static String zKeySet =
//            "insert into keyset (id)values(0)";
//    
//    final static String qKeyFolder = "create table keyfolder("+
//            " id int, "+
//            " b_typecode text default '', "+
//            " constraint pk_keyfolder primary key (id), "+
//            " constraint fk_keyfolder foreign key(id)references object "+
//            " on delete cascade"+
//            ")";  
//    
//    
//    final static String tKeyFolder =
//            "insert into type (id,code,name)values(NEXTVAL('type_id'),'keyfolder','Папка для пси-ключей')";
//    final static String zKeyFolder =
//            "insert into keyfolder (id)values(0)";
//
//    final static String qAddField = "alter table testfolder_psy add column kfo_id INT default 0";
//    final static String qAddField2 = "alter table testfolder_psy add " +
//            "constraint fk_testfolder_psy_kfo_id (kfo_id) foreign key(kfo_id)" +
//            "references keyfolder ";
//    
//    final static String[] QQall = {
//        qKeyFolder,tKeyFolder,zKeyFolder,qKeySet,tKeySet,zKeySet,qAddField,qAddField2
//    };
    
    /** Creates a new instance of Testfolder_psy */
//    boolean notYet()throws Exception{
//        int i=pp.getN("select id from type where code='keyset'");
//        return (i<0);
//    } 
//    void installTypes()throws Exception{
//        pp.exec(QQall);
//    } 
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
//        if(notYet()){
//            map.put("0","установить типы");
//            mapE.put("0","initialize");
//            installTypes();
//        }else{
            map.put("88","для одного, введите КОД бланка");
            map.put("89","по группе детально, введите код ключа сортировки");
            map.put("90", "по группе интегрально");         
            
            mapE.put("88","one person after CODE of the page");
            mapE.put("89","group in details, put SORT KEY as a parameter");
            mapE.put("90", "group total");            
//        }
    }

    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 1:switch(k){
//                case 0:return "initialization";
                case 88:return "Cases and the % for one person.<br>" +
                "Put CODE of the page as a parameter";
                case 89:return "Put CODE of the SORT KEY as a parameter";
                case 90:return "Cases и % of the group";
            }
            case 0:switch(k){
//                case 0:return "установка недостающих типов";                
                case 88:return "Количество и % несовпадений для одного человека.<br>" +
                "Задайте КОД бланка";
                case 89:return "введите код ключа сортировки";
                case 90:return "Количество и % несовпадений в группе";
            }
        }
        return "";
    } 
    
    int ID=0;
    int funN=0;
    String bTypecode = null;
    int keyfolderID;
    List<KBox> kBoxes;
    List<BBox> bBoxes;
    BBox bBox = null;
    
    public String result(String fun,String par) throws Exception {
            keyfolderID = pp.getN("select kfo_id from testfolder_psy where id="+id); 
            bTypecode = pp.getS("select b_typecode from keyfolder where id="+keyfolderID);
       
            if(bTypecode == null)
                throw new Exception("\n the field  .b_typecode. in the folder IS NOT SET!!! \n");
            
            this.par = par;
        if(fun!=null&&!fun.equals(""))funN=Integer.parseInt(fun);
        switch(funN){
            case 88:case 89: case 90:
//            case 0:
                break;
            default: return "";
        }
        if(funN==88){
            ID = pp.getN("select id from object where parent="+id+
                    " and code='" +par+
                    "'");
        }
       
        setBBlist(ID);
        setKBlist();         

        if(funN==88||funN==90)
        for(KBox kb:kBoxes){
            for(BBox bb:bBoxes){
                bb.N=0;bb.SN=0;
                for(int K:kb.keys){
                    Boolean b0 = bBox.map.get("b"+K);
                    Boolean b = bb.map.get("b"+K);
                    kb.N++;bb.N++;
                    if(b!=b0) {kb.SN++;bb.SN++;}
                }
                int p = procent(bb.SN,bb.N);
                if(p>50)kb.n50++;
                if(p>75)kb.n75++;
            }
        }
        if(funN==88)return resOne();        
        if(funN==90)return resTable();

        for(BBox bb:bBoxes){
            for(KBox kbx:kBoxes){
                KBox kb =  new KBox(kbx);
                kb.N=0;kb.SN=0;         
                for(int K:kb.keys){
                    Boolean b0 = bBox.map.get("b"+K);
                    Boolean b = bb.map.get("b"+K);
                    kb.N++;bb.N++;
                    if(b!=b0) {kb.SN++;bb.SN++;}
                }
                int p = procent(bb.SN,bb.N);
                if(p>50)kb.n50++;
                if(p>75)kb.n75++;
                bb.myKBoxes.add(kb);
            }
         }        
        return resMany();
    }
    String resMany()throws Exception{
        String s = "<table border=2><tr><td>код бланка";
        String TD = "</td><td>";
        for(KBox kb:kBoxes){
            s+=TD + kb.code;
        }
            s+="</td></tr>";    
            
        Object[] bj = bBoxes.toArray();
        Arrays.sort(bj);
            
        for(int i=0;i<bj.length;i++){
            BBox bb=(BBox)bj[i];
            s+="<tr><td>"+bb.code;
            for(KBox kb:bb.myKBoxes){
                s+=TD+procent(kb.SN,kb.N);
            }
            s+="</td></tr>";            
        }
        s+="</table>";
        return s;
    }
    String resOne()throws Exception{
        String s = "<table border=2>";
        String TD = "</td><td>";
        switch(pp.iLang){
            case 0:s+="<tr><td>Критерий</td><td>%несовпадений</td>" +
                "</tr>";break;
            case 1:s+="<tr><td>Criteria</td><td>% of bad cases</td>" +
                "</tr>";break;                
        }
        for(KBox kb:kBoxes){
            s+="<tr><td>";
            s+=kb.code+"."+kb.meaning+TD+procent(kb.SN,kb.N);
            s+="</td></tr>";
        }
        s+="</table>";
        return s;
    }
    
    String resTable()throws Exception{
        String s = "<table border=2>";
        String TD = "</td><td>";

        switch(pp.iLang){
            case 0:s+="<tr><td>Критерий</td><td>%несовпадений</td>" +
                "<td> превысивших 50%</td><td> превысивших 75%</td></tr>";
                break;
            case 1:s+="<tr><td>Criteria</td><td>% of bad cases</td>" +
                "<td> above 50%</td><td> above 75%</td></tr>";
        }
        
        for(KBox kb:kBoxes){
            s+="<tr><td>";
            s+=kb.code+"."+kb.meaning+TD+procent(kb.SN,kb.N)+TD+kb.n50+TD+kb.n75;
            s+="</td></tr>";
        }
        s+="</table>";
        return s;
    }
    
    void setKBlist()throws Exception{
        kBoxes = new ArrayList<KBox>();            
        String q = "select o.code,b.* from object o,keyset b where " +
                " o.id2=0 and o.id=b.id and o.parent=" +this.keyfolderID+
                " order by o.code ";
        setKBlist(q);
        String q2 = "select o.code,b.* from object o,object o2,keyset b where " +
                " b.id>0 and o2.id2=o.id and o.id=b.id and o2.parent=" +this.keyfolderID+
                " order by o.code ";
        setKBlist(q2);
    }
    void setKBlist(String q)throws Exception{
        Result r=new Result();
        String n="",m="";
        String c="";
        try{
            pp.select(r,46,q);
            while(r.rs.next()){
                c = r.rs.getString("code");
                m = r.rs.getString("meaning");
                n = r.rs.getString("numbers");
                kBoxes.add(new KBox(c,m,n));
            }      
        }finally{r.close();}
    } 
    
    class KBox{
        String code;
        String meaning;
        String numbers;
        List<Integer> keys;
        int N=0;
        int SN=0;
        int n50=0;
        int n75=0;

        KBox(String code,String meaning,String numbers){
            this.code = code;
            this.meaning = meaning;
            this.numbers = numbers;
            keys = new ArrayList<Integer>();
            StringTokenizer tk = new StringTokenizer(numbers,",");
            while(tk.hasMoreTokens()){
                String num = tk.nextToken();
                int k = Integer.parseInt(num.trim());
                keys.add(k);
            }
        }
        
        KBox(KBox kk){
            this.code = kk.code;
            this.meaning = kk.meaning;
            this.numbers = kk.numbers;
            keys = new ArrayList<Integer>();
            for(Integer I:kk.keys){
                Integer II = new Integer(I);
                keys.add(II);
            }
        }
    }
    
    int procent(int SN,int N){
        if(N==0)return 0;
        int k = SN*100/N;
        return k;
    }
    
    class BBox implements Comparable
    {
        String codeSort = null;
        String code;
        int id;
        SortedMap<String,Boolean> map;
        int N=0;
        int SN=0;
       
        ArrayList<KBox> myKBoxes = new ArrayList<KBox>();
        
        BBox(int id)throws Exception{
            this.id = id;
            map = new TreeMap<String,Boolean>();
        }
        
        public int compareTo(Object o){
            BBox b = (BBox)o;
            if(par==null||par.equals(""))return code.compareTo(b.code);
            for(int i=0;i<myKBoxes.size();i++){
                if(myKBoxes.get(i).code.equals(par))return myKBoxes.get(i).SN-b.myKBoxes.get(i).SN;
            }
            return code.compareTo(b.code);
        }
    }
    
    void setBBlist(int ID)throws Exception{
        bBoxes = new ArrayList<BBox>();
        if(ID>0){
            String q1 = "select 'o' code,* from " +bTypecode+
                " where " +
                " id=" +ID;
            bBoxes.addAll(getBBlist(q1));
        }else{
            String q = "select o.code,b.* from object o," +bTypecode+
                " b where " +
                " o.id2=0 and o.id=b.id and o.parent=" +id+" order by o.code ";
                       
            bBoxes.addAll(getBBlist(q));
            String q2 = "select o.code,b.* from object o,object o2," +bTypecode+
                " b where " +
                " b.id>0 and o2.id2=o.id and o.id=b.id and o2.parent=" +id+" order by o.code ";
            bBoxes.addAll(getBBlist(q2));
        }
        String q0 = "select 'o' code,* from " +bTypecode+
                " where id=0 ";
        try{
            bBox = getBBlist(q0).get(0);
        }catch(Exception e){
            throw new Exception("//"+q0+"//");
        }
    }
    List<BBox> getBBlist(String q)throws Exception{
        List<BBox> ar= new ArrayList<BBox>(); 
        Result r=new Result();
        try{    
            pp.select(r,47,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            while(r.rs.next()){
                int j = r.rs.getInt("id");
                BBox bx = new BBox(j);
                bx.code = r.rs.getString("code");
                for(int i=0;i<N;i++){
                    Boolean x;
                    try{
                        x = r.rs.getBoolean(i+1); 
                        String colname = md.getColumnName(i+1); 
                        bx.map.put(colname,x);
                    }catch(Exception e){}
                }
                ar.add(bx);
            }
        }finally{r.close();}
        return ar;
    }
}
