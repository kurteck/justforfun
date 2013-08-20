import java.util.ArrayList;
import java.util.List;

import sort.*;
import datagen.GenDataFactory;
import datagen.OrderedDF;
import datagen.RandomDF;
import datastructures.Stats;

public abstract class SortTest implements SortAlgorithm {
	

	private static Stats testSort(SortAlgorithm algo, GenDataFactory factory, int numIters) {
		
		Stats stats = new Stats();
		for (int iters=0; iters < numIters; iters++) {
			ArrayList<Integer> input = factory.getIntegerData();

			System.gc();
			MemChecker memChecker = new MemChecker(stats.memory, 5);
			new Thread(memChecker).start();
			long start = System.nanoTime();
			algo.sort(input);
			long stop = System.nanoTime();
			memChecker.stop();
			stats.time.update(stop-start);
			if (!Sort.isSorted(input)) {
				System.out.println(algo + " did not sort data properly!");
			}
		}
		
		return stats;
	}

	
	public static void testStartup() {
		
		int numIters = 1;
		int numElems = (int)(Math.pow(2,20));
		
//		SortAlgorithm algorithm = new HeapSort();
//		SortAlgorithm algorithm = new JavaSort();
//		SortAlgorithm algorithm = new CountSort();
//		SortAlgorithm algorithm = new MergeSort();
//		SortAlgorithm algorithm = new RadixSort();
//		SortAlgorithm algorithm = new QuickSort();
//		SortAlgorithm algorithm = new QuickSortOA();
//		SortAlgorithm algorithm = new QuickCount();
//		SortAlgorithm algorithm = new QuickAdaptive();
//		SortAlgorithm algorithm = new QuickPrimitive();
//		SortAlgorithm algorithm = new InsertionSort();
		SortAlgorithm algorithm = new QuickChunk();
//		SortAlgorithm algorithm = new ObjectArrayTest();
		
		GenDataFactory df = new RandomDF(numElems, 0, numElems*2);
		Stats stats = testSort(algorithm, df, numIters);
		System.out.println(algorithm.getName() + "\t" + stats.time.samples[0]);
	}
	
	
	/**
	 * 
	 */
	public static void testSteadyState() {
		
		int numIters = 50;
		int numElems = (int)(Math.pow(2, 10));
		List<SortAlgorithm> algos = new ArrayList<SortAlgorithm>();
//		algos.add(new CountSort());
//		algos.add(new HeapSort());
//		algos.add(new InsertionSort());
//		algos.add(new JavaSort());
//		algos.add(new MergeSort());
//		algos.add(new QuickAdaptive());
//		algos.add(new QuickCount());
//		algos.add(new QuickPrimitive());
//		algos.add(new QuickSort());
//		algos.add(new RadixSort());
//		algos.add(new QuickSortOA());
		algos.add(new QuickChunk());
//		algos.add(new ObjectArrayTest());
		
		for (SortAlgorithm algorithm : algos) {
			System.out.println(algorithm.getName());
			GenDataFactory warmup = new RandomDF(128, 0, 128);
			testSort(algorithm, warmup, 25);
			GenDataFactory testdf = new RandomDF(numElems, 0, numElems*2);
			Stats stats = testSort(algorithm, testdf, numIters);
			System.out.println(stats.time);
			for (int i=0; i < numIters; i++) {
				System.out.println(stats.time.samples[i]);
			}
			System.out.println();
		}
	}
	
	
	
