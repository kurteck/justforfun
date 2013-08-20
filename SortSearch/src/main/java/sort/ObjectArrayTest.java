package sort;

import java.util.List;

import datagen.DataGenerator;

public class ObjectArrayTest extends Sort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return null;
		}
		
		Object[] oArray = toArray(elements);
//		copyInto(oArray, elements);

		return elements;
	}
    
	
	public String getName() { 
		return "OATest";
	}
	
	public static void main(String[] args) {
		;
	}
	
}