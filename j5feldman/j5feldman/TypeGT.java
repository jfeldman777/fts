package j5feldman;
public class TypeGT extends GuiTree implements IGuiTree{ 
    final static int FOLDER = 2;
       public TypeGT(XPump pp,int id)throws Exception{
         super(pp);
         ft = FTree.TypeTree;
         root=getGuiNode(id);
         nodes.add(root); 
//         GuiNode folder = null;
//         if(id>0){
//            folder = getGuiNode(FOLDER);//
//            folder.parent = id;
////            folder.depth = 1;
//            nodes.add(folder); 
//         }
             
      }
    public void move(int n,int parentId)throws Exception{
        int p = parentId==2?0:parentId;
        super.move(n,p);
    }
       void checkMakeShortcut(int n,int parentId){
      }

      String T(){return "T";}
      String t(){return "t";}
   }