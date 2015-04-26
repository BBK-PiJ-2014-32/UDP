package udp;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * The Server Client Handler class.
 * @see udp.ServerClientHandler
 */
public class ServerClientHandlerImpl implements ServerClientHandler, Runnable {

	/** The client id. */
	private static Integer clientId = 0;
	
	/** The socket. */
	private Socket socket;
	
	/** The data socket. */
	private DatagramSocket dataSocket;
	
	/** The receive packet. */
	private DatagramPacket receivePacket;
	
	/** The client process. */
	private String clientProcess;
	
	/** The from client. */
	private BufferedReader fromClient;
	
	/** The tcp connection to the client. */
	private DataOutputStream toClient;
	
	/** The byte array of the audio data. */
	private static byte[] audioData;
	
	
	
	/**
	 * Instantiates a new server client handler.
	 *
	 * @param the TCP socket 
	 */
	public ServerClientHandlerImpl(Socket socket){
		this.socket = socket;
			try{
				dataSocket = new DatagramSocket(2000);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			System.out.println("SERVERCLIENTHANDLER STARTED");
			sendUniqueId();
			notifyClientIfFirst();
			listenForUDP();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see udp.ServerClientHandler#sendUniqueId()
	 */
	@Override
	public void sendUniqueId() throws IOException, InterruptedException {
		fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toClient = new DataOutputStream(socket.getOutputStream());
		clientId++;
		String inText = fromClient.readLine();
		System.out.println("REQUEST RECEIVED: " + inText);
		if(inText.equals("send id")){
			toClient.writeBytes(clientId.toString() + '\n');
			toClient.flush();
			System.out.println("CLIENT ID: " + clientId + " ALLOCATED.");
		} else {
			System.out.println("Invalid request.");
		}
	}
	
	/**
	 * @see udp.ServerClientHandler#notifyClientIfFirst()
	 */
	@Override
	public void notifyClientIfFirst() throws IOException {
		String inText = fromClient.readLine();
		if(inText.equals("first to connect?") && clientId.equals(1)){
			System.out.println("ALLOCATING: sender");
			clientProcess = "sender";
			toClient.writeBytes("Yes" + '\n');
			toClient.flush();
		} else {
			System.out.println("ALLOCATING: receiver");
			clientProcess = "receiver";
			toClient.writeBytes("No" + '\n');
			toClient.flush();
		}
		
	}

	/**
	 * @see udp.ServerClientHandler#tellClientToConnectOnUDP()
	 */
	@Override
	public void tellClientToConnectOnUDP() throws IOException {
		String instruction = "CONNECT OVER UDP.";
		toClient.writeBytes(instruction + '\n');
		toClient.flush();
	}
	
	/**
	 * @see udp.ServerClientHandler#listenForUDP()
	 */
	@Override
	public void listenForUDP() throws SocketException, IOException{
			byte[] receiveData = new byte[1000000];
            System.out.println("WAITING FOR UDP CONNECTION");
            tellClientToConnectOnUDP();
            System.out.println("PROCESSING: " + clientProcess);
            	if(clientProcess.equals("sender")){
            		receivePacket = new DatagramPacket(receiveData, receiveData.length);
            		dataSocket.receive(receivePacket);
            		int packetSize = receivePacket.getLength();
            		audioData = new byte [packetSize];
            		System.out.println("RECEIVED: " + packetSize + " bytes");
            		System.arraycopy(receivePacket.getData(), 0, audioData, 0, packetSize);
            		dataSocket.close();
            	} else if (clientProcess.equals("receiver")){
            		byte[] dataReceived = new byte [1024];
        			DatagramPacket receivePacket = new DatagramPacket(dataReceived, dataReceived.length);
        			dataSocket.receive(receivePacket);
        			String toPrint = new String(receivePacket.getData());
        		    System.out.println("SENDING: " + audioData.length + " bytes");
        		    DatagramPacket packetToSend = new DatagramPacket(audioData, audioData.length, receivePacket.getAddress(), receivePacket.getPort());
        		    dataSocket.send(packetToSend);
        		    dataSocket.close();
            	}
            }		
	
}
