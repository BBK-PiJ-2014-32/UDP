package udp;

public class ClientImpl implements Client{

	private String hostName;
	private int port;
	
	public ClientImpl(String host, int port){
		this.hostName = host;
		this.port = port;
	}
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

}
