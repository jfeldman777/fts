/*
 * ProtoNode.java
 *
 * Created on 23 Ќо€брь 2004 г., 14:37
 */

package j5feldman;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class PNode extends P3Node {
    protected String typecode;
  
    /** Creates a new instance of ProtoNode */
    public PNode(int id, String code, String name,int parent,int id2,
            int type,String typecode,boolean abstr,boolean abc, boolean fin){
        super(id,code,name,parent,id2,type,abstr,abc, fin); 
        this.typecode = typecode;
    }
    
    public String typeCode() {
        return typecode;
    }
    public String typeCodeName(XPump pp) throws Exception{
        String tname = pp.getS("select name from type where code='" +typecode+"'");
        if(tname==null||tname.equals(""))tname=typecode;
        return tname;
    }    
    public String face(XPump pp)throws Exception{
        return "<img alt='"+typecode+"' border=0 src='"
                +pp.iconpath()+pp.icon(typecode)+".gif'>&nbsp;"+codeName();
    }
    public static void allDownPath(List<PNode> list,XPump pp)throws Exception{
        PNode node=new PNode(0,"o","no data",0,0,0,"object",false,false, false); 
        node.path="/o";
        list.add(node);
        allDownPath(list,pp,node);
    }
    public static void allDownPath(List<PNode> list,XPump pp,PNode parent)throws Exception{
        String q = "select o.id,o.code,o.name,o.parent,o.id2,o.type, " +
                " t.code typecode,t.abc,t.fin,o.abstr "+
                " from object o,type t " +
                " where t.id=o.type and o.id="+parent.id;
        List<PNode> children = pp.getNodeS(q);
        if(children.size()==0)
            return;
        for(PNode root:children){
            root.path = parent.path+"/"+root.code;
            allDownPath(list,pp,root);
        }
    }
}
