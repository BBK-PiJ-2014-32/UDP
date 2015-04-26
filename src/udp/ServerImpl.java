package udp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * The Server class implementation.
 * @see udp.Server
 * 
 */
public class ServerImpl implements Server {
	
	/** The new server socket. */
	private ServerSocket newServerSocket;
	
	/** The new socket. */
	private Socket newSocket;
	
	/** The port. */
	private int port;
	
	/** The thread count. */
	private int threadCount = 0;
	
	/** The running boolean. */
	private boolean running = true;
	

	/**
	 * Instantiates a new server.
	 *
	 * @param port the port
	 */
	public ServerImpl(int port){
		this.port = port;
	}
	
	
	/**
	 *@see udp.Server#listenForClients() 
	 */
	@Override
	public void listenForClients() {
		try {
			newServerSocket = new ServerSocket(port);
			newSocket = null;
			
			while(running){
				System.out.println("SERVER LISTENING FOR CLIENTS");
				newSocket = newServerSocket.accept();
				System.out.println("CLIENT CONNECTED");
				Thread thread = new Thread(new ServerClientHandlerImpl(newSocket));
		        thread.start();
		        threadCount++;
		        System.out.println(threadCount);
		        	if(threadCount == 10){
		        		running = false;
		        	}
				}	
			try {
				Thread.sleep(2000); 
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
			closeServer();
			
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String [] args) throws IOException {
		int portNumber = 2000;
	    
		ServerImpl newServer = new ServerImpl(portNumber);
	    newServer.listenForClients();
	}

	/**
	 * @see udp.Server#closeServer()
	 */
	@Override
	public void closeServer() {
		try {
			newSocket.close();
			System.out.println("SERVER CLOSED");
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

}
