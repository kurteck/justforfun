package reader_writer.v1;

import java.util.ArrayList;
import java.util.List;


public class MessageBoard {

	RWLock rwLock;
	private String message;
	private List<Thread> readers;
	private List<Thread> writers;
	
	public MessageBoard(int numWriters, int numReaders) {
		
		rwLock = new RWLock();
		
		readers = new ArrayList<Thread>(numReaders);
		for (int i=0; i < numReaders; i++) {
			MessageReader reader = new MessageReader(i, this);
			Thread t = new Thread(reader);
			t.setName("Reader" + i);
			readers.add(t);
		}

		writers = new ArrayList<Thread>(numWriters);
		for (int i=0; i < numWriters; i++) {
			MessageWriter writer = new MessageWriter(i, this);
			Thread t = new Thread(writer);
			t.setName("Writer" + i);
			writers.add(t);
		}
	}
	
	public String readMessage() {
		return message;
	}
	
	public void writeMessage(String message) {
		this.message = message;
	}
	
	
	protected void startReadersAndWriters() {

		for (Thread t : readers) {
			t.start();
		}
		for (Thread t : writers) {
			t.start();
		}
	}
	
	
	public static void main(String[] args) {
		MessageBoard mb = new MessageBoard(1, 100);
		mb.startReadersAndWriters();
	}
}