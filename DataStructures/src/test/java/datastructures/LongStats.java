package datastructures;

public class LongStats {

	public static final int DEFAULT_MAX_RUNS = 2000;
	
	boolean dropHiLo = true;
	public int runs  = 0;
	public long min  = Long.MAX_VALUE;
	public long max  = Long.MIN_VALUE;
	public long tot  = 0;
	public long avg  = 0;
	public long last = 0;
	public long first = 0;
	public long samples[];
	
	public LongStats() {
		this(true);
	}
	
	public LongStats(boolean dropHiLo) {
		this.samples  = new long[DEFAULT_MAX_RUNS];
		this.dropHiLo = dropHiLo;
	}
	
	
	public String toString() {
		return "First: " + first + " Last: " + last + " Min: " + min + " Max: " + max + " Avg: " + avg;
	}
	
	public void update(long aSample) {
		
		if (runs == 0) {
			first = aSample;
		}
		
		this.samples[runs] = aSample;

		runs++;
		tot += aSample;
		last = aSample;
		if (aSample < min) {
			min = aSample;
		}
		if (aSample > max) {
			max = aSample;
		}
		
		if (runs > 2 && dropHiLo) {
			avg = (tot - min - max) / (runs - 2);
		}
		else {
			avg = tot / runs;
		}
	}
}