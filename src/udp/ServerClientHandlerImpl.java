package udp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
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
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
		clientId++;
		String inText = fromClient.readLine();
		System.out.println("REQUEST RECEIVED: " + inText);
		if(inText.equals("send id")){
			toClient.writeBytes(clientId.toString() + '\n');
			System.out.println("CLIENT ID: " + clientId + " SENT.");
		} else {
			System.out.println("Invalid request.");
		}
	}
	
	@Override
	public void notifyClientIfFirst() throws IOException {
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
		String inText = fromClient.readLine();
		System.out.println("REQUEST RECEIVED: " + inText);
		if(inText.equals("first to connect?") && clientId.equals(1)){
			System.out.println("Yes");
			toClient.writeBytes("Yes" + '\n');
		} else {
			System.out.println("No");
			toClient.writeBytes("No" + '\n');
		}
		
	}

	@Override
	public void tellClientToConnectOnUDP() throws IOException {
		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
		String instruction = "CONNECT OVER UDP.";
		System.out.println(instruction);
		toClient.writeBytes(instruction + '\n');
		clientProcess = fromClient.readLine();
	}
	
	@Override
	public void listenForUDP() throws SocketException, IOException{
			byte[] receiveData = new byte[1000000];
            
            System.out.println("WAITING FOR UDP CONNECTION");
            tellClientToConnectOnUDP();
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            dataSocket.receive(receivePacket);
            System.out.println("RECEIVED: " + receivePacket.getLength());
            File fileReceived = new File ("Audionew.wav");
            FileOutputStream fileOut = new FileOutputStream(fileReceived);
            fileOut.write(receivePacket.getData());
		}		

	

	
	
}
