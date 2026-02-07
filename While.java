import java.util.ArrayList;

/**
 * Derived class that represents a while statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class While extends Statement {
    private Expression expr;
    private Body body; 
    
    /**
     * Reads in a while statement from the specified stream.
     *   @param input the stream to be read from
     */
    public While(TokenStream input) throws Exception {
        if (!input.next().toString().equals("while")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        this.expr = new Expression(input);  
        if (!input.next().toString().equals("do")) {
            throw new Exception("SYNTAX ERROR: Malformed while statement");
        }
        ArrayList<Statement> stmts = new ArrayList<Statement>();
        while (!input.lookAhead().toString().equals("endwhile")) {
        	stmts.add(Statement.getStatement(input));
        }
        this.body = new Body(stmts);
        input.next();
    }

    /**
     * Executes the current while statement.
     */
    public Statement.Status execute() throws Exception {
        while (true) {
        	DataValue eVal = this.expr.evaluate();
        	if (eVal.getType() != DataValue.Type.BOOLEAN) {
        		throw new Exception("RUNTIME ERROR: while statement requires Boolean test.");
        	}
            if ((Boolean)eVal.getValue()) {
        		body.execute();
            }
            else {
            	break;
            }   
        }
        
        return Statement.Status.OK;
    }
    
    /**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "while " + this.expr + " do" + Statement.indent("\n"+this.body) + "\nendwhile";
    }
}
