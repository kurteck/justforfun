package reader_writer.v4;

import java.util.concurrent.Semaphore;

public class RWLock {
	
	private Semaphore readLock;
	private Semaphore writeLock;

	
	public RWLock() {
		readLock   = new Semaphore(1);
		writeLock  = new Semaphore(1);
	}


	/**
	 * @throws InterruptedException
	 */
	public LockStats waitForWriters()
	throws InterruptedException {
		
		LockStats stats = new LockStats();

		stats.lockOwner = Thread.currentThread();
		stats.lockRequestTime = System.currentTimeMillis();

		// Acquiring a readLock+writeLock block all in-coming Readers when a write is in progress.
		// Note that the writeLock can block reads during a write on its own; however, we have added
		// the readLock so that at most 1 Reader and is contending for the writeLock at any one time.  
		// This gives Writers write preference and also helps prevent their starvation in situations 
		// where the number of Readers far out-number Writers.
		readLock.acquire();
		writeLock.acquire(); 
		stats.lockAcquireTime = System.currentTimeMillis();

		// Releasing here now that writer is finished will allow all blocked readers to proceed.
		writeLock.release();
		readLock.release();
		stats.lockReleaseTime = System.currentTimeMillis();
		
		return stats;
	}

	
	/**
	 * @throws InterruptedException
	 */
	public LockStats acquireWriteLock()
	throws InterruptedException {

		LockStats stats = new LockStats();

		stats.lockOwner = Thread.currentThread();
		stats.lockRequestTime = System.currentTimeMillis();
		writeLock.acquire(); // lock out other writers & reading
		stats.lockAcquireTime = System.currentTimeMillis();

		return stats;
	}
	
	
	/**
	 * @throws InterruptedException
	 */
	public void releaseWriteLock() {
		writeLock.release(); // give writers opportunity to change message
	}
}