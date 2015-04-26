package udp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl implements Server {
	
	private ServerSocket newServerSocket;
	private Socket newSocket;
	private int port;
	

	public ServerImpl(int port){
		this.port = port;
	}
	
	@Override
	public void listenForClients() {
		try {
			newServerSocket = new ServerSocket(port);
			newSocket = null;
			
			while(true){
				System.out.println("SERVER LISTENING FOR CLIENTS");
				newSocket = newServerSocket.accept();
				System.out.println("CLIENT CONNECTED");
				Thread thread = new Thread(new ServerClientHandlerImpl(newSocket));
		        thread.start();
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

	@Override
	public void closeServer() {
		try {
			newSocket.close();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

}
