/*
 * Buttons.java
 *
 * Created on 28 Май 2007 г., 8:29
 * Auhor J.Feldman
 */

package j5feldman.profile;
import j5feldman.*;
import j5feldman.proc.basement.CreateObj;
/**
 *
 * @author Jacob Feldman
 */
public enum Buttons {
    Create(0,"New","Create new","Создать",true,true,false,false,false,true,false){
        String layer1(String style,int iLang){
//            String s = "<input type=image src=" +style +"new" +
//                    ".gif"+
//                    " alt='" +tip(iLang)+"' " +
//                    " onclick='this.src=\"0.gif\"; " +
//                    " x2go=1;" +
//                    " ' "+ 
//                    " onmouseout='" +
//                    " if(x2go==1) return;" +
//                    " this.src=\"" +style+"new"+
//                    ".gif\"' "+ 
//                    " onmouseover=' if(x2go==1) return; " +
//                    " this.src=\"" +style +"new" +
//                    "2.gif\";" +
//                    "' "+
//                    ">";
            return img3input(style,kw(),iLang);
        }
        
        String layer2x(IGuiNode pn,XPump pp,boolean admin)throws Exception{
            CreateObj c = new CreateObj(pp,null,0,""); 
            String L1 = pn.innerLBtypes();
            String L2 = c.optionListA();
            String L3 = (admin?c.optionListB():"");
            String s = "<input type=text name=addr title=addr>"+
        "<select name=type >\n<option value=-1>-------</option>\n" +L1+
                "</select>\n<br><select name=list>\n"+
        "<option value=-1>-------</option>\n"+
                L2 + L3 + 
                "</select>" +
                    "<input type=hidden name=parent value=" +pn.id()+
                    ">";
            
            return s;
           }
        
        String form(){
            return "<form id=fnew action='do/newobjform.jsp' method=get>";
        }
        
     String tr0(String style,boolean vis,boolean admin,IGuiNode nn,XPump pp)throws Exception{
        String s = "<tr>"+form()+"<td>"+
                layer10(style,vis,pp.iLang)+"</td><td>"+
                "<div id=\"LayerNew2\" style=\"visibility:" +(vis?"visible":"hidden") +"\">"+
                layer2x(nn,pp,admin)+"</div>"+
                "</td></form></tr>\n";
        return s;
     }
        
    },
    Edit(1,"Edit","Edit this","Изменить",false,true,false,false,true,true,false){
        String layer12(String style,int id,int iLang){
            return "<a href='do/editform.jsp?id=" +id+ "' title='" +tip(iLang)+
                    "'>"+img3a(style,kw())+
//            "<img src='" +style+ "edit" +
//                    ".gif' border=0>" +
                    
                    "</a>";
        }
    },
    Delete(2,"Delete","Delete this","Удалить",false,true,false,true,true,true,false){
        String layer12(String style,int id,int iLang){
            return "<a href='do/delete.jsp?id=A" +id+"' title='" +tip(iLang)+
                    "'>"+img3a(style,kw())+
                    //"<img src='" +style+"delete.gif' border=0>" +
                    "</a>";
        }
    },
    Type(3,"Type","Tune the type","Изменить тип",true,true,false,false,true,true,false){
        String layer12(String style,int id,int iLang){
            return "<a href='ntype/menu.jsp?xid=" +id +
                    "' title='" +
                    tip(iLang) +
                    "' onClick=\"alert('click and wait')\">" +
                    img3a(style,kw())+
                    //"<img src='" + style + "type.gif' border=0>" +
                    "</a>";
        }
    },
    Move(4,"Move","Move under address","Передвинуть по адресу",false,true,false,true,true,true,false){
        String layer1(String style,int iLang){
            String s = img3input(style,kw(),iLang);
            //String s="<input type=image src='" +style+"move.gif' alt='" +tip(iLang)+"' " +" onClick=\"f('fmove')\">";
            return s;
        }
        
        String layer2(int id){
            String s="<input type=text name=parent><input type=hidden name=id value='A" +id+
                    "'>";
            return s;
        }
        String form(){
            String s = "<form id=fmove action='do/move.jsp' method=post>";
            return s;
        }
    },
    Shortcut(5,"Shortcut","Place shortcut under address","Разместить ярлык по адресу",false,false,false,false,true,false,false){
        String layer1(String style,int iLang){
            String s = img3input(style,kw(),iLang);
            return s;
                    //"<input type=image src='" +style+ "shortcut.gif' alt='" +tip(iLang)+ "'" +" onClick=\"f('fh')\">";
        }
        
        String layer2(int id){
            return "<input type=hidden name=id value=A" +id+
                    "><input type=text name=parent>";  
        }
        
        String layer2blind(boolean blind,boolean admin){
            if(admin)return
                    "<input type=checkbox name=blind title='blind node/shortcut' " +
                    (blind?"CHECKED":"") + " >";                    
            if(blind)return "<input type=hidden name=blind value='on'>";
            return "";
        }
        
        String form(){
            return "<form id=fh action='do/shortcut.jsp' method=post>";
        }
    },
    Send(6,"Send","Send according to list","Разослать по списку",false,false,false,false,true,false,false){
        String layer1(String style,int iLang){
            String s = img3input(style,kw(),iLang);
            return s;
                    //"<input type=image src='" +style+"send.gif' alt='" +tip(iLang)+"' onClick=\"f('send')\">";
        }
        
        String layer2(int id){
            return "<input type=hidden name=id value=A" +id+"><input type=text name=parent>"; 
        }
        
        String form(){
            return "<form id=send action='do/send.jsp' method=post>";
        }
    },
    Find(7,"Find","Find with criterion","Найти по критерию",true,true,true,false,false,false,false){
        String layer1(String style,int iLang){
            String s = img3input(style,kw(),iLang);
            return s;
            //"<input type=image src='" +style+"find.gif'  alt='" +tip(iLang)+"'>";
        }
        
        String layer2(int id){
            return "<input type=text name=criteria><input type=checkbox name=sh title='look for shortcuts: o.code'>";
        }
        String form(){
            return "<form action=find.jsp method=post>";
        }
    },
    Query(8,"Sql","Execute query as sql","Выполнить запрос",false,false,true,false,false,false,false){
        String layer1(String style,int iLang){
            String s = img3input(style,kw(),iLang);
            //String s="<input type=image src='" +style+"sql.gif' title='" +tip(iLang)+"'>";
            return s;
        }
        
        String layer2(int id){
            String s = "<textarea name=sql cols=50 rows=3></textarea>";
            return s;
        }
        
        String form(){
            String s="<form action=sql2.jsp method=post>";
            return s;
        }
        
        
    },
    Export(9,"Export","Tree publication","Опубликовать дерево",true,true,true,false,false,false,false){
        String layer12(String style,int id,int iLang){
            return "<a href='do/treepub.jsp?id=" +id+"' title='" +tip(iLang)+"'>" +
                    img3a(style,kw())+
                    //"<img src='" +style+"export.gif' alt='" +tip(iLang)+"' >" +
                    "</a>";

        }

    },
    Lock(10,"Lock","Lock-unlock","Законсервировать",false,true,true,false,false,false,false){       
        String layer1(String style, int iLang){
            return img3input(style,kw(),iLang);
//            return "<input type=image src='" +style+
//                    "lock.gif' alt='lock' onClick=\"f('" +tip(iLang)+
//                    "')\" >";
        }
        
        String layer2(int id){
            return "<input type=hidden name=id value=" +id +">\n"+
            "<select name=var>\n" +
                "<option value=0>---</option>\n" +
                "<option value=1>Lock/Закрыть</option>\n" +
                "<option value=2>Unlock/Открыть</option>\n" +
                "<option value=3>Lock the tree/Закрыть дерево</option>\n"+
                "<option value=4>Unlock the tree/Открыть дерево</option>\n" +
                    "</select>";
        }
        
        String form(){
            return "<form id=lock action='do/lock.jsp' method=post>";
        }
    },
    Flash(11,"Flash","Flash editor","Flash-редактирование",false,true,true,false,true,true,false){            
        String layer12(String style,int id,int iLang){
            return "<a href='do/flash.jsp?id=" + id +
                    "' title='"+tip(iLang)+"'>" +
            img3(kw())+
                    //"<img src='/feldman-root/style/obj/flash.gif' border=0>" +
                    "</a>";
        }
    },
    
