//importing necessary java libraries to be used in the code.
import java.util.concurrent.TimeoutException;
import java.net.*;
import java.util.Objects;
import java.util.InputMismatchException;
import java.io.*;

public class ChatOfServerSide {
	
	private static BufferedReader brs = null;
	private static String txtOfCli = "";
	private static BufferedReader bri = null;
	private static PrintWriter p = null;
	private static String txtOfSer = "";
	private static ServerSocket ser = null;
	private static Socket cli = null; 
	//The initialization of the sockets, buffered readers, strings, and print writers to be used in the server side code
	
	
	///////Main Method///////////////
	public static void main(String[]args) throws IOException, TimeoutException {
	try {
		
	  System.out.println("Welcome to the bidirectional chat app");
	  
	 //converting the first argument (which represents port number)
	 //to integer by using the Integer.valueOf method, and passing this number to the inside 
	 //of ServetSocket() constructor call.
	  ser = new ServerSocket(Integer.valueOf(args[0])); 
	  
	  //Displaying port number and timeout duration to console for observation purposes.
	  System.out.println("Port Number: "+Integer.valueOf(args[0])); 
	  System.out.println("Timeout Duration : "+Integer.valueOf(args[1])); //For printing the timeout duration of the server.
		
	  //converting the second argument (which represents the timeout duration)
	  //to integer by using the Integer.valueOf() method and passing this 
	  //number to the inside of the setSoTimeout function to set the timeout duration
	  //of the server socket.
	  ser.setSoTimeout(Integer.valueOf(args[1]));  //for specifying the value of server-client connection duration coming from the command line
	  System.out.println("The local socket address of the server is: " + ser.getLocalSocketAddress() + "");
	  System.out.println("The port number of the server is: " + ser.getLocalPort() + "");
	  cli = ser.accept(); //connection with client.
	  System.out.println("Client - Server connection succeeded!!"); //for printing a related message after connection
	  
	  
	  
	  //Please Read Here//
	  //NOTE: I have put both the default and non-default timeout settings of the client socket to the project report.
	  
	  
	  //Please Read Here//
	  //for specifying the timeout duration of the client, the below command should be used.
	  
	  cli.setSoTimeout(Integer.valueOf(args[1])); //you can comment out this line for observing default timeout value (which is 0) of client socket.
	  
	  //Please Read//
	  //Note: If you comment the above line, then the value of the timeout duration of the accepted client socket will be 
	  //its default value. The default return value of getSoTimeout() function is equal to 0. Thus, after commenting the above 
	  //line, if we call cli.getSoTimeout() in a printing statement, we will obtain 0.
	  
		
	  
	//Please Read Here//
		//Important Information://///////////////////////////////////////////
      //By calling cli.setSoTimeout(Integer.valueOf(args[1])), we can set the client timeout duration.//
	  //By calling ser.setSoTimeout(Integer.valueOf(args[1])), we can set the server timeout duration.///
	  
	  
	  
	//Please Read Here//
	  // TIMEOUT SPECIFICATION CASE FOR CLIENT SOCKET:
		// If I set the timeout value of the client socket to a specific value, then the code will not enter the //
		// if block (see below if block) which checks whether the timeout duration of the client is equal to 0.//
		// After the specification of the timeout duration of client, if the client does not enter any input before the timeout duration,//
		//then the server will throw a socket timeout exception with a message which says 'read time out'.//
		//After the read timeout inside the server side terminal, the program will continue to want an input from the client side //
		//Important Information://///////////////////////////////////////////
	
	  
	//Please Read Here//
	  //NO TIMEOUT SPECIFICATION CASE FOR CLIENT SOCKET:
	  //If I do not set the timeout value of the client socket to a specific value, then the timeout value of the client socket will be default.//
	  //In other words, the timeout value of the client socket will be 0. Therefore, it will enter the below if block and will print the related//
	  //message. Moreover, cli.getSoTimeout() will return 0. Thus, the related message will be 'Timeout duration of client is : 0'//
	  
	  
	  
		
		
		
		
	  System.out.println("Timeout duration of client is: "+cli.getSoTimeout()); //printing the client timeout duration to the terminal.
	  if(cli.getSoTimeout() == 0) { //if the timeout duration of the client is 0.
			System.out.println("The timeout duration of the client is not set to a specific value!"); //then it means that the timeout duration of the client is default.
	  }
	  showSocketAddressAndPortInfo(); //calling the function which displays socket address and port info
	  createStreams(); // stream creation
	  System.out.println();
      checkInputsOfClientAndServer(); //calling the function which controls client and server inputs
      
    //dealing with possible exceptions
	} catch(SocketTimeoutException ste) {
		System.out.println("No communication occurred between the server and the client !");
		System.out.println("timeout in socket !");
		ste.printStackTrace();
	} catch (SocketException sockExcep) {
		System.out.println("Socket error happened !");
		sockExcep.printStackTrace();
	} catch(NullPointerException npexc) {
		System.out.println("Null pointer error has happened !");
		npexc.printStackTrace();
	} catch(InputMismatchException ime) {
		System.out.println("You have entered an input which is not a text");
		ime.printStackTrace();
	} catch(ArithmeticException aexc) {
		System.out.println("Arithmetic error happened !");
		aexc.printStackTrace();
	} catch (IOException ioExc) {
		System.out.println("IO exception has happened !");
		ioExc.printStackTrace();
	}
	
	//create a boolean variable which keeps track of whether the sockets, 
	//buffered readers, and print writers are null.
	boolean areNotNull = false; 
	
	
	//I have used the method called Objects.isNull() method for checking if the sockets, 
		// print writers, and buffered readers are null.
	areNotNull = 
			 Objects.isNull(cli) == false &&  
			 Objects.isNull(ser) == false && 
			 Objects.isNull(brs) == false && 
			 Objects.isNull(p) == false && 
			 Objects.isNull(bri) == false;
	 
	 
	 
	 // if all of the objects are not null, close all of the objects.
	 if(areNotNull == true) {
		    bri.close();	
			cli.close();
			p.close();
			ser.close();
			brs.close();
     }
	 
	 
 }
///////Main Method///////////////
	
	
	
////////Helper Functions/////////////////////////////////////
	
