package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLevel {
	
	private String id;
	private int capacity;
	private ParkingLot lot;
	private List<AccessGate> gates;
	private List<ParkingSpace> spaces;

	
	public ParkingLevel(String id, int capacity, ParkingLot lot) {	
		this.id = id;
		this.lot = lot;
		this.gates = new ArrayList<AccessGate>();
		this.spaces = new ArrayList<ParkingSpace>(capacity);
		this.capacity = capacity;
	}

	
	public String getId() {
		return id;
	}
	
	
	public int getOccupancy() {
		
		int occupancy = 0;
		for (ParkingSpace spot : spaces) {
			occupancy += spot.isOccupied() ? 1 : 0;
		}

		return occupancy;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	
	public ParkingSpace getParkingSpace(int i) {
		if ((i < 0) || (i > spaces.size())) {
			return null;
		}
		
		return spaces.get(i);
	}

	
	public boolean isGated() {
		return gates != null && gates.size() > 0;
	}
	
	
	public boolean isFull() {
		return getOccupancy() >= capacity;
	}
	
	
	public void closeAndlockGates() {
		for (AccessGate gate : gates) {
			gate.close();
			gate.lock();
		}
	}

	public void unlockAndOpenGates() {
		for (AccessGate gate : gates) {
			gate.unlock();
			gate.open();
		}
	}


	public boolean isAnyGateOpen() {
		
		if (gates.size() == 0) {
			return true;
		}
		
		for (AccessGate gate : gates) {
			if (gate.isOpen()) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns true if the given vehicle was 
	 * successfully parked in this ParkingSpace.
	 * 
	 * @param v
	 * @return
	 */
	protected boolean acceptVehicle(Vehicle v) {
		return lot.acceptVehicle(v) && isAnyGateOpen();
	}


	public void addParkingSpace(ParkingSpace ps) {
		if (spaces.size() < capacity) {
			spaces.add(ps);
		}
	}

	public void addAccessGate(AccessGate gate) {
		gates.add(gate);
	}
}