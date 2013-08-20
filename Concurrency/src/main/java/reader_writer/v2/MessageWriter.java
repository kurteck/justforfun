package reader_writer.v2;

public class MessageWriter implements Runnable {
	
	private int id;
	MessageBoard mb;
	
	public MessageWriter(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
	}
	
	
	@Override
	public void run() {
		
		int messageNumber = 1;
		while (true) {

			try {
				Thread.sleep(10000); // Simulate taking some time to think about what I just said.
				long start = System.currentTimeMillis();
				mb.rwLock.acquireWriteLock();
				long stop = System.currentTimeMillis();
				long elapsed = stop - start;
				String message = "MW[" + id + "] says:  Write lock #" + messageNumber + " acquired at " + stop + ".  Time to acquire was " + elapsed + " ms.";
				mb.writeMessage(message);
				System.out.println(message);
				System.exit(0);
				mb.rwLock.releaseWriteLock();
				messageNumber++;

			}
			catch (InterruptedException ie) {
				break;
			}
		}
		
	}
	
}