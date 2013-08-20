package datagen;
import java.util.ArrayList;


public class SineWaveDF implements GenDataFactory {

	public static final String NAME = "Sinusoidal";
	
	private int amplitude;
	private int wavelength;
	private int start;
	private int repeats;
	
	public SineWaveDF(int amplitude, int wavelength, int start, int repeats) {
		this.amplitude = amplitude;
		this.wavelength = wavelength;
		this.start = start;
		this.repeats = repeats;
	}
	
	public int[] getIntData() {
		return DataGenerator.getSineWave(amplitude, wavelength, start, repeats);
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