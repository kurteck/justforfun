package reader_writer.v4;

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
		try {
			LockStats stats = rwLock.waitForWriters();
			long elapsed = stats.lockReleaseTime - stats.lockRequestTime;
			return "MR[" + stats.lockOwner.getName() + "] requested lock at " + stats.lockRequestTime + ".  Acquired at " + stats.lockAcquireTime + ".  Elapsed " + elapsed + " ms.  Message: (" + message + ")";
		}
		catch (InterruptedException ie) {
			return null;
		}
	}


	/**
	 * @param message
	 */
	public void writeMessage(String message) {

		try {
			LockStats stats = rwLock.acquireWriteLock();
			long elapsed = stats.lockAcquireTime - stats.lockRequestTime;
			message += " Requested at " + stats.lockRequestTime + ".  Acquired at " + stats.lockAcquireTime + ".  Elapsed " + elapsed + " ms.";
			this.message = message;
			Thread.sleep(2000); // take 2 seconds to write (blocks for 2 seconds)
			rwLock.releaseWriteLock();
			Thread.sleep(500);  // give readers a half of a second to read
		}
		catch (InterruptedException ie) {
			return;
		}
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