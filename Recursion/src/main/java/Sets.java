
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Sets
{
	
	public static final char OPEN_PAREN  = '(';
	public static final char CLOSE_PAREN = ')';
	

	/**
	 * Returns all possible sequences of the given character set.  
	 * 
	 * A   = A
	 * AB  = AB
	 * ABC = ABC, ACB, BAC, BCA, CAB, CBA
	 * 
	 * @param chars
	 * @return
	 */
	public static Set<String> permute(String chars) {
		
		Set<String> morePerms = new HashSet<String>();
		
		// Return an empty set if there is nothing to permute
		if (chars == null || chars.length() == 0) {
			return morePerms;
		}
		
		// Otherwise, seed our permutations with the first character
		char first = chars.charAt(0);
		if (chars.length() == 1) {
			morePerms.add(String.valueOf(first));
			return morePerms;
		}

		// And then build the rest of our permutations on top of it.
		String rest = chars.substring(1); 
		Set<String> perms = permute(rest);
		for (String aPerm : perms) {
			for (int i=0; i <= aPerm.length(); i++) {
				morePerms.add(aPerm.substring(0,i) + first + aPerm.substring(i));
			}
		}
		
		return morePerms;
	}
	
	
	public static Set<String> powerSet_v1(String chars) {
		
		if (chars == null || chars.length() == 0) {
			Set<String> nullSet = new HashSet<String>();
			nullSet.add("");
			return nullSet;
		}
		
		char first  = chars.charAt(0);
		String rest = chars.substring(1);
		
		return addToSets(first, powerSet_v1(rest));
	}
	
	
	private static Set<String> addToSets(char c, Set<String> sets) {
		
		Set<String> toAdd = new HashSet<String>();
		toAdd.add(String.valueOf(c));
		for (String aSet : sets) {
			toAdd.add(c + aSet);
		}
		sets.addAll(toAdd);
		
		return sets;
	}
	
	
	
	private static Set<String> powerSet_v2(String chars) {
		
		Set<String> allSets = new HashSet<String>();
		
		int max = 1 << chars.length();
		for (int i = 0; i < max; i++) {
			String aSet = convertToSet(i, chars);
			allSets.add(aSet);
		}

		return allSets;
	}
	
	private static String convertToSet(int x, String chars) {
		
		StringBuffer aSet = new StringBuffer();
		int index = 0;
		for (int k=x; k > 0; k >>= 1) {
			if ((k & 1) == 1) {
				aSet.append(chars.charAt(index));
			}
			index++;
		}
		
		return aSet.toString();
	}
	

	/**
	 * @param numPairs
	 * @return
	 */
	public static List<String> makeParens(int numPairs) {
		
		List<String> solutions = new LinkedList<String>();
		List<String> toVisit   = new LinkedList<String>();
		toVisit.add(String.valueOf(OPEN_PAREN));

		return makeParens(numPairs, toVisit, solutions);
	}

	
	/**
	 * @param numPairs
	 * @param toVisit
	 * @param solutions
	 * @return
	 */
	private static List<String> makeParens(int numPairs, List<String> toVisit, List<String> solutions) {
		
		if (numPairs <= 0 || toVisit == null || toVisit.size() == 0) {
			return solutions;
		}
		
		String toEval = toVisit.remove(0); 
		List<String> legalMoves = getLegalMoves(numPairs, toEval);
		for (String aMove : legalMoves) {
			if (isSolution(aMove, numPairs*2)) {
				solutions.add(aMove);
			}
			else {
				toVisit.add(aMove);
			}
		}
		
		return makeParens(numPairs, toVisit, solutions);
	}
	
	
	/**
	 * 
	 * @param numPairs
	 * @param toEval
	 * @return
	 */
	private static List<String> getLegalMoves(int numPairs, String toEval) {
		
		List<String> moves = new LinkedList<String>();
		if (toEval == null || isSolution(toEval, numPairs*2)) {
			return moves;
		}

		int numOpen = countCharacters(toEval, OPEN_PAREN);
		if (numOpen < numPairs) {
			moves.add(toEval + OPEN_PAREN);
		}

		int numClosed = countCharacters(toEval, CLOSE_PAREN);
		if (numOpen > numClosed) {
			moves.add(toEval + CLOSE_PAREN);
		}
		
		return moves;
	}
	
	
	/**
	 * @param toEval
	 * @param solutionLen
	 * @return
	 */
	private static boolean isSolution(String toEval, int solutionLen) {
		return (toEval != null) && (toEval.length() == solutionLen);
	}


	/**
	 * @param s
	 * @param c
	 * @return
	 */
	private static int countCharacters(String s, char c) {
		
		if (s == null) {
			return 0;
		}

		int count = 0;
		for (int i=0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				count++;
			}
		}
		
		return count;
	}
	
	
	private static List<String> makeParens2(int numPairs) {
		
		List<String> solutions = new LinkedList<String>();

		int numSolutions = 0;
		int patternLen  = numPairs * 2;
		int numPatterns = 1 << patternLen;
		for (int pattern=0; pattern < numPatterns; pattern++) {
			if (isSolution(patternLen, pattern)) {
				numSolutions++;
				solutions.add(convertToParens(patternLen, pattern));
			}
		}
		System.out.println(numSolutions);
		
		return solutions;
	}
	
	private static boolean isSolution(int solutionLen, int pattern) {
		
		int maxOpenClosed = solutionLen >> 1;
		int numOpened = 0;
		int numClosed = 0;
		for (int bitNum = 0; bitNum < solutionLen; bitNum++) {

			int bitVal = pattern & 1;
			if (bitVal == 0) { 
				numClosed++;
			} 
			else {
				numOpened++;
			}
			
			
//			if (highOneBit == 0)
//					pattern >> (solutionLen-1)
//					((1 << solutionLen-1) & pattern) == 0) {
//				return false;
//			}
			
			if (numClosed < numOpened) {
				return false;
			}
			if (numOpened > maxOpenClosed) {
				return false;
			}
			if (numClosed > maxOpenClosed) {
				return false;
			}
			
			pattern >>= 1;
		}
		
		if (numOpened != maxOpenClosed) {
			return false;
		}
		
		return true;
	}

	
	/**
	 * 
	 * @param solutionLen
	 * @param pattern
	 * @return
	 */
	private static String convertToParens(int solutionLen, int pattern) {

		StringBuffer sb = new StringBuffer(solutionLen);
		for (int bitNum = 0; bitNum < solutionLen; bitNum++) {

			int bitVal = pattern & 1;
			if (bitVal == 0) { 
				sb.insert(0, CLOSE_PAREN);
			} 
			else {
				sb.insert(0, OPEN_PAREN);
			}
			
			pattern >>= 1;
		}
		
		return sb.toString();
	}
	

	private static void printBits(int bits) {
		for (int bitNum=31; bitNum >= 0; bitNum--) {
			System.out.print((bits >> bitNum) & 1);
		}
	}
	
	
	public static void main(String[] args) {
		testPowerSet();
		testPermute();
		testMakeParens();
	}
	
	
	public static void testPowerSet() {
		
		Set<String> powerSet1 = powerSet_v1("ABC");
		for (String aSet : powerSet1) {
			System.out.print(aSet + ",");
		}
		System.out.println();
		
		Set<String> powerSet2 = powerSet_v2("ABC");
		for (String aSet : powerSet2) {
			System.out.print(aSet + ",");
		}
		System.out.println();
	}
	
	
	public static void testPermute() {
		
//		int i=0;
//		Set<String> permutations1 = Sets.permutate("ABCDEFGHI");
//		for (String aPermutation : permutations1) {
//			System.out.printf("%-3s  %s\n", ++i, aPermutation);
//		}
//		System.out.println();

		long start = System.currentTimeMillis();
		Set<String> permutations = Sets.permute("ABC");
		System.out.println(permutations);
		long elapsed = System.currentTimeMillis() - start;
		System.out.println(elapsed);
	}
	
	
	public static void testMakeParens() {
		
		// 1, 2, 5, 14, 42, 132, 429, 1430
		long start = System.currentTimeMillis();
		List<String> parens = makeParens2(3);
		long elapsed = System.currentTimeMillis() - start;
		for (String s : parens) {
			System.out.println(s);
		}
		System.out.println(" # Parens: " + parens.size());
		
	}
}