/*
 * IGuiNode.java
 *
 * Created on 2 ƒекабрь 2004 г., 8:18
 */

package j5feldman;
import j5feldman.dbf.DbfImport;
import javax.servlet.http.*;
import java.util.*;
import j5feldman.proc.basement.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public interface IGuiNode {
    String codeName();
    int id();
    String name();
    String code();
    int type();
    boolean isPseudo();
    boolean isShadow();
    String typeCode();
    String typeImgCode2()throws Exception;     
    String typeImgCode()throws Exception;    
    String typeImgCode(XPump pp)throws Exception;
    String face();
    String faceName();    
    String innerLBtypes()throws Exception;
    
    boolean isAbc();
    void setAbc(boolean val)throws Exception;

    boolean isBlind();
    void setBlind(boolean val)throws Exception;    
    
    String getFunctionDesc()throws Exception;
    String boxView()throws Exception;
    String boxView(boolean noe)throws Exception;
    String boxTypeView()throws Exception;
    
    String editForm()throws Exception;
    String editFormShadow()throws Exception;    

    String addForm(String typecode)throws Exception;
    String desForm()throws Exception;

    void doUpdateLine(String tabcol,String val,int dtype)throws Exception;    
    void doUpdate(HttpServletRequest req)throws Exception;
    void doInsert(HttpServletRequest req)throws Exception;
    void doInsert(SortedMap<String,String> map)throws Exception; 
    
    String showIcons()throws Exception;
    void setIcon(String icon)throws Exception;
    String baseForm()throws Exception;
    void createType(String code,String name,String inh,boolean abstr,String icon)throws Exception;
    void setName(String name)throws Exception;
    String fpath(String tab,String col,char tctype)throws Exception;
    int idOrigin();
    int idOrigin(boolean yes);
    String baseView()throws Exception;
    boolean isAbstr()throws Exception;
    void setPseudo();
    
    String lastUpdate();
    List<IGuiNode> pathUp()throws Exception;
    int num();
    boolean hasChildren();
    boolean isType();
    FTree getFT();   
    String exportBaseVal()throws Exception;

    String saveWxml(boolean show)throws Exception;
    DbfImport getDbfImport(int parent,String fname)throws Exception;
//    void setWTpath(String path)throws Exception;
//    String getWTpath()throws Exception;    
}
