/*
 * TypeField.java
 *
 * Created on 2 Èþíü 2005 ã., 10:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package j5feldman;

/**
 *
 * @author Jacob Feldman
 */
public class TypeField extends TabCol implements ITabCol{
    
    /** Creates a new instance of TypeField */
    public TypeField(XPump pp,String name) {
        super(pp,"type",name);
    }
    
    public String ftype(GuiTree.GuiNode gn,boolean noe)throws Exception{
        return gn.typeCode();///////////////////?
    }
    
    public String fvalue(GuiTree.GuiNode gn,String val,boolean sh)throws Exception{
            return val+tdE(gn);
    }
    public String fvalue(GuiTree.GuiNode gn,String val,boolean sh,boolean noe)throws Exception{
            return val+tdE(gn,noe);
    }
    public String c(){
        return "t";
    }
    
    public String value(int id)throws Exception{
        String q = "select " +name+
                " from type where id="+id;
        return pp.getS(q);
    }    
    
    public void setValue(int id,String val)throws Exception{
        String q = "update type set " + name +
                "='"+ val +
                "' where id="+id;
        pp.exec(q);
    }    
}
