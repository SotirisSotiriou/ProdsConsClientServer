import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerThread extends Thread{
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	private Lock lock = new ReentrantLock();
	
	public ServerThread(Socket datasocket) {
		try {
			is = datasocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = datasocket.getOutputStream();
			out = new PrintWriter(os, true);
		} catch (IOException e) {}
	}
	
	
	public void run() {
		String inmsg;
		while(true) {
			try {
				lock.lock();
				try {
					inmsg = in.readLine();	
				} finally {
					lock.unlock();
				}
				if(inmsg.equals("put")) {
					Server.buffer.put();
					out.println("ok");
				}else if(inmsg.equals("get")) {
					int data = Server.buffer.get();
					out.println("ok");
				}
				else {
					System.out.println("error");
					out.println("not ok");
				}
			} catch (IOException e) {}
		}
	}

}
