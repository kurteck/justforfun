public class Squares {
	
	public static void main(String[] args) {
		Point p1 = new Point(2,2);
		Point p2 = new Point(6,6);
		Point p3 = new Point(2.0001,2.0001);
		Point p4 = new Point(8,8);
		Point p5 = new Point(2,8);
		Line l1 = new Line(p1,p2);
		Line l2 = new Line(p2,p4);
		Line l3 = new Line(p1,p5);
		Square s1 = new Square(4, new Point(0,0));
		Square s2 = new Square(2, new Point(6,4));
		Line cutL3 = s1.intersection(l3);
		
		System.out.println("Distance from p1 to p2 = " + p1.distanceTo(p2));
		System.out.println("Distance from p1 to p3 = " + p1.distanceTo(p3));
		System.out.println("Distance from p1 to p3 = " + p3.distanceTo(p1));
		System.out.println("Is p1 equal p3 " + p1.equals(p3));
		
		
		System.out.println("Square1 center is at " + s1.center);
		System.out.println("Square1 contains p1 " + s1.contains(p1));
		System.out.println("Square1 contains p2 " + s1.contains(p2));
		System.out.println("Square1 touches line1 " + s1.touches(l1));
		System.out.println("Square1 touches line2 " + s1.touches(l2));
		System.out.println("Line 3     " + l3);
		System.out.println("Line 3 Cut " + cutL3);
		
		Line l4 = new Line(s1.center, s2.center);
		Line l4Cut = s1.intersection(l4);
	}
	
}