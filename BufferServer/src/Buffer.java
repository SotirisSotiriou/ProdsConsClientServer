import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
	private int[] contents;
	private final int size;
	private int front, back;
	private volatile int counter = 0;
	private Lock lock = new ReentrantLock();
	private final Condition bufferFull = lock.newCondition();
	private final Condition bufferEmpty = lock.newCondition();
	
	public Buffer(int s) {
		this.size = s;
		this.contents = new int[size];
		this.front = 0;
		this.back = size - 1;
		for(int i=0; i<size; i++) {
			contents[i] = 0;
		}
	}
	
	
	public void put() {
		lock.lock();
		try {
			while(counter == size) {
				//System.out.println("The buffer is full");
				try {
					bufferFull.await();
				} catch(InterruptedException e) { }
			}
			back = (back + 1) % size;
			contents[back] = 1;
			counter++;
			System.out.println("A producer put an element to buffer. Buffer now has " + counter + " elements");
			bufferEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	
	public int get() {
		int data = 0;
		
		lock.lock();
		try {
			while(counter == 0) {
				//System.out.println("The buffer is empty");
				try {
					bufferEmpty.await();
				} catch(InterruptedException e) { }
			}
			data = contents[front];
			System.out.println("A consumer removed an element from buffer. Buffer now has " + counter + " elements");
			front = (front + 1) % size;
			counter--;
			bufferFull.signalAll();
		} finally {
			lock.unlock();
		}
		
		return data;
	}

}


