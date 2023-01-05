/*
 * Profile.java
 *
 * Created on 28 Май 2007 г., 8:25
 * Auhor J.Feldman
 */

package j5feldman.profile;
import j5feldman.*;
import javax.servlet.http.*;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class Profile {
    int iUser;
    XPump pp;   
    int toolbar;
    int buttons;
    int arrows;
    /** Creates a new instance of Profile */
    public Profile(XPump pp)throws Exception {
        this.pp = pp;        
    }
    
    public void init()throws Exception{
        iUser = pp.meid();
        toolbar = pp.getN("select toolbar from xuser where id="+iUser);
        buttons = pp.getN("select buttons from xuser where id="+iUser);
        arrows = pp.getN("select arrows from xuser where id="+iUser);
    }  
    
    public void initTask()throws Exception{
        Thread t = new Thread(){
            public void run(){
                try{
                    yield();
                    init();/////////////////////////////////////////////////////////////
                }catch(Exception e){}
            }
        }; 
        t.start();
    } 
    
    public String toolbar(int t){
        String s = "";
        for(Buttons b:Buttons.values()){
            boolean tb = b.yes(toolbar);
            boolean yes= b.yes(buttons);
            if(tb) s+=b.img_src(pp.iLang,yes);
        }
        return s;
    }
    
    public void setToolbar(int t)throws Exception {
        String  q = "update xuser set toolbar="+ t +
                " where id="+iUser;
        pp.exec(q);
        toolbar = t;
    }
    
    public void setButtons(int t)throws Exception {
        String  q = "update xuser set buttons="+ t +
                " where id="+iUser;
        pp.exec(q);
        buttons = t;
    }
    
    public void setArrows(int t)throws Exception {
        String  q = "update xuser set arrows="+ t +
                " where id="+iUser;
        pp.exec(q);
        arrows = t;
    }
    
     public String toolbarAr(int id,int type,int io){
        String s = "";
        for(Buttons b:Buttons.values()){
           if(b.isArrow()&&b.yes(arrows)) 
               s+=b.barAr(pp.iLang,id,type,io);
               //s+=b.img_srcAr(pp.iLang);
        }
        return s;
    }
    
    public String toolbar(boolean shadow,boolean pseudo,boolean admin,
//            boolean flash,
            boolean root,
            boolean z,boolean lock){
        String s = "";
        for(Buttons b:Buttons.values()){
            if(shadow && b.not4shadow)continue;
            if(pseudo && b.not4pseudo)continue;
            if(!admin && b.adminOnly)continue;
//            if(!flash && b.flashOnly)continue;
            if(root && b.not4root)continue;
            if(z && b.not40)continue;
            if(lock &&b.not4lock)continue;
            if(b.isArrow())continue;
            if(!b.yes(toolbar))continue;
            boolean yes = b.yes(buttons);
            s+=b.img_src(pp.iLang,yes);
        }
        return s;
    }
    
    public String boxes(){
        String s = "";
        for(Buttons b:Buttons.values()){
            if(b.isArrow())continue;
            s+=b.img_src(pp.iLang,false)+
                    "<input type=checkbox " + (b.yes(toolbar)?" CHECKED ":"")+
                    " name=t" +b.mask()+
                    " title=toolbar>"+                    
                    "<input type=checkbox " + (b.yes(buttons)?" CHECKED ":"")+
                    " name=b" +b.mask()+
                    " title=ON>" +
                    "<br>";
        }
        return s;
    }
    
    public void saveBoxes(HttpServletRequest req)throws Exception {
        SortedMap<String,String> m = GuiTree.req2map(req);
        int T=0;
        int B=0;
        for(String key:m.keySet()){
            if(key.charAt(0)=='x')continue;
            if(key.charAt(0)=='y')continue;
            String sn = key.substring(1);
            int n = Integer.parseInt(sn);
            if(key.charAt(0)=='t')T|=n;
            if(key.charAt(0)=='b')B|=n;
        }
        
        setToolbar(T);
        setButtons(B);
    }
    
    public void saveBoxesAr(HttpServletRequest req)throws Exception {
        SortedMap<String,String> m = GuiTree.req2map(req);
        int T=0;
        for(String key:m.keySet()){
            if(key.charAt(0)=='x')continue;
            if(key.charAt(0)=='y')continue;
            String sn = key.substring(1);
            int n = Integer.parseInt(sn);
            if(key.charAt(0)=='a')T|=n;
        }
        
        setArrows(T);
    }
    
    public String boxesAr(){
        String s = "";
        for(Buttons b:Buttons.values()){
            if(!b.isArrow())continue;
            s+=b.img_srcAr(pp.iLang)+                 
                    "<input type=checkbox " + (b.yes(arrows)?" CHECKED ":"")+
                    " name=a" +b.mask()+
                    " title=toolbar>" +
                    "<br>";
        }
        return s;
    }    
    
    public String tab1(int id,boolean isShadow,boolean isPseudo,boolean isAdmin,
//            boolean isFlash,
            boolean isRoot,boolean isZero,boolean lock){
            String s = "";  
            for(Buttons b:Buttons.values()){
                if(b.isArrow())continue;
                if(isShadow && b.not4shadow) {//s+="/H/";
                continue;}
                if(isPseudo && b.not4pseudo) {//s+="/P/";
                continue;}
                if(!isAdmin && b.adminOnly) {//s+="/A/";
                continue;}
//                if(!isFlash && b.flashOnly) {//s+="/F/";
//                continue;}
                if(isRoot && b.not4root) {//s+="/R/";
                continue;}
                if(isZero && b.not40) {//s+="/Z/";
                continue;}
                if(lock && b.not4lock) {//s+="/L/";
                continue;}
                boolean vis = b.yes(toolbar)&&b.yes(buttons);
                if(b.yes(toolbar)) s+=b.td(style+pp.ER+"obj/",id,vis,pp.iLang);
            }
            
            return s;
    }
    public String tab2(int id,boolean isShadow,boolean isPseudo,boolean isAdmin,
//            boolean isFlash,
            boolean isRoot,boolean isZero,boolean lock,boolean blind)throws Exception{
            String s = "";  
            for(Buttons b:Buttons.values()){
                if(b.value()==0)continue;
                if(b.isArrow())continue;
                if(isShadow && b.not4shadow) {//s+="/H/";
                continue;}
                if(isPseudo && b.not4pseudo) {//s+="/P/";
                continue;}
                if(!isAdmin && b.adminOnly) {//s+="/A/";
                continue;}
//                if(!isFlash && b.flashOnly) {//s+="/F/";
//                continue;}
                if(isRoot && b.not4root) {//s+="/R/";
                continue;}
                if(isZero && b.not40) {//s+="/Z/";
                continue;}
                if(lock && b.not4lock) {//s+="/L/";
                continue;}
                boolean vis = b.yes(toolbar)&&b.yes(buttons);
                if(b.yes(toolbar)) s+=b.tr(style+pp.ER+"obj/",id,vis,blind,isAdmin,pp.iLang);
            }
            return s;
    }
    public String tab0(boolean admin,IGuiNode nn)throws Exception{
            String s = "";
            Buttons b = Buttons.Create;
            boolean vis = b.yes(toolbar)&&b.yes(buttons);
            if(b.yes(toolbar)) s+=b.tr0(style+pp.ER+"obj/",vis,admin,nn,pp); 
                return s;
    }   
    final static String style = "/feldman-root/style/";

}