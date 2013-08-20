package sort;

import java.util.List;

import datagen.DataGenerator;

public class RadixSort2 extends Sort implements SortAlgorithm {
	
	    private static final int RADIX=256;
	    private static final int MASK=RADIX-1;
	    private int s[] = new int[65536];
//    	private int bucket[] = new int[RADIX];

	    public void ensureSort(int len){
	        if(s.length < len)
	            s = new int[len];
	    }   

		@SuppressWarnings("unchecked")
		public <T extends Comparable<? super T>> List<T> sort(List<T> elements) {

			int numElems = elements.size();
			int[] ints = new int[numElems];
			for (int i=0; i < numElems; i++) {
				ints[i] = ((Integer)elements.get(i)).intValue();
			}
			sort(ints);
			for (int i=0; i < numElems; i++) {
				elements.set(i, (T)(new Integer(ints[i])));
			}

			return elements;
		}

	    
	    public void sort(int[] a) {
	    
	    	int bucket[] = new int[RADIX];
	        int n=a.length;
	        ensureSort(n);
//	        System.out.println(bucket.length);
//	        for(int i=0;i<RADIX;i++) {
//	        	if (bucket[i] != 0) { 
//	        		System.out.println("Bucket[" + i + "] = " + bucket[i]);
//	        	}
//	        }
	        	
	        for(int i=0;i<RADIX;i++)	bucket[i]=0;
//	        DataGenerator.println(bucket);
	        for(int i=0;i<n;i++)		bucket[a[i]&MASK]++; //bucket[a[i] & MASK]++;
//	        DataGenerator.println(bucket);
	        for(int i=1;i<RADIX;i++)	bucket[i] += bucket[i-1];
//	        DataGenerator.println(bucket);
	        for(int i=0;i<n;i++)		s[--bucket[a[i] & MASK]] = a[i];
//	        DataGenerator.println(s);

	        for(int i=0;i<RADIX;i++)	bucket[i]=0;
	        for(int i=0;i<n;i++)		bucket[(s[i]>>8)&MASK]++;
	        for(int i=1;i<RADIX;i++)	bucket[i]+=bucket[i-1];
	        for(int i=n-1;i>=0;i--)		a[--bucket[(s[i]>>8)&MASK]]=s[i];
	        
	        for(int i=0;i<RADIX;i++)	bucket[i]=0;
	        for(int i=0;i<n;i++)		bucket[(a[i]>>16)&MASK]++;
	        for(int i=1;i<RADIX;i++)	bucket[i]+=bucket[i-1];
	        for(int i=n-1;i>=0;i--)		s[--bucket[(a[i]>>16)&MASK]]=a[i];
	        
	        for(int i=0;i<RADIX;i++)	bucket[i]=0;
	        for(int i=0;i<n;i++)		bucket[(s[i]>>24)&MASK]++;
	        for(int i=129;i<RADIX;i++)	bucket[i]+=bucket[i-1];
	        							bucket[0] += bucket[255];
	        for(int i=1;i<128;i++)		bucket[i]+=bucket[i-1];     
	        for(int i=n-1;i>=0;i--)		a[--bucket[(s[i]>>24)&MASK]]=s[i];
	    }
	
	public String getName() {
		return "RadixSort2";
	}
}