package launcher;

import java.util.Scanner;

import udp.*;

public class LauncherImpl implements Launcher{
	
	private Scanner scan;

	public static void main(String[] args){
		
		
		LauncherImpl tc = new LauncherImpl();
		tc.run();
	}

	public void run(){
		System.out.println("********************************************************");
		System.out.println("* Welcome to my TCP/UDP file transfer program.         *");
		System.out.println("* The program starts a server and then you can create  *");
		System.out.println("* create some clients, the first client will send      *");
		System.out.println("* some audio to the server, once the server has this   *");
		System.out.println("* it will relay it back to any subsequent clients      *"); 
		System.out.println("* that join. These clients will then play the audio    *"); 
		System.out.println("* received. The launcher will create and run 10 clients*");
		System.out.println("* and once then close once all audio has been recevied *");
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
