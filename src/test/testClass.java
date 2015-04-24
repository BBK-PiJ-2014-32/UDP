package test;

import udp.*;

public class testClass {

	public static void main(String[] args){
		testClass tc = new testClass();
		tc.run();
	}

	public void run(){
		ClientImpl client1 = new ClientImpl("localHost", 3000);
		client1.run();
		ClientImpl client2 = new ClientImpl("localHost", 3000);
		client2.run();
	
	}

}
