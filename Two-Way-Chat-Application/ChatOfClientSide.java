//imports of the necessary libraries
import java.util.InputMismatchException;

import java.net.*;
import java.util.Objects;
import java.io.*;



public class ChatOfClientSide {
	
	//The initialization of the sockets, buffered readers, strings, 
	//and print writers to be used in the client side code.
	private static String hostType = ""; 
	private static String zerothArgument = "";
	private static int portNum = 0;
	private static String textOfCli = "";
	private static Socket cliSock = null;
	private static PrintWriter pw = null;
	private static BufferedReader bufReadStr = null;
	private static String textOfSer = "";
	private static BufferedReader bufReadInp = null;
	
    
    /////////////Main Method////////////////////
	public static void main(String[]args) throws IOException {
		
		try {
			
			System.out.println("Welcome to the bidirectional chat app"); //printing the welcoming message
			
			
			
			boolean flushable = true; //creating a boolean variable called flushable which keeps track of whether 
			//the print writer object is flushable. assigning the value of the boolean variable called flushable to true.
			
			
			zerothArgument = args[0];  //obtaining port number as an argument in the command.
			portNum = Integer.valueOf(zerothArgument); //converting the argument to integer by using Integer.valueOf() function.
			hostType = "localhost"; //specifying the host 
			
			//System.out.println("Port Number is: " + portNum + "");
			
			cliSock = new Socket(hostType, portNum); //creating the client socket
			OutputStream clieOut = cliSock.getOutputStream(); 
			pw = new PrintWriter(clieOut,flushable);
			
			InputStream clieIn = cliSock.getInputStream();
			InputStreamReader clieInRead = new InputStreamReader(clieIn);
			bufReadStr = new BufferedReader(clieInRead);
			
			
			InputStreamReader terminalInput = new InputStreamReader(System.in);
			bufReadInp = new BufferedReader(terminalInput);


			System.out.println("Timeout duration of the created client socket: "+cliSock.getSoTimeout());
			
			if(cliSock.getSoTimeout() == 0) {
				System.out.println("The timeout duration of the client is not set to a specific value!");
			}
			
			
		    displaySocketAddressAndPortInfo();
		    System.out.println();
		    
		    
			checkClientAndServerTexts();
			
		} catch(NullPointerException npexc) { 
			System.out.println("Null pointer error has happened !");
			npexc.printStackTrace();
		} catch(SocketTimeoutException ste) { 
			System.out.println("No activity in the server !");
			System.out.println("timeout in socket !");
			ste.printStackTrace();
		} catch(InputMismatchException ime) {
			System.out.println("You have entered an input which is not a text");
			ime.printStackTrace();
		} catch (SocketException sockEx) {
			System.out.println("Socket error happened !");
			sockEx.printStackTrace();
		} catch(ArithmeticException aexc) {
			System.out.println("Arithmetic error happened !");
			aexc.printStackTrace();
		} catch (IOException ioEx) {
			System.out.println("IO Exception has happened !");
			ioEx.printStackTrace();
		}
		
		
		//creating a boolean variable which checks of whether the sockets, 
		//buffered readers, and print writers are null.
		boolean objectsNotNull = false;
		
		objectsNotNull = 
				(Objects.isNull(pw) == false) && 
				(Objects.isNull(bufReadStr) == false) && 
				(Objects.isNull(bufReadInp) == false) && 
				(Objects.isNull(cliSock) == false);
		
		
		
		//if all objects are not null
		if(objectsNotNull == true) {
			bufReadStr.close(); //close the buffered reader object which keeps track of incoming inputs from the server
			pw.close();  //close the print writer object
			bufReadInp.close(); //close the buffered reader object  which keeps track of the inputs in client
			cliSock.close(); //close the client socket 
		}
				
}
/////////////Main Method////////////////////
	
	
	
	
	
/////////////////////////////Helper functions/////////////////
	
	public static void checkClientAndServerTexts() {
		while (true) {
			System.out.print("Please type the client text to be sent to server: ");
			try {
				textOfCli = bufReadInp.readLine();//obtaining the client input
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean isInSwitchAndTextBye = false; //checks whether the code is inside the switch and text is equal to bye
			boolean loopEnds = false; //creating a boolean variable called loopEnds and assigning it to false.
			
			
			
			
			pw.println(textOfCli);
			
	    	  switch(textOfCli) {
	    	  
	        	case "": //if the client input is blank string, then assign the boolean variable called loopEnds to true.
	        		System.out.println("Blank is typed by the client !");
	        		System.out.println("Chat App terminated !");
	        		loopEnds = true;
	        		break;
	        	case "bye": //if the client input is "bye", then assign the boolean variable called loopEnds to true.
	        		isInSwitchAndTextBye = true;
	        		System.out.println(""+textOfCli+" is inputted by the client !");
	        		System.out.println("Chat App ended !");
	        		loopEnds = true; //when the client input is equal to "bye", assign loopEnds variable to true.
	        		break;
	        	default:
	        		break;
	        		
	         }
	    	  
	       if(textOfCli.equalsIgnoreCase("bye") && isInSwitchAndTextBye == false) {  //check whether the client input is equal to "bye" regardless of the case of "bye".
					System.out.println(""+textOfCli+" is inputted by the client !");
		        	System.out.println("Chat App ended !");
					loopEnds = true; //assign loopEnds variable to true
			}  
	       
	    	if(loopEnds == true) { 
	    		break;
	    	}
		
			try {
				textOfSer = bufReadStr.readLine(); //obtaining the server input
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(textOfSer) {
        		case "": //checking whether the server input is equal to the blank string.
        			System.out.println("Blank is typed by the server !");
        			System.out.println("Chat App terminated !");
        			loopEnds = true; //if the server input is blank string, assign the loopEnds to true.
        			break;
        		case "bye": //checking whether the server  input is equal to "bye"
        			isInSwitchAndTextBye = true;
        			System.out.println(""+textOfSer+" is inputted by the server !");
        			System.out.println("Chat App ended !");
        			loopEnds = true; //if the server input is bye, then assign the loopEnds variable to true.
        			break;
        		default:
        			break;
            }
			
			if(textOfSer.equalsIgnoreCase("bye") && isInSwitchAndTextBye == false) { //check whether the server input is equal to "bye" regardless of the case of "bye".
				System.out.println(""+textOfSer+" is inputted by the server !");
	        	System.out.println("Chat App ended !");
				loopEnds = true; 
			}
    	  
			if(loopEnds == true) { //if the value of loopEnds is true
				break; //terminate the loop
			}
			System.out.println("The text coming from the server: " + textOfSer + "");
		}
		
	}
	 
	public static void displaySocketAddressAndPortInfo() {
		    int localPortNum = cliSock.getLocalPort(); //obtaining the port of client socket by using getLocalPort() method.
		    
		    SocketAddress remoteCli =  cliSock.getRemoteSocketAddress(); //obtaining the remote socket address of client socket by using getRemoteSocketAddress() method.
		    SocketAddress localCli = cliSock.getLocalSocketAddress(); //obtaining the local socket address of client by using getLocalSocketAddress() method.
			
		   
			System.out.println("The local port number of client is: " + localPortNum);
			System.out.println("The remote socket address of client is: " + remoteCli + "");
			System.out.println("The local socket address of client is: " + localCli + "");
	}
	
/////////////////////////////Helper functions/////////////////
	
	}