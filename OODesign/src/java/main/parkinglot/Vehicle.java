package parkinglot;

public class Vehicle {
	
	private Dimensions dimensions;
	
	public Vehicle(Dimensions d) {
		this.dimensions = d;
	}

	public Dimensions getDimensions() {
		return this.dimensions;
	}
	

	public void park(ParkingSpace spot) {
		spot.acceptVehicle(this);
	}
}