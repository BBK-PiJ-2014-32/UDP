package launcher;

import java.io.IOException;

import udp.*;

public class Launcher {
	
	private static final String[] args = {};

	public static void main(String[] args){
		
		
		Launcher tc = new Launcher();
		tc.run();
	}

	public void run(){
		startServer();
		ClientImpl client1 = new ClientImpl("localHost", 2000);
		client1.run();
	
	}
	
	@SuppressWarnings("static-access")
	public static void startServer(){
		try {
			ServerImpl server = new ServerImpl(2000);
			server.main(args);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void createClient(){
		ClientImpl client = new ClientImpl("localHost", 2000);
		client.run();
	}
}
