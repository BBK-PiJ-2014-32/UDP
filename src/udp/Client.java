package udp;

/**
 * The Client will connect to the server via TCP and while doing so will request a uniqueId.
 * Once it has received this Id it will then ask if it is the first to connect, if it is it
 * will open a UDP connection to the server and start sending an audio file. If it is not the first 
 * it will listen for audio chunks coming down via UDP and play these chunks.
 */
public interface Client {

	void connectToServerViaTCP();
	
	void requestUniqueId();
}
