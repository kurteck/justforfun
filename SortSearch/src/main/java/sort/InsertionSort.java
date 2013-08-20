package sort;

import java.util.List;

/**
 * This class is an Adaptive version of InsertionSort which converts the 
 * given list to an Object[] before sorting for input > 16 (see performance chart below).
 * 
 * A small performance penalty is incurred on arrays of roughly less than 10 elements
 * due to the logic to make this performance optimization.  Since algorithms such as QuickSort 
 * often uses InsertSort as a performance optimization for very small arrays, the sort(Object[] elements) 
 * method may be called directly to avoid the performance penalty.
 * 
 * Algorithm/Size	1		2		3		4		5		6		7		8		9		10		11		12		13		14		15		16		17		18		19		20		21		22		23		24		25		26		27		28		29		30		31		32		33		34		35		36		37		38		39		40		41		42		43		44		45		46		47		48		49		50		51		52		53		54		55		56		57		58		59		60		61		62		63		64
 * InsertionSort	5352	12634	12809	12634	13818	13423	13818	15880	14038	15573	16231	16319	16450	17328	18381	18644	20004	20749	21408	22153	22636	24917	25970	26584	28207	28032	30532	31015	32067	34831	35709	35797	39569	39920	40447	43824	43868	46763	47422	48738	49133	49396	49878	53344	56722	56897	58827	62117	64574	64706	68566	67382	73699	71067	75629	82297	88658	84885	86948	93001	89535	96116	96160	100546
 * InsertionSort-OA	6009	14915	14564	15661	16494	15178	15529	16099	16319	16757	18688	18732	17942	22943	22548	18249	18995	18644	18951	18688	19390	19127	19697	20750	21056	20706	21627	22197	22548	21978	23557	23513	25268	19302	20486	20530	20530	22943	23294	24303	23776	23908	27066	24785	25619	25882	26540	26233	28207	27856	29084	29962	30708	30532	32594	31409	32594	33822	35270	35402	36016	36849	37814	37420
 * InsertionSort-A	6229	16757	16406	16758	18073	19697	21627	27023	14125	15617	15178	16406	18073	17152	18468	18381	16670	17328	17503	18424	18951	19170	19170	19653	21452	21188	20925	22768	22504	22943	24127	24347	22943	23469	23996	24697	24742	24215	27286	25794	28163	28953	27944	32594	34612	28514	30400	30357	31717	32463	32682	32638	34173	35709	36893	37551	37902	38253	39438	43956	42245	43868	46851	44438
 */
public class InsertionSort extends Sort implements SortAlgorithm {

	public static final int OBJECT_ARRAY_THRESHOLD = 16;
	
	/**
	 * 
	 */
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		if (elements == null) {
			return elements;
		}

		int length = elements.size();
		if (length <= 1) {
			return elements;
		}
		else if (length > OBJECT_ARRAY_THRESHOLD) {
			Object[] oArray = toArray(elements);
			sort(oArray, 0, length-1);
			copyInto(oArray, elements);
			return elements;
		}
		else {
			sortNoChecks(elements, 0, length-1);
			return elements;
		}
	}
	

	/**
	 * @param elements
	 * @param start
	 * @param end
	 */
	public static <T extends Comparable<? super T>> void sort(List<T> elements, int start, int end) {

		if (elements == null) {
			return;
		}
		
		int length = end-start+1;
		if (length <= 1) {
			return;
		}
		else {
			sortNoChecks(elements, 0, length-1);
			return;
		}
	}

	
	/** 
	 * Internal helper method that simply sorts the array without boundary or optimization checks
	 *  
	 * @param elements
	 * @param start
	 * @param end
	 */
	protected static <T extends Comparable<? super T>> void sortNoChecks(List<T> elements, int start, int end) {
		for (int i=start+1; i <= end; i++) {
			
			int insertPos = i;
			T toInsert = elements.get(i);

			// Keep shifting elements until we find the place to insert
			while (insertPos > start && (toInsert.compareTo(elements.get(insertPos-1)) < 0)) {
				elements.set(insertPos, elements.get(insertPos-1));
				insertPos--;
			}
	
			// Now perform the insert if we have moved this element up
			if (i != insertPos) {
				elements.set(insertPos, toInsert);
			}
		}
	}

	
	
	/**
	 * @param elements
	 * @param start
	 * @param end
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sort(Object[] elements, int start, int end) {
		
		if (elements == null || elements.length <= 1) {
			return;
		}
		
		for (int i=start; i <= end; i++) {
			
			int insertPos = i;
			Comparable toInsert = (Comparable)elements[i];

			// Keep shifting elements until we find the place to insert
			while (insertPos > 0 && toInsert.compareTo(elements[insertPos-1]) < 0) {
				elements[insertPos] = elements[insertPos-1];
				insertPos--;
			}
	
			// Now perform the insert if we have moved this element up
			if (i != insertPos) {
				elements[insertPos] = toInsert;
			}
		}
	}

	
	
	/**
	 * @param elements
	 * @param start
	 * @param end
	 */
	public static void sort(int[] elements, int start, int end) {
		
		if (elements == null || elements.length <= 1) {
			return;
		}
		
		for (int i=start; i <= end; i++) {
			
			int insertPos = i;
			int toInsert  = elements[i];

			// Keep shifting elements until we find the place to insert
			while (insertPos > 0 && toInsert < elements[insertPos-1]) {
				elements[insertPos] = elements[insertPos-1];
				insertPos--;
			}
	
			// Now perform the insert if we have moved this element up
			if (i != insertPos) {
				elements[insertPos] = toInsert;
			}
		}
	}
	
	
	
	
	public String getName() { 
		return "InsertionSort";
	}

	
	public static void main(String[] args) {
		;
	}
	
}