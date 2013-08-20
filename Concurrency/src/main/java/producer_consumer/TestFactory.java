package producer_consumer;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TestFactory {
	
	List<Producer> producers;
	List<Consumer> consumers;
	Deque<Object> queue;
	int toProduce;
	int totalProduced;
	int totalConsumed;
	Boolean consumerMutex;
	
	
	public TestFactory(int units, int numProducers, int numConsumers) {

		queue = new LinkedList<Object>();
		toProduce = units * numProducers;

		producers = new ArrayList<Producer>(numProducers);
		for (int i=0; i < numProducers; i++) {
			Producer producer = new Producer("P"+i, queue, units);
			producers.add(producer);
		}

		consumers = new ArrayList<Consumer>(numConsumers);
		for (int i=0; i < numConsumers; i++) {
			Consumer consumer = new Consumer("C"+i, this, queue);
			consumers.add(consumer);
			new Thread(consumer).start();
		}
	}
	
	
	public void startProduction() {

		for (Producer producer : producers) {
			new Thread(producer).start();
		}
	}

	
	public void stopConsumers() {
		
		// Wait until all consumers report that they have finished their work
		synchronized (this) {
			while (totalConsumed < toProduce) {
				try {
					wait();
				}
				catch (InterruptedException ie) {
					break;
				}
			}

			notifyAll();
		}

		for (Consumer consumer : consumers) {
			consumer.shutdown();
		}

		synchronized (queue) {
			queue.notifyAll();
		}
	}
	
	
	public synchronized void incrementConsumed() {
		totalConsumed++;
		notifyAll();
	}
	
	
	public static void main(String[] args) {
		
		TestFactory factory = new TestFactory(1000, 4, 12);
		factory.startProduction();
		factory.stopConsumers();
	}
	
}