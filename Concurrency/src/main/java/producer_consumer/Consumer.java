package producer_consumer;
import java.util.Deque;

/**
 * 
 */
public class Consumer implements Runnable {
	
	private String id;
	private boolean keepAlive;
	private Deque<Object> queue;
	private TestFactory factory;
	
	public Consumer(String id, TestFactory factory, Deque<Object> queue) {
		this.id = id;
		this.queue = queue;
		this.factory = factory;
		this.keepAlive = true;
	}
	
	@Override
	public void run() {
		
		while (keepAlive) {
			
			Object toConsume = null;
			synchronized (queue) {
				while (queue.isEmpty() && keepAlive) {
					try {
						queue.wait();
					}
					catch (InterruptedException ie) {
						keepAlive = false;
					}
				}

				toConsume = queue.poll();
				queue.notifyAll();
			}

			if (keepAlive) {
				consume(toConsume);
			}
		}
		
		System.out.println("Consumer[" + id + "] shutting down.");
	}

	
	private void consume(Object o) {

		try {
			System.out.println("Consumer[" + id + "] consuming[" + o + "]");
			Thread.sleep(20);
			factory.incrementConsumed();
//			synchronized (factory) {
//				factory.totalConsumed++;
//				factory.notifyAll();
//			}
		}
		catch (InterruptedException ie) {
			keepAlive = false;
		}
	}
	
	
	public void shutdown() {
		keepAlive = false;
		
	}
	
}