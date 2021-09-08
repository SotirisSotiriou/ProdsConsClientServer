import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Producer {
	public static final String DEFAULT_MSG = "put";
	
	public Producer(int scale) {
		Thread thread = new ProducerThread(scale);
		thread.start();
		
	}

}

class ProducerThread extends Thread{
	private int scale;
	private Lock writerLock;
	private Lock readerLock;
	
	public ProducerThread(int scale) {
		this.scale = scale;
		writerLock = new ReentrantLock();
		readerLock = new ReentrantLock();
	}
	
	public void run() {
		System.out.println("Producer started...");
		while(true) {
			writerLock.lock();
			try {
				Client.out.println(Producer.DEFAULT_MSG);
				String reply = Client.in.readLine();
				if(reply.equals("ok")) {
					System.out.println("Producer added an element");
				}
				else if(reply.equals("not ok")) {
					System.out.println("Producer could not add element");
				}
			} catch (IOException e) {
				
			} finally {
				writerLock.unlock();				
			}
			
			
			try {
				sleep((int)(Math.random()*scale));
			} catch (InterruptedException e) { }
		}
		
	}
}
