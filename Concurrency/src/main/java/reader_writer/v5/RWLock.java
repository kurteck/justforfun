package reader_writer.v5;

import java.util.concurrent.Semaphore;

public class RWLock {
	
	private int numReading;
	private Semaphore readLock;
	private Semaphore writeLock;

	
	public RWLock() {
		numReading = 0;
		readLock   = new Semaphore(1);
		writeLock  = new Semaphore(1);
	}


	/**
	 * @throws InterruptedException
	 */
	public LockStats acquireReadLock()
	throws InterruptedException {
		
		LockStats stats = new LockStats();

		stats.lockOwner = Thread.currentThread();
		stats.lockRequestTime = System.currentTimeMillis();

		readLock.acquire();
		stats.lockAcquireTime = System.currentTimeMillis();

		numReading++;
		stats.numReading = numReading;	
	
		// Wait while write is in progress.  Blocks all in-coming readers
		writeLock.acquire(); // If a writer has declared that he wants to write, then block all subsequent readers.
		writeLock.release(); // Release writeLock so that everyone may proceed after writer finishes.
		readLock.release();
		stats.lockReleaseTime = System.currentTimeMillis();
		
		return stats;
	}

	
	/**
	 * @throws InterruptedException
	 */
	public LockStats releaseReadLock()
	throws InterruptedException {

		LockStats stats = new LockStats();

		stats.lockOwner = Thread.currentThread();
		stats.lockRequestTime = System.currentTimeMillis();
		readLock.acquire();
		stats.lockAcquireTime = System.currentTimeMillis();
		numReading--;
		stats.numReading = numReading;	
		stats.lockReleaseTime = System.currentTimeMillis();
		readLock.release();
		
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
		writeLock.acquire(); // lock out other writers
//		readLock.acquire();  // lock out readers
		stats.lockAcquireTime = System.currentTimeMillis();

		return stats;
	}
	
	
	/**
	 * @throws InterruptedException
	 */
	public void releaseWriteLock()
	throws InterruptedException {
		
//		readLock.release();  // let readers see what was written
		writeLock.release(); // give writers opportunity to change message
	}
}