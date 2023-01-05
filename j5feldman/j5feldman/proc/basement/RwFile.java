/*
 * RwFile.java
 *
 * Created on 10 Август 2006 г., 8:39
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.charset.*;
import j5feldman.ex.*;
import j5feldman.*;
/**
 *
 * @author Jacob Feldman
 */
public class RwFile {
    final static String encUtf="UTF-8";    
    final static String enc1251="Windows-1251";       
    /** Creates a new instance of RwFile */
    public RwFile() {
    }
    public void write(String qq,File f)throws Exception{
        if(f.exists())f.delete();            
        f.createNewFile();
        BufferedWriter bw=null;
        bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f),Charset.forName(encUtf)));
        bw.write(qq);

        bw.close();
    }
    public void write(String qq,String path)throws Exception{
        File f = new File(path);
        write(qq,f);
    }
      public String read(File f,boolean utf)throws Exception{
        String buf;
        String s="";
        BufferedReader br=null;
        String encoding = utf?encUtf:enc1251;
        br = new BufferedReader(new InputStreamReader(
                new FileInputStream(f),
                Charset.forName(encoding)));
        while(null!=(buf=br.readLine())){
            s+=buf+"\n";
        }
        return s;
    }
      
      public String read(String path,boolean utf)throws Exception{
          File f = new File(path);
          return  read(f,utf);
    }

      public static void main(String[] args){
        SortedMap<String,Charset> m = Charset.availableCharsets();
        int N=0;
        for(String s:m.keySet()){
            N++;
            System.out.println("/"+N+":"+s);
        }
      }
}
