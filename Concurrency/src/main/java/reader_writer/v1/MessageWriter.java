package reader_writer.v1;

public class MessageWriter implements Runnable {
	
	private int id;
	private MessageBoard mb;
	private boolean keepAlive;
	
	public MessageWriter(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
		this.keepAlive = true;
	}
	
	
	@Override
	public void run() {
		
		int messageNumber = 1;
		try {
			Thread.sleep(500); // Wait a minute so that all reader threads can spool up
			
			while (keepAlive) {
				LockStats stats = mb.rwLock.acquireWriteLock();
				long elapsed = stats.lockAcquireTime - stats.lockRequestTime;
				String message = "MW[" + id + "] says:  Write lock #" + messageNumber + " acquired at " + stats.lockAcquireTime + ".  Time to acquire was " + elapsed + " ms.";
				mb.writeMessage(message);
				System.out.println(message);
				Thread.sleep(2000);
				mb.rwLock.releaseWriteLock();
				messageNumber++;
				if (messageNumber > 3) {
					keepAlive = false;
				}
			}
		}
		catch (InterruptedException ie) {
			return;
		}
	}
	
}