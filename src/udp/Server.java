package udp;
/**
* The Server will start listening for clients via TCP. 
* When a client connects the server will place the client connection and further 
* handling of the client in a separate thread. The server is limited to only running
* 10 separate client threads, once 10 have been processed the server will close.
* 
* @author P Hannant
*/
public interface Server {
	
/**
 * Listen for clients trying to connect over TCP and then starts individual client threads as
 * they begin. 
 */
void listenForClients();
/**
 * Closes the TCP socket once the server has reached had 10 clients processd.
 */
void closeServer();

}
