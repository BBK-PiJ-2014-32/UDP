package launcher;

import java.io.IOException;

import udp.ServerImpl;

public class ServerLauncherImpl implements ServerLauncher{
	
	private ServerImpl server;
	private String [] args = {};

	public static void main(String[] args){
		ServerLauncherImpl sl = new ServerLauncherImpl();
		sl.startServer();
	}
	
	@Override
	@SuppressWarnings("static-access")
	public void startServer(){
		try {
			server = new ServerImpl(2000);
			server.main(args);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	

}
