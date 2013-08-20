package sort;
import java.util.List;

public abstract class Sort implements SortAlgorithm {
	
	public static final boolean isSorted(int[] data) {

		Integer last = data[0];
		Integer curr = null;
		for (int i=1; i < data.length; i++) {
			curr = data[i];
			if (curr < last) {
				return false;
			}
			last = curr;
		}
		
		return true;
	}
	

	/**
	 * @param elements
	 * @return
	 */
	public static final <T extends Comparable<? super T>> boolean isSorted(List<T> elements) {
		
		if (elements == null) {
			return true;
		}
		
		int numElems = elements.size();
		if (numElems <= 0) {
			return true;
		}

		T last = elements.get(0);
		T curr = null;
		for (int i=1; i < numElems; i++) {
			curr = elements.get(i);
			if (curr.compareTo(last) < 0) {
				return false;
			}
			last = curr;
		}
		
		return true;
	}
	
	
	@SuppressWarnings ({"rawtypes", "unchecked"})
	public static final boolean isSorted(Object[] elements) {
		
		if (elements == null) {
			return true;
		}
		
		int numElems = elements.length;
		if (numElems <= 0) {
			return true;
		}

		Comparable last = (Comparable)elements[0];
		Comparable curr = null;
		for (int i=1; i < numElems; i++) {
			curr = (Comparable)elements[i];
			if (curr.compareTo(last) < 0) {
				return false;
			}
			last = curr;
		}
		
		return true;
	}


	/**
	 * Testing has shown this method is faster than List.toArray() 
	 * 
	 * @param elements
	 * @return
	 */
	protected static final Object[] toArray(List<?> elements) {
		int numElems = elements.size();
		Object[] oArray = new Object[numElems];
		for (int i=0; i < numElems; i++) {
			oArray[i] = elements.get(i);
		}
		return oArray;
	}
	

	/**
	 * Testing has shown this method is faster than List.toArray() 
	 * 
	 * @param elements
	 * @return
	 */
	protected static final Object[] toArray(List<?> elements, int start, int end) {
		int length = end-start+1;
		Object[] oArray = new Object[length];
		for (int i=0; i < length; i++) {
			oArray[i] = elements.get(i+start);
		}
		return oArray;
	}

	/**
	 * Testing has shown this method is faster than Arrays.asList(oArray)
	 *
	 * @param from
	 * @param to
	 */
	@SuppressWarnings ("unchecked")
	protected static final <T> void copyInto(Object[] from, List<T> to) {
		int numElems = from.length;
		for (int i=0; i < numElems; i++) {
			to.set(i, (T)from[i]);
		}
	}

	@SuppressWarnings ("unchecked")
	protected static final <T> void copyInto(Object[] from, List<T> to, int toStart) {
		int numElems = from.length;
		for (int i=0; i < numElems; i++) {
			to.set(i+toStart, (T)from[i]);
		}
	}

	
	public static final <T> void swap(List<T> elements, int left, int right) {
		
		T temp = elements.get(left);
		elements.set(left, elements.get(right));
		elements.set(right, temp);
	}

	public static final void swap(Object[] elements, int left, int right) {
		
		Object temp = elements[left];
		elements[left] = elements[right];
		elements[right] = temp;
	}

	public static final void swap(int[] elements, int left, int right) {
		
		int temp = elements[left];
		elements[left] = elements[right];
		elements[right] = temp;
	}
}