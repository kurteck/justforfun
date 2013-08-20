package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
	
	private List<ParkingLevel> levels;

	public ParkingLot() {
		levels = new ArrayList<ParkingLevel>();
	}

	
	public void addLevel(ParkingLevel level) {
		this.levels.add(level);
	}
	
	
	/**
	 * 
	 */
	public int getOccupancy() {

		int occupancy = 0;
		for (ParkingLevel level : levels) {
			occupancy += level.getOccupancy();
		}
		
		return occupancy;
	}
	
	/**
	 * 
	 */
	public int getCapacaity() {

		int occupancy = 0;
		for (ParkingLevel level : levels) {
			occupancy += level.getCapacity();
		}
		
		return occupancy;
	}
	
	/**
	 * 
	 */
	public boolean isGated() {
		
		for (ParkingLevel level : levels) {
			if (level.isGated()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	public boolean isFull() {

		for (ParkingLevel level : levels) {
			if (!level.isFull()) {
				return false;
			}
		}
		
		return true;
	}
	

	public boolean isOpen() {
		return true; // based upon hours of operation
	}
	
	
	public void closeAndlockGates() {
		for (ParkingLevel level : levels) {
			level.closeAndlockGates();
		}
	}
	
	public void unlockAndOpenGates() {
		for (ParkingLevel level : levels) {
			level.unlockAndOpenGates();
		}
	}
	

	public ParkingLevel getParkingLevel(int i) {
		
		if ((i < 0) || (i > levels.size())) {
			return null;
		}
		
		return levels.get(i);
	}

	/**
	 * Returns true if the given vehicle was 
	 * successfully parked in this ParkingSpace.
	 * 
	 * @param v
	 * @return
	 */
	protected boolean acceptVehicle(Vehicle v) {
		return isOpen();
	}
}