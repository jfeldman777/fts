/*
 * Portfolio.java
 * Author J.Feldman
 * Created on 7 Ноябрь 2005 г., 7:52
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class Portfolio extends Proc implements IProc{
    String q(int id){
         String s2=" select " +
                 " os.name \"студент\"," +
                 " op.name \"маршрут\"," +
                 " oc.name \"предмет\"," +
                 " oh.name \"тема\"," +
                 " if(h.done,'СДАНО','?') \"статус\"";
         switch(pp.iLang){
             case 1:s2=" select " +
                 " os.name \"name \"," +
                 " op.name \"route\"," +
                 " oc.name \"course\"," +
                 " oh.name \"subject\"," +
                 " if(h.done,'DONE','?') \"status\"";
         }
         String s3=   
                 
                 " from chapter h, " +
            " object os,object op,object oc,object oh" +
                 " where oh.parent=oc.id and oc.parent=op.id and op.parent=os.id and op.id=" +id+     
            " and h.id=oh.id " +

            " order by op.code,oc.code,oh.code ";
         return s2+s3;
    }
    /** Creates a new instance of Portfolio */
    public Portfolio() {
    }
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        map.put("81","Отчет полный");
        map.put("82","Все тесты");      
        mapE.put("81","Report complete");
        mapE.put("82","All tests");          
    }
    
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 81:return "Отчет полный";
                case 82:return "Все тесты <br>Можно задать порог выставления оценки. Например:<br>" +
                    " 908065 означает что 5 за 90% и выше, 4 за 80% и выше, 3 за 65% и выше." +
                    " 8070 означает, что 5 за 80% и выше, 4 за 70% и выше. Остальные оценки по умолчанию." + 
                            "<br>По умолчанию: 5 за 95%, 4 за 80%, 3 за 60%, 2 за 30%, 1 за 10%";            
            }
            case 1:switch(k){
                case 81:return "Report complete";
                case 82:return "Tests" +
                " <br>Thresholds as follows:<br>" +
                " 908065 means 5 for 90% and more, 4 for 80% to 90%, 3 for 65% to 80%." +
                " 8070 means 5 for 80% and more, 4 for 70% to 80%, rest by default." + 
                        "<br>Default: 5 for 95% and more, " +
                        "4 for 80% to 95%, 3 for 60% to 80%, 2 for 30% to 60%, 1 for 10% to 30%";
            }        
        }
        return "";
    }
    
    String q2(){return " select oh.id from " +
            " object oc,object oh where " +
            " oh.parent=oc.id " +
                 " and oc.parent=" +id+
            " order by oc.code,oh.code ";
    }
    
    public String result(String fun,String par) throws Exception {
       if(!fun.equals("81")&&!fun.equals("82"))return ""; 
       if(fun.equals("82")){
           String s = "";
           List<Integer> II = pp.getNN(q2()); 
           for(Integer I:II){
                Testfolder TF = new Testfolder();
                TF.init(pp,I);
                s+=TF.result("11", par);
           }
        return s;
       } 
        
        QuickReport qr = new QuickReport(pp);
        int nn = 3;
        return  qr.packNcut(q(id),nn);
    }
}
