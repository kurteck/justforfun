
public class StepCounter {

	private int[] countCache;
	private int numSteps;
	private int maxReach;
	
	public StepCounter(int numSteps, int maxReach) {
		countCache = new int[numSteps+1];
		this.numSteps = numSteps;
		this.maxReach = maxReach;
	}
	
	public int countWaysUp_v1() {
		return countWaysUp_v1(numSteps);
	}
	
	private int countWaysUp_v1(int stepsToGo) {
		
		if (stepsToGo <= 0) {
			return 0;
		}
		
		int cachedVal = countCache[stepsToGo];
		if (cachedVal > 0) {
			return cachedVal;
		}
		
		int waysUp = 1;  
		int paths  = Math.min(stepsToGo, maxReach);
		for (int i=paths; i >= 1; i--) {
			waysUp += countWaysUp_v1(stepsToGo - i);
		}
		countCache[stepsToGo] = waysUp;
		
		return waysUp;
	}
	

	public int countWaysUp_v2() {
		return countWaysUp_v2(numSteps);
	}
	

	private int countWaysUp_v2(int stepsToGo) {
		
		if (stepsToGo < 0 || maxReach <= 0) {
			return 0;
		}
		else if (stepsToGo == 0) {
			return 1;
		}
		else if (countCache[stepsToGo] != 0) {
			return countCache[stepsToGo];
		}
		
		int waysUp = 0;
		int paths  = Math.min(stepsToGo, maxReach);
		for (int i=1; i <= paths; i++) {
			waysUp += countWaysUp_v2(stepsToGo - i);
		}
		countCache[stepsToGo] = waysUp;
		
		return waysUp;
	}
	
	

	public int countWaysUp_v3() {
		return countWaysUp_v3(numSteps, maxReach);
	}

	public int countWaysUp_v3(int stepsToGo, int maxReach) {
		
		if (stepsToGo <= 0 || maxReach <= 0) {
			return 0;
		}
		
		if (stepsToGo <= maxReach) {
			return (int)Math.pow(2, stepsToGo-1);
		}
		
		if (countCache[stepsToGo] != 0) {
			return countCache[stepsToGo];
		}
		
		int waysUp = 0;
		for (int i=1; i <= maxReach; i++) {
			waysUp += countWaysUp_v3(stepsToGo-i, maxReach);
		}
		
		countCache[stepsToGo] = waysUp;
		
		return waysUp;
	}
	
	
	
	public int fibonacci(int n) {
		
		if (n <= 1) {
			return n;
		}
	
		return fibonacci(n-1) + fibonacci(n-2);
	}
	

	protected void printCache() {
		for (int i=0; i <= numSteps; i++) {
			System.out.println("CountCache[" + i + "] = " + countCache[i]);
		}
	}
	
	public static void main(String[] args) {
		
		int numSteps = 4;
		int maxReach = 3;
		StepCounter sc = new StepCounter(numSteps, maxReach);
		System.out.println("WaysUp_v2(" + numSteps + "," + maxReach +") = " + sc.countWaysUp_v2());
		System.out.println("WasyUp_v3(" + numSteps + "," + maxReach +") = " + sc.countWaysUp_v3());
//		sc.printCache();
	}

}