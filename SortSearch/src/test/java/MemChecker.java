import datastructures.MemStats;

public class MemChecker implements Runnable {
	
	private boolean keepAlive;
	private MemStats memStats;
	private long sleepTime;
	
	public MemChecker(MemStats memStats, long sleepTime) {
		this.memStats  = memStats;
		this.keepAlive = true;
		this.sleepTime = sleepTime;
	}
	
	public MemStats getStats() {
		return memStats;
	}
	
	public void run() {
		
		Runtime rt = Runtime.getRuntime();
		while (keepAlive) {
			try {
				memStats.update(rt.totalMemory(), rt.freeMemory(), rt.maxMemory());
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException ie) {
				keepAlive=false;
			}
		}

		memStats.update(rt.totalMemory(), rt.freeMemory(), rt.maxMemory());
	}
	
	public void stop() {
		keepAlive = false;
	}
	
	public void setAlive() {
		keepAlive = true;
	}
}