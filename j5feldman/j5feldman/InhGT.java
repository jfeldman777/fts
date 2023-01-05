package j5feldman;
import java.util.*;
public class InhGT extends GuiTree implements IGuiTree{ 
    public InhGT(XPump pp,int id)throws Exception{
     super(pp);
     ft = FTree.InheritanceTree;
     root=getGuiNode(id);
     nodes.add(root);         
    }
    
    String T(){return "H";}
    String t(){return "h";}
    
}