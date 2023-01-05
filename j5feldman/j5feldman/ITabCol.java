/*
 * ITabCol.java
 *
 * Created on 9 ƒекабрь 2004 г., 8:11
 */

package j5feldman;
import java.util.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public interface ITabCol {
    String typeEditForm()throws Exception;    
    String getRus()throws Exception;
    void setRus(String rusname)throws Exception; 
    void  setUnique(boolean b)throws Exception;
    boolean getUnique()throws Exception;
    boolean keyCol()throws Exception;
    void setKey()throws Exception;  
    String baseForm()throws Exception;
    String baseForm(boolean b)throws Exception;
    String name();
    
    String fvalue(int oid,String val)throws Exception;
    String fvalue(GuiTree.GuiNode cn,String val,boolean sh)throws Exception;
    String fvalue(GuiTree.GuiNode cn,String val,boolean sh,boolean noe)throws Exception;    
    String ftype(GuiTree.GuiNode gn,boolean noe)throws Exception;
    void setValue(String val)throws Exception;
    void setValue(int id,String val)throws Exception;

    String value();
    String baseValue(String val)throws Exception;
    List<Integer> kvars()throws Exception;

    String addCell()throws Exception;
    String editCell()throws Exception;
    void setCase()throws Exception;
    String linePoint(String rc,int k)throws Exception;

    String tabcol();    
    String getUform()throws Exception;
    void drop()throws Exception;
    void addTC(String name,String more)throws Exception; 
    int nMix()throws Exception;
    String dtName()throws Exception;
    String refTab()throws Exception; 
    boolean hasRus()throws Exception;
    String value(int id)throws Exception;  
    boolean isBoolean()throws Exception;
    boolean isKey();    
    boolean isAuto(); 
    boolean isShadow()throws Exception;    
    boolean isHidden();
    int dtype()throws Exception;
    boolean readOnly(GuiTree.GuiNode gn);
    
    void setRefhh(boolean b);
    void setRefhhBlind(boolean b);    
}
