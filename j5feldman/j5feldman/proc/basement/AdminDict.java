/*
 * AdminDict.java
 *
 * Created on 23 Май 2007 г., 13:50
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
        map.put("KEY","КЛЮЧ");
        map.put("FIELD","ПОЛЕ");
        map.put("DOCUMENT","ДОКУМЕНТ");
        map.put("PICTURE","КАРТИНКА");
        map.put("code","код");
        map.put("name","наименование");
        map.put("inherited from","унаследовано-от");
        map.put("reference","ссылка");
        map.put("datatype","тип данных");
        
        map.put("final","автономный");
        
        map.put("FTS-build","Версия");
        map.put("Last visit","Предыдущий сеанс");
        map.put("Current start","Текущий сеанс");
        map.put("user","пользователь");
        map.put("schema","база");
        map.put("Support","Поддержка-");
        map.put("Jacob Feldman","Фельдман Я.А.");        
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
