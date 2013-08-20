package sort;

import java.util.List;
import java.util.PriorityQueue;

import datagen.DataGenerator;

public class QuickChunk extends Sort implements SortAlgorithm {
	
	public static final int INSERTSORT_THRESHOLD    = 17;
	public static final int TWO_WAY_MERGE_THRESHOLD = (1 << 16);
	public static final int N_WAY_MERGE_THRESHOLD   = (1 << 19);
	public static final int N_WAY_CHUNK_SIZE        = (1 << 15);
	
	private int isThresh;
	
	public QuickChunk() {
		this(INSERTSORT_THRESHOLD);
	}
	
	public QuickChunk(int isThresh) {
		this.isThresh = isThresh;
	}
	
	/**
	 * This method always converts the given list to an Object[]before sorting.  
	 * Empirical testing shows that the overhead 1) Is small, 2) Only affects input 
	 * of size <= 16, and 3) Is quickly overtaken by the performance gains thereafter 
	 * Additionally the performance penalty for input sizes < 16 is negated since the 
	 * InsertionSort optimization for small arrays is used (see performance analysis below.)
	 * 
	 * 
	 * Algorithm/Size	1		2		4		8		16		32		64		128		256		512		1024	2048	4096	8192	16384		32768		65536		131072		262144		524288		1048576
	 * QS-IS0			12239	12897	14608	16231	21714	32287	55011	106995	220264	464393	995819	2144432	4610071	9968893	21223402	45497082	96873268	215371602	524997819	1194902301	2719710740
	 * QS-IS0-OA		5483	14696	16406	17196	20486	27286	41280	69049	130465	262948	563756	1200685	2504770	5290562	11221168	24064557	51202598	119381848	302725894	712063377	1634398271
     * InsertionSort-OA 6009	14915	15661	16099	18249	23513	44088	112128
	 */
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		int numElems = elements.size();
		if (elements == null || numElems <=1) {
			return elements;
		}
		else if (numElems <= INSERTSORT_THRESHOLD) {
			insertSort(elements);
			return elements;
		}
		else if (numElems >= N_WAY_MERGE_THRESHOLD) {
			nWayQuickMergeSort(elements);
			return elements;
		}
		else if (numElems >= TWO_WAY_MERGE_THRESHOLD) {
			twoWayQuickMergeSort(elements);
			return elements;
		}
		else {
			Object[] oArray = toArray(elements);
			sort(oArray, 0, numElems-1);
			copyInto(oArray, elements);
			return elements;
		}
	}
	


	/**
	 * @param elements
	 */
	private <T extends Comparable<? super T>> void insertSort(List<T> elements) {
		int numElems = elements.size();
		Object[] oArray = toArray(elements);
		InsertionSort.sort(oArray, 0, numElems-1);
		copyInto(oArray, elements);
	}


	/**
	 * @param elements
	 */
	private <T extends Comparable<? super T>> void twoWayQuickMergeSort(List<T> elements) {
		int numElems = elements.size();
		int mid = numElems >> 1;
		Object[] oArray1 = toArray(elements, 0, mid-1);
		Object[] oArray2 = toArray(elements, mid, numElems-1);
		sort(oArray1, 0, oArray1.length-1);
		sort(oArray2, 0, oArray2.length-1);
		merge(oArray1, oArray2, elements);
	}

	
	@SuppressWarnings({ "unchecked" })
	private <T extends Comparable<? super T>> void nWayQuickMergeSort(List<T> elements) {
		
		int numElems  = elements.size();
		int numExtra  = numElems % N_WAY_CHUNK_SIZE;
		int numChunks = numElems / N_WAY_CHUNK_SIZE + (numExtra > 0 ? 1 : 0);
		Object[][] chunks = new Object[numChunks + (numExtra > 0 ? 1 : 0)][N_WAY_CHUNK_SIZE];


		// split and sort
		for (int i=0; i < numChunks; i++) {
			int start = i * N_WAY_CHUNK_SIZE;
			int end   = (i+1) * N_WAY_CHUNK_SIZE-1; 
			if (end < numElems) {
				chunks[i] = toArray(elements, start, end);
				sort(chunks[i], 0, N_WAY_CHUNK_SIZE-1);
			}
			else {
				chunks[i] = toArray(elements, start, numElems-1);
				sort(chunks[i], 0, numExtra-1);
			}
		}
		
		
		// multi-merge
		PriorityQueue<ChunkNode> pq = new PriorityQueue<ChunkNode>(); 
		for (int i=0; i < numChunks; i++) {
			pq.add(new ChunkNode(chunks[i][0], i, 0));
		}
		
		for (int i=0; i < numElems; i++) {
			ChunkNode min = pq.remove();
			elements.set(i, (T)min.value);
			min.arrayIdx++;
			if (min.arrayIdx < chunks[min.chunkIdx].length) {
				min.value=chunks[min.chunkIdx][min.arrayIdx];
				pq.add(min);
			}
		}
	}
	
	
	
	
	
	
	private void sort(Object[] elements, int start, int end) {

		if (start >= end || start < 0 || end < 0 || end > elements.length-1) {
			return;
		}
		
		if (end-start <= isThresh) {
			InsertionSort.sort(elements, start, end);
			return;
		}

		int right = partition(elements, start, end);
		sort(elements, right+1, end);
        sort(elements, start, right-1);
    }
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static int partition(Object[] elements, int start, int end) {
		
        int left  = start;
        int right = end + 1;
		int pIndx = (int)(Math.random() * (end-start+1)) + start;
        Comparable pivot = (Comparable)elements[pIndx];
        swap(elements, start, pIndx);
        
        while (true) { 

            // Search for left element that is greater than or equal to pivot
        	while (((Comparable)elements[++left]).compareTo(pivot) < 0) {
	        	if (left >= end) {
	               	break;
	        	}
        	}
        	
            // Search for right element that is less than or equal to pivot
        	while (((Comparable)elements[--right]).compareTo(pivot) > 0) {
	        	if (right <= start) {
	               	break;
	        	}
        	}

        	// Ensure we are still within the boundaries of this list
            if (left >= right) {
            	break;
            }
            
            swap(elements, left, right);
        }

        // put pivot into correct place
        swap(elements, start, right);

        // and return its index so that we can continue partitioning
        return right;
    }

	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void merge(Object[] oArray1, Object[] oArray2, List elements) {
		
		int elemsIdx  = 0;
		int array1Idx = 0;
		int array2Idx = 0;
		int array1Len = oArray1.length;
		int array2Len = oArray2.length;
		while (array1Idx < array1Len && array2Idx < array2Len) {
			Comparable c1 = (Comparable)oArray1[array1Idx];
			Comparable c2 = (Comparable)oArray2[array2Idx];
			if (c1.compareTo(c2) <= 0) {
				array1Idx++;
				elements.set(elemsIdx++, c1);
			}
			else {
				array2Idx++;
				elements.set(elemsIdx++, c2);
			}
		}

		while (array1Idx < array1Len) elements.set(elemsIdx++, oArray1[array1Idx++]);
		while (array2Idx < array2Len) elements.set(elemsIdx++, oArray2[array2Idx++]);
	}
	
	public String getName() { 
		return "QS-IS" + isThresh + "-CT" + (N_WAY_MERGE_THRESHOLD/1024) + ".CS" + (N_WAY_CHUNK_SIZE/1024);
	}
	
	public static void main(String[] args) {

		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getOrderedData(30, 29, 100, false));
		System.out.println(data);
		QuickChunk qs = new QuickChunk();
		qs.sort(data);
		System.out.println(data);
		System.out.println("isSorted: " + isSorted(data));
	}
	
}