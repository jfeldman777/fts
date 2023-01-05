/*
 * SqlExport.java
 *
 * Created on 18 Октябрь 2006 г., 15:49
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import j5feldman.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class SqlExport {
    XPump pp;
    /** Creates a new instance of SqlExport */
    public SqlExport(XPump pp) {
            this.pp=pp;
    }
    public String db2area(int id)throws Exception{//создание типа в новой базе как в старой
        String qTranslator = "";        
        String code = pp.getS("select code from type where id="+id);
        String about = pp.getS("select about from type where id="+id).replace('\n',' ').replace('\r',' ');
        String itcamefrom = pp.getS("select itcamefrom from type where id="+id).replace('\n',' ').replace('\r',' ');
        String imadeit = pp.getS("select imadeit from type where id="+id).replace('\n',' ').replace('\r',' ');        
        String qType = "insert into type (id,code" +
                ",about,itcamefrom,imadeit" +
                ")values(NEXTVAL('type_id'),'"+code+"'" +
                ",'"+about+"','"+itcamefrom+"','"+imadeit+"'" +
                ")\n\n";
        Table tab = new Table(pp,code,false);
        String qFF = "";
        String qKK = "";
        for(ITabCol f:tab.fields){
            if(f.name().equals("id"))continue;
            String rus = f.getRus().replace('\n',' ').replace('\r',' ');
            if(f.hasRus())qTranslator+=
                "insert into translator(tabcol,rus)values('"+
                    tab.name+"::"+f.name()+"','"+rus+"')\n\n";
            qFF+=f.name()+" "+f.dtName();
            if(f.isKey()){
                qFF+=" default 0,";
                qKK+=" constraint fk_"+tab.name+"_"+f.name()+
                    " foreign key("+f.name()+")references "+f.refTab()+",";
            }else qFF+=",";
      
        }
        String qCreate = "create table "+code+"(" +
            " id int, "+qFF+
            " constraint pk_"+code+" primary key (id), "+qKK+
            " constraint fk_"+code+" foreign key(id)references object "+
            " on delete cascade"+
                ")\n\n";
        String qInsert = "insert into "+code+"(id)values(0) \n\n";
        String s = qType + qCreate + qInsert + qTranslator + about;
        return s;
    }
    public String db20area(int id)throws Exception{//создание объектов данного типа в новой базе как в старой
        String code = pp.getS("select code from type where id="+id);
        String qType = "insert into "+code+
                " (id) select o.id from object o,type t where o.type=t.id and t.code='"+code+"'";
        String qInsert = "";//qInsert(code);//id);
        List<P20Node> list = P20Node.getP20type(pp,id);
        for(P20Node node:list){
            qInsert+="insert into object (id,code,name,type)select "+
            "NEXTVAL('object_id'),'"+node.code()+"','"+
                    node.name()+"',id from type where code='"+code+"'" +
                    "\n\n";
        }
        String s = qInsert + qType;
        return s;
    }
}
