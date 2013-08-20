package reader_writer.v4;

public class LockStats {
	
	int numReading;
	long lockRequestTime;
	long lockAcquireTime;
	long lockReleaseTime;
	Thread lockOwner;
}