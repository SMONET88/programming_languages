/**
 * Interface that defines the data types for the SILLY language. 
 *   @author Dave Reed
 *   @version 1/20/26
 */
public interface DataValue extends Comparable<DataValue> {   
    public static enum Type { INTEGER, STRING, BOOLEAN, LIST }
    
    public Object getValue();
    public DataValue.Type getType();
    public String toString(); 
}