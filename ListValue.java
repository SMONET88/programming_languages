import java.util.ArrayList;

/**
 * Class that represents a list value.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class ListValue implements DataValue {
    private ArrayList<DataValue> value;

    /**
     * Constructs a list value.
     *   @param exprs the list expressions being stored
     */
    public ListValue(ArrayList<DataValue> exprs) {
    	this.value = new ArrayList<DataValue>(exprs);
    }
    
    /**
     * Accesses the stored list value.
     *   @return the list value (as an Object)
     */
    public Object getValue() {
        return this.value;
    }  

    /**
     * Identifies the actual type of the value.
     *   @return Token.Type.LIST
     */
    public DataValue.Type getType() {
        return DataValue.Type.LIST;
    }

    /**
     * Converts the list value to a String.
     *   @return a String representation of the list value
     */
    public String toString() {
    	String message = "[";
    	for (DataValue v : this.value) {
    		message += v + " ";
    	}
        return message.trim() + "]";
    }

    /**
     * Comparison method for ListValues.
     *   @param other the value being compared with
     *   @return negative if <, 0 if ==, positive if >
     */
    public int compareTo(DataValue other) {
         return (this.getValue().toString()).compareTo(other.getValue().toString());
    }
}