package sort;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeSortExt implements SortAlgorithm {

	public int IN_MEMORY_THRESHOLD = 1024 * 1024; // 1MB

	
	public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {
		
		try {
			File inputFile = new File("c:/temp/eclipse/mergeSortTest/input.txt"); 
			writeDataToFile(elements, inputFile);
			sortExt(inputFile);
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

		return sortExt(inputFile, 0);
	}
	
	/**
	 * External MergeSort for large data
	 * 
	 * @param elementsFile
	 * @return
	 */
	public File sortExt(File inputFile, int fileNum) 
	throws IOException {

		if (canFitFileIntoMemory(inputFile)) {
			return sortFileInMemory(inputFile);
		}
		else {
			File[] splitFiles = splitFile(inputFile, fileNum);
			return mergeExt(sortExt(splitFiles[0], fileNum*2+1), sortExt(splitFiles[1], fileNum*2+2), fileNum*2+1, fileNum*2+2);
		}
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
	private File[] splitFile(File inFile, int fileNum) 
	throws IOException {

		File splitFile1 = new File(inFile.getParent() + File.separator + "split" + (fileNum*2+1) + ".txt");
		File splitFile2 = new File(inFile.getParent() + File.separator + "split" + (fileNum*2+2) + ".txt");

		BufferedReader br  = new BufferedReader(new FileReader(inFile));
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(splitFile1));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(splitFile2));
		int numLines = 0;
		String aLine = null;
		while ((aLine = br.readLine()) != null) {
			numLines++;
			if (numLines % 2 == 1) {
				bw1.write(aLine);
				bw1.newLine();
			}
			else {
				bw2.write(aLine);
				bw2.newLine();
			}
		}

		br.close();
		bw1.close();
		bw2.close();
		
		return new File[] { splitFile1, splitFile2 };
	}
	
	
	/**
	 * @param inFile1
	 * @param inFile2
	 * @return
	 * @throws IOException
	 */
	private File mergeExt(File inFile1, File inFile2, int fileNum1, int fileNum2) 
	throws IOException {

		File merged = new File(inFile1.getParent() + File.separator + "merge" + fileNum1 + "_" + fileNum2 + ".txt");

		BufferedWriter bw  = new BufferedWriter(new FileWriter(merged));
		BufferedReader br1 = new BufferedReader(new FileReader(inFile1));
		BufferedReader br2 = new BufferedReader(new FileReader(inFile2));
		
		String aLine1 = br1.readLine();
		String aLine2 = br2.readLine();
		while (aLine1 != null && aLine2 != null) {
			if (aLine1.compareTo(aLine2) < 0) {
				bw.write(aLine1); bw.newLine();
				aLine1 = br1.readLine();
			}
			else {
				bw.write(aLine2); bw.newLine();
				aLine2 = br2.readLine();
			}
		}
		
		while (aLine1 != null) {
			bw.write(aLine1); bw.newLine();
			aLine1 = br1.readLine();
		}

		while (aLine2 != null) {
			bw.write(aLine2); bw.newLine();
			aLine2 = br2.readLine();
		}

		bw.close();
		br1.close();
		br2.close();
		
		return merged;
	}
	

	private boolean canFitFileIntoMemory(File aFile) {
		return (aFile.length() <= IN_MEMORY_THRESHOLD);
	}
	
	
	private File sortFileInMemory(File inFile) 
	throws IOException {

		List<String> inputData  = readDataFromFile(inFile);
		List<String> sortedData = MergeSort.sortStatic(inputData);
		File outFile = new File(inFile.getParent() + File.separator + inFile.getName() + ".sorted.txt");
		writeDataToFile(sortedData, outFile);

		return outFile;
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
		return "MergeSortExt";
	}


	public static void main(String[] args) {

//		List<Integer> data = DataGenerator.toArrayList(DataGenerator.getOrderedData(900, 999, 100, false));
//		MergeSortExt2 mse = new MergeSortExt2();
//		mse.sort(data);

		try {
			MergeSortExt2 mse = new MergeSortExt2();
			File data = new File("c:/temp/eclipse/mergeSortTest/test.8million.txt"); 
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