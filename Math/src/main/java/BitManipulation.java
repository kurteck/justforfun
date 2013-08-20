public class BitManipulation {
	
	public static void main(String[] args) {
		int num1 = 11;
		System.out.printf("%-10s", "Num1: ");
		printlnBits(num1);
		
		int mask1 = (~0 << 2);
		System.out.printf("%-10s", "Mask1: ");
		printlnBits(mask1);
		
		int num2 = num1 & mask1;
		System.out.printf("%-10s", "Num2: ");
		printlnBits(num2);	
		
		int maxIntMask = 0x7FFFFFFF; // Integer.MAX_VALUE;
		System.out.printf("%-10s", "MAX_INT: ");
		printlnBits(maxIntMask);

		int minIntMask = 0x80000000; // Integer.MIN_VALUE;
		System.out.printf("%-10s", "MIN_INT: ");
		printlnBits(minIntMask);
		System.out.println();

		System.out.println("Set Bit");
		for (int i=0; i < 8; i++) {
			printlnBits(setBit(0, i));
		}
		System.out.println();

		System.out.println("Clear Bit");
		for (int i=0; i < 8; i++) {
			printlnBits(clearBit(~0,i));
		}
		System.out.println();
		
		System.out.println("Clear Bits MS Through I");
		for (int i=0; i < 8; i++) {
			printlnBits(clearBits_MS_Through_I(~0, i));
		}
		System.out.println();
		
		System.out.println("Clear Bits I Through 0");
		for (int i=0; i < 8; i++) {
			printlnBits(clearBits_I_Through_0(0x800000FF, i));
		}
		System.out.println();

		System.out.println("Clear Bits 9 Through 2");
		printlnBits(clearBits(~0, 9, 2));
		System.out.println();

		System.out.println("Insert 00011000 in bits 9 through 2");
		printlnBits(insertBits(~0, 0x18, 9, 2));
		System.out.println();

		System.out.println("High/Low One Bit");
		System.out.println(getHighestOneBit(77));
		System.out.println(getHighestOneBit_v2(77));
		System.out.println(getLowestOneBit(77));
		System.out.println(getLowestOneBit_v2(77));
		System.out.println();

		System.out.println("Lowest Non-Trailing Zero");
		System.out.println(getLowestNonTrailingZero(36));
		System.out.println();
		
		System.out.println("Print Fractional Bits");
		printFractionalBits(0.85d);
		System.out.println();

		System.out.println("Next Bigger Num With Same Number of One Bits");
		System.out.print("174: "); printlnBits(174);
		int nextBiggerNum = getNextBiggerNumWithSameNumOneBits(174);
		System.out.print(nextBiggerNum + ": "); printlnBits(nextBiggerNum);
		System.out.println();
		
		for (int i=0; i < 16; i++) {
			int smaller = getNextSmallerNumWithSameNumOneBits(i);
			int bigger  = getNextBiggerNumWithSameNumOneBits(i);
			System.out.printf("Index: %-5s  Smaller: %-5s  Bigger: %-5s\n", i, smaller, bigger);
		}
		System.out.println();
		
		System.out.println("Draw Lines on Screen");
		int width  = 64; // pixels
		int height = 10;  // pixels
		byte[] screen = new byte[width/8*height];
		drawLine(screen, 27, 55, 4, width);
		drawLine(screen, 18, 32, 6, width);
		printScreen(screen, width);
		
		//runHighLowOneBitSpeedTest();
	}

	
	/**
	 * 
	 * @param screen
	 * @param x1
	 * @param x2
	 * @param y
	 * @param width in pixels
	 */
	public static void drawLine(byte[] screen, int x1, int x2, int y, int width) {
		
		if (width % Byte.SIZE != 0) {
			System.out.println("Resolution not supported.  Screen width must be a multiple of 8.");
			return;
		}
		
		int height = screen.length * Byte.SIZE / width;
		if (x1 < 0 | x2 < 0 | x1 >= width | x2 >= width | y < 0 || y >= height) {
			System.out.println("Point is not on the screen.");
			return;
		}

		// Ensure x1 is less than x2 by swapping if necessary
		if (x1 > x2) {
			x1 = x1 ^ x2; x2 = x1 ^ x2; x1 = x1 ^ x2;
		}
		
		int byteIndex  = (y * width + x1) / Byte.SIZE;
		int bitsToDraw = x2 - x1 + 1; 
		
		// 01111100
		// Set bytes on left side of 8 byte boundary
		int xOffset       = x1 % Byte.SIZE;
		int numLeftBits   = Byte.SIZE - xOffset;
		if (numLeftBits > bitsToDraw) { 
			numLeftBits = bitsToDraw;
		}
		
		byte leftByteMask = (byte)(((1 << numLeftBits) - 1) << (Byte.SIZE - numLeftBits - xOffset));
		screen[byteIndex] = (byte)(screen[byteIndex] | leftByteMask);
		bitsToDraw = bitsToDraw - numLeftBits;
		byteIndex++;
		
		// Set bytes in middle of window
		int numWholeBytes = bitsToDraw / Byte.SIZE;
		for (int i=0; i < numWholeBytes; i++, byteIndex++) {
			screen[byteIndex] = (byte)0xFF;
		}
		bitsToDraw = bitsToDraw - (numWholeBytes * Byte.SIZE);
					
		// Set bytes on right side of 8 byte boundary
		if (bitsToDraw > 0) {
			byte rightByteMask = (byte)(~(0xFF >>> bitsToDraw));
			screen[byteIndex]  = (byte)(screen[byteIndex] | rightByteMask);
		}
	}
	

	/**
	 * Width in pixels
	 */
	private static void printScreen(byte[] screen, int width) {
		
		int height = screen.length * Byte.SIZE / width;
		int bytesPerLine = width / Byte.SIZE;
		for (int byteIndex = 0; byteIndex < screen.length; byteIndex++) {

			printBits(screen[byteIndex]);
			if ((byteIndex+1) % bytesPerLine == 0) {
				System.out.println();
			}
		}
	}

	
	
	/**
	 * @param num
	 */
	public static void printlnBits(byte num) {
		printBits(num);
		System.out.println();
	}

	public static void printlnBits(int num) {
		printBits(num);
		System.out.println();
	}

	public static void printlnBits(long num) {
		printBits(num);
		System.out.println();
	}

	public static void printBits(byte num) {

		for (int i=Byte.SIZE-1; i >= 0; i--) {
			System.out.print((num >>> i) & 1);
		}
	}

	public static void printBits(int num) {

		for (int i=Integer.SIZE-1; i >= 0; i--) {
			System.out.print((num >>> i) & 1);
		}
	}

	public static void printBits(long num) {

		for (int i=Long.SIZE-1; i >= 0; i--) {
			System.out.print((num >>> i) & 1);
		}
	}

	
	/**
	 * Prints the bits for the given num that is between 0 and 1.
	 * 
	 * @param num
	 */
	public static void printFractionalBits(double num) {
		
		if (num < 0 || num >= 1) {
			System.out.println("ERROR");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("0.");

		while (sb.length() < 33) {

			double result = num * 2;
			if (result >= 1) {
				sb.append("1");
				num = result - 1;
			}
			else {
				sb.append("0");
				num = result;
			}
		}
		
		System.out.println(sb.toString());
		if (sb.length() >= 33) {
			System.out.println("ERROR");
		}
	}
	
	/**
	 * 
	 * @param num
	 * @param bit
	 * @return
	 */
	public static int getBit(int num, int bit) {

		if (bit < 0 || bit > 31) {
			return -1;
		}
		
		return (num >>> bit) & 0x1;

		//int bitVal = num & (1 << bit); // return 8,4,2,1 etc...
		//return (bitVal != 0) ? 1 : 0; // note we can't shift back by num bits (>>> bit) due to negative #'s
	}
	

	/**
	 * 
	 * @param num
	 * @param bit
	 * @return
	 */
	public static int setBit(int num, int bit) {
		
		if (bit < 0 || bit > 31) {
			return -1;
		}
		
		return num | (1 << bit);
	}
	
	
	/**
	 * 
	 * @param num
	 * @param bit
	 * @return
	 */
	public static int clearBit(int num, int bit) {

		if (bit < 0 || bit > 31) {
			return -1;
		}

		int mask = ~(1 << bit); // 00001000 -> 11110111
		return num & mask;
	}
	
	
	/**
	 * 11111010, 3 -> 00011010
	 * 
	 * @param num
	 * @param i
	 * @return
	 */
	public static int clearBits_MS_Through_I(int num, int i) {
		
		int mask = (1 << i) - 1; // 00000100 - 1 = 0000011
		return num & mask;
	}
	

	/**
	 * 11111010, 3 -> 00011010
	 * 
	 * @param num
	 * @param i
	 * @return
	 */
	public static int clearBits_I_Through_0(int num, int i) {
		
		int mask = (~0 << i); // 11111111 = 11111000
		return num & mask;
	}
	
	
	/**
	 * Sets num's bits to zero between position hi and lo
	 * 
	 * @param num
	 * @param hi
	 * @param lo
	 * @return
	 */
	public static int clearBits(int num, int hi, int lo) {
		
		int left  = ~0 << (hi + 1);
		int right = (1 << lo) - 1;
		int mask  = left | right;

		return num & mask;
	}
	
	/**
	 * Replaces num's bits between hi and low with toInsert's bits.
	 * 
	 * @param num
	 * @param toInsert
	 * @param hi
	 * @param lo
	 * @return
	 */
	public static int insertBits(int num, int toInsert, int hi, int lo) {
		
		int num_cleared = clearBits(num, hi, lo); // create a slot of zeros in num to hold toInsert
		int toInsert_shifted = toInsert << lo;    // now line up toInsert with the cleared bits
		
		return num_cleared | toInsert_shifted;
	}
	
	
	/**
	 * Returns the lowest set bit in the given number.
	 * 
	 * @param num
	 * @return
	 */
	public static int getLowestOneBit_v2(int num) {
		
		if (num == 0) {
			return 0;
		}
		
		int bitNum = 0;
		while ((num & (0x1 << bitNum)) == 0) {
			bitNum++;
		}
		
		return 0x1 << bitNum;
	}

	
	/**
	 * @param num
	 * @return
	 */
	public static int getLowestOneBit(int num) {
		
		if (num == 0) {
			return 0;
		}

		// 10000100 -> num
		// 10000011 -> savedLowestOne = num - 1
		// 01111100 -> mask           = ~savedLowestOne
		// 00000100 -> lowestOneBit   = num & mask
		return num & ~(num-1);
	}

	
	
	public static int getHighestOneBit_v2(int num) {
		
		if (num == 0) {
			return 0;
		}
		
		int bitNum = 31;
		while ((num & (0x1 << bitNum)) == 0) {
			bitNum--;
		}
		
		return 0x1 << bitNum;
	}

	
	public static int getHighestOneBit(int i) {

		i |= (i >>  1);
		i |= (i >>  2);
		i |= (i >>  4);
		i |= (i >>  8);
		i |= (i >> 16);

		return i - (i >>> 1);
	}
	

	/**
	 * Returns the position of the first zero bi
	 * t encountered after
	 * a one.  Bits are evaluated from LSB to MSB.  For example:
	 * 
	 *  100100 - get lowest 1 bit  = 000100 = 4  001000 - 1 = 000111 ~= 111000
	 *  111000 - create mask to clear lowest zero bits and lowest 1 bit = ~((low1 << 1) - 1)
     *  011011 - invert num to get zero's as 1's
     *  011000 - and with mask to remove lowest zeros
     *  001000 - get lowest 1 bit  = 001000 = 8 (this is the position of the first zero bit after the first 1)
	 * 
	 * @param num
	 * @return
	 */
	public static int getLowestNonTrailingZero(int num) {
		
		int lowestOneBit = getLowestOneBit(num);
		int mask         = ~((lowestOneBit << 1) - 1);
		int num_cleared  = ~num & mask;
		int result       = getLowestOneBit(num_cleared);
		
		return result;
	}

	
	/**
	 * 010011 - returns 010000
	 * 
	 * @param num
	 * @return
	 */
	public static int getLowestNonTrailingOne(int num) {

		return getLowestNonTrailingZero(~num);
	}
	
	/**
	 * Eg. 174 -> 179 (10101110 -> 10110011)
	 * 
	 * @param num
	 * @return
	 */
	public static int getNextBiggerNumWithSameNumOneBits(int num) {
		
		if (num == 0 || num == Integer.MAX_VALUE) {
			return num;
		}

		// 10101110 -> num
        // 00010000 -> zeroBitToSet   = getLowestNonTrailingZero(num)
        // 00001000 -> oneBitToClear  = zeroBitToSet >> 1
		// 00000110 -> lowBitsToPack  = (oneBitToClear - 1) AND num
		// 10100000 -> newHighBits    = ~(zeroBitToSet - 1) & num
        // 00000011 -> newLowBits     = lowBitsToPack / getLowestOneBit(lowBitsToPack)
        // 10110011 -> newNum = newHighBits | zeroBitToSet | newLowBits
		
		int zeroBitToSet  = getLowestNonTrailingZero(num);
        int oneBitToClear = zeroBitToSet >> 1;
		int lowBitsToPack = (oneBitToClear - 1) & num;
		int newHighBits   = ~(zeroBitToSet - 1) & num;
		int newLowBits    = lowBitsToPack > 0 ? lowBitsToPack / getLowestOneBit(lowBitsToPack) : 0;
		int newNum        = newHighBits | zeroBitToSet | newLowBits;

		return newNum;
	}
	
	
	/**
	 * Eg. 174 -> 179 (10101110 -> 10110011)
	 * 
	 * @param num
	 * @return
	 */
	public static int getNextSmallerNumWithSameNumOneBits(int num) {
		
		if (num == 0) {
			return num;
		}

		int oneBitToClear = getLowestNonTrailingOne(num);
		if (oneBitToClear == 0) {
			return -1;
		}
        int zeroBitToSet  = oneBitToClear >> 1;
		int lowBitsToPack = (zeroBitToSet - 1) & num;
		int newHighBits   = ~((oneBitToClear << 1) - 1) & num;
		int newLowBits    = lowBitsToPack > 0 ? lowBitsToPack * zeroBitToSet / 2 : 0;
		int newNum        = newHighBits | zeroBitToSet | newLowBits;

		return newNum;
	}
	
	public static void runHighLowOneBitSpeedTest() {
		
		System.out.println("High/Low One Bit Speed Test");
		int result = 0;

		long start = System.currentTimeMillis();
		for (int i=1; i < 134217728; i++) {
			int bitNum = 31;
			while ((i & (0x1 << bitNum)) == 0) {
				bitNum--;
			}
			result = 0x1 << bitNum;
		}
		long stop  = System.currentTimeMillis();
		System.out.println("GetHighOneBit V1 Elapsed: " + (stop - start));

		
		start = System.currentTimeMillis();
		for (int i=1; i < 134217728; i++) {

			int j = i;
			j |= (j >>  1);
			j |= (j >>  2);
			j |= (j >>  4);
			j |= (j >>  8);
			j |= (j >> 16);

			result = j - (j >>> 1);
		}
		stop  = System.currentTimeMillis();
		System.out.println("GetHighOneBit V2 Elapsed: " + (stop - start));

		
		start = System.currentTimeMillis();
		result = 0;
		for (int i=1; i < 134217728; i++) {
			int bitNum = 0;
			while ((i & (0x1 << bitNum)) == 0) {
				bitNum++;
			}
			result = 0x1 << bitNum;
		}
		stop  = System.currentTimeMillis();
		System.out.println("GetLowOneBit V1 Elapsed: " + (stop - start));

		
		start = System.currentTimeMillis();
		for (int i=1; i < 134217728; i++) {
			int j = i;
			result = j - ~(j-1);
		}
		stop  = System.currentTimeMillis();
		System.out.println("GetLowOneBit V2 Elapsed: " + (stop - start));
	}
}