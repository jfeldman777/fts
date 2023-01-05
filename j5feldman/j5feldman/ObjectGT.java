package j5feldman;
import java.util.*;    
    public class ObjectGT extends GuiTree implements IGuiTree{ 
       public ObjectGT(XPump pp,int id)throws Exception{
         super(pp);
         ft = FTree.ObjectTree;
         root=getGuiNode(id);
         nodes.add(root);        
      }
       void checkMakeShortcut(int n,int parentId){
      }

      String T(){return "A";}
      String t(){return "a";}
}