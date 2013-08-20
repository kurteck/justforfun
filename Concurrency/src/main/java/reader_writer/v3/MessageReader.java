package reader_writer.v3;

public class MessageReader implements Runnable {
	
	private int id;
	private MessageBoard mb;
	private boolean keepAlive;
	
	public MessageReader(int id, MessageBoard mb) {
		this.id = id;
		this.mb = mb;
		this.keepAlive = true;
	}

	@Override
	public void run() {
		
		int numReads = 0;
		try {

			while (keepAlive) {
				LockStats stats = mb.rwLock.acquireReadLock();
				String message = mb.readMessage();
				long elapsed = stats.lockReleaseTime - stats.lockRequestTime;
				System.out.printf("MR[%-2s]  Acquired lock at %s in %s ms.  Num Readers: %s  Message: (%s)\n", id, stats.lockAcquireTime, elapsed, stats.numReading, message);
				Thread.sleep(5);
				mb.rwLock.releaseReadLock();
				
				if ((message != null) && (numReads++ >= 1000)) {
					keepAlive = false;
				}
			}
		}
		catch (InterruptedException ie) {
			return;
		}
	}
	
}