import java.util.Scanner;

/**
 * Driver for the interactive SILLY Interpreter. 
 *   @author Dave Reed 
 *   @version 1/20/26
 */
public class Interpreter {
    public static MemorySpace MEMORY = new MemorySpace();
    
    public static void main(String[] args) throws Exception {   
    	System.out.print("Enter the program file name or hit RETURN for interactive: ");       
        Scanner input = new Scanner(System.in);
        String response = input.nextLine().strip();
        
        TokenStream inStream = new TokenStream();
        if (!response.equals("")) {
            inStream = new TokenStream(response);
        }      
        
        while (response.equals("") || inStream.hasNext()) {
            System.out.print(">>> ");
            Statement stmt = Statement.getStatement(inStream);
            
            if (!response.equals("")) {
            	System.out.println(stmt);
            }
    		
            try {
    			stmt.execute();
    		}
    		catch (Exception e) {
    			System.out.println(e);
    		}
        } 
        input.close();
    }
}
