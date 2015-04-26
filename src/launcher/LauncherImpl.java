package launcher;

import java.util.Scanner;

import udp.*;

/**
 * The implementation of the launcher interface.
 * @see launcher.Launcher
 */
public class LauncherImpl implements Launcher{
	
	/** The scanner. */
	private Scanner scan;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		
		
		LauncherImpl tc = new LauncherImpl();
		tc.run();
	}

	/**
	 * The run method which gives a brief explanation of the program and what it
	 * will demonstrate.
	 */
	public void run(){
		System.out.println("********************************************************");
		System.out.println("* TCP/UDP file transfer program.                       *");
		System.out.println("* Before running this program ensure that the server   *");
		System.out.println("* has been launched. Once the server is running this   *");
		System.out.println("* program will iniate the clients which will send audio*");
		System.out.println("* to the server, once the server has this it will relay*");
		System.out.println("* it back to any subsequent clients that join. These   *"); 
		System.out.println("* clients will then play the audio received.           *"); 
		System.out.println("* The launcher will create and run 10 clients and then *");
		System.out.println("* close once all audio has been recevied.              *");
		System.out.println("********************************************************");
		scan = new Scanner(System.in);
		System.out.println("* Type 'Yes' to begin: ");	
		String str = scan.nextLine();
			if (str.equals("Yes")){
				createClient();
			} else {
				System.out.println("Invalid choice.");
			}
		
	}
		
	/**
	 * @see launcher.Launcher#createClient()
	 */
	public void createClient(){
		try {
			for(int i = 0; i < 10; i++){
				ClientImpl client = new ClientImpl("localHost", 2000);
				client.run();
				Thread.sleep(2000);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
