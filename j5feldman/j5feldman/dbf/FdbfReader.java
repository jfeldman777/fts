//Jacob Feldman, 2007

package j5feldman.dbf;
import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class FdbfReader{
    private MyReader reader=null;
    public List<DbfFieldHeader> fieldsList;
    private char nextRecordChars[];
    private Charset charset = null;
    int NN;
    public DbfHeader header=null;
    
    public FdbfReader(String s,Charset charset)throws Exception{
        nextRecordChars = null;
        this.charset = charset; 
        NN=0;
            reader = new MyReader(new DataInputStream(new FileInputStream(s)),charset);  
            readFirstRecord();
    }

    private void readFirstRecord()throws Exception{                    
            header = new DbfHeader(reader);            
            fieldsList = new ArrayList<DbfFieldHeader>();
            int sumFieldsLenPlus1 = 1;
            do{
                
                DbfFieldHeader fh = this.getFieldHeader(reader);
                if(fh==null)break;
                else fieldsList.add(fh);
            }while(true);            

            int recLen = 1;
            for(DbfFieldHeader h:fieldsList){
                recLen+=h.len();
            }
            
            nextRecordChars = new char[recLen];          
            int x = reader.read(nextRecordChars); 
            if(x<0)throw new Exception(" empty dbf-file ");
    }


    public boolean hasNextRecord(){
        return nextRecordChars != null;
    }

    public String[] nextRecord()throws Exception{
        if(!hasNextRecord()) throw new Exception();
        String oFields[] = new String[fieldsList.size()];
        int nFieldsLenPlus1 = 1;
        for(int j = 0; j < oFields.length; j++)
        {
            int len = fieldsList.get(j).len();             
            String s = new String(nextRecordChars, nFieldsLenPlus1, len);                 
            
            oFields[j] = s;
            nFieldsLenPlus1 += fieldsList.get(j).len();
        }
                
       
            int ix = reader.read(nextRecordChars);
            if(ix<0){
                nextRecordChars = null;
            }else{
                String s = new String(nextRecordChars);
            }////////////////////////////////////////////////////

        return oFields;
    }

    public void close()throws Exception{
        nextRecordChars = null;
        reader.close();
    }
    public int getFieldCount(){
        return fieldsList.size();
    }

    
    public class DbfHeader{
        char[] versionNumber = new char[1];
        char[] lastUpdate = new char[3];
        char[] nRecords = new char[4];
        char[] headerLen = new char[2];
        char[] recordLen = new char[2];
        char[] reserved = new char[20];
        DbfHeader(MyReader r)throws Exception{
            r.read(versionNumber,"version");
            r.read(lastUpdate,"lastUpdate");
            r.read(nRecords,"nRecords");
            r.read(headerLen,"headerLen");
            r.read(recordLen,"recordLen");
            r.read(reserved,"===");
        }
        
    }
    
    DbfFieldHeader getFieldHeader(MyReader r)throws Exception{
            char[] nx = new char[1];
            r.read(nx,"name");          
            
            int x = (int)(nx[0]);
                        
            if(x==13)return null;
            
            return new DbfFieldHeader(r,nx[0]);
    }
    
    public class DbfFieldHeader{
        char[] name = new char[11];
        char[] type = new char[1];
        char[] addr = new char[4];
        char[] len = new char[1];
        char[] decCount = new char[1];
        char[] reserved = new char[14];
 
        int len(){
            return (int)len[0];
        }
        
        DbfFieldHeader(MyReader r,char c1) throws Exception{
            //name[0]=c1;
            r.read(c1,name,"name");   
            r.read(type,"type");
            r.read(addr,"addr");
            r.read(len,"len");
            r.read(decCount,"decCount");
            r.read(reserved,"===");
            String s = new String(name);
        }
       
        
        public String getName(){
            return new String(name).trim();
        }
    }
    
    class MyReader extends InputStreamReader{
        MyReader(InputStream in, Charset cs){
            super(in,cs);
        }
        
        public int read(char c,char[] cbuf,String name)throws Exception{
            cbuf[0]=c;
            int i = super.read(cbuf,1,cbuf.length-1);
//            System.out.println("//"+name+":"+new String(cbuf));
            return i+1;
        }
        
        public int read(char[] cbuf,String name)throws Exception{
            int i = super.read(cbuf);
//            System.out.println("//"+name+":"+new String(cbuf));
            return i;
        }
    }
}
