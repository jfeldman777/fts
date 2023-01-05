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
public abstract class MyTestfolder extends Proc implements IProc{ 
    /** Creates a new instance of Base */
    List<SumLine> sumlineII = null;
    
    final static String TD = "</td><td>";
    Map<String, SumTypeTable> tableSet = new HashMap<String,SumTypeTable>(1);
    public void init(XPump pp, int id)throws Exception{
        super.init(pp,id);

        bForce.add("30");
        bForce.add("90");
//        bForce.add("111");        
        map.put("10","имена по алфавиту");
        map.put("11", "коды по алфавиту");         
        map.put("20","от лучших к худшим");
        map.put("30","запереть бланки на замок");
        map.put("41", "трудные вопросы, ученики");
        map.put("42", "ученики, трудные вопросы");
        map.put("43", "вопросы, успехи, неудачи, проценты");
        map.put("44", "уровень знаний");
        map.put("50", "один ученик");
        map.put("90", "обнулить бланки");       
        map.put("111", "одной таблицей");    
        
        mapE.put("10","order by name");
        mapE.put("11", "order by code");         
        mapE.put("20","order by success");
        mapE.put("30","lock pages");
        mapE.put("41", "question-faults, who did it wrong");
        mapE.put("42", "students, errors");
        mapE.put("43", "questions, success, errors, %% ");
        mapE.put("44", "level");
        mapE.put("50", "one student");
        mapE.put("90", "initialize pages");  
        mapE.put("111", "one table report");    
        
        mapF.put("10","noms en ordre alphabetique");
        mapF.put("11", "codes en ordre alphabetique");         
        mapF.put("20","en ordre des r&eacute;sultats");
        mapF.put("30","fermer &agrave; cl&eacute;");
        mapF.put("41", "fautes, &eacute;l&egrave;ves");
        mapF.put("42", "&eacute;l&egrave;ves, fautes");
        mapF.put("43", "questions, correct, incorrect, %% ");
        mapF.put("44", "niveau de connaissances");
        mapF.put("50", "un &eacute;l&egrave;ve");
        mapF.put("90", "annuler les donn&eacute;es");          
        mapF.put("111", "une table compte ");    
                
    }

