/*
 * TreeType.java
 *
 * Created on 19 03 2003 , 9:48
 */

package j5feldman;
import java.lang.*; 
import java.util.*;
/** gui for tree of classes
 * @author Jacob Feldman/‘ельдман яков јдольфович
 */
public class NTypeTree extends InhGT {
    PNode obj;
    List<String> types;
    List<String> plusTypes=new ArrayList<String>();
    String t(){return "n";}
    public NTypeTree(XPump pp,int type,int objid,
            //ObjectGT
            IGuiTree ot) throws Exception{
        super(pp,type); 
        obj = pp.getNode(FTree.ObjectTree,objid);  
        types = pp.myTypes(obj.id());/**tables for the object with =id*/ 
        if(pp.meid>0)
        for(GuiTree.GuiNode n:ot.nodes()){//in ot only 
            String s = n.typeCode();
            if(!n.isShadow() && !n.isPseudo() &&
                !plusTypes.contains(s)) plusTypes.add(s);
        };
    }     
    /**type - center*/
    public boolean isOpen(GuiNode node) throws Exception{
        if(node.id()==0)return false;        
        boolean center = obj.typeCode().equals(node.typeCode()); 
        if(center)return false;
        String name = node.typeCode();
        
        node.lastUpdate="set type-center";
        
        return types.contains(name);
    }
    /**type plus minus*/
    boolean canRemove(GuiNode node) throws Exception{
        if(node.id()==0)return false; 
        if(pp.meid==0)return true;
        if(isOpen(node))return true;
        if(plusTypes.contains(node.typeCode()))return true;    
        return false;
    } 
    static void plus(XPump pp,String typecode,int id)throws Exception{
        List<P2Node> tabs = P2Node.pathUp(pp,typecode);
        for(P2Node tab:tabs){
            String q = "insert into "+tab.code()+" (id) values ("+id+") ";
            try{
                pp.exec(q);
            }catch(Exception e){}
        }
    }
    void plus(GuiNode node)throws Exception{
        plus(pp,node.typeCode(),obj.id());
    }
    void minus(GuiNode node)throws Exception{
        String qq = "update object set type=0 where id="+obj.id();
        List<String> QQ = new ArrayList<String>();
        List<String> ar = new ArrayList<String>();
        String q = "delete from "+node.typeCode()+" where id="+obj.id();
        QQ.add(q);
        if(node.typeCode().equals(obj.typeCode()))QQ.add(qq);
        List<P2Node> down = P2Node.allDown(pp,FTree.InheritanceTree,node.id());

        for(P2Node d:down){
            q = "delete from "+d.code()+" where id="+obj.id();
            QQ.add(q);
            if(d.code().equals(obj.typeCode()))QQ.add(qq);
        }       
        pp.exec(QQ);
        
    } 
    void editTypeSet(int n)throws Exception{
        GuiNode node = nodes.get(n);
        String name = node.typeCode();
        if(types.contains(name))minus(node);else plus(node);
    }
    int editTypeCenter(int n)throws Exception{
        GuiNode node = nodes.get(n);
        int parent = pp.getN("select parent from object where id="+obj.id());
        String q = "update object set type="+node.id()+" where id="+obj.id();
        pp.exec(q);
        return parent;
    }
    
    String delTitle(){
        return "type-set to change";
    }
    String inTitle(){
        return "type-center to change";
    } 
    String HB(GuiNode node) throws Exception {
        boolean center = obj.typeCode().equals(node.typeCode()); 
        if(center)return "";
        
        if(P2Node.inPath(P2Node.pathUp(pp,FTree.InheritanceTree,obj.type()),node)){
            return HB2(node);
        }
        
        return !canRemove(node)?"":
            
            
          "&nbsp;/<a title='"+delTitle()+"' target=right href='delete.jsp?id=n"+node.num+
          "'>"+(isOpen(node)?"-":"+")+"</a>"+HB2(node);   
    } 
    
    String HB2(GuiNode node){
              return (node.num==0?
           "&nbsp;/<a title='set old root' target=right href='root.jsp?id=n'>B</a>":
           "&nbsp;/<a title='set root here' target=right href='root.jsp?id=n"+node.num+"'>H</a>"); 
    }
    
    String T(){return "N";}
    
    String icon(GuiNode node) throws Exception{
        boolean center = obj.typeCode().equals(node.typeCode());
        String icon = node.icon();
        return "<img border=" +(center?2:0)+
                " src="+pp.iconpath()+icon+".gif>";
    }
}
