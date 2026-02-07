import java.util.HashMap;

/**
 * Class that represents a scope record in the program execution.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class ScopeRecord {
    private HashMap<Token, DataValue> map;
    private ScopeRecord parentScope;
    
    /**
     * Constructs an empty scope record.
     * @param parent a reference to the parent scope record (null if no parent)
     */
    public ScopeRecord(ScopeRecord parent) {
    	this.map = new HashMap<Token, DataValue>();
    	this.parentScope = parent;
    }
    
    /**
     * Determines if a variable is declared in this scope record.
     * @param variable the variable to check
     * @return true if variable is declared (i.e., a key in map); else, false
     */
    public boolean declaredInScope(Token variable) {
    	return this.map.containsKey(variable);
    }
    
    /**
     * Determines the value associated with a variable in this scope record.
     *   @param variable the variable to look up
     *   @return the value associated with that variable (null if not found)
     */      
    public DataValue lookupInScope(Token variable) {
        return this.map.get(variable);
    }
    
    /**
     * Stores a variable/value in this scope record.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeInScope(Token variable, DataValue val)  {
        this.map.put(variable, val);
    }
    
    /**
     * Accesses the parent scope record.
     * @return the parent scope record (or null if no parent)
     */
    public ScopeRecord getParentScope() {
    	return this.parentScope;
    }
}
