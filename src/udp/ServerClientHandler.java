package udp;

import java.io.IOException;

/**
 * The ServerClientHandler sends the client the unique Id and then indicates if that client is a sender or 
 * receiver process. Then listens for UDP connection, then tells the client to connect over UDP and then
 * relays the audio data.
 */
public interface ServerClientHandler {

	void sendUniqueId() throws IOException, InterruptedException;
	
	void notifyClientIfFirst() throws IOException;
	
	void listenForUDP();
	
	
	
}
