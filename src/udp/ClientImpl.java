package udp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientImpl implements Client{

	private String hostName;
	private Socket client;
	private int port;
	private Integer uniqueId;
	private String process;
	
	public ClientImpl(String host, int port){
		this.hostName = host;
		this.port = port;
	}
	
	public void run(){
		connectToServerViaTCP();
		requestUniqueId(client);
		isFirstToConnect();
			if(process.equals("sender")){
				sendViaUDP();
			} else {
				receiveViaUDP();
			}
	}
	
	@Override
	public void connectToServerViaTCP() {
		try{
			System.out.println("Trying to connect");
			client = new Socket(hostName, port);
			System.out.println("Connected to host: " + hostName + "at port: " + port);
		} catch (UnknownHostException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void requestUniqueId(Socket client) {
		try {
			DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Requesting unique id");
			String idRequest = "send id";
			toServer.writeBytes(idRequest + '\n');
			String receivedId = fromServer.readLine();
			uniqueId = Integer.parseInt(receivedId);
			System.out.println("Unique Id: " + uniqueId + " received");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void isFirstToConnect() {
		try {
			DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Am I the first to connect");
			String firstRequest = "first to connect?";
			toServer.writeBytes(firstRequest + '\n');
			String receivedText = fromServer.readLine();
			System.out.println("First to connect? " + receivedText);
				if(receivedText.equals("Yes")){
					System.out.println("First to connect");
					process = "sender";
				} else {
					System.out.println("Not first to connect");
					process = "receiver";	
				}	
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void recieveInstructionForUDP() {
		try {
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String instruction = fromServer.readLine();
				if (instruction.equals("Connect over UDP.")){
					//connect over UDP method called here.
				}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void sendViaUDP() {
		try {
			DatagramSocket newUDPSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localHost");
			byte[] dataToSend = process.getBytes();
			DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, IPAddress, 2000);
			newUDPSocket.send(packetToSend);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void receiveViaUDP() {
		try {
			DatagramSocket newUDPSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localHost");
			byte[] dataToSend = process.getBytes();
			DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, IPAddress, 2000);
			newUDPSocket.send(packetToSend);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	




}
