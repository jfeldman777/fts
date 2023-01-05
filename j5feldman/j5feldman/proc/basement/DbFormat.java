   package j5feldman.proc.basement;
    public enum DbFormat{
          INTEGER{
             public String setInit(){
                return "=0";
             } 
             public int getInt(){
               return 4;}},
          REAL{
            public String setInit(){
                return "=0";
             }                   
            public int getInt(){
               return 7;}},
          TEXT{
             public String setInit(){
                return "=''";
             }
             public int getInt(){
               return -1;}},
          BOOLEAN{
             public String setInit(){
                return "=0";
             }
             public int getInt(){
               return 16;}},
          TIMESTAMP{
             public String setInit(){
                return "=''";
             }
             public int getInt(){
               return 93;}},
          DATE{
             public String setInit(){
                return "=''";
             }
             public int getInt(){
               return 91;}},
          TIME{
             public String setInit(){
                return "=''";
             }
             public int getInt(){
               return 92;}};
       public abstract int getInt(); 
       public abstract String setInit();
      public static String getList(){
         String s="";
         for(DbFormat dbf:DbFormat.values()){
            s+="<option value="+dbf.getInt()+">"+dbf+"</option>\n";
         }
         return s;
      }
      
      public final static String[] formats = {
        "TIME","TIMESTAMP","DATE","BOOLEAN","TEXT","REAL","INTEGER"
      };
      
      public static String d2t(int d)throws Exception{
            switch(d){
                case 92: return "TIME";
                case 93: return "TIMESTAMP";
                case 91: return "DATE";
                case 16: return "BOOLEAN";
                case -1: return "TEXT";
                case  7: return "REAL";
                case  4: return "INTEGER";
                default : throw new Exception("unknown dt:"+d);
            } 
      }
      
      public static DbFormat d2f(int d)throws Exception{
            switch(d){
                case 92: return DbFormat.TIME;
                case 93: return DbFormat.TIMESTAMP;
                case 91: return DbFormat.DATE;
                case 16: return DbFormat.BOOLEAN;
                case -1: return DbFormat.TEXT;
                case  7: return DbFormat.REAL;
                case  4: return DbFormat.INTEGER;
                default : throw new Exception("unknown dt:"+d);
            } 
      }      
   }