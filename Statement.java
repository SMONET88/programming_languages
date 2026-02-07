/**
 * Abstract class for representing a statement in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public abstract class Statement {
    public static enum Status { OK, BREAK, EXIT }
    
    public abstract Statement.Status execute() throws Exception;
    public abstract String toString();


    /**
     * Static method that reads in an arbitrary Statement.
     *   @param input the TokenStream from which the program is read
     *   @return the next Statement in the program
     */
    public static Statement getStatement(TokenStream input) throws Exception {
        Token first = input.lookAhead(); 

        if (first.getType() == Token.Type.STATEMENT) {
        	String className = Statement.capitalize(first.toString());
        	return (Statement)(Class.forName(className).getConstructor(TokenStream.class).newInstance(input));
        	
        }
        else if (first.getType() == Token.Type.IDENTIFIER) {
            return new Assignment(input);
        }
        else {
            throw new Exception("SYNTAX ERROR: Unknown statement type (" + first + ")");
        }
    }
    
    /**
     * Static method for indenting a block of statements.
     * @param str the String representation of the statements
     * @return the String with each line indented two spaces
     */
    public static String indent(String str) {
    	return str.replace("\n", "\n  ");
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    private static String capitalize(String str) {
    	return str.substring(0,1).toUpperCase() + str.substring(1,str.length());
    }
}
