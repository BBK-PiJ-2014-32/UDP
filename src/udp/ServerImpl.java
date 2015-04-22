package udp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl implements Server {
	
	private ServerSocket newServerSocket;
	private Socket newClient;
	private int port;

	public ServerImpl(int port){
		this.port = port;
	}
	
	@Override
	public void listenForClients() {
		try {
			newServerSocket = new ServerSocket(port);
			newClient = null;
			
			while(true){
				System.out.println("Server listening for clients");
				newClient = newServerSocket.accept();
				//get unique Id goes here.
				System.out.println("Client UniqueID HERE has connected");
			}
			
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}
	public static void main(String [] args) throws IOException {
		int portNumber = 2000;
	    
		ServerImpl newServer = new ServerImpl(portNumber);
	    newServer.listenForClients();
	}

}
