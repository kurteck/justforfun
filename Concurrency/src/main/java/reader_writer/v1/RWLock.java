package reader_writer.v1;

public class RWLock {
	
	private int numReading;
	private Object readLock;
	private Object writeLock;
	private boolean writeLockWait;
	
	public RWLock() {
		numReading = 0;
		readLock   = new Object();
		writeLock  = new Object();
		writeLockWait = false;
	}


	/**
	 * @throws InterruptedException
	 */
	public LockStats acquireReadLock()
	throws InterruptedException {
		
		LockStats stats = new LockStats();

		stats.lockOwner = Thread.currentThread();
		stats.lockRequestTime = System.currentTimeMillis();

		synchronized (readLock) {
			stats.lockAcquireTime = System.currentTimeMillis();

			numReading++;
			stats.numReading = numReading;
		
			// Wait while write is in progress.  Blocks all in-coming readers
			synchronized (writeLock) {
				while (writeLockWait) {
					writeLock.wait();
				}
			}
		}
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

		// block other readers while this thread decrements the read count
		synchronized (readLock) {
			stats.lockAcquireTime = System.currentTimeMillis();
			numReading--;
			stats.numReading = numReading;	
		}
		
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
		
		// block other writers so this thread can write
		synchronized (writeLock) {
			while (writeLockWait) {
				writeLock.wait();
			}
			writeLockWait = true;
		}
		
		stats.lockAcquireTime = System.currentTimeMillis();

		return stats;
	}
	
	
	/**
	 * @throws InterruptedException
	 */
	public void releaseWriteLock()
	throws InterruptedException {
		
		synchronized (writeLock) {
			writeLockWait = false;
			writeLock.notifyAll();
		}
	}
}