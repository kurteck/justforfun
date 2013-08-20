package callcenter.server.model;

public enum Priority {

	LOW(5), NORMAL(20), HIGH(50), URGENT(90);
	
	private int intValue;
	
	private Priority(int intValue) {
		this.intValue = intValue;
	}
	
	public int getIntValue() {
		return intValue;
	}
}
