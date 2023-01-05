package j5feldman;
import java.lang.*; 
import java.util.*;
public interface IGuiTree{
   int id2n(int id)throws Exception;
   int num2n(int num)throws Exception;
   int num2id(int num)throws Exception;       
   void drop(int n)throws Exception;
   void move(int n,int parentId)throws Exception;
   void moveId(int n,int parentId)throws Exception; 
   void collapse(int n)throws Exception;
   void expand(int n)throws Exception;
   void expand2(int id)throws Exception;   
   void makeShortcutNum(int n,int parentId,boolean blind)throws Exception;
   void makeShortcut(IGuiNode gn,int parentId,boolean blind)throws Exception;
   IGuiNode getNode(int n)throws Exception;
   IGuiNode getGuiNode(int id)throws Exception;
   FTree getFT();
   boolean isObj();
   boolean isType(); 
   String show()throws Exception;
   IGuiNode root();
//   void doInsert(SortedMap<String,String> map)throws Exception;
       
    List<GuiTree.GuiNode> nodes();
    
    void refreshId(int parent)throws Exception;
    void refreshParentId(int parent)throws Exception;
    void collapseParentId(int parent)throws Exception;
    void makeShortcutId(int id, int parent, boolean b)throws Exception;

    void makeShortcutList(int n, int parent)throws Exception;  
    
    void showPath(int id)throws Exception;
}