/*
 * DbfImport.java
 *
 * Created on 31 Май 2007 г., 13:30
 * Auhor J.Feldman
 */

package j5feldman.dbf;
import j5feldman.proc.basement.*;
import java.io.*;
import java.util.*;
import j5feldman.*;
import javax.servlet.http.*;  
import java.nio.charset.*;

/**
 *
 * @author Jacob Feldman
 */
public class DbfImport {
    int parent;
    GuiTree.GuiNode gn;
    XPump pp;
    String fname=null;
    File file=null;
    FdbfReader reader;
    Table table = null;
    Map<String,Map<String,Integer>> myMaps = new TreeMap<String,Map<String,Integer>>();
    Map<String,String> qqMap = new TreeMap<String,String>();
    List<String> errors = new ArrayList<String>();
    String ers="";
    /** Creates a new instance of DbfImport */
    public DbfImport(XPump pp,String fname,GuiTree.GuiNode gn,String sCharset,int parent) throws Exception{
        this.parent = parent;
        this.gn = gn;
        this.pp = pp;
        this.fname = fname;
        file = new File(fname);
        table = pp.getTable(file.getName());
        reader = new FdbfReader(fname,Charset.forName(sCharset));
        
    }
    
    public int go(HttpServletRequest req)throws Exception{ 
        String coFields = pp.getS("select co_fields from dbf_import where id="+gn.id());
        String coBoolean= pp.getS("select co_boolean from dbf_import where id="+gn.id());
        SortedMap<String,String> coFieldsMap = co2map(coFields);
        SortedMap<String,String> coBooleanMap = co2map(coBoolean);
        
        int type = pp.getN("select id from type where code='" +gn.name()+"'");

        List<Table> tList = GuiTree.getTableList(type,pp); 
        
        TreeMap<String,TreeMap<String,Integer>> mapMapKeys = new TreeMap<String,TreeMap<String,Integer>>(); 
        TreeMap<String,String> mapRefNames = new TreeMap<String,String>();
        
        for(Table t:tList){

            for(ITabCol f:t.fields){
                String ftc = f.tabcol();
                
                if(f.isKey() && coFieldsMap.values().contains(ftc)){
                    
                    mapRefNames.put(f.tabcol(),f.refTab());
                    
                    
                    if(!mapMapKeys.keySet().contains(f.refTab())){
                        TreeMap<String,Integer> mapCodeId = dict2map(pp,f.refTab());
                        mapMapKeys.put(f.refTab(),mapCodeId);
                        
                        
                    }
                }
            }
        }        
        
        int N=reader.getFieldCount();
        int M=0;
        int bN = 0;
        
        
        do{           
            String[] fo=reader.nextRecord();///////////////////////////////////////////////////////
            TreeMap<String,String> mapFieldValue = new TreeMap<String,String>();
            for(int i=0;i<N;i++){//fields
                FdbfReader.DbfFieldHeader f = reader.fieldsList.get(i);//////////////////////////                  
                
                String fn = f.getName();
                String sTabCol = coFieldsMap.get(fn);                    
                
                String s = fo[i].trim();    
          
                if(sTabCol!=null){

                    String refName = null;
                    Integer sID = null;
                    
                    if((refName=mapRefNames.get(sTabCol))!=null){
                                                
                        sID = mapMapKeys.get(refName).get(s);                     
                        
                        if(sID==null)sID=0;
                        s = ""+sID;
                    }
                    
                    mapFieldValue.put(sTabCol,s);
                }                
            }
            
            try{
                List<String> QQ = new ArrayList<String>(); 
                gn.doInsert(type,QQ,mapFieldValue,parent,tList);
                
                pp.exec(QQ);
            }catch(Exception e){
                System.out.println("bad record for import:"+ ++bN);
            }
        }while(reader.hasNextRecord());    
        ////////////////////////////////////////////////////////////////////////////////////

        return 0;
        
    }
    
    
    
    SortedMap<String,String> co2map(String co)throws Exception{
        return Substitution.fromString2(co);
    }
    
    void init2()throws Exception{
        for(ITabCol f:table.fields){
            String q = "select distinct o.id,o.code from object o," +f.refTab()+" t ";
            Map map = sql2map(pp,q);
            myMaps.put(f.name(),map);
        }
    }
    
    public String checkDict()throws Exception{
        int N=reader.getFieldCount();
        Integer ID=0;
        do{
            reader.nextRecord();
            for(int i=0;i<N;i++){
                FdbfReader.DbfFieldHeader f = reader.fieldsList.get(i);  
                Map<String,Integer> m = myMaps.get(f.getName());
                if(m!=null){
                    ID = m.get(f.toString());
                    if(ID==null){
                        String s = f.getName()+"::"+f.toString();
                        errors.add(s);
                        ers+=s + "<br>\n";
                    }
                }

            }
        }while(reader.hasNextRecord());
        return ers;
    }
        
    protected void finalize(){
        if(reader!=null)
            try {reader.close(); 
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
    }
    
    public static TreeMap<String,Integer> dict2map(XPump pp,String tab)throws Exception{
        String q = "select o.id,o.code from object o, " + tab + " t where t.id=o.id ";
        return sql2map(pp,q);
    }
    
    public static TreeMap<String,Integer> sql2map(XPump pp,String q)throws Exception{
        Result r=new Result();
        TreeMap<String,Integer> map = new TreeMap<String,Integer>();
        try{
            pp.select(r,35,q);
            while(r.rs.next()){
                int id=r.rs.getInt("id");
                String code=r.rs.getString("code");
                map.put(code,id);
            }
        }finally{r.close();}
        return map;
    }    
}
