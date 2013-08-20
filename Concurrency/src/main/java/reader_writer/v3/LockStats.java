package reader_writer.v3;

public class LockStats {
	
	int numReading;
	long lockRequestTime;
	long lockAcquireTime;
	long lockReleaseTime;
	Thread lockOwner;
}