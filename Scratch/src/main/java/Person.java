public class Person {
	
	String name;
	
	public Person() {
		;
	}
	
	public Person(String name) {
		this.name = name;
	}

	
	@Override 
	public boolean equals(Object o) {

		if (o == null || !(o instanceof Person)) {
			return false;
		}
		
		if (o == this) {
			return true;
		}

		
		Person p = (Person)o;

		if (p.name == null || p.name.equals(name)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * We must override hashCode when overriding equals.
	 * 
	 * If we do not, then two objects that are "equal" will very likely
	 * generate different hashCodes.  As a result, a collection class that
	 * uses this class as a key will store multiple copies of "equal" objects.
	 
	 * This may cause performance and/or logic problems when using data structures
	 * that expect equal objects to be unique.
	 */
	@Override
	public int hashCode() {
	 
	 	int result = 0;
	 	if (name != null) result += name.hashCode();
	 	
	 	return result;
	}
	
}