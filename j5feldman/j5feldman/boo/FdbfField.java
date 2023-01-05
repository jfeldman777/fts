//Jacob Feldman 2007

package j5feldman.boo;
import java.text.*;
import java.util.Date;
import java.io.*;

public class FdbfField{
    
//    public FdbfField getDbfField(InputStreamReader r)throws Exception{
//        char[] cbuf = new char[32];
//        r.read(cbuf);
//        
//    } 

    public FdbfField(String s, char cFieldType, int iFieldLen, int jDecCount)
        throws JDBFException
    {
        if(s.length() > 10)
            throw new JDBFException("The field name is more than 10 characters long: " + s);
        if(cFieldType != 'C' && cFieldType != 'N' && cFieldType != 'L' && cFieldType != 'D' && cFieldType != 'F')
            throw new JDBFException("The field type is not a valid. Got: " + cFieldType);
        if(iFieldLen < 1)
            throw new JDBFException("The field length should be a positive integer. Got: " + iFieldLen);
        if(cFieldType == 'C' && iFieldLen >= 254)
            throw new JDBFException("The field length should be less than 254 characters for character fields. Got: " + iFieldLen);
        if(cFieldType == 'N' && iFieldLen >= 21)
            throw new JDBFException("The field length should be less than 21 digits for numeric fields. Got: " + iFieldLen);
        if(cFieldType == 'L' && iFieldLen != 1)
            throw new JDBFException("The field length should be 1 characater for logical fields. Got: " + iFieldLen);
        if(cFieldType == 'D' && iFieldLen != 8)
            throw new JDBFException("The field length should be 8 characaters for date fields. Got: " + iFieldLen);
        if(cFieldType == 'F' && iFieldLen >= 21)
            throw new JDBFException("The field length should be less than 21 digits for floating point fields. Got: " + iFieldLen);
        if(jDecCount < 0)
            throw new JDBFException("The field decimal count should not be a negative integer. Got: " + jDecCount);
        if((cFieldType == 'C' || cFieldType == 'L' || cFieldType == 'D') && jDecCount != 0)
            throw new JDBFException("The field decimal count should be 0 for character, logical, and date fields. Got: " + jDecCount);
        if(jDecCount > iFieldLen - 1){
            throw new JDBFException("The field decimal count should be less than the length - 1. Got: " + jDecCount);
        }else{
            name = s;
            type = cFieldType;
            length = iFieldLen;
            decimalCount = jDecCount;
            return;
        }
    }

    public String getName(){
        return name;
    }

    public char getType(){
        return type;
    }

    public int getLength(){
        return length;
    }

    public int getDecimalCount(){
        return decimalCount;
    }

    public String format(Object obj)throws JDBFException{
        if(type == 'N' || type == 'F')
        {
            if(obj == null)
                obj = new Double(0.0D);
            if(obj instanceof Number)
            {
                Number number = (Number)obj;
                StringBuffer stringbuffer = new StringBuffer(getLength());
                for(int i = 0; i < getLength(); i++)
                    stringbuffer.append("#");

                if(getDecimalCount() > 0)
                    stringbuffer.setCharAt(getLength() - getDecimalCount() - 1, '.');
                DecimalFormat decimalformat = new DecimalFormat(stringbuffer.toString());
                String s1 = decimalformat.format(number);
                int k = getLength() - s1.length();
                if(k < 0)
                    throw new JDBFException("Value " + number + " cannot fit in pattern: '" + stringbuffer + "'.");
                StringBuffer stringbuffer2 = new StringBuffer(k);
                for(int l = 0; l < k; l++)
                    stringbuffer2.append(" ");

                return stringbuffer2 + s1;
            } else
            {
                throw new JDBFException("Expected a Number, got " + obj.getClass() + ".");
            }
        }
        if(type == 'C')
        {
            if(obj == null)
                obj = "";
            if(obj instanceof String)
            {
                String s = (String)obj;
                if(s.length() > getLength())
                    throw new JDBFException("'" + obj + "' is longer than " + getLength() + " characters.");
                StringBuffer stringbuffer1 = new StringBuffer(getLength() - s.length());
                for(int j = 0; j < getLength() - s.length(); j++)
                    stringbuffer1.append(' ');

                return s + stringbuffer1;
            } else
            {
                throw new JDBFException("Expected a String, got " + obj.getClass() + ".");
            }
        }
        if(type == 'L')
        {
            if(obj == null)
                obj = new Boolean(false);
            if(obj instanceof Boolean)
            {
                Boolean boolean1 = (Boolean)obj;
                return boolean1.booleanValue() ? "Y" : "N";
            } else
            {
                throw new JDBFException("Expected a Boolean, got " + obj.getClass() + ".");
            }
        }
        if(type == 'D')
        {
            if(obj == null)
                obj = new Date();
            if(obj instanceof Date)
            {
                Date date = (Date)obj;
                SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
                return simpledateformat.format(date);
            } else
            {
                throw new JDBFException("Expected a Date, got " + obj.getClass() + ".");
            }
        } else
        {
            throw new JDBFException("Unrecognized JDBFField type: " + type);
        }
    }

    public Object parse(String s)throws JDBFException{
        s = s.trim();
        if(type == 'N' || type == 'F'){
            if(s.equals(""))s = "0";
            try{
                if(getDecimalCount() == 0)return new Long(s);
                else return new Double(s);
            }catch(NumberFormatException nfex){
                throw new JDBFException(nfex);
            }
        }
        if(type == 'C')
            return s;
        if(type == 'L'){
            if(s.equals("Y") || s.equals("y") || s.equals("T") || s.equals("t"))
                return new Boolean(true);
            if(s.equals("N") || s.equals("n") || s.equals("F") || s.equals("f"))
                return new Boolean(false);
            else
                throw new JDBFException("Unrecognized value for logical field: " + s);
        }
        if(type == 'D'){
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            try{
                if("".equals(s))return null;
                else return simpledateformat.parse(s);
            }
            catch(ParseException pex){
                throw new JDBFException(pex);
            }
        }else{
            throw new JDBFException("Unrecognized JDBFField type: " + type);
        }
    }

    public String toString(){
        return name;
    }

    private String name;
    private char type;
    private int length;
    private int decimalCount;
}
