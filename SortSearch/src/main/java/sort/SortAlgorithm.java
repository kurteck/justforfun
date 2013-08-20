package sort;
import java.util.List;

public interface SortAlgorithm {
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements);
	public String getName();
}