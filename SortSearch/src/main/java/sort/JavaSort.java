package sort;
import java.util.Collections;
import java.util.List;

public class JavaSort implements SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		return sortStatic(elements);
	}
	
	public static <T extends Comparable<? super T>> List<T> sortStatic(List<T> elements) {
		Collections.sort(elements);
		return elements;
	}
	
	public String getName() {
		return "JavaSort";
	}
	
}