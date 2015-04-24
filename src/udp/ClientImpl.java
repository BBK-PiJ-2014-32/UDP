package udp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import sun.audio.*;

public class ClientImpl implements Client{

	private String hostName;
	private Socket client;
	private int port;
	private Integer uniqueId;
	private String process;
	private DatagramSocket UDPSocket;
	private FileInputStream fileStream;
	private BufferedInputStream bufferedStream;
	private File fileToSend;
	private DataOutputStream toServer;
	private BufferedReader fromServer;
	
	
	public ClientImpl(String host, int port){
		this.hostName = host;
		this.port = port;
	}
	
	public void run(){
		connectToServerViaTCP();
		requestUniqueId(client);
		isFirstToConnect();
		System.out.println("client is a: " + process);
		recieveInstructionForUDP();
			if(process.equals("sender")){
				sendViaUDP();
			} else {
				System.out.println("Connecting to receive via UDP");
				receiveViaUDP();
			}
	}
	
	@Override
	public void connectToServerViaTCP() {
		try{
			System.out.println("TRYING TO CONNECT");
			client = new Socket(hostName, port);
			System.out.println("CONNECTED TO HOST: " + hostName + "AT PORT: " + port);
		} catch (UnknownHostException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void requestUniqueId(Socket client) {
		try {
			toServer = new DataOutputStream(client.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("REQUESTING UNIQUE ID");
			String idRequest = "send id";
			toServer.writeBytes(idRequest + '\n');
			toServer.flush();
			String receivedId = fromServer.readLine();
			uniqueId = Integer.parseInt(receivedId);
			System.out.println("UNIQUE ID: " + uniqueId + " RECIEVED");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void isFirstToConnect() {
		try {
			//DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
			//BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Am I the first to connect");
			String firstRequest = "first to connect?";
			toServer.writeBytes(firstRequest + '\n');
			toServer.flush();
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
			//BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String instruction = fromServer.readLine();
			System.out.println(instruction);
				if (instruction.equals("CONNECT OVER UDP.")){
					System.out.println("INSTRUCTION RECEIVED: " + instruction);
					UDPSocket = new DatagramSocket();
					//sendProcessType();
				}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	@Override
	public void sendProcessType(){
		try {
			//DataOutputStream toServer = new DataOutputStream(client.getOutputStream());
			toServer.writeBytes(process + '\n');
			toServer.flush();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void sendViaUDP() {
		try {
			byte[] dataToSend;
			InetAddress IPAddress = InetAddress.getByName("localHost");
			fileToSend = new File("AudioFile.wav");
		    int size = (int) fileToSend.length();
		    dataToSend = new byte[size];
		    fileStream = new FileInputStream(fileToSend);
		    int bytes_read = 0;
		    int count;
		    System.out.println("SENDING PACKET: ");
		        do { 
		          count = fileStream.read(dataToSend, bytes_read, size - bytes_read);
		          bytes_read += count;
		          DatagramPacket packetToSend = new DatagramPacket(dataToSend, dataToSend.length, IPAddress, 2000);
		          UDPSocket.send(packetToSend);
		          System.out.println("SENDING PACKET: " + count);
		        } while (bytes_read < size);
		      //  UDPSocket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void receiveViaUDP() {
		try {
			//UDPSocket = new DatagramSocket();
			byte[] dataReceived = new byte [100000];
			DatagramPacket receivePacket = new DatagramPacket(dataReceived, dataReceived.length);
			UDPSocket.receive(receivePacket);
            System.out.println("RECEIVED: " + receivePacket.getLength());
            File fileReceived = new File ("AudioNew.wav");
            FileOutputStream fileOut = new FileOutputStream(fileReceived);
            fileOut.write(receivePacket.getData());
            playAudio();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	public void playAudio(){
		try {
			File fileToPlay = new File("AudioNew.wav");
			fileStream = new FileInputStream(fileToPlay);
			AudioStream audioStream = new AudioStream(fileStream);
			AudioPlayer.player.start(audioStream);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) { 
			ex.printStackTrace();
		}
	}
	




}
