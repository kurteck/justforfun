
import java.util.Arrays;

public class MagicIndex
{
	public int[] makeArray(int numElems, int maxValue) {

		int[] array = new int[numElems];
		for (int i=0; i < numElems; i++) {
			int sign = 1; //(int)(Math.random() * 2) == 0 ? -1 : 1;
			array[i] = sign * (int)(Math.random() * maxValue);
		}

		Arrays.sort(array);
		return array;
	}

	public Result getMagicIndex_Linear(int[] array) {
		
		long start = System.currentTimeMillis();
		Result result = getMagicIndex_LinearHelper(array);
		result.runtime = System.currentTimeMillis() - start;

		return result;
	}
	
	public Result getMagicIndex_LinearHelper(int[] array) {
		
		int iters = 0;
		for (int i=0; i < array.length; i++) {
			iters++;
//			System.out.print(i);
			if (array[i] == i) {
				return new Result(i, iters);
			}
			else {
//				System.out.println(" = " + values[i]);
				i = Math.max(i, array[i]-1);
			}
		}

		return new Result(-1, iters);
	}
	
	
	
	
	public Result getMagicIndex_Binary(int[] array) {

		long start = System.currentTimeMillis();
		Result result = getMagicIndex_Binary(array, 0, array.length - 1, 0);
		result.runtime = System.currentTimeMillis() - start;

		return result;
	}
	
	public Result getMagicIndex_Binary(int[] array, int lo, int hi, int iters) {

		iters++;
		if (lo > hi || lo < 0 || hi >= array.length) {
			return new Result(-1, iters);
		}
		
		int midIdx = (lo + hi) / 2;
		int midVal = array[midIdx];
		
		if (midIdx == midVal) {
			return new Result(midIdx, iters);
		}
		
		int newHi = Math.min(midIdx - 1, midVal);
		Result left = getMagicIndex_Binary(array, lo, newHi, iters);
		if (left.index >= 0) {
			return left;
		}
		
		int newLo = Math.max(midIdx + 1, midVal);
		Result right = getMagicIndex_Binary(array, newLo, hi, iters);
		return right;
	}

	
	private class Result {
		
		int iters;
		int index;
		long runtime;
		
		public Result(int index, int iters) {
			this.index = index;
			this.iters = iters;
		}
	}
	

	public void printArray(int[] array) {
		for (int i=0; i < array.length; i++) {
			System.out.printf("%-3s", i);
		}
		System.out.println();
		for (int i=0; i < array.length; i++) {
			System.out.printf("%-3s", array[i]);
		}
		System.out.println();
	}
	
	
	public static void main(String[] args) {

		int totBinIters = 0;
		int totLinIters = 0;
		long totBinRuntime = 0;
		long totLinRuntime = 0;
		
		MagicIndex mi = new MagicIndex();

		int numIters = 100;
		for (int i=0; i < numIters; i++) {
			int[] array = mi.makeArray(1000000, 10000000);
			Result binResult = mi.getMagicIndex_Binary(array);
			Result linResult = mi.getMagicIndex_Linear(array);
			totBinIters += binResult.iters;
			totLinIters += linResult.iters;
			totBinRuntime += binResult.runtime;
			totLinRuntime += linResult.runtime;
		}
		
		System.out.println("Binary Iters: " + totBinIters + " Avg(" + (totBinIters/numIters) + ") Runtime: " + totBinRuntime);
		System.out.println("Linear Iters: " + totLinIters + " Avg(" + (totLinIters/numIters) + ") Runtime: " + totLinRuntime);
	}
}