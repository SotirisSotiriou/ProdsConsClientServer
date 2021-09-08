import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer {
    public static final String DEFAULT_MSG = "get";
	
	public Consumer(int scale) {
		Thread thread = new ConsumerThread(scale);
		thread.start();
		
	}

}

class ConsumerThread extends Thread{
	private int scale;
	private Lock lock;
	
	public ConsumerThread(int scale) {
		this.scale = scale;
		lock = new ReentrantLock();
	}
	
	public void run() {
		System.out.println("Consumer started...");
		while(true) {
			lock.lock();
			try {
				Client.out.println(Consumer.DEFAULT_MSG);	
				String reply = Client.in.readLine();
				if(reply.equals("ok")) {
					System.out.println("Consumer removed an element");					
				}
				else if (reply.equals("not ok")){
					System.out.println("Consumer could not remove element");
				}
			} catch (IOException e) {
				
			} finally {
				lock.unlock();				
			}
			
			try {
				sleep((int)(Math.random()*scale));
			} catch (InterruptedException e) { }
		}
	}
}
