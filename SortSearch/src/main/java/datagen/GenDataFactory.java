package datagen;
import java.util.ArrayList;

public interface GenDataFactory {

	public String getName();
	public String getDescription();
	public int getSize();
	public int[] getIntData();
	public ArrayList<Integer> getIntegerData();
	
}