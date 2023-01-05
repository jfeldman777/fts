/*
 * Proc.java
 *
 * Created on 6 04 2004, 15:42
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.charset.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
abstract public class Proc implements IProc{
   public String par;
   final public static String encoding="UTF-8";
   public XPump pp;
   public int id;
   public String dirPath;
   public String absPath;
   public String urlPath;
   public SortedMap<String,String> map = new TreeMap<String,String>();//Russian
   public SortedMap<String,String> mapE = new TreeMap<String,String>();//English   
   public SortedMap<String,String> mapF = new TreeMap<String,String>();//French
   public Set<String> bForce = new TreeSet<String>();//
   
    /** Creates a new instance of Proc */
   public Proc(){}
      public Set<String> getKeySet(){
       switch(pp.iLang){
           case 1:return mapE.keySet();
           case 2:return mapF.keySet();           
       }
       return map.keySet();
   }
   
   public boolean isForce(String fun){
        return bForce.contains(fun);
   } 
//      
   public String getK1()throws Exception{
       String s = getKeySet().iterator().next();
       return s;
   }   
   public String getK1(boolean force)throws Exception{
       String s = getKeySet().iterator().next();
       for(String x: getKeySet()){
            if(force&&bForce.contains(x)||
                    !force&&!bForce.contains(x)
            )return x;
       }
       return null;
   }
   
   
   String getK(String k){
       switch(pp.iLang){
           case 1:return mapE.get(k);
           case 2:return mapF.get(k);           
       }
       return map.get(k);
   }
   
    public void init(XPump pp, int id) throws Exception {
        this.pp = pp;
        this.id = id;
        setPath();        
    }    
       
    void setPath(){
        //try{
        urlPath = pp.pubpath(); 
        dirPath = pp.pubPath(); 
        absPath = dirPath+"/";
        File dir = new File(dirPath);
        if(!dir.exists())dir.mkdirs();
        //}catch(Exception e){}
    }
  
    String publishPath(){
        return absPath;
    } 
    String pubpath(){
        return urlPath;
    }
    
    void delete()throws Exception{
        String path = publishPath()+id+".htm"; 
        File f = new File(path);
        if(f.exists())f.delete();
    }  
    void write(String fun,String par)throws Exception{
        String path="::";
        File f=null;
        BufferedWriter bw=null;
        try{
            path = publishPath()+id+".htm"; 
            f = new File(path);
            if(f.exists())f.delete();            
            f.createNewFile();
            
            bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f),Charset.forName(encoding)));
            bw.write(webPage(fun,par));
            bw.flush();
        }
        finally{if(bw!=null)bw.close();}
    }
    String webPage(String fun,String par)throws Exception{//
        String s = "<html><head>" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset="+
        encoding+"\">" +
        "</head><body><table border=3>"+result(fun,par)+"</table></body></html>";
        return s;
    }
    
    public String publish(String fun,String par)throws Exception{  
        delete();
        write(fun,par);
        String http = pubpath()+id+".htm";
        return http;
    }
    
    public String parDesc(int id,int k){
        return "";//id="+id+"//k="+k;
    }    
    public String description(){
        String s="";
        for(String k:getKeySet()){
            s+="<option value='"+k+"'>("+k+")"+getK(k)+"</option>";
        }
        return s;
    }
    public String ulTreePub(){
        String s="<ul>";
        for(String k:getKeySet()){
            if(!bForce.contains(k))
            s+="<li><a target=right href='node" +id+
                    "f"+k+
                    ".htm'>("+k+")"+getK(k)+"</a></li>";
        }
        return s+"</ul>";
    }  
      
//    public String descriptionW(int num){
//        String s="<ul>";
//        for(String k:getKeySet()){
//            s+="<li><a href='exec.jsp?id=" +num+
//                    "&fun="+k+
//                    "'>("+k+")"+getK(k)+"</a></li>";
//        }
//        return s+"</ul>";
//    }    
    public String descriptionT(int id){
        return descriptionT(id,false);
    }
    
    public boolean hasForce(){
        return !bForce.isEmpty();
    }
    
    public String descriptionT(int id,boolean force){
        String s="";
        for(String k:getKeySet()){
//            System.out.println("k="+k);////////////////////////////////////////////////////
            if(force&&bForce.contains(k)||
                    !force&&!bForce.contains(k)
                    ){
//            System.out.println("force:"+force);//////////////////////////
            s+="<tr><td><a href='bottom.jsp?id=" +id+
                    "&k=" +k+
                    "' target=2>"+k+"</a></td><td>"+getK(k)+"</td>" +
                    "</tr>";
            }
        }
        return s;
    }     
    
    public String result(String fun,String par) throws Exception {
        return "";
    }   
       
    List<Integer> getChildrenIDs(int parent)throws Exception{
        String q = "select id from object where id2=0 and parent ="+parent+
        " order by code,id ";
        return pp.getNN(q);
    }  
    List<Integer> getChildrenIDs2(int parent)throws Exception{
        String q = "select o.id from "+
        " object o,object o2 where " +
                " o2.id2=o.id and o2.id2>0 and o2.parent ="+parent+
        " order by o.code,o.id ";
        return pp.getNN(q);
    }  

}
