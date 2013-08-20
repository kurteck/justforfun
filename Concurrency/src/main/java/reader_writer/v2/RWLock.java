package reader_writer.v2;

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
	public int acquireReadLock()
	throws InterruptedException {
		
		readLock.acquire();
		numReading++;
		
		// If I am the first reader, then I need to inform writers 
		// they should not write until all readers have finished.
		// Note this solution can starve writers!
		if (numReading == 1) {
			writeLock.acquire();
		}

		System.out.println("Acquire - Num Reading is " + numReading);
		int numReaders = numReading; // save # of readers before releasing lock
		readLock.release();
		
		return numReaders;
	}

	
	/**
	 * @throws InterruptedException
	 */
	public void releaseReadLock()
	throws InterruptedException {
		
		readLock.acquire();
		numReading--;
		
		// Inform writers they may write now
		if (numReading <= 0) {
			writeLock.release();
		}
		
		System.out.println("Release - Num Reading is " + numReading);
		readLock.release();
	}
	

	/**
	 * @throws InterruptedException
	 */
	public void acquireWriteLock()
	throws InterruptedException {
		writeLock.acquire();
	}
	
	
	/**
	 * @throws InterruptedException
	 */
	public void releaseWriteLock()
	throws InterruptedException {
		writeLock.release();
	}
}