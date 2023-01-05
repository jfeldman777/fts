/*
 * SubSystems.java
 *
 * Created on 11 Март 2005 г., 12:00
 */

package j5feldman.proc.basement;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public enum SubSystem {
    ProcessMan{public List<String> qq(){
        String[] QQ ={"insert into type (id,code)values(NEXTVAL('type_id'),'process')",
        "create table process( id int, author INTEGER default 0,seed INTEGER default 0," +
            " father INTEGER default 0," +
                " constraint pk_process primary key (id)," +                
                " constraint fk_process_author foreign key(author)references xuser," +
                " constraint fk_process_seed foreign key(seed)references process," +
                " constraint fk_process_father foreign key(father)references process, " +

            "  constraint fk_process foreign key(id)references object  on delete cascade)",
        "insert into process(id)values(0)",
        "insert into translator(tabcol,rus)values('process::author','автор')",
        "insert into translator(tabcol,rus)values('process::seed','исток')",
        "insert into translator(tabcol,rus)values('process::father','предпроцесс')"};
        return Arrays.asList(QQ);
    }},
    PrjMan{public List<String> qq(){
             String[] QQ ={
"insert into type (id,code)values(NEXTVAL('type_id'),'event')",
"create table event( id int, start_date DATE,end_date DATE, " +
        "constraint pk_event primary key (id),  " +
        "constraint fk_event foreign key(id)references object  on delete cascade)",
"insert into event(id)values(0)",
"insert into translator(tabcol,rus)values('event::start_date','начало')",
"insert into translator(tabcol,rus)values('event::end_date','окончание')",
              
"insert into type (id,code)values(NEXTVAL('type_id'),'task')",
"create table task( id int, started BOOLEAN,ended BOOLEAN,fact_end DATE," +
        " constraint pk_task primary key (id),  " +
        " constraint fk_task foreign key(id)references object  on delete cascade)",
"insert into task(id)values(0)",
               
"insert into translator(tabcol,rus)values('task::started','начато')",
"insert into translator(tabcol,rus)values('task::ended','сделано')",
"insert into translator(tabcol,rus)values('task::fact_end','окончание - факт')",
              
"insert into type (id,code)values(NEXTVAL('type_id'),'task_1')",
"create table task_1( id int,  constraint pk_task_1 primary key (id), " +
        " constraint fk_task_1 foreign key(id)references object  on delete cascade)",
"insert into task_1(id)values(0)",
              
"insert into type (id,code)values(NEXTVAL('type_id'),'wait_1')",
"create table wait_1( id int, task INTEGER default 0, " +
        "constraint fk_wait_1_task foreign key(task_1)references task_1, " +
        "constraint pk_wait_1 primary key (id),  " +
        "constraint fk_wait_1 foreign key(id)references object  on delete cascade)",
"insert into wait_1(id)values(0)",
"insert into translator(tabcol,rus)values('wait_1::task','ожидаем')",
              
"insert into type (id,code)values(NEXTVAL('type_id'),'manager')",
"create table manager( id int,  " +
        "constraint pk_manager primary key (id),  " +
        "constraint fk_manager foreign key(id)references object  on delete cascade)",
"insert into manager(id)values(0)",

 "insert into object (id,code,name,type)select NEXTVAL('object_id'),'m1','Смирнов',id from type where code='manager'",
 "insert into manager (id) select o.id from object o,type t where o.type=t.id and t.code='manager'"
        
             };
        return Arrays.asList(QQ);
    }},
    Testing{public List<String> qq(){
             String[] QQ ={
        "insert into type (id,code)values(NEXTVAL('type_id'),'testfolder')",
        "create table testfolder( id int,  " +
                "constraint pk_testfolder primary key (id),  constraint " +
                "fk_testfolder foreign key(id)references object  on delete cascade)",
        "insert into testfolder(id)values(0)",
                
"insert into type (id,code)values(NEXTVAL('type_id'),'abcd')",
"create table abcd( id int,  constraint pk_abcd primary key (id),  constraint fk_abcd foreign key(id)references object  on delete cascade)",
"insert into abcd(id)values(0)",
                      
"insert into object (id,code,name,type)select NEXTVAL('object_id'),'a','',id from type where code='abcd'",
"insert into object (id,code,name,type)select NEXTVAL('object_id'),'b','',id from type where code='abcd'",
"insert into object (id,code,name,type)select NEXTVAL('object_id'),'c','',id from type where code='abcd'",
"insert into object (id,code,name,type)select NEXTVAL('object_id'),'d','',id from type where code='abcd'",
"insert into abcd (id) select o.id from object o,type t where o.type=t.id and t.code='abcd'"
                 
             };
        return Arrays.asList(QQ);
    }};
    abstract public List<String> qq();
   
  /////////////////////////////////////////////////////////////////////////////////  
//    String resProcess()throws Exception{
//        List<String> list = SubSystem.ProcessMan.qq();
//        pp.exec(list);
//        return "process type";
//    }
    
//    String resPrj()throws Exception{
//        int iEvent = pp.getNewId(FTree.TypeTree);
//        int iTask = pp.getNewId(FTree.TypeTree);
//        int iTask1 = pp.getNewId(FTree.TypeTree);
//        int iTask2 = pp.getNewId(FTree.TypeTree);
//        int iWait1 = pp.getNewId(FTree.TypeTree);
//        int iManager = pp.getNewId(FTree.TypeTree);
//        
//             String[] QQ ={
//             
//"insert into type (id,code,name,parent2)values("+iTask+",'task','Задача',"+iEvent+")",
//"create table task( id int, started BOOLEAN,ended BOOLEAN,fact_end DATE," +
//        " constraint pk_task primary key (id),  " +
//        " constraint fk_task foreign key(id)references object  on delete cascade)",
//"insert into task(id)values(0)",
//               
//"insert into translator(tabcol,rus)values('task::started','начато')",
//"insert into translator(tabcol,rus)values('task::ended','сделано')",
//"insert into translator(tabcol,rus)values('task::fact_end','окончание - факт')",
//        
//"insert into type (id,code,name,parent2,icon)values("+iManager+",'manager','Менеджер',1,'palm')",//1 = user as type
//"create table manager( id int,  " +
//        "constraint pk_manager primary key (id),  " +
//        "constraint fk_manager foreign key(id)references object  on delete cascade)",
//"insert into manager(id)values(0)",
//        
//"insert into type (id,code,name,parent,parent2,icon)values("+
//        iTask1+",'task_1','Задача 1',"+iManager+","+iTask+",'task')",
//"create table task_1( id int,  constraint pk_task_1 primary key (id), " +
//        " constraint fk_task_1 foreign key(id)references object  on delete cascade)",
//"insert into task_1(id)values(0)",
//
//"insert into type (id,id2,code,parent)values("+
//        iTask2+","+iTask1+",'task_1_',"+iTask1+")",        
//        
//"insert into type (id,code,name,parent,icon)values("+iWait1+",'wait_1','Зависимость1',"+iTask1+",'wait')",
//"create table wait_1( id int, task INTEGER default 0, " +
//        "constraint fk_wait_1_task foreign key(task)references task_1, " +
//        "constraint pk_wait_1 primary key (id),  " +
//        "constraint fk_wait_1 foreign key(id)references object  on delete cascade)",
//"insert into wait_1(id)values(0)",
//"insert into translator(tabcol,rus)values('wait_1::task','ожидаем')",    
//             };
//             pp.exec(Arrays.asList(QQ));
//        return "task-wait-manager";
//    }
    
//    String resPrjPlus()throws Exception{
//        int iPar = Integer.parseInt(par);
//        int iEvent = pp.getN("select id from type where code='event'");
//        int iTask = pp.getN("select id from type where code='task'");
//        int iTask1 = pp.getNewId(FTree.TypeTree);
//        int iTask2 = pp.getNewId(FTree.TypeTree);
//        int iWait1 = pp.getNewId(FTree.TypeTree);
//        int iManager = pp.getN("select id from type where code='manager'");
//String[] QQ ={
//        
//"insert into type (id,code,name,parent,parent2,icon)values("+
//        iTask1+",'task_"+iPar+"','Задача "+iPar+"',"+iManager+","+iTask+",'task')",
//"create table task_"+iPar+"( id int,  constraint pk_task_"+iPar+" primary key (id), " +
//        " constraint fk_task_"+iPar+" foreign key(id)references object  on delete cascade)",
//"insert into task_"+iPar+"(id)values(0)",
//
//"insert into type (id,id2,code,parent)values("+
//        iTask2+","+iTask1+",'task_"+iPar+"_',"+iTask1+")",        
//        
//"insert into type (id,code,name,parent,icon)values("+iWait1+
//        ",'wait_"+iPar+"','Зависимость "+iPar+"',"+iTask1+",'wait')",
//"create table wait_"+iPar+"( id int, task INTEGER default 0, " +
//        "constraint fk_wait_"+iPar+"_task foreign key(task)references task_"+iPar+", " +
//        "constraint pk_wait_"+iPar+" primary key (id),  " +
//        "constraint fk_wait_"+iPar+" foreign key(id)references object  on delete cascade)",
//"insert into wait_"+iPar+"(id)values(0)",
//"insert into translator(tabcol,rus)values('wait_"+iPar+"::task','ожидаем')",
// 
//             };
//             pp.exec(Arrays.asList(QQ));
//        return "task-wait-manager";
//    }
    
//        switch(f){
//            //case 10: return resAllSchool(); 
//            
//            case 20: return resProcess();
//            case 30: return resPrj();
//            case 31: return resPrjPlus();
//        }
//    final static String d1 = "Управление процессами";
//    final static String d2 = "Управление проектами - начинаем";
//    final static String d3 = "Управление проектами - продолжаем. " +
//            " Задайте номер продолжения >1";    
    
//        map.put("20","process manager");
//        map.put("30","project manager");
//        map.put("31","project plus N");    
//            case 20:return d1;
//            case 30:return d2;
//            case 31:return d3;              
//    int getId(String type)throws Exception{
//        return pp.getN("select id from type where code='"+type+"'");
//    }    
}
