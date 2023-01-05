/*
 * Doc.java
 *
 * Created on 9 ƒекабрь 2004 г., 9:08
 */

package j5feldman;
import java.io.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class Doc extends TabCol implements ITabCol{
    
    /** Creates a new instance of Doc */
    public Doc(XPump pp,String tab,String col) {
        super(pp,tab,col);
    }
    
    String c(){
        return "d";
    }
    public String fvalue(GuiTree.GuiNode gn, String val,boolean sh)throws Exception {
        return fvalue(gn,val,sh,false);
    }
    public String fvalue(GuiTree.GuiNode gn, String val,boolean sh,boolean noe)throws Exception {
        String h = bridge()+gn.idOrigin(!sh)+getExt();
        String ph = pp.docpath()+h;
        String phd = pp.docPath()+h;
        File f = new File(phd);
        if(f.exists())return "<a target=doc href='"+ph+"'>"+name+"</a>"+tdE(gn,noe); 
        else return tdE(gn,noe);
    }
    
    public String ftype(GuiTree.GuiNode gn,boolean noe)throws Exception{
        return "FILE"+tdE(gn,noe); 
    }
    
    String myPath()throws Exception{
        return pp.docPath();
    }

}
