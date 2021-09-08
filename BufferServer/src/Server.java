import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int PORT = 1234;
	
	public static Buffer buffer;

	public static void main(String[] args) throws IOException {
		//establish listening server socket
		ServerSocket connectionSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);
		
		//create/start buffer
		buffer = new Buffer(100);
		
		while(true) {
			//wait for connection and produce actual socket
			Socket datasocket = connectionSocket.accept();
			System.out.println("Recieved request from " + datasocket.getInetAddress());
			
			ServerThread sthread = new ServerThread(datasocket);
			sthread.start();
		}

	}

}
