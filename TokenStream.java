import java.util.Scanner;
import java.io.File;

/**
 * Class for reading SILLY language tokens from an input stream, either
 * standard input or from a file.
 *   @author Dave Reed
 *   @version 1/20/26
 */
public class TokenStream {
    private Scanner input;
    private Token nextToken;
    private String buffer;

    /**
     * Constructs a TokenStream connected to System.in.
     */
    public TokenStream() {
        this.input = new Scanner(System.in);
        this.buffer = "";
    }
    
    /**
     * Constructs a TokenStream connected to a file.
     *   @param filename the file to read from
     */
    public TokenStream(String filename) throws java.io.FileNotFoundException {
        this.input = new Scanner(new File(filename));
        this.buffer = "";
    }

    /**
     * Returns the next token in the TokenStream (without removing it).
     *   @return the next token
     */
    public Token lookAhead() {
    	if (this.nextToken == null) {    		
            if (this.buffer.equals("") && this.input.hasNext()) {
            	this.buffer = this.input.next().strip();
            }
            
            int index = 1;
            Token first = new Token(this.buffer.substring(0,1));
            if (first.getType() != Token.Type.DELIM &&
            	    first.getType() != Token.Type.UNARY_OP &&	
            		first.getType() != Token.Type.BINARY_OP) { 
	            if (this.buffer.charAt(0) == '"') {
	            	while (index < this.buffer.length() && this.buffer.charAt(index) != '"') {
	            		index++;
	            	}
	            	index++;
	            }
	            else {
	            	while (index < this.buffer.length()) {
	            		Token last = new Token(this.buffer.substring(index,index+1));
	            		if (last.getType() == Token.Type.DELIM ||
	                    	    last.getType() == Token.Type.UNARY_OP ||	
	                    		last.getType() == Token.Type.BINARY_OP) { 
	            			break;
	            		}
	            		index++;
	            	}
	            }
            }
            this.nextToken = new Token(this.buffer.substring(0, index));
        }
        return this.nextToken;
    }
    
    /**
     * Returns the next token in the TokenStream (and removes it).
     *   @return the next token
     */
    public Token next() {
        Token safe = this.lookAhead();
        this.nextToken = null;
        this.buffer = this.buffer.substring(safe.toString().length()).strip();
        return safe;
     }
     
     /**
      * Determines whether there are any more tokens to read.
      *   @return true if tokens remaining, else false
      */
     public boolean hasNext() {
        return (this.nextToken != null || !this.buffer.equals("") || this.input.hasNext());
     }
}
