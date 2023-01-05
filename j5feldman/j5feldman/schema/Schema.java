package j5feldman.schema;
import java.io.*;
import j5feldman.XPump;
public class Schema{
    final static String qType = "create table type ("+
            "id int,"+
            "id2 int default 0,"+
            "id22 int default 0,"+/////////////////////////add
            "code text not null,"+
            "name text default '',"+
            "parent int default 0,"+
            "parent2 int default 0,"+
            "abstr boolean default false,"+
            "fin boolean default false,"+
            "abc boolean default false,"+
//            "flash boolean default false,"+
            "icon text," +
            "imadeit text default ''," + 
            "itcamefrom text default '',"+
            "about text default '',"+            
            "constraint pk_type primary key (id),"+
            "constraint uq_type unique (code),"+
            "constraint fk_type_id2 foreign key (id2) references type on delete cascade,"+
            "constraint fk_type_id22 foreign key (id22) references type on delete cascade,"+
            "constraint fk_type_parent foreign key (parent) references type,"+
            "constraint fk_type_parent2 foreign key (parent2) references type,"+
            "constraint ch_type_code check (code REGEX '[A-z_]+[A-z_0-9]*')"+
            ")";
    final static String qObject = "create table object ("+
            " id int,  "+
            " code text not null, "+
            " name text default '', "+
            " parent int default 0, "+
            " type int default 0, "+
            " id2 int default 0,"+
            " abstr boolean default false,"+//blind
            " fin boolean default false,"+         
            " creation_date timestamp default dateob(),"+
            " last_update timestamp default dateob(),"+
            " constraint pk_object primary key (id), "+
            " constraint fk_object_type foreign key (type) references type, "+
            " constraint fk_object_parent foreign key(parent)references object "+
            " on delete cascade ,"+
            " constraint fk_object_id2 foreign key (id2)references object "+
            " on delete cascade, "+
            " constraint uq_object unique (parent, code) "+
            ")";
    
    final static String qUser = "create table xuser("+
            " id int, "+
            " pwd text not null, "+
            " hello text, "+
            " constraint pk_xuser primary key (id), "+
            " constraint fk_xuser foreign key (id)references object "+
            " on delete cascade, "+
            " constraint uq_xuser unique (pwd) "+
            ")";
    final static String qQuery = "create table query("+
            " id int, "+
            " body text default '', "+
            " pardesc text default '', "+
            " headlevel int default 0, "+
            " params text default '',"+
            " constraint pk_query primary key (id), "+
            " constraint fk_query foreign key(id)references object "+
            " on delete cascade"+
            ")";
    
    final static String qFolder = "create table folder("+
            " id int, "+
            " constraint pk_folder primary key (id), "+
            " constraint fk_folder foreign key(id)references object "+
            " on delete cascade"+
            ")";
    final static String qQfolder = 
            "create table qfolder( id int,  constraint pk_qfolder primary key (id),  " +
            "constraint fk_qfolder foreign key(id)references object  on delete cascade)";
    
    final static String tObject =
            "insert into type (id,code,name)values(0,'object','Объект')";
    final static String tUser =
            "insert into type (id,code,name)values(1,'xuser','Пользователь')";
    final static String tFolder =
            "insert into type (id,code,name)values(2,'folder','Папка')";
    final static String tQfolder = 
            "insert into type (id,code,name)values(3,'qfolder','Пакет запросов')"; 
    final static String tQuery =
            "insert into type (id,code,name,parent)values(4,'query','Запрос',3)";

    final static String zObject =
            "insert into object (id,parent,code,name,type)values(0,0,'o','нет данных',0)";
    static String zUser;
    final static String zQuery =
            "insert into query (id)values(0)";
    final static String zFolder =
            "insert into folder (id)values(0)";

    final static String zQfolder = "insert into qfolder(id)values(0)";   
    final static String qTranslator = "create table translator ("+
            " tabcol text, "+
            " rus text, "+
            " constraint pk_translator primary key (tabcol)"+
            ")";
    final public static String qShadowcols = "create table " +
            " shadowcols ("+
            " tab text not null, "+
            " col text not null, " +
            " tctype char(1) not null,"+
            " constraint pk_shadowcols primary key (tab,col)," +
            " constraint ch_shadowcols_tctype check (tctype REGEX '[cdp]')"+
            ")";    

        final public static String qRefhh = "create table " +
            " refhh ("+
            " tab text not null, "+
            " col text not null, " +
                " blind boolean default true, " +
            " ref text not null, " +                
            " constraint pk_refhh primary key (tab,col)" +
            ")"; 
    
    final static String rusCode =
            "insert into translator (tabcol,rus)values('object::code','код')";
    final static String rusName =
            "insert into  translator (tabcol,rus)values('object::name','наименование')";
    final static String rusLev =
            "insert into  translator (tabcol,rus)values('query::headlevel','уровень разрезания')";
    
    final static String rusPwd=
            "insert into  translator (tabcol,rus)values('xuser::pwd','пароль')";
    final static String rusHello=
            "insert into  translator (tabcol,rus)values('xuser::hello','приветствие')";
    final static String rusQpar=
            "insert into  translator (tabcol,rus)values('query::pardesc','описание параметров')";
    
    final static String rusQpar2=
            "insert into  translator (tabcol,rus)values('query::params','значение параметров')";
    
    final static String rusQbody=
            "insert into  translator (tabcol,rus)values('query::body','тело запроса')";    
    final static String rusTauthor=
            "insert into  translator (tabcol,rus)values('type::imadeit','составитель')";    
    final static String rusTsource=
            "insert into  translator (tabcol,rus)values('type::itcamefrom','источник')"; 
    final static String rusTabout=
            "insert into  translator (tabcol,rus)values('type::about','смысл')"; 
    
    final static String sType = "create sequence type_id increment 1 start 8 cache 1";
    final static String sObject = "create sequence object_id increment 1 start 0 cache 1";
    final static String sErrlog = "create sequence errlog_id increment 1 start 1 cache 1";
    
    final static String qErrlog = "create table errlog(n int default NEXTVAL('errlog_id'),err text)";
    
    final static String qToolbar="alter table xuser add column toolbar integer default 0";
    final static String qButtons="alter table xuser add column buttons integer default 0";
    final static String qArrows="alter table xuser add column arrows  integer default 0";
  
    XPump go(String schema,File f)throws Exception{
        if(!f.exists())throw new Exception("no such file:"+f.getAbsolutePath());
        String conf = f.getAbsolutePath();
        XPump pp = new XPump();
        try{
            pp.connect(conf,schema,true);

            zUser = "insert into xuser(id,pwd,hello)values(0,'"
                    + schema + //"pwd" +
                    "','Master')";    
            String[] QQ={
                        sType,sObject,sErrlog,
                        qType,qErrlog,qTranslator,qShadowcols,
                qObject,qUser,qFolder,qQfolder,qQuery,
                tObject,tUser,tFolder,tQfolder,tQuery,
                zObject,zUser,zFolder,zQfolder,zQuery,

                rusCode,rusName,
                rusLev,rusPwd,rusHello,rusQpar, rusQpar2,rusQbody,
                rusTsource,rusTauthor,rusTabout
                                
                ,qRefhh,qToolbar,qButtons,qArrows
            };        
            for(int i=0;i<QQ.length;i++){
                System.out.println("i="+i+":"+QQ[i]);
            }
            pp.exec(QQ);
        }catch(Exception e){
            System.out.println("database is created before");
        }
        return pp;
    }
    

}