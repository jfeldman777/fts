/*
 * Upgrade.java
 *
 * Created on 25 Май 2007 г., 8:15
 * Auhor J.Feldman
 */

package j5feldman;

/**
 *
 * @author Jacob Feldman
 */
public class Upgrade {
    XPump pp;
    /** Creates a new instance of Upgrade */
    public Upgrade(XPump pp) {
        this.pp = pp;
    }
    
    void go()throws Exception{
        //go_5_2007();
        //go_6_2007();
        //go_61_2007();
    }
    
    void go_61_2007()throws Exception{ 
        String q = "alter table type drop column flash ";
        try{
            pp.exec(q);
        }catch(Exception e){}
    }
    
    void go_6_2007()throws Exception{
        String q = "alter table xuser add column toolbar int default -1";
        String q2= "alter table xuser add column buttons int default -1";
        String q3= "alter table xuser add column arrows int default -1";  
        try{
            pp.exec(q);
            pp.exec(q2);
            pp.exec(q3);    
        }catch(Exception e){}
    }
    
    void go_5_2007()throws Exception{
        String q = "alter table object add column fin boolean default false ";
        String q2 = "alter table type add column fin boolean default false ";
        try{
            pp.exec(q);
            pp.exec(q2);
        }catch(Exception e){}
        
    }
    
   void goTask()throws Exception{
        Thread t = new Thread(){
            public void run(){
                try{
                    yield();
                    go();///////////////////////////////////////////////////////////// 
                }catch(Exception e){}
            }
        }; 
        t.start();
    }
}