	/**
	 * 
	 */
	public static void testScalability() {

		int numIters = 10;
		int minElems = (int)(Math.pow(2,20));
		int maxElems = (int)(Math.pow(2,20));
		List<SortAlgorithm> algos = new ArrayList<SortAlgorithm>();
//		algos.add(new CountSort());
//		algos.add(new HeapSort());
//		algos.add(new InsertionSort());
		
//		algos.add(new JavaSort());
//		algos.add(new QuickSort());
//		algos.add(new QuickChunk());
//		algos.add(new RadixSort2());
//		algos.add(new RadixSort());

		
//		algos.add(new MergeSort());
//		algos.add(new QuickAdaptive());
//		algos.add(new QuickPrimitive());
//		algos.add(new QuickSort());
//		algos.add(new QuickSortOA());
//		algos.add(new ObjectArrayTest());
		
		System.out.print("Algorithm/Size\t");
		for (int numElems=minElems; numElems <= maxElems; numElems*=2) {
			System.out.print(numElems + "\t");
		}
		System.out.println();

		for (SortAlgorithm algorithm : algos) {
			System.out.print(algorithm.getName() + "\t");
			for (int numElems=minElems; numElems <= maxElems; numElems*=2) {
				GenDataFactory warmup = new RandomDF(128, 0, 128); 
				testSort(algorithm, warmup, 25); 
				GenDataFactory testdf = new RandomDF(numElems, 0, numElems*2);
				Stats stats = testSort(algorithm, testdf, numIters);
				System.out.print(stats.time.avg + "\t");
				System.out.println(stats.time);
			}
			System.out.println();
		}
		
		
//		System.out.print("Algorithm/Size\t");
//		for (int numElems=minElems; numElems <= maxElems; numElems++) {
//			System.out.print(numElems + "\t");
//		}
//		System.out.println();
//
//		for (SortAlgorithm algorithm : algos) {
//			System.out.print(algorithm.getName() + "\t");
//			for (int numElems=Elems; numElems <= maxElems; numElems++) {
//				GenDataFactory warmup = new RandomDF(128, 0, 128);
//				testSort(algorithm, warmup, 25);
//				GenDataFactory testdf = new RandomDF(numElems, 0, 262144);
//				Stats stats = testSort(algorithm, testdf, numIters);
//				System.out.print(stats.avg + "\t");
//			}
//			System.out.println();
//		}
		
		
	}
	

	

	
	
	
	public static void runAllTests() {

		int numIters = 10;
		int numElems = 1;
		SortAlgorithm[] algos = {
			new InsertionSort(),
			new BubbleSort(),
			new CountSort(),
			new RadixSort(),
			new QuickSort(),
			new MergeSort(),
			new HeapSort(),
			new SelectionSort()
		};
		
		GenDataFactory[] factories = {
			new OrderedDF(numElems, 0, 100, true)
//			new RandomData(numElems, 0, numElems*2),
//			new SawtoothWaveDF(256, 0, numElems/256, true),
//			new TriangleWaveDF(257, 0, numElems/512),
//			new SquareWaveData(10, 256, 20, numElems/256),
//			new SineWaveData(10, 8, 20, numElems/8),
//			new PartiallySortedDF(numElems, 0, 2, true)
		};
		
		Stats stats  = null;
//		System.out.printf("%-10s   %-7s   %-15s   %5s  %5s  %5s\n", "Input Data", "Size", "Algorithm", "Min", "Max", "Avg");
//		System.out.printf("%-10s   %-7s   %-15s   %5s  %5s  %5s\n", "----------", "-------", "---------------", "---", "---", "---");

		// Print Table Header
		System.out.print("Input/Algo\t");
		for (int i=0; i < factories.length; i++) {
			System.out.print(factories[i].getName() + "\t");
		}
		System.out.println();

		for (int i=0; i < algos.length; i++) {

			SortAlgorithm algorithm = algos[i];
			System.out.print(algorithm.getName() + "\t");

			for (int j=0; j < factories.length; j++) {
				GenDataFactory dataFactory = factories[j];
				stats = testSort(algorithm, dataFactory, numIters);
				System.out.print(stats.time.avg + "\t" + stats.time.min + "\t" + stats.time.max);
//				System.out.printf("%-10s   %-7s   %-15s   %5s  %5s  %5s\n", dataFactory.getName(), dataFactory.getSize(), algorithm.getName(), stats.min, stats.max, stats.avg);
			}
			System.out.println();
		}
	}


	public static void testMemory() {

		int numIters = 3;
		int numElems = (int)(Math.pow(2,15));
		List<SortAlgorithm> algos = new ArrayList<SortAlgorithm>();
//		algos.add(new CountSort());
//		algos.add(new RadixSort());
//		algos.add(new RadixSort2());
		algos.add(new MergeSort());
//		algos.add(new QuickChunk());
//		algos.add(new InsertionSort());

		for (SortAlgorithm algorithm : algos) {
			System.out.println(algorithm.getName());
			GenDataFactory warmup = new RandomDF(128, 0, 128); 
			testSort(algorithm, warmup, 25); 
			GenDataFactory testdf = new RandomDF(numElems, 0, numElems*2);
			Stats stats = testSort(algorithm, testdf, numIters);
//			System.out.println("Time Test");
//			for (int i=0; i < stats.time.runs; i++) {
//				System.out.println("#" + i + "\tElapsed Time:\t"  + stats.time.samples[i]);
//			}
//			System.out.println("Avg:" + "\tAverage Time:\t"  + stats.time.avg);
//			System.out.println("Initial Free Memory\t" + stats.memory.freeMemory.first + "\tInitial Max Memory:\t" + stats.memory.maxMemory.first + "\tInitial Used Memory\t" + stats.memory.usedMemory.first + "\tUsed Min\t" + stats.memory.usedMemory.min + "\tUsed Avg\t" + stats.memory.usedMemory.avg + "\tUsed Max\t" + stats.memory.usedMemory.max);
			for (int i=0; i < stats.memory.usedMemory.runs; i++) {
				System.out.println(stats.memory.usedMemory.samples[i]);
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) {
		testMemory();
//		testStartup();
//		testSteadyState();
//		testScalability();

	}
	
}