    int sortOrder = 1;
    private float f0=10;
    private float f1=30;
    private float f2=60;
    private float f3=80;
    private float f4=95;
    public String parDesc(int id,int k){
        switch(pp.iLang){
            case 0:switch(k){
                case 10:return "Результаты: алфавитный порядок фамилий." + 
                        " <br>Можно задать порог выставления оценки. Например:<br>" +
                " 908065 означает что 5 за 90% и выше, 4 за 80% и выше, 3 за 65% и выше." +
                " 8070 означает, что 5 за 80% и выше, 4 за 70% и выше. Остальные оценки по умолчанию." + 
                        "<br>По умолчанию: 5 за 95%, 4 за 80%, 3 за 60%, 2 за 30%, 1 за 10%" +
                        "<br>-xxx означает исключить из анализа тип с номером xxx,возможно -xxx-yyy-zzz...";///+languages!
                case 20:return "Результаты: от лучших к худшим." + 
                        " <br>Можно задать порог выставления оценки. Например:<br>" +
                " 908065 означает что 5 за 90% и выше, 4 за 80% и выше, 3 за 65% и выше." +
                " 8070 означает, что 5 за 80% и выше, 4 за 70% и выше. Остальные оценки по умолчанию." + 
                        "<br>По умолчанию: 5 за 95%, 4 за 80%, 3 за 60%, 2 за 30%, 1 за 10%" +
                    "<br>-xxx означает исключить из анализа тип с номером xxx,возможно -xxx-yyy-zzz...";
                case 30:return "Запереть бланки на замок!!! защита от несанкционированного доступа";
                case 41:return "Ошибки, начиная с самых частых, и кто их сделал. Если хотите включить в подсчет чистые бланки," +
                " поставьте 1 в поле параметров ";  
                case 42:return "Ученики по алфавиту и их ошибки. "+
                        " <br> Если хотите включить в подсчет чистые бланки," +
                " поставьте 1 в поле параметров ";  
                case 43:return "Номера вопросов, число успехов, число неудач,%"+
                        " <br> Если хотите включить всех," +
                " поставьте 1 в поле параметров ";
                case 44:return "Качество знаний, обученность и пр."; 
                case 50:return "Проценты и ошибки для одного. Укажите его ID"; 
                case 11:return "Результаты: алфавитный порядок кодов." +
                        " <br>Можно задать порог выставления оценки. Например:<br>" +
                " 908065 означает что 5 за 90% и выше, 4 за 80% и выше, 3 за 65% и выше." +
                " 8070 означает, что 5 за 80% и выше, 4 за 70% и выше. Остальные оценки по умолчанию." + 
                        "<br>По умолчанию: 5 за 95%, 4 за 80%, 3 за 60%, 2 за 30%, 1 за 10%" +
                        "<br>-xxx означает исключить из анализа тип с номером xxx,возможно -xxx-yyy-zzz...(только классический вариант)";                        
                case 90:return "Обнулить бланки, включая ключи, поля, даты изменения";
                case 111:return "все ответы (верно-неверно) одной таблицей";
            }
            case 1:switch(k){
                case 10:return "Order by names:" + 
                        " <br>Thresholds as follows:<br>" +
                " 908065 means 5 for 90% and more, 4 for 80% to 90%, 3 for 65% to 80%." +
                " 8070 means 5 for 80% and more, 4 for 70% to 80%, rest by default." + 
                        "<br>Default: 5 for 95% and more, 4 for 80% to 95%, 3 for 60% to 80%, 2 for 30% to 60%, 1 for 10% to 30%" +
                    "<br>-xxx-yyy-zzz excludes types xxx,yyy,zzz";
                case 20:return "Order by success." + 
                        " <br>Thresholds as follows:<br>" +
                " 908065 means 5 for 90% and more, 4 for 80% to 90%, 3 for 65% to 80%." +
                " 8070 means 5 for 80% and more, 4 for 70% to 80%, rest by default." + 
                        "<br>Default: 5 for 95% and more, 4 for 80% to 95%, 3 for 60% to 80%, 2 for 30% to 60%, 1 for 10% to 30%" +
                    "<br>-xxx-yyy-zzz excludes types xxx,yyy,zzz";
                case 30:return "Lock pages from unathorizated access";
                case 41:return "Questions-faults and who did it wrong." +
                        " <br> To include empty pages put 1 (digit one) as a parameter ";
                case 42:return "Order by names, list of errors"+
                        " <br> To include empty pages put 1 (digit one) as a parameter ";
                case 43:return "Questions, success, errors, errors%"+
                        " <br> To include empty pages put 1 (digit one) as a parameter ";
                case 44:return "Level of the group"; 
                case 50:return "About one student. Put his/her ID as a parameter"; 
                case 11:return "Order by codes." +
                            " <br>Thresholds as follows:<br>" +
                " 908065 means 5 for 90% and more, 4 for 80% to 90%, 3 for 65% to 80%." +
                " 8070 means 5 for 80% and more, 4 for 70% to 80%, rest by default." + 
                        "<br>Default: 5 for 95% and more, 4 for 80% to 95%, 3 for 60% to 80%, 2 for 30% to 60%, 1 for 10% to 30%" +
                    "<br>-xxx-yyy-zzz excludes types xxx,yyy,zzz (classics only)";
                case 90:return "annuler les donn&eacute;es";
                case 111:return "one table report";                
            }      
            
            case 2:switch(k){
                case 10:return "noms en ordre alphabetique:" + 
                            " <br>908065-xxx-yyy..." ;
                case 20:return "en ordre des r&eacute;sultats" + 
                            " <br>908065-xxx-yyy..." ;
                case 30:return "fermer &agrave; cl&eacute;";
                case 41:return "fautes, &eacute;l&egrave;ves";
                case 42:return "&eacute;l&egrave;ves, fautes";
                case 43:return "questions, correct, incorrect, %% ";
                case 44:return "niveau de connaissances"; 
                case 50:return "un &eacute;l&egrave;ve (ID)"; 
                case 11:return "codes en ordre alphabetique" +
                            " <br>908065-xxx-yyy...(par classic)" ;
                case 90:return "initialize pages, including keys, fields, hidden dates";
                case 111:return "une table report";                   
            }            
        }
        return "";
    }    
    public MyTestfolder(){}
    class EstBox{//44
        int e5=0;
        int e4=0;
        int e3=0;
        int e2=0;
        int e1=0;
        int e0=0;
        int eN=0;
        int f1(){return procent(100*e5+64*e4+36*e3+16*(e2+e1+e0),100*eN);}
        int f2(){return procent(e5+e4,eN);}
        int f3(){return procent(e5+e4+e3,eN);}
        void count(int e){
            eN++;
            switch(e){
                case 5:e5++;return;
                case 4:e4++;return;
                case 3:e3++;return;
                case 2:e2++;return;
                case 1:e1++;return;
                case 0:e0++;return;
            }
        }
    }
    String f10_11_20_44(boolean uob,String notype)throws Exception{
        EstBox eBox = new EstBox();
        String htm =
"<tr><td>код"+TD+"имя"+TD+"Оценка"+TD+"из всех"+TD+"из выполненных"+TD+"всего" +TD+"сделано" +TD+"правильно" +
                "</td></tr>"; 
        switch(pp.iLang){
            case 1:htm = "<tr><td>code"+TD+"name"+TD+"score"+TD+"from all"+TD+
                    "from done"+TD+"all" +TD+"done" +TD+"correct" +
                "</td></tr>"; 
        }
        
//        List<SumLine> ar = getArray1(id,notype);
//        ar.addAll(getArray2(id,notype));
        List<SumLine> ar = sumlineII;
        for(SumLine b:ar){
            b.setNumbers();
        }
        Object[] bbb = ar.toArray();

        Arrays.sort(bbb);
        P2Node p = P2Node.getP2Node(pp,FTree.ObjectTree,id);
        SumLine bSum = new SumLine(-1,"",p.codeName(),  0, 0, 0, "");
        for(int i=0;i<bbb.length;i++){
            SumLine b = (SumLine)bbb[i];
            int est = pro2est(b.fpN);
            if(b.nbase>0)eBox.count(est);
            htm+="<tr><td>"+b.code+TD+b.name+TD+est+TD+b.fpN+"%"+TD+b.fpn+
            "%" +TD+b.Nbase+TD+b.nbase+TD+b.pbase+
            "</td></tr>";
            bSum.nbase+=b.nbase;
            bSum.Nbase+=b.Nbase;
            bSum.pbase+=b.pbase;
        }
        setFloat(bSum);
        
        htm+="<tr><td>";
        switch(pp.iLang){
            case 1:htm+="Total:";break;            
            default:htm+="Итого:";break;
        }

        htm+=TD+bSum.name+TD+pro2est(bSum.fpN)+TD+bSum.fpN+"%"+TD+bSum.fpn+
            "%" +TD+bSum.Nbase+TD+bSum.nbase+TD+bSum.pbase+
            "</td></tr>";
            
        return uob?f44(eBox):"<table border=2>"+htm+"</table>";
    }    
    

    
    public String result(String fun,String par) throws Exception {
        sortOrder = Integer.parseInt(fun);
        switch(sortOrder){
            case 41:return f41_42(par,true);
            case 42:return f41_42(par,false);  
            case 43:return f43_111(par, false);  
            case 44:break;
            case 30:return randomizeCodes(); 
            case 50:return resOne(par);
            case 90:return initBlanks(); 
            case 11: 
            case 10:
            case 20:break;
            case 111:return f43_111(par, true);

            default: return "";
        }

        String notype = "";
        String par2 = par.trim();
        int k = par2.indexOf("-");
        String par31 = par2; if(k>=0)par31=par2.substring(0,k);
        String par32 = "";
        if(k>=0){
            par32=par2.substring(k+1);
            notype = par32.replace('-',',');
        }
        String parx = par31.trim();
        int L = parx.length();
        try{
        if(L>=2)f4=Integer.parseInt(parx.substring(0,2));
        if(L>=4)f3=Integer.parseInt(parx.substring(2,4));
        if(L>=6)f2=Integer.parseInt(parx.substring(4,6));
        if(L>=8)f1=Integer.parseInt(parx.substring(6,8));
        if(L>=10)f0=Integer.parseInt(parx.substring(8,10));
        }catch(Exception e){
            throw new Exception("//L="+L+"//");
        }
        return f10_11_20_44(sortOrder==44,notype);
    }
    String f44(EstBox b){
        String s1= "<table border=2>" +
                "<tr align=right><td>Работ:</td><td>" +b.eN+"</td></tr>" +                
                "<tr align=right><td>5:</td><td>" +b.e5+"</td></tr>" +
                "<tr align=right><td>4:</td><td>" +b.e4+"</td></tr>" +
                "<tr align=right><td>3:</td><td>" +b.e3+"</td></tr>" +
                "<tr align=right><td>2:</td><td>" +b.e2+"</td></tr>" + 
                "<tr align=right><td>1:</td><td>" +b.e1+"</td></tr>" +
                "<tr align=right><td>0:</td><td>" +b.e0+"</td></tr>" + 
                "<tr align=right><td>n5*100 + n4*64 + n3*36 + n012*16 / 100*n =";
                
        String s2 = "<br>Средний уровень обученности:</td><td>" +b.f1()+"</td></tr>" + 
                "<tr align=right><td>Качество знаний:</td><td>" +b.f2()+"</td></tr>" +
                "<tr align=right><td>Успеваемость:</td><td>" +b.f3()+"</td></tr>";
        switch(pp.iLang){
            case 1:s2 = "<br>Average level:</td><td>" +b.f1()+"</td></tr>" + 
                "<tr align=right><td>Quality:</td><td>" +b.f2()+"</td></tr>" +
                "<tr align=right><td>Success:</td><td>" +b.f3()+"</td></tr>";
        }        
        String s3 = "</table>";
        
        return s1+s2+s3;
    }
       