     Word(12,"Word","Format for Word","Публиковать в шаблоне Word",false,false,true,false,true,false,false){            
        String layer12(String style,int id,int iLang){
            return "<a href='do/word.jsp?id=" + id +
                    "' title='" +tip(iLang)+            "'>" +
                    img3(kw())+
            //"<img src='/feldman-root/style/obj/word.gif' border=0>" +
                    "</a>";
        }
    },
    
    Import(13,"Import","import data from file","Импортировать данные из файла",true,true,true,false,false,true,false){
        String layer1(String style,int iLang){
             String s = img3input(style,kw(),iLang);
            //String s="<input type=image src='" +style+"import.gif' title='" +tip(iLang)+"'>";
            return s;
        }
        
        String layer2(int id){
            String s = "<input type=hidden name=parent value=" +id+">" +
                    "<input type=text name=id>" +
                    "<input type=CHECKBOX name=dict CHECKED title='check dictionnary first'>"+
                    "<input type=file name=file>";
            return s;
        }
        
        String form(){ 
            String s="<form action=dbf/imp1.jsp method=post>";
            return s;
        }
        
        
    },
    Javascript(14,"Js","Javascript editor","Javascript-редактирование",false,true,true,false,true,true,false){            
        String layer12(String style,int id,int iLang){
            return "<a href='do/js.jsp?id=" + id +
                    "' title='" +tip(iLang)+
                    "'>" +
                    img3(kw())+
           // "<img src='/feldman-root/style/obj/js.gif' border=0>" +
                    "</a>";
        }
    },    
    Left(0,"Left","To inh tree","К зеленому дереву",false,false,false,false,false,false,true){

     public String path(int id,int type,int io){
            return "../inh/root.jsp?id=H"+type;
        }

    },   
    Up(1,"Up","One level up","Один уровень вверх",true,false,false,false,true,false,true){
         public String path(int id,int type,int io){
            return "rootup.jsp?id="+id;
        }
  
    }, 
    Here(2,"Here","Set root here","Корень поставить здесь",true,true,false,true,true,false,true){
         public String path(int id,int type,int io){
            return "root.jsp?id=A"+id;
        }
        
    },
    Back(3,"Back","Back to origin","К оригиналу",true,true,true,false,true,false,true){
                 public String path(int id,int type,int io){
            return "root.jsp?id=A"+io;
        }

    },    
    Down(4,"Down","Path to root","Путь к корню",false,false,false,false,true,false,true){
         public String path(int id,int type,int io){
            return "path.jsp?id="+id;
        }     

    },
    Right(5,"Right","To type tree","К синему дереву",false,false,false,false,false,false,true){
         public String path(int id,int type,int io){
            return "../type/root.jsp?id=T"+type;
        }

    };

