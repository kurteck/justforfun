package datagen;
import java.util.ArrayList;


public class SawtoothWaveDF implements GenDataFactory {

	public static final String NAME = "Sawtooth";
	
	private int amplitude;
	private int start;
	private int repeats;
	private boolean ascending;
	
	public SawtoothWaveDF(int amplitude, int start, int repeats, boolean ascending) {
		this.amplitude = amplitude;
		this.start = start;
		this.repeats = repeats;
		this.ascending = ascending;
	}
	
	public int[] getIntData() {
		return DataGenerator.getSawtoothWave(amplitude, start, repeats, ascending);
	}
	
	public ArrayList<Integer> getIntegerData() {
		return DataGenerator.toArrayList(getIntData());
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getSize() { 
		return amplitude*repeats;
	}

	public int getAmplitude() { 
		return amplitude;
	}

	public int getStart() {
		return start;
	}

	public int getRepeats() {
		return repeats;
	}
	
	public boolean isAscending() { 
		return ascending;
	}
	
	public String getDescription() {
		String sAscending = ascending ? "Ascending" : "Descending";
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tStart:\t" + start + "\tAmplitude\t" + "\tRepeats:\t" + repeats + "\tOrder:\t" + sAscending;
	}
	
	public String toString() { 
		return getDescription();
	}
}