package udp;

import java.io.IOException;
import java.net.SocketException;

/**
 * The ServerClientHandler sends the client the unique Id and then indicates if that client is a sender or 
 * receiver process. Then listens for UDP connection, then tells the client to connect over UDP and then
 * relays the audio data.
 * 
 * @author P Hannant
 */
public interface ServerClientHandler {

	/**
	 * Sends a unique id to the client that has requested it.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	void sendUniqueId() throws IOException, InterruptedException;
	
	/**
	 * Notifies the client that has connected if they are the first to connect
	 * in order to allocate the correct process to that client.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void notifyClientIfFirst() throws IOException;
	
	/**
	 * Listens for any incoming data sent over UDP through the server.
	 *
	 * @throws SocketException the socket exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void listenForUDP() throws SocketException, IOException;
	
	/**
	 * Tells the client to connect over UDP so that the audio data can
	 * begin to be sent.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void tellClientToConnectOnUDP()  throws IOException;
	
	
}
