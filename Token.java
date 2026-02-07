import java.util.Arrays;
import java.util.List;

/**
 * Class that represents a token in the SILLY language.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class Token {
    private static List<String> delims =    Arrays.asList( "(", ")", "[", "]"                         );
    private static List<String> unaryOps =  Arrays.asList( "!", "#"                                   );  
    private static List<String> binaryOps = Arrays.asList( "&", "|", "+", "*", "/", "%", "^", "@",
    		                                               "=", "\\", ">", "<"                        );    
    private static List<String> booleans =  Arrays.asList( "true", "false"                            );
    private static List<String> stmtKeys =  Arrays.asList( "var", "print", "if", "while", "repeat", 
    		                                               "sub", "call", "exit", "break"             );
    private static List<String> otherKeys = Arrays.asList( "gets", "then", "elseif", "else", "endif", 
    		                                               "do", "endwhile", "times", "endrepeat"     );
    
    public static enum Type { UNKNOWN, DELIM, UNARY_OP, BINARY_OP, STATEMENT, KEYWORD, 
    	                      IDENTIFIER, INT_LITERAL, BOO_LITERAL, STR_LITERAL }
    
    private String strVal;
    
    /**
     * Constructs a token out of the given string.
     *   @param str the string value of the token
     */
    public Token(String str) {
        this.strVal = str;
    }
    
    /**
     * Identifies what type of token it is.
     *   @return the token type (e.g., Token.Type.IDENTIFIER)
     */
    public Token.Type getType() {
    	if (Token.delims.contains(this.strVal)) {
            return Token.Type.DELIM;
        }
        else if (Token.stmtKeys.contains(this.strVal)) {
            return Token.Type.STATEMENT;
        }
        else if (Token.otherKeys.contains(this.strVal)) {
            return Token.Type.KEYWORD;
        }
        else if (Token.unaryOps.contains(this.strVal)) {
            return Token.Type.UNARY_OP;
        }
        else if (Token.binaryOps.contains(this.strVal)) {
            return Token.Type.BINARY_OP;
        }
        else if (Token.booleans.contains(this.strVal)) {
        	return Token.Type.BOO_LITERAL;
        }     
        else if (this.strVal.charAt(0) == '"') {
            if (this.strVal.length() == 1 || this.strVal.charAt(this.strVal.length()-1) != '"') {
                return Token.Type.UNKNOWN;
            }
            return Token.Type.STR_LITERAL;
        }
        else if (Character.isDigit(this.strVal.charAt(0)) || 
    	    (this.strVal.charAt(0) == '-' && this.strVal.length() > 1 && Character.isDigit(this.strVal.charAt(1)))) {
            for (int i = 1; i < this.strVal.length(); i++) {
                if (!Character.isDigit(this.strVal.charAt(i))) {
                    return Token.Type.UNKNOWN;
                }
            }
            return Token.Type.INT_LITERAL;
        }
        else if (Character.isLetter(this.strVal.charAt(0))) {
            for (int i = 1; i < this.strVal.length(); i++) {
                if (!Character.isLetterOrDigit(this.strVal.charAt(i))) {
                    return Token.Type.UNKNOWN;
                }
            }
            return Token.Type.IDENTIFIER;
        }
        else {
            return Token.Type.UNKNOWN;
        }
    }
    
    /**
     * Determines when two tokens are identical.
     *   @param other the other token being compared
     *   @return whether the two tokens represent the same string value
     */
    public boolean equals(Object other) {
        return this.strVal.equals(((Token)other).strVal);
    }
   
    /**
     * Converts the token to its string representation.
     *   @return the string representation
     */
    public String toString() {
        return this.strVal;
    }
    
    /**
     * Generates a hash code for a Token (based on its String hash code).
     *   @return a hash code for the Token
     */
    public int hashCode() {
    	return this.strVal.hashCode();
    }
}
