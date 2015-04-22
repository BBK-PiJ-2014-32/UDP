package udp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerClientHandlerImpl implements ServerClientHandler, Runnable {

	private static int clientId = 0;
	private Socket client;
	
	public ServerClientHandlerImpl(Socket client){
		this.client = client;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("ServerClientHandler started");
			sendUniqueId();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void sendUniqueId() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		clientId++;
		writer.write(clientId);
		writer.flush();
		writer.close();
	}
	
}
