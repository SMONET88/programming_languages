import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class MemorySpace {

    private Stack<ScopeRecord> runtimeStack;
    private ArrayList<String> heapSpace;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.runtimeStack = new Stack<ScopeRecord>();
        this.runtimeStack.push(new ScopeRecord(null));
        this.heapSpace = new ArrayList<String>();
    }

    /**
     * Adds a new scope to the top of the runtime stack (linked to previous top).
     */
    public void beginScope() {
        this.runtimeStack.push(new ScopeRecord(this.runtimeStack.peek()));
    }

    /**
     * Removes the current scope from the top of the runtime stack.
     */
    public void endScope() {
        this.runtimeStack.pop();
    }

    /**
     * Declares a variable in local scope (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declareVariable(Token variable) {
        this.runtimeStack.peek().storeInScope(variable, null);
    }

    /**
     * Determines if a variable is already declared.
     * @param variable the variable to be found
     * @return true if it is declared and/or assigned; else, false
     */
    public boolean isDeclared(Token variable) {
        return (this.findScopeinStack(variable) != null);
    }

    /**
     * Stores a variable/value in the runtime stack.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void storeValue(Token variable, DataValue val) {
        this.findScopeinStack(variable).storeInScope(variable, val);
    }

    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */
    public DataValue lookupValue(Token variable) {
        return this.findScopeinStack(variable).lookupInScope(variable);
    }

    /**
     * Stores a string value in the heap (if not already stored), returns its index.
     * @param str the string being added
     * @return its index in the heap list
     */
    public int heapStore(String str) {
        for (int i = 0; i < heapSpace.size(); i++) {
            if (str.equals(heapSpace.get(i))) {
                return i;
            }
        }
        heapSpace.add(str);
        return heapSpace.size() - 1;
    }

    /**
     * Look up a string value in the heap.
     * @param index the index in the heap list
     * @return the string value stored at that index
     */
    public String heapLookup(int index) {
        return heapSpace.get(index);
    }

    /**
     * @param variable the variable being searched for
     * @returns true if the specified token is declared within the current scope
     */

    public boolean isLocal(Token variable) {
        if (this.runtimeStack.peek().declaredInScope(variable)) {
            return true;
        }

        return false;
    }

    /////////////////////////////////////////////////////////////////////////////

    /**
     * Locates the Scope in the stackSegment that contains the specified variable.
     * @param variable the variable being searched for
     * @return the Scope containing that variable
     */
    private ScopeRecord findScopeinStack(Token variable) {
        ScopeRecord stepper = this.runtimeStack.peek();
        while (stepper != null && !stepper.declaredInScope(variable)) {
            stepper = stepper.getParentScope();
        }
        return stepper;
    }
}
