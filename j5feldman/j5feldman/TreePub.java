/*
 * TreePub.java
 *
 * Created on 19 Июль 2006 г., 10:50
 * Auhor J.Feldman
 */

package j5feldman;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.charset.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author Jacob Feldman
 */
public class TreePub {
    int root;
    XPump pp;
    String path;
    File d=null;    
    final static String encUtf="UTF-8";    
    final static String enc1251="Windows-1251"; 
    IGuiTree objTree;     
    /** Creates a new instance of TreePub */
    public TreePub(XPump pp,int root,IGuiTree objTree) {
        this.root = root;
        this.pp = pp;
        this.path = pp.pubPath()+"\\"+root;
        this.objTree=objTree;       
        d = new File(path);
        d.mkdirs();
    }
    
    void s2file(String qq,String path)throws Exception{
        File f=null;
            f = new File(path);
        f.delete();     
        f.createNewFile();
        BufferedWriter bw=null;
        bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f),Charset.forName(enc1251)));
        bw.write(qq);

        bw.close();
    }
    
    List<P2Node> p2down()throws Exception{
        return P2Node.allDown(pp,FTree.ObjectTree,root);
    }
    void writeFiles()throws Exception{
        List<P2Node> L = p2down();
        for(P2Node p:L){
            writeFiles(p.id());
        }
        writeIndex();        
    }
    void writeFiles(int id)throws Exception{
        int n = objTree.id2n(id);
        IGuiNode gn = objTree.getNode(n);         
        writeRight(id);
    }
    void writeIndex()throws Exception{
        String s = "<html><head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\">"+
                "</head>" +
                "\n<body bgcolor=white>" +
                "\n" +treeIndex()+
                "\n</body>" +
                "</html>";
        
        s2file(frame(),path+"\\index.htm");
        s2file(blank(),path+"\\blank.htm");
        s2file(s,path+"\\tree.htm");        
    }
    
    String treeIndex()throws Exception{
        String s = "";
        for(GuiTree.GuiNode p:objTree.nodes()){  
            s+="<br>";
//            s+="<a href='frame" +p.id()+
//                    ".htm' target=righ title=frames>";
            for(int i=0;i<p.depth;i++){
                s+="+";
            }
//            s+="</a>";
            s+="\n";
            if(!p.isPseudo())
            s+="<a title='" +p.typeCode() +
                        "' href='right" +p.id()+//Origin()+
                    ".htm' target=right>";
            s+=p.codeName();
            if(!p.isPseudo())s+="</a>";
//            String x = ulExec(p.id());
//            if(!x.equals(""))
//            s+="<ul>"+x+"</ul>";
        }
        return s;
    }

    
    void writeRight(int id)throws Exception{
        String s = "<html><head>" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\">"+
                "</head><body>" +
                "\n<table border=2 bgcolor=white>" +
                topTable(id)+
                "\n</table>" +//ulExec(id)+
                "\n</body></html>";
        s2file(s,path+"\\right"+id+".htm");
    }
        String blank(){
           return "<HTML><HEAD>" +
                   "<TITLE>blank</TITLE></HEAD><body></body>" +
                                "\n</HTML>";
    }    
    String frame(){
           return "<HTML><HEAD>" +
                   "<TITLE>fts frames</TITLE></HEAD>" +
                "\n<frameset cols='30%,*'>" +
                "\n<frame name=left src=tree.htm></frame>" +
                "\n<frame name=right src=blank.htm></frame>" +
                "\n</frameset>" +
                "\n</HTML>";
    }
   

    public String topTable(int id)throws Exception{////////////////////////       
        int n = objTree.id2n(id);  
        objTree.expand(n);            
        IGuiNode nn = objTree.getNode(n);  
        String s = FTree.InheritanceTree.boxView(pp,(GuiTree.GuiNode)nn,true);  
        
//        System.out.println("s1"+s);

        String x = pp.brrr();
        String s2 = s.replaceAll(x,"../../");

//        System.out.println("s2"+s);
        
        return s2;
    }    
    
//    public static void main(String[] args){
//        String b = "<tr valign=bottom ><td>код</td><td>Грейвз, Клер Уильям</td></tr>" +
//                "<tr valign=bottom ><td>наименование</td><td>1914-1987</td></tr><tr valign=bottom >" +
//                "<td>описание</td><td></td></tr><tr valign=bottom ><td>описание англ</td><td>" +
//                "</td></tr><tr valign=bottom ><td>рисунок</td>" +
//                "<td><img border=0 src='/feldman-root/system/tuai/pic/description/figure.gif/111.gif'></td></tr>" +
//                "<tr valign=bottom ><td>фото</td><td></td></tr>";
//        String x = "/feldman-root/system/tuai/";
//        String s = b.replaceAll(x,"../../");
//        System.out.println(s);
//    }
    
//    public String ulExec(int id)throws Exception{
//        int n = objTree.id2n(id);        
//        IGuiNode p = objTree.getNode(n);  
//
//            String su = "";
//            String q = "select count(*) from type t,object o where " +
//                    " o.type=t.id and o.id="+p.id();
//            if(pp.getN(q)>0){
//                su+=ul(p.idOrigin());
//            }    
//            return su;
//        }    
//    public String ul(int id)throws Exception{
//            String s = "";
//            List<IProc> list = pp.getProc(id);
//            for(IProc proc:list){
//                s += proc.ulTreePub();
//                Set<String> L = proc.getKeySet();
//                for(String fun:L){
//                    if(!proc.isForce(fun)) 
//                        if(!fun.equals("999"))writeReport(id,fun);
//                }
//            }
//            return s;
//        }        
//    public String procResult(int id,String fun)throws Exception{
//            String s = "";
//            List<IProc> list = pp.getProc(id);
//            for(IProc proc:list){
//                s += proc.result(fun,"");//null 
//            }                                                                                                                                             ;
//            return s;
//    } 
//    
//    void writeReport(int id,String fun)throws Exception{
//        String x = "exception:"+id+":"+fun;
//        try{
//            x=procResult(id,fun);
//        }catch(Exception e){
//            x+="::"+e.getMessage();
//        };
//        String s = "<html><head>" +
//            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1251\">"+
//                "</head><body>" +x+              
//                "\n</body></html>";
//        s2file(s,path+"\\node"+id+"f" +fun+
//                ".htm");
//    }
}
