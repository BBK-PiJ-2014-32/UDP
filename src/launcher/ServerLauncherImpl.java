package launcher;

import java.io.IOException;

import udp.ServerImpl;

/**
 * The Server Launcher class.
 * @see launcher.ServerLauncher
 */
public class ServerLauncherImpl implements ServerLauncher{
	
	/** The server object. */
	private ServerImpl server;
	
	/** The empty arguments used to run the servers main method. */
	private String [] args = {};

	/**
	 * The main method used to run the start the server method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		ServerLauncherImpl sl = new ServerLauncherImpl();
		sl.startServer();
	}
	
	/**
	 * Starts the server.
	 *
	 * @see launcher.ServerLauncher#startServer()
	 */
	@Override
	@SuppressWarnings("static-access")
	public void startServer(){
		try {
			server = new ServerImpl(2000);
			server.main(args);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
