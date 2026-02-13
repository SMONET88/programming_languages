/**
 * Derived class that represents a variable declaration+assignment in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class Var extends Statement {

    private Token vbl;
    private Assignment assign;

    /**
     * Reads in a variable declaration+assignment from the specified TokenStream.
     *   @param input the stream to be read from
     */
    public Var(TokenStream input) throws Exception {
        if (!input.next().toString().equals("var")) {
            throw new Exception(
                "SYNTAX ERROR: Illegal variable declaration (must begin with 'var')."
            );
        }
        this.vbl = input.lookAhead();
        this.assign = new Assignment(input);
    }
    /**
     * Executes the current variable declaration+assignment statement.
     */
    public Statement.Status execute() throws Exception {
        boolean isLocal = Interpreter.MEMORY.isLocal(this.vbl);

        if (isLocal) {
            throw new Exception(
                "SYNTAX ERROR: Variable '" + this.vbl + "' is already declared."
            );
        } else {
            Interpreter.MEMORY.declareVariable(this.vbl);
            this.assign.execute();
        }

        return Statement.Status.OK;
    }
    /**
     * Converts the current variable declaration+assignment statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return "var " + this.assign;
    }
}
