import java.util.ArrayList;

/**
 * Derived class that represents an expression in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */

public class Expression {

    private Token tok; // used for simple expressions (no operators)
    private Token op; // used for operator
    private ArrayList<Expression> exprs; // operator expressions or list contents

    /**
     * Creates an expression from the specified TokenStream.
     *   @param input the TokenStream from which the program is read
     */
    public Expression(TokenStream input) throws Exception {
        this.tok = input.next();
        if (this.tok.toString().equals("(")) {
            this.exprs = new ArrayList<Expression>();
            if (input.lookAhead().getType() == Token.Type.UNARY_OP) {
                this.op = input.next();
                this.exprs.add(new Expression(input));
            } else {
                this.exprs.add(new Expression(input));
                if (input.lookAhead().getType() == Token.Type.BINARY_OP) {
                    this.op = input.next();
                    this.exprs.add(new Expression(input));
                } else {
                    throw new Exception("SYNTAX ERROR: Malformed expression");
                }
            }
            if (!(input.next().toString().equals(")"))) {
                throw new Exception("SYNTAX ERROR: Malformed expression");
            }
        } else if (this.tok.toString().equals("[")) {
            this.exprs = new ArrayList<Expression>();
            while (!input.lookAhead().toString().equals("]")) {
                this.exprs.add(new Expression(input));
            }
            input.next();
        } else if (
            this.tok.getType() != Token.Type.IDENTIFIER &&
            this.tok.getType() != Token.Type.INT_LITERAL &&
            this.tok.getType() != Token.Type.STR_LITERAL &&
            this.tok.getType() != Token.Type.BOO_LITERAL
        ) {
            throw new Exception("SYNTAX ERROR: malformed expression");
        }
    }

