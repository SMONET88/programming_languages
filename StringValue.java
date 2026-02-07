/**
 * Class that represents a String value.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class StringValue implements DataValue {
    private int address;
    
    /**
     * Constructs a String value.
     *   @param str the String being stored
     */
    public StringValue(String str) {
    	this.address = Interpreter.MEMORY.heapStore(str);
    }

    /**
     * Identifies the actual type of the value.
     *   @return Token.Type.STRING_VALUE
     */
    public DataValue.Type getType() {
        return DataValue.Type.STRING;
    }
    
    /**
     * Accesses the stored Boolean value.
     *   @return the Boolean value (as an Object)
     */
    public Object getValue() {
        return Interpreter.MEMORY.heapLookup(this.address);
    }

    /**
     * Converts the String value to a String.
     *   @return the stored String value (with double quotes)
     */
    public String toString() {
        return "\"" + this.getValue() + "\"";
    }
    
    /**
     * Comparison method for StringValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
        return ((String)this.getValue()).compareTo((String)other.getValue());
    }

}
