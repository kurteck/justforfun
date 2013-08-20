package parkinglot;

public class ParkingLotTest {
	
	public static void main(String[] args) {
		
		ParkingLot deck = createParkingLot();
		System.out.println("Capacity: " + deck.getCapacaity());
		System.out.println("Occupancy: " + deck.getOccupancy());

		Vehicle hummer = new Vehicle(new Dimensions(22, 10, 9));
		Vehicle winnie = new Vehicle(new Dimensions(40, 12, 16));
		Vehicle smarty = new Vehicle(new Dimensions(6, 6, 6));
		
		winnie.park(deck.getParkingLevel(0).getParkingSpace(0));
		smarty.park(deck.getParkingLevel(1).getParkingSpace(14));		
		winnie.park(deck.getParkingLevel(0).getParkingSpace(49));
		System.out.println("Occupancy: " + deck.getOccupancy());
		
		deck.closeAndlockGates();
		hummer.park(deck.getParkingLevel(0).getParkingSpace(40));
		System.out.println("Occupancy: " + deck.getOccupancy());

		deck.unlockAndOpenGates();
		hummer.park(deck.getParkingLevel(0).getParkingSpace(40));
		System.out.println("Occupancy: " + deck.getOccupancy());
	}
	
	
	private static ParkingLot createParkingLot() {

		ParkingLot lot = new ParkingLot();

		lot.addLevel(createParkingLevel("L0", 50, lot));
		lot.addLevel(createParkingLevel("L1", 20, lot));

		return lot;
	}
	
	private static ParkingLevel createParkingLevel(String id, int capacity, ParkingLot lot) {
		
		ParkingLevel level = new ParkingLevel(id, capacity, lot);
		for (int spot=0; spot < capacity; spot++) {

			String spotId = id + "S" + spot; 
			Dimensions dimensions = Dimensions.NORMAL;
			if (spot < 10) {
				dimensions = Dimensions.NORMAL;
			}
			else if (spot < 36) {
				dimensions = Dimensions.COMPACT;
			}
			else if (spot < 48) {
				dimensions = Dimensions.OVERSIZE;
			}
			else {
				dimensions = Dimensions.JUMBO;
			}
			
			level.addParkingSpace(new ParkingSpace(spotId, dimensions, level));
		}
		
		level.addAccessGate(new AccessGate());
		
		return level;
	}
	
}