    boolean yes(int t){
     return (t & mask())==mask();
    }
    
    String kw(){
        return Kw.toLowerCase();
    }
    
   String img_src(String src,String alt,int border){
        String s = "<img name=Button" +Kw+
                " src='/feldman-root/style/obj/_" +src+
                ".gif' alt='" +alt+
                "' border =" +border+" "+
                 onclick()+
                "\n>";
        return s;
    }
    
   
   String img_srcAr(String src,String alt){
        String s = "<img name=Arrow" +Kw+
                " src='/feldman-root/style/obj/" +src+
                ".gif' alt='" +alt+
                "' border=0>\n";
        return s;
    }
   
    String tip(int iLang){
        if(iLang==0)return Rtip;
        return tip;
    }
    public String img_srcAr(int iLang){
        return img_srcAr(kw(),tip(iLang));
    }
    public String img_src(int iLang,boolean yes){
        return img_src(kw(),tip(iLang),yes?1:0);
    }
       
    private final int value;
    private final int mask;
    private final String Kw;
    private final String tip;
    private final String Rtip;
    public final boolean not4shadow;
    public final boolean not4pseudo;
    public final boolean adminOnly;
    public final boolean not4root;
    public final boolean not40;
    public final boolean not4lock;
    private final boolean arrow;
    
    public int value() { return value; }
    
    Buttons(int value,String Kw,String tip,String Rtip,boolean not4shadow,boolean not4pseudo,boolean adminOnly,
            boolean not4root,boolean not40,boolean not4lock,
            boolean arrow) {
        this.value = value; 
        this.mask = 1 << value;
        this.Kw = Kw;
        this.tip = tip;
        this.Rtip = Rtip;
        this.not4shadow = not4shadow;
        this.not4pseudo = not4pseudo;
        this.adminOnly = adminOnly;
//        this.flashOnly = flashOnly;
        this.not4root = not4root;
        this.not40 = not40;
        this.not4lock = not4lock;
        this.arrow = arrow;
    }
    
    public int mask(){
        return mask;
    }
    
    public boolean isArrow(){
        return arrow;
    }
    
    String onclick(){
        String s = " onclick='up(\"" +Kw+
                "\");'"; 
        return s;
}
    String layer12(String style,int id,int iLang){return "";} 
    String layer1(String style,int iLang){return null;}

    String layer2(int id){return "";}
    String layer2x(IGuiNode pn,XPump pp,boolean admin)throws Exception{return "";}
    
