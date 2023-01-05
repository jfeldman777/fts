/*
 * Object.java
 *
 * Created on 14 Март 2005 г., 8:14
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.*;
import j5feldman.ex.*;
import java.sql.Date;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Jacob Feldman
 */
public class MyObject  extends Proc implements IProc{ 
    
    /** Creates a new instance of Object */
    public MyObject() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        map.put("501","1->01"); 
        mapE.put("501","1->01");
        map.put("502","XX->Xx"); 
        mapE.put("502","XX->Xx");
        map.put("503","X->x"); 
        mapE.put("503","X->x"); 
        map.put("504","x->X"); 
        mapE.put("504","x->X");
        
        map.put("505","split"); 
        mapE.put("505","split"); 
        
//        map.put("506","chain split"); 
//        mapE.put("506","chain split");
        
        map.put("507","all up"); 
        mapE.put("507","all up");
        
//        map.put("508","merge"); 
//        mapE.put("508","merge");         
        
        
        map.put("700","Поля-наклейки"); 
        mapE.put("700","shadow cols of shadows"); 
        
        map.put("999","Обновления за последние Х дней"); 
        mapE.put("999","For last X days: updated items"); 
        
        map.put("998","Ярлыки");         
        mapE.put("998","Shortcuts");   
        
        map.put("888","Все дети в одной таблице"); 
        mapE.put("888","family report");   
        
        map.put("887","Все дети в одной таблице кратко"); 
        mapE.put("887","family report in brief"); 
        
        map.put("880","Все дети "); 
        mapE.put("880","family ");      
        
        map.put("555","now"); 
        mapE.put("555","now");     
        
        bForce.add("998");   
        bForce.add("999");    
        bForce.add("887");   
        bForce.add("888");
        bForce.add("700");
        bForce.add("501");   
        bForce.add("502");
        bForce.add("503");
        bForce.add("504"); 
        
