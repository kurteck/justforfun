package datagen;
import java.util.ArrayList;


public class TriangleWaveDF implements GenDataFactory {

	public static final String NAME = "Triangle";
	
	private int amplitude;
	private int start;
	private int repeats;
	
	public TriangleWaveDF(int amplitude, int start, int repeats) {
		this.amplitude = amplitude;
		this.start = start;
		this.repeats = repeats;
	}
	
	public int[] getIntData() {
		return DataGenerator.getTriangleWave(amplitude, start, repeats);
	}
	
	public ArrayList<Integer> getIntegerData() {
		return DataGenerator.toArrayList(getIntData());
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getSize() { 
		return getWavelength() * repeats;
	}

	public int getWavelength() { 
		return (amplitude << 1) - 2;
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
	
	public String getDescription() {
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tAmplitude:\t" + amplitude + "\tStart:\t" + start + "\tRepeats:\t" + repeats;
	}
	
	public String toString() { 
		return getDescription();
	}
}