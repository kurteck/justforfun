package reader_writer.v2;

public class MessageReader implements Runnable {
	
	private int id;
	private MessageBoard mb;
	
	public MessageReader(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
	}

	@Override
	public void run() {
		
		while (true) {

			// Read message
			try {
				int numReaders = mb.rwLock.acquireReadLock();
				String message = mb.readMessage();
				Thread.sleep(50); // Simulate taking a moment to think about what I just read 5=balanced, 50=starvation
				mb.rwLock.releaseReadLock();
				Thread.sleep(50); // Simulate taking a moment to think about what I just read
				//System.out.printf("MR[%-2s] sees there are %s readers.  %s\n", id, numReaders, message);

			}
			catch (InterruptedException ie) {
				break;
			}
		}
	}
	
}