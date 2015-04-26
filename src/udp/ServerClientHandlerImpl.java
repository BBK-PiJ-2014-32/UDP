package udp;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerClientHandlerImpl implements ServerClientHandler, Runnable {

	private static Integer clientId = 0;
	private Socket socket;
	private DatagramSocket dataSocket;
	private DatagramPacket receivePacket;
	private String clientProcess;
	private BufferedReader fromClient;
	private DataOutputStream toClient;
	private static byte[] audioData;
	
	
	
	public ServerClientHandlerImpl(Socket socket){
		this.socket = socket;
			try{
				dataSocket = new DatagramSocket(2000);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	
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
			System.out.println("CLIENT ID: " + clientId + " SENT.");
		} else {
			System.out.println("Invalid request.");
		}
	}
	
	@Override
	public void notifyClientIfFirst() throws IOException {
		String inText = fromClient.readLine();
		System.out.println("REQUEST RECEIVED: " + inText);
		if(inText.equals("first to connect?") && clientId.equals(1)){
			System.out.println("Yes");
			clientProcess = "sender";
			toClient.writeBytes("Yes" + '\n');
			toClient.flush();
		} else {
			System.out.println("No");
			clientProcess = "receiver";
			toClient.writeBytes("No" + '\n');
			toClient.flush();
		}
		
	}

	@Override
	public void tellClientToConnectOnUDP() throws IOException {
		String instruction = "CONNECT OVER UDP.";
		System.out.println(instruction);
		toClient.writeBytes(instruction + '\n');
		toClient.flush();
	}
	
	@Override
	public void getProcess() throws IOException {
		clientProcess = fromClient.readLine();
		System.out.println("CONNECTED WITH: " + clientProcess);
	}
	
	@Override
	public void listenForUDP() throws SocketException, IOException{
			byte[] receiveData = new byte[1000000];
            System.out.println("WAITING FOR UDP CONNECTION");
            tellClientToConnectOnUDP();
            System.out.println("Processing: " + clientProcess);
            	if(clientProcess.equals("sender")){
            		System.out.println("Executing: " + clientProcess);
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
        			System.out.println("RECEIVED = " + toPrint); 
        		    System.out.println("SENDING PACKET: ");
        		    DatagramPacket packetToSend = new DatagramPacket(audioData, audioData.length, receivePacket.getAddress(), receivePacket.getPort());
        		    dataSocket.send(packetToSend);
        		    System.out.println(audioData.length + "bytes sent");
        		    dataSocket.close();
            	}
            }		

	

	
	
}