    int pro2est(float f){
        if(f<=f0)return 0;
        if(f<f1)return 1;
        if(f<f2)return 2;
        if(f<f3)return 3;
        if(f<f4)return 4;
        return 5;
    }
    abstract String f41_42(String par,boolean is41)throws Exception;
    
    /*
    String f41_42(String par,boolean is41)throws Exception{
        String s = "";
        boolean includeEmptyPages = par.equals("1");
        List<String> typecodes = getTypes(id);
        for(String name:typecodes){
            String qz = "select * from "+name+" where id=0";
            String q1 = "select o.name student,t.* from "+name+" t,object o where " +
                    " o.id>0 " +
                    (includeEmptyPages?"":" and o.creation_date<>o.last_update ")+
                    " and o.id2=0 and o.id=t.id and o.parent="+id;
            String q2 = "select o2.name student,t.* from "+name+" t,object o,object o2 where " +
                    " o.id2>0 " +
                    (includeEmptyPages?"":" and o2.creation_date<>o2.last_update ")+
                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;

            SortedMap<String,String> treemap = new TreeMap<String,String>();
            setBase(treemap,qz);
            noBaseKeys2null(treemap,name);
            List<PersonalSumCell> far = new ArrayList<PersonalSumCell>();
            
            setPersonalList43(treemap, far, name, q1);
            setPersonalList43(treemap, far, name, q2);
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
    */
    SortedMap<String,String> tcNames = new TreeMap<String,String>();
    
String f41(List<PersonalSumCell> far,String tabname)throws Exception{
        SortedMap<String,String> stNames = new TreeMap<String,String>();
        SortedMap<String,Integer> bugs = new TreeMap<String,Integer>();
        for(PersonalSumCell f:far){
            String tabcol = f.typecode+"::"+f.colname;
            String y = stNames.get(tabcol);
            if(y==null){
                stNames.put(tabcol,f.studentName);
                bugs.put(tabcol,1);
            }else{
                stNames.put(tabcol,y +","+ f.studentName);
                bugs.put(tabcol,bugs.get(tabcol)+1);
            }
        }
        List<BugSumCell> err = new ArrayList<BugSumCell>();
        for(String k:stNames.keySet()){
                Integer N = bugs.get(k);
                err.add(new BugSumCell(N, k, stNames.get(k)));
        }
        
        Object[] bbb = err.toArray();

        Arrays.sort(bbb);
        
        String s = "<p>тип теста: " + tabname +
                "</p><table border=2><tr><td>ошибок" +TD+"вопрос"+TD+
                "кто ошибся</td></tr>";
        switch(pp.iLang){
            case 1:s = "<p>test type: " + tabname +
                "</p><table border=2><tr><td>errors" +TD+"question"+TD+
                "who did it wrong</td></tr>";
        }
        
        for(int i=0;i<bbb.length;i++){
            BugSumCell z = (BugSumCell)bbb[i];
            s+="<tr><td>"+z.n+TD+head(TabCol.getRus(pp,z.key))+TD+z.name+"</td></tr>";
        }
        return s+"</table>";
    }    

abstract String f43_111(String par, boolean is111)throws Exception;

/*
String f43_111(String par, boolean is111)throws Exception{
        String s = "";
        boolean b1 = par.equals("1");
        List<String> typecodes = getTypes(id);
        for(String name:typecodes){
            String qz = "select * from "+name+" where id=0";
            String q1 = "select o.name student,t.* from "+name+" t,object o where " +
                    " o.id>0 " +
                    (b1?"":" and o.creation_date<>o.last_update ")+
                    " and o.id2=0 and o.id=t.id and o.parent="+id;
            String q2 = "select o2.name student,t.* from "+name+" t,object o,object o2 where " +
                    " o.id2>0 " +
                    (b1?"":" and o2.creation_date<>o2.last_update ")+
                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;
            String q1n = "select count(*) from "+name+" t,object o where " +
                    " o.id>0 " +
                    (b1?"":" and o.creation_date<>o.last_update ")+
                    " and o.id2=0 and o.id=t.id and o.parent="+id;
            String q2n = "select count(*) from "+name+" t,object o,object o2 where " +
                    " o.id2>0 " +
                    (b1?"":" and o2.creation_date<>o2.last_update ")+
                    " and o2.id=o.id2 and o2.id=t.id and o.parent="+id;
            SortedMap<String,String> treemap = new TreeMap<String,String>();
            setBase(treemap,qz);
            noBaseKeys2null(treemap,name);


            if(is111){
                List<PersonalNameCell> far2 = new ArrayList<PersonalNameCell>();
                List<String> cols = new ArrayList <String>();
                setPersonalList111(treemap, far2, name, q1,cols);
                setPersonalList111(treemap, far2, name, q2,null);       
                s+="<p>test = "+name+f111(far2,cols);
            }
            else{
                List<PersonalSumCell> far = new ArrayList<PersonalSumCell>();                
                setPersonalList43(treemap, far, name, q1);
                setPersonalList43(treemap, far, name, q2);                
                int N1 = pp.getN(q1n);
                int N2 = pp.getN(q2n);
                int Nst = N1+N2;
                switch(pp.iLang){
                    case 0:s+="<p>В испытаниях участвовало: "+Nst+" человек <br>" ;break;
                    case 1:s+="<p>Number of students: "+Nst+"<br>" ;break;
                }
                s+=f43tab(far,name,Nst);
            }
        }
        return s;
    } 
*/
    String f43tab(List<PersonalSumCell> far,String tabname,int Nst)throws Exception{
        SortedMap<String,Integer> bugs = new TreeMap<String,Integer>();
        for(PersonalSumCell f:far){
            String tabcol = f.typecode+"::"+f.colname;
            Integer y = bugs.get(tabcol);
            if(y==null){
                bugs.put(tabcol,1);
            }else{
                bugs.put(tabcol,bugs.get(tabcol)+1);
            }
        }
        List<NofKeyErr> err = new ArrayList<NofKeyErr>();
        for(String k:bugs.keySet()){
                Integer N = bugs.get(k);
                err.add(new NofKeyErr(headNumInt(TabCol.getRus(pp,k)), k, N));
        }
        
        Object[] bbb = err.toArray();

        Arrays.sort(bbb);
        
        String s = "тип теста: " + tabname +
                "<table border=2><tr><td>номер вопроса"+TD+
                "верных ответов" +TD+
                "ошибочных ответов" +TD+
                "процент ошибок</td></tr>";
        switch(pp.iLang){
            case 1:s = "test type: " + tabname +
                "<table border=2><tr><td>#question"+TD+
                "correct" +TD+
                "errors" +TD+
                "errors %</td></tr>";
        }
        
        int sumAll=0;
        int sumNo=0;
        int sumYes=0;
        for(int i=0;i<bbb.length;i++){
            NofKeyErr z = (NofKeyErr)bbb[i];
            s+="<tr align=right><td>"+
                    z.n+TD+
                    (Nst-z.err)+TD+
                    z.err+TD+procent(z.err, Nst)+
                    "%</td></tr>";
            sumYes+=Nst-z.err;
            sumNo+=z.err;
            sumAll+=Nst;
        }
        s+="<tr align=right><td>";
        switch(pp.iLang){
            case 0:s+="Итого";break;
            case 1:s+="Total";break;
        }
        s+=TD+sumYes+TD+
                    sumNo+TD+procent(sumNo, sumAll)+
                    "%</td></tr>";
        return s+"</table>";
    }
    public void setTcNames(String typecode)throws Exception{
        String q = "select tabcol,rus from translator where tabcol LIKE '"+typecode+"::%'";
        Result r=new Result();
        try{    
            pp.select(r,40,q);
            while(r.rs.next()){
                String tabcol = r.rs.getString("tabcol");
                String rus = r.rs.getString("rus");
                tcNames.put(tabcol, rus);
            }
        }catch(Exception e){
        }finally{r.close();}
    }
    public void setPersonalList43(SortedMap<String,String> treemap, List<PersonalSumCell> far, 
            String typecode,String q)throws Exception{
//        String q = "select * from "+name+" where id="+id;
        Result r=new Result();
        try{    
            pp.select(r,41,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            while(r.rs.next()){
            String student=null;
                for(int i=0;i<N;i++){
                    String x = r.rs.getString(i+1); 
                    String colname = md.getColumnName(i+1); 
                    String y = treemap.get(colname);
                    if(colname.equals("id")){}else
                        if(colname.equals("student")){student=x;}else
                            if(y!=null && !y.equals(x))far.add(new PersonalSumCell(typecode,colname,student)); 
                }                
            }
        }catch(Exception e){
        }finally{r.close();}
    }
    String f111(List<PersonalNameCell> far,List<String> cols)throws Exception{
        String s="<table border=1><tr><td>#</td>";
        for(String col:cols){
            s+="<td>"+col+"</td>";
        }
        s+="";
        boolean start=true;
        int N = 0;
        for(PersonalNameCell cell:far){
            if(cell.ind==0){              
                if(start){   
                    start=false;
                }else s+="</tr>";
                s+="<tr><td>" +(++N)+ "</td><td>" +cell.studentName+ "</td>";                 
            }
            String val = "0";
            if(cell.val>0)val="+";
            if(cell.val<0)val="-";
            s+="<td>"+val+"</td>";
        }
        s+="</tr></table>";
        return s;
    } 
    public void setPersonalList111(SortedMap<String,String> treemap, List<PersonalNameCell> far, 
            String typecode,String q,List<String> cols)throws Exception{
//        String q = "select * from "+name+" where id="+id;
        Result r=new Result();
        try{    
            pp.select(r,41,q);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            for(int i=0;i<N;i++){
                 String colname = md.getColumnName(i+1); 
                 if(cols!=null&&!colname.equals("id"))cols.add(colname);                 
            }
            while(r.rs.next()){
            String student=null;
                int ind=0;            
                for(int i=0;i<N;i++){
                    String x = r.rs.getString(i+1); 
                    String colname = md.getColumnName(i+1); 
                    String y = treemap.get(colname);
                    if(colname.equals("id")){
                    }else if(//i==0 //
                            colname.equals("student")){student=x;
                          }else{
                            int val=0;
                            if(y!=null){
                                if(!x.equals("0")) val=y.equals(x)?1:-1;
                            }

                            far.add(new PersonalNameCell(typecode,colname,student,val,ind++)); 
                          }
                }                
            }
        }catch(Exception e){
        }finally{r.close();}
    }    
    public String resOne(String par)throws Exception{
        String htm =
"<tr><td>код"+TD+"из всех"+TD+"из выполненных"+TD+"всего" +TD+"сделано" +TD+"правильно" +
                "</td></tr>"; 
        
        switch(pp.iLang){
            case 1:htm =
"<tr><td>code"+TD+"from all"+TD+"from done"+TD+"all" +TD+"done" +TD+"correct" +
                "</td></tr>"; 
        }
        
        int ID = pp.getN("select if(id2>0,id2,id) from object where id="+par);
        if(ID<0){
            String q = "select if(id2>0,id2,id) from object where parent="+id;
            //if(true)throw new Exception("<p>"+q+"<p>");
            ID = pp.getN(q);
        }
        String tabname = pp.getS("select t.code from object o, type t where o.type=t.id and o.id="+ID);
        SumTypeTable t = new SumTypeTable(pp,tabname);
        SortedMap<String,String> mapBase = new TreeMap<String,String>();
        SortedMap<String,String> mapVal = new TreeMap<String,String>();
        setBase(mapBase,"select * from "+tabname+" where id=0");
        setBase(mapVal,"select * from "+tabname+" where id="+ID);
        SumLine b = new SumLine(ID, tabname);
        b.setNumbers(t);
        htm+="<tr><td>"+b.code+TD+b.fpN+"%"+TD+b.fpn+"%" +TD+b.Nbase+TD+b.nbase+TD+b.pbase+
            "</td></tr>";
        htm+= "</table><br>Ошибки:<table border=2><tr><td>вопрос</td></tr>";
        for(String k:mapVal.keySet()){
            String val = mapVal.get(k);
            String base = mapBase.get(k);
            if(!k.equals("id") && base!=null && !base.equals(val)){
                htm+="<tr><td>"+head(TabCol.getRus(pp,tabname+"::"+k))+"</td></tr>";
            }
        }
        return "<table border=2>"+htm+"</table>name = " +
                pp.getS("select name from object where id="+ID)+
                "<hr>";            
    } 
    List<String> getTypes(int parent)throws Exception{
        String q = "select distinct t.code from object o, type t " +
                " where o.id2=0 and t.id=o.type and o.parent="+parent+" order by type ";
        String q2 = "select distinct t.code from object o2,object o, type t " +
                " where o.id2>0 and o.id2=o2.id and t.id=o2.type and o.parent="+parent+" order by type ";
        List<String> ar = pp.getSS(q);
        ar.addAll(pp.getSS(q2));
        return ar;
    }
    List<SumLine> getArray(String q)throws Exception{
        List<SumLine> ar = new ArrayList<SumLine>();
        Result r=new Result();
        String code="",name="",typecode="";
        int id;
        //try{
            pp.select(r,42,q);
            while(r.rs.next()){
                id = r.rs.getInt("id");
                code = r.rs.getString("code");
                name = r.rs.getString("name");
                typecode = r.rs.getString("typecode");
                ar.add(new SumLine(id,code,name,0,0,0,typecode));
            }       
        //}finally{r.close();}
        return ar; 
    }
    
    String andNotIn(String s)throws Exception{
//        if(true)throw new Exception(s);
        if(s.equals(""))return " ";
        return " and o.type not in ("+s+")";
    }
    
    List<SumLine> getArray1(int parent,String notype)throws Exception{
        String q = "select o.id,o.code,o.name,t.code typecode from type t,object o where o.type=t.id " +
                " and o.id2=0 and o.parent ="+parent+
                andNotIn(notype)+
        " order by o.code ";
        return getArray(q);
    }  
    List<SumLine> getArray2(int parent,String notype)throws Exception{
        String q = "select o.id,o.code,o.name,t.code typecode from type t,"+
        //Substitution.fromNwhere(true,parent,"o","o2")        
        " object o,object o2 where o2.id2=o.id and o2.id2>0 and o2.parent ="+parent+
                " and o.type=t.id " +
                andNotIn(notype)+
        " order by o.code ";
        return getArray(q);
    }  
    
    class PersonalNameCell extends PersonalSumCell{
        int val=0;
        int ind=0;
        PersonalNameCell(String typecode,String colname,String studentName,int val,int ind){
            super(typecode,colname,studentName);
            this.val = val;
            this.ind = ind;
        }
        
        public int compareTo(Object o) {
            int b1 = ((PersonalNameCell) o).typecode.compareTo(typecode);            
            int b3 = ((PersonalNameCell) o).colname.compareTo(colname);          
            int b2 = ((PersonalNameCell) o).studentName.compareTo(studentName);
            if(b1!=0)return b1;
            if(b2!=0)return b2;
            return b3;
        } 
    }
    
    class PersonalSumCell implements Comparable{
    String typecode;
    String colname;
    String studentName;
    String rus;
    PersonalSumCell(String typecode,String colname,String studentName){
        this.typecode=typecode;
        this.colname=colname;
        this.studentName=studentName;
    }
    
    public int compareTo(Object o) {
            return ((PersonalSumCell) o).studentName.compareTo(studentName);
    }     
}
    class BugSumCell implements Comparable{
        BugSumCell(int n,String key,String name){
            this.n = n;
            this.key = key;
            this.name = name;
        }
        int n;
        String key;
        String name;
        
        public int compareTo(Object o) {
            return ((BugSumCell) o).n - n;
        }        
    }

    class NofKeyErr implements Comparable{
        NofKeyErr(int n,String key,int err){
            this.n = n;
            this.key = key;
            this.err = err;
        }
        int n;
        String key;
        int err;
        
        public int compareTo(Object o) {
            return n-((NofKeyErr)o).n;
        }        
    }
    
    class SumTypeTable extends Table{//41,42,43,44
        String qNoTxtVal;
        String qCompareKeys;
        String qNoDataKeys;
        int nErrKeys;
        SumTypeTable(XPump pp,String typecode)throws Exception{
            super(pp,typecode,false);
            nErrKeys = pp.getN(qErrKeys());
            qCompareKeys = qCompareKeys();
            qNoTxtVal = qNoTxtVal();
            qNoDataKeys = qNoDataKeys();
        }
        String qErrKeys()throws Exception{
            String q = "select 0 ";
            for(ITabCol k:fields){
                if(k.isKey()){q+="+if(t."+k.name()+"=0,1,0)";}
            }
            q+=" from "+name+" t where t.id=0";
            return q;
        }  
        String qCompareKeys()throws Exception{
            String q = "select 0 ";
            for(ITabCol k:fields){
                if(k.isKey()){
                    q+="+if(t1."+k.name()+"=t2."+k.name()+
                    " and t2."+k.name()+">0,1,0)";
                }else{
                    q+="+if(t1."+k.name()+"=t2."+k.name()+",1,0)";
                }
            }        
            q+=" from "+name+" t1, "+name+" t2 where t2.id=0 and t1.id=";//+id;

            return q;
        }   
        String qNoTxtVal()throws Exception{
            String q = "select 0 ";
            for(ITabCol k:fields){
                if(!k.isKey()){q+="+if(t."+k.name()+" is NULL,1,0)";}
            }        
            q+=" from "+name+" t where t.id=";//+id;
            return q;
        }  
        String qNoDataKeys()throws Exception{
            String q = "select 0 ";
            for(ITabCol k:fields){
                if(k.isKey()){q+="+if(t."+k.name()+"=0,1,0)";}
            }
            q+=" from "+name+" t where t.id=";//+id;
            return q;
        }        
    } 
    class SumLine implements Comparable{ 
    /** Creates a new instance of TfBox */
    public SumLine() {//10,11,2
    }
        int id;
        String code;
        String name;
        int nbase;
        int pbase;
        int Nbase;
        float fpn;
        float fpN;
        String typecode;
        SumLine(int id,String code,String name,int nbase,int pbase,int Nbase,String typecode){
            this.id = id;
            this.code = code;
            this.name = name;
            this.nbase = nbase;
            this.pbase = pbase;
            this.Nbase = Nbase;
            this.typecode = typecode;
        }       
        SumLine(int id,String typecode){
            this.id = id;
            this.code = ""+id;
            this.name = ""+id;
            this.typecode = typecode;  
        }        
        SumLine(int id,float fn,float fN){
            this.id = id;
            fpN = fN;
            fpn = fn;
        }     
        public int compareTo(Object o) {
            if(sortOrder==11) return this.name.compareToIgnoreCase(((SumLine) o).name);
            if(sortOrder==10) return this.code.compareToIgnoreCase(((SumLine) o).code);
            int N = Math.round((((SumLine) o).fpN - this.fpN)*1000);
            //if(sortOrder==2) 
                return N;
        }
    void setNumbers()throws Exception{
        SumTypeTable t = tableSet.get(typecode);
        if(t==null){
            t=new SumTypeTable(pp,typecode);
            tableSet.put(typecode,t);
        }
        setNumbers(t);
    }
    void setNumbers(SumTypeTable t)throws Exception{
        pbase = pp.getN(t.qCompareKeys + id); 
        int noDataKey = pp.getN(t.qNoDataKeys + id);
        int noTxt = pp.getN(t.qNoTxtVal + id);
        Nbase = t.fields.size()-t.nErrKeys - 1; ////////////////////
        nbase = Nbase-noDataKey + t.nErrKeys - noTxt;          
        setFloat(this);
    }
    }
    void setFloat(SumLine b)throws Exception{
        float fpNx=0;
        float fpnx=0;
        try{
            fpNx = b.pbase*100/b.Nbase;
        }catch(Exception e){fpNx=0;}
       if(fpNx>0)
        try{
            fpnx = b.pbase*100/b.nbase;
        }catch(Exception e){fpnx=0;}  
        
        b.fpn = fpnx;
        b.fpN = fpNx;
    }
    void setBase(SortedMap<String,String> treemap, String qz)throws Exception{
        Result r=new Result();
        try{    
            pp.select(r,43,qz);
            ResultSetMetaData md = r.rs.getMetaData();
            int N = md.getColumnCount();
            r.rs.next();
            for(int i=0;i<N;i++){
                String x = r.rs.getString(i+1); 
                String colname = md.getColumnName(i+1); 
                treemap.put(colname,x);
            }                    
        }catch(Exception e){
        }finally{r.close();}
    }
    void noBaseKeys2null(SortedMap<String,String> treemap,String tabname)throws Exception{
        String q =
                " select fcolumn from "+
                " sUSRFKeyInfo,sUSRForeignColumns where " +
                " id=fk_id and "+
                " name<>'id' and \"schema\"='"+pp.schema+
                "' and \"table\"='"+tabname+"' ";
        List<String> fcols = pp.getSSinfo(q);
        for(String key:fcols){
            String x = treemap.get(key);
            if(x.equals("0"))treemap.put(key,null);
        }
    }
    String head(String s){
        int i=s.indexOf("<ul>");
        if(i<0)return s;
        return s.substring(0,i);
    }
    String headNum(String s){
        int i=s.indexOf(".");
        if(i<0)return s;
        return s.substring(0,i);
    }
    
    int headNumInt(String s){
        String z="";
        int i=s.indexOf(".");
        z=(i<0)?s:s.substring(0,i);
        try{
            return Integer.parseInt(z);
        }catch(Exception e){
            return 0;
        }
    }
    
    String codeRand(String code){
        String num = ""+Math.random();
        String h = num.substring(2);
        return code+"+"+h;
    }
    String randomizeCodes()throws Exception{
        List<String> QQ = new ArrayList<String>();
        String htm = "<tr><td>id" +TD+"код"+TD+"имя</td></tr>";  
        switch(pp.iLang){
            case 1:htm = "<tr><td>id" +TD+"code"+TD+"name</td></tr>";  
        }
//        List<SumLine> ar = getArray1(id,"");
//        List<SumLine> ar = xII;
            for(SumLine b:sumlineII){
            b.code = codeRand(b.code);
            QQ.add("update object set code='"+b.code+"' where id="+b.id);
            htm+="<tr><td>"+b.id+TD+b.code+TD+b.name+"</td></tr>";
        }
        pp.exec(QQ);
       return "<table border=2>"+htm+"</table>"; 
    }
    
    int procent(int SN,int N){
        if(N==0)return 0;
        int k = SN*100/N;
        return k;
    }  
    ///////////////////////9
    String initBlanks()throws Exception{
//        List<SumLine> ar = getArray1(id,"");        
//        ar.addAll(getArray2(id,""));
//        List<SumLine> ar = xII;
        
        List<String> qq = new ArrayList<String>();
        for(SumLine b:sumlineII){
            Table t = new Table(pp, b.typecode,false);
            qq.add(t.qInit(b.id));
            Table o = new Table(pp,"object",false);
            qq.add(o.qInit(b.id));
        }
        pp.exec(qq);
        return "done";
    }    
}
