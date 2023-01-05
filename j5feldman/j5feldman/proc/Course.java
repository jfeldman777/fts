/*
 * Course.java
 * Author J.Feldman
 * Created on 7 ������ 2005 �., 7:52
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class Course extends Proc implements IProc{
    String q(int id){
         String s2=" select " +
                 " os.name \"�������\"," +
                 " op.name \"�������\"," +
                 " oc.name \"�������\"," +
                 " oh.name \"����\"," +
                 " if(h.done,'�����','?') \"������\"";
         switch(pp.iLang){
             case 1:s2=" select " +
                 " os.name \"name\"," +
                 " op.name \"route\"," +
                 " oc.name \"course\"," +
                 " oh.name \"subject\"," +
                 " if(h.done,'DONE','?') \"status\"";
         }
         String s3=
                 " from " +
            " object os,object op,object oc,object oh,chapter h where " +
            " h.id=oh.id and oh.parent=oc.id and oc.parent=op.id and op.parent=os.id " +
                 " and oc.id=" +id+
            " order by op.code,oc.code,oh.code ";
         return s2+s3;
    }
    /** Creates a new instance of Portfolio */
    public Course() {
    }
    public void init(XPump pp, int id)throws Exception{
    super.init(pp,id);     
        map.put("61","����� ������");
        map.put("62","��� �����");        
        
        mapE.put("61","Report complete");
        mapE.put("62","All tests");          
    }
    
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 61:return "����� ";
                case 62:return "�����: <br>����� ������ ����� ����������� ������. ��������:<br>" +
                    " 908065 �������� ��� 5 �� 90% � ����, 4 �� 80% � ����, 3 �� 65% � ����." +
                    " 8070 ��������, ��� 5 �� 80% � ����, 4 �� 70% � ����. ��������� ������ �� ���������." + 
                            "<br>�� ���������: 5 �� 95%, 4 �� 80%, 3 �� 60%, 2 �� 30%, 1 �� 10%";            
            }
            case 1:switch(k){
                case 61:return "Report ";
                case 62:return "Tests: " +
                " <br>Thresholds as follows:<br>" +
                " 908065 means 5 for 90% and more, 4 for 80% to 90%, 3 for 65% to 80%." +
                " 8070 means 5 for 80% and more, 4 for 70% to 80%, rest by default." + 
                        "<br>Default: 5 for 95% and more, " +
                        "4 for 80% to 95%, 3 for 60% to 80%, 2 for 30% to 60%, 1 for 10% to 30%";            }        
        }
        return "";
    }
    String q2(){return " select oh.id from " +
            " object oh where " +
            "  oh.parent=" +id+
            " order by oh.code ";
    }
    
    public String result(String fun,String par) throws Exception {
       if(!fun.equals("61")&&!fun.equals("62"))return ""; 
       if(fun.equals("62")){
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
