package udp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientImpl implements Client{

	private String hostName;
	private Socket client;
	private int port;
	private int uniqueId;
	
	public ClientImpl(String host, int port){
		this.hostName = host;
		this.port = port;
	}
	
	public void run(){
		connectToServerViaTCP();
		requestUniqueId(client);
	}
	
	@Override
	public void connectToServerViaTCP() {
		try{
			System.out.println("Trying to connect");
			client = new Socket(hostName, port);
			System.out.println("Connected to host: " + hostName + "at port: " + port);
		} catch (UnknownHostException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void requestUniqueId(Socket client) {
		try {
			DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Requesting unique id");
			String idRequest = "send id";
			toServer.writeBytes(idRequest + '\n');
			String receivedId = fromServer.readLine();
			System.out.println("Unique Id: " + receivedId + " received");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


}
