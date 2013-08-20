package datagen;
import java.util.ArrayList;


public class SemiData implements GenDataFactory {

	public static final String NAME = "Semi";
	
	private int num;
	private int pctAscend;
	
	public SemiData(int num, int pctAscend) {
		this.num = num;
		this.pctAscend = pctAscend;
	}
	
	public int[] getIntData() {
		return DataGenerator.getSemiSortedInts(num, pctAscend);
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
	

	public String getDescription() {
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tPctAscend:\t" + pctAscend;
	}
	
	public String toString() { 
		return getDescription();
	}
}