/*
 * AdminDict.java
 *
 * Created on 23 ��� 2007 �., 13:50
 * Auhor J.Feldman
 */

package j5feldman.proc.basement;
import java.util.*;
/**
 *
 * @author Jacob Feldman
 */
public class AdminDict {
    int lang = 0;
    Map<String,String> map = new TreeMap <String,String>();
    MyTask task = null;
    
    /** Creates a new instance of AdminDict */
    public AdminDict(int lang) {
        this.lang = lang;
        task = new MyTask();
        task.start();
    }
    
    void init(){
        map.put("KEY","����");
        map.put("FIELD","����");
        map.put("DOCUMENT","��������");
        map.put("PICTURE","��������");
        map.put("code","���");
        map.put("name","������������");
        map.put("inherited from","������������-��");
        map.put("reference","������");
        map.put("datatype","��� ������");
        
        map.put("final","����������");
        
        map.put("FTS-build","������");
        map.put("Last visit","���������� �����");
        map.put("Current start","������� �����");
        map.put("user","������������");
        map.put("schema","����");
        map.put("Support","���������-");
        map.put("Jacob Feldman","�������� �.�.");        
    }
    
    public String get(String x){
        switch(lang){
            case 1: return x;
            default: return map.get(x);
        }
    }
    
    class MyTask extends Thread{
        public void run(){
            yield();
            init();
        }
    }
}