        bForce.add("505");  
//        bForce.add("506");  
        bForce.add("507");  
        bForce.add("555");  
    }
    

    
    public String parDesc(int id,int k){
      switch(pp.iLang){
        case 0:switch(k){
            case 700:return "Поля-наклейки";
            case 999: return "Найти обновления за последние Х дней (по умолчанию Х=1, вчера и сегодня)," +
                    " кроме базовых типов, <=100 записей";
            case 998: return "Найти ярлыки данного узла, показать их путь";   
            case 888: return "все дети в одной таблице";     
            case 887: return "все дети в одной таблице кратко ";  
            
            case 880: return "все дети "; 
            case 501:   return "1->01.....2=name, 1=code(default)";
            case 502:   return "XX->Xx.....2=name, 1=code(default)";
            case 503:   return "XX->xx.....2=name, 1=code(default)";
            case 504:   return "xx->XX.....2=name, 1=code(default)";
            
            case 505: return "split";
//            case 506: return "chain split";
            case 507: return "all up";
            case 555: return "now";
                
        }
        default: switch(k){
            case 700:return "shadow cols of shadows";            
            case 999: return "updated items for last X days (by default X=1, yesterday and today) " +
                    "without basic types, <=100 records";    
            case 998: return "find shortcuts for the node? show path to it";      
            case 888: return "family report";   
            case 887: return "family report in brief ";
            
            case 880: return "family";      
            
            case 501:   return "1->01.....2=name, 1=code(default)";
            case 502:   return "XX->Xx.....2=name, 1=code(default)";
            case 503:   return "XX->xx.....2=name, 1=code(default)";
            case 504:   return "xx->XX.....2=name, 1=code(default)";     
            
            case 505: return "split";
//            case 506: return "chain split";
            case 507: return "all up";
            case 555: return "now";            
        }
      }
        return "";
    } 
    
    public String f700() throws Exception {
        List<Integer> L = pp.getNN("select id from object where id2="+id);
        int t = pp.getN("select type from object where id="+id);
        String s = "<table border=1><tr><td>#</td><td>...</td>" + FTree.InheritanceTree.reportHeaderShadow(pp,t,false)+"</tr>";
        int N = 0;
        for(Integer ID:L){
            N++;
            s+="<tr><td>" +N+ "</td><td>";
                List<P2Node>  lp2 = P2Node.pathUp(pp,FTree.ObjectTree,ID,0);
                for(P2Node p2:lp2) {
                    if(p2.id2()>0)s+=p2.nameOrigin(pp);else
                    s+=p2.nameRcode();
                    s+="/";
                }
                s+="</td>";
                s+=FTree.InheritanceTree.reportLineShadow(pp,ID,t);
                s+="</tr>";
         }  
        return s+="</table>";
    }
    
    int days = 1;
    public String f887() throws Exception {
        return f888(true);
    }  
    public String f888() throws Exception {
        return f888(false);
    } 
    public String f888(boolean inBrief) throws Exception {
        String code = pp.getS("select code from object where id="+id);
        String name = pp.getS("select name from object where id="+id);
//        String top = //"<p><b>("+code+")"+name+"</b>" +
//                "<table border=1><tr>";
        List<Integer> LL = pp.getNN("select distinct type from object where type>0 and id2=0 and parent="+id+" order by type ");
        String q9 = "select distinct o.type from " +
                Substitution.fromNwhere(true,id,"o","o2")+
                " order by o.type ";
        List<Integer> LL5 = pp.getNN(q9);
        
        LL.addAll(LL5);
        String s="";
        for(int t:LL){

            String q2 = "select code from type where id="+t;
            String typecode = pp.getS(q2);
            String q3 = "select name from type where id="+t;
            String typename = pp.getS(q3);
            boolean start=true; 
            String q = "select id from object where type="+t+" and parent="+id+" and id2=0 order by code";
            List<Integer> II = pp.getNN(q);
            
            String q5 = "select o.id from  " +Substitution.fromNwhere(true,id,"o","o2")+
                    " and o.type="+t+
                    " order by o.code";
            List<Integer> II5 = pp.getNN(q5);
            II.addAll(II5);
             for(int i:II){              
                if(start){
                    s+="<p>" + j5feldman.proc.basement.Icons.icon(pp,typecode)+
                            " <b>"+typename+
                            "</b><table border=1><tr>" + FTree.InheritanceTree.reportHeaderView(pp,t,inBrief)+"</tr>"; 
                    start=false;
                }
                s+="<tr>"+FTree.InheritanceTree.reportLineView(pp,i,t)+"</tr>";
                
            }
            s+="</table>";
        }
        return s;
    }    
    public String f880() throws Exception {
            String s="<table border=1>";
            boolean start=true; 
            List<P20Node> LL = P20Node.getP20children(pp,id);
            LL.addAll(P20Node.getP20parent2(pp,id));
            for(P20Node p:LL){
                    s+="<td><a href='../root.jsp?id=A"+p.id()+"' target=left title=set-root>"+p.code()+
                            "</a></td><td>"+p.name()+               
                            "</td></tr>"; 
            }   

        return s+"</table>";
    }
    public String f998() throws Exception {
        List<Integer> L = pp.getNN("select id from object where id2="+id);
        String s = "<hr>";
        for(Integer ID:L){
                List<P2Node>  lp2 = P2Node.pathUp(pp,FTree.ObjectTree,ID,0);
                for(P2Node p2:lp2) s+=p2.code() + "/";
                s+="<br>";
         }  
        return s+="<hr>";
    }
    
    public String f501()throws Exception{
        Box ttc = new Box(par);
        List<P20Node> L = getList();
        int N = 0;
        for(P20Node p:L){
            String z = ttc.getVal(p);
            int len = z.length();
            if(N < len) N = len;
        }
        
        List<String> QQ = new ArrayList<String>();
        for(P20Node p:L){
            String s = ttc.getVal(p);
            int len2 = s.length();
            for(int j=0; j<N-len2; j++){
                s="0"+s;
            }
            
            String q = "update object set code='" +s+ "' where id="+p.id();
            QQ.add(q);
        }
        
        pp.exec(QQ);
        return ""+QQ.size();
    }
        
    public String f502()throws Exception{
        Box ttc = new Box(par);
        List<P20Node> L = getList();

        List<String> QQ = new ArrayList<String>();
        for(P20Node p:L){
            String s = ttc.getVal(p);
            String s2 = "";
            for(int i=0;i<s.length();i++){
                String sx = s.substring(i,i+1);
                boolean b= i==0;
                
                s2 +=b?sx.toUpperCase():sx.toLowerCase();
            }

            String q = "update object set " +ttc.getCol()+
                    "='" +s2+ "' where id="+p.id();
            QQ.add(q);
        }
        
        pp.exec(QQ);
        return ""+QQ.size();
    }
    
    public String f503()throws Exception{
        Box ttc = new Box(par);
        List<P20Node> L = getList();

        List<String> QQ = new ArrayList<String>();
        for(P20Node p:L){
            String s = ttc.getVal(p);
            s = s.toLowerCase();
            String q = "update object set " +ttc.getCol()+"='" +s+ "' where id="+p.id();
            QQ.add(q);
        }
        
        pp.exec(QQ);
        return ""+QQ.size();
    }
    
    public String f504()throws Exception{
        Box ttc = new Box(par);
        List<P20Node> L = getList();

        List<String> QQ = new ArrayList<String>();
        for(P20Node p:L){
            String s = ttc.getVal(p);
            s = s.toUpperCase();
            String q = "update object set " +ttc.getCol()+"='" +s+ "' where id="+p.id();
            QQ.add(q);
        }
        
        pp.exec(QQ);
        return ""+QQ.size();
    }
    
    public String f505()throws Exception{//split
        int L = pp.page3-1; 
        List<Integer> II = pp.getNN("select id from object where parent="+id+" order by code ");
        int N = II.size();
        int NP = N/L;
        
        for(int i=0; i<NP; i++){
            CreateObj c =  new CreateObj(pp,null,II.get(L*i),null);      
            int ID = c.createFolderAboveNoRefresh();
            
            for(int j=1; j<L; j++){
                pp.exec("update object set parent="+ID+" where id="+II.get(j + L*i));            
            }
        }
        
        return "";
    }
   
    
