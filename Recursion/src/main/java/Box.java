public class Box {
	
	int width;
	int depth;
	int height;
	
	public Box(int width, int depth, int height) {
		
		this.width  = width;
		this.depth  = depth;
		this.height = height;
	}

	
	public boolean canSupport(Box other) {

		if (other == null) {
			return true;
		}
		
		return (this.width  > other.width && 
				this.depth  > other.depth);
	}
	
	
	

	public boolean isBiggerThan(Box other) {

		if (other == null) {
			return true;
		}
		
		return (this.width  >= other.width && 
				this.depth  >= other.depth);
	}

	public String toString() {
		return width + "x" + depth + "x" + height;
	}
}