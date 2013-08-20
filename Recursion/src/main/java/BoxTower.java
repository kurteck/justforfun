import java.util.ArrayList;
import java.util.LinkedList;

public class BoxTower {
	
	int height;
	LinkedList<Box> boxStack;
	
	public BoxTower() {
		this.height   = 0;
		this.boxStack = new LinkedList<Box>();
	}
	

	public static BoxTower makeTower(final ArrayList<Box> boxes) {
		
		if (boxes == null) {
			return new BoxTower();
		}
		
		return makeTower(boxes, new boolean[boxes.size()], new BoxTower());
	}
	
	private static BoxTower makeTower(final ArrayList<Box> boxes, boolean[] usedBoxes, BoxTower currentTower) {
		
		if (boxes == null || boxes.size() == 0) {
			return currentTower;
		}

		BoxTower tallestTower = currentTower;
		for (int i=0; i < usedBoxes.length; i++) {
			if (usedBoxes[i] == false) {

				Box aBox = boxes.get(i);
//				System.out.println("Looking at box: " + aBox);
				if (currentTower.canSupport(aBox)) {
//					System.out.println("Current Tower supports it.");
					BoxTower currentTowerClone = currentTower.clone();
					currentTowerClone.addBox(aBox);
//					currentTowerClone.print();

					boolean[] usedBoxesClone = usedBoxes.clone();
					usedBoxesClone[i] = true;

					BoxTower newTower = makeTower(boxes, usedBoxesClone, currentTowerClone);
					if (newTower.isTallerThan(tallestTower)) {
						tallestTower = newTower;
					}
				}
				else {
//					System.out.println("Too big for current tower: ");
//					currentTower.print();
				}
			}
//			System.out.println();
		}

		return tallestTower;
	}
	
	
	public void addBox(Box aBox) {

		if (canSupport(aBox)) {
			boxStack.push(aBox);
			height += aBox.height;
		}
	}

	
	public boolean isTallerThan(BoxTower aTower) {
		
		if (this.height > aTower.height) {
			return true;
		}
		return false;
	}
	
	public BoxTower clone() {

		BoxTower bt = new BoxTower();
		bt.height   = this.height;
		
		LinkedList<Box> boxStackClone = new LinkedList<Box>();
		for (Box aBox : boxStack) {
			boxStackClone.add(aBox);
		}
		bt.boxStack = boxStackClone;
		
		return bt;
	}
	
	
	public boolean canSupport(Box aBox) {
		
		if (boxStack == null || boxStack.size() == 0) {
			return true;
		}
		
		Box topBox = boxStack.peek();
		return topBox.canSupport(aBox);
	}
	
	
	public void print() {
		for (Box aBox : boxStack) {
			System.out.println(aBox);
		}
	}
	
	
	public static BoxTower makeTower2(ArrayList<Box> boxes) {
		
		if (boxes == null) {
			return new BoxTower();
		}

		LinkedList<Box> boxStack = new LinkedList<Box>();
		while (!boxes.isEmpty()) {
			
			Box biggest = null;
			int biggestBoxIndex = 0;
			for (int i=0; i < boxes.size(); i++) {

				Box aBox = boxes.get(i);
				if (aBox.isBiggerThan(biggest)) {
					biggest = aBox;
					biggestBoxIndex = i;
				}
			}
			
			boxes.remove(biggestBoxIndex);
			
			System.out.println("Next Biggest: " + biggest);
			
			if (boxStack.isEmpty() || boxStack.peek().isBiggerThan(biggest)) {
				boxStack.push(biggest);
			}
		}
		
		
		BoxTower tallest = new BoxTower();
		tallest.boxStack = boxStack;

		return tallest;
	}
	
	
	public static void main(String[] args) {
		
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (int i=0; i < 10; i++) {
			int l = (int)(Math.random() * i) + 1;
			int w = (int)(Math.random() * i) + 1;
			int h = (int)(Math.random() * i) + 1;
			Box aBox = new Box(l,w,h);
			boxes.add(aBox);
			System.out.println("Box: " + i + "  " + aBox);
		}
		
		BoxTower tallestTower = BoxTower.makeTower(boxes);
		System.out.println("Tallest Tower 1");
		tallestTower.print();
		System.out.println();

		System.out.println("Tallest Tower 2");
		BoxTower tallestTower2 = BoxTower.makeTower2(boxes);
		tallestTower2.print();
	}
	
}