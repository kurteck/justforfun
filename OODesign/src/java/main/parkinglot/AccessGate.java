package parkinglot;


public class AccessGate {

	private boolean isOpen;
	private boolean isLocked;
	private boolean isArmBlocked;
	
	public AccessGate() {
		isOpen = true;
		isLocked = false;
		isArmBlocked = false;
	}
	
	public void open() {
		isOpen = true;
	}
	
	public void close() {
		isOpen = false;
	}
	
	public void lock() {
		isLocked = true;
	}
	
	public void unlock() {
		isLocked = false;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public boolean isLocked() { 
		return isLocked;
	}

	public boolean isArmBlocked() { 
		return isArmBlocked;
	}
}