/*
 * QScriptor.java
 * Created on 12 Сентябрь 2005 г., 9:48
 */

package j5feldman.proc.basement;
import java.util.*;
import j5feldman.*;

/**
 *
 * @author Jacob Feldman
 */
public class QScriptor {
    XPump pp;
        SortedMap<String,Integer> code2id;
    /** Creates a new instance of QScriptor */
    public QScriptor(String path,String parDesc) {
        this.path = path;
        this.parDesc = parDesc;
    }
    
    List <StrLR> intoList = new ArrayList<StrLR>();
    List <StrLR> into2List = new ArrayList<StrLR>();
    List <StrLR> keyrefList = new ArrayList<StrLR>();
    List <StrLR> ofList = new ArrayList<StrLR>();
    
    String parDesc;
    public String path;
    
    public void addInto(String left,String right){
        intoList.add(new StrLR(left, right));
    }
    
    public void addInto2(String left,String right){
        into2List.add(new StrLR(left, right));
    }    
    
    public void addKeyref(String left,String right){
        keyrefList.add(new StrLR(left, right));
    }
    
    public void addOf(String left,String right){
        ofList.add(new StrLR(left, right));
    }

    public void setMap(SortedMap<String,String> map,SortedMap<String,Integer> code2id)throws Exception{
        map.put("query::pardesc", parDesc);
        map.put("query::body", body(code2id));
    }
    
    public String body(SortedMap<String,Integer> code2id)throws Exception{
        String S = "";
        String where = "";
        String from = "";
        boolean start = true;        
        for(StrLR Sz:ofList){
            String baseTable = Sz.right;
            String colListS = Sz.left;
            String t0 = tN(Sz.right, code2id);                
        
            StringTokenizer t = new StringTokenizer(colListS,",");
            boolean start2 = true;
            while(t.hasMoreTokens()){
                String Sx = t.nextToken();
                S+=(start?"":",");
                String sL;
                S+=tNtN(sL=sL(Sx), baseTable, code2id)+"."+sR(Sx);

                tabMap.put(tNtN(sL, baseTable, code2id),sL);
                where += (start2? ((start?"":" and ")+t0+".id>0 "):"")+
                        " and "+t0+".id="+tNtN(sL, baseTable, code2id)+".id ";                
                start2 = false;
            }
            tabMap.put(t0,baseTable);
            start = false;
        }

        
        for(StrLR sx:intoList){
            where+=" and t0t"+code2id.get(sx.left) +".parent=t0t" + code2id.get(sx.right)+".id ";
            tabMap.put("t0t"+code2id.get(sx.left),"object");
            tabMap.put("t0t"+code2id.get(sx.right),"object");            
        }
        
        for(StrLR sx:into2List){
            from+=",t0t"+code2id.get(sx.left) +"h";
            where+=" and t0t"+code2id.get(sx.left) +"h.id2=t0t" + code2id.get(sx.left)+".id "+
                    " and t0t"+code2id.get(sx.left) +"h.parent=t0t" + code2id.get(sx.right)+".id ";
        }
        
        for(StrLR sx:keyrefList){
            where+=" and  "+
                    "t"+code2id.get(sx.left)+"."+sR(sx.left) +"=t"+code2id.get(sx.right)+".id";
        }
        start = true;
        for(String sx:tabMap.keySet()){
            from  += (start? "":",")+tabMap.get(sx)+" "+sx;
            start = false;
        }
        return " SELECT " + S + " FROM " + from +
               " WHERE " +  where + " ORDER BY "+S;
    }
    
    String tN(String type,SortedMap<String,Integer> map){
        return "t"+map.get(type);
    }    
    
    String ttNtN(String type,String base,SortedMap<String,Integer> map){
        return " "+type+" "+tNtN(type,base,map);
    }
    
    String tNtN(String type,String base,SortedMap<String,Integer> map){
        if(type.equals(base))return tN(type, map);
        return tN(type,map)+tN(base, map);
    }
    
    String sL(String s){
        int i=s.indexOf("::");
        return s.substring(0, i).trim();
    }
    
    String sR(String s){
        int i=s.indexOf("::");
        return s.substring(i+2).trim();
    }    
    
    SortedMap<String,String> tabMap = new TreeMap<String,String>();
    
    class StrLR{
        String left;
        String right;
        StrLR(String left,String right){
            this.left = left;
            this.right = right;
        }
    }
   public List<String> qqx(boolean dbg)throws Exception{
        SortedMap<String,String> map = new TreeMap<String,String>();
        List<String> QQ = new ArrayList<String>();        
        setMap(map, code2id);          
        int child = 0;
            child = P2Node.path2id(pp,FTree.ObjectTree,path);
            map.put("object::id", ""+child);
            Table t = new Table(pp,"query",false);
            QQ.add(t.qUpdate(map));
            if(dbg) return QQ;
            pp.exec(QQ);
            return new ArrayList<String>();   
    }    
}