    /**
     * Evaluates the current expression.
     *   @return the value represented by the expression
     */
    public DataValue evaluate() throws Exception {
        if (this.op == null) {
            if (this.tok.toString().equals("[")) {
                ArrayList<DataValue> vals = new ArrayList<DataValue>();
                for (Expression e : this.exprs) {
                    vals.add(e.evaluate());
                }
                return new ListValue(vals);
            } else if (this.tok.getType() == Token.Type.IDENTIFIER) {
                if (!Interpreter.MEMORY.isDeclared(this.tok)) {
                    throw new Exception(
                        "RUNTIME ERROR: variable " + this.tok + " is undeclared"
                    );
                }
                return Interpreter.MEMORY.lookupValue(this.tok);
            } else if (this.tok.getType() == Token.Type.INT_LITERAL) {
                return new IntegerValue(Integer.parseInt(this.tok.toString()));
            } else if (this.tok.getType() == Token.Type.STR_LITERAL) {
                String s = this.tok.toString();
                return new StringValue(s.substring(1, s.length() - 1));
            } else if (this.tok.getType() == Token.Type.BOO_LITERAL) {
                return new BooleanValue(Boolean.valueOf(this.tok.toString()));
            }
        } else if (this.op.getType() == Token.Type.UNARY_OP) {
            DataValue rhs = this.exprs.get(0).evaluate();
            if (this.op.toString().equals("!")) {
                if (rhs.getType() == DataValue.Type.BOOLEAN) {
                    boolean b2 = ((Boolean) (rhs.getValue()));
                    return new BooleanValue(!b2);
                }
            } else if (this.op.toString().equals("#")) {
                if (rhs.getType() == DataValue.Type.STRING) {
                    String strLength = ((String) (rhs.getValue()));
                    return new IntegerValue(strLength.length());
                } else if (rhs.getType() == DataValue.Type.LIST) {
                    @SuppressWarnings("unchecked")
                    ArrayList<DataValue> items = (ArrayList<
                        DataValue
                    >) rhs.getValue();
                    return new IntegerValue(items.size());
                }
            }

            throw new Exception(
                "RUNTIME ERROR: Type mismatch in unary expression"
            );
        } else if (this.op.getType() == Token.Type.BINARY_OP) {
            DataValue lhs = this.exprs.get(0).evaluate();
            DataValue rhs = this.exprs.get(1).evaluate();
            ArrayList<Boolean> boolCheck = new ArrayList<>();

            if (lhs.getType() == rhs.getType()) {
                if (op.toString().equals("=")) {
                    return new BooleanValue(lhs.compareTo(rhs) == 0);
                } else if (op.toString().equals("\\")) {
                    return new BooleanValue(lhs.compareTo(rhs) != 0);
                } else if (op.toString().equals(">")) {
                    return new BooleanValue(lhs.compareTo(rhs) > 0);
                } else if (op.toString().equals("<")) {
                    return new BooleanValue(lhs.compareTo(rhs) < 0);
                } else if (this.op.toString().equals("&")) {
                    if (rhs.getType() != DataValue.Type.BOOLEAN) {
                        throw new Exception(
                            "ILLEGAL TYPE: Must have boolean type with & and |"
                        );
                    }
                    if (rhs.getType() == DataValue.Type.BOOLEAN) {
                        boolean bool1 = ((Boolean) (rhs.getValue()));
                        boolean bool2 = ((Boolean) (lhs.getValue()));
                        if (bool1 == true && bool2 == true) {
                            boolCheck.add(true);
                        } else {
                            boolCheck.add(false);
                        }
                    }
                    if (boolCheck.contains(false)) {
                        return new BooleanValue(false);
                    } else {
                        return new BooleanValue(true);
                    }
                } else if (this.op.toString().equals("|")) {
                    if (rhs.getType() == DataValue.Type.BOOLEAN) {
                        boolean bool1 = ((Boolean) (rhs.getValue()));
                        boolean bool2 = ((Boolean) (lhs.getValue()));
                        if (bool1 == true || bool2 == true) {
                            boolCheck.add(true);
                        } else {
                            boolCheck.add(false);
                        }
                    }
                    if (boolCheck.contains(true)) {
                        return new BooleanValue(true);
                    } else {
                        return new BooleanValue(false);
                    }
                } else if (lhs.getType() == DataValue.Type.STRING) {
                    String str1 = (String) lhs.getValue();
                    String str2 = (String) rhs.getValue();

                    if (op.toString().equals("+")) {
                        return new StringValue(str1 + str2);
                    }
                } else if (lhs.getType() == DataValue.Type.INTEGER) {
                    int num1 = ((Integer) (lhs.getValue()));
                    int num2 = ((Integer) (rhs.getValue()));

                    if (op.toString().equals("+")) {
                        return new IntegerValue(num1 + num2);
                    } else if (op.toString().equals("*")) {
                        return new IntegerValue(num1 * num2);
                    } else if (op.toString().equals("/")) {
                        return new IntegerValue(num1 / num2);
                    } else if (op.toString().equals("%")) {
                        return new IntegerValue(num1 % num2);
                    } else if (op.toString().equals("^")) {
                        return new IntegerValue((int) Math.pow(num1, num2));
                    }
                } else if (lhs.getType() == DataValue.Type.LIST) {
                    ListValue str1 = (ListValue) lhs;
                    ListValue str2 = (ListValue) rhs;

                    @SuppressWarnings("unchecked")
                    ArrayList<DataValue> result1 = (ArrayList<
                        DataValue
                    >) str1.getValue();
                    @SuppressWarnings("unchecked")
                    ArrayList<DataValue> result2 = (ArrayList<
                        DataValue
                    >) str2.getValue();

                    ArrayList<DataValue> list = new ArrayList<>();
                    list.addAll(result1);
                    list.addAll(result2);

                    return new ListValue(list);
                }
            } else if (
                lhs.getType() != rhs.getType() && op.toString().equals("@")
            ) {
                if (
                    lhs.getType() != DataValue.Type.STRING &&
                    lhs.getType() != DataValue.Type.LIST &&
                    rhs.getType() != DataValue.Type.INTEGER
                ) {
                    throw new Exception(
                        "RUNTIME ERROR: Type mismatch, only a string/list and integer can be used with @ operator"
                    );
                }
                if (
                    op.toString().equals("@") &&
                    lhs.getType() == DataValue.Type.STRING
                ) {
                    String word = (String) lhs.getValue();
                    int index = (int) rhs.getValue();
                    char character = word.charAt(index);
                    return new StringValue(String.valueOf(character));
                } else if (
                    op.toString().equals("@") &&
                    lhs.getType() == DataValue.Type.LIST
                ) {
                    @SuppressWarnings("unchecked")
                    ArrayList<DataValue> listValues = (ArrayList<
                        DataValue
                    >) lhs.getValue();
                    int index = (int) rhs.getValue();

                    return listValues.get(index);
                }
            }

            throw new Exception(
                "RUNTIME ERROR: Type mismatch in binary expression"
            );
        }

        return null;
    }

    /**
     * Converts the current expression into a String.
     *   @return the String representation of this expression
     */
    public String toString() {
        if (this.op == null) {
            if (this.tok.toString().equals("[")) {
                String message = "[";
                for (Expression e : this.exprs) {
                    message += e + " ";
                }
                return message.trim() + "]";
            } else {
                return this.tok.toString();
            }
        } else if (this.op.getType() == Token.Type.UNARY_OP) {
            return "(" + this.op + " " + this.exprs.get(0) + ")";
        } else {
            return (
                "(" +
                this.exprs.get(0) +
                " " +
                this.op +
                " " +
                this.exprs.get(1) +
                ")"
            );
        }
    }
}
