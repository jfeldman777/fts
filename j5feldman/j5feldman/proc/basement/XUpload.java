/*
 * XUpload.java
 *
 * Created on 4 04 2003 , 21:54
 */

package j5feldman.proc.basement;
import j5feldman.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
//import com.oreilly.servlet.multipart.*;
/** real upload
 * @author Jacob Feldman/‘ельдман яков јдольфович
 */
public class XUpload {
    XPump pp;
    String ppPath;
    boolean isDoc=false;
    /** file to write */    
    private File dir;    
    public XUpload(XPump pp) {
        this.pp=pp;
    }
    public XUpload() {
    }
    public XUpload(XPump pp,String ppPath) {
        this.pp=pp;
        this.ppPath = ppPath;
        File parent = new File(ppPath);
        if(!parent.exists())parent.mkdirs();
    }
    
    public long save(HttpServletRequest request)throws Exception{
        DiskFileUpload dfu = new DiskFileUpload();
        dfu.setSizeMax(500*1024);
        dfu.setSizeThreshold(500*1024);
   
        long L = -1;

        List fileItems = dfu.parseRequest(request);
        Iterator itr = fileItems.iterator();

        while(itr.hasNext()) {
          FileItem fi = (FileItem)itr.next();
          if(!fi.isFormField()) {
            L=fi.getSize();
            fi.write(dir);
          }
          else {
            String name = fi.getFieldName();
            if(name==null){}else if(name.equals("path")){
               dir = new File(fi.getString());
            }
          }
        }

        return L;
    } 
//    final static String encUtf="UTF-8";    
//    final static String enc1251="Windows-1251";      
//    String get(HttpServletRequest request)throws Exception{
//        String enc = enc1251;
//        String s = "";
//        DiskFileUpload dfu = new DiskFileUpload();
//        dfu.setSizeMax(500*1024);
//        dfu.setSizeThreshold(500*1024);
//   
//        long L = -1;
//
//        List fileItems = dfu.parseRequest(request);
//        Iterator itr = fileItems.iterator();
//
//        while(itr.hasNext()) {
//          FileItem fi = (FileItem)itr.next();
//          if(!fi.isFormField()) {
//              s+=fi.getString(enc);
//          }
//          else {
//            String name = fi.getFieldName();
//            if(name==null){}else if(name.equals("utf")){
//                enc = encUtf;
//            }
//          }
//        }
//
//        return s;
//    } 
    public void deleteU(String path)throws Exception{
        File dir = new File(path); 
        dir.delete();
    }   
    
}
