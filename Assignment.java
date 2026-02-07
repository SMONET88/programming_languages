/**
 * Derived class that represents an assignment statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class Assignment extends Statement {
    private Token vbl;
    private Expression expr;
    
    /**
     * Reads in a assignment statement from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Assignment(TokenStream input) throws Exception {
        this.vbl = input.next();
        if (this.vbl.getType() != Token.Type.IDENTIFIER) {
	        throw new Exception("SYNTAX ERROR: Illegal lhs of assignment statement (" + this.vbl + ")");
        } 
        
        if (!input.next().toString().equals("gets")) {
	        throw new Exception("SYNTAX ERROR: Malformed assignment statement (expecting 'gets')");
        } 

        this.expr = new Expression(input);
    }
    
    /**
     * Executes the current assignment statement.
     */
    public Statement.Status execute() throws Exception {
    	if (!Interpreter.MEMORY.isDeclared(this.vbl)) {
    		throw new Exception("RUNTIME ERROR: " + this.vbl + " is not declared.");
    	} 
        Interpreter.MEMORY.storeValue(this.vbl, this.expr.evaluate());
        
        return Statement.Status.OK;
    }
    
    /**
     * Converts the current assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return this.vbl + " gets " + this.expr;
    }
}
