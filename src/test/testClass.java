package test;

import udp.*;

public class testClass {

	public static void main(String[] args){
		testClass tc = new testClass();
		tc.run();
	}

	public void run(){
		ClientImpl client = new ClientImpl("localHost", 2000);
		client.connectToServerViaTCP();
	}

}
