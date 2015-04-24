package udp;
/**
* The Server will start listening for clients via TCP. 
* When a client connects the server will place the client connection and further 
* handling of the client in a separate thread.
* 
* @author P Hannant
*/
public interface Server {
	
/**
 * Listen for clients trying to connect over TCP.
 */
void listenForClients();

}
