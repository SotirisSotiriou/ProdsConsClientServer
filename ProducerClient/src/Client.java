import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
	
public class Client {
	
	private static final String HOST = "localhost";
    private static final int PORT = 1234;
    public static Socket datasocket;
    
  //input and output streams
    public static InputStream is;
    public static BufferedReader in;
    public static OutputStream os;
    public static PrintWriter out;
    

	public static void main(String[] args) throws IOException {
		//try to connect to server
		datasocket = new Socket(HOST, PORT);
		
		//setup input and output streams
		is = datasocket.getInputStream();
		in = new BufferedReader(new InputStreamReader(is));
		os = datasocket.getOutputStream();
		out = new PrintWriter(os, true);
		System.out.println("Connection to " + HOST + " established.");
		
		//create/start producers
		int noProds = 3;
		Producer[] prods = new Producer[noProds];
		
		for(int i=0; i<noProds; i++) {
			prods[i] = new Producer(500);
		}
		
	}

}
