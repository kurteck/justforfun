import java.util.Arrays;

public class BFSToInOrder {

		public static int[] fromLevelToInorder(int[] levelOrder) {
			
			
			int numNodes = levelOrder.length;
			int[] inOrder = new int[numNodes];
			
			int currentNodeIndex = 0;
			int currentLevel = 0;
			int nodesInLevel;
			int nodesPerQuadrant;
			int currentLowerBound;
			int currentUpperBound; /* Will always contain 1 more than the valid index, don't forget to decrement. */
			
			while (currentNodeIndex < numNodes) { //While there are still nodes to process
				
				nodesInLevel = (int)Math.pow(2, currentLevel); //Get the number of nodes/quadrants in this level
				nodesPerQuadrant = numNodes/nodesInLevel; //Get the number of nodes in each quadrant
				currentLowerBound = 0; //Initialize the lower bound
				
				for(int i = 0; i < nodesInLevel; i++) {
					currentUpperBound = currentLowerBound + nodesPerQuadrant; //Initialize the upper bound
					
					/* Place the node in the center of the current quadrant */
					inOrder[currentLowerBound + (((currentUpperBound - 1) - currentLowerBound)/2)] = levelOrder[currentNodeIndex];
					
					currentLowerBound = currentUpperBound + 1;
					
					currentNodeIndex++;
				}
				
				currentLevel++;
			}
			
			
			return inOrder;
		}
		
		public static void main(String[] args) {
			
			int[] result;
			//int[] input = {2, 1, 3};
			//int[] input = {50, 25, 75, 1, 26, 52, 100};
			int[] input = {70, 35, 105, 17, 50, 81, 200, 1, 18, 40, 60, 75, 90, 106, 205};
			
			result = fromLevelToInorder(input);
			System.out.println(Arrays.toString(result));
		}
}