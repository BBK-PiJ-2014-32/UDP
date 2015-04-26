package launcher;

import java.io.IOException;
import java.util.Scanner;

import udp.*;

public class LauncherImpl implements Launcher{
	
	private static final String[] args = {};
	private Scanner scan;
	private boolean running;
	private int count = 0;
	private static ServerImpl server;

	public static void main(String[] args){
		
		
		LauncherImpl tc = new LauncherImpl();
		tc.run();
	}

	public void run(){
		System.out.println("*******************************************************");
		System.out.println("* Welcome to my TCP/UDP file transfer program.        *");
		System.out.println("* The program starts a server and then you can create *");
		System.out.println("* create some clients, the first client will send     *");
		System.out.println("* some audio to the server, once the server has this  *");
		System.out.println("* it will relay it back to any subsequent clients     *"); 
		System.out.println("* that join. These clients will then play the audio   *"); 
		System.out.println("* received. A maximum of 10 clients can join.         *");
		System.out.println("* There are 3 options to choose from:                 *");
		System.out.println("* Press '1' to start the server.                      *");
		System.out.println("* Press '2' to create a client.                       *");
		System.out.println("* Press '9' to close.                                 *");
		System.out.println("*******************************************************");
		scan = new Scanner(System.in);
		System.out.println("Please choose an option: ");
		String str = scan.nextLine();
		switch (str) {
			case "1":
				startServer();
			case "2":
				createClient();
				count++;
			case "9":
				closeServer();
			default:
				System.out.println("Invalid choice.");
		
		/*while(running){
		
			if(str.equals("1")){
				startServer();
			} else if (str.equals("2")){
				createClient();
				count++;
				
			} else if (str.equals("9")|| count == 10){
				running = false;
			} else {
				System.out.println("Invalid choice.");
			}*/
		}
		
	}
	
	@SuppressWarnings("static-access")
	public void startServer(){
		try {
			server = new ServerImpl(2000);
			server.main(args);
			System.out.println("Server started.");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void createClient(){
		ClientImpl client = new ClientImpl("localHost", 2000);
		client.run();
	}
	
	public void closeServer(){
		server.closeServer();
	}
}
