package datastructures;

public class MemStats {

	public static final int DEFAULT_MAX_SAMPLES = 5000;
	
	public LongStats totalMemory;
	public LongStats freeMemory;
	public LongStats maxMemory;
	public LongStats usedMemory;

	public MemStats() {
		totalMemory = new LongStats();
		freeMemory  = new LongStats();
		maxMemory   = new LongStats();
		usedMemory  = new LongStats();
	}

	public String toString() {
		return "First: " + freeMemory.first + " Last: " + freeMemory.last + " Min: " + freeMemory.min + " Max: " + freeMemory.max + " Avg: " + freeMemory.avg;
	}

	public void update(long totalMemory, long freeMemory, long maxMemory) {
		this.totalMemory.update(totalMemory);
		this.freeMemory.update(freeMemory);
		this.maxMemory.update(maxMemory);
		this.usedMemory.update(totalMemory - freeMemory);
	}

}