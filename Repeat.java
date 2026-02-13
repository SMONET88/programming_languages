
import java.util.ArrayList;

/**
 * Derived class that represents a repeat statement in the SILLY language.
 *   @author Sam Phillips
 */

public class Repeat extends Statement {

    private Expression expr;
    private Body body;

    public Repeat(TokenStream input) throws Exception {
        if (!input.next().toString().equals("repeat")) {
            throw new Exception("SYNTAX ERROR: Malformed repeat statement");
        }
        this.expr = new Expression(input);
        if (!input.next().toString().equals("times")) {
            throw new Exception("SYNTAX ERROR: Malformed repeat statement");
        }
        ArrayList<Statement> stmts = new ArrayList<Statement>();
        while (!input.lookAhead().toString().equals("endrepeat")) {
            stmts.add(Statement.getStatement(input));
        }
        this.body = new Body(stmts);
        input.next();
    }

    public Statement.Status execute() throws Exception {
        DataValue eVal = this.expr.evaluate();
        if (eVal == null || !(eVal.getValue() instanceof Integer)) {
            throw new Exception(
                "RUNTIME ERROR: Expression does not evaluate to an integer value"
            );
        }
        int count = (Integer) eVal.getValue();
        for (int i = 0; i < count; i++) {
            body.execute();
        }

        return Statement.Status.OK;
    }

    /**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
    public String toString() {
        return (
            "repeat " +
            this.expr +
            " times" +
            Statement.indent("\n" + this.body) +
            "\nendrepeat"
        );
    }
}
