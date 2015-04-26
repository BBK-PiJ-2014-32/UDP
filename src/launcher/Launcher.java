package launcher;

import udp.*;

public class Launcher {

	public static void main(String[] args){
		Launcher tc = new Launcher();
		tc.run();
	}

	public void run(){
		ClientImpl client1 = new ClientImpl("localHost", 2000);
		client1.run();
		//ClientImpl client2 = new ClientImpl("localHost", 3000);
		//client2.run();
	
	}

}
