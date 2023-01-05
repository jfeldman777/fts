/*
 * Base.java
 *
 * Created on 18 Февраль 2004 г., 14:03
 */

package j5feldman.proc;
import j5feldman.*;
import j5feldman.proc.basement.Result;
import java.util.*;
import java.sql.*;
import j5feldman.proc.basement.Proc;
import j5feldman.proc.basement.IProc;
/**
 *
 * @author  Jacob Feldman/Фельдман Яков Адольфович
 */
public class Testfolder2 extends MyTestfolder implements IProc{ 
    /** Creates a new instance of Base */
    public Testfolder2(){}
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);
        String q = "select q.body from query q, testfolder2 t where t.query=q.id and t.id="+id;
        String q2 = pp.getS(q);
        try{
            sumlineII = getArray(q2);
        }catch(Exception e){
                System.out.println("q2:"+q2);
        }
    }
    
    SortedSet<String> myTypes()throws Exception{
        SortedSet set = new TreeSet<String>();
        for(MyTestfolder.SumLine s:sumlineII){
            set.add(s.typecode);
        }
        return set;
    }
    
    String f41_42(String par,boolean is41)throws Exception{
        String s = "";
        boolean includeEmptyPages = par.equals("1");
        SortedSet <String> typecodes = myTypes();
        for(String name:typecodes){
            String qz = "select * from "+name+" where id=0";
            
            String ss="-1";
            for(MyTestfolder.SumLine sum:sumlineII){
                ss+=","+sum.id;
            }
            
            String q1 = "select o.name student,t.* from "+name+" t,object o where " +
                    " o.id>0 " +
                    (includeEmptyPages?"":" and o.creation_date<>o.last_update ")+
                    " and o.id2=0 and o.id=t.id and " +
                    " o.id IN ("+ss+")"; 
                    //"o.parent="+id;
//            String q2 = "select o2.name student,t.* from "+name+" t,object o,object o2 where " +
//                    " o.id2>0 " +
//                    (includeEmptyPages?"":" and o2.creation_date<>o2.last_update ")+
//                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;

            
            
            
            SortedMap<String,String> treemap = new TreeMap<String,String>();
            setBase(treemap,qz);
            noBaseKeys2null(treemap,name);
            List<PersonalSumCell> far = new ArrayList<PersonalSumCell>();
            
            setPersonalList43(treemap, far, name, q1);///////////////////////////////////////////
//            setPersonalList43(treemap, far, name, q2);//////////////////////////////////////////
            if(is41)s+=f41(far,name);
            else {
                setTcNames(name);
                for(PersonalSumCell xx:far){
                    xx.rus = headNum(tcNames.get(xx.typecode+"::"+xx.colname))+";";
                }
                for(int i=far.size()-1;i>0;i--){
                    PersonalSumCell xi = far.get(i);
                    ii:for(int j=i-1;j>=0;j--){
                        PersonalSumCell xj = far.get(j);
                        if(xi.studentName.equals(xj.studentName)){
                            xj.rus+=xi.rus;
                            far.remove(xi);
                            break ii;
                        }
                    }
                }
                switch(pp.iLang){
                    case 0:s+= "<table border=2><tr><td>Ученик" +TD+"трудные вопросы</td></tr>";  
                        break;
                    case 1:s+= "<table border=2><tr><td>Student" +TD+"questions-faults</td></tr>";  
                        break;
                }
                Object[] zzz = far.toArray();
                for(int k=0;k<zzz.length;k++){
                    PersonalSumCell z = (PersonalSumCell)zzz[k];
                    s+="<tr><td>"+z.studentName+TD+z.rus+"</td></tr>";
                }
                s+="</table>";
            }
        }
        

        return s;
    }
    
String f43_111(String par, boolean is111)throws Exception{
        String s = "";
        boolean b1 = par.equals("1");
        SortedSet <String> typecodes = myTypes();
        for(String name:typecodes){
            String qz = "select * from "+name+" where id=0";
           
            String ss="-1";
            for(MyTestfolder.SumLine sum:sumlineII){
                ss+=","+sum.id;
            }
            
            String q1 = "select o.name student,t.* from "+name+" t,object o where " +
                    " o.id>0 " +
                    (b1?"":" and o.creation_date<>o.last_update ")+
                    " and o.id2=0 and o.id=t.id and " +
                    " o.id IN ("+ss+")"; 
            
//                    "o.parent="+id;
            
//            String q2 = "select o2.name student,t.* from "+name+" t,object o,object o2 where " +
//                    " o.id2>0 " +
//                    (b1?"":" and o2.creation_date<>o2.last_update ")+
//                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;
            
            
            
            
//            String q1n = "select count(*) from "+name+" t,object o where " +
//                    " o.id>0 " +
//                    (b1?"":" and o.creation_date<>o.last_update ")+
//                    " and o.id2=0 and o.id=t.id and " +
//                    " o.id IN ("+ss+")"; 
//                    //"o.parent="+id;
//            String q2n = "select count(*) from "+name+" t,object o,object o2 where " +
//                    " o.id2>0 " +
//                    (b1?"":" and o2.creation_date<>o2.last_update ")+
//                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;
            
            
            
            
            SortedMap<String,String> treemap = new TreeMap<String,String>();
            setBase(treemap,qz);
            noBaseKeys2null(treemap,name);

            if(is111){
                List<PersonalNameCell> far2 = new ArrayList<PersonalNameCell>();
                List<String> cols = new ArrayList <String>();
                
                setPersonalList111(treemap, far2, name, q1,cols);
//                setPersonalList111(treemap, far2, name, q2,null);    
                
                s+="<p>test = "+name+f111(far2,cols);
            }
            else{
                List<PersonalSumCell> far = new ArrayList<PersonalSumCell>();        
                
                setPersonalList43(treemap, far, name, q1);
//                setPersonalList43(treemap, far, name, q2);        
                
//                int N1 = pp.getN(q1n);
//                int N2 = pp.getN(q2n);
//                
//                int Nst = N1+N2;
                int Nst = sumlineII.size();
                
                switch(pp.iLang){
                    case 0:s+="<p>В испытаниях участвовало: "+Nst+" человек <br>" ;break;
                    case 1:s+="<p>Number of students: "+Nst+"<br>" ;break;
                }
                s+=f43tab(far,name,Nst);
            }
        }
        return s;
    } 
   
}
