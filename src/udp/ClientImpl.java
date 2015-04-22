package udp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientImpl implements Client{

	private String hostName;
	private int port;
	
	public ClientImpl(String host, int port){
		this.hostName = host;
		this.port = port;
	}
	
	@Override
	public void connectToServerViaTCP() {
		try{
			System.out.println("Trying to connect");
			Socket client = new Socket(hostName, port);
			System.out.println("Connected to host: " + hostName + "at port: " + port);
		} catch (UnknownHostException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

}
