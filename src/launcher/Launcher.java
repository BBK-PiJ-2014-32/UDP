package launcher;

/**
 * The launcher is used to create and run the clients that connect to the server, send the audio
 * and play the audio bytes received.
 * 
 * @author P Hannant
 */
public interface Launcher {

	/**
	 * Creates the clients and initiates the each clients run method..
	 */
	void createClient();
	
}
