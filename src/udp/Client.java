package udp;

import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Client will connect to the server via TCP and while doing so will request a uniqueId.
 * Once it has received this Id it will then ask if it is the first to connect, if it is it
 * will open a UDP connection to the server and start sending an audio file. If it is not the first 
 * it will listen for audio chunks coming down via UDP and play these chunks.
 */
public interface Client {

	/**
	 * Connects to the server via a TCP socket connection.
	 * @exception UnknownHostException
	 * @exception IOException
	 */
	void connectToServerViaTCP();
	
	/**
	 * Requests a unique id from the server, which is used to allocate what process
	 * the client will be carrying out.
	 *
	 * @exception IOException
	 * @param the socket which the client is connected on. 
	 */
	void requestUniqueId(Socket client);
	
	/**
	 * Checks if is first to connect and if it is the client is allocated as the sender.
	 * 
	 * @exception IOException
	 */
	void isFirstToConnect();
	
	/**
	 * Receives the instruction from the server to connect over UDP.
	 * 
	 * @exception IOException
	 */
	void receiveInstructionForUDP();
	
	/**
	 * Sends the audio file over UDP to the server.
	 * 
	 * @exception IOException
	 */
	void sendViaUDP();
	
	/**
	 * Receives the byte array from the server via UDP.
	 * 
	 * @exception IOException
	 */
	void receiveViaUDP();
	
	/**
	 * Plays the byte array that has been sent from the server.
	 *
	 * @param the byte array to be played.
	 */
	void playAudio(byte[] audioBytes);
}
