package sort;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import datagen.DataGenerator;

public class MergeSortExt2 implements SortAlgorithm {

	public int MAX_CHUNK_SIZE = 128; //1024*1024; // 1MB 
	
	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		try {
			File testData = new File("c:/temp/eclipse/mergeSortTest/test.8million.txt"); 
//			writeDataToFile(elements, testData);		
			File sorted = sortExt(testData);
//			System.out.println("Sorted Data is in file: " + sorted.getAbsolutePath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * External MergeSort for large data
	 * 
	 * @param elementsFile
	 * @return
	 */
	public File sortExt(File inputFile) 
	throws IOException {

		if (!canReadWriteFiles(inputFile)) {
			return null;
		}
		
		List<File> chunks = splitFile(inputFile, MAX_CHUNK_SIZE);
		List<File> sorted = sortFiles(chunks);
		return mergeFiles(sorted, inputFile);
	}
	

	/**
	 * Splits the given file into two "split" files.
	 * Assumes new lines are delimited by a standard line separator
	 * 
	 * @param inFile
	 * @param fileNum
	 * @return
	 * @throws IOException
	 */
	private List<File> splitFile(File inFile, int maxChunkSize) 
	throws IOException {

		List<File> chunks = new ArrayList<File>();
		
		int fileNum  = 0;
		int fileLen  = 0; 
		String aLine = null;
		String newLine  = System.getProperty("line.separator");
		int newLineSize = newLine.codePointCount(0, newLine.length());
		File splitFile    = new File(inFile.getParent() + File.separator + inFile.getName() + ".split" + fileNum + ".txt");
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(splitFile));
		while ((aLine = br.readLine()) != null) {

			int lineSizeInBytes = aLine.codePointCount(0, aLine.length()) + newLineSize; // do not assume UTF-8 encoding (1 byte per char)
//			System.out.println("Line: " + aLine+  " Size: " + lineSizeInBytes);
			boolean canLineFit  = fileLen + lineSizeInBytes <= maxChunkSize;
			if (!canLineFit) {
				chunks.add(splitFile);
				bw.close();

				fileNum++;
				fileLen = 0;
				splitFile = new File(inFile.getParent() + File.separator + inFile.getName() + ".split" + fileNum + ".txt");
				bw = new BufferedWriter(new FileWriter(splitFile));
			}

			bw.write(aLine);
			bw.newLine();
			fileLen += lineSizeInBytes;
		}
		
		chunks.add(splitFile);
		bw.close();
		br.close();
//		System.out.println("File Length: " + fileLen + " System reports: " + splitFile.length());
		
		return chunks;
	}
	

	/**
	 * @param chunks
	 * @return
	 * @throws IOException
	 */
	private List<File> sortFiles(List<File> chunks) 
	throws IOException {

		List<File> sorted = new ArrayList<File>();
		
		QuickSort sorter = new QuickSort();
		for (File inFile : chunks) {
//			System.out.println("Sorting: " + inFile);
			List<String> inputData = readDataFromFile(inFile);
			sorter.sort(inputData);
			File outFile = new File(inFile.getParent() + File.separator + inFile.getName() + ".sorted.txt");
			writeDataToFile(inputData, outFile);
			sorted.add(outFile);
		}
		
		return sorted;
	}

	
	
	/**
	 * @param inFile1
	 * @param inFile2
	 * @return
	 * @throws IOException
	 */
	private File mergeFiles(List<File> chunks, File inFile) 
	throws IOException {

		PriorityQueue<MergeFileBuffer> heap = new PriorityQueue<MergeFileBuffer>();
		for (File chunk : chunks) {
			heap.add(new MergeFileBuffer(chunk));
		}
		
		File merged = new File(inFile.getParent() + File.separator + inFile.getName() + ".sorted.txt");
		BufferedWriter bw  = new BufferedWriter(new FileWriter(merged));
		while (!heap.isEmpty()) {
			MergeFileBuffer buffer = heap.remove();
			String nextLine = buffer.remove();
			if (nextLine != null) {
				bw.write(nextLine);
				bw.newLine();
			}
			
//			System.out.println("Added:" + nextLine + " from buffer: " + buffer + ".  Next val is: " + buffer.peek() + " Is Empty: " + buffer.isEmpty());
			if (!buffer.isEmpty()) {
				heap.add(buffer);
			}
		}

		bw.close();
		return merged;
	}
	

	
	
	private List<String> readDataFromFile(File inFile) 
	throws IOException {
		
		List<String> data = new ArrayList<String>();
		String aLine = null;
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		while ((aLine = br.readLine()) != null) {
			data.add(aLine);
		}
		br.close();
		
		return data;
	}
	

	private void writeDataToFile(List<?> data, File toFile) 
	throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(toFile));
		for (Object datum : data) {
			bw.write(datum.toString());
			bw.newLine();
		}
		bw.close();
	}
	
	
	/**
	 * 
	 * @param inputFile
	 * @return
	 */
	private boolean canReadWriteFiles(File inputFile) {
		if (inputFile == null || !inputFile.isFile() || !inputFile.canRead()) {
			return false;
		}
		
		File inputDir = inputFile.getParentFile();
		if (!inputDir.canWrite()) {
			return false;
		}
		
		return true;
	}


	public String getName() { 
		return "MergeSortExt2";
	}
	
	
	private class MergeFileBuffer implements Comparable<MergeFileBuffer> {

		private static final int MAX_BUFFERED_LINES = 256;
		
		BufferedReader br;
		LinkedList<String> buffer;

		public MergeFileBuffer(File inFile) 
		throws IOException {
			this.br = new BufferedReader(new FileReader(inFile));
			this.buffer = new LinkedList<String>();
		}
		
		
		private void reload()
		throws IOException {
			String aLine = null;
			while (buffer.size() < MAX_BUFFERED_LINES && (aLine = br.readLine()) != null) {
				buffer.add(aLine);
			}
		}
		
		
		public String remove() 
		throws IOException {
			if (buffer.isEmpty()) {
				reload();
			}
			if (buffer.isEmpty()) {
				br.close();
				return null;
			}
			else {
				return buffer.remove();
			}
		}
		
		
		public String peek() 
		throws IOException {
			if (buffer.isEmpty()) {
				reload();
			}
			if (buffer.isEmpty()) {
				br.close();
				return null;
			}
			else {
				return buffer.peek();
			}
		}
		
		
		public boolean isEmpty() 
		throws IOException {
			return peek() == null;
		}
		
		
		public int compareTo(MergeFileBuffer other) {
			try {
				return this.peek().compareTo(other.peek());
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				return -1;
			}
		}
	}
	
	
	public static void main(String[] args) {

//		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getOrderedData(900, 999, 100, false));
//		MergeSortExt2 mse = new MergeSortExt2();
//		mse.sort(data);

		try {
			MergeSortExt2 mse = new MergeSortExt2();
			File data = new File("c:/temp/eclipse/mergeSortTest2/test.1k.txt"); 
			long start = System.currentTimeMillis();
			mse.sortExt(data);
			long elapsed = System.currentTimeMillis() - start;
			System.out.println("Elapsed: " + elapsed);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}