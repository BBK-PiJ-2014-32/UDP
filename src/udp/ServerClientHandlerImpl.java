package udp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerClientHandlerImpl implements ServerClientHandler, Runnable {

	private static Integer clientId = 0;
	private Socket socket;
	
	public ServerClientHandlerImpl(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("ServerClientHandler started");
			sendUniqueId();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void sendUniqueId() throws IOException, InterruptedException {
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
		clientId++;
		String inText = fromClient.readLine();
		System.out.println("Request Received: " + inText);
		if(inText.equals("send id")){
			toClient.writeBytes(clientId.toString() + '\n');
			System.out.println("Client id: " + clientId + " sent.");
		} else {
			System.out.println("false");
		}
	}

	@Override
	public void listenForUDP() {
		// TODO Auto-generated method stub
		
	}
	
}
