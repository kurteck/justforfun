package reader_writer.v5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MessageBoard {

	RWLock rwLock;
	private String message;
	private ExecutorService executorSvc;
	private List<MessageReader> readers;
	private List<MessageWriter> writers;
	
	public MessageBoard(int numWriters, int numReaders) {
		
		executorSvc = Executors.newCachedThreadPool();
		rwLock = new RWLock();
		
		readers = new ArrayList<MessageReader>(numReaders);
		for (int i=0; i < numReaders; i++) {
			MessageReader reader = new MessageReader(i, this);
			readers.add(reader);
		}

		writers = new ArrayList<MessageWriter>(numWriters);
		for (int i=0; i < numWriters; i++) {
			MessageWriter writer = new MessageWriter(i, this);
			writers.add(writer);
		}
	}
	
	public String readMessage() {
		return message;
	}
	
	public void writeMessage(String message) {
		this.message = message;
	}
	
	
	protected void startReadersAndWriters() {

		for (MessageReader reader : readers) {
			executorSvc.execute(reader);
		}
		for (MessageWriter writer: writers) {
			executorSvc.execute(writer);
		}
	}
	
	
	public static void main(String[] args) {
		MessageBoard mb = new MessageBoard(1, 100);
		mb.startReadersAndWriters();
	}
}