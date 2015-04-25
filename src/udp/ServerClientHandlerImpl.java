package udp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ServerClientHandlerImpl implements ServerClientHandler, Runnable {

	private static Integer clientId = 0;
	private Socket socket;
	private DatagramSocket dataSocket;
	private DatagramPacket receivePacket;
	private String clientProcess;
	private FileInputStream fileStream;
	private File fileToSend;
	private BufferedReader fromClient;
	private DataOutputStream toClient;
	
	
	
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
            		System.out.println("RECEIVED: " + packetSize);
            		File fileReceived = new File ("AudioNew.wav");
            		FileOutputStream fileOut = new FileOutputStream(fileReceived);
            		fileOut.write(receivePacket.getData(), 0, packetSize);
            		dataSocket.close();
            		fileOut.close();
            	} else if (clientProcess.equals("receiver")){
            		byte[] dataReceived = new byte [1024];
        			DatagramPacket receivePacket = new DatagramPacket(dataReceived, dataReceived.length);
        			dataSocket.receive(receivePacket);
        			String toPrint = new String(receivePacket.getData());
        			System.out.println("RECEIVED = " + toPrint);
            		byte[] dataToSend;
        			fileToSend = new File("AudioNew.wav");
        		    int size = (int) fileToSend.length();
        		    System.out.println("Size = " + size);
        		    dataToSend = new byte[size];
        		    fileStream = new FileInputStream(fileToSend);
        		    int bytes_read = 0;
        		    int count;
        		    System.out.println("SENDING PACKET: ");
        		        do { 
        		          count = fileStream.read(dataToSend, bytes_read, size - bytes_read);
        		          bytes_read += count;
        		          DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, receivePacket.getAddress(), receivePacket.getPort());
        		          dataSocket.send(packetToSend);
        		          System.out.println("SENDING PACKET: " + count);
        		        } while (bytes_read < size);
        		    dataSocket.close();
            	}
            }		

	

	
	
}
