/*
 * MyThread.java
 *
 * Created on 21 Март 2005 г., 8:59
 */

package j5feldman.boo;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author Jacob Feldman
 */
public class MyThread extends Thread{
    App app;
    int N;
    long L;
    URL url;
    long Lsum;
    long Lmax;
    String read;
    /** Creates a new instance of MyThread */
    public MyThread(App app,String read) {
        this.app = app;
        this.read = read;
        System.out.println("::"+read);
        N = 0;
        L = 0;
        Lsum = 0;
        String u = app.confFile.getAbsolutePath().replace('\\','/');
        try{
            url = new URL("http://localhost:8080/fel3/obj/do/boo.jsp?dbconfig="+
                    u+"&schema="+app.schemaName+
                    (read==null?"":"&read=on")
                    );
        }catch(Exception e){
                JOptionPane.showMessageDialog(app,"url exception");
        }
    }
    
    public void run(){
        while(app.go){
            long L1 = System.currentTimeMillis();
            app.setText1(""+(N++));
            int LL=0;
            InputStream stream;
            try{
                stream = url.openStream();
                LL = stream.read();
            }catch(Exception e){
                app.go = false;
                JOptionPane.showInputDialog(app,"???",url);
            }
            
            long L2 = System.currentTimeMillis();
            L = L2-L1;
            Lmax = Math.max(Lmax,L);
            Lsum+=L;
            app.setText2(""+L);
            app.setText3(""+Lmax);
            app.setText4(""+Lsum/N);
            if(read!=null)return;
        }
    }    

}
