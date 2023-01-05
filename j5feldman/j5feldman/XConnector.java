/*
 * XConnector.java
 *
 * Created on 24 Ќо€брь 2004 г., 13:53
 */

package j5feldman;
import j5feldman.proc.basement.Result;
import java.sql.*;
import java.util.*;
import java.io.*;   
import j5feldman.ex.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
    public class XConnector {
//        protected String qtLog = "";
//        boolean sqlOut=false;
//        boolean sqlOutIn=false;
//        void setQTlog(){
//            qtLog = "";
//            sqlOutIn=true;
//        }

//        void addQTlog(int n,String Q,long L){
//            qtLog+=L+"="+Q+"//"+n+"<br>";
//        }
//        
//        String getQTlog(){
//            sqlOutIn = false;
//            return sqlOut?qtLog:"";
//        }        
    /** Creates a new instance of XConnector */
       public XConnector() {
      }
      public Connection con;
      public Connection con2;
      public String schema;
       public int getNinfo(String q)throws Exception {
           return getN(con2,q);
       }
       public int getN(String q)throws Exception {
           return getN(con,q);
       }       
       public int getN(Connection cc,String q)throws Exception {
         Result r=new Result();
         int i=-1;
         try{
            select(r,0,cc,q);
            r.rs.next();
            i = r.rs.getInt(1);      
         }catch(Exception e){}
         finally{r.close();}
         return i; 
      }
       
       public List<Integer> getNN(String q)throws Exception{
         List<Integer> ar = new ArrayList<Integer>();
         Result r=new Result();
         int i=0;
         try{
            select(r,4,q);
            while(r.rs.next()){
               i = r.rs.getInt(1);
               ar.add(i);
            }       
         }catch(Exception e){}
         finally{r.close();}
         return ar; 
      } 
       public List<Integer> getNNd(String q,java.sql.Date d)throws Exception{
         List<Integer> ar = new ArrayList<Integer>();
         Result r=new Result();
         int i=0;
         try{
            selectDate(r,41,q,d);//(Result bs,int n,Connection cc,String q,java.sql.Date d)
            while(r.rs.next()){
               i = r.rs.getInt(1);
               ar.add(i);
            }       
         }catch(Exception e){}
         finally{r.close();}
         return ar; 
      } 
       public int getNdi(String q,java.sql.Date d,int ii)throws Exception{
         Result r=new Result();
         int i=0;
         try{
            selectDateInt(r,41,q,d,ii);//(Result bs,int n,Connection cc,String q,java.sql.Date d)
            r.rs.next();
            i = r.rs.getInt(1);    
         }//catch(Exception e){}
         finally{r.close();}
         return i; 
      }        
       public java.sql.Date getD(String q)throws Exception {
         Result r=new Result();
         java.sql.Date i=null;
         try{
            select(r,5,q);
            r.rs.next();
            i = r.rs.getDate(1);        
         }catch(Exception e){}
         finally{r.close();}
         return i; 
      }
   
       
    public boolean getB(String q) throws Exception{
         Result r=new Result();
         boolean i=false;
         try{
            select(r,51,q);
            r.rs.next();
            i = r.rs.getBoolean(1);        
         }catch(Exception e){}
         finally{r.close();}      
        return i;
    }       
       public float getF(String q)throws Exception {
         Result r=new Result();
         float i=-1;
         try{
            select(r,6,q);
            r.rs.next();
            i = r.rs.getFloat(1);        
         }catch(Exception e){}
         finally{r.close();}
         return i; 
      }
       public List<Float> getFF(String q)throws Exception{
         List<Float> ar = new ArrayList<Float>();
         Result r=new Result();
         float i=0;
         try{
            select(r,7,q);
            while(r.rs.next()){
               i = r.rs.getFloat(1);
               ar.add(i);
            }       
         }catch(Exception e){}
         finally{r.close();}
         return ar; 
      }  
       
       public String getSinfo(String q)throws Exception{
            return getS(con2,q);
       }
       
       public String getS(String q)throws Exception{
            return getS(con,q);
       }
       
       public String getS(Connection cc,String q)throws Exception{
         Result r=new Result();
         String s="";
         try{
            select(r,0,cc,q);
            r.rs.next();
            s = r.rs.getString(1);        
         }catch(Exception e){}
         finally{r.close();}
         return s; 
      }
       public List<String> getSSinfo(String q)throws Exception{
            return getSS(con2,q);
       }
       public List<String> getSS(String q)throws Exception{
            return getSS(con,q);
       }
       public List<String> getSS(Connection cc,String q)throws Exception{
         Result r=new Result();      
         String s="";
         List<String> ar = new ArrayList<String>();
         try{
            select(r,0,con,q);
            while(r.rs.next()){
               s = r.rs.getString(1);
               ar.add(s);
            }      
         }catch(Exception e){}
         finally{r.close();}
         return ar; 
      }
       public Map<String,String> getMap(String q)throws Exception{
         Result r=new Result();
         String s1="",s2="";
         Map<String,String> ar = new HashMap<String,String>();
         try{
            select(r,8,q);
            while(r.rs.next()){
               s1 = r.rs.getString(1);
               s2 = r.rs.getString(2);
               ar.put(s1,s2);
            }      
         }catch(Exception e){}
         finally{r.close();}
         return ar; 
      }
       
       public List<String> getSS2(String q)throws Exception{
         Result r=new Result();
         String s="";
         List<String> ar = new ArrayList<String>();
         try{
            select(r,9,q);
            while(r.rs.next()){
               s+= r.rs.getString(1)+"//";

            }      
         }
         finally{r.close();}
         if(true)throw new Exception(s);
         return ar; 
      }   
       public void connect(String dbconfig,String schema) throws Exception{
        connect(dbconfig,schema,false);
       }
       public void connect(String dbconfig,String schema,boolean bNew) throws Exception{
         try {
            Class.forName("com.mckoi.JDBCDriver").newInstance();
         }
             catch (Exception e) {
               String s;  
               System.out.println(s=
                  "Unable to register the JDBC Driver.\n" +
                  "Make sure the JDBC driver is in the\n" +
                  "classpath.\n");
               throw new Exception(s);
            }
         
         String url = "jdbc:mckoi:local://"+dbconfig;
         String username = "jacob";
         String password = "feldman";
      Statement stmt=null;
      Statement stmt2=null;
         try {
            con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(true);
            stmt = con.createStatement();
            this.schema=schema;
            if(bNew)stmt.executeUpdate("create schema "+schema);
            stmt.executeUpdate("set schema "+schema);
            
                con2 = DriverManager.getConnection(url, username, password);
                con2.setAutoCommit(true);
                stmt2 = con2.createStatement();                
                stmt2.executeUpdate("set schema SYS_INFO");                

         }catch (SQLException e) {
               String s;
               System.out.println(s = 
                  "Unable to make a connection to the database.\n" +
                  "The reason: " + e.getMessage());
               throw new Exception(s);
            }finally{
                if(stmt!=null)stmt.close();
                if(stmt2!=null)stmt2.close();
            }
         
         //long usa = System.currentTimeMillis();
         //Long usa2 = new Long("1150603530795"); 
         //if(usa > usa2)finalize();    //18-june-2006         
      }
   
    /**exec + select*/
       public int exec(String q)throws Exception{
           int N = -1;
           Statement stmt = null;
           if(q!=null && !q.trim().equals(""))  
           try{
               stmt = con.createStatement();
               N = stmt.executeUpdate(q);              
           }catch(Exception e){
               if(e.getMessage()!=null&&
                       e.getMessage().indexOf("uq_xuser")>0)throw new ExPwdUq();
               if(e.getMessage()!=null&&
                       e.getMessage().indexOf("uq_object")>0)throw new ExParentCode();
            throw new Exception("//"+q+"//"+e.getMessage());
           }finally{if(stmt!=null)stmt.close();}
           return N;
      }
       
       public int exec0(String q)throws Exception{
            if(true)throw new Exception("//"+q+"//");
            return 0;
      }  
       
       public void exec(String[] qq)throws Exception{
         List<String> ar = Arrays.asList(qq);
         try{
            exec(ar);
         }catch(Exception e){
            if(e.getMessage()!=null&&
                    e.getMessage().indexOf("fk_type_parent2")>0)
                throw new ExSuperType();
            else throw e;
         }
      }
       public void exec3(List<String> qq)throws Exception{
           String s = "";
            for(int i=0;i<qq.size();i++){
                System.out.println(""+i+":"+qq.get(i));                
            }
       }        
       
       public void exec2(List<String> qq)throws Exception{
           String s = "";
            for(int i=0;i<qq.size();i++){
                String q=qq.get(i);
                s+="<br> \n("+i+")" +q+ ";\n ";
            }
            throw new Exception(s);
       }
       public void exec1(List<String> qq)throws Exception{
         Statement stmt = null;
           try{
            stmt = con.createStatement();
            for(String q:qq){
               try{
               if(q!=null && !q.trim().equals(""))    
                     stmt.executeUpdate(q);    
               }catch(Exception e){
                    System.out.println(":?:>"+q);
               }
            }
         }finally{if(stmt!=null)stmt.close();}
      } 
       public void exec(List<String> qq)throws Exception{
         String qx="";
         Statement stmt=null;
         try{
            con.setAutoCommit(false);
            for(String q:qq){
               qx=q;
               if(q!=null && !q.trim().equals("")){       
                     stmt = con.createStatement();                   
                     stmt.executeUpdate(q);

               }
            }
            con.commit();
            con.setAutoCommit(true); 
         }
             catch(SQLException e){
               con.rollback();
               String s = e.getMessage();
               if(s.indexOf("uq_xuser)")>0)
                  throw new ExPwdUq(); 
               if(s.indexOf("uq_object")>0)
                  throw new ExParentCode();
               throw new SQLException(qx + e.getMessage());
            }finally{if(stmt!=null)stmt.close();}
      } 
       
       public void select(Result bs,int n,Connection cc,String q) throws Exception {
           long L1 = System.currentTimeMillis();
            bs.stmt = cc.prepareStatement(q);
            bs.rs = bs.stmt.executeQuery();
//            if(sqlOut && sqlOutIn){
//                long L2 = System.currentTimeMillis();   
//                addQTlog(n,q,L2-L1);
//            }
      }
       public void selectDate(Result bs,int n,String q,java.sql.Date d) throws Exception {
           long L1 = System.currentTimeMillis();
            bs.stmt = con.prepareStatement(q);
            bs.stmt.setDate(1,d);
            bs.rs = bs.stmt.executeQuery();
//            if(sqlOut && sqlOutIn){
//                long L2 = System.currentTimeMillis();   
//                addQTlog(n,q,L2-L1);
//            }
      }
       
       public void selectDateInt(Result bs,int n,String q,java.sql.Date d,int i) throws Exception {
           long L1 = System.currentTimeMillis();
            bs.stmt = con.prepareStatement(q);
            bs.stmt.setDate(1,d);
            bs.stmt.setInt(2,i);
            bs.rs = bs.stmt.executeQuery();
//            if(sqlOut && sqlOutIn){
//                long L2 = System.currentTimeMillis();   
//                addQTlog(n,q,L2-L1);
//            }
      }       
      public void select(Result rr,int n,String q) throws Exception {
        select(rr,n,con,q);
      } 
       public void selectInfo(Result rr,int n,String q) throws Exception {
        select(rr,n,con2,q);
      }
       
       
    protected void finalize(){
        try{
            if(con!=null)con.close();
        }catch(Exception e){}
    }
   }
