package launcher;

/**
 * The ServerLauncher initializes the server program to be used by the clients.
 */
public interface ServerLauncher {
	
	/**
	 * Starts the server to be used.
	 * 
	 * @exception IOException
	 */
	void startServer();

}