    String layer2blind(boolean blind,boolean admin){return "";}
    String form(){return null;}
    String tr0(String style,boolean vis,boolean admin,IGuiNode nn,XPump pp)throws Exception{
        return "";
    }
    
    
    String layer120(String style,int id,boolean visible,int iLang){
        String s = "<div id=\"Layer" +Kw+             
                "2\" style=\"visibility:" +(visible?"visible":"hidden") +"\"></div>\n" +
                "<div id=\"Layer" +Kw+             //"Move" +
                "1\" style=\"visibility:" +(visible?"visible":"hidden") +"\">"+
                layer12(style,id,iLang)+"</div>";
        return s;
    };
    
    String layer10(String style,boolean visible,int iLang){
        String s = "<div id=\"Layer" +Kw+             //"Move" +
                "1\" style=\"visibility:" +(visible?"visible":"hidden") +"\">"+
                layer1(style,iLang)+"</div>\n";
        return s;
    };   
    
    String layer20(int id,boolean visible,boolean blind,boolean admin)throws Exception{
        String s = "<div id=\"Layer" +Kw+             //"Move" +
                "2\" style=\"visibility:" +(visible?"visible":"hidden") +"\">"+
                layer2(id)+ layer2blind(blind,admin)+"</div>\n";
        return s;
    }; 
    
    String tr(String style,int id,boolean vis,boolean blind,boolean admin,int iLang)throws Exception{
        if(form()==null)return "";
        if(layer2(id).equals(""))return "";
        String s = "<tr>"+form()+"<td>"+
                layer10(style,vis,iLang)+"</td><td>"+
                layer20(id,vis,blind,admin)+
                "</td></form></tr>\n";
        return s;
    }


    
    String td(String style,int id,boolean vis,int iLang){
        if(layer12(style,id,iLang).equals(""))return "";
        String s = "<td>"+
                (form()==null?"":form())+
                layer120(style,id,vis,iLang)+(form()==null?"":"</form>")+
                "</td>\n";
        return s;
    }
    
    public String path(int id,int type,int io){
        return "";
    }    

    public String barAr(int iLang,int id,int type,int io){
        return   "<a href='" +
                path(id,type,io)+ //"../type/root.jsp?id=T<%=type%>" +
                "' target=left title='" +
                tip(iLang)+//"to type tree/к синему дереву" +
                "'><img src='/feldman-root/style/obj/" +
                kw()+//"right" +
                ".gif' border=0></a>\n";
    }
    String img3input(String style,String kwd,int iLang){
            String s = "<input type=image src=" +style +kwd+
                    ".gif"+
                    " alt='" +tip(iLang)+"' " +
                    " onclick='this.src=\"/feldman-root/style/obj/0.gif\"; " +
                    " x2go=1;" +
                    " ' "+ 
                    " onmouseout='" +
                    " if(x2go==1) return;" +
                    " this.src=\"" +style+kwd+
                    ".gif\"' "+ 
                    " onmouseover=' if(x2go==1) return; " +
                    " this.src=\"" +style +kwd+
                    "2.gif\";" +
                    "' "+
                    ">";
            return s;
    }
    String img3a(String style,String kwd){
        String s0 = "/feldman-root/style/obj/0.gif";
            String s = "<img src='" +//s0+
                    style+kwd +".gif" +
                    "' " +
                    " onclick='this.src=\""+s0+
                    //"/feldman-root/style/obj/0.gif"+
                    "\"; " +
                    " x2go=1;" +
                    " ' "+ 
                    " onmouseout='" +
                    " if(x2go==1) return;" +
                    " this.src=\"" +style+kwd+
                    ".gif\"' "+ 
                    " onmouseover=' if(x2go==1) return; " +
                    " this.src=\"" +style +kwd+
                    "2.gif\";" +
                    "' "+
                    "border=0>";
            return s;
    }
        String img3(String kwd){
        String s0 = "/feldman-root/style/obj/0.gif";
            String s = "<img src='/feldman-root/style/obj/" +kwd +".gif" +
                    "' " +
                    " onclick='this.src=\""+s0+
                    "\"; " +
                    " x2go=1;" +
                    " ' "+ 
                    " onmouseout='" +
                    " if(x2go==1) return;" +
                    " this.src=\"/feldman-root/style/obj/" +kwd+".gif\"' "+ 
                    " onmouseover=' if(x2go==1) return; " +
                    " this.src=\"/feldman-root/style/obj/" +kwd+"2.gif\";" +
                    "' "+
                    "border=0>";
            return s;
    }  
}