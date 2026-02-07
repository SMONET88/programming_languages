import java.util.ArrayList;

/**
 * Class that represents a control statement body (i.e., a sequence of statements).
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class Body {
    private ArrayList<Statement> stmts;
    
    /**
     * Constructs a a body of statements.
     *   param s the list of statements
     */
    public Body(ArrayList<Statement> s) {
    	this.stmts = new ArrayList<Statement>(s);
    }
    
    /**
     * Exexcutes the body statements in order.
     *   @return true if an exit occured, else fals
     */
    public Statement.Status execute() throws Exception {
    	Interpreter.MEMORY.beginScope();
    	for (Statement s: this.stmts) {
    		s.execute();
    	}
    	Interpreter.MEMORY.endScope();
    	
    	return Statement.Status.OK;
    }
    
    /**
     * Converts the body into a String.
     *   @return the string representation
     */
    public String toString() {
    	String msg = "";
    	for (Statement s : this.stmts) {
    		msg += s + "\n";
    	}
    	return msg.trim();
    }
}
