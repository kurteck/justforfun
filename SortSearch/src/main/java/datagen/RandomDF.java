package datagen;
import java.util.ArrayList;


public class RandomDF implements GenDataFactory {

	public static final String NAME = "Random";
	
	private int num;
	private int min;
	private int max;
	
	public RandomDF(int num, int min, int max) {
		this.num = num;
		this.min = min;
		this.max = max;
	}
	
	public int[] getIntData() {
		return DataGenerator.getRandomData(num, min, max);
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
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return min;
	}

	public String getDescription() {
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tMin:\t" + min + "\tMax:\t" + max;
	}
	
	public String toString() { 
		return getDescription();
	}
}