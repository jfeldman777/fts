/*
 * ObjectGTqf.java
 *
 * Created on 14 Èþíü 2006 ã., 15:32
 * Auhor J.Feldman
 */

package j5feldman;

/**
 *
 * @author Jacob Feldman
 */
public class ObjectGTnof extends ObjectGT implements IGuiTree{
    public boolean isOpen(GuiNode node)throws Exception{
        return true;
    }
    /** Creates a new instance of ObjectGTqf */
    public ObjectGTnof(XPump pp,int id) throws Exception{
        super(pp,id);
        pp.setMtree();
    }
    String icon(GuiNode node) throws Exception{
        String code = node.typeCode();
        String icon = node.icon();
                    return  "<img border=0 src="+pp.iconpath()+icon+".gif>";

    }
    String bridge(){
        return "../";
    }    
}