	public static void showSocketAddressAndPortInfo() {
		
		//getting the remote socket address of client by using getRemoteSocketAddress() method 
		  SocketAddress remoteClientAddr = cli.getRemoteSocketAddress(); 
		  
		//getting the local socket address of client by using getLocalSocketAddress() method 
		  SocketAddress localClientAddr = cli.getLocalSocketAddress();
		
		  
		//getting the local socket address of server by using getLocalSocketAddress() method 
		  SocketAddress localServerAddr = ser.getLocalSocketAddress();
		  
		  int portOfServer = ser.getLocalPort(); //obtaining the local port of server by using getLocalPort() method.
		  int portOfClient = cli.getLocalPort(); //obtaining the local port of client by using getLocalPort() method.
		  
		//displaying the remote socket address of client by utilizing getRemoteSocketAddress() method.
		  System.out.println("Remote socket address of client is: " + remoteClientAddr + "");
		  
		//displaying the local socket address of client by utilizing getLocalSocketAddress() method.
		  System.out.println("Local socket address of client is: " + localClientAddr + "");
		  
		//displaying the local socket address of server by utilizing getLocalSocketAddress() method.
		  System.out.println("Local socket address of server is: " + localServerAddr + "");
		 
		  //Displaying the port number of server and client by using getLocalPort() method.
		  System.out.println("The port number of the server is: " + portOfServer + "");
		  System.out.println("The port number of the client is: " + portOfClient + "");
	}
	public static void createStreams() {
		InputStream cliIn = null;
		boolean isFlushable = true;
		try {
			cliIn = cli.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      InputStreamReader cliInRead = new InputStreamReader(cliIn); 
	      brs = new BufferedReader(cliInRead); //creating a buffered reader object which is for reading client inputs
	    
	      OutputStream cliOut = null;
		try {
			cliOut = cli.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      p = new PrintWriter(cliOut,isFlushable); //for writing to client output
	    
	      InputStreamReader consoleInp = new InputStreamReader(System.in);
	      bri =  new BufferedReader(consoleInp);
	}
	
	public static void checkInputsOfClientAndServer() {
		while(true) {
	    	  try {
				txtOfCli = brs.readLine(); //reading the client input
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	    	 boolean inSwitchAndTextBye = false; //checks whether the code is in switch and text is bye.
	    	 boolean terminate = false; //creating a boolean variable which checks the termination of program.
	    	  
	    	 switch(txtOfCli) {
	        	case "": //checking whether the client input is blank.
	        		System.out.println("Blank is entered by the client !");
	        		System.out.println("Chat App terminated !");
	        		terminate = true; //if the client input is blank string, make the boolean variable called terminate true.
	        		break;
	        	case "bye": //checking whether the client is input is bye.
	        		System.out.println(""+txtOfCli+" is inputted by the client !");
	        		System.out.println("Chat App ended !");
	        		terminate = true;  //if the client input is "bye", make the boolean variable called terminate true.
	        		inSwitchAndTextBye = true;
	        		break;
	        	default:
	        		break;
	        }
	    	  
	    	if(txtOfCli.equalsIgnoreCase("bye") && inSwitchAndTextBye == false) { //checking whether the client input is equal to bye regardless of the case of input.
	    		  System.out.println(""+txtOfCli+" is inputted by the client !");
	        	  System.out.println("Chat App ended !");
	    		  terminate = true;
	    	}  
	    	
	    	if(terminate == true) { //if the value of the boolean variable called terminate is true
	    		break; //terminate the while loop
	    	}
	    	
	    	System.out.println("Text which comes from the client:" +txtOfCli+ "" );
	    	System.out.print("Please type the server text to be sent to client: ");
	    	try {
				txtOfSer = bri.readLine(); //reading the server input
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        p.println (txtOfSer); //attaching the server input
	     
	        switch(txtOfSer) { 
	        	case "": //checking whether the server input is equal to blank.
	        		System.out.println("Blank is entered by the server !");
	        		System.out.println("Chat App ended !");
	        		terminate = true;  //if the server input is blank string, make the boolean variable called terminate true.
	        		break;
	        	case "bye": //checking whether the server input is equal to bye.
	        		System.out.println(""+txtOfSer+" is typed by the server !");
	        		System.out.println("Chat App terminated !");
	        		terminate = true; //if the server input is "bye", make the boolean variable called terminate true.
	        		inSwitchAndTextBye = true;
	        		break;
	        	default:
	        		break;
	        }
	        
	        if(txtOfSer.equalsIgnoreCase("bye") && inSwitchAndTextBye == false) { //checking whether the server input is equal to bye regardless of the case of input.
	        	 System.out.println(""+txtOfSer+" is inputted by the server !");
	        	 System.out.println("Chat App ended !");
	        	 terminate = true; //make the value of the boolean variable called terminate true.
	        }
	        
	        if(terminate == true) { 
	        	break;
	        }
	       
	      }
	}
	
	
     ////////Helper Functions/////////////////////////////////////
 }