//    public String f506()throws Exception{//chain split
//        return "";
//    }
    public String f507()throws Exception{//all up
        List<Integer> II = pp.getNN("select id from object where parent="+id+" order by code ");
        int parent = pp.getN("select parent from object where id="+id);
        for(int i=0;i<II.size();i++){
            pp.exec("update object set parent="+parent+" where id="+II.get(i));
        }
        return "";
    }
    public String f555()throws Exception{//now
        String q = "update object set creation_date=dateob(), last_update=dateob() where id="+id;
        //if(true)throw new Exception(q);
        pp.exec(q);
        return "";
    }    
//    public String f508()throws Exception{//merge
//        return "";
//    }    
    
    public String result(String fun,String par) throws Exception {
        this.par = par;
        try{days = Integer.parseInt(par);}catch(Exception e){}
        int f = Integer.parseInt(fun);
        switch(f){
            case 999:break;
            case 998:return f998(); 
            case 888:return f888();
            case 880:return f880();          
            case 887:return f887(); 
            case 700:return f700();  
            case 501:return f501();  
            case 502:return f502();  
            case 503:return f503();  
            case 504:return f504();   
            
            case 505:return f505();  
//            case 506:return f506();  
            case 507:return f507();  
            case 555:return f555();              
            default: return "";
        }
 
        String s = "<hr>";
        String q = "select count(*) from object where type<>1 and " +
                " last_update >= ? and id=? ";
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR,-days);
        java.sql.Date d = new java.sql.Date(cal.getTimeInMillis()); 
        int N=0;
        long L = System.currentTimeMillis();
        
        List<Integer> lp = P3Node.allDown30(pp,id,L+20000);///max=300 

        for(Integer ip:lp){
            int ic = pp.getNdi(q,d,ip); 
            if(ic>0){
                N++;
                if(N>100)break;
                List<P2Node>  lp2 = P2Node.pathUp(pp,FTree.ObjectTree,ip,id);
                for(P2Node p2:lp2){
                s+="<a title='"+p2.nameOrigin(pp)+"' href='../object2.jsp?id=A" +p2.id()+
                        "' target=right>" + p2.codeOrigin(pp) + "</a>/";
                }
                s+="<br>";
            }
         } 
        
         return s+"<hr>";
    }
  
    List<P20Node> getList()throws Exception{
            String q = "select o.id,o.code,o.name from object o, object t where o.id=t.id and o.parent="+id;
            List<P20Node> L = P20Node.getP20list(pp,q);       
            return L;
        }
   
    class Box{
       boolean forName;
        Box(String p)throws ExNoPars{
            forName = p.equals("2");
        }
        
        String getCol(){
            return forName?"name":"code";
        }
        
        String getVal(P20Node p){
            return forName? p.name():p.code();
        }
   }
}
