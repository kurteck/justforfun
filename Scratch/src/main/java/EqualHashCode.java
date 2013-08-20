import java.util.HashMap;
import java.util.Map;

public class EqualHashCode {
	
	
	public static void main(String[] args) {
		
		runStringTests();
		runPersonTests();
	}
	
	
	private static void runStringTests() { 
		
		String a1 = new String("a");
		String a2 = new String("a");
		String b1 = new String("b");
		
		System.out.println("a1 == a2 " + (a1 == a2));
		System.out.println("a1.equals(a2) " + a1.equals(a2));
		System.out.println("a1.hashCode() " + a1.hashCode());
		System.out.println("a2.hashCode() " + a2.hashCode());
		System.out.println("b1.hashCode() " + b1.hashCode());

		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put(a1,  a2);
		System.out.println("Size: " + aMap.size());
		
		String aString = aMap.get(a2);
		System.out.println("aString = " + aString);
		System.out.println("aString == a1 " + (aString == a1));
		System.out.println("aString == a2 " + (aString == a2));
		System.out.println("aString.equals(a1) " + aString.equals(a1));
		System.out.println("aString.equals(a2) " + aString.equals(a2));

		aMap.put(a2,  a2);
		System.out.println("Size: " + aMap.size());
		System.out.println();
	}
	
	private static void runPersonTests() {
		
		Person janeDoe1 = new Person("jane");
		Person janeDoe2 = new Person("jane");
		
		System.out.println("Jane1.hashCode() " + janeDoe1.hashCode());
		System.out.println("Jane2.hashCode() " + janeDoe2.hashCode());
		System.out.println("Jane1 == Jane 2 " + (janeDoe1 == janeDoe2));
		System.out.println("Jane1.equals(Jane2) " + janeDoe1.equals(janeDoe2));
		
		Map<Person, Person> aMap = new HashMap<Person, Person>();
		aMap.put(janeDoe1, janeDoe1);
		aMap.put(janeDoe2, janeDoe2);
		
		System.out.println("Size: " + aMap.size());
	}
}