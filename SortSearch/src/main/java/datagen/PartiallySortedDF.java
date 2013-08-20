package datagen;
import java.util.ArrayList;


public class PartiallySortedDF implements GenDataFactory {

	public static final String NAME = "Partially";
	
	private int num;
	private int start;
	private int jitter;
	private boolean ascending;
	
	public PartiallySortedDF(int num, int start, int jitter, boolean ascending) {
		this.num = num;
		this.start = start;
		this.jitter = jitter;
		this.ascending = ascending;
	}
	
	public int[] getIntData() {
		return DataGenerator.getPartiallySortedData(num, start, jitter, ascending);
	}
	
	public ArrayList<Integer> getIntegerData() {
		return DataGenerator.toArrayList(getIntData());
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getNum() { 
		return num;
	}

	public int getSize() { 
		return num;
	}
	
	public int getStart() {
		return start;
	}

	public int getJitter() {
		return jitter;
	}
	
	public boolean isAscending() { 
		return ascending;
	}
	
	public String getDescription() {
		String sAscending = ascending ? "Ascending" : "Descending";
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tStart:\t" + start + "\tJitter %:\t" + jitter + "\tOrder:\t" + sAscending;
	}
	
	public String toString() { 
		return getDescription();
	}
}