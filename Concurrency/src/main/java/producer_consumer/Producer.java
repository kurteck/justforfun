package producer_consumer;
import java.util.Deque;

/**
 * 
 */
public class Producer implements Runnable {
	
	private String id;
	private int numToProduce;
	private int numProduced;
	private Deque<Object> queue;

	
	public Producer(String id, Deque<Object> queue, int numToProduce) {
		this.id = id;
		this.queue = queue;
		this.numProduced  = 0;
		this.numToProduce = numToProduce; 
	}
	
	
	@Override
	public void run() {
		
		while (numProduced < numToProduce) {
			
			synchronized (queue) {
				while (queue.size() >= 10) {
					try {
						queue.wait();
					}
					catch (InterruptedException ie) {
						break;
					}
				}

				String newProduct = new String("Product" + id + "_" + numProduced);
				queue.add(newProduct);
				queue.notifyAll();

				numProduced++;
			}
		}
	}
}