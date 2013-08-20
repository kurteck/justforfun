package reader_writer.v3;

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
			
			while (keepAlive) {
				Thread.sleep(250); // Wait a bit so that readers can process what was written or spool up
				LockStats stats = mb.rwLock.acquireWriteLock();
				long elapsed = stats.lockAcquireTime - stats.lockRequestTime;
				String message = "MW[" + id + "] says:  Write lock #" + messageNumber + " acquired at " + stats.lockAcquireTime + ".  Time to acquire was " + elapsed + " ms.";
				mb.writeMessage(message);
				System.out.println(message);
				Thread.sleep(1000);
				mb.rwLock.releaseWriteLock();
				messageNumber++;
				if (messageNumber > 10) {
					keepAlive = false;
				}
			}
		}
		catch (InterruptedException ie) {
			return;
		}
	}
	
}