package datagen;
import java.util.ArrayList;


public class OrderedDF implements GenDataFactory {

	public static final String NAME = "Ordered";
	
	private int num;
	private int start;
	private int uniqueness;
	private boolean ascending;
	
	public OrderedDF(int num, int start, int uniqueness, boolean ascending) {
		this.num = num;
		this.start = start;
		this.uniqueness = uniqueness;
		this.ascending = ascending;
	}
	
	public int[] getIntData() {
		return DataGenerator.getOrderedData(num, start, uniqueness, ascending);
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

	public int getUniqueness() {
		return uniqueness;
	}
	
	public boolean isAscending() { 
		return ascending;
	}
	
	public String getDescription() {
		String sAscending = ascending ? "Ascending" : "Descending";
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tStart:\t" + start + "\tUnique %:\t" + uniqueness + "\tOrder:\t" + sAscending;
	}
	
	public String toString() { 
		return getDescription();
	}
}