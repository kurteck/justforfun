package datagen;
import java.util.ArrayList;


public class SquareWaveDF implements GenDataFactory {

	public static final String NAME = "Square";
	
	private int amplitude;
	private int wavelength;
	private int start;
	private int repeats;
	
	public SquareWaveDF(int amplitude, int wavelength, int start, int repeats) {
		this.amplitude = amplitude;
		this.wavelength = wavelength;
		this.start = start;
		this.repeats = repeats;
	}
	
	public int[] getIntData() {
		return DataGenerator.getSquareWave(amplitude, wavelength, start, repeats);
	}
	
	public ArrayList<Integer> getIntegerData() {
		return DataGenerator.toArrayList(getIntData());
	}
	
	public String getName() {
		return NAME;
	}
	
	public int getSize() { 
		return wavelength * repeats;
	}

	public int getAmplitude() {
		return amplitude;
	}

	public int getWavelength() { 
		return wavelength;
	}

	public int getStart() {
		return start;
	}

	public int getRepeats() {
		return repeats;
	}
	
	public String getDescription() {
		return "Name:\t" + NAME + "\tSize:\t" + getSize() + "\tAmplitude:\t" + amplitude + "\tWavelength:\t" + wavelength +"\tStart:\t" + start + "\tRepeats:\t" + repeats;
	}
	
	public String toString() { 
		return getDescription();
	}
}