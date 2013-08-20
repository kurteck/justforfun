package parkinglot;

public class Dimensions {
	
	public static final Dimensions COMPACT  = new Dimensions(20, 8,  10);
	public static final Dimensions NORMAL   = new Dimensions(20, 10, 10);
	public static final Dimensions OVERSIZE = new Dimensions(25, 15, 10);
	public static final Dimensions JUMBO    = new Dimensions(60, 20, 20);

	private final int length;
	private final int width;
	private final int height;
	
	public Dimensions(int length, int width, int height) {
		this.length = length;
		this.width  = width;
		this.height = height;
	}
	
	public int getLength() {
		return this.length;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

	/**
	 * True if d1.width <= d2.width, d1.length <= d2.length, and d1.height <= d2.height
	 */
	public boolean fitsInside(Dimensions d2) {
		return Dimensions.fitsInside(this, d2);
	}

	/**
	 * True if d1.width <= d2.width, d1.length <= d2.length, and d1.height <= d2.height
	 */
	public static boolean fitsInside(Dimensions d1, Dimensions d2) {
		
		if (d1 == null || d2 == null) {
			return false;
		}
		
		return (d1.width  <= d2.width && 
				d1.length <= d2.length && 
				d1.height <= d2.height);
	}
}