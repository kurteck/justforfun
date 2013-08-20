package datastructures;
import datastructures.LinkedList;

public class LinkedListTest {
	
	public static void main(String[] args) {
		
		LinkedList<Integer> ll = new LinkedList<Integer>();
		ll.addToTail(new Integer(42));
		ll.addToTail(new Integer(52));
		ll.addToTail(new Integer(-2));
		ll.addToTail(new Integer(99));
		System.out.println("Size [" + ll.size + "]");
		System.out.println("Contents [" + ll.toString() + "]");

		ll.addToHead(new Integer(0));
		System.out.println("Size [" + ll.size + "]");
		System.out.println("Contents [" + ll.toString() + "]");

		ll.remove(new Integer(99));
		ll.remove(new Integer(0));
		ll.remove(new Integer(52));
		ll.remove(new Integer(42));
		ll.remove(new Integer(-1));
		ll.remove(new Integer(-2));
		System.out.println("Size [" + ll.size + "]");
		System.out.println("Contents [" + ll.toString() + "]");


		ll.addToHead(new Integer(50));
		System.out.println("Size [" + ll.size + "]");
		System.out.println("Contents [" + ll.toString() + "]");

		ll.addToHead(new Integer(0));
		ll.addToTail(new Integer(75));
		System.out.println("Size [" + ll.size + "]");
		System.out.println("Contents [" + ll.toString() + "]");
	}
	
}