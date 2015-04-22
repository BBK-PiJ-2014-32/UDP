package udp;

import java.net.ServerSocket;

public class ServerImpl implements Server {
	
	private ServerSocket serverSocket;
	private int port;

	public ServerImpl(int port){
		this.port = port;
	}
	
	@Override
	public void listenForClients() {
		// TODO Auto-generated method stub
		
	}

}
