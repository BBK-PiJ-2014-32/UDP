package launcher;

/**
 * The ServerLauncher initializes the server program to be used by the clients.
 * 
 * @author P Hannant
 */
public interface ServerLauncher {
	
	/**
	 * Starts the server to be used.
	 * 
	 * @exception IOException
	 */
	void startServer();

}
